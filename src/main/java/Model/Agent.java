package Model;

import Utilities.Tuple;

import static config.GameConstants.*;

public class Agent extends Entity {

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
        vY += aY * deltaTime;

        // the velocity gets higher as the time passes
        x += vX * deltaTime;
        y += vY * deltaTime;


        if (getPosition().getSecond() >= FLOOR_Y) {
            y = FLOOR_Y;
            setGrounded(true);
        } else {
            setGrounded(false);
        }

        // TODO: se sto cadendo deve essere presente anche la velocità sulla X
        setAcceleration(new Tuple<>(getAcceleration().getFirst(), aY));
        setVelocity(new Tuple<>(getVelocity().getFirst(), vY));
        setPosition(new Tuple<>(getPosition().getFirst(), y));

    }

}
