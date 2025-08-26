package View.AnimationHandler;

import Model.Entity;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static config.GameConstants.RUN_FRAME_DURATION;

public class AgentPainter extends EntityPainter {
    public AgentPainter(Entity entity) {
        super(entity);
        // TODO: creare animazioni
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
        Animation idleL = new Animation("idleL", false)
                .add(new Frame(new Rectangle2D(0, 0, 12, 28), 0, 0, 1));
        // TODO: inzializzare animationhandler allo sheet dell'agente
        setAnimationHandler(new AnimationHandler(
                new Image(getClass().getResourceAsStream(
                        "/spriteSheets/agent/Commodore64ImpossibleMissionAgent4125.gif"))));

        // TODO: aggiungere animazioni all'handler
        getAnimationHandler().addAnimation(idle);
        // getAnimationHandler().addAnimation(idleL);
        getAnimationHandler().addAnimation(run);

    }

    public void draw(GraphicsContext gc, double dt, double scale) {
        getAnimationHandler().update(dt);

        switch (getEntity().getDirection()) {
            case RIGHT -> getAnimationHandler().render(gc, getEntity().getPosition().getFirst(),
                    getEntity().getPosition().getSecond(), scale);
            case LEFT -> {
                gc.save();
                double frameW = getAnimationHandler().getCurrentFrameWidth() * scale;
                double x = getEntity().getPosition().getFirst();
                double y = getEntity().getPosition().getSecond();

                gc.translate(x + frameW, y);
                gc.scale(-1, 1);
                getAnimationHandler().render(gc, 0,0, scale);
                gc.restore();
            }
        }
    }
}
