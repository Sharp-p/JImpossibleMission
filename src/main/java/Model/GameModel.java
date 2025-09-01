package Model;

import java.util.*;
import Utilities.*;

import static Model.Robot.GROUND_ROBOT_HEIGHT;
import static Model.StillPlatform.STILL_PLATFORM_HEIGHT;
import static Model.StillPlatform.STILL_PLATFORM_WIDTH;
import static config.GameConstants.*;

/**
 * An interface for all the models in the game environment.
 * To act as a tunnel to all other model it incorporates the other models methods.
 */
public class GameModel extends Observable {
    private GameStatistics statistics = new GameStatistics();
    private Stronghold stronghold;
    private boolean isPaused = false;


    // TODO: classe statistiche: mantiene tutte le statistiche, timer ecc

    public GameModel() {
        // TODO: creare funzioni più comode per l'aggiunta di entità
        stronghold = new Stronghold();

        setChanged();
        notifyObservers();
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

    public void foundPswPiece() { statistics.foundPswPiece(); }

    public void addTotalPswPieces() { statistics.addTotalPswPieces(); }

    public void consumeRobotsCode() { statistics.consumeRobotsCode(); }

    public void consumePlatformsCode() { statistics.consumePlatformsCode(); }

    public void addRobotsCode() { statistics.addRobotsCode(); }

    public void addPlatformsCode() { statistics.addPlatformsCode(); }

    public int getRobotsCodeTot() { return statistics.getRobotsCodeTot(); }

    public int getPlatformsCodeTot() { return statistics.getPlatformsCodeTot(); }

    /**
     * Function that creates a new agent. Used for every respawn instance.
     * @return Returns the new agent that the model uses
     */
    public void createAgent(double x, double y, int areaIndex) { stronghold.createAgent(x, y, areaIndex); }

    public void moveAgent(Direction direction, Double deltaTime) { stronghold.moveAgent(direction, deltaTime); }

    public GameModel newModel() { return new GameModel(); }

    public void addEntity(Entity entity) { stronghold.addEntity(entity); }

    public void setPaused(boolean paused) { isPaused = paused; }

    public void setUsingLift(boolean usingLift) { stronghold.setUsingLift(usingLift); }

    public void setShowingStatistics(boolean showingStatistics) {  statistics.setShowingStatistics(showingStatistics); }

    public boolean isShowingStatistics() { return statistics.isShowingStatistics(); }

    public boolean isPaused() { return isPaused; }

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