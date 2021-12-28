package tr.edu.ku.devnull.needforspear.Model.Obstacle;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMap;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Size;

public class HollowPurpleObstacle extends Obstacle{

    public HollowPurpleObstacle(GameMap gameMap, Double speed, Location location) {
        this.obstacleType = "HollowPurpleObstacle";
        this.gameMap = gameMap;
        this.health = 1;
        this.speed = speed;
        this.size = new Size((int) ((Constants.UIConstants.INITIAL_SCREEN_WIDTH * Constants.ProportionConstants.RATIO_OF_NOBLE_PHANTASM) / 5), Constants.ProportionConstants.HEIGHT_OF_THE_OBSTACLE);
        this.location = location;
        this.color = "PURPLE";
    }
}
