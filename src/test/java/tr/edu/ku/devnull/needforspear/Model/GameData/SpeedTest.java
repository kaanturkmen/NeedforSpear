package tr.edu.ku.devnull.needforspear.Model.GameData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.GiftObstacle;
import tr.edu.ku.devnull.needforspear.Model.Spell.SpellFactory;

import static org.junit.jupiter.api.Assertions.*;

class SpeedTest {
    Speed speed;
    @BeforeEach
    void initEach() {
        speed = new Speed(Long.valueOf(3), Long.valueOf(5));
    }

    @Test
    void dot() {
        assertTrue(speed.repOk());
        assertEquals(speed.dot(new Speed(Long.valueOf(12), Long.valueOf(21))),141.0);
        assertTrue(speed.repOk());
        assertEquals(speed.dot(new Speed(Long.valueOf(9), Long.valueOf(32))),187.0);
        assertTrue(speed.repOk());
        assertEquals(speed.dot(new Speed(Long.valueOf(25), Long.valueOf(45))),300.0);
        assertTrue(speed.repOk());
        assertEquals(speed.dot(new Speed(Long.valueOf(81), Long.valueOf(76))),623.0);
        assertTrue(speed.repOk());

    }

    @Test
    void subtract() {
        Speed result = speed.subtract(new Speed(Long.valueOf(32), Long.valueOf(78)));
        assertTrue(result.repOk());
        assertEquals(result.getSpeedOnXAxis(),-29.0);
        assertTrue(result.repOk());
        assertEquals(result.getSpeedOnYAxis(),-73.0);
        assertTrue(result.repOk());

        result = speed.subtract(new Speed(Long.valueOf(-1), Long.valueOf(33)));
        assertTrue(result.repOk());
        assertEquals(result.getSpeedOnXAxis(),4.0);
        assertTrue(result.repOk());
        assertEquals(result.getSpeedOnYAxis(),-28.0);
        assertTrue(result.repOk());

        result = speed.subtract(new Speed(Long.valueOf(-23), Long.valueOf(-72)));
        assertTrue(result.repOk());
        assertEquals(result.getSpeedOnXAxis(),26.0);
        assertTrue(result.repOk());
        assertEquals(result.getSpeedOnYAxis(),77.0);
        assertTrue(result.repOk());
    }

    @Test
    void scale() {
        Speed result = speed.scale(3);
        assertTrue(result.repOk());
        assertEquals(result.getSpeedOnXAxis(), 9.0);
        assertTrue(result.repOk());
        assertEquals(result.getSpeedOnYAxis(), 15.0);
        assertTrue(result.repOk());

        result = speed.scale(5);
        assertTrue(result.repOk());
        assertEquals(result.getSpeedOnXAxis(), 15.0);
        assertTrue(result.repOk());
        assertEquals(result.getSpeedOnYAxis(), 25.0);
        assertTrue(result.repOk());

        result = speed.scale(14);
        assertTrue(result.repOk());
        assertEquals(result.getSpeedOnXAxis(), 42.0);
        assertTrue(result.repOk());
        assertEquals(result.getSpeedOnYAxis(), 70.0);
        assertTrue(result.repOk());
    }

    @Test
    void halveSpeed() {
        Double xSpeed = speed.getSpeedOnXAxis();
        Double ySpeed = speed.getSpeedOnYAxis();

        speed.halveSpeed();
        assertEquals(speed.getSpeedOnXAxis(), xSpeed/2);
        assertEquals(speed.getSpeedOnYAxis(), ySpeed/2);

        speed.halveSpeed();
        assertEquals(speed.getSpeedOnXAxis(), xSpeed/4);
        assertEquals(speed.getSpeedOnYAxis(), ySpeed/4);

        speed.halveSpeed();
        assertEquals(speed.getSpeedOnXAxis(), xSpeed/8);
        assertEquals(speed.getSpeedOnYAxis(), ySpeed/8);

        speed.halveSpeed();
        assertEquals(speed.getSpeedOnXAxis(), xSpeed/16);
        assertEquals(speed.getSpeedOnYAxis(), ySpeed/16);
    }

    @Test
    void doubleSpeed() {
        Double xSpeed = speed.getSpeedOnXAxis();
        Double ySpeed = speed.getSpeedOnYAxis();

        speed.doubleSpeed();
        assertEquals(speed.getSpeedOnXAxis(), xSpeed*2);
        assertEquals(speed.getSpeedOnYAxis(), ySpeed*2);

        speed.doubleSpeed();
        assertEquals(speed.getSpeedOnXAxis(), xSpeed*4);
        assertEquals(speed.getSpeedOnYAxis(), ySpeed*4);

        speed.doubleSpeed();
        assertEquals(speed.getSpeedOnXAxis(), xSpeed*8);
        assertEquals(speed.getSpeedOnYAxis(), ySpeed*8);

        speed.doubleSpeed();
        assertEquals(speed.getSpeedOnXAxis(), xSpeed*16);
        assertEquals(speed.getSpeedOnYAxis(), ySpeed*16);
    }
}