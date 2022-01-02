package tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers;

import tr.edu.ku.devnull.needforspear.View.PlayViews.SwitchModeSubscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Using controller pattern to handle switching to running mode with an observer.
 * a pure fabrication class
 *
 * @author Melis
 */
public class SwitchModeHandler {

    List<SwitchModeSubscriber> subscribers = new ArrayList<>();

    private static SwitchModeHandler onlyInstance = null;

    /**
     * Singleton constructor
     *
     * @return
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
     * @param panel
     */
    public void subscribe(SwitchModeSubscriber panel) {
        subscribers.add(panel);
    }

    /**
     * Removes panel from subscribers of the event.
     *
     * @param panel
     */
    public void unSubscribe(SwitchModeSubscriber panel) {
        subscribers.remove(panel);
    }

    /**
     *  Updates subscribers.
     */

    public void notifySubscribers() {
        for (SwitchModeSubscriber subscriber : subscribers) {
            subscriber.update();
        }
    }
}
