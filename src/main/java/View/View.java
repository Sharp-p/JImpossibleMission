package View;

import Controller.GameController;
import Controller.MenuController;
import Model.GameModel;
import javafx.scene.Scene;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import static config.GameConstants.*;

public class View {
    private final Stage stage;

    private Scene scene;
    private MenuView menuView;
    private GameView gameView;
    private ScoreboardView scoreboardView;
    private ProfileView profileView;

    public View(Stage stage) {
        this.stage = stage;
    }

    public void setUp(MenuView menuView, GameView gameView, ScoreboardView scoreboardView, ProfileView profileView) {
        this.menuView = menuView;
        this.gameView = gameView;
        this.scoreboardView = scoreboardView;
        this.profileView = profileView;

        //menuView.setPrefSize(LOGICAL_WIDTH, LOGICAL_HEIGHT);
        gameView.setPrefSize(LOGICAL_WIDTH, LOGICAL_HEIGHT);
        //scoreboardView.setPrefSize(LOGICAL_WIDTH, LOGICAL_HEIGHT);

        this.scene = new Scene(menuView, SCREEN_WIDTH, SCREEN_HEIGHT);

        //double scaleFactor = Math.min((double)SCREEN_WIDTH / LOGICAL_WIDTH, (double)SCREEN_HEIGHT / LOGICAL_HEIGHT);
        Scale scale = new Scale(SCALE_FACTOR, SCALE_FACTOR, 0, 0);
        //menuView.getTransforms().add(scale);
        //gameView.getTransforms().add(scale);
        gameView.setScale(scale);
        //scoreboardView.getTransforms().add(scale);

        stage.setScene(scene);
        stage.show();
    }

    public void showMenu() {
        scene.setRoot(menuView);
        menuView.requestFocus();
    }

    public void showProfiles() {
        scene.setRoot(profileView);
        profileView.requestFocus();
    }

    public void showGame() {
        scene.setRoot(gameView);
        gameView.requestFocus();
    }

    public void showEndGame(EndGameView endGameView) {
        scene.setRoot(endGameView);
        endGameView.requestFocus();
    }

    public void showScoreboard() {
        scene.setRoot(scoreboardView);
        scoreboardView.requestFocus();
    }

    public void quit() {
        stage.close();
    }

    public Scene getScene() { return scene;}

    public Stage getStage() { return stage; }

    public MenuView getMenuView() { return menuView; }

    public GameView getGameView() { return gameView; }

    public ScoreboardView getScoreboardView() { return scoreboardView; }

    public ProfileView getProfileView() { return profileView; }

    public void setMenuView(MenuView menuView) { this.menuView = menuView; }

    public void setGameView(GameView gameView) { this.gameView = gameView; }

    public void setScoreboardView(ScoreboardView scoreboardView) { this.scoreboardView = scoreboardView; }
}
