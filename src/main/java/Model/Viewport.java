package Model;

public class Viewport {
    private double cameraX;
    private double cameraY;
    private boolean staleVP = true;

    public Viewport(int x, int y) {
        cameraX = x;
        cameraY = y;
    }

    public void setStaleVP(boolean staleVP) { this.staleVP = staleVP; }

    public void setCameraX(int cameraX) { this.cameraX = cameraX; }

    public void setCameraY(int cameraY) { this.cameraY = cameraY; }

    public boolean isVPStale() { return staleVP; }

    public  double getCameraX() { return cameraX; }

    public double getCameraY() { return cameraY; }
}
