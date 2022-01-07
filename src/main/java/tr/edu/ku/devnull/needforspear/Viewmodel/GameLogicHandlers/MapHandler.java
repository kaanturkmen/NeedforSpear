package tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;

/**
 * MapHandler is Controller Design Pattern
 * to achieve MVVM Design on map actions.
 */
public class MapHandler {

    /**
     * Finds the number of obstacles of given type in game map.
     *
     * @return Integer value of number of obstacles of given type in map.
     */
    public int retrieveObstacleNumber(String obstacleType) {
        int x = 0;
        for (Obstacle obs : NeedforSpearGame.getInstance().getGameInfo().getGameMap().getListofObstacles()) {
            if (obs.getObstacleType().equals(obstacleType)) {
                x++;
            }
        }
        return x;
    }
}
