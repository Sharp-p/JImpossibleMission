package View;

import Model.*;
import View.AnimationHandler.Animation;
import View.AnimationHandler.AnimationHandler;
import View.AnimationHandler.Frame;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;

import static config.GameConstants.*;
import static config.GameVariables.scale;
import static javafx.scene.paint.Color.*;

public class EndGameView extends StackPane {
    private final Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();
    private final Scoreboard scoreboard = new Scoreboard("scores.csv");
    private final AnimationHandler animationHandler;
    private GridPane grid;
    private final EndGame endGame;
    private final GameStatistics statistics;

    public EndGameView(EndGame endGame, GameStatistics statistics, double width, double height) {
        this.endGame = endGame;
        this.statistics = statistics;


        gc.setImageSmoothing(false);
        setPrefSize(width, height);

        grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        getChildren().addAll(canvas, grid);

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
                enter.setFont(Font.loadFont(getClass().getResourceAsStream(
                        "/fonts/script-screen/Script Screen.ttf"
                ), 35));
                grid.add(enter, 0, 0, 1, 1);

                setOnKeyPressed(e -> {
                    if (e.getCode() == KeyCode.ENTER) {
                        endGame.setStatus(EndGameStatus.SCOREBOARD);
                        draw(deltaTime, endRoom, view);
                    }
                });
            }
            case SCOREBOARD -> {
                grid.getChildren().clear();

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

                List<Scoreboard.ScoreEntry> entries = scoreboard.getScores();

                // TODO: adattare con grid annidate magari
                for (int i = 0; i < entries.size() && i < 6; i++) {
                    Text entry = new Text(entries.get(i).toString());
                    entry.setFont(Font.loadFont(getClass().getResourceAsStream(
                            "/fonts/script-screen/Script Screen.ttf"
                    ), 15));
                    entry.setFill(CYAN);

                    grid.add(entry, i % 6, i / 6 );
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
