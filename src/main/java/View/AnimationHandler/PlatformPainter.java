package View.AnimationHandler;

import Model.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.List;

public class PlatformPainter extends EntityPainter {

    public PlatformPainter(Platform platforms) {
        super(platforms);

        Animation still = new Animation("still", false)
                .add(new Frame(new Rectangle2D(5, 5, 15, 9), 0, 0, 1));

        Animation wall = new Animation("wall", false)
                .add(new Frame(new Rectangle2D(23, 5, 15, 9), 0, 0, 1));

        Animation moving = new Animation("moving", false)
                .add(new Frame(new Rectangle2D(41, 5, 28, 9), 0, 0, 1));


        setAnimationHandler(new AnimationHandler(new Image(getClass()
                .getResourceAsStream("/spriteSheets/tiles/yellow_sheet.png"))));

        getAnimationHandler().addAnimation(still);
        getAnimationHandler().addAnimation(moving);
        getAnimationHandler().addAnimation(wall);
    }

    @Override
    public void draw(GraphicsContext gc, double dt, double scale) {
        Entity platform = getEntity();
        if (platform.getClass() ==  StillPlatform.class) getAnimationHandler().play("still");
        else if(platform.getClass() == MovingPlatform.class) getAnimationHandler().play("moving");
        else getAnimationHandler().play("wall");

        getAnimationHandler().update(dt);

        getAnimationHandler().render(gc, platform.getPosition().getFirst(),
                platform.getPosition().getSecond(), scale);

        updateEntitySize();
    }
}
