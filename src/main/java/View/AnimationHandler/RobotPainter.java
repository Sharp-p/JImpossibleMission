package View.AnimationHandler;

import Model.Robot;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class RobotPainter extends EntityPainter {
    public RobotPainter(Robot robot) {
        super(robot);

        // the animation is identical for everything
        // that is not the turning animation
        Animation notTurning = new Animation("notTurning", false)
                .add(new Frame(new Rectangle2D(4, 14, 16, 23), 0, 0, 1));

        Animation turning = new Animation("turning", false)
                .add(new Frame(new Rectangle2D(4, 14, 16, 23), 0, 0, 0.2))
                .add(new Frame(new Rectangle2D(27, 14, 16, 23), 0, 0, 0.2))
                .add(new Frame(new Rectangle2D(51, 14, 16, 23), 0, 0, 0.2))
                .add(new Frame(new Rectangle2D(75, 14,  16, 23), 0, 0, 0.2));

        setAnimationHandler(new AnimationHandler(
                new Image(getClass().getResourceAsStream(
                        "/spriteSheets/enemies/robot/robot_sprite_sheet_yellow.png"))));

        getAnimationHandler().addAnimation(notTurning);
        getAnimationHandler().addAnimation(turning);
    }

    public void draw(GraphicsContext gc, double dt, double scale) {
        // TODO: gestire il caso IsTurning
        switch (getEntity().getDirection()) {
            case LEFT -> {
                gc.save();
                // the image starts drawing from the upper left corner so it
                // needs an offset when mirroring the canvas (and coordinates)
                double frameW = getAnimationHandler().getCurrentFrameWidth();

                double x = getEntity().getPosition().getFirst();
                double y = getEntity().getPosition().getSecond();

                gc.scale(-1, 1);

                getAnimationHandler().render(gc, -(x + frameW), y, scale);
                gc.restore();
            }
            default -> getAnimationHandler().render(
                    gc, getEntity().getPosition().getFirst(),
                    getEntity().getPosition().getSecond(), scale);
        }
        updateEntitySize();
    }
}
