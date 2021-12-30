package tr.edu.ku.devnull.needforspear.View.PlayViews.Animators;
import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.UIModels.NoblePhantasm;
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
    Image phantasmSpell;
    Image phantasm;


    /**
     * Constructor for NoblePhantasmaAnimator.
     */
    public NoblePhantasmAnimator() {
        phantasmLocation = NoblePhantasm.getInstance().getLocation();
        x_pos_NoblePhantasm = phantasmLocation.getXCoordinates();
        y_pos_NoblePhantasm = phantasmLocation.getYCoordinates();
        phantasmSpell  = getNoblePhantasmImage(Constants.UIConstants.PHANTASM_IMAGE_EXPANSION);
        phantasm = getNoblePhantasmImage(Constants.UIConstants.PHANTASM_IMAGE);

    }

    /**
     * Creates a noble phantasm image and scaling it.
     *
     * @param path Path of the noble phantasm image.
     * @return Image of the noble phantasm.
     */

    public Image getNoblePhantasmImage(String path) {
        return Toolkit.getDefaultToolkit().getImage(Constants.UIConstants.USER_DIRECTORY_TO_RESOURCE_FOLDER + path)
                .getScaledInstance(NoblePhantasm.getInstance().getSize().getWidth(), Constants.ProportionConstants.HEIGHT_OF_NOBLE_PHANTASM, Image.SCALE_SMOOTH);

    }
    /**
     * This method draws the NoblePhantasm based on its movement and rotation in MovementHandler class.
     * @param g Graphic instance to be updated.
     */

    public void draw(Graphics g) {

        NoblePhantasm noblePhantasm = NoblePhantasm.getInstance();

        int x_location =  noblePhantasm.getLocation().getXCoordinates().intValue();
        int y_location =  noblePhantasm.getLocation().getYCoordinates().intValue();
        Graphics2D g2d = (Graphics2D) g;
        noblePhantasm.getLocation().setXCoordinates(movementHandler.movementOfNoblePhantasm());

        currentX = noblePhantasm.getLocation().getXCoordinates();
        currentY = noblePhantasm.getLocation().getYCoordinates();
        AffineTransform tx = g2d.getTransform();

        movementHandler.rotationOfNoblePhantasm();
        
        g2d.rotate(noblePhantasm.getRotationDegree(), currentX + noblePhantasm.getSize().getWidth() / 2, currentY + Constants.ProportionConstants.Y_CENTER_OF_NOBLE_PHANTASM);
        if(noblePhantasm.isMagicActivated()){
            g2d.drawImage(phantasmSpell, x_location, y_location, noblePhantasm.getSize().getWidth(), noblePhantasm.getSize().getLength(), null);

        }else {
            g2d.drawImage(phantasm, x_location, y_location, noblePhantasm.getSize().getWidth(), noblePhantasm.getSize().getLength(), null);
        }

        g2d.setTransform(tx);
    }


}