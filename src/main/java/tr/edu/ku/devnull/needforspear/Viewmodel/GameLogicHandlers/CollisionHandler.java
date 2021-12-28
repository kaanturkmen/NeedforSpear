package tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Size;

import tr.edu.ku.devnull.needforspear.Model.Obstacle.ExplosiveObstacle;

import tr.edu.ku.devnull.needforspear.Model.Obstacle.GiftObstacle;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.Model.Player.Player;
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
     * @param obstacle
     * @param sphere
     * @return true if there is collision between sphere and obstacle
     */
    public boolean collision(Obstacle obstacle, Sphere sphere) {
        Location obsLoc = obstacle.getLocation();
        Size obsSize = obstacle.getSize();
        int x_coordinates_loc = (int) obsLoc.getXCoordinates().doubleValue();
        int y_coordinates_loc = (int) obsLoc.getYCoordinates().doubleValue();
        Rectangle obsRect = new Rectangle(x_coordinates_loc, y_coordinates_loc, obsSize.getWidth(), obsSize.getLength());
        Location sphereLoc = sphere.getLocation();
        int diameter = 2 * Constants.ProportionConstants.RADIUS_OF_THE_SPHERE;
        int x_coordinates_sphereloc = (int) sphereLoc.getXCoordinates().doubleValue();
        int y_coordinates_sphereloc = (int) sphereLoc.getYCoordinates().doubleValue();
        return obsRect.getBounds().intersects(new Rectangle(x_coordinates_sphereloc, y_coordinates_sphereloc, diameter, diameter));

    }

    /**
     * Collision checker if two obstacles collide
     *
     * @param obstacle
     * @param obstacle2
     * @return true if obstacle and obstacle2 collide
     */
    public boolean collision(Obstacle obstacle, Obstacle obstacle2) {
        Location obsLoc = obstacle.getLocation();
        Size obsSize = obstacle.getSize();
        int x_coordinates_loc = (int) obsLoc.getXCoordinates().doubleValue();
        int y_coordinates_loc = (int) obsLoc.getYCoordinates().doubleValue();
        Rectangle obsRect = new Rectangle(x_coordinates_loc, y_coordinates_loc, obsSize.getWidth(), obsSize.getLength());
        Location obstacle2Location = obstacle2.getLocation();
        Size obs2Size = obstacle2.getSize();
        int x_coordinates_obstacle2loc = (int) obstacle2Location.getXCoordinates().doubleValue();
        int y_coordinates_obstacle2loc = (int) obstacle2Location.getYCoordinates().doubleValue();
        return obsRect.getBounds().intersects(new Rectangle(x_coordinates_obstacle2loc, y_coordinates_obstacle2loc, obs2Size.getWidth(), obs2Size.getLength()));

    }

    /**
     * Collision checker if noble phantasm and sphere collide
     *
     * @param noblePhantasm
     * @param sphere
     * @return true if there is collision
     */

    public boolean collision(NoblePhantasm noblePhantasm, Sphere sphere) {
        Location noblePhantasmLoc = noblePhantasm.getLocation();
        Size noblePhantasmSize = noblePhantasm.getSize();
        int x_coordinates_nploc = (int) noblePhantasmLoc.getXCoordinates().doubleValue();
        int y_coordinates_nploc = (int) noblePhantasmLoc.getYCoordinates().doubleValue();
        Rectangle noblePhantasmRect = new Rectangle(x_coordinates_nploc, y_coordinates_nploc, noblePhantasmSize.getWidth(), noblePhantasmSize.getLength());
        Location sphereLoc = sphere.getLocation();
        int diameter = 2 * Constants.ProportionConstants.RADIUS_OF_THE_SPHERE;
        int x_coordinates_sphereloc = (int) sphereLoc.getXCoordinates().doubleValue();
        int y_coordinates_sphereloc = (int) sphereLoc.getYCoordinates().doubleValue();
        return noblePhantasmRect.getBounds().intersects(new Rectangle(x_coordinates_sphereloc, y_coordinates_sphereloc, diameter, diameter));

    }
    public boolean collision (NoblePhantasm noblePhantasm, Spell spell){
        Location noblePhantasmLoc = noblePhantasm.getLocation();
        Size noblePhantasmSizeSize = noblePhantasm.getSize();
        Rectangle noblePhantasmRect = new Rectangle(noblePhantasmLoc.getXCoordinates().intValue(),
                noblePhantasmLoc.getYCoordinates().intValue(), noblePhantasmSizeSize.getWidth(), noblePhantasmSizeSize.getWidth());
        Location spellLoc = spell.getLocation();
        Size spellSize = spell.getSize();
        return noblePhantasmRect.getBounds().intersects(new Rectangle(spellLoc.getXCoordinates().intValue(),
                spellLoc.getYCoordinates().intValue(), spellSize.getWidth(), spellSize.getLength()));
    }

    public boolean collision(Obstacle obstacle, Bullet bullet) {
        Location obsLoc = obstacle.getLocation();
        Size obsSize = obstacle.getSize();
        int x_coordinates_obsloc = (int) obsLoc.getXCoordinates().doubleValue();
        int y_coordinates_obsloc = (int) obsLoc.getYCoordinates().doubleValue();
        Rectangle obsRect = new Rectangle(x_coordinates_obsloc, y_coordinates_obsloc, obsSize.getWidth(), obsSize.getLength());
            Location sphereLoc = bullet.getLocation();
            int diameter = 2 * Constants.ProportionConstants.RADIUS_OF_THE_BULLET;
            int x_coordinates_bulletloc = (int) sphereLoc.getXCoordinates().doubleValue();
            int y_coordinates_bulletloc = (int) sphereLoc.getYCoordinates().doubleValue();
            return obsRect.getBounds().intersects(new Rectangle(x_coordinates_bulletloc, y_coordinates_bulletloc, diameter, diameter));
    }

    public boolean collisionWithExplosive(Obstacle obstacle, NoblePhantasm noblePhantasm){
        Location noblePhantasmLoc = noblePhantasm.getLocation();
        Size noblePhantasmSize = noblePhantasm.getSize();
        int x_np = (int) noblePhantasmLoc.getXCoordinates().doubleValue();
        int y_np = (int) noblePhantasmLoc.getYCoordinates().doubleValue();

        Rectangle noblePhantasmRect = new Rectangle(x_np, y_np, noblePhantasmSize.getWidth(), noblePhantasmSize.getWidth());
        Location obstacleLocation = obstacle.getLocation();
        int x_obs = (int) obstacleLocation.getXCoordinates().doubleValue();
        int y_obs = (int) obstacleLocation.getYCoordinates().doubleValue();

        int diameter = obstacle.getSize().getWidth();
        Rectangle obstacleRect = new Rectangle(x_obs, y_obs, diameter, diameter);


        return noblePhantasmRect.getBounds().intersects(obstacleRect);

    }

    public boolean collisionWithExplosiveOrbit(Rectangle orbit, Obstacle obstacle){
        Location obstacleLocation = obstacle.getLocation();
        int x_obs = (int) obstacleLocation.getXCoordinates().doubleValue();
        int y_obs = (int) obstacleLocation.getYCoordinates().doubleValue();

        int diameter = obstacle.getSize().getWidth();
        Rectangle obstacleRect = new Rectangle(x_obs, y_obs, diameter, diameter);
        return orbit.intersects(obstacleRect);
    }

    /**
     * removes obstacle if enough collisions occurred and updates score by expert principle
     *
     * @param obstacle
     * @return true if removed
     */
    public boolean isRemovedObstacle(Obstacle obstacle) {


        boolean isRemoved = obstacle.getHealth() <= 0;
        
         if (isRemoved ) {


            if(obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.GIFT)){
                SpellAnimator.listOfMovingSpells.add(obstacle.getSpell());
            }
            //listofObstacles.remove(obstacle);
        }
    

        return isRemoved;
    }

    public void removeObstacle(Obstacle obstacle,  List<Obstacle> listofObstacles){
        if(!obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.HOLLOW)) {
            PlayerScoreHandler.getInstance().updateScore(NeedforSpearGame.getInstance().getGameInfo().getPlayer());
        }
        listofObstacles.remove(obstacle);
    }

       
    


}




