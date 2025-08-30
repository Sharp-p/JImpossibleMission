package Model;

import Utilities.Tuple;

public class StillRobot extends Robot{
    public StillRobot(Tuple<Double, Double> position) {
        super(position, new StillMovement(), 0.0);
    }

    public void searchingPattern() {}

    public void moveTo(Direction dir, Double deltaTime) {}

}
