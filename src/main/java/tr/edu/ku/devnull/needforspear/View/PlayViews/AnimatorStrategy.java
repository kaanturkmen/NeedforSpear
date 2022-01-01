package tr.edu.ku.devnull.needforspear.View.PlayViews;

import java.awt.*;

/**
 * The strategy pattern for drawing different animations
 * by game object types: Sphere and Obstacle
 *
 * @author Melis Oktayoğlu
 */

public interface AnimatorStrategy {
    void draw(Graphics g);
}
