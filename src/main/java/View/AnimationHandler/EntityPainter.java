package View.AnimationHandler;

import Model.Entity;
import javafx.scene.canvas.GraphicsContext;

public abstract class EntityPainter {
    private AnimationHandler animationHandler;
    private Entity entity;

    protected EntityPainter(Entity entity) {
        this.entity = entity;
    }

    protected abstract void draw(GraphicsContext gc, double dt, double scale);

    public AnimationHandler getAnimationHandler() { return this.animationHandler; }

    protected void setAnimationHandler(AnimationHandler animationHandler) { this.animationHandler = animationHandler; }

    protected Entity getEntity() { return this.entity; }
}
