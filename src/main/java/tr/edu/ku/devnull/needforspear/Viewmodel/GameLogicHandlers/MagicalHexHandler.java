package tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers;

import tr.edu.ku.devnull.needforspear.View.PlayViews.MagicalHexSubscriber;

import java.util.ArrayList;
import java.util.List;


public class MagicalHexHandler {

        List<MagicalHexSubscriber> subscribers = new ArrayList<>();

        private static MagicalHexHandler onlyInstance = null;

        /**
         * Singleton constructor
         *
         * @return
         */
        public static synchronized MagicalHexHandler getInstance() {
            if (onlyInstance == null) {
                onlyInstance = new MagicalHexHandler();
            }

            return onlyInstance;
        }

        /**
         * Adds panel to subscribers of the event
         *
         * @param panel
         */
        public void subscribe2(MagicalHexSubscriber panel) {
            subscribers.add(panel);
        }


        public void unSubscribe(MagicalHexSubscriber panel) {
            subscribers.remove(panel);
        }

        public void notifySubscribers() {
            for (MagicalHexSubscriber subscriber : subscribers) {
                subscriber.update2();
            }
        }
    }

