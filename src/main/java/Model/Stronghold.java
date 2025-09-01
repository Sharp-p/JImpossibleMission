package Model;

import Utilities.Tuple;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;
import java.util.List;

import static Model.Robot.GROUND_ROBOT_HEIGHT;
import static Model.StillPlatform.*;
import static Model.MovingPlatform.*;
import static config.GameConstants.*;

public class Stronghold {
    private List<Platform> platforms = new ArrayList<>();
    private PlatformFactory pltFactory = new  PlatformFactory();
    private List<FurniturePiece> furniture = new ArrayList<>();
    private List<Rectangle2D> areas = new ArrayList<>();
    private List<Entity> entities = new ArrayList<>();

    private Agent agent;
    private int currentArea = 0;

    // TODO: se nell'area di una stanza spostare view su quella stanza

    // TODO nella classe stanza costruzione delle piattaforme,
    //  con coordinate rispetto al (MinX,MinY) della stanza

    // TODO: (MinX, MinY) della stanza secondo il sistema delle coordinate delle platforms,
    //  cosi le platforms interne possono usarlo tranquillamente

    public Stronghold() {
        // TODO: se c'hai proprio sbattimento, slidare tutto il
        //  sistema delle coordinate (delle platform) di -0.5
        createAgent(13.0,  ROW_HEIGHT - 30, 0);

        entities.add(new StillRobot(new Tuple<>(70.0, ROW_HEIGHT - 18.0), 4));
        entities.add(new MovingRobot(new Tuple<>(14.0 * STILL_PLATFORM_WIDTH, (double)ROW_HEIGHT_TILES * STILL_PLATFORM_HEIGHT - GROUND_ROBOT_HEIGHT + 1)));
        ((MovingRobot)entities.getLast()).setBounds(13 *  STILL_PLATFORM_WIDTH, 15 *  STILL_PLATFORM_WIDTH);
        entities.add(new SightRobot(new Tuple<>(14.0 * STILL_PLATFORM_WIDTH, ROW_HEIGHT_TILES * STILL_PLATFORM_HEIGHT * 4.0 - GROUND_ROBOT_HEIGHT + 1)));
        ((SightRobot)entities.getLast()).setBounds(8 * STILL_PLATFORM_WIDTH, 20 * STILL_PLATFORM_WIDTH);
        entities.add(new ShootingRobot(
                new Tuple<>(3.0 * STILL_PLATFORM_WIDTH, ROW_HEIGHT_TILES * STILL_PLATFORM_HEIGHT * 4.0 - GROUND_ROBOT_HEIGHT + 1),
                3 * STILL_PLATFORM_WIDTH, 5 * STILL_PLATFORM_WIDTH)
        );

        entities.add((((ShootingRobot)entities.getLast()).getPlasmaBolt()));

        // creates all the world areas on which the viewport can focus
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                areas.add(new Rectangle2D(j * LOGICAL_WIDTH, i * LOGICAL_HEIGHT, LOGICAL_WIDTH, LOGICAL_HEIGHT));
                System.out.println(areas.getLast());
            }
        }

        // TODO: aggiustare la posizione delle entità per area
        // room 1
        createRoom7(0);
        createRoom4(1);
        

    }

    private void createRoom7(int areaIndex) {
        addHorizontalPlatforms(1, 1, 2, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(3, 1, 1,  areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), 1);
        setSlotIndex(platforms.getLast(), 0);

        addHorizontalPlatforms(5, 1, 13, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(18, 1, 1, areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), 3);
        addSlot(platforms.getLast(), 2);
        addSlot(platforms.getLast(), 1);
        setSlotIndex(platforms.getLast(), 0);


        addHorizontalPlatforms(5, 2, 5, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(16, 2, 2, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(20, 2, 1, areaIndex, StillPlatform.class);

        addHorizontalPlatforms(12, 2.5, 1, areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), 1);
        setSlotIndex(platforms.getLast(), 0);

        addHorizontalPlatforms(14, 2.5, 2, areaIndex, StillPlatform.class);

        addHorizontalPlatforms(1, 3, 1, areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), 1);
        addSlot(platforms.getLast(), -1);
        setSlotIndex(platforms.getLast(), 1);

        addHorizontalPlatforms(5, 3, 5, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(16, 3, 2, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(20, 3, 1, areaIndex, StillPlatform.class);

        addHorizontalPlatforms(14, 3.5, 2, areaIndex, StillPlatform.class);

        addHorizontalPlatforms(3, 4, 7, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(16, 4, 2, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(20, 4, 1, areaIndex, StillPlatform.class);

    }


    private void createRoom4(int areaIndex) {
        addHorizontalPlatforms(1, 1, 10, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(13, 1, 3, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(18, 1, 3, areaIndex, StillPlatform.class);

        addHorizontalPlatforms(3, 2, 3, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(8,  2, 3, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(11,  2, 1, areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), -1);
        setSlotIndex(platforms.getLast(), 1);

        addHorizontalPlatforms(13,  2, 3, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(16,  2, 1, areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), -1);
        setSlotIndex(platforms.getLast(), 1);
        addHorizontalPlatforms(18,  2, 3, areaIndex, StillPlatform.class);

        addHorizontalPlatforms(1,  3, 1, areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), -1);
        setSlotIndex(platforms.getLast(), 1);
        addHorizontalPlatforms(3,  3, 3, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(6,  3, 1, areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), -1);
        setSlotIndex(platforms.getLast(), 1);
        addHorizontalPlatforms(8,  3, 3, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(11,  3, 1, areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), -1);
        setSlotIndex(platforms.getLast(), 1);
        addHorizontalPlatforms(13,  3, 3, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(16,  3, 1, areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), -1);
        setSlotIndex(platforms.getLast(), 1);
        addHorizontalPlatforms(18,  3, 3, areaIndex, StillPlatform.class);

        addHorizontalPlatforms(1,  4, 1, areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), -1);
        setSlotIndex(platforms.getLast(), 1);
        addHorizontalPlatforms(3,  4, 3, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(6,  4, 1, areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), -1);
        setSlotIndex(platforms.getLast(), 1);
        addHorizontalPlatforms(8,  4, 13, areaIndex, StillPlatform.class);

        addVerticalPlatforms(0,  3, areaIndex,"left");
        addVerticalPlatforms( 4,  4, areaIndex,"left");
        addVerticalPlatforms(0, 0, areaIndex,"right");
        addVerticalPlatforms(1,   4, areaIndex,"right");

        for (Platform platform : platforms) {
            if (platform instanceof MovingPlatform) { ((MovingPlatform) platform).setUpGroup(platforms);}
        }

        createFurniture(1, 1, areaIndex);

        createFurniture(6, 1, areaIndex);

        createFurniture(8, 4, areaIndex);

        createFurniture(16, 4, areaIndex);
    }

    /**
     * Function that creates a new agent. Used for every respawn instance.
     * @return Returns the new agent that the model uses
     */
    public void createAgent(double x, double y, int areaIndex) {
        // TODO: [RESPAWN] luogo respawn rispetto a porta di entrata
        agent = new Agent(new Tuple<>(x, y));
    }

    public void setSlotIndex(Platform platform, int slotIndex) {
        ((MovingPlatform)platform).setSlotIndex(slotIndex);
    }

    public void createFurniture(int x, int y, int areaIndex) {
        x = x * STILL_PLATFORM_WIDTH + (int) areas.get(areaIndex).getMinX();
        y = y * ROW_HEIGHT_TILES * STILL_PLATFORM_HEIGHT + (int) areas.get(areaIndex).getMinY();
        furniture.add(new FurniturePiece(new Tuple<>((double) x, (double) y)));
    }


    /**
     * Add platforms horizontally starting at (areaX, areaY),
     * following a (platform, row) coordinate system.
     * @param x X coordinate in tiles width
     * @param y Y coordinate in tiles height for each row (ROW_HEIGHT_TILES)
     * @param qnt The quantity of platforms
     */
    private <T extends Platform> void addHorizontalPlatforms(double x,
                                                             double y,
                                                             int qnt,
                                                             int areaIndex,
                                                             Class<T> platformClass) {
        int realWidth = platformClass.isAssignableFrom(MovingPlatform.class) ? MOVING_PLATFORM_WIDTH : STILL_PLATFORM_WIDTH;
        // from (platform, row) coordinates system to real coordinates
        double sX = x * STILL_PLATFORM_WIDTH + areas.get(areaIndex).getMinX();
        double sY = y * STILL_PLATFORM_HEIGHT * ROW_HEIGHT_TILES +
                areas.get(areaIndex).getMinY();

        System.out.println("Prima riga: " + sY);

        for (int i = 0; i < qnt; i++) {
            platforms.add(pltFactory.createPlatform(
                    platformClass,
                    sX + realWidth * i,
                    sY));
        }
    }

    /**
     * Used only to build vertical walls on the right or left side of a room.
     * The coordinates follow a row based coordinate system.
     * @param upperY The highest platform of the wall
     * @param lowerY The lowest platform of the wall
     * @param side Either 'left' or 'right'
     * @param <T> The type of platform
     */
    private <T extends Platform> void addVerticalPlatforms(int upperY, int lowerY, int areaIndex, String side) {
        upperY = (int) (upperY * ROW_HEIGHT_TILES + areas.get(areaIndex).getMinY());
        lowerY = (int) (lowerY * ROW_HEIGHT_TILES + areas.get(areaIndex).getMinY());
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

    /**
     * The position on which the platform will stop moving
     * @param platform The platform to which the slot will be added
     * @param offsetY The offset in rows (negative up, positive down)
     *                from the platform position
     */
    private void addSlot(Platform platform, double offsetY) {
        offsetY *= ROW_HEIGHT_TILES * STILL_PLATFORM_HEIGHT;
        ((MovingPlatform)platforms.getLast()).addVerticalSlot(
                platform.getPosition().getFirst(),
                platform.getPosition().getSecond() + offsetY);
    }

    public void moveAgent(Direction direction, Double deltaTime) {
        agent.moveTo(direction, deltaTime);
    }

    public void addEntity(Entity entity) { entities.add(entity); }

    public void setUsingLift(boolean usingLift) { agent.setUsingLift(usingLift); }

    public boolean isUsingLift() { return agent.isUsingLift(); }

    public int getCurrentArea() { return currentArea; }

    public List<Enemy> getEnemies() {
        return entities.stream()
                .filter(e -> e instanceof Enemy)
                .map(e -> (Enemy) e)
                .toList();
    }

    public List<Robot> getRobots() {
        return entities.stream()
                .filter(e -> e instanceof Robot)
                .map(e -> (Robot) e)
                .toList();
    }

    public List<Entity> getEntities() { return entities; }

    public List<FurniturePiece> getFurniture() { return furniture; }

    public Agent getAgent() { return agent; }

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
