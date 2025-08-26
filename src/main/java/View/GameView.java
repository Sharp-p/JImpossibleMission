package View;

import Model.GameModel;
import View.AnimationHandler.AgentPainter;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Toggle;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;

import java.util.Observable;
import java.util.Observer;

import static config.GameConstants.*;

public class GameView extends Pane implements Observer {
    private final View view;
    private final Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();

    private Scale scale;
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

        // System.out.println("Scale: " + scale.getX());
        agentPainter.draw(gc, deltaTime, scale.getX());
    }

    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
        gameModel.addObserver(this);

        agentPainter = new AgentPainter(gameModel.getAgent());
    }

    public void setScale(Scale scale) {
        this.scale = scale;
    }

    public AgentPainter getAgentPainter() { return agentPainter; }

    public GraphicsContext getGraphicsContext() { return gc; }
}
