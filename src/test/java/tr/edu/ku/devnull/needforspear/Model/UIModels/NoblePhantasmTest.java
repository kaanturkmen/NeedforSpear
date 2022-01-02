package tr.edu.ku.devnull.needforspear.Model.UIModels;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Gökçe Sevimli
 * <p>
 * This test class executes test for NoblePhantasm.
 */

public class NoblePhantasmTest {

    private NoblePhantasm noblePhantasm;

    @BeforeEach
    void setUp() {
        noblePhantasm = NoblePhantasm.getInstance();
    }

    @Test
    @DisplayName("Ensure getters and setters work properly.")
    public void getterSetterTest() {
        noblePhantasm.getLocation().setXCoordinates(576.0);
        assertEquals(576.0, noblePhantasm.getLocation().getXCoordinates());
        noblePhantasm.getLocation().setYCoordinates(670.0);
        assertEquals(670.0, noblePhantasm.getLocation().getYCoordinates());
        noblePhantasm.setRotationDegree(45.0);
        assertEquals(45.0, noblePhantasm.getRotationDegree());
        noblePhantasm.setSpeed(5.0);
        assertEquals(5.0, noblePhantasm.getSpeed());
        noblePhantasm.setDoubleSize(true);
        assertEquals(true, noblePhantasm.isDoubleSize());
        noblePhantasm.setSpeeding(true);
        assertEquals(true, noblePhantasm.isSpeeding());
        noblePhantasm.setMagicActivated(false);
        assertEquals(false, noblePhantasm.isMagicActivated());
    }


    @Test
    @DisplayName("Ensure location resets.")
    public void resetLocationTest() {
        //EFFECTS: If the position of the NoblePhantasm is not in the center, it resets the location to the center.
        //MODIFIES: x position of NoblePhantasm.
        noblePhantasm.getLocation().setXCoordinates(80.0);
        noblePhantasm.resetLocation();
        assertEquals(576.0, noblePhantasm.getLocation().getXCoordinates());
    }

    @Test
    @DisplayName("Ensure getInstance of NoblePhantasm handles correctly.")
    public void getInstanceTest() {
        assertNotNull(noblePhantasm, "Get instance returns null");
        assertTrue(noblePhantasm instanceof NoblePhantasm, "Get instance does not return NoblePhantasm");
    }


    @Test
    @DisplayName("Ensure Noble Phantasm Expansion spell is activated properly.")
    public void activateExpansionSpellTest() {
        //REQUIRES: Magic is not activated.
        //EFFECTS: If the Expansion Spell is activated either pressing T or to the icon on the screen, NoblePhantasm width doubles.
        //MODIFIES: x position and width of NoblePhantasm.
        noblePhantasm.setMagicActivated(false);
        int oldWidth = noblePhantasm.getSize().getWidth();
        noblePhantasm.activateExpansionSpell();
        int newWidth = noblePhantasm.getSize().getWidth();
        assertEquals(newWidth, oldWidth * 2);
    }

    @Test
    @DisplayName("Ensure Noble Phantasm Expansion spell is deactivated properly.")
    public void deactivateExpansionSpell() {
        //EFFECTS: If the Expansion Spell is deactivated due to time pass, NoblePhantasm width turns back to its initial width.
        //MODIFIES: x position and width of NoblePhantasm
        int oldWidth = Constants.ProportionConstants.WIDTH_OF_NOBLE_PHANTASM;
        noblePhantasm.deactivateExpansionSpell();
        int newWidth = noblePhantasm.getSize().getWidth();
        assertEquals(newWidth, oldWidth);
    }

}
