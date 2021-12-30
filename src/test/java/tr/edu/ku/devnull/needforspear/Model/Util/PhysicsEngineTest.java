package tr.edu.ku.devnull.needforspear.Model.Util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMap;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Size;
import tr.edu.ku.devnull.needforspear.Model.GameData.Speed;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.GiftObstacle;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.Model.UIModels.NoblePhantasm;
import tr.edu.ku.devnull.needforspear.Viewmodel.Util.CollisionData;
import tr.edu.ku.devnull.needforspear.Viewmodel.Util.PhysicsEngine;

import static org.junit.jupiter.api.Assertions.*;

class PhysicsEngineTest {

    GameMap sampleMap;
    CollisionData sampleCollisionData;
    Obstacle sampleObstacle;
    NoblePhantasm samplePhantasm;

    @BeforeEach
    void init() {
        sampleMap = new GameMap(new Size(1280, 720));
        sampleCollisionData = new CollisionData(new Location(1020.0, 106.0), new Speed(Double.valueOf(2.0).longValue(), Double.valueOf(2.0).longValue()));
        sampleObstacle = new GiftObstacle(sampleMap, .0, new Location(1050.0, 110.0));
        samplePhantasm = NoblePhantasm.getInstance();
    }

    /*
     * Blackbox Testing: Do not look into implementation, check what method guarantees and test if they are valid.
     *
     * Glassbox Testing: Look into implementation, check edge cases.
     */

    /*
     * Going to test the reflection of the sphere from the obstacles since it is mentioned
     * to create tests for the one method.
     */


    /*

     Recalled the method signature of the obstacle reflection method. We need to test if the cases in the requires
     holds and produces a valid output.

     Also, we should test if method returns a correct output for the sample inputs.

     Method signature:

     /**
     * Creates a reflection of the given data about the sphere.
     *
     * @requires incident != null, obstacle's X and Y coordinates should be inside of the gameMap.
     * @effects Creates a new data with the new location and speed to be reflected.
     * @modifies None
     * @param incident Data of the incident vector.
     * @return Returns a collision data of how it is going to be reflected.
     */

    @Test
    void GlassBoxTestReflectionException() {
        PhysicsEngine pe = new PhysicsEngine();

        // Test to check if the invalid inputs throws an exception.

        // Invalid X value, < 0.
        final Obstacle invalidObstacle1 = new GiftObstacle(sampleMap, 14.0, new Location(-145, 40));
        assertThrows(Exception.class, () -> {
            CollisionData reflect = pe.reflect(sampleCollisionData, invalidObstacle1);
        });

        // Invalid X value, > 1280.
        final Obstacle invalidObstacle2 = new GiftObstacle(sampleMap, 14.0, new Location(1875, 21));
        assertThrows(Exception.class, () -> {
            CollisionData reflect = pe.reflect(sampleCollisionData, invalidObstacle2);
        });
    }

    @Test
    void GlassBoxTestReflectionException2() {
        PhysicsEngine pe = new PhysicsEngine();
        // Invalid Y value, < 0.
        final Obstacle invalidObstacle3 = new GiftObstacle(sampleMap, 14.0, new Location(187, -47));
        assertThrows(Exception.class, () -> {
            CollisionData reflect = pe.reflect(sampleCollisionData, invalidObstacle3);
        });

        // Invalid Y value, > 720.
        final Obstacle invalidObstacle4 = new GiftObstacle(sampleMap, 14.0, new Location(874, 874));
        assertThrows(Exception.class, () -> {
            CollisionData reflect = pe.reflect(sampleCollisionData, invalidObstacle4);
        });

        // Invalid X and Y value.
        final Obstacle invalidObstacle5 = new GiftObstacle(sampleMap, 14.0, new Location(-475, 874));
        assertThrows(Exception.class, () -> {
            CollisionData reflect = pe.reflect(sampleCollisionData, invalidObstacle5);
        });
    }

    @Test
    void GlassBoxTestReflectionOutput() throws Exception {
        PhysicsEngine pe = new PhysicsEngine();

        CollisionData output = pe.reflect(sampleCollisionData, sampleObstacle);

        assertEquals(output.getCurrentLocation().getXCoordinates(), 1020.0);
        assertEquals(output.getCurrentLocation().getYCoordinates(), 106.0);
        assertEquals(output.getCurrentSpeed().getSpeedOnXAxis(), -2.0);
        assertEquals(output.getCurrentSpeed().getSpeedOnYAxis(), 2.0);
    }

    @Test
    void BlackBoxReflectionException() throws Exception {
        PhysicsEngine pe = new PhysicsEngine();
        assertNull(pe.reflect(null, sampleObstacle));
        assertNull(pe.reflect( sampleCollisionData, (Obstacle) null));
        assertNull(pe.reflect( null, (Obstacle) null));
    }

    @Test
    void BlackBoxReflectionOutput() throws Exception {
        // On BlackBox texting, we should check properties such as if the 0 and last indices in range, if the equalities
        // hold in certain cases, and if the edge cases produces valid input.

        // In physics engine, there is only comparisons, so we'll be checking edge cases on comparisons.
        // Such as should it be x > 10 or x >= 10. Does it produce a correct result?

        PhysicsEngine pe = new PhysicsEngine();
        Obstacle obstacle = new GiftObstacle(sampleMap, .0, new Location(1020.0, 106.0));

        CollisionData output = pe.reflect(sampleCollisionData, obstacle);

        assertEquals(output.getCurrentLocation().getXCoordinates(), 1020.0);
        assertEquals(output.getCurrentLocation().getYCoordinates(), 106.0);
        assertEquals(output.getCurrentSpeed().getSpeedOnXAxis(), -2.0);
        assertEquals(output.getCurrentSpeed().getSpeedOnYAxis(), 2.0);
    }
}