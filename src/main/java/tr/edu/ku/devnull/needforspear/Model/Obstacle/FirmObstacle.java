package tr.edu.ku.devnull.needforspear.Model.Obstacle;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMap;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Size;

import java.awt.*;
import java.util.Random;

/**
 * FirmObstacle is a class that extends Obstacle
 * which contains the attributes of a firm obstacle.
 *
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
        this.obstacleType = "FirmObstacle";
        this.gameMap = gameMap;
        this.health = determineHealth();
        this.speed = speed;
        this.size = new Size((int) ((Constants.UIConstants.INITIAL_SCREEN_WIDTH * Constants.ProportionConstants.RATIO_OF_NOBLE_PHANTASM) / 5), Constants.ProportionConstants.HEIGHT_OF_THE_OBSTACLE);
        this.location = location;
        this.color = "ORANGE";
    }

    /**
     * Creates a random Integer from 3 to 10 for the health attribute of firm obstacle.
     *
     * @return Integer value from 3 to 10 for firm obstacle's health attribute.
     */
    private Integer determineHealth() {

        // Solution is taken from https://stackoverflow.com/a/5271613
        Random r = new Random();
        int low = 3;
        int high = 10;

        return r.nextInt(high - low) + low;
    }
}
