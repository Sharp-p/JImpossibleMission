package Controller;

import Model.GameModel;

import Model.MenuModel;
import Model.Profile;
import Model.Scoreboard;
import View.MenuView;
import View.View;
import View.ProfileView;
import View.ScoreboardView;
import View.GameView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class starts the game. The structure is dictated by the JavaFX Application structure
 */
public class JImpossibleMission extends Application {

    /**
     * JavaFX start any application from here and gives the programmer a primary stage.
     * @param primaryStage A stage in which to allocate the interface.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JImpossibleMission");

        View view = new View(primaryStage);

        // set-ups the Menu section
        MenuModel menuModel = new MenuModel();
        MenuView menuView = new MenuView(menuModel, view);

        // set-ups the Scoreboard section
        Scoreboard scoreboard = new Scoreboard("src/main/resources/scores.csv");
        ScoreboardView scoreboardView = new ScoreboardView(view, scoreboard);

        // set-ups the Game section
        GameModel gameModel = new GameModel();
        GameView gameView = new GameView(view);

        Profile profile = new Profile("src/main/resources/profiles.csv");
        ProfileView profileView = new ProfileView(view, profile);

        view.setUp(menuView, gameView, scoreboardView, profileView);

        ScoreboardController scoreboardController = new ScoreboardController(scoreboard, view);
        MenuController menuController = new MenuController(menuModel, view);
        ProfilesController profilesController = new ProfilesController(profile, view);

        view.showMenu();
    }

    /**
     * The actual main from which the application starts.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}