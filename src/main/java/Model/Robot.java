package Model;

import Utilities.Tuple;

public abstract class Robot extends Enemy {
    public static final int GROUND_ROBOT_WIDTH = 23;
    public static final int GROUND_ROBOT_HEIGHT = 16;

    private boolean isTurning = false;
    private boolean hasTurned = false;

    public Robot(Tuple<Double, Double> position,
                 MovementBehavior movementBehav,
                 Double speed) {
        super(position, movementBehav, speed);
    }

    public Direction switchDirection() {
        if (getDirection() == Direction.RIGHT) return Direction.LEFT;
        else return Direction.RIGHT;
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
