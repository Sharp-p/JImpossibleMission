package Model;

public class GameModel {
    private int playerX = 100;
    private int playerY = 100;

    public void movePlayer(int dx, int dy) {
        playerX += dx;
        playerY += dy;
    }

    public int getPlayerX() { return playerX; }
    public int getPlayerY() { return playerY; }

}
