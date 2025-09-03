package View.AnimationHandler;

import Model.GameStatistics;
import Model.Terminal;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Font;

import static config.GameConstants.LOGICAL_HEIGHT;
import static javafx.scene.paint.Color.*;
import static javafx.scene.paint.Color.CYAN;

public class TerminalPainter {
    private final Terminal terminal;
    private AnimationHandler animationHandler = new AnimationHandler(new Image(getClass().getResourceAsStream(
            "/spriteSheets/terminal.png"
    )));

    public TerminalPainter(Terminal terminal) {
        this.terminal = terminal;

        Animation terminalPixelArt = new Animation("terminal", false)
                .add(new Frame(new Rectangle2D(0, 0, 290, 203), 0, 0, 1));

        animationHandler.addAnimation(terminalPixelArt);
    }

    public void draw(GraphicsContext gc, double scale, double offsetX, double offsetY) {
        //animationHandler.update(deltaTime);
        // made the rows too much tall in platforms number so the scale is messed up.
        // hardcoded, not a good solution, but for now a solution
        animationHandler.render(
                gc,
                offsetX,
                offsetY,
                scale);

        // GraphicsContainer is not automatically scaled
        int selectedIndex = terminal.getSelectedIndex();
        String selected = ">";

        gc.setFill(MAGENTA);
        gc.setFont(Font.loadFont(getClass().getResourceAsStream(
                "/fonts/script-screen/Script Screen.ttf"
        ), 17));

        gc.fillText(
                "=== TERMINALE DELLA SICUREZZA ===",
                (offsetX + animationHandler.getCurrentFrameWidth() / 6) * scale,
                (offsetY + animationHandler.getCurrentFrameHeight() / 7) * scale);

        gc.fillText(
                "SELEZIONA UN'OPERAZIONE",
                (offsetX + (animationHandler.getCurrentFrameWidth() / 64) * 17) * scale,
                (offsetY + (animationHandler.getCurrentFrameHeight() / 14) * 3) * scale);


        if (selectedIndex == 0) {
            gc.setFill(CYAN);
            gc.fillText(
                    selected,
                    (offsetX + animationHandler.getCurrentFrameWidth() / 6) * scale,
                    (offsetY + (animationHandler.getCurrentFrameHeight() / 14) * 5) * scale);
        }
        gc.fillText(terminal.getOptions().get(0),
                (offsetX + animationHandler.getCurrentFrameWidth() / 5) * scale,
                (offsetY + (animationHandler.getCurrentFrameHeight() / 14) * 5) * scale,
                185 * scale);

        gc.setFill(MAGENTA);

        if (selectedIndex == 1) {
            gc.setFill(CYAN);
            gc.fillText(
                    selected,
                    (offsetX + animationHandler.getCurrentFrameWidth() / 6) * scale,
                    (offsetY + (animationHandler.getCurrentFrameHeight() / 14) * 6) * scale);
        }
        gc.fillText(terminal.getOptions().get(1),
                (offsetX + animationHandler.getCurrentFrameWidth() / 5) * scale,
                (offsetY + (animationHandler.getCurrentFrameHeight() / 14) * 6) * scale,
                185 * scale);

        gc.setFill(MAGENTA);
        if (selectedIndex == 2) {
            gc.setFill(CYAN);
            gc.fillText(
                    selected,
                    (offsetX + animationHandler.getCurrentFrameWidth() / 6) * scale,
                    (offsetY + (animationHandler.getCurrentFrameHeight() / 14) * 7) * scale
            );
        }
        gc.fillText(terminal.getOptions().get(2),
                (offsetX + animationHandler.getCurrentFrameWidth() / 5) * scale,
                (offsetY + (animationHandler.getCurrentFrameHeight() / 14) * 7) * scale,
                185 * scale);

        gc.setFont(Font.loadFont(getClass().getResourceAsStream(
                "/fonts/script-screen/Script Screen.ttf"
        ), 10));
        gc.setFill(SALMON);
    }
}
