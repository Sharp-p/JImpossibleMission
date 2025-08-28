package config;

public class GameConstants {
    // physical constants

    /**
     * Gravity constant in px/s^2
     */
    public static final double GRAVITY = 150.0;

    public static final double FLOOR_Y = 150.0;

    // graphical constants
    public static final int LOGICAL_WIDTH = 290;
    public static final int LOGICAL_HEIGHT = 190;

    public static final int SCREEN_WIDTH = 870;
    public static final int SCREEN_HEIGHT = 585;

    public static final double RUN_FRAME_DURATION = 0.07;
    public static final double JUMP_FRAME_DURATION = 0.1;

    public static final int PLATFORM_WIDTH = 13;
    public static final int PLATFORM_HEIGHT = 7;
    public static final int ROW_HEIGHT = LOGICAL_HEIGHT / 4;
    public static final int COLUMN_HEIGHT = 7;
    public static final int HIGHEST_STANDING_SIZE = 30;
}
