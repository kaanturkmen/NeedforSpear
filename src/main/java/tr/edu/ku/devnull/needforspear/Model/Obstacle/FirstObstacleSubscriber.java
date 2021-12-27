package tr.edu.ku.devnull.needforspear.Model.Obstacle;

import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.ObstacleSubscriber;

/**
 * Subscriber design pattern implementation to verify the detection of obstacle events to the developers.
 */
public class FirstObstacleSubscriber implements ObstacleSubscriber {
    private boolean destroyed= false;
    @Override
    public void update(Obstacle obstacle) {
        System.out.println("I just detected that this obstacle got destroyed! I must set my isDestroyed value to true!");
        destroyed=true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
