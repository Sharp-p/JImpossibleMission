package Controller;

import Model.Scoreboard;
import View.ScoreboardView;
import View.View;
import javafx.beans.Observable;
import javafx.scene.input.KeyCode;

import java.util.List;

/**
 * Controls the scoreboard view
 */
public class ScoreboardController {
    private Scoreboard scoreboard;
    private View view;

    /**
     * Sets the key listeners inside the ScoreboardView
     * @param scoreboard
     * @param view
     */
    public ScoreboardController(Scoreboard scoreboard, View view) {
        this.scoreboard = scoreboard;
        this.view = view;

        view.getScoreboardView().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) view.showMenu();
            if (e.getCode() == KeyCode.RIGHT) {
                scoreboard.next();
            }
            else if (e.getCode() == KeyCode.LEFT) {
                scoreboard.previous();
            }
        });


    }
}
