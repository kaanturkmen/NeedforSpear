package tr.edu.ku.devnull.needforspear.View.PlayViews.Animators;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.Difficulty;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Speed;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.Model.UIModels.NoblePhantasm;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.PlayViews.AnimatorStrategy;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.BackgroundHandler;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.MovementHandler;

import java.awt.*;
import java.util.List;

/**
 * Class for the animation of sphere with java swing graphics
 *
 * @author Melis Oktayoğlu
 */

public class SphereAnimator implements AnimatorStrategy {
    private static List<Obstacle> listofObstacles;
    private final Image sphereImage;
    private final MovementHandler movementHandler = new MovementHandler();

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
        sphereImage = new BackgroundHandler().getRespectiveImage(Constants.UIConstants.SPHERE_IMAGE);
    }

    /**
     * Gets the list of obstacle.
     *
     * @return List of obstacle.
     */
    public static List<Obstacle> getListofObstacles() {
        return listofObstacles;
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
            g.drawImage(sphereImage, x_coordinates_loc, y_coordinates_loc, Constants.ProportionConstants.RADIUS_OF_THE_SPHERE * 2, Constants.ProportionConstants.RADIUS_OF_THE_SPHERE * 2, null);
        } else {
            NoblePhantasm noblePhantasm = NoblePhantasm.getInstance();
            Location followNoblePhantasm = new Location((noblePhantasm.getLocation().getXCoordinates() + (noblePhantasm.getSize().getWidth() / 2.0) - Constants.ProportionConstants.RADIUS_OF_THE_SPHERE), (noblePhantasm.getLocation().getYCoordinates() - 2 * Constants.ProportionConstants.RADIUS_OF_THE_SPHERE));
            NeedforSpearGame.getInstance().getGameInfo().getSphere().setLocation(followNoblePhantasm);
            int x_coordinates_loc = (int) followNoblePhantasm.getXCoordinates();
            int y_coordinates_loc = (int) followNoblePhantasm.getYCoordinates();
            g.drawImage(sphereImage, x_coordinates_loc, y_coordinates_loc, Constants.ProportionConstants.RADIUS_OF_THE_SPHERE * 2, Constants.ProportionConstants.RADIUS_OF_THE_SPHERE * 2, null);
        }
    }
}