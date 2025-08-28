package Model;

import Utilities.Tuple;

public class VerticalMovement implements MovementBehavior {
    public void move(Entity entity, Direction dir, double deltaTime) {
        double yV;

        switch (dir) {
            case UP -> {
                yV = -entity.getSpeed();
                entity.setDirection(Direction.UP);
            }
            case DOWN -> {
                yV = entity.getSpeed();
                entity.setDirection(Direction.DOWN);
            }
            case NONE -> yV = 0;
            default -> { return; }
        }

        // new coordinates of the entity position
        Double newY = entity.getPosition().getSecond() + yV * deltaTime;
        entity.setVelocity(new Tuple<>(entity.getVelocity().getFirst(), yV));

        entity.setPosition(new Tuple<>(entity.getPosition().getFirst(), newY));
    }
}
