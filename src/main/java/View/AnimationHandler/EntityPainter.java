package View.AnimationHandler;

import Model.Entity;
import Utilities.Tuple;
import javafx.scene.canvas.GraphicsContext;

public abstract class EntityPainter {
    private AnimationHandler animationHandler;
    private Entity entity;

    protected EntityPainter(Entity entity) {
        this.entity = entity;
    }

    protected abstract void draw(GraphicsContext gc, double dt, double scale);

    protected void drawInverted(GraphicsContext gc, double scale) {
        gc.save();
        // the image starts drawing from the upper left corner so it
        // needs an offset when mirroring the canvas (and coordinates)
        double frameW = getAnimationHandler().getCurrentFrameWidth();

        double x = getEntity().getPosition().getFirst();
        double y = getEntity().getPosition().getSecond();

        gc.scale(-1, 1);

        getAnimationHandler().render(gc, -(x + frameW) + 2, y, scale);
        gc.restore();
    }

    public void updateEntitySize() {
        // size X and Y
        double sX = animationHandler.getCurrentFrameWidth();
        double sY = animationHandler.getCurrentFrameHeight();

        entity.setSize(new Tuple<>(sX, sY));
    }

    protected void setAnimationHandler(AnimationHandler animationHandler) { this.animationHandler = animationHandler; }

    public AnimationHandler getAnimationHandler() { return this.animationHandler; }

    protected Entity getEntity() { return this.entity; }
}
