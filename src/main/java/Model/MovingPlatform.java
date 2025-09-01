package Model;

import Utilities.Tuple;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;
import java.util.List;

import static config.GameConstants.LOGICAL_WIDTH;
import static java.util.stream.Collectors.toList;

public class MovingPlatform extends Platform {
    public static final int MOVING_PLATFORM_WIDTH = 26;
    public static final int MOVING_PLATFORM_HEIGHT = 7;
    public static final double SPEED = 100;

    private List<Rectangle2D> verticalSlots = new ArrayList<>();
    private int slotIndex = 0;
    private List<MovingPlatform> group = new ArrayList<>();

    public MovingPlatform(Tuple<Double, Double> position) {
        super(position, new VerticalMovement(), SPEED, 26, 7);
        verticalSlots.add(
                new Rectangle2D(
                        getPosition().getFirst(),
                        getPosition().getSecond() + 3,
                        MOVING_PLATFORM_WIDTH,
                        getSize().getSecond())
        );
    }

    private boolean isCandidate(Platform platform) {
        Rectangle2D groupBorder = new Rectangle2D(
                getPosition().getFirst() + (MOVING_PLATFORM_WIDTH / 2),
                0.0,
                2.0,
                MOVING_PLATFORM_HEIGHT * (LOGICAL_WIDTH / MOVING_PLATFORM_HEIGHT));

        Rectangle2D platformBorder = new Rectangle2D(
                platform.getPosition().getFirst(),
                platform.getPosition().getSecond(),
                platform.getSize().getFirst(),
                platform.getSize().getSecond()
        );

        return platform instanceof MovingPlatform && groupBorder.intersects(platformBorder) && platform != this;
    }

    public void setUpGroup(List<Platform> platforms) {
        List<Platform> candiates = platforms.stream()
                .filter(this::isCandidate).toList();

        for (Platform platform : candiates) { addToGroup((MovingPlatform) platform); }

    }

    public void addToGroup(MovingPlatform movingPlatform) { group.add(movingPlatform); }

    public List<MovingPlatform> getGroup() { return group; }

    public int getSlotIndex() { return slotIndex; }

    public void addVerticalSlot(double x, double y) {
        verticalSlots.add(new Rectangle2D(x, y + 3, MOVING_PLATFORM_WIDTH, 1));
    }

    public List<Rectangle2D> getVerticalSlots() { return verticalSlots; }

    /**
     * Sets the slot on which the platform is positioned. The highest one must be 0
     * @param slotIndex
     */
    public void setSlotIndex(int slotIndex) { this.slotIndex = slotIndex; }

    public boolean nextSlot() {
        if (slotIndex < verticalSlots.size() - 1) {
            slotIndex = (slotIndex + 1);
            for (MovingPlatform movingPlatform : group) {
                movingPlatform.setSlotIndex(slotIndex);
            }
            return true;
        }
        return false;
    }

    public boolean prevSlot() {
        if (slotIndex > 0) {
            slotIndex = (slotIndex - 1);
            for (MovingPlatform movingPlatform : group) {
                movingPlatform.setSlotIndex(slotIndex);
            }
            return true;
        }
        return false;
    }

    public boolean checkMovementDirection(Direction dir) {
        switch (dir) {
            case UP -> {
                return nextSlot();
            }
            case DOWN -> {
                return prevSlot();
            }
        }
        return false;
    }

}
