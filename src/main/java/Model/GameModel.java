package Model;

import java.util.*;
import Utilities.*;

import static Model.StillPlatform.STILL_PLATFORM_WIDTH;
import static config.GameConstants.*;

/**
 * An interface for all the models in the game environment
 */
public class GameModel extends Observable {
    private List<Entity> entities = new ArrayList<>();
    private RobotFactory robotFactory = new  RobotFactory();
    private Agent agent;
    private Stronghold stronghold;

    public GameModel() {
        createAgent(13.0,  ROW_HEIGHT - 30);
        entities.add(new StillRobot(new Tuple<>(70.0, ROW_HEIGHT - 18.0), 4));
        entities.add(new MovingRobot(new Tuple<>(14.0 * STILL_PLATFORM_WIDTH, ROW_HEIGHT - 18.0)));
        ((MovingRobot)entities.getLast()).setBounds(13 *  STILL_PLATFORM_WIDTH, 15 *  STILL_PLATFORM_WIDTH);

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

    public void setUsingLift(boolean usingLift) { agent.setUsingLift(usingLift); }

    public List<Robot> getRobots() {
        return entities.stream()
                .filter(e -> e instanceof Robot)
                .map(e -> (Robot) e)
                .toList();
    }

    public Agent getAgent() { return agent; }

    public List<Entity> getEntities() { return entities; }

    public List<Platform> getPlatforms() { return stronghold.getPlatforms(); }

    public List<Platform> getStillPlatforms() { return stronghold.getStillPlatforms(); }

    public List<MovingPlatform> getMovingPlatforms() { return stronghold.getMovingPlatforms(); }

    public boolean isUsingLift() { return agent.isUsingLift(); }
}