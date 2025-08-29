package Model;

import Utilities.Tuple;

public class MovingPlatform extends Platform {
    public static final int MOVING_PLATFORM_WIDTH = 26;
    public static final int MOVING_PLATFORM_HEIGHT = 7;

    public MovingPlatform(Tuple<Double, Double> position) {
        super(position, new VerticalMovement(), 40.0, 26, 7);
    }
}
