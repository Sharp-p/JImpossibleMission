package Model;

public class FreeMovement implements MovementBehavior{
    @Override
    public void move(Entity entity,
                                     Direction dir, double deltaTime) {
        double xV = 0;
        double yV = 0;

        switch (dir) {
            case LEFT -> {
                xV = -entity.getSpeed();
                entity.setDirection(Direction.LEFT);
            }
            case RIGHT -> {
                xV = entity.getSpeed();
                entity.setDirection(Direction.RIGHT);
            }
            case UP -> yV = -entity.getSpeed();
            case DOWN -> yV = entity.getSpeed();
        }

    }
}
