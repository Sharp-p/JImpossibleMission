package Model;

import Utilities.Tuple;

import static Model.Projectile.PROJECTILE_WIDTH;

public class ShootingRobot extends MovingRobot {
    private Projectile plasmaBolt = new Projectile(0.0, 0.0);
    private double shootingFrequency = 0.5;
    private double timeLastShot = 0.0;


    public ShootingRobot(Tuple<Double, Double> position) {
        super(position);
    }

    public ShootingRobot(Tuple<Double, Double> position, double leftBoundX, double rightBoundX) {
        super(position);
        setBounds(leftBoundX, rightBoundX);
    }

    public void update(double deltaTime) {
        // if I'm not turning so I try to shoot
        if (!isTurning()) {
            // if I'm not already shooting
            if (!plasmaBolt.isActive()) {
                timeLastShot += deltaTime;
                // if it's time to shoot
                if (timeLastShot >= shootingFrequency) {
                    timeLastShot -= shootingFrequency;

                    plasmaBolt.setActive(true);
                    plasmaBolt.addDuration(deltaTime);

                    double boltX = getDirection() == Direction.RIGHT ?
                            getPosition().getFirst() + GROUND_ROBOT_WIDTH :
                            getPosition().getFirst() - PROJECTILE_WIDTH;

                    plasmaBolt.setDirection(getDirection());

                    plasmaBolt.setPosition(new Tuple<>(boltX, getPosition().getSecond()));
                    return;
                }
            }
            else {
                plasmaBolt.addDuration(deltaTime);
                //System.out.println("Dopo addDuration ACTIVE: " + plasmaBolt.isActive());
                if (isActive()) return;
            }
        }
        super.update(deltaTime);
    }

    public Projectile getPlasmaBolt() { return plasmaBolt; }

}
