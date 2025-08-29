package Model;

import Utilities.Tuple;

import java.util.ArrayList;
import java.util.List;

import static Model.StillPlatform.*;
import static Model.MovingPlatform.*;
import static config.GameConstants.*;

public class Stronghold {
    private List<Platform> platforms = new ArrayList<>();
    private PlatformFactory pltFactory = new  PlatformFactory();

    public Stronghold() {
        StillMovement still = new StillMovement();
        VerticalMovement vertical = new VerticalMovement();
        int rowHeightTiles = (int) Math.ceil((double) ROW_HEIGHT / PLATFORM_HEIGHT);

        // TODO: se c'hai proprio sbattimento, slidare tutto il
        //  sistema delle coordinate (delle platform) di -0.5
        // room 1
        addHorizontalPlatforms(1, rowHeightTiles, 10, StillPlatform.class);
        addHorizontalPlatforms(13, rowHeightTiles, 3, StillPlatform.class);
        addHorizontalPlatforms(18, rowHeightTiles, 3, StillPlatform.class);

        addHorizontalPlatforms(3, rowHeightTiles * 2, 3, StillPlatform.class);
        addHorizontalPlatforms(8, rowHeightTiles * 2, 3, StillPlatform.class);
        addHorizontalPlatforms(11, rowHeightTiles * 2, 1, MovingPlatform.class);
        addHorizontalPlatforms(13, rowHeightTiles * 2, 3, StillPlatform.class);
        addHorizontalPlatforms(16, rowHeightTiles * 2, 1, MovingPlatform.class);
        addHorizontalPlatforms(18, rowHeightTiles * 2, 3, StillPlatform.class);

        addHorizontalPlatforms(1, rowHeightTiles * 3, 1, MovingPlatform.class);
        addHorizontalPlatforms(3, rowHeightTiles * 3, 3, StillPlatform.class);
        addHorizontalPlatforms(6, rowHeightTiles * 3, 1, MovingPlatform.class);
        addHorizontalPlatforms(8, rowHeightTiles * 3, 3, StillPlatform.class);
        addHorizontalPlatforms(11, rowHeightTiles * 3, 1, MovingPlatform.class);
        addHorizontalPlatforms(13, rowHeightTiles * 3, 3, StillPlatform.class);
        addHorizontalPlatforms(16, rowHeightTiles * 3, 1, MovingPlatform.class);
        addHorizontalPlatforms(18, rowHeightTiles * 3, 3, StillPlatform.class);

        addHorizontalPlatforms(1, rowHeightTiles * 4, 1, MovingPlatform.class);
        addHorizontalPlatforms(3, rowHeightTiles * 4, 3, StillPlatform.class);
        addHorizontalPlatforms(6, rowHeightTiles * 4, 1, MovingPlatform.class);
        addHorizontalPlatforms(8, rowHeightTiles * 4, 13, StillPlatform.class);

        addVerticalPlatforms(0, rowHeightTiles * 3, "left");
        addVerticalPlatforms(rowHeightTiles * 4, rowHeightTiles * 4, "left");
        addVerticalPlatforms(0, 0, "right");
        System.out.println("qua");
        addVerticalPlatforms(rowHeightTiles,  rowHeightTiles * 4, "right");
        System.out.println("qua");
    }

    /**
     * Add platforms horizontally starting at (x, y) using a slot system based on the dimen
     * @param x X coordinate
     * @param y Y coordinate
     * @param qnt The quantity of platforms
     */
    private <T extends Platform> void addHorizontalPlatforms(double x,
                                                             double y,
                                                             int qnt,
                                                             Class<T> platformClass) {
        int realWidth = platformClass.isAssignableFrom(MovingPlatform.class) ? MOVING_PLATFORM_WIDTH : STILL_PLATFORM_WIDTH;
        // from platforms coordinates system to real coordinates
        double sX = x * STILL_PLATFORM_WIDTH;
        double sY = y * STILL_PLATFORM_HEIGHT;

        for (int i = 0; i < qnt; i++) {
            platforms.add(pltFactory.createPlatform(
                    platformClass,
                    new Tuple<>(sX + realWidth * i, sY)));
        }
    }

    private <T extends Platform> void addVerticalPlatforms(int upperY, int lowerY, String side) {
        int sUpper = upperY * STILL_PLATFORM_HEIGHT;
        for (int i = 0; i <= lowerY - upperY; i++) {
            Tuple<Double, Double> pos = new Tuple<>(
                    (double) (side.equals("left") ? 0 : (LOGICAL_WIDTH / STILL_PLATFORM_WIDTH) - 1) * STILL_PLATFORM_WIDTH,
                    (double) sUpper + STILL_PLATFORM_HEIGHT * i);
            System.out.println(pos);
            platforms.add(pltFactory.createPlatform(
                    Wall.class, pos
                    ));
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
