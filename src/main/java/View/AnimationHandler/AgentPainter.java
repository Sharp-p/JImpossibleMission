package View.AnimationHandler;

import Model.Agent;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static config.GameConstants.RUN_FRAME_DURATION;
import static config.GameConstants.JUMP_FRAME_DURATION;

public class AgentPainter extends EntityPainter {
    public AgentPainter(Agent agent) {
        super(agent);

        Animation run = new Animation("run", true)
                .add(new Frame(new Rectangle2D(9, 76, 14, 29), 0, 0, RUN_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(27, 76, 17, 29), 0, 0, RUN_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(49, 75, 19, 30), 0, 0, RUN_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(73, 77, 22, 28), 0, 0, RUN_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(100, 76, 24, 29), 0, 0, RUN_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(129, 76, 22, 29), 0, 0, RUN_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(158, 76, 15, 29), 0, 0, RUN_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(184, 76, 13, 29), 0, 0, RUN_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(203, 77, 16, 29), 0, 0, RUN_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(224, 76, 19, 30), 0, 0, RUN_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(247, 78, 22, 28), 0, 0, RUN_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(276, 77, 24, 29), 0, 0, RUN_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(306, 77, 22, 29), 0, 0, RUN_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(338, 77, 15, 29), 0, 0, RUN_FRAME_DURATION));

        Animation idle = new Animation("idle", false)
                .add(new Frame(new Rectangle2D(11, 27, 12, 28), 0, 0, 1));

        Animation jump = new Animation("jump", false)
                .add(new Frame(new Rectangle2D(29, 33, 24, 22), 0, 0, JUMP_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(60, 30, 17, 21), 0, 0, JUMP_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(84, 24, 18, 19), 0, 0, JUMP_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(111, 23, 24, 12), 0, 0, JUMP_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(144, 18, 17, 15), 0, 0, JUMP_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(173, 14, 14, 17), 0, 0, JUMP_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(196, 14, 14, 15), 0, 0, JUMP_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(219, 17, 22, 13), 0, 0, JUMP_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(248, 18, 30, 14), 0, 0, JUMP_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(286, 18, 25, 21), 0, 0, JUMP_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(323, 20, 15, 31), 0, 0, JUMP_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(353, 25, 16, 30), 0, 0, 1));

        Animation falling = new Animation("falling", false)
                .add(new Frame(new Rectangle2D(353, 25, 16, 30), 0, 0, 1));

        setAnimationHandler(new AnimationHandler(
                new Image(getClass().getResourceAsStream(
                        "/spriteSheets/agent/Commodore64ImpossibleMissionAgent4125_gimp.png"))));


        getAnimationHandler().addAnimation(idle);
        getAnimationHandler().addAnimation(run);
        getAnimationHandler().addAnimation(jump);
        getAnimationHandler().addAnimation(falling);
    }

    public void draw(GraphicsContext gc, double dt, double scale) {
        //getAnimationHandler().update(dt);

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
//        System.out.println(getEntity().getSize().getFirst() +
//                " " + getEntity().getSize().getSecond());
    }
}
