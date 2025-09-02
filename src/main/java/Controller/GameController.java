package Controller;

import Model.*;
import Utilities.Tuple;
import View.View;
import View.GameView;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
            if (!gameModel.isPaused()) {
                mainGameKeyPressed(e);
            }
            else if (gameModel.isShowingMenu()) {
                gameMenuKeyPressed(e);
            }
            pressedKeys.add(e.getCode());
            System.out.println(e.getCode());
        });
        view.getGameView().setOnKeyReleased(e -> {
            // not main game GameMenu or statistics
            if (!gameModel.isPaused()) mainGameKeyReleased(e);
            else if (gameModel.isShowingStatistics()) statisticsKeyReleased(e);

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
            gameModel.addTotalPswPieces();
            System.out.println(furniturePiece.get() + "\nSONO ENTRATO QUA");
        }
    }

    private void gameMenuKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.RIGHT) gameModel.nextMenuOption();
        else if (e.getCode() == KeyCode.LEFT) gameModel.previousMenuOption();
        else if (e.getCode() == KeyCode.ENTER) {
            switch (gameModel.getMenuOption()) {
                case 0 -> {
                    gameModel.setShowingMenu(false);
                    gameModel.setPaused(false);
                }
                case 1 -> {
                    gameLoopTimer.stop();
                    gameModel.getStatistics().getGameClock().stop();
                    // TODO: classi per endGame
                    view.showMenu();
                }
            }
        }
    }

    private void gameLoop(long now) {
        deltaTime = (now - lastTime) / 1_000_000_000.0;

        //System.out.println("DeltaTime: " + deltaTime);

        // TODO: if terminal isActive: terminal else OperazioniNormali

        if (!gameModel.isPaused()) mainGame();
        else if (gameModel.isShowingStatistics()) checkShowingStatistics();
        //else pauseMenu();
        //System.out.println("Per terra: " + gameModel.getAgent().isGrounded());

        // to update the camera
        checkViewPort();

        checkTime();

        gameModel.updated(deltaTime);
        lastTime = now;
    }

    /**
     * The method that controls if the agent ha moved in another area.
     * If so it updates the values og the viewport and the spawning point of the agent.
     */
    private void checkViewPort() {
        Rectangle2D currentArea = gameModel.getCurrentArea();
        Rectangle2D agentBorder = getBounds(gameModel.getAgent());
        System.out.println("Current area: " + currentArea);
        if (!currentArea.intersects(agentBorder)) {
            for (int i = 0; i < gameModel.getAreas().size(); i++) {
                if (gameModel.getAreas().get(i).intersects(agentBorder)) {
                    gameModel.setCurrentArea(i);
                    gameModel.setCameraX((int)gameModel.getCurrentArea().getMinX());
                    gameModel.setCameraY((int)gameModel.getCurrentArea().getMinY());

                    gameModel.getAgent().setSpawn(
                            gameModel.getAgent().getPosition().getFirst(),
                            gameModel.getAgent().getPosition().getSecond()
                    );
                    break;
                }
            }
        }
    }

    public void statisticsKeyReleased(KeyEvent e) {
        gameModel.getStatistics().getGameClock().start();
    }

    private void checkShowingStatistics() {
        if (!pressedKeys.contains(KeyCode.CONTROL)) {
            gameModel.setShowingStatistics(false);
            gameModel.setPaused(false);
        }
    }

    private void mainGameKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ESCAPE) {
            gameModel.setPaused(true);
            gameModel.setShowingMenu(true);
        }
        else if (e.getCode() == KeyCode.CONTROL) {
            gameModel.setPaused(true);
            gameModel.setShowingStatistics(true);

            int heightBefore = gameModel.getAgent()
                    .getSize().getSecond().intValue();
            view.getGameView().getAgentPainter()
                    .getAnimationHandler().play("idle");
            int heightAfter = (int) view.getGameView().getAgentPainter()
                    .getAnimationHandler().getCurrentFrameHeight();

            int dif = heightAfter - heightBefore;

            gameModel.getAgent().setPosition(new Tuple<>(
                    gameModel.getAgent().getPosition().getFirst(),
                    gameModel.getAgent().getPosition().getSecond() - dif
            ));

            gameModel.getStatistics().getGameClock().stop();
        }
    }

    private void mainGameKeyReleased(KeyEvent e) {
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
    }

    private void mainGame() {
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
        } else if (!(Objects.equals(view.getGameView().getAgentPainter()
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

        //System.out.println("Pre collisioni: " + gameModel.getAgent().getPosition());
        handleCollision();

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
                if (!furniturePiece.isActive()) {
                    switch (furniturePiece.getCode().getType()) {
                        case PSW_PIECE -> gameModel.foundPswPiece();
                        case ROBOT -> gameModel.addRobotsCode();
                        case PLATFORMS -> gameModel.addPlatformsCode();
                    }
                }
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
//                    Tuple<Double, Double> newPosition;
//                    if (dir == UP) {
//                        assert slot != null;
//                        newPosition = new Tuple<>(
//                                platform.getPosition().getFirst(), slot.getMinY() - 3);
//
//                        System.out.println(slot);
//                        System.out.println("[SECONDO IF] sto andando già up");
//                    }
//                    else {
//                        assert slot != null;
//                        System.out.println("[SECONDO IF]QUESTO é LO SLOT: " + slot);
//                        newPosition = new Tuple<>(platform.getPosition().getFirst(), slot.getMinY() - 3);
//                        // using difY to keep the agent stuck
//                        // to the platform when going down
//                        difY = newPosition.getSecond() - platform.getPosition().getSecond();
//                        gameModel.getAgent().setPosition(new Tuple<>(
//                                gameModel.getAgent().getPosition().getFirst(),
//                                gameModel.getAgent().getPosition().getSecond() + difY
//                        ));
//                    }
                    assert slot != null;
                    Tuple<Double, Double> newPosition =  new Tuple<>(
                            platform.getPosition().getFirst(), slot.getMinY() - 3);

                    // difY is need for the platforms in the group too
                    difY = newPosition.getSecond() - platform.getPosition().getSecond();
                    gameModel.getAgent().setPosition(new Tuple<>(
                            gameModel.getAgent().getPosition().getFirst(),
                            gameModel.getAgent().getPosition().getSecond() + difY
                    ));
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
    private void platformCollision(Agent entity, double difWidth) {
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


                // TODO: refactor: separare meglio i casi, tirare fuori i lati
                // distringuo i casi destri
                if (entBorder.getMinX() < pltBorder.getMaxX() && entBorder.getMinX() > pltBorder.getMinX()) {
                    // distinguo sopra o lato
                    if (pltBorder.contains(entBorder.getMinX(), entBorder.getMaxY()))
                    {
                        // se dal lato
                        if (entBorder.getMaxY() - pltBorder.getMinY() > pltBorder.getMaxX() - entBorder.getMinX()) {
                            //System.out.println("da destra 1");
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
                            //System.out.println("da destra 2");
                            double newX = pltBorder.getMaxX();
                            entity.setPosition(new Tuple<>(newX, y));
                            entity.setVelocity(new Tuple<>(0.0, vY));
                        }
                        else {
                            //System.out.println("da sotto Destra");
                            double newY = pltBorder.getMaxY();
                            entity.setPosition(new Tuple<>(x, newY));
                            entity.setVelocity(new Tuple<>(vX, 0.0));
                        }
                    }
                    else {
                        //System.out.println("da destra 3");
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
                            //System.out.println("da sinistra 1");
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
                        if (pltBorder.getMaxY() - entBorder.getMinY() > entBorder.getMaxX() - pltBorder.getMinX() + difWidth) {
                            //System.out.println("da sinistra 1");
                            double newX = pltBorder.getMinX() - entity.getSize().getFirst();
                            entity.setPosition(new Tuple<>(newX, y));
                            entity.setVelocity(new Tuple<>(0.0, vY));
                        }
                        else {
                            //System.out.println("da sotto Sinistra");
                            double newY = pltBorder.getMaxY();
                            entity.setPosition(new Tuple<>(x, newY));
                            entity.setVelocity(new Tuple<>(vX, 0.0));
                        }
                    }
                    else {
                        //System.out.println("da sinistra 3");
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
        double osX = gameModel.getAgent().getSize().getFirst();

        // updates the frame so it gets the right size for the collision updates
        view.getGameView().getAgentPainter().getAnimationHandler().update(deltaTime);
        view.getGameView().getAgentPainter().updateEntitySize();
//        System.out.println("Size: " + gameModel.getAgent().getSize().getFirst() +
//                " "  + gameModel.getAgent().getSize().getSecond());

        double difY = osY - gameModel.getAgent().getSize().getSecond();
        double difX = osX - gameModel.getAgent().getSize().getFirst();
        // adjust the position with the updated size
        gameModel.getAgent().setPosition(new Tuple<>(
                gameModel.getAgent().getPosition().getFirst(),
                gameModel.getAgent().getPosition().getSecond() + difY
        ));

        platformCollision(gameModel.getAgent(), difX);

        enemyCollision(gameModel.getAgent());
    }

    private void checkTime() {
        // ends the game if the hour is past 18pm
        if (gameModel.getTime() >= 3600 * 18) {
            gameModel.setEnded(true);
        }
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
        // TODO: aggiunta 10 min timer ogni morte

        // TODO: ad ogni cambio di area impostare un respawn point nel game
        //  model, e usare quello

        // TODO: per ogni area, controlla se interseziona personagio, se si,
        //  usa le sue coordinate di respawn

        // TODO: una volta implementato Statistics aggiungere 10 min al timer
        gameModel.resetPositions();
        gameModel.addTime(600);

//        stopGameLoop();
//        // area index 0 because the coordinates are global
//        gameModel.createAgent(
//                gameModel.getAgent().getSpawn().getFirst(),
//                gameModel.getAgent().getSpawn().getSecond(),
//                0);
//        GameView gameView = new GameView(view);
//        gameView.setGameModel(gameModel);
////        double scaleFactor = Math.min((SCREEN_WIDTH + 15) / LOGICAL_WIDTH, SCREEN_HEIGHT / LOGICAL_HEIGHT);
//        Scale scale = new Scale(SCALE_FACTOR, SCALE_FACTOR, 0, 0);
//        gameView.setScale(scale);
//        view.setGameView(gameView);
//        GameController gameController = new GameController(gameModel, view);
//        gameController.startGameLoop();
//        view.showGame();
    }
}
