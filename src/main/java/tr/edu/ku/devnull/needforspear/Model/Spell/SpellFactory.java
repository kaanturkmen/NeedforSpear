package tr.edu.ku.devnull.needforspear.Model.Spell;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Size;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.*;
/**
 * SpellFactory implements the Factory design pattern
 * for the creation of similar but unique types of spells.
 *
 * @author Can Usluel
 */
public class SpellFactory {
    private static SpellFactory factory;
    /**
     * Constructor for obstacleFactory
     */
    private SpellFactory(){

    }
    /**
     * Singleton design pattern implementation for the creation of SpellFactory.
     *
     * @return new SpellFactory if no previous instances present.
     */
    public static SpellFactory getInstance() {
        if (factory == null) {
            factory = new SpellFactory();
        }
        return factory;
    }
    /**
     * Creates the desired type of spell with unique attributes.
     *
     * @param spellType String containing type of spell to be created.
     * @param giftObstacle GiftObstacle that will drop the spell once destroyed
     * @return New Spell with spellType and unique attributes inserted into given giftObstacle.
     */
    public Spell getSpell(String spellType, Obstacle giftObstacle) {
        if (giftObstacle != null && giftObstacle.getObstacleType().equals(Constants.ObstacleNameConstants.GIFT)) {
            switch (spellType) {
                case Constants.SpellNameConstants.CHANCE:
                    return new ChanceGivingSpell(new Size(Constants.ProportionConstants.SPELL_SIZE, Constants.ProportionConstants.SPELL_SIZE),
                            new Location(giftObstacle.getLocation().getXCoordinates() + (giftObstacle.getSize().getWidth() - Constants.ProportionConstants.SPELL_SIZE) / 2
                                    , giftObstacle.getLocation().getYCoordinates() + (giftObstacle.getSize().getLength() - Constants.ProportionConstants.SPELL_SIZE) / 2));

                case Constants.SpellNameConstants.EXPANSION:
                    return new ExpansionSpell(new Size(Constants.ProportionConstants.SPELL_SIZE, Constants.ProportionConstants.SPELL_SIZE),
                            new Location(giftObstacle.getLocation().getXCoordinates() + (giftObstacle.getSize().getWidth() - Constants.ProportionConstants.SPELL_SIZE) / 2
                                    , giftObstacle.getLocation().getYCoordinates() + (giftObstacle.getSize().getLength() - Constants.ProportionConstants.SPELL_SIZE) / 2));

                case Constants.SpellNameConstants.HEX:
                    return new MagicalHexSpell(new Size(Constants.ProportionConstants.SPELL_SIZE, Constants.ProportionConstants.SPELL_SIZE),
                            new Location(giftObstacle.getLocation().getXCoordinates() + (giftObstacle.getSize().getWidth() - Constants.ProportionConstants.SPELL_SIZE) / 2
                                    , giftObstacle.getLocation().getYCoordinates() + (giftObstacle.getSize().getLength() - Constants.ProportionConstants.SPELL_SIZE) / 2));

                case Constants.SpellNameConstants.UNSTOPPABLE:
                    return new UnstoppableSpell(new Size(Constants.ProportionConstants.SPELL_SIZE, Constants.ProportionConstants.SPELL_SIZE),
                            new Location(giftObstacle.getLocation().getXCoordinates() + (giftObstacle.getSize().getWidth() - Constants.ProportionConstants.SPELL_SIZE) / 2
                                    , giftObstacle.getLocation().getYCoordinates() + (giftObstacle.getSize().getLength() - Constants.ProportionConstants.SPELL_SIZE) / 2));

                default:
                    return null;
            }
        } else{
            return null;
        }
    }
}
