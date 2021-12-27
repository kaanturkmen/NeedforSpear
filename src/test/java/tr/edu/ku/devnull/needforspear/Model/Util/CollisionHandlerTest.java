package tr.edu.ku.devnull.needforspear.Model.Util;

import javafx.fxml.LoadException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sun.rmi.transport.ObjectTable;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Size;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.ExplosiveObstacle;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.ObstacleFactory;
import tr.edu.ku.devnull.needforspear.Model.Spell.ExpansionSpell;
import tr.edu.ku.devnull.needforspear.Model.Spell.Spell;

import tr.edu.ku.devnull.needforspear.Model.UIModels.NoblePhantasm;
import tr.edu.ku.devnull.needforspear.Model.UIModels.Sphere;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.CollisionHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CollisionHandlerTest {
    CollisionHandler collisionHandler;
    Obstacle sampleObstacle;
    NoblePhantasm noblePhantasm;

    @BeforeEach
    void init() {
        collisionHandler = new CollisionHandler();
        sampleObstacle = ObstacleFactory.getInstance().getObstacle("SimpleObstacle");
        noblePhantasm = NoblePhantasm.getInstance();
    }



    @Test
    public void obstacleSphereCollisionTest() {
        //BB testing
        sampleObstacle.setLocation(new Location(300, 300));
        Sphere.getInstance().setLocation(new Location(300,300));
        //True positive collision
        assertTrue(collisionHandler.collision(sampleObstacle, Sphere.getInstance()));

        sampleObstacle.setLocation(new Location(500,500));
        //False positive collision
        assertFalse(collisionHandler.collision(sampleObstacle, Sphere.getInstance()));

    }

    @Test
    public void phantasmSpellCollisionTest(){
        //BB testing
        noblePhantasm.setLocation(new Location(300, 300));
        Spell spell = new ExpansionSpell(new Size(50,50), new Location(300, 300));

        //True positive collision
        assertTrue(collisionHandler.collision(noblePhantasm, spell));

        noblePhantasm.setLocation(new Location(500,500));
        //False positive collision
        assertFalse(collisionHandler.collision(noblePhantasm, spell));

    }

    @Test
    public void obstacleObstacleCollisionTest(){
        Obstacle sampleObstacle2 = ObstacleFactory.getInstance().getObstacle("SimpleObstacle");

        sampleObstacle.setLocation(new Location(300,300));
        sampleObstacle2.setLocation(new Location(300, 300));

        assertTrue(collisionHandler.collision(sampleObstacle2, sampleObstacle));

        sampleObstacle2.setLocation(new Location(500, 500));
        assertFalse(collisionHandler.collision(sampleObstacle2, sampleObstacle));
    }


    @Test
    public void isRemovedObstacleTest(){
        //MODIFIES obstacle health

        //GB testing

        //obstacles have some nonzero health from start
        assertFalse(collisionHandler.isRemovedObstacle(sampleObstacle));

        sampleObstacle.damageObstacle(); //simple obstacle has 1 health
        assertTrue(collisionHandler.isRemovedObstacle(sampleObstacle));
        assertEquals(sampleObstacle.getHealth(), 0);
    }










}

