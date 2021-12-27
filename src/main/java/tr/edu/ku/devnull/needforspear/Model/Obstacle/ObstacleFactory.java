package tr.edu.ku.devnull.needforspear.Model.Obstacle;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMap;
import tr.edu.ku.devnull.needforspear.Model.GameData.Size;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;

import java.util.Random;

/**
 * ObstacleFactory implements the Factory design pattern
 * for the creation of similar but unique types of obstacles.
 *
 * @author Melis Oktayoğlu and Gökçe Sevimli
 */
public class ObstacleFactory {
    private GameMap gameMap = new GameMap(new Size(1280, 720));
    private static ObstacleFactory factory;

    /**
     * Constructor for obstacleFactory
     */
    private ObstacleFactory() {
    }

    /**
     * Singleton design pattern implementation for the creation of ObstacleFactory.
     *
     * @return new ObstacleFactory if no previous instances present.
     */
    public static ObstacleFactory getInstance() {
        if (factory == null) {
            factory = new ObstacleFactory();
        }
        return factory;
    }

    /**
     * Creates the desired type of obstacle with unique attributes.
     *
     * @param obstacleType String containing type of obstacle to be created.
     * @return New Obstacle with obstacleType and unique attributes.
     */
    public Obstacle getObstacle(String obstacleType) {
        Location initialLocation = new Location(0, 0);
        Random rand = new Random();

        //obstacle moving back and forth with 0.2 probability
        boolean is_moving = rand.nextInt(5) == 0;
        double speed;

        switch (obstacleType) {
            case Constants.ObstacleNameConstants.SIMPLE:

                if (is_moving) {
                    speed = (Constants.UIConstants.INITIAL_SCREEN_WIDTH*Constants.ProportionConstants.RATIO_OF_NOBLE_PHANTASM /400);
                } else {
                    speed = 0;
                }

                return new SimpleObstacle(gameMap, speed, initialLocation);

            case Constants.ObstacleNameConstants.FIRM:

                if (is_moving) {
                    speed = (Constants.UIConstants.INITIAL_SCREEN_WIDTH*Constants.ProportionConstants.RATIO_OF_NOBLE_PHANTASM /400);
                } else {
                    speed = 0;
                }

                return new FirmObstacle(gameMap, speed, initialLocation);

            case Constants.ObstacleNameConstants.EXP:
                return new ExplosiveObstacle(gameMap, 2.0, initialLocation);

            case Constants.ObstacleNameConstants.GIFT:
                return new GiftObstacle(gameMap, 0.0, initialLocation);

            default:
                return null;
        }


    }
}