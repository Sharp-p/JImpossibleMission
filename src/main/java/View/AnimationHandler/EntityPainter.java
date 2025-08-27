package View.AnimationHandler;

import Model.Entity;
import Utilities.Tuple;
import javafx.scene.canvas.GraphicsContext;

public abstract class EntityPainter {
    private AnimationHandler animationHandler;
    private Entity entity;

    protected EntityPainter () {}

    protected EntityPainter(Entity entity) {
        this.entity = entity;
    }

    protected abstract void draw(GraphicsContext gc, double dt, double scale);

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
