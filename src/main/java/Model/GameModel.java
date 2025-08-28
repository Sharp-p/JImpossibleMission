package Model;

import java.util.*;
import Utilities.*;

import static config.GameConstants.FLOOR_Y;
import static config.GameConstants.GRAVITY;

public class GameModel extends Observable {
    private List<Entity> entities = new ArrayList<>();
    private Agent agent;
    private Stronghold stronghold;

    public GameModel() {
        agent = new Agent(new Tuple<>(100.0, 100.0), new HorizontalMovement());

        // TODO: function that creates the world model HERE
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

    public void moveAgent(Direction direction, Double deltaTime) {
        agent.moveTo(direction, deltaTime);
    }

    public GameModel newModel() { return new GameModel(); }

    public void addEntity(Entity entity) { entities.add(entity); }

    public Agent getAgent() { return agent; }

    public List<Entity> getEntities() { return entities; }

    public List<Platform> getPlatforms() { return stronghold.getPlatforms(); }
}