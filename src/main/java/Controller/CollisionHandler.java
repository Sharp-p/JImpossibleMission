package Controller;

import Model.Entity;
import javafx.geometry.Rectangle2D;

public class CollisionHandler {
    public static Rectangle2D getBounds(Entity entity) {
        return new Rectangle2D(
                entity.getPosition().getFirst(),
                entity.getPosition().getSecond(),
                entity.getSize().getFirst(),
                entity.getSize().getSecond()
        );
    }

    public static boolean checkCollision(Entity a, Entity b) {
        return getBounds(a).intersects(getBounds(b));
    }
}
