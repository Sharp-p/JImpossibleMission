package Model;

import Utilities.Tuple;

public class FurniturePiece extends Entity {
    public static final double MAX_SEARCH_TIME = 4.0;
    private FurnitureType type;
    private Code code = new Code();

    private boolean beingSearched = false;
    private double searchTime;

    // TODO: aggiungere punteggio quando consumato

    public FurniturePiece(Tuple<Double, Double> position) {
        super(position, new StillMovement(), 0.0);

        // chooses a random search time
        searchTime = Math.random() * 3.5 + 0.5;

        // chooses a random Type
        FurnitureType[] values = FurnitureType.values();

        int index = (int) (Math.random() * (values.length - 1));

        type = values[index];
    }

    public FurniturePiece(Tuple<Double, Double> position, FurnitureType type) {
        this(position);
        this.type = type;
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
            System.out.println("HAI TROVATO IL SEGUENTE CODE: " + code.getType());
            beingSearched = false;
        }
    }

    @Override
    public String toString() {
        return "Tipo: " + type + "\n\tCode: " + code.getType();
    }

    public void moveTo(Direction dir, Double deltaTime) {}

    public boolean isBeingSearched() { return beingSearched; }

    public void setType(FurnitureType type) { this.type = type; }

    public Code getCode() { return code; }

    public double getSearchTime() { return searchTime; }

    public FurnitureType getType() { return type;}

    public void setCode(Code code) { this.code = code; }

    public void setSearching(boolean searching) { this.beingSearched = searching; }
}
