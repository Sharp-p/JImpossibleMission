package Model;

import Utilities.Tuple;

public class FurniturePiece extends Entity {
    public static final double MAX_SEARCH_TIME = 4.0;
    private final FurnitureType type;

    private boolean beingSearched = false;
    private double searchTime;

    public FurniturePiece(Tuple<Double, Double> position) {
        super(position, new StillMovement(), 0.0);
        searchTime = Math.random() * 3.5 + 0.5;

        FurnitureType[] values = FurnitureType.values();

        int index = (int) (Math.random() * (values.length - 1));

        type = values[index];
    }

    public void use(double deltaTime) {
        // TODO: forse va controllato prima se è attiva
        if (searchTime >= 0.0) {
            searchTime -= deltaTime;
            beingSearched = true;
        }
        else {
            setActive(false);
            setVisibility(false);
            beingSearched = false;
        }
    }


    public void moveTo(Direction dir, Double deltaTime) {}

    public boolean isBeingSearched() { return beingSearched; }

    public double getSearchTime() { return searchTime; }

    public FurnitureType getType() { return type;}

    public void setSearching(boolean searching) { this.beingSearched = searching; }
}
