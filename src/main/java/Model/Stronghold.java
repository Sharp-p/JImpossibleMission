package Model;

import Utilities.Tuple;

import java.util.ArrayList;
import java.util.List;

import static config.GameConstants.PLATFORM_WIDTH;
import static config.GameConstants.ROW_HEIGHT;

public class Stronghold {
    private List<Platform> platforms= new ArrayList<>();

    public Stronghold() {
        StillMovement still = new StillMovement();
        VerticalMovement vertical = new VerticalMovement();
        // room 1
        // TODO: sistema di coordinate per le tiles (0,0) -> (0,0), (1,1) -> (1*PLT_WIDTH, 1*PLT_HEIGHT)
        addPlatforms(10, ROW_HEIGHT, 2, still);
        addPlatforms(140, ROW_HEIGHT, 2, still);
        addPlatforms(218, ROW_HEIGHT, 4, still);

        addPlatforms(10, ROW_HEIGHT * 2, 2, still);
        addPlatforms(140, ROW_HEIGHT * 2, 2, still);
        addPlatforms(218, ROW_HEIGHT * 2, 4, still);

        addPlatforms(10, ROW_HEIGHT * 3, 6, still);
        addPlatforms(218, ROW_HEIGHT * 3, 4, still);

        addPlatforms(10, ROW_HEIGHT * 4, 15, still);

        addPlatforms(10 + PLATFORM_WIDTH * 15, ROW_HEIGHT * 4, 3, still);
        // TODO: add moving platforms
    }

    /**
     * Add platforms horizontally starting at (x, y)
     * @param x X coordinate
     * @param y Y coordinate
     * @param qnt The quantity of platforms
     */
    private void addPlatforms(double x,
                              double y,
                              int qnt,
                              MovementBehavior movementBehavior) {
        for (int i = 0; i < qnt; i++) {
            platforms.add(new Platform(
                    new Tuple<>(x + i * PLATFORM_WIDTH, y), movementBehavior));
        }
    }

    public List<Platform> getPlatforms() { return platforms; }

    public List<Platform> getStillPlatforms() {
        return platforms.stream()
                .filter(p -> p.getMovementBehavior() instanceof StillMovement)
                .toList();
    }

    public List<Platform> getMovingPlatforms() {
        return platforms.stream()
                .filter(p -> p.getMovementBehavior() instanceof VerticalMovement)
                .toList();
    }
}
