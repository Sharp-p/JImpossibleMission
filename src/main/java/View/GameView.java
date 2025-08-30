package View;

import Model.GameModel;
import Model.Platform;
import Model.Robot;
import View.AnimationHandler.AgentPainter;
import View.AnimationHandler.PlatformPainter;
import View.AnimationHandler.StillRobotPainter;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static config.GameConstants.*;
import static javafx.scene.paint.Color.*;

public class GameView extends Pane implements Observer {
    private final View view;
    private final Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();

    private Scale scale;
    private AgentPainter agentPainter;
    private List<PlatformPainter> platformPainters = new ArrayList<>();
    private List<StillRobotPainter> robotPainters = new ArrayList<>();
    private GameModel gameModel;

    public GameView(View view) {
        this.view = view;

        // TODO: scrivere che si esce con esc
        getChildren().addAll(canvas);

        // to not mess up the pixel art
        gc.setImageSmoothing(false);
        gc.setFill(SALMON);
    }

    public void clearCanvas() {
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @Override
    public void update(Observable o, Object arg) {
        double deltaTime = (double) arg;

        for  (PlatformPainter platformPainter : platformPainters) {
            platformPainter.draw(gc, deltaTime, scale.getX());
        }

        for (StillRobotPainter robotPainter : robotPainters) {
            robotPainter.draw(gc, deltaTime, scale.getX());
        }
        agentPainter.draw(gc, deltaTime, scale.getX());
    }

    /**
     * What is creates in the constructor of the GameModel will be
     * initialized in the GameView in this method.
     * It can act as a refresh when some objects are destroyed in the game model.
     * @param gameModel The GameModel that the GameView will represent
     */
    public void setGameModel(GameModel gameModel) {
        this.gameModel = gameModel;
        gameModel.addObserver(this);

        // TODO: da resettare penso pure la view ogni volta che rientro
        //  nel menu altrimenti danni

        // creates a painter for each platform
        for (Platform platform : gameModel.getPlatforms()) {
            platformPainters.add(new PlatformPainter(platform));
        }

        for (Robot robot : gameModel.getRobots()) {
            robotPainters.add(new StillRobotPainter(robot));
        }
        // creates a painter for the agent
        agentPainter = new AgentPainter(gameModel.getAgent());
    }

    public void setScale(Scale scale) {
        this.scale = scale;
    }

    public AgentPainter getAgentPainter() { return agentPainter; }

    public GraphicsContext getGraphicsContext() { return gc; }

    public List<PlatformPainter> getPlatformPainters() { return platformPainters; }
}
