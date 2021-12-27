package tr.edu.ku.devnull.needforspear.Model.Obstacle;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMap;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Size;
import tr.edu.ku.devnull.needforspear.Model.Spell.Spell;

import java.awt.*;

/**
 * GiftObstacle is a class that extends Obstacle
 * which contains the attributes of a gift obstacle.
 *
 * Gift obstacles drops a spell downwards after being destroyed.
 * The player can potentially obtain the spell and use it to their advantage.
 */
public class GiftObstacle extends Obstacle {
    /**
     * Constructor for GiftObstacle.
     *
     * @param gameMap  GameMap which the gift obstacle is in.
     * @param speed    Double value of the gift obstacle's speed.
     * @param location Location of the gift obstacle.
     */
    public GiftObstacle(GameMap gameMap, Double speed, Location location) {
        this.obstacleType = "GiftObstacle";
        this.gameMap = gameMap;
        this.health = 1;
        this.speed = speed;
        this.size = new Size((int) ((Constants.UIConstants.INITIAL_SCREEN_WIDTH * Constants.ProportionConstants.RATIO_OF_NOBLE_PHANTASM) / 5), Constants.ProportionConstants.HEIGHT_OF_THE_OBSTACLE);
        this.location = location;
        this.color = "GREEN";
    }

    @Override
    public Spell getSpell() {
        return super.getSpell();
    }

    @Override
    public void setSpell(Spell spell) {
        super.setSpell(spell);
    }
}
