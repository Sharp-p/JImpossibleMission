package View;

import Model.GameModel;
import Model.Platform;
import Model.StillMovement;
import Utilities.Tuple;
import View.AnimationHandler.AgentPainter;
import View.AnimationHandler.PlatformPainter;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static config.GameConstants.*;

public class GameView extends Pane implements Observer {
    private final View view;
    private final Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();

    private Scale scale;
    private AgentPainter agentPainter;
    private List<PlatformPainter> platforms;
    private GameModel gameModel;

    public GameView(View view) {
        this.view = view;

        platforms = new ArrayList<>();
        // TODO: platforms si aggiorna tramite OO in update quando rileva una
        //  nuova platform, nuove platform create nel gameController
        platforms.add(new PlatformPainter(
                new Platform(new Tuple<>(100.0, 100.0), new StillMovement(), 0.0)));


        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> { view.showMenu(); });

        getChildren().addAll(canvas, backBtn);

        // to not mess up the pixel art
        gc.setImageSmoothing(false);
    }

    public void clearCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @Override
    public void update(Observable o, Object arg) {
        double deltaTime = (double) arg;

        platforms.get(0).draw(gc, deltaTime, scale.getX());
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

    public List<PlatformPainter> getPlatforms() { return platforms; }
}
