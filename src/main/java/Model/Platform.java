package Model;

import Utilities.Tuple;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;
import java.util.List;

public abstract class Platform extends Entity{
    public Platform(Tuple<Double, Double> position,
                    MovementBehavior movementBehav, Double speed, int width, int height) {
        super(position, movementBehav, speed);

        setSize(new Tuple<>((double) width, (double) height));
    }

    public void moveTo(Direction dir, Double deltaTime) {
        getMovementBehavior().move(this, dir, deltaTime);
    }

    @Override
    public String toString() {
        return "Piattaforma:\n" +
            "\tTipo: " + this.getClass() + "\n" +
            "\tMovimento: " + getMovementBehavior().getClass();
    }

}
