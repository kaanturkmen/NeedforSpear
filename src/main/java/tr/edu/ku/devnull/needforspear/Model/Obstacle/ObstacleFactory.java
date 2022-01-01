package tr.edu.ku.devnull.needforspear.Model.Obstacle;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMap;
import tr.edu.ku.devnull.needforspear.Model.GameData.Size;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;

import java.util.Random;

/**
 * ObstacleFactory implements the Factory design pattern
 * for the creation of similar but unique types of obstacles.
 *
 * @author Melis Oktayoğlu and Gökçe Sevimli
 */
public class ObstacleFactory {
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

        // Obstacle moving back and forth with 0.2 probability
        boolean is_moving = new Random().nextFloat() <= Constants.UIConstants.OBSTACLE_MOVE_PROBABILITY;
        double speed;

        switch (obstacleType) {
            case Constants.ObstacleNameConstants.SIMPLE_OBSTACLE:

                if (is_moving) {
                    speed = (Constants.UIConstants.INITIAL_SCREEN_WIDTH * Constants.ProportionConstants.RATIO_OF_NOBLE_PHANTASM / Constants.UIConstants.SPEED_DIVISION_CONSTANT);
                } else {
                    speed = 0;
                }

                return new SimpleObstacle(NeedforSpearGame.getInstance().getGameInfo().getGameMap(), speed, initialLocation);

            case Constants.ObstacleNameConstants.FIRM_OBSTACLE:

                if (is_moving) {
                    speed = (Constants.UIConstants.INITIAL_SCREEN_WIDTH * Constants.ProportionConstants.RATIO_OF_NOBLE_PHANTASM / Constants.UIConstants.SPEED_DIVISION_CONSTANT);
                } else {
                    speed = 0;
                }

                return new FirmObstacle(NeedforSpearGame.getInstance().getGameInfo().getGameMap(), speed, initialLocation);

            case Constants.ObstacleNameConstants.EXPLOSIVE_OBSTACLE:
                return new ExplosiveObstacle(NeedforSpearGame.getInstance().getGameInfo().getGameMap(), Constants.UIConstants.OBSTACLE_FAST_CONSTANT_OF_SPEED, initialLocation);

            case Constants.ObstacleNameConstants.GIFT_OBSTACLE:
                return new GiftObstacle(NeedforSpearGame.getInstance().getGameInfo().getGameMap(), 0.0, initialLocation);

            case Constants.ObstacleNameConstants.HOLLOW_PURPLE_OBSTACLE:
                return new HollowPurpleObstacle(NeedforSpearGame.getInstance().getGameInfo().getGameMap(), 0.0, initialLocation);

            default:
                return null;
        }


    }
}