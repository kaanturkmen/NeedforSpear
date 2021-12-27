package tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers;
import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.UIModels.NoblePhantasm;
import tr.edu.ku.devnull.needforspear.Model.UIModels.Sphere;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import java.awt.*;
import java.awt.event.KeyEvent;


/**
 * KeyDispatcher class is implemented for listening the key events and taking action accordingly.
 *
 * @author Gokce Sevimli
 */

public class KeyDispatcher implements KeyEventDispatcher {

    /**
     * @param e
     * @return true if key event(A, D, W, T, Rightkey, Leftkey, DownKey) is pressed or released, false if not.
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_PRESSED) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                NoblePhantasm noblePhantasm = NoblePhantasm.getInstance();
                noblePhantasm.setMovingLeft(false);
                noblePhantasm.setMovingRight(true);
                noblePhantasm.setStartTimeMoving(System.currentTimeMillis());

            }

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                NoblePhantasm noblePhantasm = NoblePhantasm.getInstance();
                noblePhantasm.setMovingRight(false);
                noblePhantasm.setMovingLeft(true);
                noblePhantasm.setStartTimeMoving(System.currentTimeMillis());

            }

            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                NoblePhantasm noblePhantasm = NoblePhantasm.getInstance();
                noblePhantasm.setSpeeding(true);
                noblePhantasm.setStartTimeMoving(System.currentTimeMillis());

            }

            if (e.getKeyCode() == KeyEvent.VK_A) {
                NoblePhantasm.getInstance().setRightRotate(true);
                NoblePhantasm.getInstance().setLeftRotate(false);
            }

            if (e.getKeyCode() == KeyEvent.VK_D) {
                NoblePhantasm.getInstance().setRightRotate(false);
                NoblePhantasm.getInstance().setLeftRotate(true);

            }

            if (e.getKeyCode() == KeyEvent.VK_W) {
                Sphere.getInstance().setMoving(true);

            }

            if(e.getKeyCode() == KeyEvent.VK_T){
                SpellHandler.getInstance().activateSpell(SpellHandler.getInstance().getAvailableSpell(Constants.SpellNameConstants.EXPANSION));
            }

            return true;
        }
        if (e.getID() == KeyEvent.KEY_RELEASED) {
            if (e.getKeyCode() == KeyEvent.VK_A) {
                NoblePhantasm.getInstance().setRightRotate(false);
                NoblePhantasm.getInstance().setLeftRotate(false);
            }

            if (e.getKeyCode() == KeyEvent.VK_D) {
                NoblePhantasm.getInstance().setRightRotate(false);
                NoblePhantasm.getInstance().setLeftRotate(false);
            }

            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                NoblePhantasm noblePhantasm = NoblePhantasm.getInstance();
                noblePhantasm.setMovingRight(false);
            }

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                NoblePhantasm noblePhantasm = NoblePhantasm.getInstance();
                noblePhantasm.setMovingLeft(false);
            }

            if (e.getKeyCode() == KeyEvent.VK_DOWN) {

                NoblePhantasm noblePhantasm = NoblePhantasm.getInstance();
                noblePhantasm.setSpeeding(false);
            }

            if(e.getKeyCode() == KeyEvent.VK_T){
                //NoblePhantasm noblePhantasm = NoblePhantasm.getInstance();
                //noblePhantasm.setDoubleSize(false);
            }

            if(!NeedforSpearGame.getInstance().getIsPaused()){

                if(e.getKeyCode() == KeyEvent.VK_C){
                    SpellHandler.getInstance().activateSpell(SpellHandler.getInstance().getAvailableSpell(Constants.SpellNameConstants.CHANCE));
                }

                if(e.getKeyCode() == KeyEvent.VK_E){
                    SpellHandler.getInstance().activateSpell(SpellHandler.getInstance().getAvailableSpell(Constants.SpellNameConstants.EXPANSION));
                }

                if(e.getKeyCode() == KeyEvent.VK_M){
                    SpellHandler.getInstance().activateSpell(SpellHandler.getInstance().getAvailableSpell(Constants.SpellNameConstants.HEX));
                }

                if(e.getKeyCode() == KeyEvent.VK_U){
                    SpellHandler.getInstance().activateSpell(SpellHandler.getInstance().getAvailableSpell(Constants.SpellNameConstants.UNSTOPPABLE));
                }

            }
            return true;
        }
        return false;
    }
}
