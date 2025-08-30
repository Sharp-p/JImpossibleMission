package Model;

import Utilities.Tuple;

public class FreeMovement implements MovementBehavior{
    @Override
    public void move(Entity entity,
                                     Direction dir, double deltaTime) {
        double xV = entity.getVelocity().getFirst();
        double yV = entity.getVelocity().getSecond();
        switch (dir) {
            case LEFT -> {
                xV = -entity.getSpeed();
                entity.setDirection(Direction.LEFT);
            }
            case RIGHT -> {
                xV = entity.getSpeed();
                entity.setDirection(Direction.RIGHT);
            }
            case UP -> {
                yV = -entity.getSpeed();
                entity.setDirection(Direction.UP);
            }
            case DOWN -> {
                yV = entity.getSpeed();
                entity.setDirection(Direction.DOWN);
            }
            case NONE -> {
                xV = 0;
                yV = 0;
            }
        }

        // new coordinates of the entity position
        Double newX = entity.getPosition().getFirst() + xV * deltaTime;
        Double newY = entity.getPosition().getSecond() + yV * deltaTime;
        entity.setVelocity(new Tuple<>(xV, yV));
        entity.setPosition(new Tuple<>(newX, newY));
    }
}
