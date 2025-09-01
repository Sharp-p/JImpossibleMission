package Model;

public enum FurnitureType {
    POP_MACHINE(12,21),
    CIG_MACHINE(39, 31),
    CANDY_MACHINE(32, 31),
    BATH_TUB(47, 31),
    WC(32, 16),
    SINK(30, 32),
    DESK(55, 24),
    TYPEWRITER(24, 20),
    DRAWER(20, 20),
    BED(48, 11),
    KITCHEN(45, 23),
    FRIDGE(24, 31),
    FIREPLACE(69, 28),
    SOFA(42, 16),
    BOOKSHELF(23, 36),
    TERMINAL(24, 22),
    END_ROOM(43, 38);

    private final int width;
    private final int height;

    FurnitureType(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() { return width; }

    public int getHeight() { return height; }
}
