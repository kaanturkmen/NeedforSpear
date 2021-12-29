package tr.edu.ku.devnull.needforspear.View.PlayViews.Animators;
import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.UIModels.NoblePhantasm;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.MovementHandler;
import java.awt.*;
import java.awt.geom.AffineTransform;


/**
 * NoblePhantasmAnimator class is used to draw and animate paddle for NeedForSpearGame.
 *
 * @author Gokce Sevimli
 */

public class NoblePhantasmAnimator {

    private Location phantasmLocation;
    double x_pos_NoblePhantasm;
    double y_pos_NoblePhantasm;
    double currentX;
    double currentY;
    private boolean isGameStarted = false; //when game starts it should not play without user action.
    private MovementHandler movementHandler = new MovementHandler();
    Image paddleImage1;


    /**
     * Constructor for NoblePhantasmaAnimator.
     */
    public NoblePhantasmAnimator() {
        phantasmLocation = NoblePhantasm.getInstance().getLocation();
        x_pos_NoblePhantasm = phantasmLocation.getXCoordinates();
        y_pos_NoblePhantasm = phantasmLocation.getYCoordinates();
    }


    public Image getNoblePhantasmImage(String path) {
        return Toolkit.getDefaultToolkit().getImage(Constants.UIConstants.USER_DIRECTORY_TO_RESOURCE_FOLDER + path)
                .getScaledInstance(NoblePhantasm.getInstance().getSize().getWidth(), Constants.ProportionConstants.HEIGHT_OF_NOBLE_PHANTASM, Image.SCALE_SMOOTH);

    }
    /**
     * This method draws the NoblePhantasm based on its movement and rotation in MovementHandler class.
     * @param g Graphic instance to be updated.
     */

    public void draw(Graphics g) {

        NoblePhantasm noblePhantasm = NoblePhantasm.getInstance();;
        if(noblePhantasm.isMagicActivated()){
            paddleImage1 = getNoblePhantasmImage(Constants.UIConstants.PHANTASM_IMAGE_EXPANSION); }
            else { paddleImage1 = getNoblePhantasmImage(Constants.UIConstants.PHANTASM_IMAGE); }
        Graphics2D g2d = (Graphics2D) g;
        int x_location = phantasmLocation.getXCoordinates().intValue();
        int y_location = phantasmLocation.getYCoordinates().intValue();

        noblePhantasm.getLocation().setXCoordinates(movementHandler.movementOfNoblePhantasm());

        currentX = noblePhantasm.getLocation().getXCoordinates();
        currentY = noblePhantasm.getLocation().getYCoordinates();
        AffineTransform tx = g2d.getTransform();

        movementHandler.rotationOfNoblePhantasm();

        g2d.rotate(noblePhantasm.getRotationDegree(), currentX + noblePhantasm.getSize().getWidth() / 2, currentY + Constants.ProportionConstants.Y_CENTER_OF_NOBLE_PHANTASM);
        g2d.drawImage(paddleImage1, x_location, y_location, noblePhantasm.getSize().getWidth(), noblePhantasm.getSize().getLength(), null);

        g2d.setTransform(tx);
    }


}