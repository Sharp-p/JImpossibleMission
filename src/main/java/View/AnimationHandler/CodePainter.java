package View.AnimationHandler;

import Model.Code;
import Model.FurniturePiece;
import View.GameView;
import javafx.scene.canvas.GraphicsContext;

import static Model.StillPlatform.STILL_PLATFORM_HEIGHT;
import static javafx.scene.paint.Color.*;

public class CodePainter {
    private final double paintTime = 3;
    private final FurniturePiece furniturePiece;
    private final int textMaxWidth = 150
            ;

    private double currentTime = 0.0;

    public CodePainter(FurniturePiece furniturePiece) {
        this.furniturePiece = furniturePiece;
    }

    public void draw(GraphicsContext gc, double deltaTime, double scale) {
        // if the furniturePiece has been searched (not Visible) in
        // the last paintTime (currentTime <= paintTime)
        if (!(furniturePiece.isVisible()) && currentTime <= paintTime) {
            currentTime += deltaTime;
            String toPrint = null;
            double midFurniture = furniturePiece.getPosition().getFirst()
                    + furniturePiece.getSize().getFirst() / 2;
            double y = furniturePiece.getPosition().getSecond()
                    + furniturePiece.getSize().getSecond() - 40;
            double x = (midFurniture - textMaxWidth / 2.0);
            if (x < 0) x = 0;

            switch (furniturePiece.getCode().getType()) {
                case ROBOT -> toPrint = "Codice per disattivare i robot trovato!!";
                case PLATFORMS -> toPrint = "Codice per riavviare le piattaforme trovato!!";
                // TODO: totale psw, stampare psw rimanenti
                case PSW_PIECE -> toPrint = "Hai trovato un pezzo di password!!";
            }

            gc.setFill(GREEN);
            if (toPrint != null) gc.fillText(
                    "Codice per disattivare i robot trovato!!",
                    x * scale,
                    y * scale);
            gc.setFill(SALMON);
        }
    }
}
