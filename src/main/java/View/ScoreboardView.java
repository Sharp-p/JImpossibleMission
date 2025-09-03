package View;

import Model.Scoreboard;
import java.util.Observable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Observer;

import static config.GameConstants.*;
import static javafx.scene.paint.Color.*;
import static javafx.scene.paint.Color.WHITE;


public class ScoreboardView extends StackPane implements Observer {
    private final Scoreboard scoreboard;
    private BorderPane borderPane = new BorderPane();
    private Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_WIDTH);
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    private GridPane gridPane = new GridPane();
    private Font font = Font.loadFont(getClass().getResourceAsStream(
            "/fonts/script-screen/Script Screen.ttf"
    ), 15);

    public ScoreboardView(View view, Scoreboard scoreboard) {
        scoreboard.addObserver(this);
        this.scoreboard = scoreboard;
        borderPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(100, 38, 60, 38));
        gridPane.setHgap(30);
        gridPane.setVgap(10);


        Image image = new Image(getClass().getResourceAsStream("/spriteSheets/scoreboard_border.png")); // oppure getResource
        gc.setImageSmoothing(false);
        gc.drawImage(image, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        setAlignment(canvas, Pos.TOP_LEFT);

        Text scoreboardText = new Text("SCOREBOARD - Ordinamento: ");
        scoreboardText.setFont(Font.font(font.getFamily(), 18));
        scoreboardText.setFill(YELLOW);
        Text option = new Text(scoreboard.getOptions().get(scoreboard.getSelectedIndex()));
        option.setFont(Font.font(font.getFamily(), 15));
        option.setFill(WHITE);
        HBox topHBox = new HBox();
        topHBox.setAlignment(Pos.CENTER);
        topHBox.getChildren().addAll(scoreboardText, option);
        borderPane.setTop(topHBox);

        Text back = new Text("- Indietro: ");
        back.setFont(Font.font(font.getFamily(), 15));
        back.setFill(YELLOW);
        Text escape = new Text("ESCAPE ");
        escape.setFont(Font.font(font.getFamily(), 13));
        escape.setFill(WHITE);

        Text change = new Text("- Cambio ordinamento: ");
        change.setFont(Font.font(font.getFamily(), 15));
        change.setFill(YELLOW);
        Text arrows = new Text("LEFT/RIGHT ");
        arrows.setFont(Font.font(font.getFamily(), 13));
        arrows.setFill(WHITE);

        HBox commandsHBox = new HBox();
        commandsHBox.setAlignment(Pos.TOP_LEFT);
        commandsHBox.setPadding(new Insets(60, 38, 60, 38));
        commandsHBox.getChildren().addAll(back, escape, change, arrows);

        getChildren().addAll(canvas, gridPane, borderPane, commandsHBox);

        List<Scoreboard.ScoreEntry> entries = null;

        switch (scoreboard.getSelectedIndex()) {
            case 0 -> entries  = scoreboard.getNewestScores();
            case 1 -> entries = scoreboard.getScores();
            case 2 -> entries = scoreboard.getLowestScores();
            case 3 -> entries = scoreboard.getHighestScores();
        }

        for (int i = 0; i < entries.size() && i < 48; i++) {
            Text name = new Text(entries.get(i).getPlayerName());
            name.setFont(Font.font(font.getFamily(), 15));
            name.setFill(MAGENTA);

            Text score = new Text(entries.get(i).getScore() + " - ");
            score.setFont(Font.font(font.getFamily(), 15));
            score.setFill(WHITE);
            HBox hBox = new HBox();
            hBox.getChildren().addAll(score, name);
            gridPane.add(hBox, i / 16, i % 16 );
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        borderPane.getChildren().clear();
        gridPane.getChildren().clear();

        Text scoreboardText = new Text("SCOREBOARD - Ordinamento: ");
        scoreboardText.setFont(Font.font(font.getFamily(), 18));
        scoreboardText.setFill(YELLOW);
        Text option = new Text(scoreboard.getOptions().get(scoreboard.getSelectedIndex()));
        option.setFont(Font.font(font.getFamily(), 15));
        option.setFill(WHITE);
        HBox topHBox = new HBox();
        topHBox.getChildren().addAll(scoreboardText, option);
        borderPane.setTop(topHBox);

        List<Scoreboard.ScoreEntry> entries = null;

        switch (scoreboard.getSelectedIndex()) {
            case 0 -> entries  = scoreboard.getNewestScores();
            case 1 -> entries = scoreboard.getScores();
            case 2 -> entries = scoreboard.getLowestScores();
            case 3 -> entries = scoreboard.getHighestScores();
        }

        for (int i = 0; i < entries.size() && i < 48; i++) {
            Text name = new Text(entries.get(i).getPlayerName());
            name.setFont(Font.font(font.getFamily(), 15));
            name.setFill(MAGENTA);

            Text score = new Text(entries.get(i).getScore() + " - ");
            score.setFont(Font.font(font.getFamily(), 15));
            score.setFill(WHITE);
            HBox hBox = new HBox();

            hBox.getChildren().addAll(score, name);
            gridPane.add(hBox, i / 16, i % 16 );
        }
    }

}
