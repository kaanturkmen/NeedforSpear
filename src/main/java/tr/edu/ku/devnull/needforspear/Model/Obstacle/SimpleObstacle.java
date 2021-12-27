package tr.edu.ku.devnull.needforspear.Model.Obstacle;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMap;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Size;

import java.awt.*;

/**
 * SimpleObstacle is a class that extends Obstacle
 * which contains the attributes of a simple obstacle.
 *
 * Simple obstacles are obstacles that are destroyed after 1 hit with no extra effect.
 */
public class SimpleObstacle extends Obstacle {

    /**
     * Constructor for SimpleObstacle.
     *
     * @param gameMap  GameMap which the simple obstacle is in.
     * @param speed    Integer value of the simple obstacle's speed.
     * @param location Location of the simple obstacle.
     */
    public SimpleObstacle(GameMap gameMap, Double speed, Location location) {
        this.obstacleType = "SimpleObstacle";
        this.gameMap = gameMap;
        this.health = 1;
        this.speed = speed;
        this.size = new Size((int) ((Constants.UIConstants.INITIAL_SCREEN_WIDTH * Constants.ProportionConstants.RATIO_OF_NOBLE_PHANTASM) / 5), Constants.ProportionConstants.HEIGHT_OF_THE_OBSTACLE);
        this.location = location;
        this.color = "BLUE";
    }
}
