package View.AnimationHandler;

import Model.Projectile;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static config.GameConstants.SHOOTING_FRAME_DURATION;

public class ProjectilePainter extends EntityPainter {
    public ProjectilePainter(Projectile projectile) {
        super(projectile);

        Animation shooting = new Animation("shooting", true)
                .add(new Frame(new Rectangle2D(2, 5, 41, 10), -1, -1, SHOOTING_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(2, 33, 41, 9), -1, -1, SHOOTING_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(2, 59, 41, 11), -1, -1, SHOOTING_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(2, 46, 41, 11), -1, -1, SHOOTING_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(2, 18, 41, 11), -1, -1, SHOOTING_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(2, 59, 41, 11), -1, -1, SHOOTING_FRAME_DURATION))
                .add(new Frame(new Rectangle2D(2, 46, 41, 11), -1, -1, SHOOTING_FRAME_DURATION));

        setAnimationHandler(new AnimationHandler(
                new Image(getClass().getResourceAsStream(
                        "/spriteSheets/enemies/robot/plasma_bolts_spritesheet.png"
                ))
        ));

        getAnimationHandler().addAnimation(shooting);
    }

    @Override
    public void draw(GraphicsContext gc, double dt, double scale) {
        if (getEntity().isActive()) {
            getAnimationHandler().update(dt);

            switch (getEntity().getDirection()) {
                case LEFT -> drawInverted(gc, scale);
                case RIGHT -> getAnimationHandler().render(
                        gc, getEntity().getPosition().getFirst(),
                        getEntity().getPosition().getSecond(), scale);
            }
        }
        updateEntitySize();
    }
}
