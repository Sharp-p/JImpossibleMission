package View.AnimationHandler;

import Model.Robot;
import Model.StillRobot;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static config.GameConstants.TURNING_FRAME_DURATION;

public class StillRobotPainter extends EntityPainter {
    public StillRobotPainter(Robot robot) {
        super(robot);

        // the animation is identical for everything
        // that is not the turning animation
        Animation notTurning = new Animation("notTurning", true)
                .add(new Frame(new Rectangle2D(4, 14, 16, 23), 0, 0, 1));

        Animation turning = new Animation("turning", false)
                .add(new Frame(new Rectangle2D(28, 14, 16, 23), 0, 0, TURNING_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(52, 14, 16, 23), 0, 0, TURNING_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(76, 14,  16, 23), 0, 0, TURNING_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(99, 14,  16, 23), 0, 0, TURNING_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(123, 14,  16, 23), 0, 0, TURNING_FRAME_DURATION));
//                .add(new Frame(new Rectangle2D(147,  14,  16, 23), 0, 0, TURNING_FRAME_DURATION))
                //add(new Frame(new Rectangle2D(4, 14, 16, 23), 0, 0, TURNING_FRAME_DURATION))


        setAnimationHandler(new AnimationHandler(
                new Image(getClass().getResourceAsStream(
                        "/spriteSheets/enemies/robot/robot_sprite_sheet_yellow.png"))));

        getAnimationHandler().addAnimation(notTurning);
        getAnimationHandler().addAnimation(turning);
    }

    @Override
    public void draw(GraphicsContext gc, double dt, double scale) {
        // TODO: gestire il caso IsTurning
        StillRobot robot = (StillRobot) getEntity();
        if(robot.hasTurned()){
            getAnimationHandler().play("turning");
            robot.setHasTurned(false);
        }
        else if (!robot.isTurning()) {
            getAnimationHandler().play("notTurning");
        }

        getAnimationHandler().update(dt);
        // the only animation that ends is the turning animation so if
        // isPlaying is false the robot has completely turned
        if (!getAnimationHandler().isPlaying()) {
            robot.setTurning(false);
        }


        switch (getEntity().getDirection()) {
            case LEFT -> {
                if (getAnimationHandler().getCurrentAnimation().getName().equals("turning")) {
                    getAnimationHandler().render(
                            gc, getEntity().getPosition().getFirst(),
                            getEntity().getPosition().getSecond(), scale);
                    break;
                }
                drawInverted(gc, scale);
            }
            default -> {
                if (getAnimationHandler().getCurrentAnimation().getName().equals("notTurning")) {
                    getAnimationHandler().render(
                            gc, getEntity().getPosition().getFirst(),
                            getEntity().getPosition().getSecond(), scale);
                    break;
                }
                drawInverted(gc, scale);
            }
        }
        updateEntitySize();
    }
}
