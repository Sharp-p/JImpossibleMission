package Model;

import Utilities.Tuple;

/**
 * This class defines any entity that can kill the agent
 */
public abstract class Enemy extends Entity{
    public Enemy(Tuple<Double, Double> position, MovementBehavior movementBehavior, double speed) {
        super(position, movementBehavior, speed);
    }
}
