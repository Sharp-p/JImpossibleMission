package Model;

import Utilities.Tuple;

public class Platform extends Entity{
    //private Tuple<Double, Double> size;

    public Platform(Tuple<Double, Double> position,
                    MovementBehavior movementBehav, Double speed) {
        super(position, movementBehav, speed);
    }

    public void moveTo(Direction dir, Double deltaTime) {
        getMovementBehavior().move(this, dir, deltaTime);
    }

    //public Tuple<Double, Double> getSize() { return size; }
}
