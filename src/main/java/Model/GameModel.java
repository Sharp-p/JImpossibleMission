package Model;

import java.util.*;
import Utilities.*;

import static config.GameConstants.FLOOR_Y;
import static config.GameConstants.GRAVITY;

public class GameModel extends Observable {
    private java.util.List<Entity> entities = new ArrayList<>();
    private Agent agent;
    private List<Platform> platforms = new ArrayList<>();

    public GameModel() {
        agent = new Agent(new Tuple<>(100.0, 100.0), new HorizontalMovement());


        setChanged();
        notifyObservers();
    }

    public GameModel newModel() { return new GameModel(); }

    public void addEntity(Entity entity) { entities.add(entity); }

    public void moveAgent(Direction direction, Double deltaTime) {
        agent.moveTo(direction, deltaTime);
    }

    /**
     * For now it updates only the physics of the agent
     * @param dt
     */
    public void loopUpdate(double dt) {

        getAgent().applyGravity(dt);

        setChanged();
        notifyObservers(dt);
    }

    public Agent getAgent() { return agent; }

    public List<Entity> getEntities() { return entities; }
}