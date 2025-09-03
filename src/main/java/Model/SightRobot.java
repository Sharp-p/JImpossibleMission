package Model;

import Utilities.Tuple;
import javafx.geometry.Rectangle2D;

import static Controller.CollisionHandler.getBounds;
import static Model.StillPlatform.STILL_PLATFORM_WIDTH;

public class SightRobot extends MovingRobot {
    public static final double UPGRADED_SPEED_FACTOR = 1.8;

    private Rectangle2D fieldOfView;
    private boolean inSight = false;

    public SightRobot(Tuple<Double, Double> position) {
        super(position);
        updateFieldOfView();
    }

    public SightRobot(Tuple<Double, Double> position, double leftBoundX, double rightBoundX) {
        super(position);
        setBounds(leftBoundX, rightBoundX);
        updateFieldOfView();
    }

    private void updateFieldOfView() {
        // if is turning it behaves as if it isn't watching in any direction
        if (isTurning()) {
            fieldOfView = getBounds(this);
        }
        else if (getDirection() == Direction.RIGHT) {
            fieldOfView = new Rectangle2D(
                    getPosition().getFirst() + GROUND_ROBOT_WIDTH,
                    getPosition().getSecond() + GROUND_ROBOT_HEIGHT / 2.0,
                    STILL_PLATFORM_WIDTH * 20,
                    3
            );
        }
        else {
            fieldOfView = new Rectangle2D(
                    getPosition().getFirst() - STILL_PLATFORM_WIDTH * 20,
                    getPosition().getSecond() + GROUND_ROBOT_HEIGHT / 2.0,
                    STILL_PLATFORM_WIDTH * 20,
                    3
            );
        }
    }


    public void update(double deltaTime, Agent agent) {
        Rectangle2D agentBorders = getBounds(agent);
        Rectangle2D robotBorders = getBounds(this);
        // if it intersects with the agent I'm surely
        // not turning since it is "blind" when turning
        if (fieldOfView.intersects(agentBorders)) {
            if (!inSight) {
                setSpeed(SPEED * UPGRADED_SPEED_FACTOR);
                inSight = true;
            }
            // in my direction there is an agent
            // if I'm not on a bound I move in my direction
            if (!((robotBorders.intersects(getLeftBound()) && getDirection() == Direction.LEFT) ||
                    (robotBorders.intersects(getRightBound()) && getDirection() == Direction.RIGHT))) {
                moveTo(getDirection(), deltaTime);
            }
        }
        // if there isn't an agent in my field of view I behave like a normal moving robot
        else {
            if (inSight) {
                inSight = false;
                setSpeed(SPEED / UPGRADED_SPEED_FACTOR);
            }
            update(deltaTime);
        }
        updateFieldOfView();
    }
}
