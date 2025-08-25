package View;

import Model.GameModel;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameView extends Pane {
    private final View view;

    private Rectangle player;
    private GameModel gameModel;

    public GameView(GameModel gameModel, View view) {
        this.gameModel = gameModel;
        this.view = view;

        player = new Rectangle(40, 40, Color.BLUE);

        Button backBtn = new Button("Back");
        getChildren().addAll(player, backBtn);

        backBtn.setOnAction(e -> {view.showMenu();});
    }

    public void updatePlayerPosition(int x, int y) {
        player.setX(x);
        player.setY(y);
    }

}
