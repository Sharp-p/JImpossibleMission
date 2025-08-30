package Model;

import Utilities.Tuple;

import static config.GameConstants.*;

public class Agent extends Entity {
    private static final double JUMP_STRENGTH = 82.5;
    private static final double JUMP_DISTANCE = 117.0;
    public static final double SPEED = 74.28;

    private boolean grounded = false;
    private boolean hasHitGround = false;
    private boolean usingLift = false;

    public Agent(Tuple<Double, Double> position,
                 MovementBehavior movementBehav) {
        super(position, movementBehav, SPEED);
    }

    public void applyGravity(double deltaTime) {
        double vY, vX, aY, x, y;

        x = getPosition().getFirst();
        y = getPosition().getSecond();
        vX = getVelocity().getFirst();
        vY = getVelocity().getSecond();

        // sets the acceleration to the gravitational
        // acceleration if not on the ground
        if (!isGrounded()) {
            aY = GRAVITY;
            // System.out.println("CONTROLLO GRAVITà");
        }
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
//        if (y >= FLOOR_Y) {
//            y = FLOOR_Y;
//            // if it was not grounded
//            if(!isGrounded()) setHitGround(true);
//            setGrounded(true);
//        } else {
//            setGrounded(false);
//        }

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
            // TODO: and not using platform - else
            case UP -> {
                if (isGrounded()) {
                    if (!usingLift) {
                        jump();
                    }
                    else getMovementBehavior().move(this, dir, deltaTime);
                }
            }
            case DOWN -> getMovementBehavior().move(this, dir, deltaTime);
        }
    }

    /**
     * Updates the agent velocities so that the physics
     * system will handle the position accordingly
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

    public boolean isGrounded() { return this.grounded; }

    public void setUsingLift(boolean usingLift) { this.usingLift = usingLift; }

    public void setHitGround(boolean hasChanged) { this.hasHitGround = hasChanged; }

    public void setGrounded(boolean grounded) { this.grounded = grounded; }

    public double getJumpStrength() { return JUMP_STRENGTH; }

    public boolean isUsingLift() { return usingLift; }

}
