package tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMode;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Speed;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.Model.Spell.Spell;
import tr.edu.ku.devnull.needforspear.Model.UIModels.Bullet;
import tr.edu.ku.devnull.needforspear.Model.UIModels.NoblePhantasm;
import tr.edu.ku.devnull.needforspear.View.PlayViews.Animators.BulletAnimator;
import tr.edu.ku.devnull.needforspear.View.PlayViews.Animators.ObstacleAnimator;
import tr.edu.ku.devnull.needforspear.View.PlayViews.Animators.SphereAnimator;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameHandlers.SoundHandler;
import tr.edu.ku.devnull.needforspear.Viewmodel.Util.CollisionData;
import tr.edu.ku.devnull.needforspear.Viewmodel.Util.PhysicsEngine;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.PlayViews.Animators.SpellAnimator;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.util.List;

/**
 * This class does the location calculations for movements of sphere, obstacles and NoblePhantasm.
 *
 * @author Melis Oktayoğlu, Gökçe Sevimli
 */
public class MovementHandler {
    double x, y;
    double dx, dy;
    int radius = Constants.ProportionConstants.RADIUS_OF_THE_SPHERE;
    NoblePhantasm noblePhantasm;

    double currentX;
    double currentY;
    private PhysicsEngine physicsEngine = new PhysicsEngine();
    private CollisionHandler collisionHandler = new CollisionHandler();


    /**
     * Sphere's bouncing of the game frame
     */
    public void bounceSphereFromFrame() {
        getSphereCurrentPhysics();
        CollisionData collisionData = physicsEngine.reflect(new CollisionData(new Location(x,y), new Speed(new Double(dx).longValue(),new Double(dy).longValue())));
        checkIfObstacleBelowPhantasm();
        updateSphereMovement(collisionData);
    }

    /**
     * Sphere's bouncing from obstacle objects
     *
     * @param obstacle
     */
    public void bounceSphereFromObstacle(Obstacle obstacle) {
        getSphereCurrentPhysics();
        Location obstacleLocation = obstacle.getLocation();
        double obs_x = obstacleLocation.getXCoordinates();
        double obs_y = obstacleLocation.getYCoordinates();

        int obs_width = obstacle.getSize().getWidth();
        int obs_length = obstacle.getSize().getLength();

        //solution adapted from https://happycoding.io/tutorials/processing/collision-detection
/*
        if (x + 2 * radius + dx > obs_x &&
                x + dx < obs_x + obs_width &&
                y + 2 * radius > obs_y &&
                y < obs_y + obs_length) {
            dx *= -1;
        }

        if (x + 2 * radius > obs_x &&
                x < obs_x + obs_width &&
                y + 2 * radius + dy > obs_y &&
                y + dy < obs_y + obs_length) {
            dy *= -1;
            dx *= -1;
        }
        */
        CollisionData collisionData = null;

        try {
            collisionData = physicsEngine.reflect(new CollisionData(new Location(x,y), new Speed(new Double(dx).longValue(),new Double(dy).longValue())), obstacle);
        } catch (Exception e) {
            System.out.println("Found an exception about obstacle placement.");
        }


        if (collisionData!= null)  {
            SoundHandler.getInstance().playSound("obstacleHitEffect.wav");
            updateSphereMovement(collisionData);
        }

    }

    /**
     * Sphere's bounce from phantasm
     */
    public void bounceSphereFromPhantasm() {
        SoundHandler.getInstance().playSound("phantasmHitEffect.wav");

        getSphereCurrentPhysics();
        dy *= -1;
        y = y - 2 * radius;
        updateSphereMovement(new CollisionData(new Location(x, y), new Speed(new Double(dx).longValue(),new Double(dy).longValue())));
    }

    /**
     * Obstacle's horizontal movement
     *
     * @param obstacle
     * @param listofObstacles
     * @return
     */
    public Location moveObstacleHorizontally(Obstacle obstacle, List<Obstacle> listofObstacles) {
        CollisionHandler collisionHandler = new CollisionHandler();
        double x = obstacle.getLocation().getXCoordinates();
        int width = obstacle.getSize().getWidth();

        double dx = obstacle.getSpeed();

        for (int i = 0; i < listofObstacles.size(); i++) {
            Obstacle obs = listofObstacles.get(i);
            if (obs != obstacle) {
                if (collisionHandler.collision(obs, obstacle) || x + dx < 0 || x + dx + width > Constants.UIConstants.INITIAL_SCREEN_WIDTH) {
                    dx *= -1;
                    obstacle.setSpeed(dx);
                }
            }
        }

        return new Location(x + dx, obstacle.getLocation().getYCoordinates());


    }

    private double angleObstacle = 0.0;

    public Location circularMotion(Obstacle obstacle){
        int width = obstacle.getSize().getWidth();
        int height = obstacle.getSize().getLength();
        double circle_center_x = obstacle.getOrbitCenter().getXCoordinates();
        double circle_center_y = obstacle.getOrbitCenter().getYCoordinates();

        int x = (int) (Math.cos(angleObstacle) * width/3 + circle_center_x);
        int y = (int) (Math.sin(angleObstacle) * height/3 + circle_center_y);
        angleObstacle += 0.001;


        return new Location(x- width/2,y-height/2);
    }

    public Location moveObstacleDown(Obstacle obstacle){

        double y = obstacle.getLocation().getYCoordinates();


        double downSpeed = Math.abs(obstacle.getSpeed());
        return new Location(obstacle.getLocation().getXCoordinates(), y +  downSpeed);
    }

    /**
     * Drops a spell box downwards, checks if it and the noble phantasm collides
     * and updates player's spell values accordingly.
     *
     * @param spell Spell box to be dropped.
     * @return Spell box location dropped downwards by 1 bit.
     */
    public Location moveSpellDownward(Spell spell) {
        CollisionHandler collisionHandler = new CollisionHandler();
        Double y = spell.getLocation().getYCoordinates();
        int length = spell.getSize().getLength();
        int dy = 1;
        if (collisionHandler.collision(NoblePhantasm.getInstance(), spell)) {
            if(spell.getSpellType().equals(Constants.SpellNameConstants.CHANCE)) {
                SpellAnimator.listOfMovingSpells.remove(spell);
                PlayerLivesHandler.getInstance().increasePlayerLives(NeedforSpearGame.getInstance().getGameInfo().getPlayer());
            }
            else {
                NeedforSpearGame.getInstance().getGameInfo().getPlayer().getListofSpells().add(spell);
                SpellAnimator.listOfMovingSpells.remove(spell);
                NeedforSpearGame.getInstance().getViewData().getGameView().updateSpellNumbers();
                System.out.println(spell);
            }
        }else if(y + dy + length > Constants.UIConstants.INITIAL_SCREEN_HEIGHT){
            SpellAnimator.listOfMovingSpells.remove(spell);
        }
        return new Location(spell.getLocation().getXCoordinates(), y+dy);


    }


    public Location updateBulletMovement(Bullet bullet) {
        Location loc = bullet.getLocation();
        x = loc.getXCoordinates();
        y = loc.getYCoordinates();

        dx = bullet.getSpeed().getSpeedOnXAxis();
        dy = -bullet.getSpeed().getSpeedOnYAxis();

        x += dx;
        y += dy;

        return new Location (x,y);
    }


    /**
     * Updates Sphere's location and speed
     *
     */
    public void updateSphereMovement(CollisionData collisionData) {
        double x = collisionData.getCurrentLocation().getXCoordinates();
        double y = collisionData.getCurrentLocation().getYCoordinates();
        x +=collisionData.getCurrentSpeed().getSpeedOnXAxis();
        y += collisionData.getCurrentSpeed().getSpeedOnYAxis();

        //update location and speed
        NeedforSpearGame.getInstance().getGameInfo().getSphere().setLocation(new Location(x, y));
        NeedforSpearGame.getInstance().getGameInfo().getSphere().setSpeed(new Speed(new Double(collisionData.getCurrentSpeed().getSpeedOnXAxis()).longValue(),  new Double(collisionData.getCurrentSpeed().getSpeedOnYAxis()).longValue()));
    }

    /**
     * Sets global variables to current movement values of sphere
     */
    public void getSphereCurrentPhysics() {
        Location loc = NeedforSpearGame.getInstance().getGameInfo().getSphere().getLocation();
        x = loc.getXCoordinates();
        y = loc.getYCoordinates();

        dx = NeedforSpearGame.getInstance().getGameInfo().getSphere().getSpeed().getSpeedOnXAxis();
        dy = NeedforSpearGame.getInstance().getGameInfo().getSphere().getSpeed().getSpeedOnYAxis();
    }

    /**
     * Checks if obstacle is below the noble phantasm
     */
    public void checkIfObstacleBelowPhantasm() {
        NoblePhantasm noblePhantasm = NoblePhantasm.getInstance();
        if (y > noblePhantasm.getLocation().getYCoordinates()) {
            NeedforSpearGame.getInstance().getGameInfo().getSphere().setMoving(false);
            PlayerLivesHandler.getInstance().notifyPlayerSphereFall(NeedforSpearGame.getInstance().getGameInfo().getSphere());

        }

    }

    /**
     * Checks the collision of bullets and obstacle.
     * @param listOfObstacles
     * @param bullet
     */


    public void checkForCollisions(List<Obstacle> listOfObstacles, Bullet bullet) {
        //collision with Obstacles - Bullet
        for (int i = 0; i < listOfObstacles.size(); i++) {
            Obstacle obs = listOfObstacles.get(i);
            if (collisionHandler.collision(obs, bullet)) {
                System.out.println("bullet hit obstacle");
                obs.damageObstacle();
                BulletAnimator.listOfBullets.remove(bullet);
            }

            //Bullet hits screen frame and is destroyed
            if (bullet.getLocation().getXCoordinates() < 0 || bullet.getLocation().getXCoordinates() > Constants.UIConstants.INITIAL_SCREEN_WIDTH - 2 * radius || bullet.getLocation().getYCoordinates() < 0) {
                BulletAnimator.listOfBullets.remove(bullet);
                System.out.println("bullet exit screen");
            }
            if (collisionHandler.isRemovedObstacle(obs)) {
                if (!obs.getObstacleType().equals(Constants.ObstacleNameConstants.EXP)) {
                    collisionHandler.removeObstacle(obs, listOfObstacles);
                }
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

            Location newLoc = moveObstacleDown(explosiveObstacle);
            explosiveObstacle.setLocation(newLoc);
        }else{
            if (!checkIfOrbitCollides(explosiveObstacle.retrieveOrbitBounds())) {
                Location newLoc = circularMotion(explosiveObstacle);
                explosiveObstacle.setLocation(newLoc);
            }
        }
    }

    public boolean checkIfOrbitCollides(Rectangle orbit) {

        for (int i = 0; i < ObstacleAnimator.listofObstacles.size(); i++) {
            Obstacle obstacle = ObstacleAnimator.listofObstacles.get(i);
            if (collisionHandler.collisionWithExplosiveOrbit(orbit, obstacle)) {
                return true;
            }
        }
        return false;
    }

    public void removingObstacles(Obstacle obstacle){
        double y_bottom = obstacle.getLocation().getYCoordinates() + obstacle.getSize().getLength();
        //TODO carry all removals here EXPLOSIVE REMOVAL
        if (collisionHandler.isRemovedObstacle(obstacle) && obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.EXP) &&
                (collisionHandler.collisionWithExplosive(obstacle,NoblePhantasm.getInstance()) ||
                        y_bottom > Constants.UIConstants.INITIAL_SCREEN_HEIGHT )){

            if (collisionHandler.collisionWithExplosive(obstacle,NoblePhantasm.getInstance())){
                PlayerLivesHandler.getInstance().notifyPlayerExplosiveFall(NoblePhantasm.getInstance());
                System.out.println("player hit by explosive");
            }

            collisionHandler.removeObstacle(obstacle, ObstacleAnimator.listofObstacles);



        }
    }


    /**
     * Animation of sphere movement
     *
     * @param g
     */
    public void sphereMovement(Graphics g) {
        MovementHandler bounceHandler = new MovementHandler();
        bounceHandler.bounceSphereFromFrame();
        checkForCollisions(SphereAnimator.listofObstacles);

    }


    /**
     * To appropriately animate sphere checks for collisions
     * with different game objects
     *
     * @param listofObstacles
     */
    public void checkForCollisions(List<Obstacle> listofObstacles) {
        CollisionHandler collisionHandler = new CollisionHandler();
        MovementHandler bounceHandler = new MovementHandler();

        //collision with Obstacles - Sphere
        for (int i = 0; i < listofObstacles.size(); i++) {
            Obstacle obs = listofObstacles.get(i);

            if (collisionHandler.collision(obs, NeedforSpearGame.getInstance().getGameInfo().getSphere())) {

                //UNSTOPPABLE
                if (NeedforSpearGame.getInstance().getGameInfo().getSphere().isUnstoppable()) {
                    long current_time = System.currentTimeMillis();
                    long start_time = NeedforSpearGame.getInstance().getGameInfo().getSphere().getUnstoppableStartTime();

                    int current_health = obs.getHealth();
                    for (int k = 0; k < current_health; k++) {
                        obs.damageObstacle();
                    }

                    if ((current_time - start_time) / 1000 >= 30) {
                        NeedforSpearGame.getInstance().getGameInfo().getSphere().deactivateUnstoppable();
                    }
                } else {
                    obs.damageObstacle();
                    bounceHandler.bounceSphereFromObstacle(obs);
                }


                //TODO HANDLING OBSTACLE REMOVALS should be removed from here for better cohesion and less coupling
                if (!collisionHandler.isRemovedObstacle(obs)) {
                    bounceHandler.bounceSphereFromObstacle(obs);
                } else {
                    if (!obs.getObstacleType().equals(Constants.ObstacleNameConstants.EXP)) {
                        collisionHandler.removeObstacle(obs, listofObstacles);
                    }

                }
            }

        }


        //collision with Noble Phantasm - Sphere
        if (collisionHandler.collision(NoblePhantasm.getInstance(), NeedforSpearGame.getInstance().getGameInfo().getSphere())) {
            System.out.println("collision with noble phantasm!");
            bounceHandler.bounceSphereFromPhantasm();
        }

    }

    /**
     * @return double x location of NoblePhantasm.
     * This method moves the NoblePhantasm by multiplying time passed with a proper speed, which is L/seconds, according to its direction and the location is updated accordingly.
     * If the NoblePhantasm is speeding, it goes with a 2L/seconds.
     */

    public double movementOfNoblePhantasm(){
        noblePhantasm = NoblePhantasm.getInstance();
        Long currentTime = System.currentTimeMillis();
        Long lastUpdateTime = noblePhantasm.getLastUpdateTime();
        Long moveTimeDiff = currentTime - lastUpdateTime;
        currentX = noblePhantasm.getLocation().getXCoordinates();
        currentY = noblePhantasm.getLocation().getYCoordinates();

        if (noblePhantasm.isMovingLeft()) {
            if(currentX<0){
                currentX = 0;
            }
            if(noblePhantasm.isSpeeding()){
                currentX =  (currentX - (1.0 * Constants.UIConstants.INITIAL_SCREEN_WIDTH / 5.0 / 1000.0) * moveTimeDiff);
            }
            else {
                currentX = (currentX - (1.0 * Constants.UIConstants.INITIAL_SCREEN_WIDTH / 10.0 / 1000.0) * moveTimeDiff);
            }

        } else if (noblePhantasm.isMovingRight()) {
            if(currentX>Constants.UIConstants.INITIAL_SCREEN_WIDTH-noblePhantasm.getSize().getWidth()){
                currentX = Constants.UIConstants.INITIAL_SCREEN_WIDTH-noblePhantasm.getSize().getWidth();
            }
            if (noblePhantasm.isSpeeding()) {
                currentX = (currentX + (1.0 * Constants.UIConstants.INITIAL_SCREEN_WIDTH / 5.0 / 1000.0) * moveTimeDiff);
            }else {
                currentX = (currentX + (1.0 * Constants.UIConstants.INITIAL_SCREEN_WIDTH / 10.0 / 1000.0) * moveTimeDiff);
            }

        }

        return currentX;
    }

    /**
     * NoblePhantasm rotation is handled here.
     */

    public void rotationOfNoblePhantasm(){
        Long currentTime = System.currentTimeMillis();
        Long lastUpdateTime = noblePhantasm.getLastUpdateTime();

        double angle;
        double newAngle = 0;

        if (noblePhantasm.isRightRotate()) {
            angle = noblePhantasm.getRotationDegree();
            if (lastUpdateTime == 0L) {
                newAngle = angle + 0.01;
            } else {
                newAngle = angle + (0.35 / 1000.0) * (currentTime - lastUpdateTime);  //radian equivalence of 20 degrees is 0.35
            }
            if (newAngle > 0.78) //radian equivalence of 45 degrees is 0.78.
                newAngle = 0.78;


        } else if (NoblePhantasm.getInstance().isLeftRotate()) {
            angle = noblePhantasm.getRotationDegree();
            if (lastUpdateTime == 0L) {
                newAngle = angle - 0.01;
            } else {
                newAngle = angle - (0.35 / 1000.0) * (currentTime - lastUpdateTime);
            }
            if (newAngle < -0.78)
                newAngle = -0.78;

        } else {
            angle = noblePhantasm.getRotationDegree();

            if (angle > 0) {

                newAngle = angle - (0.78 / 1000) * (currentTime - lastUpdateTime);
                if (newAngle < 0) {
                    newAngle = 0;
                }
                noblePhantasm.setRotationDegree(newAngle);
                noblePhantasm.setLastUpdateTime(currentTime);
            } else if (angle < 0) {
                newAngle = angle + (0.78 / 1000) * (currentTime - lastUpdateTime);
                if (newAngle > 0) {
                    newAngle = 0;
                }
            }
        }
        noblePhantasm.setRotationDegree(newAngle);
        noblePhantasm.setLastUpdateTime(currentTime);
    }
}