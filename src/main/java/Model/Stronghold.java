package Model;

import Utilities.Tuple;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;
import java.util.List;

import static Controller.CollisionHandler.getBounds;
import static Model.Robot.GROUND_ROBOT_HEIGHT;
import static Model.StillPlatform.*;
import static Model.MovingPlatform.*;
import static config.GameConstants.*;

public class Stronghold {
    private List<Platform> platforms = new ArrayList<>();
    private List<FurniturePiece> furniture = new ArrayList<>();
    private List<Rectangle2D> areas = new ArrayList<>();
    private List<Entity> entities = new ArrayList<>();
    private PlatformFactory pltFactory = new  PlatformFactory();
    private RobotFactory robotFactory = new  RobotFactory();

    private Agent agent;
    private int currentArea = 1;

    // TODO: se nell'area di una stanza spostare view su quella stanza

    // TODO nella classe stanza costruzione delle piattaforme,
    //  con coordinate rispetto al (MinX,MinY) della stanza

    // TODO: (MinX, MinY) della stanza secondo il sistema delle coordinate delle platforms,
    //  cosi le platforms interne possono usarlo tranquillamente

    public Stronghold() {
        // TODO: se c'hai proprio sbattimento, slidare tutto il
        //  sistema delle coordinate (delle platform) di -0.5

        // creates all the world areas on which the viewport can focus
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                areas.add(new Rectangle2D(
                        j * LOGICAL_WIDTH,
                        i * (ROW_HEIGHT_TILES * STILL_PLATFORM_HEIGHT * 4 + STILL_PLATFORM_HEIGHT),
                        LOGICAL_WIDTH,
                        ROW_HEIGHT_TILES * STILL_PLATFORM_HEIGHT * 4 + STILL_PLATFORM_HEIGHT));
                System.out.println(areas.getLast());
            }
        }

        // map
        createRoom0(0);createLift1(1);createRoom2(2);createLift3(3);
                                createLift6(6);createRoom7(7);createLift8(8);
                                createLift11(11);createRoom12(12);createLift13(13);createRoom14(14);

        for (Platform platform : platforms) {
            if (platform instanceof MovingPlatform) {
                Rectangle2D platformBorder = getBounds(platform);

                for (Rectangle2D area : areas) {
                    if (area.contains(platformBorder)) {
                        ((MovingPlatform) platform).setUpGroup(platforms, area.getMinY());
                    }
                }
            }
        }

        createAgent(13.0,  ROW_HEIGHT - 30, 1);

    }

    private void createLift13(int areaIndex) {
        addHorizontalPlatforms(1, 1, 9, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(1, 0, 9, areaIndex, StillPlatform.class);

        addHorizontalPlatforms(12, 3, 10, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(12, 4, 10, areaIndex, StillPlatform.class);

        addLiftVerticalPlatforms(0, 0, areaIndex, "left");
        addLiftVerticalPlatforms(1, 4, areaIndex, "left");
        addLiftVerticalPlatforms(0, 3,  areaIndex, "right");
        addLiftVerticalPlatforms(4, 4, areaIndex, "right");

        addVerticalPlatforms(1, 4, areaIndex, "left");
        addVerticalPlatforms(0, 0, areaIndex, "left");
        addVerticalPlatforms(0, 3, areaIndex, "right");
        addVerticalPlatforms(4, 4, areaIndex, "right");
    }

    private void createLift8(int areaIndex) {
        addHorizontalPlatforms(1, 1, 9, areaIndex, StillPlatform.class);

        addHorizontalPlatforms(1, 0, 9, areaIndex, StillPlatform.class);

        addLiftVerticalPlatforms(0, 0, areaIndex, "left");
        addLiftVerticalPlatforms(1, 4, areaIndex, "left");
        addLiftVerticalPlatforms(0, 4, areaIndex, "right");

        addVerticalPlatforms(1, 4, areaIndex, "left");
        addVerticalPlatforms(0, 0, areaIndex, "left");
        addVerticalPlatforms(0, 4, areaIndex, "right");
    }

    private void createLift3(int areaIndex) {
        // to be modified when there are all the rooms
        addHorizontalPlatforms(1, 1, 9, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(10, 1, 1, areaIndex, MovingPlatform.class);
        addLiftSlot(platforms.getLast(), 1, "up");
        addLiftSlot(platforms.getLast(), 2, "up");
        addLiftSlot(platforms.getLast(), 2, "down");
        setSlotIndex(platforms.getLast(), 0);

        addHorizontalPlatforms(1, 0, 9, areaIndex, StillPlatform.class);

        addLiftVerticalPlatforms(1, 4, areaIndex, "left");
        addLiftVerticalPlatforms(0, 0, areaIndex, "left");
        addLiftVerticalPlatforms(0, 4, areaIndex, "right");

        addVerticalPlatforms(1, 4, areaIndex, "left");
        addVerticalPlatforms(0, 0, areaIndex, "left");
        addVerticalPlatforms(0, 4, areaIndex, "right");
    }

    private void createLift11(int areaIndex) {
        // should be a different structure but the rooms are not all implemented yet
        //addHorizontalPlatforms(1, 1, 9, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(12, 1, 10,  areaIndex, StillPlatform.class);

        //addHorizontalPlatforms(1, 0, 9, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(12, 0, 10,  areaIndex, StillPlatform.class);

        addLiftVerticalPlatforms(0, 4, areaIndex, "left");
        addLiftVerticalPlatforms(0, 0, areaIndex, "right");
        addLiftVerticalPlatforms(1, 4, areaIndex, "right");

        addVerticalPlatforms(0, 4, areaIndex, "left");
        addVerticalPlatforms(1, 4, areaIndex, "right");
        addVerticalPlatforms(0, 0, areaIndex, "right");    }

    /**
     * Create a lift zone with two upper corridors in the specified zone.
     * @param areaIndex The area in which to create the structure
     */
    private void createLift6(int areaIndex) {
        //addHorizontalPlatforms(1, 4, 9, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(12, 4, 10,  areaIndex, StillPlatform.class);

        //addHorizontalPlatforms(1, 3, 9, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(12, 3, 10,  areaIndex, StillPlatform.class);

        addLiftVerticalPlatforms(0, 4, areaIndex, "left");
        addLiftVerticalPlatforms(0, 3, areaIndex, "right");
        addLiftVerticalPlatforms(4, 4, areaIndex, "right");

        addVerticalPlatforms(0, 4, areaIndex, "left");
        addVerticalPlatforms(0, 3, areaIndex, "right");
        addVerticalPlatforms(4, 4, areaIndex, "right");
    }

    private void createLift1(int areaIndex) {
        addHorizontalPlatforms(1, 1, 9, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(10, 1, 1, areaIndex, MovingPlatform.class);
        addLiftSlot(platforms.getLast(), 1, "down");
        addLiftSlot(platforms.getLast(), 2, "up");
        setSlotIndex(platforms.getLast(), 0);
        addHorizontalPlatforms(12, 1, 10,  areaIndex, StillPlatform.class);

        addHorizontalPlatforms(1, 0, 9, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(12, 0, 10,  areaIndex, StillPlatform.class);

        addLiftVerticalPlatforms(0, 0, areaIndex, "left");
        addLiftVerticalPlatforms(1, 4, areaIndex, "left");
        addLiftVerticalPlatforms(0, 0, areaIndex, "right");
        addLiftVerticalPlatforms(1, 4, areaIndex, "right");

        addVerticalPlatforms(1, 4, areaIndex, "left");
        addVerticalPlatforms(0, 0, areaIndex, "left");
        addVerticalPlatforms(1, 4, areaIndex, "right");
        addVerticalPlatforms(0, 0, areaIndex, "right");
    }

    private void createRoom14(int areaIndex) {
        addHorizontalPlatforms(1, 4, 1, areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), -1);
        setSlotIndex(platforms.getLast(), 1);
        addHorizontalPlatforms(3, 4, 20, areaIndex, StillPlatform.class);

        addHorizontalPlatforms(3, 3, 5, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(10, 3, 2,  areaIndex, StillPlatform.class);
        addHorizontalPlatforms(14, 3, 5, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(19, 3, 1,  areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), -1);
        setSlotIndex(platforms.getLast(), 1);
        addHorizontalPlatforms(21, 3, 1,  areaIndex, StillPlatform.class);

        addHorizontalPlatforms(1, 2, 1, areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), -1);
        setSlotIndex(platforms.getLast(), 1);
        addHorizontalPlatforms(3, 2, 16, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(21, 2, 1, areaIndex, StillPlatform.class);

        addHorizontalPlatforms(3, 1, 2,  areaIndex, StillPlatform.class);
        addHorizontalPlatforms(7, 1, 2,   areaIndex, StillPlatform.class);
        addHorizontalPlatforms(11, 1, 2,  areaIndex, StillPlatform.class);
        addHorizontalPlatforms(16, 1, 6,  areaIndex, StillPlatform.class);

        addVerticalPlatforms(0, 3, areaIndex, "left");
        addVerticalPlatforms(4, 4, areaIndex, "left");
        addVerticalPlatforms(0, 4, areaIndex, "right");

        // ENTITIES
        createMovingRobot(MovingRobot.class, 5, 3, 3, 7, areaIndex);
        createMovingRobot(ShootingRobot.class, 16, 3, 15, 18, areaIndex);
        createMovingRobot(SightRobot.class, 5, 2, 3, 18, areaIndex);

        // FURNITURE
        createTerminal(4, 4, areaIndex);
        createFurniture(5, 2, areaIndex);
        createFurniture(13, 2, areaIndex);
        createFurniture(16, 1, areaIndex);

    }

    private void createRoom2(int areaIndex) {
        addHorizontalPlatforms(1, 1, 9, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(10, 1, 1, areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), 1);
        setSlotIndex(platforms.getLast(), 0);
        addHorizontalPlatforms(12, 1, 4, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(16, 1, 1, areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), 1);
        setSlotIndex(platforms.getLast(), 0);
        addHorizontalPlatforms(18, 1, 4, areaIndex, StillPlatform.class);

        addHorizontalPlatforms(1, 2, 9, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(10, 2, 1, areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), 1);
        setSlotIndex(platforms.getLast(), 0);
        addHorizontalPlatforms(12, 2, 4, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(16, 2, 1, areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), 1);
        setSlotIndex(platforms.getLast(), 0);
        addHorizontalPlatforms(18, 2, 4, areaIndex, StillPlatform.class);

        addHorizontalPlatforms(1, 3, 2, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(3, 3, 1, areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), 1);
        setSlotIndex(platforms.getLast(), 0);
        addHorizontalPlatforms(5, 3, 1, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(6, 3, 1, areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), 1);
        setSlotIndex(platforms.getLast(), 0);
        addHorizontalPlatforms(8, 3, 2, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(12, 3, 4, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(18, 3, 4, areaIndex, StillPlatform.class);

        addHorizontalPlatforms(1, 4, 2,  areaIndex, StillPlatform.class);
        addHorizontalPlatforms(5, 4, 1,  areaIndex, StillPlatform.class);
        addHorizontalPlatforms(8, 4, 14, areaIndex, StillPlatform.class);

        addVerticalPlatforms(0, 0, areaIndex, "left");
        addVerticalPlatforms(1, 4, areaIndex, "left");
        addVerticalPlatforms(0, 0, areaIndex, "right");
        addVerticalPlatforms(1, 4, areaIndex, "right");

        // ENTITIES
        createMovingRobot(MovingRobot.class, 13, 2, 12, 15, areaIndex);
        createStillRobot(5, 3, areaIndex);
        createMovingRobot(ShootingRobot.class, 13, 3, 12, 15, areaIndex);
        createStillRobot(5, 4,  areaIndex);

        // FURNITURE
        createTerminal(13, 1, areaIndex);
        createTerminal(11, 4, areaIndex);
        createFurniture(1, 2, areaIndex);
        createFurniture(5, 2, areaIndex);
        createFurniture(19, 3, areaIndex);
        setLastFurniture(FurnitureType.POP_MACHINE);
        createFurniture(16, 4, areaIndex);


    }

    private void createRoom0(int areaIndex) {
        // entrance row 1
        addHorizontalPlatforms(1, 1, 9, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(10, 1, 1, areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), 1);
        setSlotIndex(platforms.getLast(), 0);
        addHorizontalPlatforms(12, 1, 10,  areaIndex, StillPlatform.class);

        addHorizontalPlatforms(1, 2, 4, areaIndex,  StillPlatform.class);
        addHorizontalPlatforms(5, 2, 1, areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), 1);
        setSlotIndex(platforms.getLast(), 0);
        addHorizontalPlatforms(7, 2, 3, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(12, 2, 3, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(15, 2, 1, areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), 1);
        setSlotIndex(platforms.getLast(), 0);
        addHorizontalPlatforms(17, 2, 5, areaIndex, StillPlatform.class);

        addHorizontalPlatforms(1, 3, 4, areaIndex,  StillPlatform.class);
        addHorizontalPlatforms(5, 3, 1, areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), 1);
        setSlotIndex(platforms.getLast(), 0);
        addHorizontalPlatforms(7, 3, 8, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(15, 3, 1, areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), 1);
        setSlotIndex(platforms.getLast(), 0);
        addHorizontalPlatforms(17, 3, 5, areaIndex, StillPlatform.class);

        addHorizontalPlatforms(1, 4, 4, areaIndex,  StillPlatform.class);
        addHorizontalPlatforms(7, 4, 8, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(17, 4, 5, areaIndex, StillPlatform.class);

        addVerticalPlatforms(0, 4, areaIndex, "left");
        addVerticalPlatforms(1, 4, areaIndex, "right");

        // ENTITIES
        createMovingRobot(ShootingRobot.class, 3, 2, 1, 4, areaIndex);
        createMovingRobot(ShootingRobot.class, 18, 2, 17, 20, areaIndex);
        createMovingRobot(SightRobot.class, 10, 3, 7, 14, areaIndex);
        createMovingRobot(MovingRobot.class, 10, 4, 7, 14, areaIndex);

        // FURNITURE
        createTerminal(1, 1, areaIndex);
        createFurniture(7, 3, areaIndex);
        createFurniture(11, 3, areaIndex);
        createFurniture(10, 4, areaIndex);

    }

    private void createRoom12(int areaIndex) {
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
        addHorizontalPlatforms(20, 1, 2, areaIndex, StillPlatform.class);


        addHorizontalPlatforms(5, 2, 5, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(16, 2, 2, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(20, 2, 2, areaIndex, StillPlatform.class);

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
        addHorizontalPlatforms(20, 3, 2, areaIndex, StillPlatform.class);

        addHorizontalPlatforms(14, 3.5, 2, areaIndex, StillPlatform.class);

        addHorizontalPlatforms(3, 4, 7, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(16, 4, 2, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(20, 4, 2, areaIndex, StillPlatform.class);

        addVerticalPlatforms(0, 0, areaIndex, "left");
        addVerticalPlatforms(1, 4,  areaIndex, "left");
        addVerticalPlatforms(0, 0, areaIndex, "right");
        addVerticalPlatforms(1, 4,  areaIndex, "right");


        // ETITIES
        createMovingRobot(SightRobot.class, 9, 2, 5, 9, areaIndex);
        createStillRobot(9, 3, areaIndex);

        // FURNITURE
        createTerminal(6, 1, areaIndex);
        createFurniture(5, 2, areaIndex);
        createFurniture(5, 3, areaIndex);
        createEndRoom(5, 4, areaIndex);

    }

    private void createRoom7(int areaIndex) {
        addHorizontalPlatforms(1, 1, 10, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(13, 1, 3, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(18, 1, 4, areaIndex, StillPlatform.class);

        addHorizontalPlatforms(3, 2, 3, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(8,  2, 3, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(11,  2, 1, areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), -1);
        setSlotIndex(platforms.getLast(), 1);

        addHorizontalPlatforms(13,  2, 3, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(16,  2, 1, areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), -1);
        setSlotIndex(platforms.getLast(), 1);
        addHorizontalPlatforms(18,  2, 4, areaIndex, StillPlatform.class);

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
        addHorizontalPlatforms(18,  3, 4, areaIndex, StillPlatform.class);

        addHorizontalPlatforms(1,  4, 1, areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), -1);
        setSlotIndex(platforms.getLast(), 1);
        addHorizontalPlatforms(3,  4, 3, areaIndex, StillPlatform.class);
        addHorizontalPlatforms(6,  4, 1, areaIndex, MovingPlatform.class);
        addSlot(platforms.getLast(), -1);
        setSlotIndex(platforms.getLast(), 1);
        addHorizontalPlatforms(8,  4, 14, areaIndex, StillPlatform.class);

        addVerticalPlatforms(0,  3, areaIndex,"left");
        addVerticalPlatforms( 4,  4, areaIndex,"left");
        addVerticalPlatforms(0, 0, areaIndex,"right");
        addVerticalPlatforms(1,   4, areaIndex,"right");

        createStillRobot(5, 1, areaIndex);
        createMovingRobot(MovingRobot.class, 14, 1, 13, 15, areaIndex);
        createMovingRobot(SightRobot.class, 14, 4, 8, 20, areaIndex);
        createMovingRobot(ShootingRobot.class, 3, 4, 3, 5, areaIndex);

        createFurniture(1, 1, areaIndex);
        createFurniture(6, 1, areaIndex);
        createFurniture(8, 4, areaIndex);
        createFurniture(16, 4, areaIndex);
    }

    private void createStillRobot(int x, int y, int areaIndex) {
        entities.add(new StillRobot(new Tuple<>(
                (double) x * STILL_PLATFORM_WIDTH + areas.get(areaIndex).getMinX(),
                (double) y * ROW_HEIGHT_TILES * STILL_PLATFORM_HEIGHT -
                        GROUND_ROBOT_HEIGHT + 1 + areas.get(areaIndex).getMinY()
        )));
    }

    /**
     * It creates and adds to entities an object of type
     * that is MovingRobot or that extends MovingRobot.
     * @param x The X platform tile on which the robot will spawn.
     * @param y The Y platform tile on which the robot will spawn.
     * @param leftBound The leftmost platform on which the robot will stop
     * @param rightBound The rightmost platform on which the robot will stop
     */
    public <T extends MovingRobot> void createMovingRobot(Class<T> clazz, int x, int y, int leftBound, int rightBound, int areaIndex) {
        double sX = x * STILL_PLATFORM_WIDTH + areas.get(areaIndex).getMinX();
        double sY = y * ROW_HEIGHT_TILES * STILL_PLATFORM_HEIGHT -
                GROUND_ROBOT_HEIGHT + 1 + areas.get(areaIndex).getMinY();

        double sRB = rightBound * STILL_PLATFORM_WIDTH + areas.get(areaIndex).getMinX();
        double sLB = leftBound * STILL_PLATFORM_WIDTH + areas.get(areaIndex).getMinX();

        entities.add(robotFactory.createMovingRobot(clazz, sX, sY, sLB, sRB));
        // the ShootingRobot as a "companion" entity, its projectile,
        // so it needs to be added
        if (clazz == ShootingRobot.class)
            entities.add((((ShootingRobot)entities.getLast()).getPlasmaBolt()));;
    }

    /**
     * Function that creates a new agent. Used for every respawn instance.
     * @return Returns the new agent that the model uses
     */
    public void createAgent(double x, double y, int areaIndex) {
        // TODO: [RESPAWN] luogo respawn rispetto a porta di entrata
        agent = new Agent(new Tuple<>(
                x + areas.get(areaIndex).getMinX(),
                y + areas.get(areaIndex).getMinY()
        ));
    }

    public void setSlotIndex(Platform platform, int slotIndex) {
        ((MovingPlatform)platform).setSlotIndex(slotIndex);
    }

    public void createFurniture(int x, int y, int areaIndex) {
        x = x * STILL_PLATFORM_WIDTH + (int) areas.get(areaIndex).getMinX();
        y = y * ROW_HEIGHT_TILES * STILL_PLATFORM_HEIGHT + (int) areas.get(areaIndex).getMinY();
        furniture.add(new FurniturePiece(new Tuple<>((double) x, (double) y)));
    }

    public void setLastFurniture(FurnitureType type) { furniture.getLast().setType(type); }

    public void createTerminal(int x, int y, int areaIndex) {
        createFurniture(x, y, areaIndex);
        furniture.getLast().setType(FurnitureType.TERMINAL);
    }

    public void createEndRoom(int x, int y, int areaIndex) {
        createFurniture(x, y, areaIndex);
        furniture.getLast().setType(FurnitureType.END_ROOM);
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
        upperY = upperY * ROW_HEIGHT_TILES;
        lowerY = lowerY * ROW_HEIGHT_TILES;
        int sUpper = upperY * STILL_PLATFORM_HEIGHT + (int)areas.get(areaIndex).getMinY();
        for (int i = 0; i <= lowerY - upperY; i++) {
            Tuple<Double, Double> pos = new Tuple<>(
                     (side.equals("left") ? areas.get(areaIndex).getMinX() : (areas.get(areaIndex).getMaxX() - STILL_PLATFORM_WIDTH)),
                    (double) sUpper + STILL_PLATFORM_HEIGHT * i);
            platforms.add(pltFactory.createPlatform(
                    Wall.class, pos.getFirst(), pos.getSecond()
            ));
        }
    }

    private <T extends Platform> void addLiftVerticalPlatforms(int upperY, int lowerY, int areaIndex, String side) {
        upperY = upperY * ROW_HEIGHT_TILES;
        lowerY = lowerY * ROW_HEIGHT_TILES;
        int sUpper = upperY * STILL_PLATFORM_HEIGHT + (int)areas.get(areaIndex).getMinY();

        for (int i = 0; i <= lowerY - upperY; i++) {
            Tuple<Double, Double> pos = new Tuple<>(
                    (areas.get(areaIndex).getMinX() + STILL_PLATFORM_WIDTH * (side.equals("left") ? 9 : 12)),
                    (double) sUpper + STILL_PLATFORM_HEIGHT * i);

            if (areas.get(areaIndex).getMinY() != 0) {
                System.out.println("Offet area Y:" + areas.get(areaIndex).getMinY() + "\nPosizione piattaforma Y: " + (sUpper + STILL_PLATFORM_HEIGHT * i));
            }

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


    private void addLiftSlot(Platform platform, int areaY, String pos) {
        double areaOffset = areaY * areas.get(areaY).getHeight();

        double offsetY = pos.equals("up") ? ROW_HEIGHT_TILES * STILL_PLATFORM_HEIGHT : ROW_HEIGHT_TILES * STILL_PLATFORM_HEIGHT * 4;

        ((MovingPlatform)platforms.getLast()).addVerticalSlot(
                platform.getPosition().getFirst(),
                offsetY +  areaOffset
        );

    }

    public void moveAgent(Direction direction, Double deltaTime) {
        agent.moveTo(direction, deltaTime);
    }

    public void addEntity(Entity entity) { entities.add(entity); }

    public void setUsingLift(boolean usingLift) { agent.setUsingLift(usingLift); }

    public void setCurrentArea(int i) { currentArea = i; }

    public boolean isUsingLift() { return agent.isUsingLift(); }

    public List<Rectangle2D> getAreas() { return areas; }

    public int getCurrentAreaIndex() { return currentArea; }

    public Rectangle2D getCurrentArea() { return areas.get(currentArea); }

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
