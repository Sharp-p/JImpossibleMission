package Controller;

import Model.GameModel;
import Model.MenuModel;
import View.View;
import View.GameView;
import javafx.scene.input.KeyCode;
import javafx.scene.transform.Scale;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static config.GameConstants.*;
import static config.GameConstants.LOGICAL_HEIGHT;
import static config.GameVariables.scale;

/**
 * This class manages the inputs from the keyboard and reacts accordingly
 * updating the menu's model.
 *
 * @author Thomas Sharp
 */
public class MenuController {
    /**
     * Reference to the view to change panel.
     */
    private final View view;

    /**
     * Reference to the model to update its state.
     */
    private MenuModel menuModel;

    public MenuController(MenuModel menuModel, View view) {
        this.menuModel = menuModel;
        this.view = view;

        setMenuListener();
    }

    /**
     * Sets the given MenuModel to be controlled from this MenuController
     * @param menuModel The MenuModel to control
     */
    public void setMenuModel(MenuModel menuModel) {
        this.menuModel = menuModel;

        setMenuListener();
    }

    private void setMenuListener(){
        view.getMenuView().setOnKeyPressed(e -> {
            System.out.println("Sono stato premuto: " + e.getCode());
            switch(e.getCode()) {
                case KeyCode.UP -> menuModel.moveUp();
                case KeyCode.DOWN -> menuModel.moveDown();
                case KeyCode.ENTER -> {
                    int choice = menuModel.getSelectedIndex();
                    System.out.println("Hai scelto: " + menuModel.getOptions().get(choice));

                    switch (choice) {
                        case 0 -> {
                            GameModel gameModel = new GameModel();
                            GameView gameView = new GameView(view);
                            gameView.setGameModel(gameModel);

                            //double scaleFactor = Math.min((double)SCREEN_WIDTH / LOGICAL_WIDTH, (double)SCREEN_HEIGHT / LOGICAL_HEIGHT);
                            Scale scale = new Scale(SCALE_FACTOR, SCALE_FACTOR, 0, 0);
                            gameView.setScale(scale);

                            view.setGameView(gameView);
                            GameController gameController = new GameController(gameModel, view);
                            gameController.startGameLoop();
                            view.showGame();
                        }
                        case 1 -> view.showScoreboard();
                        case 3 -> System.exit(0);
                        default -> System.out.println("CON CALMA ANCORA NON LI HO FATTI STI PANNELLI");
                    }

                }
            }
        });
    }
}
