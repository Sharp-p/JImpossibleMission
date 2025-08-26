package Model;

import Utilities.Tuple;

public class HorizontalMovement implements MovementBehavior{
    @Override
    public void move(Entity entity, Direction dir, double deltaTime) {
        double xV;

        switch (dir) {
            case LEFT -> {
                xV = -entity.getSpeed();
                entity.setDirection(Direction.LEFT);
            }
            case RIGHT -> {
                xV = entity.getSpeed();
                entity.setDirection(Direction.RIGHT);
            }
            case NONE -> xV = 0;
            default -> { return; }
        }

        // new coordinates of the entity position
        Double newX = entity.getPosition().getFirst() + xV * deltaTime;
        entity.setVelocity(new Tuple<>(xV, entity.getVelocity().getSecond()));

        double diffX =  entity.getPosition().getFirst() - newX;
        // System.out.println("Difference X: " + diffX);

        entity.setPosition(new Tuple<>(newX, entity.getPosition().getSecond()));
    }
}
