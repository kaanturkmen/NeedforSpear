package tr.edu.ku.devnull.needforspear.View.PlayViews.Animators;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMode;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.Model.UIModels.Bullet;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.PlayViews.AnimatorStrategy;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.BackgroundHandler;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.MovementHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * BulletAnimator is created for the creating animations for the bullets.
 */
public class BulletAnimator implements AnimatorStrategy {
    private static List<Obstacle> listOfObstacles;
    private static List<Bullet> listOfBullets;
    private final Image bulletImage;
    private final MovementHandler movementHandler = new MovementHandler();

    /**
     * Constructor of the BulletAnimator.
     *
     * @param listOfObstacles List of obstacles to be set.
     */
    public BulletAnimator(List<Obstacle> listOfObstacles) {
        BulletAnimator.listOfObstacles = listOfObstacles;
        listOfBullets = new ArrayList<>();
        BackgroundHandler backgroundHandler = new BackgroundHandler();
        bulletImage = backgroundHandler.getBackgroundImage(Constants.UIConstants.BULLET_IMAGE);
    }

    /**
     * Gets the list of bullets.
     *
     * @return List of bullets.
     */
    public static List<Bullet> getListOfBullets() {
        return listOfBullets;
    }

    /**
     * Draw method of swing library.
     *
     * @param g Graphics to be drawn.
     */
    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        bulletMovement(g2);
    }

    /**
     * Determines the bullet movement.
     *
     * @param g2 2D Graphics to be rendered.
     */
    private void bulletMovement(Graphics2D g2) {
        if (NeedforSpearGame.getInstance().getGameInfo().getGameMode() != GameMode.BUILDING_MODE) {

            // Do not use enhanced for: Since enhanced for uses iterator to iterate, it is not compatible with Java Swing Thread.
            for (int i = 0; i < listOfBullets.size(); i++) {
                Bullet bullet = listOfBullets.get(i);
                if (bullet != null) {
                    Location newLocBullet = movementHandler.updateBulletMovement(bullet);
                    bullet.setLocation(newLocBullet);
                    g2.drawImage(bulletImage, (int) newLocBullet.getXCoordinates(), (int) newLocBullet.getYCoordinates(), Constants.ProportionConstants.RADIUS_OF_THE_BULLET * 2, Constants.ProportionConstants.RADIUS_OF_THE_BULLET * 2, null);
                    movementHandler.checkForCollisions(listOfObstacles, bullet);
                }
            }
        }
    }
}

