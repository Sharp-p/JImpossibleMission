package Model;

import java.util.*;
import Utilities.*;

import static config.GameConstants.FLOOR_Y;
import static config.GameConstants.GRAVITY;

public class GameModel extends Observable {
    private java.util.List<Entity> entities = new ArrayList<>();
    private Agent agent;

    public GameModel() {
        agent = new Agent(new Tuple<>(100.0, 100.0), new HorizontalMovement());
        setChanged();
        notifyObservers();
    }

    public GameModel newModel() { return new GameModel(); }

    public void addEntity(Entity entity) { entities.add(entity); }

    public void moveAgent(Direction direction, Double deltaTime) {
        // System.out.println("Direction: " + direction);
        agent.moveTo(direction, deltaTime);
    }

    public void loopUpdate(double dt) {
        // TODO: spostare in vari metodi dentro le varie classi che qua verranno aggiornate
        double vY, vX, aY, x, y;

        x = agent.getPosition().getFirst();
        y = agent.getPosition().getSecond();
        vX = agent.getVelocity().getFirst();
        vY = agent.getVelocity().getSecond();

        if (!agent.isGrounded()) aY = GRAVITY;
        else {
            aY = 0;
            vY = 0;
        }

        vX += agent.getAcceleration().getFirst();
        vY += aY * dt;

        x += vX * dt;
        y += vY * dt;


        if (agent.getPosition().getSecond() >= FLOOR_Y) {
            y = FLOOR_Y;
            agent.setGrounded(true);
        } else {
            agent.setGrounded(false);
        }

        agent.setAcceleration(new Tuple<>(agent.getPosition().getFirst(), aY));
        agent.setVelocity(new Tuple<>(agent.getVelocity().getFirst(), vY));
        agent.setPosition(new Tuple<>(agent.getAcceleration().getFirst(), y));

        setChanged();
        notifyObservers();
    }

    public Agent getAgent() { return agent; }

    public List<Entity> getEntities() { return entities; }
}