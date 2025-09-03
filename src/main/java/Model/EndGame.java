package Model;

public class EndGame {
    public static double WARNING_TIME = 3.0;
    private EndGameStatus status = EndGameStatus.NONE;
    private double timer = 0.0;
    public void setStatus(EndGameStatus endGameStatus) { this.status = endGameStatus; }

    public EndGameStatus getStatus() { return this.status; }

    /**
     * Add the given delta time, and if it reaches the target time it returns true.
     * If the return is ignored, the methods already update the end game status.
     * @param deltaTime A time span
     * @return A boolean that notifies with true that it reached the target time
     */
    public boolean addTime(double deltaTime) {
        timer += deltaTime;
        if (timer >= WARNING_TIME) {
            timer = 0.0;
            status = EndGameStatus.NONE;
            return true;
        }
        return false;
    }
}
