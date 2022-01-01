package tr.edu.ku.devnull.needforspear.View.PlayViews.Animators;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.Difficulty;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Speed;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.Model.UIModels.NoblePhantasm;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.PlayViews.AnimatorStrategy;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.MovementHandler;

import java.awt.*;
import java.util.List;

/**
 * Class for the animation of sphere with java swing graphics
 *
 * @author Melis Oktayoğlu
 */

public class SphereAnimator implements AnimatorStrategy {

    int radius = Constants.ProportionConstants.RADIUS_OF_THE_SPHERE;
    public static List<Obstacle> listofObstacles;
    Image sphereImage;
    MovementHandler movementHandler = new MovementHandler();

    /**
     * Constructor of the SphereAnimator.
     *
     * @param listofObstacles List of obstacles to be set.
     */
    public SphereAnimator(List<Obstacle> listofObstacles) {
        if (NeedforSpearGame.getInstance().getGameInfo().getDifficultyHandler().getCurrentDifficulty() == Difficulty.NORMAL) {
            NeedforSpearGame.getInstance().getGameInfo().getSphere().setSpeed(new Speed(2, 2));
        } else NeedforSpearGame.getInstance().getGameInfo().getSphere().setSpeed(new Speed(4, 4));
        SphereAnimator.listofObstacles = listofObstacles;
        sphereImage = getSphereImage(Constants.UIConstants.SPHERE_IMAGE);
    }

    /**
     * Creates a sphere image and scaling it.
     *
     * @param path Path of the sphere image.
     * @return Image of the sphere.
     */


    public Image getSphereImage(String path) {
        return Toolkit.getDefaultToolkit().getImage(Constants.UIConstants.USER_DIRECTORY_TO_RESOURCE_FOLDER + path)
                .getScaledInstance(radius*2, radius*2, Image.SCALE_SMOOTH);

    }

    /**
     * Override on AnimatorStrategy to draw sphere graphics
     * reinvoked in GamePanel
     *
     * @param g Graphics to be drawn.
     */
    @Override
    public void draw(Graphics g) {

        if (NeedforSpearGame.getInstance().getGameInfo().getSphere().isMoving()) {
            movementHandler.sphereMovement(g);
            g.setColor(Color.red);
            //new image on the new location
            int x_coordinates_loc = (int) NeedforSpearGame.getInstance().getGameInfo().getSphere().getLocation().getXCoordinates();
            int y_coordinates_loc = (int) NeedforSpearGame.getInstance().getGameInfo().getSphere().getLocation().getYCoordinates();
            g.drawImage(sphereImage, x_coordinates_loc, y_coordinates_loc, radius * 2, radius * 2, null);
        } else {
            NoblePhantasm noblePhantasm = NoblePhantasm.getInstance();
            Location followNoblePhantasm = new Location((noblePhantasm.getLocation().getXCoordinates() + (noblePhantasm.getSize().getWidth() / 2.0) - radius), (noblePhantasm.getLocation().getYCoordinates() - 2 * radius));
            NeedforSpearGame.getInstance().getGameInfo().getSphere().setLocation(followNoblePhantasm);
            int x_coordinates_loc = (int) followNoblePhantasm.getXCoordinates();
            int y_coordinates_loc = (int) followNoblePhantasm.getYCoordinates();
            g.drawImage(sphereImage, x_coordinates_loc, y_coordinates_loc, radius * 2, radius * 2, null);
        }
    }

}