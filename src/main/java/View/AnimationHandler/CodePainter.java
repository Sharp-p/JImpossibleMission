package View.AnimationHandler;

import Model.Code;
import Model.FurniturePiece;
import View.GameView;
import javafx.scene.canvas.GraphicsContext;

import static Model.StillPlatform.STILL_PLATFORM_HEIGHT;
import static config.GameConstants.LOGICAL_WIDTH;
import static javafx.scene.paint.Color.*;

public class CodePainter {
    private final double paintTime = 3;
    private final FurniturePiece furniturePiece;
    private final int textMaxWidth = 150;

    private double currentTime = 0.0;

    public CodePainter(FurniturePiece furniturePiece) {
        this.furniturePiece = furniturePiece;
    }

    public void draw(GraphicsContext gc, double deltaTime, double scale, int pswPiecesFound, int totalPswPieces) {
        // if the furniturePiece has been searched (not Visible) in
        // the last paintTime (currentTime <= paintTime)
        if (!(furniturePiece.isVisible()) && currentTime <= paintTime) {
            //System.out.println(furniturePiece);

            currentTime += deltaTime;
            String toPrint = null;
            double midFurniture = furniturePiece.getPosition().getFirst()
                    + furniturePiece.getSize().getFirst() / 2;
            double y = furniturePiece.getPosition().getSecond()
                    + furniturePiece.getSize().getSecond() - 40;
            double x = (midFurniture - textMaxWidth / 2.0);
            double areaX = (int)(furniturePiece.getPosition().getFirst() / LOGICAL_WIDTH) * LOGICAL_WIDTH;
            if (x < areaX) x = areaX;
            else if (x + textMaxWidth > (areaX + LOGICAL_WIDTH)) x = areaX + LOGICAL_WIDTH - textMaxWidth;


            switch (furniturePiece.getCode().getType()) {
                case ROBOT -> toPrint = "Codice per disattivare i robot trovato!!";
                case PLATFORMS -> toPrint = "Codice per riavviare le piattaforme trovato!!";
                case PSW_PIECE -> {
                    if (pswPiecesFound == totalPswPieces)
                        toPrint = "Complimenti! Hai trovato tutti i pezzi di password!! " + pswPiecesFound + "/" + totalPswPieces;
                    else
                        toPrint = "Hai trovato un pezzo di password!! " + pswPiecesFound + "/" + totalPswPieces;
                }
            }
            //System.out.println(furniturePiece.getCode().getType().toString());

            gc.setFill(GREEN);
            if (toPrint != null) gc.fillText(
                    toPrint,
                    x * scale,
                    y * scale);
            gc.setFill(SALMON);
        }
    }
}
