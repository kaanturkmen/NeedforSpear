package tr.edu.ku.devnull.needforspear.Model.Spell.YmirSpells;

import tr.edu.ku.devnull.needforspear.Model.UIModels.Sphere;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;

public class DoubleAccelSpell implements YmirPower {

    @Override
    public void triggerYmirEffect() {
        NeedforSpearGame.getInstance().getGameInfo().getSphere().activateDoubleAccel();
        System.out.println("Double Accel is activated.");
    }

}

