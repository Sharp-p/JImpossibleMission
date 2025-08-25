package Model;

public interface MovementBehavior {
    public void move(Entity entity, Direction dir,  double deltaTime);
}
