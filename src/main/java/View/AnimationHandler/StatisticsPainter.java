package View.AnimationHandler;

import Model.GameStatistics;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static config.GameConstants.LOGICAL_HEIGHT;
import static config.GameConstants.SCREEN_HEIGHT;
import static javafx.scene.paint.Color.MAGENTA;
import static javafx.scene.paint.Color.SALMON;

public class StatisticsPainter {
    private final GameStatistics statistics;
    private AnimationHandler animationHandler = new AnimationHandler(new Image(getClass().getResourceAsStream(
            "/spriteSheets/gameMenu.png"
    )));

    public StatisticsPainter(GameStatistics statistics) {
        this.statistics = statistics;

        Animation background = new Animation("background", false)
                .add(new Frame(new Rectangle2D(0, 0, 290, 68), 0, 0, 1));

        animationHandler.addAnimation(background);
    }

    public void draw(GraphicsContext gc, double scale, double offsetX, double offsetY) {
        //animationHandler.update(deltaTime);
        // made the rows too much tall in platforms number so the scale is messed up.
        // hardcoded, not a good solution, but for now a solution
        int y = LOGICAL_HEIGHT - 51 + (int)offsetY;

        animationHandler.render(
                gc,
                offsetX,
                y,
                scale);
        gc.setFill(MAGENTA);

        System.out.println("OffsetX Y: " + offsetX + "," + offsetY);
        // GraphicsContainer is not automatically scaled
        gc.fillText(
                "Pezzi di password trovati: " + statistics.getPswPiecesFound() + "/" + statistics.getTotalPswPieces(),
                (offsetX + 10) * scale,
                (offsetY + y + 14) * scale);

        gc.fillText(
                "Codici per disattivare i robot: " + statistics.getRobotsCodeTot(),
                (offsetX + 10) * scale,
                (offsetY + y + 20) * scale
        );

        gc.fillText(
                "Codici per resettare le piattaforme: " + statistics.getPlatformsCodeTot(),
                (offsetX + 10) * scale,
                (offsetY + y + 26) * scale
        );

        gc.fillText(
                statistics.getGameClock().toString(),
                (offsetX + animationHandler.getCurrentFrameWidth() - 30) * scale,
                (offsetY + y + 14) * scale
        );
        gc.setFill(SALMON);
    }
}
