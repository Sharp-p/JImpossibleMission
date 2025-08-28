package Model;

import Utilities.Tuple;

import java.util.ArrayList;
import java.util.List;

import static config.GameConstants.PLATFORM_WIDTH;
import static config.GameConstants.ROW_HEIGHT;

public class Stronghold {
    private List<Platform> platforms= new ArrayList<>();

    public Stronghold() {
        addPlatforms(10, ROW_HEIGHT, 2);
        addPlatforms(140, ROW_HEIGHT, 2);
        addPlatforms(218, ROW_HEIGHT, 4);

        addPlatforms(10, ROW_HEIGHT * 2, 2);
        addPlatforms(140, ROW_HEIGHT * 2, 2);
        addPlatforms(218, ROW_HEIGHT * 2, 4);

        addPlatforms(10, ROW_HEIGHT * 3, 6);
        addPlatforms(218, ROW_HEIGHT * 3, 4);

        addPlatforms(10, ROW_HEIGHT * 4, 20);
    }

    /**
     * Add platforms horizontally starting at (x, y)
     * @param x X coordinate
     * @param y Y coordinate
     * @param qnt The quantity of platforms
     */
    private void addPlatforms(double x, double y, int qnt) {
        for (int i = 0; i < qnt; i++) {
            platforms.add(new Platform(new Tuple<>(x + i * PLATFORM_WIDTH, y), new StillMovement(), 0.0));
        }
    }

    public List<Platform> getPlatforms() { return platforms; }
}
