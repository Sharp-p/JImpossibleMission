package Model;

import Utilities.Tuple;

import java.util.ArrayList;
import java.util.List;

// TODO: dividere in 3 sottoclassi, still platforms, vertical platforms, lift
public abstract class Platform extends Entity{
    // TODO: utiilizzare il campo size di entity
    private List<Double> verticalSlots = new ArrayList<>();
    private int slotIndex = 0;

    public Platform(Tuple<Double, Double> position,
                    MovementBehavior movementBehav, Double speed, int width, int height) {
        super(position, movementBehav, speed);

        setSize(new Tuple<>((double) width, (double) height));
    }

    public void moveTo(Direction dir, Double deltaTime) {
        getMovementBehavior().move(this, dir, deltaTime);
    }

    public int getSlotIndex() { return slotIndex; }

    public List<Double> getVerticalSlots() { return verticalSlots; }

    public void setSlotIndex(int slotIndex) { this.slotIndex = slotIndex; }

    public void nextSlot() {
        if (slotIndex < verticalSlots.size() - 1) slotIndex = (slotIndex + 1);
    }

    public void prevSlot() { if (slotIndex > 0) slotIndex = (slotIndex - 1); }

    @Override
    public String toString() {
        return "Piattaforma:\n" +
            "\tTipo: " + this.getClass() + "\n" +
            "\tMovimento: " + getMovementBehavior().getClass();
    }

}
