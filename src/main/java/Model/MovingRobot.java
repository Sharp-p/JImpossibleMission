package Model;

import Utilities.Tuple;
import javafx.geometry.Rectangle2D;

import static Controller.CollisionHandler.getBounds;
import static Model.StillPlatform.STILL_PLATFORM_WIDTH;

public class MovingRobot extends Robot {
    public static final double SPEED = 64.28;


    private Rectangle2D leftBound;
    private Rectangle2D rightBound;

    public MovingRobot(Tuple<Double, Double> position) {
        super(position, new HorizontalMovement(), SPEED);
    }

    /**
     * Requires the X coordinate of the left most and
     * right most platform on which to stop on
     * @param minX X of left most platform
     * @param maxX X of right most platform
     */
    public void setBounds(double minX, double maxX) {
        leftBound = new Rectangle2D(
                minX,
                getPosition().getSecond(),
                4,
                GROUND_ROBOT_HEIGHT);
        rightBound = new Rectangle2D(
                maxX + STILL_PLATFORM_WIDTH - 2,
                getPosition().getSecond(),
                4,
                GROUND_ROBOT_HEIGHT);
    }

    @Override
    public void update(double deltaTime) {
        Rectangle2D robotBorders = getBounds(this);
        if ((robotBorders.intersects(leftBound) && getDirection() == Direction.LEFT) ||
                (robotBorders.intersects(rightBound) && getDirection() == Direction.RIGHT)) {
            setHasTurned(true);
            setTurning(true);
            setDirection(switchDirection());
        }
        else if (!isTurning()) {
            moveTo(getDirection(), deltaTime);
        }
    }

    @Override
    public void moveTo(Direction dir, Double deltaTime) {
        getMovementBehavior().move(this, dir, deltaTime);
    }
}
