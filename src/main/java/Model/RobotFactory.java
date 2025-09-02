package Model;

import Utilities.Tuple;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class RobotFactory extends EntityFactory {
    // to be modified  when a constructor of a robot si modified

    /**
     * Create an object of type Robot (or that extends Robot).
     * If the Class passed is of type MovingRobot the object
     * will still need its bounds to be set (see createMovingRobot).
     * @param clazz The Class object that represents the type of Robot
     * @param x The global X coordinate
     * @param y The global Y coordinate
     * @return Returns an object as specified in the parameters
     * @param <T> T is a generic type that extends Robot
     */
    public <T extends Robot> T createRobot(
            Class<T> clazz,
            double x, double y) {
     return create(clazz, x, y);
    }

    /**
     * Create an object of type MovingRobot (or that extends MovingRobot).
     * @param clazz The Class object that represents the type of MovingRobot
     * @param x The global X coordinate
     * @param y The global Y coordinate
     * @param leftBound The left boundary of the robot on the X axis
     * @param rightBound The right boundary of the robot on the X axis
     * @return Returns an object as specified in the parameters
     * @param <T> T is a generic type that extends MovingRobot
     */
    public <T extends MovingRobot> T createMovingRobot(
            Class<T> clazz,
            double x,
            double y,
            double leftBound,
            double rightBound) {
        Tuple<Double, Double> position = new Tuple<>(x, y);
        try {
            Constructor<T> constructor = clazz.getConstructor(
                    Tuple.class,
                    double.class,
                    double.class);
            return constructor.newInstance(
                    position,
                    leftBound,
                    rightBound);
        }
        catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
