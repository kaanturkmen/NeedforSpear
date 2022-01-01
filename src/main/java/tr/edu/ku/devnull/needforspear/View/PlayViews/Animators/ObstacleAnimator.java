package tr.edu.ku.devnull.needforspear.View.PlayViews.Animators;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMode;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.View.PlayViews.AnimatorStrategy;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.MovementHandler;

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

    public static List<Obstacle> listofObstacles;
    private MovementHandler movementHandler = new MovementHandler();

    Image explosiveObstacleImage;
    Image firmObstacleImage;
    Image simpleObstacleImage;
    Image giftObstacleImage;
    Image hollowObstacleImage;


    public ObstacleAnimator(List<Obstacle> listofObstacles) {

        this.listofObstacles = listofObstacles;
        explosiveObstacleImage = getObstacleImage(Constants.UIConstants.EXPLOSIVE_OBSTACLE);
        firmObstacleImage = getObstacleImage(Constants.UIConstants.FIRM_OBSTACLE);
        giftObstacleImage = getObstacleImage(Constants.UIConstants.GIFT_OBSTACLE);
        simpleObstacleImage = getObstacleImage(Constants.UIConstants.SIMPLE_OBSTACLE);
        hollowObstacleImage = getObstacleImage(Constants.UIConstants.HOLLOW_OBSTACLE);
    }

    /**
     * Creates a obstacle image and scaling it.
     *
     * @param path Path of the obstacle image.
     * @return Image of the obstacle.
     */

    public Image getObstacleImage(String path) {
        return Toolkit.getDefaultToolkit().getImage(Constants.UIConstants.USER_DIRECTORY_TO_RESOURCE_FOLDER + path)
                .getScaledInstance(Constants.ProportionConstants.RADIUS_OF_EXPLOSIVE_OBSTACLE * 2, Constants.ProportionConstants.RADIUS_OF_EXPLOSIVE_OBSTACLE * 2, Image.SCALE_SMOOTH);

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
            g2.setColor(obs.retrieveColorEquivalent(obs.getColor()));
            int x_coordinates_loc = (int) loc.getXCoordinates();
            int y_coordinates_loc = (int) loc.getYCoordinates();

            if (obs.getObstacleType().equals(Constants.ObstacleNameConstants.EXPLOSIVE_OBSTACLE)) {
                g2.drawImage(explosiveObstacleImage, x_coordinates_loc, y_coordinates_loc, width, length, null);

            } else if (obs.getObstacleType().equals(Constants.ObstacleNameConstants.FIRM_OBSTACLE)) {
                g2.drawImage(firmObstacleImage, x_coordinates_loc, y_coordinates_loc, width, length, null);

            } else if (obs.getObstacleType().equals(Constants.ObstacleNameConstants.SIMPLE_OBSTACLE)) {
                g2.drawImage(simpleObstacleImage, x_coordinates_loc, y_coordinates_loc, width, length, null);

            } else if (obs.getObstacleType().equals(Constants.ObstacleNameConstants.GIFT_OBSTACLE)) {
                g2.drawImage(giftObstacleImage, x_coordinates_loc, y_coordinates_loc, width, length, null);

            } else if (obs.getObstacleType().equals(Constants.ObstacleNameConstants.HOLLOW_PURPLE_OBSTACLE)) {
                g2.drawImage(hollowObstacleImage, x_coordinates_loc, y_coordinates_loc, width, length, null);
            }
            if (obs.getObstacleType().equals(Constants.ObstacleNameConstants.FIRM_OBSTACLE)) {
                displayFirmObstacleHealth(g2, obs, loc);
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
        int x_coordinates_loc = (int) loc.getXCoordinates();
        int y_coordinates_loc = (int) loc.getYCoordinates();
        if (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.EXPLOSIVE_OBSTACLE)) {
            g2.drawImage(explosiveObstacleImage, x_coordinates_loc, y_coordinates_loc, width, length, null);

        } else if (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.FIRM_OBSTACLE)) {
            g2.drawImage(firmObstacleImage, x_coordinates_loc, y_coordinates_loc, width, length, null);
        } else if (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.SIMPLE_OBSTACLE)) {
            g2.drawImage(simpleObstacleImage, x_coordinates_loc, y_coordinates_loc, width, length, null);
        } else if (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.GIFT_OBSTACLE)) {
            g2.drawImage(giftObstacleImage, x_coordinates_loc, y_coordinates_loc, width, length, null);
        } else if (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.HOLLOW_PURPLE_OBSTACLE)) {
            g2.drawImage(hollowObstacleImage, x_coordinates_loc, y_coordinates_loc, width, length, null);
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

        if (NeedforSpearGame.getInstance().getGameInfo().getGameMode() != GameMode.BUILDING_MODE) {
            for (int i = 0; i < listofObstacles.size(); i++) {
                Obstacle explosiveObstacle = listofObstacles.get(i);

                if (explosiveObstacle.getObstacleType().equals(Constants.ObstacleNameConstants.EXPLOSIVE_OBSTACLE)) {
                    int x = (int) explosiveObstacle.getLocation().getXCoordinates();
                    int y = (int) explosiveObstacle.getLocation().getYCoordinates();
                    int width = explosiveObstacle.getSize().getWidth();
                    int length = explosiveObstacle.getSize().getLength();
                    movementHandler.explosiveAnimation(g2, explosiveObstacle);
                    g2.drawImage(explosiveObstacleImage, x, y, width, length, null);
                }

                //TODO these could be removed from here
                if (explosiveObstacle.getSpeed() != 0 && (explosiveObstacle.getObstacleType().equals(Constants.ObstacleNameConstants.FIRM_OBSTACLE) || explosiveObstacle.getObstacleType().equals(Constants.ObstacleNameConstants.SIMPLE_OBSTACLE))) {
                    Location newLoc = movementHandler.moveObstacleHorizontally(explosiveObstacle, listofObstacles);
                    explosiveObstacle.setLocation(newLoc);
                    int x = (int) newLoc.getXCoordinates();
                    int y = (int) newLoc.getYCoordinates();
                    int width = explosiveObstacle.getSize().getWidth();
                    int length = explosiveObstacle.getSize().getLength();
                    if (explosiveObstacle.getObstacleType().equals(Constants.ObstacleNameConstants.FIRM_OBSTACLE)) {
                        g2.drawImage(firmObstacleImage, x, y, width, length, null);
                    } else if (explosiveObstacle.getObstacleType().equals(Constants.ObstacleNameConstants.SIMPLE_OBSTACLE)) {
                        g2.drawImage(simpleObstacleImage, width, length, width, length, null);
                    }

                    if (explosiveObstacle.getObstacleType().equals(Constants.ObstacleNameConstants.FIRM_OBSTACLE)) {
                        displayFirmObstacleHealth(g2, explosiveObstacle, newLoc);
                    }

                }
                movementHandler.removingObstacles(explosiveObstacle);
            }
        }
    }

    /**
     * draws the firm obstacles' health inside their rectangle
     *
     * @param g2
     */
    //solution taken from https://coderanch.com/t/342611/java/Text-surrounded-rectangle
    private void displayFirmObstacleHealth(Graphics2D g2, Obstacle obstacle, Location location) {
        Font font = g2.getFont().deriveFont(16f);
        g2.setFont(font);
        String text = String.valueOf(obstacle.getHealth());
        FontRenderContext frc = g2.getFontRenderContext();
        int textWidth = (int) font.getStringBounds(text, frc).getWidth();
        LineMetrics lm = font.getLineMetrics(text, frc);
        int textHeight = (int) (lm.getAscent() + lm.getDescent());
        int x_coordinates_loc = (int) location.getXCoordinates();
        int sx = x_coordinates_loc + (obstacle.getSize().getWidth() - textWidth) / 2;
        int sy = (int) (location.getYCoordinates() + (obstacle.getSize().getLength() + textHeight) / 2 - lm.getDescent());
        g2.setColor(Color.BLACK);
        g2.drawString(text, sx, sy);
    }

}
