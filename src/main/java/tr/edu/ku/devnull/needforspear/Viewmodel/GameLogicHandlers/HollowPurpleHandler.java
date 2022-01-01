package tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers;

import tr.edu.ku.devnull.needforspear.Model.Spell.YmirSpells.HollowPurpleSubscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler for HollowPurpleSpell.
 */
public class HollowPurpleHandler {

    List<HollowPurpleSubscriber> subscribers = new ArrayList<>();

    private static HollowPurpleHandler onlyInstance = null;


    private HollowPurpleHandler() {}

    /**
     * Singleton Design Pattern's getInstance method.
     *
     * @return Single instance of the HollowPurpleHandler.
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
     * @param panel Panel to subscribe the object.
     */
    public void subscribe(HollowPurpleSubscriber panel) {
        subscribers.add(panel);
    }

    /**
     * Removes panel to subscribers of the event.
     *
     * @param panel Panel to subscribe the object.
     */
    public void unSubscribe(HollowPurpleSubscriber panel) {
        subscribers.remove(panel);
    }

    /**
     * Notifies the subscribers of the panel.
     */
    public void notifySubscribers() {
        for (HollowPurpleSubscriber subscriber : subscribers) {
            System.out.println("Notified");
            subscriber.updateHollow();
        }
    }
}
