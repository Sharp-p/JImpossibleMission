package View.AnimationHandler;

import Model.GameMenuModel;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Font;

import static config.GameConstants.LOGICAL_HEIGHT;
import static javafx.scene.paint.Color.*;

public class GameMenuPainter {
    private final GameMenuModel gameMenuModel;
    private AnimationHandler animationHandler = new AnimationHandler(new Image(getClass().getResourceAsStream(
            "/spriteSheets/gameMenu.png"
    )));

    public GameMenuPainter(GameMenuModel gameMenuModel) {
        this.gameMenuModel = gameMenuModel;

        Animation background = new Animation("background", false)
                .add(new Frame(new Rectangle2D(0, 0, 290, 68), 0, 0, 1));

        animationHandler.addAnimation(background);
    }

    public void draw(GraphicsContext gc, double scale, double offsetX, double offsetY) {
        //animationHandler.update(deltaTime);
        // made the rows too much tall in platforms number so the scale is messed up.
        // hardcoded, not a good solution, but for now a solution
        int y = LOGICAL_HEIGHT - 51 + (int) offsetY;

        animationHandler.render(
                gc,
                offsetX,
                y,
                scale);


        // GraphicsContainer is not automatically scaled
        int selectedIndex = gameMenuModel.getSelectedIndex();
        String selected = ">";

        gc.setFill(MAGENTA);
        gc.setFont(Font.loadFont(getClass().getResourceAsStream(
                "/fonts/script-screen/Script Screen.ttf"
        ), 25));
        if (selectedIndex == 0) {
            gc.setFill(CYAN);
            gc.fillText(
                    selected,
                    (offsetX + animationHandler.getCurrentFrameWidth() / 8) * scale,
                    (y + animationHandler.getCurrentFrameHeight() / 2) * scale);
        }
        gc.fillText(gameMenuModel.getOptions().get(0),
                (offsetX + (animationHandler.getCurrentFrameWidth() / 16) * 3) * scale,
                (y + animationHandler.getCurrentFrameHeight() / 2) * scale);

        gc.setFill(MAGENTA);

        if (selectedIndex == 1) {
            gc.setFill(CYAN);
            gc.fillText(
                    selected,
                    (offsetX + (animationHandler.getCurrentFrameWidth() / 16) * 9) * scale,
                    (y + animationHandler.getCurrentFrameHeight() / 2) * scale);
        }
        gc.fillText(gameMenuModel.getOptions().get(1),
                (offsetX + (animationHandler.getCurrentFrameWidth() / 8) * 5) * scale,
                (y + animationHandler.getCurrentFrameHeight() / 2) * scale);

        gc.setFont(Font.loadFont(getClass().getResourceAsStream(
                "/fonts/script-screen/Script Screen.ttf"
        ), 10));
        gc.setFill(SALMON);
    }
}
