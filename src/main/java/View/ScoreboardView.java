package View;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ScoreboardView extends VBox {
    public ScoreboardView(View view) {
        setAlignment(Pos.CENTER);
        setSpacing(20);

        Label label = new Label("Scoreboard (placeholder)");

        Button backButton = new Button("Back to Menu");
        backButton.setOnAction(e -> view.showMenu());

        getChildren().addAll(label, backButton);
    }

}
