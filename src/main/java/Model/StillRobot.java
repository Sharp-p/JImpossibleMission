package Model;

import Utilities.Tuple;

public class StillRobot extends Robot{
    private static final double TURN_TIME = 4.0;
    private double timeSinceTurn = 0.0;

    public StillRobot(Tuple<Double, Double> position) {
        super(position, new StillMovement(), 0.0);
        //this.turnTime = turnTime;
    }



    @Override
    public void update(double deltaTime) {
        timeSinceTurn += deltaTime;
        Direction oldDir = getDirection();
        while (timeSinceTurn >= TURN_TIME) {
            if (timeSinceTurn > TURN_TIME) {
                setDirection(switchDirection());
                timeSinceTurn -= TURN_TIME;
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
