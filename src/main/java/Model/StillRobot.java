package Model;

import Utilities.Tuple;

public class StillRobot extends Robot{
    private double timeSinceTurn = 0.0;
    private double turnTime;

    public StillRobot(Tuple<Double, Double> position, double turnTime) {
        super(position, new StillMovement(), 0.0);
        this.turnTime = turnTime;
    }

    private Direction switchDirection() {
        if (getDirection() == Direction.RIGHT) return Direction.LEFT;
        else return Direction.RIGHT;
    }

    @Override
    public void update(double deltaTime) {
        timeSinceTurn += deltaTime;
        Direction oldDir = getDirection();
        while (timeSinceTurn >= turnTime) {
            if (timeSinceTurn > turnTime) {
                setDirection(switchDirection());
                timeSinceTurn -= turnTime;
            }
        }
        if (oldDir != getDirection()) {
            setTurning(true);
            setHasTurned(true);
            //System.out.println("Direction: " + getDirection());
        }
    }

    @Override
    public void moveTo(Direction dir, Double deltaTime) {}

}
