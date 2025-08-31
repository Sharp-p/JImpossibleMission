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
    private List<FurniturePiece> furniture = new ArrayList<>();

    // TODO: lista di stanze (Rectangle2D)

    // TODO: se nell'area di una stanza spostare view su quella stanza

    // TODO nella classe stanza costruzione delle piattaforme,
    //  con coordinate rispetto al (MinX,MinY) della stanza

    // TODO: (MinX, MinY) della stanza secondo il sistema delle coordinate delle platforms,
    //  cosi le platforms interne possono usarlo tranquillamente

    public Stronghold() {
        StillMovement still = new StillMovement();
        VerticalMovement vertical = new VerticalMovement();

        // TODO: se c'hai proprio sbattimento, slidare tutto il
        //  sistema delle coordinate (delle platform) di -0.5
        // room 1
        createRoom1();


    }

    private void createRoom1 () {
        addHorizontalPlatforms(1, ROW_HEIGHT_TILES, 10, StillPlatform.class);
        addHorizontalPlatforms(13, ROW_HEIGHT_TILES, 3, StillPlatform.class);
        addHorizontalPlatforms(18, ROW_HEIGHT_TILES, 3, StillPlatform.class);

        addHorizontalPlatforms(3, ROW_HEIGHT_TILES * 2, 3, StillPlatform.class);
        addHorizontalPlatforms(8, ROW_HEIGHT_TILES * 2, 3, StillPlatform.class);
        addHorizontalPlatforms(11, ROW_HEIGHT_TILES * 2, 1, MovingPlatform.class);
        addSlot(platforms.getLast(),
                -ROW_HEIGHT_TILES * STILL_PLATFORM_HEIGHT);
        ((MovingPlatform)platforms.getLast()).setSlotIndex(1);

        addHorizontalPlatforms(13, ROW_HEIGHT_TILES * 2, 3, StillPlatform.class);
        addHorizontalPlatforms(16, ROW_HEIGHT_TILES * 2, 1, MovingPlatform.class);
        addSlot(platforms.getLast(),
                -ROW_HEIGHT_TILES * STILL_PLATFORM_HEIGHT);
        ((MovingPlatform)platforms.getLast()).setSlotIndex(1);

        addHorizontalPlatforms(18, ROW_HEIGHT_TILES * 2, 3, StillPlatform.class);

        addHorizontalPlatforms(1, ROW_HEIGHT_TILES * 3, 1, MovingPlatform.class);
        addSlot(platforms.getLast(),
                -ROW_HEIGHT_TILES * STILL_PLATFORM_HEIGHT);
        ((MovingPlatform)platforms.getLast()).setSlotIndex(1);
        addHorizontalPlatforms(3, ROW_HEIGHT_TILES * 3, 3, StillPlatform.class);
        addHorizontalPlatforms(6, ROW_HEIGHT_TILES * 3, 1, MovingPlatform.class);
        addSlot(platforms.getLast(),
                -ROW_HEIGHT_TILES * STILL_PLATFORM_HEIGHT);
        ((MovingPlatform)platforms.getLast()).setSlotIndex(1);
        addHorizontalPlatforms(8, ROW_HEIGHT_TILES * 3, 3, StillPlatform.class);
        addHorizontalPlatforms(11, ROW_HEIGHT_TILES * 3, 1, MovingPlatform.class);
        addSlot(platforms.getLast(),
                -ROW_HEIGHT_TILES * STILL_PLATFORM_HEIGHT);
        ((MovingPlatform)platforms.getLast()).setSlotIndex(1);
        addHorizontalPlatforms(13, ROW_HEIGHT_TILES * 3, 3, StillPlatform.class);
        addHorizontalPlatforms(16, ROW_HEIGHT_TILES * 3, 1, MovingPlatform.class);
        addSlot(platforms.getLast(),
                -ROW_HEIGHT_TILES * STILL_PLATFORM_HEIGHT);
        ((MovingPlatform)platforms.getLast()).setSlotIndex(1);
        addHorizontalPlatforms(18, ROW_HEIGHT_TILES * 3, 3, StillPlatform.class);

        addHorizontalPlatforms(1, ROW_HEIGHT_TILES * 4, 1, MovingPlatform.class);
        addSlot(platforms.getLast(),
                -ROW_HEIGHT_TILES * STILL_PLATFORM_HEIGHT);
        ((MovingPlatform)platforms.getLast()).setSlotIndex(1);
        addHorizontalPlatforms(3, ROW_HEIGHT_TILES * 4, 3, StillPlatform.class);
        addHorizontalPlatforms(6, ROW_HEIGHT_TILES * 4, 1, MovingPlatform.class);
        addSlot(platforms.getLast(),
                -ROW_HEIGHT_TILES * STILL_PLATFORM_HEIGHT);
        ((MovingPlatform)platforms.getLast()).setSlotIndex(1);
        addHorizontalPlatforms(8, ROW_HEIGHT_TILES * 4, 13, StillPlatform.class);

        addVerticalPlatforms(0, ROW_HEIGHT_TILES * 3, "left");
        addVerticalPlatforms(ROW_HEIGHT_TILES * 4, ROW_HEIGHT_TILES * 4, "left");
        addVerticalPlatforms(0, 0, "right");
        addVerticalPlatforms(ROW_HEIGHT_TILES,  ROW_HEIGHT_TILES * 4, "right");

        for (Platform platform : platforms) {
            if (platform instanceof MovingPlatform) { ((MovingPlatform) platform).setUpGroup(platforms);}
        }

        furniture.add(new FurniturePiece(new Tuple<>(
                (double)STILL_PLATFORM_WIDTH,
                (double)ROW_HEIGHT_TILES * STILL_PLATFORM_HEIGHT
        )));

        furniture.add(new FurniturePiece(new Tuple<>(
                STILL_PLATFORM_WIDTH * 6.0,
                (double)ROW_HEIGHT_TILES * STILL_PLATFORM_HEIGHT
        )));

        furniture.add(new FurniturePiece(new Tuple<>(
                STILL_PLATFORM_WIDTH * 8.0,
                ROW_HEIGHT_TILES * STILL_PLATFORM_HEIGHT * 4.0
        )));

        furniture.add(new FurniturePiece(new Tuple<>(
                STILL_PLATFORM_WIDTH * 16.0,
                ROW_HEIGHT_TILES * STILL_PLATFORM_HEIGHT * 4.0
        )));
    }

    public List<FurniturePiece> getFurniture() { return furniture; }

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
                    sX + realWidth * i,
                    sY));
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
                    Wall.class, pos.getFirst(), pos.getSecond()
            ));
        }
    }

    private void addSlot(Platform platform, double offsetY) {
        ((MovingPlatform)platforms.getLast()).addVerticalSlot(
                platform.getPosition().getFirst(),
                platform.getPosition().getSecond() + offsetY);
    }

    public List<Platform> getPlatforms() { return platforms; }

    public List<Platform> getStillPlatforms() {
        return platforms.stream()
                .filter(p -> p.getMovementBehavior() instanceof StillMovement)
                .toList();
    }

    public List<MovingPlatform> getMovingPlatforms() {
        return platforms.stream()
                .filter(p -> p.getMovementBehavior() instanceof VerticalMovement)
                .map(p -> (MovingPlatform) p)
                .toList();
    }
}
