package Model;

public class GameStatistics {
    private int pswPiecesFound = 0;
    private int totalPswPieces = 0;
    private int stopRobotsCode = 0;
    private int resetPlatformsCode = 0;
    private double score = 0.0;
    private GameClock gameClock = new GameClock();
    private boolean showingStatistics = false;
    private boolean ended = false;
    private boolean won = false;

    // TODO: statistiche stanze appena ho le stanze

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

    public void setEnded(boolean ended) { this.ended = ended; }

    public void setWon(boolean won) { this.won = won; }

    public int getSeconds() { return gameClock.getSeconds();}

    public boolean getEnded() { return ended; }

    public boolean getWon() { return won; }

    public GameClock getGameClock() { return gameClock; }

    public int getRobotsCodeTot() { return stopRobotsCode; }

    public int getPlatformsCodeTot() { return resetPlatformsCode; }

    public int getTotalPswPieces() { return totalPswPieces; }

    public int getPswPiecesFound() { return pswPiecesFound; }

    public boolean isShowingStatistics() { return showingStatistics; }
}
