package View;

import Model.Scoreboard;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class ScoreboardView extends StackPane {
    private final Scoreboard scoreboard = new Scoreboard("src/main/resources/scores.csv");

    public ScoreboardView(View view) {


        Label label = new Label("Scoreboard (placeholder)");

        Button backButton = new Button("Back to Menu");
        backButton.setOnAction(e -> view.showMenu());

        getChildren().addAll(label, backButton);
    }

}
