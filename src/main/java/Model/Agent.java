package Model;

import Utilities.Tuple;

import static config.GameConstants.*;

public class Agent extends Entity {
    private static final double JUMP_STRENGTH = 70.0;
    private static final double JUMP_DISTANCE = 200.0;

    private boolean hasHitGround = false;

    public Agent(Tuple<Double, Double> position,
                 MovementBehavior movementBehav) {
        super(position, movementBehav, 200.0);
    }

    public void applyGravity(double deltaTime) {
        double vY, vX, aY, x, y;

        x = getPosition().getFirst();
        y = getPosition().getSecond();
        vX = getVelocity().getFirst();
        vY = getVelocity().getSecond();

        // sets the acceleration to the gravitational
        // acceleration if not on the ground
        if (!isGrounded()) aY = GRAVITY;
        else {
            aY = 0;
            // vY = 0;
            vX = 0;
        }

        // vX += getAcceleration().getFirst();
        // System.out.println("vY pre gravity: " + vY);
        vY += aY * deltaTime;
        // System.out.println("vY after gravity: " + vY);

        // the velocity gets higher as the time passes
        x += vX * deltaTime;
        y += vY * deltaTime;

        // System.out.println("Posizione X: " + getPosition().getFirst() + " Y:" + y);

        // if hit ground
        if (y >= FLOOR_Y) {
            y = FLOOR_Y;
            // if it was not grounded
            if(!isGrounded()) setHitGround(true);
            setGrounded(true);
        } else {
            setGrounded(false);
        }

        // TODO: se sto cadendo deve essere presente anche la velocità sulla X
        setAcceleration(new Tuple<>(getAcceleration().getFirst(), aY));
        setVelocity(new Tuple<>(vX, vY));
        setPosition(new Tuple<>(x, y));
    }

    @Override
    public void moveTo(Direction dir, Double deltaTime) {
        // new velocity from the movement behavior
        // this is a second check done because the
        // logic of the jump is separated from the
        // logic of the animation of the jump
        switch (dir) {
            case LEFT, RIGHT -> {
                if  (isGrounded()) getMovementBehavior().move(
                        this, dir, deltaTime);
            }
            case UP -> { if (isGrounded()) jump(); }
        }
    }

    /**
     * Updates the agent velocities so that the physics
     * system will update the position accordingly
     */
    public void jump() {
        double vY = -getJumpStrength();
        double direction = getDirection() == Direction.RIGHT ? 1 : - 1;

        // half and total jump time
        double t = Math.sqrt(2 * getJumpStrength() / GRAVITY);
        double totalTime = 2 * t;

        double vX = (JUMP_DISTANCE / totalTime) * direction;

        setVelocity(new Tuple<>(vX, vY));
        setGrounded(false);
    }

    public boolean hasHitGround() { return hasHitGround; }

    public void setHitGround(boolean hasChanged) { this.hasHitGround = hasChanged; }

    public double getJumpStrength() { return JUMP_STRENGTH; }

}
