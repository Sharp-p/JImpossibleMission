package Model;

import Utilities.Tuple;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class PlatformFactory extends EntityFactory {
    // to be modified  when a constructor of a platform si modified
    public <T extends Platform> T createPlatform(
            Class<T> clazz,
            double x, double y) {
        return create(clazz, x, y);
    }
}