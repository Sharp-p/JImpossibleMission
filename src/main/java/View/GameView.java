package View;

import Model.GameModel;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Observable;
import java.util.Observer;

public class GameView extends Pane implements Observer {
    private final View view;

    private Rectangle player;
    private GameModel gameModel;

    public GameView(View view) {
        this.view = view;

        player = new Rectangle(40, 40, Color.BLUE);

        Button backBtn = new Button("Back");
        getChildren().addAll(player, backBtn);

        backBtn.setOnAction(e -> {view.showMenu();});
    }

    public void updatePlayerPosition(double x, double y) {
        player.setX(x);
        player.setY(y);
    }

    @Override
    public void update(Observable o, Object arg) {
        updatePlayerPosition(gameModel.getAgent().getPosition().getFirst(), gameModel.getAgent().getPosition().getSecond());
    }

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
        gameModel.addObserver(this);
    }
}
