package Controller;

import Model.Agent;
import Model.Enemy;
import Model.Entity;
import javafx.geometry.Rectangle2D;

public class CollisionHandler {
    public static Rectangle2D getBounds(Entity entity) {
        Rectangle2D bounds = null;
        // if the entity is an agent and is not grounded
        if (entity instanceof Agent) {
            if (!((Agent)entity).isGrounded()) {
                bounds = new Rectangle2D(
                        entity.getPosition().getFirst() + 4,
                        entity.getPosition().getSecond() + 4,
                        entity.getSize().getFirst() - 8,
                        entity.getSize().getSecond() - 8
                );
                return bounds;
            }
        }
        else if (entity instanceof Enemy) {
            bounds = new Rectangle2D(
                    entity.getPosition().getFirst() + 4,
                    entity.getPosition().getSecond() + 3,
                    entity.getSize().getFirst() - 8,
                    entity.getSize().getSecond() - 3
            );
            return bounds;
        }

        bounds = new Rectangle2D(
                entity.getPosition().getFirst(),
                entity.getPosition().getSecond(),
                entity.getSize().getFirst(),
                entity.getSize().getSecond()
        );

        return bounds;
    }

    public static boolean checkCollision(Entity a, Entity b) {
        return getBounds(a).intersects(getBounds(b));
    }
}
