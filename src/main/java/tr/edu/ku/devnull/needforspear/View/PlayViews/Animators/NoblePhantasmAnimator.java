package tr.edu.ku.devnull.needforspear.View.PlayViews.Animators;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.UIModels.NoblePhantasm;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.MovementHandler;
import java.awt.*;



/**
 * NoblePhantasmAnimator class is used to draw and animate paddle for NeedForSpearGame.
 *
 * @author Gokce Sevimli
 */

public class NoblePhantasmAnimator {

    private Location phantasmLocation;
    double x_pos_NoblePhantasm;
    double y_pos_NoblePhantasm;
    private boolean isGameStarted = false; //when game starts it should not play without user action.
    private MovementHandler movementHandler = new MovementHandler();

    /**
     * Constructor for NoblePhantasmaAnimator.
     */
    public NoblePhantasmAnimator() {
        phantasmLocation = NoblePhantasm.getInstance().getLocation();
        x_pos_NoblePhantasm = phantasmLocation.getXCoordinates();
        y_pos_NoblePhantasm = phantasmLocation.getYCoordinates();
    }

    /**
     * This method draws the NoblePhantasm based on its movement and rotation in MovementHandler class.
     * @param g Graphic instance to be updated.
     */

    public void draw(Graphics g) {

        NoblePhantasm noblePhantasm = NoblePhantasm.getInstance();
        noblePhantasm.setLocation(movementHandler.movementOfNoblePhantasm());

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.magenta);

        g2d.draw(movementHandler.rotateOfNoblePhantasm());
        g2d.fill(movementHandler.rotateOfNoblePhantasm());

    }
}

