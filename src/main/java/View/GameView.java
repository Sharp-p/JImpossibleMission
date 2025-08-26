package View;

import Model.GameModel;
import View.AnimationHandler.AgentPainter;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Observable;
import java.util.Observer;

import static config.GameConstants.*;

public class GameView extends Pane implements Observer {
    private final View view;
    private final Canvas canvas = new Canvas(LOGICAL_WIDTH, LOGICAL_HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();

    private AgentPainter agentPainter;
    private GameModel gameModel;

    public GameView(View view) {
        this.view = view;

        Button backBtn = new Button("Back");
        getChildren().addAll(canvas, backBtn);

        // to not mess up the pixel art
        gc.setImageSmoothing(false);


        backBtn.setOnAction(e -> { view.showMenu(); });
    }

    public void clearCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @Override
    public void update(Observable o, Object arg) {
        double deltaTime = (double) arg;

        agentPainter.draw(gc, deltaTime, getScaleX());
    }

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
        gameModel.addObserver(this);

        agentPainter = new AgentPainter(gameModel.getAgent());
    }

    public AgentPainter getAgentPainter() { return agentPainter; }

    public GraphicsContext getGraphicsContext() { return gc; }
}
