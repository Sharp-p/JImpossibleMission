package Controller;

import Model.GameModel;

import Model.MenuModel;
import View.MenuView;
import View.View;
import View.ScoreboardView;
import View.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JImpossibleMission");

        View view = new View(primaryStage);

        // set-ups the Menu section
        MenuModel menuModel = new MenuModel();
        MenuView menuView = new MenuView(menuModel, view);

        // set-ups the Scoreboard section
        // TODO: ScoreboardModel
        // TODO: ScoreboardController
        ScoreboardView scoreboardView = new ScoreboardView(view);

        // set-ups the Game section
        GameModel gameModel = new GameModel();
        GameView gameView = new GameView(gameModel, view);

        view.setUp(menuView, gameView, scoreboardView);

        MenuController menuController = new MenuController(menuModel, view);
        GameController gameController = new GameController(gameModel, view);

        view.showMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}