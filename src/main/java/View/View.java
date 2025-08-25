package View;

import Controller.GameController;
import Controller.MenuController;
import Model.GameModel;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class View {
    private final Stage stage;

    private Scene scene;
    private MenuView menuView;
    private GameView gameView;
    private ScoreboardView scoreboardView;

    public View(Stage stage) {
        this.stage = stage;
    }

    public void setUp(MenuView menuView, GameView gameView, ScoreboardView scoreboardView) {
        this.menuView = menuView;
        this.gameView = gameView;
        this.scoreboardView = scoreboardView;

        this.scene = new Scene(menuView, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public void showMenu() {
        scene.setRoot(menuView);
        menuView.requestFocus();
    }

    public void showGame() {
        scene.setRoot(gameView);
        gameView.requestFocus();
    }

    public void showScoreboard() {
        scene.setRoot(scoreboardView);
    }

    public void quit() {
        stage.close();
    }

    public Scene getScene() { return scene;}

    public Stage getStage() { return stage; }

    public MenuView getMenuView() { return menuView; }

    public GameView getGameView() { return gameView; }

    public ScoreboardView getScoreboardView() { return scoreboardView; }

    public void setMenuView(MenuView menuView) { this.menuView = menuView; }

    public void setGameView(GameView gameView) { this.gameView = gameView; }

    public void setScoreboardView(ScoreboardView scoreboardView) { this.scoreboardView = scoreboardView; }
}
