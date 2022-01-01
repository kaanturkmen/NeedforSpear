package tr.edu.ku.devnull.needforspear.Model.Obstacle;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMap;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Size;

import java.util.Random;

/**
 * FirmObstacle is a class that extends Obstacle
 * which contains the attributes of a firm obstacle.
 * <p>
 * Firm obstacles are obstacles with more health than simple obstacles, they can have from 3 to 10 health.
 */
public class FirmObstacle extends Obstacle {

    /**
     * Constructor for FirmObstacle.
     *
     * @param gameMap  GameMap which the firm obstacle is in.
     * @param speed    Integer value of the firm obstacle's speed.
     * @param location Location of the firm obstacle.
     */
    public FirmObstacle(GameMap gameMap, Double speed, Location location) {
        this.obstacleType = Constants.ObstacleNameConstants.FIRM_OBSTACLE;
        this.gameMap = gameMap;
        this.health = determineHealth();
        this.speed = speed;
        this.size = new Size((int) ((Constants.UIConstants.INITIAL_SCREEN_WIDTH * Constants.ProportionConstants.RATIO_OF_NOBLE_PHANTASM) / 5), Constants.ProportionConstants.HEIGHT_OF_THE_OBSTACLE);
        this.location = location;
        this.color = Constants.UIConstants.ORANGE_COLOR_STRING;
    }

    /**
     * Creates a random Integer from 3 to 10 for the health attribute of firm obstacle.
     *
     * @return Integer value from 3 to 10 for firm obstacle's health attribute.
     */
    private Integer determineHealth() {
        return new Random().nextInt(Constants.UIConstants.OBSTACLE_MULTIPLE_HIT_MAX_HEALTH - Constants.UIConstants.OBSTACLE_MULTIPLE_HIT_MIN_HEALTH) + Constants.UIConstants.OBSTACLE_MULTIPLE_HIT_MIN_HEALTH;
    }
}
