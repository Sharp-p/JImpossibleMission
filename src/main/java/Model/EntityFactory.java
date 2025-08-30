package Model;

import Utilities.Tuple;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class EntityFactory {
    public <T extends Entity> T createEntity(
            Class<T> clazz,
            double x, double y) {
        Tuple<Double, Double> position = new Tuple<>(x, y);
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
