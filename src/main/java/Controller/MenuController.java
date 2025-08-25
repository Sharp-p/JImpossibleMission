package Controller;

import Model.MenuModel;
import View.View;
import javafx.scene.input.KeyCode;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
        System.out.println("Sono un controler vivo");
        view.getMenuView().setOnKeyPressed(e -> {
        System.out.println("Sono stato premuto: " + e.getCode());
            switch(e.getCode()) {
                case KeyCode.UP -> menuModel.moveUp();
                case KeyCode.DOWN -> menuModel.moveDown();
                case KeyCode.ENTER -> {
                    int choice = menuModel.getSelectedIndex();
                    System.out.println("Hai scelto: " + menuModel.getOptions().get(choice));

                    switch (choice) {
                        case 0 -> view.showGame();
                        case 1 -> view.showScoreboard();
                        case 3 -> System.exit(0);
                        default -> System.out.println("CON CALMA ANCORA NON LI HO FATTI STI PANNELLI");
                    }

                }
            }
        });
    }

    /**
     * Sets the give MenuModel to be controlled from this MenuController
     * @param menuModel The MenuModel to control
     */
    public void setMenuModel(MenuModel menuModel) {
        this.menuModel = menuModel;

        view.getMenuView().setOnKeyPressed(e -> {
            switch(e.getCode()) {
                case KeyCode.UP -> menuModel.moveUp();
                case KeyCode.DOWN -> menuModel.moveDown();
                case KeyCode.ENTER -> {
                    int choice = menuModel.getSelectedIndex();
                    System.out.println("Hai scelto: " + menuModel.getOptions().get(choice));

                    switch (choice) {
                        case 0 -> view.showGame();
                        case 1 -> view.showScoreboard();
                        case 3 -> System.exit(0);
                        default -> System.out.println("CON CALMA ANCORA NON LI HO FATTI STI PANNELLI");
                    }

                }
            }
        });
    }
}
