package Controller;

import Model.*;
import Utilities.Tuple;
import View.View;
import View.GameView;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Scale;

import java.util.*;

import static Controller.CollisionHandler.checkCollision;
import static Controller.CollisionHandler.getBounds;
import static Model.Direction.*;
import static config.GameConstants.*;

public class GameController {
    private final GameModel gameModel;
    private final View view;
    private final Set<KeyCode> pressedKeys = new HashSet<>();

    private AnimationTimer gameLoopTimer;

    private Double deltaTime = 0.0;
    private long lastTime;

    public GameController(GameModel gameModel, View view) {
        this.gameModel = gameModel;
        this.view = view;

        // asynchronous input listening
        view.getGameView().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                view.showMenu();
                stopGameLoop();
            }
            pressedKeys.add(e.getCode());
            System.out.println(e.getCode());
        });
        view.getGameView().setOnKeyReleased(e -> {
            if ((e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT)
                    && gameModel.getAgent().isGrounded()) {
                view.getGameView().getAgentPainter()
                        .getAnimationHandler().play("idle");
            }

            if (e.getCode() == KeyCode.UP) {
                Optional<FurniturePiece> furniturePiece =
                        gameModel.getFurniture().stream()
                                .filter(FurniturePiece::isBeingSearched)
                                .findFirst();

                if (furniturePiece.isPresent()) {
                    view.getGameView().getAgentPainter()
                            .getAnimationHandler().play("idle");
                    furniturePiece.get().setSearching(false);
                }
            }
            pressedKeys.remove(e.getCode());
        });
        view.getGameView().setFocusTraversable(true);

        // first view update
        gameModel.updated(deltaTime);


        // TODO: end_room se premo UP controlla se ho tutti i psw_piece della partita
        // TODO: se un furniture piece non è visible vuol dire
        //  che l'ho cercato e qualsisi code al suo interno l'ho trovato

        // before starting the game loop, checks if any piece of furniture contains a psw_piece
        // if not takes a furniture piece and set the code to a psw_piece
        if (gameModel.getFurniture().stream()
                .noneMatch(p -> p.getCode().getType() == CodeType.PSW_PIECE)) {
            Optional<FurniturePiece> furniturePiece = gameModel.getFurniture().stream()
                    .findAny();

            furniturePiece.ifPresent(piece -> piece.setCode(new Code(CodeType.PSW_PIECE)));

        }
    }

    private void gameLoop(long now) {
        deltaTime = (now - lastTime) / 1_000_000_000.0;

        // TODO: if terminal isActive: terminal else OperazioniNormali

        // OPERATIONS ON THE PLAYER POSITION:

        // if the agent was in air and has now hit the ground, changes the animation
        // and updates the flag
        if (gameModel.getAgent().hasHitGround()) {
            view.getGameView().getAgentPainter().getAnimationHandler().play("idle");
            gameModel.getAgent().setHitGround(false);
        }

        // takes keys in input only if on the ground
        if (gameModel.getAgent().isGrounded()) {

            //System.out.println("Direzione: " + gameModel.getAgent().getDirection());
            if (pressedKeys.contains(KeyCode.UP) || (gameModel.isUsingLift() && gameModel.getAgent().getDirection() == UP)) {
                view.getGameView().getAgentPainter().getAnimationHandler().play("idle");
                platformMovement(UP);
                searchFurniture(deltaTime);
            }
            if (pressedKeys.contains(KeyCode.DOWN) || (gameModel.isUsingLift() && gameModel.getAgent().getDirection() == DOWN)) {
                view.getGameView().getAgentPainter().getAnimationHandler().play("idle");
                platformMovement(DOWN);
            }

            if (!gameModel.isUsingLift()) {
                if (pressedKeys.contains(KeyCode.RIGHT)) {
                    view.getGameView().getAgentPainter().getAnimationHandler().play("run");
                    gameModel.moveAgent(RIGHT, deltaTime);
                }

                if (pressedKeys.contains(KeyCode.LEFT)) {
                    view.getGameView().getAgentPainter().getAnimationHandler().play("run");
                    gameModel.moveAgent(LEFT, deltaTime);
                }

                if (pressedKeys.contains(KeyCode.SPACE)) {
                    view.getGameView().getAgentPainter().getAnimationHandler().play("jump");
                    gameModel.moveAgent(UP, deltaTime);
                }
            }
        }
        else if (!(Objects.equals(view.getGameView().getAgentPainter()
                .getAnimationHandler().getCurrentAnimation().getName(), "jump"))) {
            view.getGameView().getAgentPainter().getAnimationHandler().play("falling");
        }
        gameModel.applyPhysics(deltaTime);

        // OPERATIONS ON THE ENEMIES
        // for each robot applies it's behavior
        // for each robot with sight
        gameModel.getRobots().stream()
                .filter(r -> r.getClass() == SightRobot.class)
                .map(r -> (SightRobot) r)
                .forEach(r -> r.update(deltaTime, gameModel.getAgent()
                ));

        // for each moving robot (exact class) and still robot
        gameModel.getRobots().stream()
                .filter(r -> r.getClass() != SightRobot.class)
                        .forEach(r -> r.update(deltaTime));

        handleCollision();
        //System.out.println("Per terra: " + gameModel.getAgent().isGrounded());
        gameModel.updated(deltaTime);
        lastTime = now;
    }

    private void searchFurniture(double deltaTime) {
        List<FurniturePiece> furniture = gameModel.getFurniture();
        Rectangle2D agentBorder = getBounds(gameModel.getAgent());

        for (FurniturePiece furniturePiece : furniture) {
            Rectangle2D furnitureBorder = getBounds(furniturePiece);

            // if half of the player is in front of
            // the furniture piece allows the search and the furniture is active
            //
            if (furnitureBorder.contains(agentBorder.getMinX() + agentBorder.getWidth() / 2,
                    agentBorder.getMaxY() - 3)
                    && furniturePiece.isActive()) {
                view.getGameView().getAgentPainter().getAnimationHandler().play("searching");

                furniturePiece.use(deltaTime);
            }
        }
    }

    private void platformMovement(Direction dir) {
        List<MovingPlatform> platforms = gameModel.getMovingPlatforms();
        Rectangle2D agentBorder = getBounds(gameModel.getAgent());

        for (MovingPlatform platform : platforms) {
            Rectangle2D platformBorder = getBounds(platform);
            // checks if the agent is fully on the platform
            if (checkCollision(platform, gameModel.getAgent())
                    && agentBorder.getMinX() > platformBorder.getMinX()
                    && agentBorder.getMaxX() < platformBorder.getMaxX()) {

                double oldY = platform.getPosition().getSecond();
                double difY = 0.0;
                Rectangle2D slot = null;
                Optional<Rectangle2D> opt = platform.getVerticalSlots().stream()
                        .filter(platformBorder::intersects).findFirst();

                if (opt.isPresent()) {
                    slot = opt.get();
                }

                // if I'm not using a lift I'm surely on a slot
                if (!gameModel.isUsingLift()) {
                    // save the state of the direction at the start of using the lift
                    gameModel.getAgent().setOldDirection(gameModel.getAgent().getDirection());
                    Tuple<Double, Double> newPosition;

                    if (dir == UP) {
                        if (!platform.prevSlot()) return;

                        assert slot != null;
                        newPosition = new Tuple<>(
                                platform.getPosition().getFirst(),
                                slot.getMinY() - MovingPlatform.MOVING_PLATFORM_HEIGHT - 1);
                        System.out.println("[PRIMO IF] se ho premuto UP qua");
                    } else {
                        if (!platform.nextSlot()) return;

                        assert slot != null;
                        newPosition = new Tuple<>(
                                platform.getPosition().getFirst(),
                                slot.getMaxY() + 1);
                        // using difY to keep the agent stuck
                        // to the platform when going down
                        difY = newPosition.getSecond() - platform.getPosition().getSecond();
                        gameModel.getAgent().setPosition(new Tuple<>(
                                gameModel.getAgent().getPosition().getFirst(),
                                gameModel.getAgent().getPosition().getSecond() + difY
                        ));
                        System.out.println("[PRIMO IF]QUESTO é LO SLOT: " + slot);
                    }

                    platform.setPosition(newPosition);
                    System.out.println("NON USANDO LIFT");
                }
                // if the lift is already moving it could now
                // be on a slot, and it should stop if it is
                else if (platformBorder.intersects(slot)) {
                    Tuple<Double, Double> newPosition;
                    if (dir == UP) {
                        assert slot != null;
                        newPosition = new Tuple<>(
                                platform.getPosition().getFirst(), slot.getMinY() - 3);
                        System.out.println("[SECONDO IF] sto andando già up");
                    }
                    else {
                        assert slot != null;
                        System.out.println("[SECONDO IF]QUESTO é LO SLOT: " + slot);
                        newPosition = new Tuple<>(platform.getPosition().getFirst(), slot.getMinY() - 3);
                        // using difY to keep the agent stuck
                        // to the platform when going down
                        difY = newPosition.getSecond() - platform.getPosition().getSecond();
                        gameModel.getAgent().setPosition(new Tuple<>(
                                gameModel.getAgent().getPosition().getFirst(),
                                gameModel.getAgent().getPosition().getSecond() + difY
                        ));
                    }
                    // difY is need for the platforms in the group too
                    difY = newPosition.getSecond() - platform.getPosition().getSecond();

                    platform.setPosition(newPosition);

                    gameModel.setUsingLift(false);

                    // always move the other platforms in the group
                    for (MovingPlatform sister : platform.getGroup()) {
                        sister.setPosition(new Tuple<>(
                                sister.getPosition().getFirst(),
                                sister.getPosition().getSecond() + difY
                        ));
                    }
                    // reapply the old status of the facing direction
                    gameModel.getAgent().setDirection(gameModel.getAgent().getOldDirection());
                    System.out.println("USANDO LIFT");

                    // stops from moving any further
                    return;
                }
                gameModel.setUsingLift(true);
                //gameModel.getAgent().setSpeed(MovingPlatform.SPEED);
                platform.moveTo(dir, deltaTime);
                // using difY to update the other platforms in the group accordingly
                difY = platform.getPosition().getSecond() - oldY;
                // updates all the platforms in the group
                for (MovingPlatform sister : platform.getGroup()) {
                    sister.setPosition(new Tuple<>(
                            sister.getPosition().getFirst(),
                            sister.getPosition().getSecond() + difY
                    ));
                    System.out.println("SARO PRINTATO ESCLUSIVAMENTE UNA VOLTA");
                }
                gameModel.moveAgent(dir, deltaTime);
            }
        }
    }

    // TODO: [REFACTOR] cambiare nome variabile
    private void platformCollision(Agent entity) {
        boolean touchedGround = false;
        // System.out.println("pre-ciclo");
        for (Platform platform : gameModel.getPlatforms()) {
            if (checkCollision(entity, platform)) {
                Rectangle2D entBorder = getBounds(entity);
                Rectangle2D pltBorder = getBounds(platform);

//                System.out.println("Agente: \n\t("
//                        + entBorder.getMinX() + ", " + entBorder.getMinY() + "\n\t"
//                        + entBorder.getMaxX() + ", " + entBorder.getMinY() + "\n\t"
//                        + entBorder.getMinX() + ", " + entBorder.getMaxY() + "\n\t"
//                        + entBorder.getMaxX() + ", " + entBorder.getMaxY() + "\n\t");
//
//                System.out.println("Piattaforma: \n\t("
//                        + pltBorder.getMinX() + ", " + pltBorder.getMinY() + ")\n\t"
//                        + pltBorder.getMaxX() + ", " + pltBorder.getMinY() + ")\n\t"
//                        + pltBorder.getMinX() + ", " + pltBorder.getMaxY() + ")\n\t"
//                        + pltBorder.getMaxX() + ", " + pltBorder.getMaxY() + ")");

                double x = entity.getPosition().getFirst();
                double y = entity.getPosition().getSecond();
                double vX = entity.getVelocity().getFirst();
                double vY = entity.getVelocity().getSecond();

                // is inside the platform "walkable area"
//                if (entBorder.getMaxY() > pltBorder.getMinY() - 3
//                        && entBorder.getMinY() < pltBorder.getMinY()) {
//                    System.out.println("da sopra");
//
//                    touchedGround = true;
//                    // has collided with the platform from over it
//                    if (entBorder.getMaxY() > pltBorder.getMinY() + 3
//                            && entBorder.getMinY() < pltBorder.getMinY()) {
//                        double newY = pltBorder.getMinY() - entity.getSize().getSecond() + 1;
//
//                        entity.setPosition(new Tuple<>(x, newY));
//                        entity.setVelocity(new Tuple<>(vX, 0.0));
//
//                        if(!entity.isGrounded()) entity.setHitGround(true);
//                        entity.setGrounded(true);
//                    }
//                }
                // TODO: refactor: separare meglio i casi, tirare fuori i lati
                // distringuo i casi destri
                if (entBorder.getMinX() < pltBorder.getMaxX() && entBorder.getMinX() > pltBorder.getMinX()) {
                    // distinguo sopra o lato
                    if (pltBorder.contains(entBorder.getMinX(), entBorder.getMaxY()))
                    {
                        // se dal lato
                        if (entBorder.getMaxY() - pltBorder.getMinY() > pltBorder.getMaxX() - entBorder.getMinX()) {
                            //System.out.println("da destra");
                            double newX = pltBorder.getMaxX();
                            entity.setPosition(new Tuple<>(newX, y));
                            entity.setVelocity(new Tuple<>(0.0, vY));
                        }
                        else {
                            //System.out.println("da sopra");

                            touchedGround = true;
                            double newY = pltBorder.getMinY() - entity.getSize().getSecond() + 1;

                            entity.setPosition(new Tuple<>(x, newY));
                            entity.setVelocity(new Tuple<>(vX, 0.0));

                            if(!entity.isGrounded()) entity.setHitGround(true);
                            entity.setGrounded(true);
                        }
                    }
                    // distinguo sotto e lato
                    else if (pltBorder.contains(entBorder.getMinX(), entBorder.getMinY())) {
                        // se dal lato
                        if (pltBorder.getMaxY() - entBorder.getMinY() > pltBorder.getMaxX() - entBorder.getMinX()) {
                            //System.out.println("da destra");
                            double newX = pltBorder.getMaxX();
                            entity.setPosition(new Tuple<>(newX, y));
                            entity.setVelocity(new Tuple<>(0.0, vY));
                        }
                        else {
                            System.out.println("da sotto");
                            double newY = pltBorder.getMaxY();
                            entity.setPosition(new Tuple<>(x, newY));
                            entity.setVelocity(new Tuple<>(vX, 0.0));
                        }
                    }
                    else {
                        //System.out.println("da destra");
                        double newX = pltBorder.getMaxX();
                        entity.setPosition(new Tuple<>(newX, y));
                        entity.setVelocity(new Tuple<>(0.0, vY));
                    }
                }
                // distinguo a sinistra, se da sopra, sotto o lato
                else if (entBorder.getMaxX() > pltBorder.getMinX() && entBorder.getMaxX() < pltBorder.getMaxX()) {
                    // distinguo sopra o lato
                    if (pltBorder.contains(entBorder.getMaxX(), entBorder.getMaxY())) {
                        // se dal lato
                        if (entBorder.getMaxY() - pltBorder.getMinY() > entBorder.getMaxX() - pltBorder.getMinX()) {
                            //System.out.println("da sinistra");
                            double newX = pltBorder.getMinX() - entity.getSize().getFirst();
                            entity.setPosition(new Tuple<>(newX, y));
                            entity.setVelocity(new Tuple<>(0.0, vY));
                        }
                        else {
                            //System.out.println("da sopra");

                            touchedGround = true;
                            double newY = pltBorder.getMinY() - entity.getSize().getSecond() + 1;

                            entity.setPosition(new Tuple<>(x, newY));
                            entity.setVelocity(new Tuple<>(vX, 0.0));

                            if(!entity.isGrounded()) entity.setHitGround(true);
                            entity.setGrounded(true);
                        }
                    }
                    // distinugo lato e sotto
                    else if (pltBorder.contains(entBorder.getMaxX(), entBorder.getMinY())){
                        // se dal lato
                        if (pltBorder.getMaxY() - entBorder.getMinY() > entBorder.getMaxX() - pltBorder.getMinX()) {
                            //System.out.println("da sinistra");
                            double newX = pltBorder.getMinX() - entity.getSize().getFirst();
                            entity.setPosition(new Tuple<>(newX, y));
                            entity.setVelocity(new Tuple<>(0.0, vY));
                        }
                        else {
                            System.out.println("da sotto");
                            double newY = pltBorder.getMaxY();
                            entity.setPosition(new Tuple<>(x, newY));
                            entity.setVelocity(new Tuple<>(vX, 0.0));
                        }
                    }
                    else {
                        //System.out.println("da sinistra");
                        double newX = pltBorder.getMinX() - entity.getSize().getFirst();
                        entity.setPosition(new Tuple<>(newX, y));
                        entity.setVelocity(new Tuple<>(0.0, vY));
                    }
                }
                else {
                    //System.out.println("da sopra");

                    touchedGround = true;
                    double newY = pltBorder.getMinY() - entity.getSize().getSecond() + 1;

                    entity.setPosition(new Tuple<>(x, newY));
                    entity.setVelocity(new Tuple<>(vX, 0.0));

                    if(!entity.isGrounded()) entity.setHitGround(true);
                    entity.setGrounded(true);
                }
            }
        }
        // if it didn't collide with any platform from over it, it's falling
        if (!touchedGround) entity.setGrounded(false);
    }

    public void startGameLoop() {


        // gets the system time needed for the deltaTime in the game loop
        lastTime = System.nanoTime();
        // gameloop
        gameLoopTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                view.getGameView().clearCanvas();
                gameLoop(now);
            }
        };
        gameLoopTimer.start();
    }

    public void stopGameLoop() {
        gameLoopTimer.stop();
    }

    public void handleCollision() {
        double osY = gameModel.getAgent().getSize().getSecond();

        // updates the frame so it gets the right size for the collision updates
        view.getGameView().getAgentPainter().getAnimationHandler().update(deltaTime);
        view.getGameView().getAgentPainter().updateEntitySize();
//        System.out.println("Size: " + gameModel.getAgent().getSize().getFirst() +
//                " "  + gameModel.getAgent().getSize().getSecond());
        double difY = osY - gameModel.getAgent().getSize().getSecond();

        // adjust the position with the updated size
        gameModel.getAgent().setPosition(new Tuple<>(
                gameModel.getAgent().getPosition().getFirst(),
                gameModel.getAgent().getPosition().getSecond() + difY
        ));

        platformCollision(gameModel.getAgent());

        enemyCollision(gameModel.getAgent());
    }

    public void enemyCollision(Agent agent) {
        Rectangle2D agentBorder = getBounds(agent);
        for (Enemy enemy : gameModel.getEnemies()) {
            Rectangle2D enemyBorder = getBounds(enemy);
            if (enemyBorder.intersects(agentBorder) && enemy.isActive()) {
                agentHit();
            }
        }
    }

    public void agentHit() {

        // TODO: Rewrite respawn system una volta che ho le Room
        // TODO: fare Room con coordinate di respawn
        // TODO: per ogni room, controlla se interseziona personagio, se si,
        //  usa le sue coordinate di respawn

        // TODO: una volta implementato Statistics aggiungere 10 min al timer
        stopGameLoop();
        gameModel.createAgent(13, ROW_HEIGHT - 30);
        GameView gameView = new GameView(view);
        gameView.setGameModel(gameModel);
//        double scaleFactor = Math.min((SCREEN_WIDTH + 15) / LOGICAL_WIDTH, SCREEN_HEIGHT / LOGICAL_HEIGHT);
        Scale scale = new Scale(SCALE_FACTOR, SCALE_FACTOR, 0, 0);
        gameView.setScale(scale);
        view.setGameView(gameView);
        GameController gameController = new GameController(gameModel, view);
        gameController.startGameLoop();
        view.showGame();
    }
}
