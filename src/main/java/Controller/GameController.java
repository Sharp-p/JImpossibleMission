package Controller;

import Model.GameModel;
import View.View;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;

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

        // gestione input
        view.getGameView().setOnKeyPressed(e -> pressedKeys.add(e.getCode()));
        view.getGameView().setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT)
                view.getGameView().getAgentPainter()
                        .getAnimationHandler().play("idle");

            pressedKeys.remove(e.getCode());
        });
        view.getGameView().setFocusTraversable(true);

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

        if (pressedKeys.contains(KeyCode.RIGHT)) {
            view.getGameView().getAgentPainter().getAnimationHandler().play("run");
            gameModel.moveAgent(RIGHT, deltaTime);
        }

        if (pressedKeys.contains(KeyCode.LEFT)) {
            view.getGameView().getAgentPainter().getAnimationHandler().play("run");
            gameModel.moveAgent(LEFT, deltaTime);
        }

        if (pressedKeys.contains(KeyCode.UP))    gameModel.moveAgent(UP, deltaTime);
        if (pressedKeys.contains(KeyCode.DOWN))  gameModel.moveAgent(DOWN, deltaTime);

        gameModel.loopUpdate(deltaTime);

        lastTime = now;
    }
}
