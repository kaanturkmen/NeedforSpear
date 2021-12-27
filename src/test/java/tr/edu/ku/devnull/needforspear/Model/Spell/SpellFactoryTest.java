package tr.edu.ku.devnull.needforspear.Model.Spell;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tr.edu.ku.devnull.needforspear.Model.GameData.*;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.*;

import static org.junit.jupiter.api.Assertions.*;
/**
 * This test class test the functionalities of spellFactory
 *
 * @author Can Usluel
 */
public class SpellFactoryTest {
    SpellFactory factory;
    GiftObstacle testGiftObstacle;
    GameMap testMap;
    Double testSpeed;
    Location testLocation;

    @BeforeEach
    void initEach() {
        testMap = new GameMap(new Size(1280, 720));
        factory = SpellFactory.getInstance();
        testSpeed = 0.0;
        testLocation = new Location(0,0);
        testGiftObstacle = new GiftObstacle(testMap, testSpeed, testLocation);
    }

    /**
     * MODIFIES: factory
     * EFFECTS: creates instance of SpellFactory if it is not created before, gets the only instance when getInstance() called.
     */
    @Test
    @DisplayName("Checks if SpellFactory is created at first call and is equal to the same object after each getInstance()")
    void getInstanceTest(){
        factory = SpellFactory.getInstance();
        assertNotNull(factory);
        assertTrue(factory instanceof SpellFactory);
        SpellFactory testFactory = SpellFactory.getInstance();
        assertEquals(factory, testFactory);
    }
    /**
     * REQUIRES: testGiftObstacle != null && testGiftObstacle.getObstacleType().equals(Constants.ObstacleNameConstants.GIFT)
     * MODIFIES: chanceGivingSpell
     * EFFECTS: creates an chanceGivingSpell whose location and size is set according to giftObstacle's size and location
     */
    @Test
    @DisplayName("Checks if an instance of ChanceGivingSpell is created using SpellFactory")
    void getChanceGivingSpellTest(){
        Spell chanceGivingSpell = factory.getSpell(Constants.SpellNameConstants.CHANCE, testGiftObstacle);
        assertTrue(chanceGivingSpell instanceof ChanceGivingSpell);
        assertEquals(chanceGivingSpell.getSpellType(), Constants.SpellNameConstants.CHANCE);
    }
    /**
     * REQUIRES: testGiftObstacle != null && testGiftObstacle.getObstacleType().equals(Constants.ObstacleNameConstants.GIFT)
     * MODIFIES: expansionSpell
     * EFFECTS: creates an expansionSpell whose location and size is set according to giftObstacle's size and location
     */
    @Test
    @DisplayName("Checks if an instance of ExpansionSpell is created using SpellFactory")
    void getExpansionSpellTest(){
        Spell expansionSpell =  factory.getSpell(Constants.SpellNameConstants.EXPANSION, testGiftObstacle);
        assertTrue(expansionSpell instanceof ExpansionSpell);
        assertEquals(expansionSpell.getSpellType(), Constants.SpellNameConstants.EXPANSION);
    }
    /**
     * REQUIRES: testGiftObstacle != null && testGiftObstacle.getObstacleType().equals(Constants.ObstacleNameConstants.GIFT)
     * MODIFIES: magicalHexSpell
     * EFFECTS: creates an magicalHexSpell whose location and size is set according to giftObstacle's size and location
     */
    @Test
    @DisplayName("Checks if an instance of MagicalHexSpell is created using SpellFactory")
    void getMagicalHexSpellTest(){
        Spell magicalHexSpell = factory.getSpell(Constants.SpellNameConstants.HEX, testGiftObstacle);
        assertTrue(magicalHexSpell instanceof MagicalHexSpell);
        assertEquals(magicalHexSpell.getSpellType(), Constants.SpellNameConstants.HEX);
    }
    /**
     * REQUIRES: testGiftObstacle != null && testGiftObstacle.getObstacleType().equals(Constants.ObstacleNameConstants.GIFT)
     * MODIFIES: unstoppableSpell
     * EFFECTS: creates an unstoppableSpell whose location and size is set according to giftObstacle's size and location
     */
    @Test
    @DisplayName("Checks if an instance of UnstoppableSpell is created using SpellFactory")
    void getUnstoppableSpellTest(){
        Spell unstoppableSpell =  factory.getSpell(Constants.SpellNameConstants.UNSTOPPABLE, testGiftObstacle);
        assertTrue(unstoppableSpell instanceof UnstoppableSpell);
        assertEquals(unstoppableSpell.getSpellType(), Constants.SpellNameConstants.UNSTOPPABLE);

    }

    /**
     * MODIFIES: testSpell
     * EFFECTS: creates an unstoppableSpell whose location and size is set according to giftObstacle's size and location
     */
    @Test
    @DisplayName("Checks if getSpell method returns null when given spell name is invalid or giftObstacle is null")
    void GlassBoxTestGetSpellException(){
        Spell testSpell =  factory.getSpell("test", testGiftObstacle);
        assertNull(testSpell);
        testSpell = factory.getSpell(Constants.SpellNameConstants.UNSTOPPABLE, null);
        assertNull(testSpell);
    }
}


