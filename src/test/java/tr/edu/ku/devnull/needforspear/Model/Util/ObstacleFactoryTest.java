package tr.edu.ku.devnull.needforspear.Model.Util;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMap;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.*;

import static org.junit.Assert.*;

/**
 * Test for ObstacleFactory
 *
 * @author Melis Oktayoğlu
 */
public class ObstacleFactoryTest {
    ObstacleFactory factory;

    @Before
    public void getInstanceTest(){
        factory = ObstacleFactory.getInstance();
        assertNotNull("getInstance() returns null", factory);
        assertTrue("getInstance() does not return the ObstacleFactory", factory instanceof ObstacleFactory);
    }

    @Test
    public void getExplosiveTest(){
        Obstacle obstacle = factory.getObstacle("ExplosiveObstacle");
        assertTrue("getObstacle fails in creating an Explosive Obstacle", obstacle instanceof ExplosiveObstacle);
    }

    @Test
    public void getSimpleTest(){
        Obstacle obstacle = factory.getObstacle("SimpleObstacle");
        assertTrue("getObstacle fails in creating a Simple Obstacle", obstacle instanceof SimpleObstacle);
    }

    @Test
    public void getGiftTest(){
        Obstacle obstacle = factory.getObstacle("GiftObstacle");
        assertTrue("getObstacle fails in creating a Gift Obstacle", obstacle instanceof GiftObstacle);
    }

    @Test
    public void getFirmTest(){
        Obstacle obstacle = factory.getObstacle("FirmObstacle");
        assertTrue("getObstacle fails in creating a Firm Obstacle", obstacle instanceof FirmObstacle);

    }

    @Test
    public void checkSpeed(){
        Obstacle obstacle = factory.getObstacle("GiftObstacle");
        if(obstacle.getSpeed()!=0){
            Assertions.assertEquals(0.32, obstacle.getSpeed());
        }
    }



}


