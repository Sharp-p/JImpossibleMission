package Model;

import Utilities.Tuple;

import java.util.ArrayList;
import java.util.List;

public class Platform extends Entity{
    private List<Double> verticalSlots = new ArrayList<>();
    private int slotIndex = 0;

    public Platform(Tuple<Double, Double> position,
                    MovementBehavior movementBehav) {
        super(position, movementBehav, 40.0);
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



}
