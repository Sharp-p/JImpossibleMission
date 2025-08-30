package Model;

import Utilities.Tuple;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class RobotFactory extends EntityFactory {
    // to be modified  when a constructor of a robot si modified
    public <T extends Robot> T createRobot(
            Class<T> clazz,
            double x, double y) {
     return createEntity(clazz, x, y);
    }
}
