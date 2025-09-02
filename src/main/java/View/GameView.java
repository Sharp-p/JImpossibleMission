package View;

import Model.*;
import View.AnimationHandler.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static Model.CodeType.NONE;
import static Model.CodeType.PSW_PIECE;
import static config.GameConstants.*;
import static javafx.scene.paint.Color.*;

public class GameView extends Pane implements Observer {
    private final View view;
    private final Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();
    private final double PROGRESSBAR_DIM = 70.0;

    private Scale scale;
    private AgentPainter agentPainter;
    private List<PlatformPainter> platformPainters = new ArrayList<>();
    private List<GroundRobotPainter> groundRobotPainters = new ArrayList<>();
    private List<ProjectilePainter> projectilePainters = new ArrayList<>();
    private List<FurniturePainter> furniturePainters = new ArrayList<>();
    private List<CodePainter> codePainters = new ArrayList<>();
    private StatisticsPainter statisticsPainter;
    private GameModel gameModel;

    public GameView(View view) {
        this.view = view;

        // TODO: scrivere che si esce con esc
        getChildren().addAll(canvas);

        // to not mess up the pixel art
        gc.setImageSmoothing(false);
        gc.setFill(SALMON);
        gc.setFont(Font.loadFont(getClass().getResourceAsStream(
                "/fonts/script-screen/Script Screen.ttf"
        ), 10));
        gc.setStroke(GREEN);
        gc.setLineWidth(2);
    }

    public void clearCanvas() {
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @Override
    public void update(Observable o, Object arg) {
        double deltaTime = (double) arg;

        gc.save();

        gc.translate(-gameModel.getCameraX() * scale.getX(), -gameModel.getCameraY() * scale.getX());

        // i want the projectiles to stay behind
        for (ProjectilePainter projectilePainter : projectilePainters) {
            projectilePainter.draw(gc, deltaTime, scale.getX());
        }

        for  (PlatformPainter platformPainter : platformPainters) {
            platformPainter.draw(gc, deltaTime, scale.getX());
        }

        for (FurniturePainter furniturePainter: furniturePainters) {
            furniturePainter.draw(gc, deltaTime, scale.getX());
        }

        for (GroundRobotPainter groundRobotPainter : groundRobotPainters) {
            groundRobotPainter.draw(gc, deltaTime, scale.getX());
        }


        if (agentPainter.getAnimationHandler().getCurrentAnimation().getName()
                .equals("searching")) {
            drawSearchBar();
        }

        for (CodePainter codePainter: codePainters) {
            codePainter.draw(gc, deltaTime, scale.getX(), gameModel.getPswPiecesFound(), gameModel.getTotalPswPieces());
        }

        agentPainter.draw(gc, deltaTime, scale.getX());

        if (gameModel.isShowingStatistics()) statisticsPainter.draw(gc, scale.getX(), gameModel.getCameraX(), gameModel.getCameraY());

        gc.restore();

//        System.out.println("Altezza canvas: " + canvas.getHeight());
//        System.out.println("Larghezza canvas: " + canvas.getWidth());
//
//        System.out.println("Altezza schermo: " + getHeight());
//        System.out.println("Larghezza schermo: " + getWidth());
    }

    private void drawSearchBar() {
        List<FurniturePiece> furniture = gameModel.getFurniture();

        for (FurniturePiece furniturePiece : furniture) {
            if (furniturePiece.isBeingSearched()) {


                double fraction = furniturePiece.getSearchTime() /
                        FurniturePiece.MAX_SEARCH_TIME;
                double actualWidth = PROGRESSBAR_DIM * fraction;

                double midFurniture = furniturePiece.getPosition().getFirst() + furniturePiece.getSize().getFirst() / 2;

                // scaled dim and coordiantes (che pane is already scaled,
                // but the canvas and the gc use their own coordinate
                // system that needs tu be manually scaled)
                actualWidth *= scale.getX();
                midFurniture *= scale.getX();
                double sY = gameModel.getAgent().getPosition().getSecond() * scale.getX();


                gc.fillRect(
                        midFurniture - actualWidth / 2,
                         - 14,
                        actualWidth,
                        4
                );
                gc.strokeRect(midFurniture - actualWidth / 2,
                        sY - 14,
                        actualWidth,
                        4);
            }
        }
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

        // create a painter for each enemy
        for (Enemy enemy: gameModel.getEnemies()) {
            if (enemy instanceof Robot)
                groundRobotPainters.add(new GroundRobotPainter((Robot)enemy));
            else projectilePainters.add(new ProjectilePainter((Projectile)enemy));

        }

        // creates a painter for each furniture piece
        for (FurniturePiece furniturePiece : gameModel.getFurniture()) {
            furniturePainters.add(new FurniturePainter(furniturePiece));

            // sets ups the counter and checks that no terminal or end room
            // has a code assigned
            if (furniturePiece.getType() != FurnitureType.TERMINAL
                    && furniturePiece.getType() != FurnitureType.END_ROOM) {
                codePainters.add(new CodePainter(furniturePiece));

                // checks if the furniturePiece hides a psw_piece,
                // if so, it adds to the counter
                if (furniturePiece.getCode().getType() == PSW_PIECE) {
                    gameModel.addTotalPswPieces();
                }
            }
            else furniturePiece.setCode(new Code(NONE));
            System.out.println(furniturePiece);
        }


        statisticsPainter = new StatisticsPainter(gameModel.getStatistics());

        System.out.println(gameModel.getTotalPswPieces());

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
