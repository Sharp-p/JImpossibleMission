package Model;

import Utilities.Tuple;

public abstract class Robot extends Enemy {
    public Robot(Tuple<Double, Double> position,
                 MovementBehavior movementBehav,
                 Double speed) {
        super(position, movementBehav, speed);
    }

    // TODO: per ogni tipo di roobot update del comportamento

    public abstract void searchingPattern();
}
