package Model;

import java.util.*;

import javafx.geometry.Rectangle2D;

/**
 * An interface for all the models in the game environment.
 * To act as a tunnel to all other model it incorporates the other models methods.
 */
public class GameModel extends Observable {
    private static final double DISABLE_ROBOTS_TIME = 15.0;

    private GameStatistics statistics = new GameStatistics();
    private Stronghold stronghold;
    private Viewport viewport;
    private GameMenuModel gameMenuModel;
    private Terminal terminal;
    private boolean isPaused = false;
    private boolean robotsDisabled = false;
    private double disableBotsTimer = 0.0;


    // TODO: timer che si aggiorna ad ogni morte

    public GameModel() {
        stronghold = new Stronghold();

        viewport = new Viewport(
                (int)stronghold.getCurrentArea().getMinX(),
                (int)stronghold.getCurrentArea().getMinY());

        gameMenuModel = new GameMenuModel();
        terminal = new Terminal();

        setChanged();
        notifyObservers();
    }

    /**
     * Method that check and update the disabled timer.
     * To only get the value of robotsDisabled see areRobotsDisabled()
     * @param deltaTime The duration of the last time step
     * @return Return the new value of robotsDisabled
     */
    public boolean checkDisabled(double deltaTime) {
        if (areRobotsDisabled()) {
            if (disableBotsTimer > DISABLE_ROBOTS_TIME) {
                disableBotsTimer = 0;
                robotsDisabled = false;
                return false;
            }

            disableBotsTimer += deltaTime;
            return true;
        }
        return false;
    }

    /**
     * This method will reset all game entities (platforms and furniture excluded) to their last spawn position.
     */
    public void resetPositions() {
        for (Entity entity : getEntities()) {
            if (!(entity instanceof Platform) && !(entity instanceof FurniturePiece)) {
                System.out.println(entity);
                entity.setPosition(entity.getSpawn());
            }
        }
    }

    /**
     * For now, it updates only the physics of the agent
     * @param dt
     */
    public void applyPhysics(double dt) {
        getAgent().applyGravity(dt);
    }

    public void updated(double dt) {
        setChanged();
        notifyObservers(dt);
    }

    public void setRobotsDisabled(boolean robotsDisabled) { this.robotsDisabled = robotsDisabled; }

    public boolean areRobotsDisabled() { return robotsDisabled; }

    public void foundPswPiece() { statistics.foundPswPiece(); }

    public void addTotalPswPieces() { statistics.addTotalPswPieces(); }

    public void consumeRobotsCode() { statistics.consumeRobotsCode(); }

    public void consumePlatformsCode() { statistics.consumePlatformsCode(); }

    public void addRobotsCode() { statistics.addRobotsCode(); }

    public void addPlatformsCode() { statistics.addPlatformsCode(); }


    /**
     * Function that creates a new agent. Used for every respawn instance.
     * @return Returns the new agent that the model uses
     */
    public void createAgent(double x, double y, int areaIndex) { stronghold.createAgent(x, y, areaIndex); }

    public void moveAgent(Direction direction, Double deltaTime) { stronghold.moveAgent(direction, deltaTime); }

    public GameModel newModel() { return new GameModel(); }

    public void addTime(int seconds) { statistics.addTime(seconds); }

    public void setCurrentArea(int i) { stronghold.setCurrentArea(i); }

    public void addEntity(Entity entity) { stronghold.addEntity(entity); }

    public void setPaused(boolean paused) { isPaused = paused; }

    public void setUsingLift(boolean usingLift) { stronghold.setUsingLift(usingLift); }

    public void setShowingStatistics(boolean showingStatistics) {  statistics.setShowingStatistics(showingStatistics); }

    public boolean isShowingStatistics() { return statistics.isShowingStatistics(); }

    public boolean isPaused() { return isPaused; }

    public void setStaleVP(boolean staleVP) { viewport.setStaleVP(staleVP); }

    public void setCameraX(int cameraX) { viewport.setCameraX(cameraX); }

    public void setCameraY(int cameraY) { viewport.setCameraY(cameraY); }

    public void setEnded(boolean ended) { statistics.setEnded(ended); }

    public void setWon(boolean won) { statistics.setWon(won); }

    public void nextMenuOption() { gameMenuModel.next(); }

    public GameMenuModel getGameMenuModel() { return gameMenuModel; }

    public int getGameMenuSelection() { return gameMenuModel.getSelectedIndex(); }

    public void previousMenuOption() { gameMenuModel.previous(); }

    public void addScore(double points) { statistics.addScore(points); }

    public void setShowingMenu(boolean showingMenu) { gameMenuModel.setShowingMenu(showingMenu); }

    public void setShowingTerminal(boolean showingTerminal) { terminal.setShowingTerminal(showingTerminal); }

    public void nextTerminalOption() { terminal.next(); }

    public void previousTerminalOption() { terminal.previous(); }

    public int getTerminalSelection() { return terminal.getSelectedIndex(); }

    public Terminal getTerminal() { return terminal; }

    public boolean isShowingTerminal() { return terminal.isShowingTerminal(); }

    public boolean isShowingMenu() { return gameMenuModel.isShowingMenu(); }

    public double getScore() { return statistics.getScore(); }

    public int getTime() { return statistics.getSeconds(); }

    public boolean getWon() { return statistics.getWon(); }

    public boolean hasEnded() { return statistics.getEnded(); }

    public boolean isVPStale() { return viewport.isVPStale(); }

    public double getCameraX() { return viewport.getCameraX(); }

    public double getCameraY() { return viewport.getCameraY(); }

    public Rectangle2D getCurrentArea() { return stronghold.getCurrentArea(); }

    public int getRobotsCodeTot() { return statistics.getRobotsCodeTot(); }

    public int getPlatformsCodeTot() { return statistics.getPlatformsCodeTot(); }

    public List<Rectangle2D> getAreas() { return stronghold.getAreas(); }

    public int getCurrentAreaIndex() { return stronghold.getCurrentAreaIndex(); }

    public GameStatistics getStatistics() { return statistics; }

    public int getTotalPswPieces() { return statistics.getTotalPswPieces(); }

    public int getPswPiecesFound() { return statistics.getPswPiecesFound(); }

    public List<FurniturePiece> getFurniture() { return stronghold.getFurniture(); }

    public List<Robot> getRobots() { return stronghold.getRobots(); }

    public List<Enemy> getEnemies() { return stronghold.getEnemies(); }

    public Agent getAgent() { return stronghold.getAgent(); }

    public List<Entity> getEntities() { return stronghold.getEntities(); }

    public List<Platform> getPlatforms() { return stronghold.getPlatforms(); }

    public List<Platform> getStillPlatforms() { return stronghold.getStillPlatforms(); }

    public List<MovingPlatform> getMovingPlatforms() { return stronghold.getMovingPlatforms(); }

    public boolean isUsingLift() { return stronghold.isUsingLift(); }
}