package Model;

import Utilities.Tuple;

import static config.GameConstants.*;

public class Agent extends Entity {

    public Agent(Tuple<Double, Double> position,
                 MovementBehavior movementBehav) {
        super(position, movementBehav, 200.0);
    }



}
