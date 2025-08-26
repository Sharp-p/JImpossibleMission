package config;

import static config.GameConstants.*;

public class GameVariables {
    public static int screenWidth = SCREEN_WIDTH;
    public static int screenHeight = SCREEN_HEIGHT;

    // TODO: variabile globale della scala da usare
    //  per scalare direttamente nell'animationHandler

    public static int scale = Math.min(SCREEN_WIDTH / LOGICAL_WIDTH, SCREEN_HEIGHT / LOGICAL_HEIGHT);
}
