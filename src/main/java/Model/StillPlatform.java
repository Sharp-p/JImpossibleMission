package Model;

import Utilities.Tuple;

public class StillPlatform extends Platform {
    public static final int STILL_PLATFORM_WIDTH = 13;
    public static final int STILL_PLATFORM_HEIGHT = 7;

    public StillPlatform(Tuple<Double, Double> position) {
        super(position, new StillMovement(), 0.0, 13, 7);
    }
}
