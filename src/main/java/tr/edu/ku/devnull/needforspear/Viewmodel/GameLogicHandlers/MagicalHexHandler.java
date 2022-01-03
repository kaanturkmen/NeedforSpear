package tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers;

import tr.edu.ku.devnull.needforspear.View.PlayViews.MagicalHexSubscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler for the Magical Hex.
 */
public class MagicalHexHandler {

    private static MagicalHexHandler onlyInstance = null;
    private final List<MagicalHexSubscriber> subscribers = new ArrayList<>();

    public MagicalHexHandler() {
    }

    /**
     * Singleton Design Pattern's getInstance Method.
     *
     * @return Single instance of MagicalHexHandler.
     */
    public static synchronized MagicalHexHandler getInstance() {
        if (onlyInstance == null) {
            onlyInstance = new MagicalHexHandler();
        }

        return onlyInstance;
    }

    /**
     * Adds panel to subscribers of the event.
     *
     * @param panel Panel which is going to be subscribed to this object.
     */
    public void subscribe2(MagicalHexSubscriber panel) {
        subscribers.add(panel);
    }

    /**
     * Removes the panel from subscribers of the event.
     *
     * @param panel Panel which is going to be unsubscribed to this object.
     */
    public void unSubscribe(MagicalHexSubscriber panel) {
        subscribers.remove(panel);
    }

    /**
     * Notifies the subscribers of the object.
     */
    public void notifySubscribers() {
        for (MagicalHexSubscriber subscriber : subscribers) {
            subscriber.updateHex();
        }
    }
}

