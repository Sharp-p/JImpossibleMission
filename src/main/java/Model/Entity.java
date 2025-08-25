package Model;

import Utilities.Tuple;

public abstract class Entity {
    private Tuple<Double, Double> position;
    private boolean active;
    private boolean visible;
    private Tuple<Double, Double> velocity;
    private Direction direction = Direction.NONE;
    private Tuple<Double, Double> acceleration;
    private boolean grounded;
    private final MovementBehavior movementBehav;
    private final Double speed;

    public Entity(Tuple<Double, Double> position,
                  MovementBehavior movementBehav, Double speed) {
        this.position = position;
        this.movementBehav = movementBehav;
        this.active = true;
        this.visible = true;
        this.speed = speed;
        this.velocity = new Tuple<>(0.0, 0.0);
        this.acceleration = new Tuple<>(0.0, 0.0);
        this.grounded = false;
    }

    public void moveTo(Direction dir, Double deltaTime) {
        // new velocity from the movement behavior
        movementBehav.move(this, dir, deltaTime);
    }

    public void setAcceleration(Tuple<Double, Double> acceleration) { this.acceleration = acceleration; }

    public void setGrounded(boolean grounded) { this.grounded = grounded; }

    public void setDirection(Direction direction) { this.direction = direction; }

    public void setActive(boolean active) { this.active = active; }

    public void setVisibility(boolean visible) { this.visible = visible; }

    public void setPosition(Tuple<Double, Double> position) { this.position = position; }

    public void setVelocity(Tuple<Double, Double> velocity) { this.velocity = velocity; }

    public Tuple<Double, Double> getAcceleration() { return this.acceleration; }

    public boolean isGrounded() { return this.grounded; }

    public Direction getDirection() { return direction; }

    public Double getSpeed() { return speed; }

    public Tuple<Double, Double> getVelocity() { return this.velocity; }

    public Tuple<Double, Double> getPosition() { return position; }

    public boolean isActive() { return active; }

    public boolean isVisible() { return visible; }

    public MovementBehavior getMovementBehavior() { return movementBehav; }

}
