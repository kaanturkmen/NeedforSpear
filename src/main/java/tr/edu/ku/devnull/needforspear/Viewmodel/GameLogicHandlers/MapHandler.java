package tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;

public class MapHandler {
    /**
     * Finds the number of simple obstacles in game map.
     *
     * @return Integer value of number of simple obstacles in map.
     */
    public int retrieveSimpleObstacleNumber() {
        int x = 0;
        for (Obstacle obs : NeedforSpearGame.getInstance().getGameInfo().getGameMap().getListofObstacles()) {
            if (obs.getObstacleType().equals(Constants.ObstacleNameConstants.SIMPLE)) {
                x++;
            }
        }
        return x;
    }

    /**
     * Finds the number of firm obstacles in game map.
     *
     * @return Integer value of number of firm obstacles in map.
     */
    public int retrieveFirmObstacleNumber() {
        int x = 0;
        for (Obstacle obs : NeedforSpearGame.getInstance().getGameInfo().getGameMap().getListofObstacles()) {
            if (obs.getObstacleType().equals(Constants.ObstacleNameConstants.FIRM)) {
                x++;
            }
        }
        return x;
    }

    /**
     * Finds the number of explosive obstacles in game map.
     *
     * @return Integer value of number of explosive obstacles in map.
     */
    public int retrieveExplosiveObstacleNumber() {
        int x = 0;
        for (Obstacle obs : NeedforSpearGame.getInstance().getGameInfo().getGameMap().getListofObstacles()) {
            if (obs.getObstacleType().equals(Constants.ObstacleNameConstants.EXP)) {
                x++;
            }
        }
        return x;
    }

    /**
     * Finds the number of gift obstacles in game map.
     *
     * @return Integer value of number of gift obstacles in map.
     */
    public int retrieveGiftObstacleNumber() {
        int x = 0;
        for (Obstacle obs : NeedforSpearGame.getInstance().getGameInfo().getGameMap().getListofObstacles()) {
            if (obs.getObstacleType().equals(Constants.ObstacleNameConstants.GIFT)) {
                x++;
            }
        }
        return x;
    }
}
