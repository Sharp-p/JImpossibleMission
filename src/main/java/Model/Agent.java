package Model;

import Utilities.Tuple;

import static config.GameConstants.*;

public class Agent extends Entity {
    private static final double JUMP_STRENGTH = 20.0;

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
            vY = 0;
        }

        vX += getAcceleration().getFirst();
        System.out.println("vY pre gravity: " + vY);
        vY += aY * deltaTime;
        System.out.println("vY after gravity: " + vY);

        // the velocity gets higher as the time passes
        x += vX * deltaTime;
        y += vY * deltaTime;

        System.out.println("Posizione X: " + getPosition().getFirst() + " Y:" + y);

        if (y >= FLOOR_Y) {
            y = FLOOR_Y;
            setGrounded(true);
            System.out.println("qua");
        } else {
            setGrounded(false);
        }

        // TODO: se sto cadendo deve essere presente anche la velocità sulla X
        setAcceleration(new Tuple<>(getAcceleration().getFirst(), aY));
        setVelocity(new Tuple<>(getVelocity().getFirst(), vY));
        setPosition(new Tuple<>(getPosition().getFirst(), y));
    }

    @Override
    public void moveTo(Direction dir, Double deltaTime) {
        // new velocity from the movement behavior
        switch (dir) {
            case LEFT, RIGHT -> getMovementBehavior().move(this, dir, deltaTime);
            case UP -> {
                if (isGrounded()) {
                    //setPosition(new Tuple<>(getPosition().getFirst(), getVelocity().getSecond()));
                    setVelocity(new Tuple<>(getVelocity().getFirst(), -getJumpStrength()));
                    setGrounded(false);
                }
            }
        }
    }

    public double getJumpStrength() { return JUMP_STRENGTH; }

}
