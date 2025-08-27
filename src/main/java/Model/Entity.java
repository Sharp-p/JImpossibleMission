package Model;

import Utilities.Tuple;

/**
 * Every game object is an entity, from the tiles to the player and enemies
 */
public abstract class Entity {
    private final MovementBehavior movementBehav;
    private final Double speed;

    private Tuple<Double, Double> position;
    private Tuple<Double, Double> velocity;
    private Tuple<Double, Double> acceleration;
    /**
     * The already scaled size (for collision handling) of the entity.
     */
    private Tuple<Double, Double> size;
    private Direction direction = Direction.RIGHT;
    private boolean active;
    private boolean visible;

    public Entity(Tuple<Double, Double> position,
                  MovementBehavior movementBehav, Double speed) {
        this.position = position;
        this.movementBehav = movementBehav;
        this.active = true;
        this.visible = true;
        this.speed = speed;
        this.velocity = new Tuple<>(0.0, 0.0);
        this.acceleration = new Tuple<>(0.0, 0.0);
    }

    public abstract void moveTo(Direction dir, Double deltaTime);

    public void setAcceleration(Tuple<Double, Double> acceleration) { this.acceleration = acceleration; }

    public void setDirection(Direction direction) { this.direction = direction; }

    public void setActive(boolean active) { this.active = active; }

    public void setVisibility(boolean visible) { this.visible = visible; }

    public void setPosition(Tuple<Double, Double> position) { this.position = position; }

    public void setVelocity(Tuple<Double, Double> velocity) { this.velocity = velocity; }

    public void setSize(Tuple<Double, Double> size) { this.size = size; }

    public Tuple<Double, Double> getSize() { return size; }

    public Tuple<Double, Double> getAcceleration() { return this.acceleration; }

    public Direction getDirection() { return direction; }

    public Double getSpeed() { return speed; }

    public Tuple<Double, Double> getVelocity() { return this.velocity; }

    public Tuple<Double, Double> getPosition() { return position; }

    public boolean isActive() { return active; }

    public boolean isVisible() { return visible; }

    public MovementBehavior getMovementBehavior() { return movementBehav; }

}
