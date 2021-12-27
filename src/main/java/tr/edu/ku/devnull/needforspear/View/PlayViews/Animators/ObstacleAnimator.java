package tr.edu.ku.devnull.needforspear.View.PlayViews.Animators;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMode;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.Model.UIModels.NoblePhantasm;
import tr.edu.ku.devnull.needforspear.Model.UIModels.Sphere;
import tr.edu.ku.devnull.needforspear.View.PlayViews.AnimatorStrategy;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.MovementHandler;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.CollisionHandler;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.PlayerLivesHandler;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.util.List;

/**
 * Class for animation of Obstacles using java Swing graphics
 *
 * @author Melis Oktayoğlu
 */

public class ObstacleAnimator implements AnimatorStrategy {

    private List<Obstacle> listofObstacles;
    private MovementHandler movementHandler = new MovementHandler();
    CollisionHandler collisionHandler = new CollisionHandler();

    public ObstacleAnimator(List<Obstacle> listofObstacles) {
        this.listofObstacles = listofObstacles;
    }

    /**
     * Override on AnimatorStrategy to draw obstacle graphics
     * reinvoked in GamePanel
     *
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        for (int i = 0; i < listofObstacles.size(); i++) {
            Obstacle obs = listofObstacles.get(i);
            Location loc = obs.getLocation();
            int width = obs.getSize().getWidth();
            int length = obs.getSize().getLength();
            g2.setColor(NeedforSpearGame.getColorEquivalent(obs.getColor()));
            int x_coordinates_loc = loc.getXCoordinates().intValue();
            int y_coordinates_loc = loc.getYCoordinates().intValue();
            if (obs.getObstacleType().equals(Constants.ObstacleNameConstants.EXP)) {

                g2.fillOval(x_coordinates_loc, y_coordinates_loc , width, length);

            } else {
                //to avoid duplicate printing of simple and firm obstacles if moving
                    /*
                    if (NeedforSpearGame.getGameMode()!= GameMode.BUILDING_MODE && obs.getSpeed() != 0) {
                        g2.fillRect(loc.getXCoordinates(), loc.getYCoordinates(), width, length);
                    }else{

                    }*/
                g2.fillRect(x_coordinates_loc, y_coordinates_loc, width, length);
                if(obs.getObstacleType().equals(Constants.ObstacleNameConstants.FIRM)){
                   displayFirmObstacleHealth(g2,obs,loc);
                }
            }
        }
        movementOfObstacles(g2);

    }


    /**
     * To draw single obstacles during build mode
     *
     * @param g
     * @param obstacle (desired obstacle type)
     */
    public void drawSingleObstacle(Graphics g, Obstacle obstacle) {
        Graphics2D g2 = (Graphics2D) g;
        Location loc = obstacle.getLocation();
        int width = obstacle.getSize().getWidth();
        int length = obstacle.getSize().getLength();
        g2.setColor(NeedforSpearGame.getColorEquivalent(obstacle.getColor()));
        int x_coordinates_loc = loc.getXCoordinates().intValue();
        int y_coordinates_loc = loc.getYCoordinates().intValue();
        if (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.EXP)) {
            g2.fillOval(x_coordinates_loc, y_coordinates_loc, width, length);
        } else {
            g2.fillRect(x_coordinates_loc, y_coordinates_loc, width, length);
        }

    }

    /**
     * Animation of movement of the obstacles
     * uses BounceHandler to get new locations of
     * horizontal movement if moves
     *
     * @param g2
     */
    public void movementOfObstacles(Graphics2D g2) {

        if (NeedforSpearGame.getGameMode() != GameMode.BUILDING_MODE) {
            for (int i = 0; i < listofObstacles.size(); i++) {
                Obstacle explosiveObstacle = listofObstacles.get(i);

                if (explosiveObstacle.getObstacleType().equals(Constants.ObstacleNameConstants.EXP)){
                    explosiveAnimation(g2, explosiveObstacle);
                }

                g2.setColor(NeedforSpearGame.getColorEquivalent(explosiveObstacle.getColor()));
                //TODO these could be removed from here
                if (explosiveObstacle.getSpeed() != 0 && (explosiveObstacle.getObstacleType().equals(Constants.ObstacleNameConstants.FIRM)||explosiveObstacle.getObstacleType().equals(Constants.ObstacleNameConstants.SIMPLE) )) {
                    Location newLoc = movementHandler.moveObstacleHorizontally(explosiveObstacle, listofObstacles);
                    explosiveObstacle.setLocation(newLoc);
                    int x =  newLoc.getXCoordinates().intValue();
                    int y =  newLoc.getYCoordinates().intValue();
                    int width = explosiveObstacle.getSize().getWidth();
                    int length = explosiveObstacle.getSize().getLength();
                    g2.fillRect(x, y, width, length);

                    if(explosiveObstacle.getObstacleType().equals(Constants.ObstacleNameConstants.FIRM)){
                        displayFirmObstacleHealth(g2, explosiveObstacle, newLoc);
                    }

                }
                removingObstacles(explosiveObstacle);
            }
        }
    }

    /**
     * animates only the explosive obstacles
     *
     * @param g2       2D graphics
     * @param explosiveObstacle explosive obstacle
     */
    public void explosiveAnimation(Graphics2D g2, Obstacle explosiveObstacle){

        if (collisionHandler.isRemovedObstacle(explosiveObstacle)){

            Location newLoc = movementHandler.moveObstacleDown(explosiveObstacle);
            explosiveObstacle.setLocation(newLoc);
        }else{
            if (!checkIfOrbitCollides(explosiveObstacle.retrieveOrbitBounds())) {
                Location newLoc = movementHandler.circularMotion(explosiveObstacle);
                explosiveObstacle.setLocation(newLoc);
                //System.out.println("x"+ newLoc.getXCoordinates() + "y: " + newLoc.getYCoordinates());
            }
        }

        int x = explosiveObstacle.getLocation().getXCoordinates().intValue();
        int y = explosiveObstacle.getLocation().getYCoordinates().intValue();
        int width = explosiveObstacle.getSize().getWidth();
        int length = explosiveObstacle.getSize().getLength();
        g2.setColor(NeedforSpearGame.getColorEquivalent(explosiveObstacle.getColor()));
        g2.fillOval(x, y, width, length);

    }

    public boolean checkIfOrbitCollides(Rectangle orbit) {

        for (int i = 0; i < listofObstacles.size(); i++) {
            Obstacle obstacle = listofObstacles.get(i);
            if (collisionHandler.collisionWithExplosiveOrbit(orbit, obstacle)) {
                return true;
            }
        }
        return false;
    }

    /**
     * draws the firm obstacles' health inside their rectangle
     * @param g2
     */
    //solution taken from https://coderanch.com/t/342611/java/Text-surrounded-rectangle
    private void displayFirmObstacleHealth(Graphics2D g2, Obstacle obstacle, Location location){
        Font font = g2.getFont().deriveFont(16f);
        g2.setFont(font);
        String text = obstacle.getHealth().toString();
        FontRenderContext frc = g2.getFontRenderContext();
        int textWidth = (int)font.getStringBounds(text, frc).getWidth();
        LineMetrics lm = font.getLineMetrics(text, frc);
        int textHeight = (int)(lm.getAscent() + lm.getDescent());
        int x_coordinates_loc = location.getXCoordinates().intValue();
        int sx = x_coordinates_loc + (obstacle.getSize().getWidth() - textWidth)/2;
        int sy = (int)(location.getYCoordinates() + (obstacle.getSize().getLength() + textHeight)/2 - lm.getDescent());
        g2.setColor(Color.BLACK);
        g2.drawString(text, sx, sy);
    }

    private void removingObstacles(Obstacle obstacle){
        double y_bottom = obstacle.getLocation().getYCoordinates() + obstacle.getSize().getLength();
        PlayerLivesHandler playerLivesHandler = new PlayerLivesHandler(NeedforSpearGame.getPlayer(),Sphere.getInstance(),NoblePhantasm.getInstance());
        //TODO carry all removals here EXPLOSIVE REMOVAL
        if (collisionHandler.isRemovedObstacle(obstacle) && obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.EXP) &&
                (collisionHandler.collisionWithExplosive(obstacle,NoblePhantasm.getInstance()) ||
                        y_bottom > Constants.UIConstants.INITIAL_SCREEN_HEIGHT )){

            if (collisionHandler.collisionWithExplosive(obstacle,NoblePhantasm.getInstance())){
                playerLivesHandler.notifyPlayerExplosiveFall(NoblePhantasm.getInstance());
                System.out.println("player hit by explosive");
            }

            collisionHandler.removeObstacle(obstacle, listofObstacles);



        }
    }
}
