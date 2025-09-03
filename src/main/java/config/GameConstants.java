package config;

/**
 * Class that define many constant used in any part of the project
 */
public class GameConstants {
    // physical constants

    /**
     * Gravity constant in px/s^2
     */
    public static final double GRAVITY = 180.0;

    // graphical constants
    public static final int LOGICAL_WIDTH = 290;
    public static final int LOGICAL_HEIGHT = 190;

    public static final int SCREEN_WIDTH = 870;
    public static final int SCREEN_HEIGHT = 610;

    // it's a double but need to be integer because the pixel cannot be split,
    // and it would cause graphical alignment issues if it isn't an integer
    public static final double SCALE_FACTOR = Math.min((double)SCREEN_WIDTH / LOGICAL_WIDTH, (double)SCREEN_HEIGHT / LOGICAL_HEIGHT);

    public static final double RUN_FRAME_DURATION = 0.07;
    public static final double JUMP_FRAME_DURATION = 0.1;
    public static final double TURNING_FRAME_DURATION = 0.1;
    public static final double SHOOTING_FRAME_DURATION = 0.05;

    public static final int PLATFORM_WIDTH = 13;
    public static final int PLATFORM_HEIGHT = 7;
    public static final int ROW_HEIGHT = LOGICAL_HEIGHT / 4;
    public static final int ROW_HEIGHT_TILES = (int) Math.ceil((double) ROW_HEIGHT / PLATFORM_HEIGHT);
    public static final int COLUMN_HEIGHT = 7;
    public static final int HIGHEST_STANDING_SIZE = 30;
}
