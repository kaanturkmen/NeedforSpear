package tr.edu.ku.devnull.needforspear.Model.Spell.YmirSpells;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.HollowPurpleHandler;

/**
 * HollowPurpleSpell is a spell type which implements YmirPower interface.
 */
public class HollowPurpleSpell implements YmirPower {

    /**
     * Triggers spell effect.
     */
    @Override
    public void triggerYmirEffect() {
        System.out.println(Constants.MessageConstants.HOLLOW_PURPLE_TRIGGERED);
        HollowPurpleHandler.getInstance().notifySubscribers();
    }

}
