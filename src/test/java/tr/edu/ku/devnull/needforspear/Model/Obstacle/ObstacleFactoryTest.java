package tr.edu.ku.devnull.needforspear.Model.Obstacle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for ObstacleFactory
 *
 * @author Melis Oktayoğlu
 */
public class ObstacleFactoryTest {
    ObstacleFactory factory;

    @BeforeEach
    public void getInstanceTest(){
        factory = ObstacleFactory.getInstance();
        assertNotNull(factory, "getInstance() returns null");
        assertTrue(factory instanceof ObstacleFactory, "getInstance() does not return the ObstacleFactory");
    }

    @Test
    public void getExplosiveTest(){
        Obstacle obstacle = factory.getObstacle("ExplosiveObstacle");
        assertTrue(obstacle instanceof ExplosiveObstacle, "getObstacle fails in creating an Explosive Obstacle");
    }

    @Test
    public void getSimpleTest(){
        Obstacle obstacle = factory.getObstacle("SimpleObstacle");
        assertTrue(obstacle instanceof SimpleObstacle, "getObstacle fails in creating a Simple Obstacle");
    }

    @Test
    public void getGiftTest(){
        Obstacle obstacle = factory.getObstacle("GiftObstacle");
        assertTrue(obstacle instanceof GiftObstacle, "getObstacle fails in creating a Gift Obstacle");
    }

    @Test
    public void getFirmTest(){
        Obstacle obstacle = factory.getObstacle("FirmObstacle");
        assertTrue(obstacle instanceof FirmObstacle, "getObstacle fails in creating a Firm Obstacle");

    }

    @Test
    public void checkSpeed(){
        Obstacle obstacle = factory.getObstacle("GiftObstacle");
        if(obstacle.getSpeed()!=0){
            Assertions.assertEquals(0.32, obstacle.getSpeed());
        }
    }



}


