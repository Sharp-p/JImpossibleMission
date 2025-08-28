package View.AnimationHandler;

import Model.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.List;

public class PlatformPainter extends EntityPainter {

    public PlatformPainter(Platform platforms) {
        super(platforms);

        Animation still = new Animation("still", false)
                .add(new Frame(new Rectangle2D(0, 0, 13, 7), 0, 0, 1));

        // TODO: add moving platform Animation (just one frame but exlusive to the "moving" platforms

        setAnimationHandler(new AnimationHandler(new Image(getClass()
                .getResourceAsStream("/spriteSheets/tiles/horizontal_yellow.png"))));

        getAnimationHandler().addAnimation(still);
    }

    @Override
    public void draw(GraphicsContext gc, double dt, double scale) {
        Platform platform = (Platform) getEntity();
        // TODO: checks on the MovementBehavior to play the correct animation
        getAnimationHandler().update(dt);

        getAnimationHandler().render(gc, platform.getPosition().getFirst(),
                platform.getPosition().getSecond(), scale);

        updateEntitySize();
    }
}
