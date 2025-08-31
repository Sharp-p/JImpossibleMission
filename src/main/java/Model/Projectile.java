package Model;

import Utilities.Tuple;

import static Model.StillPlatform.STILL_PLATFORM_WIDTH;

public class Projectile extends Enemy {
    public static final int PROJECTILE_WIDTH = STILL_PLATFORM_WIDTH * 3;
    public static final int PROJECTILE_HEIGHT = 9;
    public static final double FULL_DURATION = 1.25;

    private double current_duration = 0.0;

    public Projectile(double x, double y) {
        super(new Tuple<>(x, y), new StillMovement(), 0.0);
        setActive(false);
    }

    public double getDuration() { return current_duration; }

    public void addDuration(double deltaTime) {
        current_duration += deltaTime;
        if (current_duration >= FULL_DURATION) {
            current_duration -= FULL_DURATION;
            setActive(false);
        }
    }

    public void moveTo(Direction dir, Double deltaTime) {}
}
