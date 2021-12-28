package tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers;

import tr.edu.ku.devnull.needforspear.Model.Spell.YmirSpells.HollowPurpleSubscriber;

import java.util.ArrayList;
import java.util.List;

public class HollowPurpleHandler {

    List<HollowPurpleSubscriber> subscribers = new ArrayList<>();

    private static HollowPurpleHandler onlyInstance = null;

    /**

     * Singleton constructor
     *
     * @return
     */
    public static synchronized HollowPurpleHandler getInstance() {
        if (onlyInstance == null) {
            onlyInstance = new HollowPurpleHandler();
        }

        return onlyInstance;
    }

    /**
     * Adds panel to subscribers of the event
     *
     * @param panel
     */
    public void subscribe(HollowPurpleSubscriber panel) {
        subscribers.add(panel);
    }


    public void unSubscribe(HollowPurpleSubscriber panel) {
        subscribers.remove(panel);
    }

    public void notifySubscribers() {
        for (HollowPurpleSubscriber subscriber : subscribers) {
            System.out.println("Notified");
            subscriber.updateHollow();
        }
    }
}
