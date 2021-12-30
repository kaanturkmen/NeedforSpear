package tr.edu.ku.devnull.needforspear.Model.Obstacle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameInfo;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMap;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Size;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.CollisionHandler;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests how the damageObstacle() method of Obstacle interacts
 * with specific obstacles in specific situations.
 *
 * @author Halil Doruk Yıldırım
 */
class ObstacleTest {

    GameMap testMap;
    Obstacle firmObs,simpleObs;
    List<Obstacle> listOfObstacles;
    CollisionHandler collisionHandler = new CollisionHandler();
    FirstObstacleSubscriber subscriber = new FirstObstacleSubscriber();


    @BeforeEach
    void initEach() {
        NeedforSpearGame.getInstance().setGameInfo(new GameInfo());
        testMap = new GameMap(new Size(1280, 720));
        firmObs = new FirmObstacle(testMap, 0.0, new Location(500.0,100.0));
        simpleObs = new SimpleObstacle(testMap, 0.0, new Location(700.0,100.0));
        listOfObstacles = testMap.getListofObstacles();
    }

    /**
     * REQUIRES simpleObs!=null
     * MODIFIES simpleObs
     * EFFECTS damages the simple obstacle, lowering health to zero
     */
    @Test
    @DisplayName("Checks if simple obstacles are removed from game after damaged.")
    void damageOneHPObstacle() {
        simpleObs.damageObstacle();
        assertTrue(collisionHandler.isRemovedObstacle(simpleObs));
    }

    /**
     * REQUIRES simpleObs!=null
     * MODIFIES simpleObs, subscriber
     * EFFECTS damages the simple obstacle and updates the subscriber's isDestroyed value.
     */
    @Test
    @DisplayName("Checks if subscriber is appropriately updated when obstacles are destroyed.")
    void destroyedObstacleSubscriber() {
        simpleObs.subscribe(subscriber);
        simpleObs.damageObstacle();
        assertTrue(subscriber.isDestroyed());
    }

    /**
     * REQUIRES firmObs!=null
     * MODIFIES firmObs
     * EFFECTS damages the simple obstacle, lowering health by one
     */
    @Test
    @DisplayName("Checks if firm obstacles are not destroyed by getting hit once but damaged.")
    void damageFirmObstacle() {
        int prevHealth= firmObs.getHealth();
        firmObs.damageObstacle();
        assertFalse(collisionHandler.isRemovedObstacle(firmObs));
        assertEquals(prevHealth, firmObs.getHealth()+1);
    }

    /**
     * REQUIRES firmObs!=null
     * MODIFIES none
     * EFFECTS attempts to damage firm obstacle but fails due to infinite void spell
     */
    @Test
    @DisplayName("Checks if infinite void appropriately prevents obstacles from taking damage.")
    void damageDuringInfiniteVoidObstacle() {
        assertNotNull(firmObs);
        firmObs.activateInfiniteVoid(true);
        int prevHealth= firmObs.getHealth();
        firmObs.damageObstacle();
        assertEquals(prevHealth, firmObs.getHealth());
    }

    /**
     * REQUIRES firmObs!=null
     * MODIFIES firmObs
     * EFFECTS hits firm obstacle when infinite void is deactivated and damages it.
     */
    @Test
    @DisplayName("Checks if obstacles are appropriately damaged after infinite void gets deactivated.")
    void damageAfterInfiniteVoidObstacle() {
        int prevHealth= firmObs.getHealth();
        firmObs.activateInfiniteVoid(true);
        firmObs.deactivateInfiniteVoidSpell();
        firmObs.damageObstacle();
        assertEquals(prevHealth, firmObs.getHealth()+1);
    }


}