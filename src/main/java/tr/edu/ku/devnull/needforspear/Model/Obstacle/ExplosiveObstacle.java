package tr.edu.ku.devnull.needforspear.Model.Obstacle;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMap;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Size;

import java.awt.*;

/**
 * ExplosiveObstacle is a class that extends Obstacle
 * which contains the attributes of an explosive obstacle.
 *
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
        this.obstacleType = "ExplosiveObstacle";
        this.gameMap = gameMap;
        this.health = 1;
        this.speed = speed;
        this.size = new Size(2*Constants.ProportionConstants.RADIUS_OF_EXPLOSIVE_OBSTACLE, 2*Constants.ProportionConstants.RADIUS_OF_EXPLOSIVE_OBSTACLE);
        this.location = location;
        this.color = "RED";

    }

    @Override
    public void setOrbitCenter(Location orbitCenter) {
        super.setOrbitCenter(orbitCenter);
    }

    public void alterOrbitCenter(){
        orbitCenter = new Location(this.location.getXCoordinates() + Constants.ProportionConstants.RADIUS_OF_EXPLOSIVE_OBSTACLE,
                this.location.getYCoordinates() + Constants.ProportionConstants.RADIUS_OF_EXPLOSIVE_OBSTACLE);
    }
    @Override
    public Location getOrbitCenter(){
        return orbitCenter;
    }

    @Override
    public Rectangle retrieveOrbitBounds() {
        return super.retrieveOrbitBounds();
    }
}
