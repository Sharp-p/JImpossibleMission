package Controller;

import Model.Agent;
import Model.Enemy;
import Model.Entity;
import javafx.geometry.Rectangle2D;

/**
 * This class defines the auxiliary methods to check collision
 */
public class CollisionHandler {
    /**
     * Create a Rectangle2D boundary of an entity
     * @param entity The entity of which we want the boundaries
     * @return The boundary as a Rectangle2D
     */
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

    /**
     * Takes two entities and check if they collide
     * @param a First entity
     * @param b Second entity
     * @return True if they collide, else false
     */
    public static boolean checkCollision(Entity a, Entity b) {
        return getBounds(a).intersects(getBounds(b));
    }
}
