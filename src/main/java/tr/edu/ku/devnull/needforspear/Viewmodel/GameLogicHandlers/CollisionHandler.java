package tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Size;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.Model.Spell.Spell;
import tr.edu.ku.devnull.needforspear.Model.UIModels.Bullet;
import tr.edu.ku.devnull.needforspear.Model.UIModels.NoblePhantasm;
import tr.edu.ku.devnull.needforspear.Model.UIModels.Sphere;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.PlayViews.Animators.SpellAnimator;

import java.awt.*;
import java.util.List;

/**
 * CollisionHandler performs computations for detecting collisions
 * among different types of game objects
 *
 * @author Melis Oktayoğlu
 */

public class CollisionHandler {

    /**
     * Collision checker between an obstacle and the sphere
     *
     * @param obstacle Obstacle to be checked.
     * @param sphere   Sphere to be checked.
     * @return true if there is collision between sphere and obstacle.
     */
    public boolean collision(Obstacle obstacle, Sphere sphere) {
        Location obsLoc = obstacle.getLocation();
        Size obsSize = obstacle.getSize();
        int x_coordinates_loc = (int) obsLoc.getXCoordinates();
        int y_coordinates_loc = (int) obsLoc.getYCoordinates();
        Rectangle obsRect = new Rectangle(x_coordinates_loc, y_coordinates_loc, obsSize.getWidth(), obsSize.getLength());
        Location sphereLoc = sphere.getLocation();
        int diameter = 2 * Constants.ProportionConstants.RADIUS_OF_THE_SPHERE;
        int x_coordinates_sphereloc = (int) sphereLoc.getXCoordinates();
        int y_coordinates_sphereloc = (int) sphereLoc.getYCoordinates();
        return obsRect.getBounds().intersects(new Rectangle(x_coordinates_sphereloc, y_coordinates_sphereloc, diameter, diameter));

    }

    /**
     * Collision checker if two obstacles collide
     *
     * @param obstacle  Obstacle to be checked.
     * @param obstacle2 Other obstacle to be checked.
     * @return True if obstacle and obstacle2 collide.
     */
    public boolean collision(Obstacle obstacle, Obstacle obstacle2) {
        Location obsLoc = obstacle.getLocation();
        Size obsSize = obstacle.getSize();
        int x_coordinates_loc = (int) obsLoc.getXCoordinates();
        int y_coordinates_loc = (int) obsLoc.getYCoordinates();
        Rectangle obsRect = new Rectangle(x_coordinates_loc, y_coordinates_loc, obsSize.getWidth(), obsSize.getLength());
        Location obstacle2Location = obstacle2.getLocation();
        Size obs2Size = obstacle2.getSize();
        int x_coordinates_obstacle2loc = (int) obstacle2Location.getXCoordinates();
        int y_coordinates_obstacle2loc = (int) obstacle2Location.getYCoordinates();
        return obsRect.getBounds().intersects(new Rectangle(x_coordinates_obstacle2loc, y_coordinates_obstacle2loc, obs2Size.getWidth(), obs2Size.getLength()));

    }

    /**
     * Collision checker if noble phantasm and sphere collide
     *
     * @param noblePhantasm Noble phantasm to be checked.
     * @param sphere        Sphere to be checked.
     * @return True if there is collision.
     */
    public boolean collision(NoblePhantasm noblePhantasm, Sphere sphere) {
        Location noblePhantasmLoc = noblePhantasm.getLocation();
        Size noblePhantasmSize = noblePhantasm.getSize();
        int x_coordinates_nploc = (int) noblePhantasmLoc.getXCoordinates();
        int y_coordinates_nploc = (int) noblePhantasmLoc.getYCoordinates();
        Rectangle noblePhantasmRect = new Rectangle(x_coordinates_nploc, y_coordinates_nploc, noblePhantasmSize.getWidth(), noblePhantasmSize.getLength());
        Location sphereLoc = sphere.getLocation();
        int diameter = 2 * Constants.ProportionConstants.RADIUS_OF_THE_SPHERE;
        int x_coordinates_sphereloc = (int) sphereLoc.getXCoordinates();
        int y_coordinates_sphereloc = (int) sphereLoc.getYCoordinates();
        return noblePhantasmRect.getBounds().intersects(new Rectangle(x_coordinates_sphereloc, y_coordinates_sphereloc, diameter, diameter));

    }

    /**
     * Collision checker between spell and noble phantasm.
     *
     * @param noblePhantasm Noble phantasm to be checked.
     * @param spell         Spell to be checked.
     * @return true if there is a collision.
     */
    public boolean collision(NoblePhantasm noblePhantasm, Spell spell) {
        Location noblePhantasmLoc = noblePhantasm.getLocation();
        Size noblePhantasmSizeSize = noblePhantasm.getSize();
        Rectangle noblePhantasmRect = new Rectangle((int) noblePhantasmLoc.getXCoordinates(),
                (int) noblePhantasmLoc.getYCoordinates(), noblePhantasmSizeSize.getWidth(), noblePhantasmSizeSize.getWidth());
        Location spellLoc = spell.getLocation();
        Size spellSize = spell.getSize();
        return noblePhantasmRect.getBounds().intersects(new Rectangle((int) spellLoc.getXCoordinates(),
                (int) spellLoc.getYCoordinates(), spellSize.getWidth(), spellSize.getLength()));
    }

    /**
     * Collision checker between obstacle and bullet.
     *
     * @param obstacle Obstacle to be checked.
     * @param bullet   Bullet to be checked.
     * @return True if there is a collision.
     */
    public boolean collision(Obstacle obstacle, Bullet bullet) {
        Location obsLoc = obstacle.getLocation();
        Size obsSize = obstacle.getSize();
        int x_coordinates_obsloc = (int) obsLoc.getXCoordinates();
        int y_coordinates_obsloc = (int) obsLoc.getYCoordinates();
        Rectangle obsRect = new Rectangle(x_coordinates_obsloc, y_coordinates_obsloc, obsSize.getWidth(), obsSize.getLength());
        Location sphereLoc = bullet.getLocation();
        int diameter = 2 * Constants.ProportionConstants.RADIUS_OF_THE_BULLET;
        int x_coordinates_bulletloc = (int) sphereLoc.getXCoordinates();
        int y_coordinates_bulletloc = (int) sphereLoc.getYCoordinates();
        return obsRect.getBounds().intersects(new Rectangle(x_coordinates_bulletloc, y_coordinates_bulletloc, diameter, diameter));
    }

    /**
     * Collision checker between Explosive Obstacle and noble phantasm.
     *
     * @param obstacle      Obstacle to be checked.
     * @param noblePhantasm Noble phantasm to be checked.
     * @return True if there is a collision.
     */
    public boolean collisionWithExplosive(Obstacle obstacle, NoblePhantasm noblePhantasm) {
        Location noblePhantasmLoc = noblePhantasm.getLocation();
        Size noblePhantasmSize = noblePhantasm.getSize();
        int x_np = (int) noblePhantasmLoc.getXCoordinates();
        int y_np = (int) noblePhantasmLoc.getYCoordinates();

        Rectangle noblePhantasmRect = new Rectangle(x_np, y_np, noblePhantasmSize.getWidth(), noblePhantasmSize.getWidth());
        Location obstacleLocation = obstacle.getLocation();
        int x_obs = (int) obstacleLocation.getXCoordinates();
        int y_obs = (int) obstacleLocation.getYCoordinates();

        int diameter = obstacle.getSize().getWidth();
        Rectangle obstacleRect = new Rectangle(x_obs, y_obs, diameter, diameter);


        return noblePhantasmRect.getBounds().intersects(obstacleRect);

    }

    /**
     * Checks collision with explosive orbit.
     *
     * @param orbit    Orbit to be checked.
     * @param obstacle Obstacle to be checked.
     * @return True if there is a collision.
     */
    public boolean collisionWithExplosiveOrbit(Rectangle orbit, Obstacle obstacle) {
        Location obstacleLocation = obstacle.getLocation();
        int x_obs = (int) obstacleLocation.getXCoordinates();
        int y_obs = (int) obstacleLocation.getYCoordinates();

        int diameter = obstacle.getSize().getWidth();
        Rectangle obstacleRect = new Rectangle(x_obs, y_obs, diameter, diameter);
        return orbit.intersects(obstacleRect);
    }

    /**
     * removes obstacle if enough collisions occurred and updates score by expert principle
     *
     * @param obstacle Obstacle to be checked if it is removed.
     * @return True if removed
     */
    public boolean isRemovedObstacle(Obstacle obstacle) {
        boolean isRemoved = obstacle.getHealth() <= 0;
        if (isRemoved) {
            if (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.GIFT_OBSTACLE)) {
                SpellAnimator.getListOfMovingSpells().add(obstacle.getSpell());
            }
            //listofObstacles.remove(obstacle);
        }
        return isRemoved;
    }

    /**
     * Removes obstacle from the list of obstacles.
     *
     * @param obstacle        Obstacle to be removed.
     * @param listofObstacles List of obstacle to be removed from.
     */
    public void removeObstacle(Obstacle obstacle, List<Obstacle> listofObstacles) {
        if (!obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.HOLLOW_PURPLE_OBSTACLE)) {
            PlayerScoreHandler.getInstance().updateScore(NeedforSpearGame.getInstance().getGameInfo().getPlayer());
        }
        listofObstacles.remove(obstacle);
    }
}




