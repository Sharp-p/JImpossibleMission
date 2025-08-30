package Model;

import Utilities.Tuple;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class PlatformFactory {
    // to be modified  when a constructor of a platform si modified
    public <T extends Platform> T createPlatform(
            Class<T> clazz,
            Tuple<Double, Double> position) {
        try {
            Constructor<T> contructor = clazz.getConstructor(Tuple.class);
            return contructor.newInstance(position);
        }
        catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
