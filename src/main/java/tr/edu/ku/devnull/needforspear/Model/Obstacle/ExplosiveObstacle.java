package tr.edu.ku.devnull.needforspear.Model.Obstacle;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMap;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Size;

import java.awt.*;

/**
 * ExplosiveObstacle is a class that extends Obstacle
 * which contains the attributes of an explosive obstacle.
 * <p>
 * Explosive obstacles fall down after being destroyed, potentially damaging the player.
 */
public class ExplosiveObstacle extends Obstacle {

    /**
     * Constructor for ExplosiveObstacle.
     *
     * @param gameMap  GameMap which the explosive obstacle is in.
     * @param speed    Double value of the explosive obstacle's speed.
     * @param location Location of the explosive obstacle.
     */
    public ExplosiveObstacle(GameMap gameMap, Double speed, Location location) {
        this.obstacleType = Constants.ObstacleNameConstants.EXPLOSIVE_OBSTACLE;
        this.gameMap = gameMap;
        this.health = Constants.UIConstants.OBSTACLE_SIMPLE_HIT_HEALTH;
        this.speed = speed;
        this.size = new Size(2 * Constants.ProportionConstants.RADIUS_OF_EXPLOSIVE_OBSTACLE, 2 * Constants.ProportionConstants.RADIUS_OF_EXPLOSIVE_OBSTACLE);
        this.location = location;
        this.color = Constants.UIConstants.RED_COLOR_STRING;
    }

    /**
     * Sets location of center orbit.
     *
     * @param orbitCenter Location of the orbit.
     */
    @Override
    public void setOrbitCenter(Location orbitCenter) {
        super.setOrbitCenter(orbitCenter);
    }

    /**
     * Alters the default location of the orbit.
     */
    public void alterOrbitCenter() {
        orbitCenter = new Location(this.location.getXCoordinates() + Constants.ProportionConstants.RADIUS_OF_EXPLOSIVE_OBSTACLE,
                this.location.getYCoordinates() + Constants.ProportionConstants.RADIUS_OF_EXPLOSIVE_OBSTACLE);
    }

    /**
     * Gets the Orbit Location.
     *
     * @return Location of the orbit.
     */
    @Override
    public Location getOrbitCenter() {
        return orbitCenter;
    }

    /**
     * Gets the orbit bounds.
     *
     * @return Rectangle of orbit bounds.
     */
    @Override
    public Rectangle retrieveOrbitBounds() {
        return super.retrieveOrbitBounds();
    }
}
