package tr.edu.ku.devnull.needforspear.Model.Obstacle;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;

/**
 * Subscriber design pattern implementation to verify the detection of obstacle events to the developers.
 * <p>
 * Being used for the testing purposes.
 */
public class FirstObstacleSubscriber implements ObstacleSubscriber {
    private boolean destroyed = false;

    /**
     * Updates if the obstacle is destroyed.
     *
     * @param obstacle Obstacle to be destroyed.
     */
    @Override
    public void update(Obstacle obstacle) {
        System.out.println(Constants.MessageConstants.OBSTACLE_SUBSCRIBER_MESSAGE);
        destroyed = true;
    }

    /**
     * Gets if the obstacle is destroyed.
     *
     * @return Boolean value indicating if the obstacle is destroyed.
     */
    public boolean isDestroyed() {
        return destroyed;
    }
}
