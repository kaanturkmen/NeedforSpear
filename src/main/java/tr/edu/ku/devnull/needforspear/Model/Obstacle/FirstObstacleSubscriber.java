package tr.edu.ku.devnull.needforspear.Model.Obstacle;

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
        System.out.println("I just detected that this obstacle got destroyed! I must set my isDestroyed value to true!");
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
