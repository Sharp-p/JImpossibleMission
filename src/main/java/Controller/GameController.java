package Controller;

import Model.*;
import Utilities.Tuple;
import View.View;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Shape;

import java.util.*;

import static Controller.CollisionHandler.checkCollision;
import static Controller.CollisionHandler.getBounds;
import static Model.Direction.*;

public class GameController {
    private final GameModel gameModel;
    private final View view;
    private final Set<KeyCode> pressedKeys = new HashSet<>();

    private Double deltaTime = 0.0;
    private long lastTime;

    public GameController(GameModel gameModel, View view) {
        this.gameModel = gameModel;
        this.view = view;

        // asynchronous input listening
        view.getGameView().setOnKeyPressed(e -> {
            pressedKeys.add(e.getCode());
            System.out.println(e.getCode());
        });
        view.getGameView().setOnKeyReleased(e -> {
            if ((e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT)
                    && gameModel.getAgent().isGrounded()) {
                view.getGameView().getAgentPainter()
                        .getAnimationHandler().play("idle");
            }
            pressedKeys.remove(e.getCode());
        });
        view.getGameView().setFocusTraversable(true);

        // first view update
        gameModel.updated(deltaTime);

        // gets the system time needed for the deltaTime in the game loop
        lastTime = System.nanoTime();
        // gameloop
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                view.getGameView().clearCanvas();
                gameLoop(now);
            }
        }.start();
    }

    private void gameLoop(long now) {
        deltaTime = (now - lastTime) / 1_000_000_000.0;


        // if the agent was in air and has now hit the ground, changes the animation
        // and updates the flag
        if (gameModel.getAgent().hasHitGround()) {
            view.getGameView().getAgentPainter().getAnimationHandler().play("idle");
            gameModel.getAgent().setHitGround(false);
        }

        // takes keys in input only if on the ground
        if (gameModel.getAgent().isGrounded()) {
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
            // TODO: gestore dei input UP e DOWN sulle piattaforme movibili
            if (pressedKeys.contains(KeyCode.UP)) platformMovement(UP);
            if (pressedKeys.contains(KeyCode.DOWN)) platformMovement(DOWN);

        }
        else if (!(Objects.equals(view.getGameView().getAgentPainter()
                .getAnimationHandler().getCurrentAnimation().getName(), "jump"))) {
            view.getGameView().getAgentPainter().getAnimationHandler().play("falling");
        }


        gameModel.applyPhysics(deltaTime);

        handleCollision();
        // System.out.println("Per terra: " + gameModel.getAgent().isGrounded());
        gameModel.updated(deltaTime);

        lastTime = now;
    }

    // TODO: prende le piattaforme movibili, controlla se l'agent ci è sopra, se si, applica il movimento
    private void platformMovement(Direction dir) {
        List<Platform> platforms = gameModel.getMovingPlatforms();

        Rectangle2D agentBorder = getBounds(gameModel.getAgent());
        for (Platform platform : platforms) {
            if(checkCollision(platform, gameModel.getAgent())) {
                Rectangle2D platformBorder = getBounds(platform);
                platform.moveTo(dir, deltaTime);
            }
        }
    }
    // TODO: tipo di classe da passare Agent o entity
    private void platformCollision(Agent entity) {
        boolean touchedGround = false;
        System.out.println("pre-ciclo");
        for (Platform platform : gameModel.getPlatforms()) {
            if (checkCollision(entity, platform)) {
                Rectangle2D entBorder = getBounds(entity);
                Rectangle2D pltBorder = getBounds(platform);

                System.out.println("Agente: \n\t("
                        + entBorder.getMinX() + ", " + entBorder.getMinY() + "\n\t"
                        + entBorder.getMaxX() + ", " + entBorder.getMinY() + "\n\t"
                        + entBorder.getMinX() + ", " + entBorder.getMaxY() + "\n\t"
                        + entBorder.getMaxX() + ", " + entBorder.getMaxY() + "\n\t");

                System.out.println("Piattaforma: \n\t("
                        + pltBorder.getMinX() + ", " + pltBorder.getMinY() + ")\n\t"
                        + pltBorder.getMaxX() + ", " + pltBorder.getMinY() + ")\n\t"
                        + pltBorder.getMinX() + ", " + pltBorder.getMaxY() + ")\n\t"
                        + pltBorder.getMaxX() + ", " + pltBorder.getMaxY() + ")");

                double x = entity.getPosition().getFirst();
                double y = entity.getPosition().getSecond();
                double vX = entity.getVelocity().getFirst();
                double vY = entity.getVelocity().getSecond();


                if (platform.getMovementBehavior() instanceof VerticalMovement) {
                    System.out.println("PROCODIOOOO");
                }

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
                            System.out.println("da destra");
                            double newX = pltBorder.getMaxX();
                            entity.setPosition(new Tuple<>(newX, y));
                            entity.setVelocity(new Tuple<>(0.0, vY));
                        }
                        else {
                            System.out.println("da sopra");

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
                            System.out.println("da destra");
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
                        System.out.println("da destra");
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
                            System.out.println("da sinistra");
                            double newX = pltBorder.getMinX() - entity.getSize().getFirst();
                            entity.setPosition(new Tuple<>(newX, y));
                            entity.setVelocity(new Tuple<>(0.0, vY));
                        }
                        else {
                            System.out.println("da sopra");

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
                            System.out.println("da sinistra");
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
                        System.out.println("da sinistra");
                        double newX = pltBorder.getMinX() - entity.getSize().getFirst();
                        entity.setPosition(new Tuple<>(newX, y));
                        entity.setVelocity(new Tuple<>(0.0, vY));
                    }
                }
                else {
                    System.out.println("da sopra");

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

    public void handleCollision() {
        double osY = gameModel.getAgent().getSize().getSecond();

        // updates the frame so it gets the right size for the collision updates
        view.getGameView().getAgentPainter().getAnimationHandler().update(deltaTime);
        view.getGameView().getAgentPainter().updateEntitySize();
//        System.out.println("Size: " + gameModel.getAgent().getSize().getFirst() +
//                " "  + gameModel.getAgent().getSize().getSecond());
        double difY = osY - gameModel.getAgent().getSize().getSecond();

        gameModel.getAgent().setPosition(new Tuple<>(
                gameModel.getAgent().getPosition().getFirst(),
                gameModel.getAgent().getPosition().getSecond() + difY
        ));

        platformCollision(gameModel.getAgent());
    }
}
