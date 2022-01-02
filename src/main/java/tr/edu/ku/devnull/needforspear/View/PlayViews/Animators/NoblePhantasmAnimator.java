package tr.edu.ku.devnull.needforspear.View.PlayViews.Animators;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.UIModels.NoblePhantasm;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.BackgroundHandler;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.MovementHandler;

import java.awt.*;
import java.awt.geom.AffineTransform;


/**
 * NoblePhantasmAnimator class is used to draw and animate paddle for NeedForSpearGame.
 *
 * @author Gokce Sevimli
 */
public class NoblePhantasmAnimator {
    private final Image phantasmSpell, phantasm;
    private final MovementHandler movementHandler = new MovementHandler();

    /**
     * Constructor for NoblePhantasmaAnimator.
     */
    public NoblePhantasmAnimator() {
        phantasmSpell = new BackgroundHandler().getBackgroundImage(Constants.UIConstants.PHANTASM_IMAGE_EXPANSION);
        phantasm = new BackgroundHandler().getBackgroundImage(Constants.UIConstants.PHANTASM_IMAGE);
    }

    /**
     * This method draws the NoblePhantasm based on its movement and rotation in MovementHandler class.
     *
     * @param g Graphic instance to be updated.
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        NoblePhantasm.getInstance().getLocation().setXCoordinates(movementHandler.movementOfNoblePhantasm());

        AffineTransform tx = g2d.getTransform();

        movementHandler.rotationOfNoblePhantasm();

        g2d.rotate(NoblePhantasm.getInstance().getRotationDegree(), NoblePhantasm.getInstance().getLocation().getXCoordinates() + NoblePhantasm.getInstance().getSize().getWidth() / 2.0, NoblePhantasm.getInstance().getLocation().getYCoordinates() + Constants.ProportionConstants.Y_CENTER_OF_NOBLE_PHANTASM);
        if (NoblePhantasm.getInstance().isMagicActivated()) {
            g2d.drawImage(phantasmSpell, (int) NoblePhantasm.getInstance().getLocation().getXCoordinates(), (int) NoblePhantasm.getInstance().getLocation().getYCoordinates(), NoblePhantasm.getInstance().getSize().getWidth(), NoblePhantasm.getInstance().getSize().getLength(), null);
        } else {
            g2d.drawImage(phantasm, (int) NoblePhantasm.getInstance().getLocation().getXCoordinates(), (int) NoblePhantasm.getInstance().getLocation().getYCoordinates(), NoblePhantasm.getInstance().getSize().getWidth(), NoblePhantasm.getInstance().getSize().getLength(), null);
        }

        g2d.setTransform(tx);
    }
}