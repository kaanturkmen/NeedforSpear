package tr.edu.ku.devnull.needforspear.View.PlayViews.Animators;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMode;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.Model.UIModels.Bullet;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.PlayViews.AnimatorStrategy;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.CollisionHandler;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.MovementHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * BulletAnimator is created for the creating animations for the bullets.
 */
public class BulletAnimator implements AnimatorStrategy {

    public static List<Obstacle> listOfObstacles;
    public static List<Bullet> listOfBullets;
    int radius = Constants.ProportionConstants.RADIUS_OF_THE_BULLET;
    Image bulletImage;
    private final MovementHandler movementHandler = new MovementHandler();
    private final CollisionHandler collisionHandler = new CollisionHandler();

    /**
     * Constructor of the BulletAnimator.
     *
     * @param listOfObstacles List of obstacles to be set.
     */
    public BulletAnimator(List<Obstacle> listOfObstacles) {
        BulletAnimator.listOfObstacles = listOfObstacles;
        listOfBullets = new ArrayList<>();
        bulletImage = getBulletImage(Constants.UIConstants.BULLET_IMAGE);
    }

    /**
     * Creates a bullet image and scaling it.
     *
     * @param path Path of the bullet image.
     * @return Image of the bullet.
     */
    public Image getBulletImage(String path) {
        return Toolkit.getDefaultToolkit().getImage(Constants.UIConstants.USER_DIRECTORY_TO_RESOURCE_FOLDER + path)
                .getScaledInstance(radius * 2, radius * 2, Image.SCALE_SMOOTH);

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
            for (int i = 0; i < listOfBullets.size(); i++) {
                Bullet bullet = listOfBullets.get(i);
                if (bullet != null) {
                    Location newLocBullet = movementHandler.updateBulletMovement(bullet);
                    bullet.setLocation(newLocBullet);
                    g2.drawImage(bulletImage, (int) newLocBullet.getXCoordinates(), (int) newLocBullet.getYCoordinates(), radius * 2, radius * 2, null);
                    movementHandler.checkForCollisions(listOfObstacles, bullet);
                }
            }
        }
    }
}

