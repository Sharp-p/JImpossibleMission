package Model;

import Utilities.Tuple;

public abstract class Robot extends Entity {
    public Robot(Tuple<Double, Double> position,
                 MovementBehavior movementBehav,
                 Double speed) {
        super(position, movementBehav, speed);
    }

    public abstract void searchingPattern();
}
