package tr.edu.ku.devnull.needforspear.Model.Obstacle;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMap;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Size;
import tr.edu.ku.devnull.needforspear.Model.Spell.Spell;

/**
 * GiftObstacle is a class that extends Obstacle
 * which contains the attributes of a gift obstacle.
 * <p>
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
        this.obstacleType = Constants.UIConstants.GIFT_OBSTACLE;
        this.gameMap = gameMap;
        this.health = Constants.UIConstants.OBSTACLE_SIMPLE_HIT_HEALTH;
        this.speed = speed;
        this.size = new Size((int) ((Constants.UIConstants.INITIAL_SCREEN_WIDTH * Constants.ProportionConstants.RATIO_OF_NOBLE_PHANTASM) / Constants.UIConstants.OBSTACLE_SIZE_DIVISION_CONSTANT), Constants.ProportionConstants.HEIGHT_OF_THE_OBSTACLE);
        this.location = location;
        this.color = Constants.UIConstants.GREEN_COLOR_STRING;
    }

    /**
     * Gets the spell.
     *
     * @return Spell acquired by the played.
     */
    @Override
    public Spell getSpell() {
        return super.getSpell();
    }

    /**
     * Sets the spell.
     *
     * @param spell Spell to be set.
     */
    @Override
    public void setSpell(Spell spell) {
        super.setSpell(spell);
    }
}
