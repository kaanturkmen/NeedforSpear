package tr.edu.ku.devnull.needforspear.Model.Obstacle;

import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;

/**
 * ObstacleSubscriber is an interface to apply Observer Design Pattern.
 *
 */
public interface ObstacleSubscriber {
   void update(Obstacle obstacle);
}
