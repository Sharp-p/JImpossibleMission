package Controller;

import Model.GameModel;
import View.View;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;

public class GameController {
    private final GameModel model;
    private final View view;
    private final Set<KeyCode> pressedKeys = new HashSet<>();

    public GameController(GameModel model, View view) {
        this.model = model;
        this.view = view;

        // gestione input
        view.getGameView().setOnKeyPressed(e -> pressedKeys.add(e.getCode()));
        view.getGameView().setOnKeyReleased(e -> pressedKeys.remove(e.getCode()));
        view.getGameView().setFocusTraversable(true);

        // gameloop
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameLoop();
            }
        }.start();
    }

    private void gameLoop() {
        if (pressedKeys.contains(KeyCode.RIGHT)) model.movePlayer(3, 0);
        if (pressedKeys.contains(KeyCode.LEFT))  model.movePlayer(-3, 0);
        if (pressedKeys.contains(KeyCode.UP))    model.movePlayer(0, -3);
        if (pressedKeys.contains(KeyCode.DOWN))  model.movePlayer(0, 3);

        // aggiorna la view
        view.getGameView().updatePlayerPosition(model.getPlayerX(), model.getPlayerY());
    }
}
