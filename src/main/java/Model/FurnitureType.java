package Model;

public enum FurnitureType {
    POP_MACHINE(17,21),
    CIG_MACHINE(41, 31),
    CANDY_MACHINE(34, 31),
    BATH_TUB(49, 31),
    WC(34, 16),
    SINK(32, 32),
    DESK(57, 24),
    TYPEWRITER(26, 19),
    DRAWER(22, 20),
    BED(50, 11),
    KITCHEN(47, 23),
    FRIDGE(26, 31),
    FIREPLACE(71, 28),
    SOFA(44, 16),
    BOOKSHELF(25, 36),
    TERMINAL(26, 22),
    END_ROOM(45, 38);

    private final int width;
    private final int height;

    FurnitureType(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() { return width; }

    public int getHeight() { return height; }
}
