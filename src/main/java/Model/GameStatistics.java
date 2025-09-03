package Model;

public class GameStatistics {
    private int pswPiecesFound = 0;
    private int totalPswPieces = 0;
    private int stopRobotsCode = 0;
    private int resetPlatformsCode = 0;
    private double score = 0.0;
    private GameClock gameClock = new GameClock();
    private boolean showingStatistics = false;
    private boolean won = false;

    /**
     * Elaborates the final score as the sum of:
     * the score from the searched furniture, number of pswPiecesFound * 1000, resetPlatformsCode * 500, stopRobotsCode * 200,
     * and if won 2 times all the seconds remaining.
     * @return The score as an int
     */
    public int getFinalScore() {
        return (int)score +
                ((3600 * 18) - gameClock.getSeconds()) * (won ? 2 : 0) +
                pswPiecesFound * 1000 +
                resetPlatformsCode * 500 +
                stopRobotsCode * 200;
    }

    public GameStatistics() {
        gameClock.start();
    }

    public void addTime(int seconds) { gameClock.addSeconds(seconds); }

    public void foundPswPiece() { pswPiecesFound++; }

    public void addTotalPswPieces() { totalPswPieces++; }

    public void setShowingStatistics(boolean showingStatistics) { this.showingStatistics = showingStatistics; }

    public void consumeRobotsCode() { stopRobotsCode--; }

    public void consumePlatformsCode() { resetPlatformsCode--; }

    public void addRobotsCode() { stopRobotsCode++; }

    public void addPlatformsCode() { resetPlatformsCode++; }

    public void addScore(double points) { this.score += points; }

    public double getScore() { return score; }

    public void setWon(boolean won) { this.won = won; }

    public int getSeconds() { return gameClock.getSeconds();}

    public boolean getWon() { return won; }

    public GameClock getGameClock() { return gameClock; }

    public int getRobotsCodeTot() { return stopRobotsCode; }

    public int getPlatformsCodeTot() { return resetPlatformsCode; }

    public int getTotalPswPieces() { return totalPswPieces; }

    public int getPswPiecesFound() { return pswPiecesFound; }

    public boolean isShowingStatistics() { return showingStatistics; }
}
