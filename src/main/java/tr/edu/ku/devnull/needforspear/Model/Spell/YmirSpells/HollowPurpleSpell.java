package tr.edu.ku.devnull.needforspear.Model.Spell.YmirSpells;

import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.HollowPurpleHandler;

public class HollowPurpleSpell implements YmirPower {

    @Override
    public void triggerYmirEffect() {
        System.out.println("Hollow Purple Spell is activated.");
        HollowPurpleHandler.getInstance().notifySubscribers();
    }

}
