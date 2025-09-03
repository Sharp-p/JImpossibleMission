package View;

import Model.*;
import View.AnimationHandler.Animation;
import View.AnimationHandler.AnimationHandler;
import View.AnimationHandler.Frame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.List;

import static config.GameConstants.*;
import static config.GameVariables.scale;
import static javafx.scene.paint.Color.*;

public class EndGameView extends StackPane {
    private final Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();
    private final Scoreboard scoreboard = new Scoreboard("src/main/resources/scores.csv");
    private final AnimationHandler animationHandler;
    private GridPane grid;
    private BorderPane borderPane;
    private Font font = Font.loadFont(getClass().getResourceAsStream(
            "/fonts/script-screen/Script Screen.ttf"
    ), 10);
    private final EndGame endGame;
    private final GameStatistics statistics;

    public EndGameView(EndGame endGame, GameStatistics statistics, double width, double height) {
        this.endGame = endGame;
        this.statistics = statistics;

        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20, 0, 30, 0));


        gc.setImageSmoothing(false);
        setPrefSize(width, height);

        grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(18);
        grid.setVgap(10);
        grid.setPadding(new Insets(440, 35, 0, 35));

        getChildren().addAll(canvas, grid, borderPane);

        Animation endRoom = new Animation("endRoom", false)
                .add(new Frame(new Rectangle2D(0, 0, 290, 203), 0, 0, 1));

        Animation scoreboard = new Animation("scoreboard", false)
                .add(new Frame(new Rectangle2D(0, 213, 290, 203), 0, 0, 1));

        animationHandler = new AnimationHandler(
                new Image(getClass().getResourceAsStream(
                        "/spriteSheets/end_screens.png"
                ))
        );

        animationHandler.addAnimation(endRoom);
        animationHandler.addAnimation(scoreboard);
    }

    public void draw(double deltaTime, FurniturePiece endRoom, View view) {
        switch (endGame.getStatus()) {
            case END_ROOM -> {
                view.showEndGame(this);
                // separated from viewport so no grid offset
                animationHandler.render(
                        gc,
                        0,
                        0,
                        SCALE_FACTOR
                );


                Text enter = new Text("Premi ENTER per andare avanti.");
                enter.setFill(CYAN);
                enter.setFont(font);
                enter.setTextAlignment(TextAlignment.CENTER);

                Text victory = new Text("VITTORIA!!");
                victory.setFill(ORANGE);
                victory.setFont(Font.font(font.getFamily(), 20));
                victory.setTextAlignment(TextAlignment.CENTER);

                borderPane.setTop(victory);
                borderPane.setBottom(enter);
                BorderPane.setAlignment(victory, Pos.TOP_CENTER);
                BorderPane.setAlignment(enter, Pos.BOTTOM_CENTER);

                setOnKeyPressed(e -> {
                    if (e.getCode() == KeyCode.ENTER) {
                        endGame.setStatus(EndGameStatus.SCOREBOARD);
                        draw(deltaTime, endRoom, view);
                    }
                });
            }
            case SCOREBOARD -> {
                borderPane.getChildren().clear();

                Text total = new Text("PUNTEGGIO TOTALE: ");
                total.setFont(Font.font(font.getFamily(), 15));
                total.setFill(YELLOW);
                Text points = new Text( "" + (int)statistics.getScore());
                points.setFont(Font.font(font.getFamily(), 15));
                points.setFill(WHITE);
                GridPane pointsGrid = new GridPane();
                pointsGrid.setPadding(new Insets(65, 35, 0, 45));
                pointsGrid.setAlignment(Pos.TOP_LEFT);
                pointsGrid.add(total, 0, 0);
                pointsGrid.add(points, 1, 0);

                Text remaining = new Text("SECONDI RIMANENTI: ");
                remaining.setFont(Font.font(font.getFamily(), 15));
                remaining.setFill(YELLOW);
                Text seconds = new Text("" + statistics.getSeconds());
                seconds.setFont(Font.font(font.getFamily(), 15));
                seconds.setFill(WHITE);
                GridPane secondsGrid = new GridPane();
                secondsGrid.setPadding(new Insets(150, 35, 0, 45));
                secondsGrid.setAlignment(Pos.TOP_LEFT);
                secondsGrid.add(remaining, 0, 0);
                secondsGrid.add(seconds, 1, 0);

                Text mission = new Text("MISSIONE ");
                mission.setFont(Font.font(font.getFamily(), 15));
                mission.setFill(YELLOW);
                Text result = new Text(statistics.getWon() ? "COMPLETATA" : "FALLITA");
                result.setFont(Font.font(font.getFamily(), 15));
                result.setFill(statistics.getWon() ? GREEN : RED);
                GridPane resultGrid = new GridPane();
                resultGrid.setPadding(new Insets(230, 45, 0, 45));
                resultGrid.setAlignment(Pos.TOP_LEFT);
                resultGrid.add(mission, 0, 0);
                resultGrid.add(result, 1, 0);

                // TODO: includere il nome
                Text thanks = new Text("GRAZIE PER AVER GIOCATO!");
                thanks.setFont(Font.font(font.getFamily(), 25));
                thanks.setFill(YELLOW);

                HBox thanksBox = new HBox();
                thanksBox.setAlignment(Pos.TOP_CENTER);
                thanksBox.setPadding(new Insets(310
                        , 35, 0, 35));
                thanksBox.getChildren().add(thanks);

                getChildren().addAll(pointsGrid, secondsGrid, resultGrid,  thanksBox);
                animationHandler.play("scoreboard");
                animationHandler.render(
                        gc,
                        0,
                        0,
                        SCALE_FACTOR
                );

                view.showEndGame(this);
                // TODO: usare nome player
                scoreboard.addScore("Player1", statistics.getFinalScore());

                List<Scoreboard.ScoreEntry> entries = scoreboard.getHighestScores();

                // TODO: adattare con grid annidate magari
                for (int i = 0; i < entries.size() && i < 12; i++) {
                    Text name = new Text(entries.get(i).getPlayerName());
                    name.setFont(Font.font(font.getFamily(), 15));
                    name.setFill(MAGENTA);

                    Text score = new Text(entries.get(i).getScore() + " - ");
                    score.setFont(Font.font(font.getFamily(), 15));
                    score.setFill(WHITE);
                    HBox hBox = new HBox();

                    hBox.getChildren().addAll(score, name);
                    grid.add(hBox, i / 4, i % 4 );
                }
                setOnKeyPressed(e -> {
                    if (e.getCode() == KeyCode.ENTER) {
                        view.showMenu();
                    }
                });

            }
        }
    }
}
