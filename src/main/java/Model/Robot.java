package Model;

import Utilities.Tuple;

public abstract class Robot extends Enemy {
    private boolean isTurning = false;
    private boolean hasTurned = false;

    public Robot(Tuple<Double, Double> position,
                 MovementBehavior movementBehav,
                 Double speed) {
        super(position, movementBehav, speed);
    }

    public void setHasTurned(boolean hasTurned){ this.hasTurned = hasTurned; }

    public void setTurning(boolean turning) { this.isTurning = turning; }

    public boolean hasTurned() { return hasTurned; }

    public boolean isTurning() { return isTurning; }

    // TODO: per ogni tipo di robot update del comportamento
    /**
     * Defines the robot behavior
     */
    public abstract void update(double deltaTime);
}
