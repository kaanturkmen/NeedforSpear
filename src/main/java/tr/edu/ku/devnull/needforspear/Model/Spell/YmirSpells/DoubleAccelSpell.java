package tr.edu.ku.devnull.needforspear.Model.Spell.YmirSpells;

import tr.edu.ku.devnull.needforspear.NeedforSpearGame;

/**
 * DoubleAccelSpell is a spell type which implements YmirPower interface.
 */
public class DoubleAccelSpell implements YmirPower {

    /**
     * Triggers spell effect.
     */
    @Override
    public void triggerYmirEffect() {
        NeedforSpearGame.getInstance().getGameInfo().getSphere().activateDoubleAccel();
        System.out.println("Double Accel is activated.");
    }

}

