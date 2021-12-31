package tr.edu.ku.devnull.needforspear.View.PlayViews.Animators;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMode;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.Model.UIModels.Bullet;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.PlayViews.AnimatorStrategy;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.MovementHandler;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.CollisionHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BulletAnimator implements AnimatorStrategy {

    int radius = Constants.ProportionConstants.RADIUS_OF_THE_BULLET;
    public static List<Obstacle> listOfObstacles;
    public static List<Bullet> listOfBullets;
    private MovementHandler movementHandler = new MovementHandler();
    private CollisionHandler collisionHandler = new CollisionHandler();
    Image bulletImage;

    /**
     * Constructor
     *
     * @param listOfObstacles
     */
    public BulletAnimator(List<Obstacle> listOfObstacles) {
        this.listOfObstacles = listOfObstacles;
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
                .getScaledInstance(radius*2, radius*2, Image.SCALE_SMOOTH);

    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        bulletMovement(g2);
    }

    private void bulletMovement(Graphics2D g2) {
        if (NeedforSpearGame.getInstance().getGameInfo().getGameMode() != GameMode.BUILDING_MODE) {
            for (int i = 0; i < listOfBullets.size(); i++) {
                Bullet bullet = listOfBullets.get(i);
                if (bullet != null) {
                    Location newLocBullet = movementHandler.updateBulletMovement(bullet);
                    bullet.setLocation(newLocBullet);
                    g2.drawImage(bulletImage, newLocBullet.getXCoordinates().intValue(), newLocBullet.getYCoordinates().intValue(), radius * 2, radius * 2, null);
                    movementHandler.checkForCollisions(listOfObstacles, bullet);
                }
            }
        }
    }
}

