package tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers;

import tr.edu.ku.devnull.needforspear.View.PlayViews.SwitchModeSubscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Using controller pattern to handle switching to running mode with an observer.
 * a pure fabrication class
 *
 * @author Melis Oktayoglu
 */
public class SwitchModeHandler {

    private static SwitchModeHandler onlyInstance = null;
    List<SwitchModeSubscriber> subscribers = new ArrayList<>();

    /**
     * Private constructor of the SwitchModeHandler.
     */
    public SwitchModeHandler() {
    }

    /**
     * Singleton Design Pattern's getInstance method.
     *
     * @return Single instance of SwitchModeHandler.
     */
    public static synchronized SwitchModeHandler getInstance() {
        if (onlyInstance == null) {
            onlyInstance = new SwitchModeHandler();
        }

        return onlyInstance;
    }

    /**
     * Adds panel to subscribers of the event.
     *
     * @param panel Panel to be subscribed to this object.
     */
    public void subscribe(SwitchModeSubscriber panel) {
        subscribers.add(panel);
    }

    /**
     * Removes panel from subscribers of the event.
     *
     * @param panel Panel to be unsubscribed to this object.
     */
    public void unSubscribe(SwitchModeSubscriber panel) {
        subscribers.remove(panel);
    }

    /**
     * Notifies the subscribers.
     */
    public void notifySubscribers() {
        for (SwitchModeSubscriber subscriber : subscribers) {
            subscriber.update();
        }
    }
}
