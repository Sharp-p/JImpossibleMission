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
    private List<Entity> entities = new ArrayList<>();
    private RobotFactory robotFactory = new  RobotFactory();
    private GameStatistics statistics = new GameStatistics();
    private Agent agent;
    private Stronghold stronghold;
    private boolean isPaused = false;


    // TODO: classe statistiche: mantiene tutte le statistiche, timer ecc

    public GameModel() {
        // TODO: creare funzioni più comode per l'aggiunta di entità
        createAgent(13.0,  ROW_HEIGHT - 30);
        entities.add(new StillRobot(new Tuple<>(70.0, ROW_HEIGHT - 18.0), 4));
        entities.add(new MovingRobot(new Tuple<>(14.0 * STILL_PLATFORM_WIDTH, (double)ROW_HEIGHT_TILES * STILL_PLATFORM_HEIGHT - GROUND_ROBOT_HEIGHT + 1)));
        ((MovingRobot)entities.getLast()).setBounds(13 *  STILL_PLATFORM_WIDTH, 15 *  STILL_PLATFORM_WIDTH);
        entities.add(new SightRobot(new Tuple<>(14.0 * STILL_PLATFORM_WIDTH, ROW_HEIGHT_TILES * STILL_PLATFORM_HEIGHT * 4.0 - GROUND_ROBOT_HEIGHT + 1)));
        ((SightRobot)entities.getLast()).setBounds(8 * STILL_PLATFORM_WIDTH, 20 * STILL_PLATFORM_WIDTH);
        entities.add(new ShootingRobot(
                new Tuple<>(3.0 * STILL_PLATFORM_WIDTH, ROW_HEIGHT_TILES * STILL_PLATFORM_HEIGHT * 4.0 - GROUND_ROBOT_HEIGHT + 1),
                3 * STILL_PLATFORM_WIDTH, 5 * STILL_PLATFORM_WIDTH)
        );

        entities.add((((ShootingRobot)entities.getLast()).getPlasmaBolt()));

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
    public void createAgent(double x, double y) {
        // TODO: [RESPAWN] luogo respawn rispetto a porta di entrata
        agent = new Agent(new Tuple<>(x, y));
    }

    public void moveAgent(Direction direction, Double deltaTime) {
        agent.moveTo(direction, deltaTime);
    }

    public GameModel newModel() { return new GameModel(); }

    public void addEntity(Entity entity) { entities.add(entity); }

    public void setPaused(boolean paused) { isPaused = paused; }

    public void setUsingLift(boolean usingLift) { agent.setUsingLift(usingLift); }

    public void setShowingStatistics(boolean showingStatistics) {  statistics.setShowingStatistics(showingStatistics); }

    public boolean isShowingStatistics() { return statistics.isShowingStatistics(); }

    public boolean isPaused() { return isPaused; }

    public GameStatistics getStatistics() { return statistics; }

    public int getTotalPswPieces() { return statistics.getTotalPswPieces(); }

    public int getPswPiecesFound() { return statistics.getPswPiecesFound(); }

    public List<FurniturePiece> getFurniture() { return stronghold.getFurniture(); }

    public List<Robot> getRobots() {
        return entities.stream()
                .filter(e -> e instanceof Robot)
                .map(e -> (Robot) e)
                .toList();
    }

    public List<Enemy> getEnemies() {
        return entities.stream()
                .filter(e -> e instanceof Enemy)
                .map(e -> (Enemy) e)
                .toList();
    }

    public Agent getAgent() { return agent; }

    public List<Entity> getEntities() { return entities; }

    public List<Platform> getPlatforms() { return stronghold.getPlatforms(); }

    public List<Platform> getStillPlatforms() { return stronghold.getStillPlatforms(); }

    public List<MovingPlatform> getMovingPlatforms() { return stronghold.getMovingPlatforms(); }

    public boolean isUsingLift() { return agent.isUsingLift(); }
}