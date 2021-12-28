package tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Speed;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.Model.Spell.Spell;
import tr.edu.ku.devnull.needforspear.Model.UIModels.Bullet;
import tr.edu.ku.devnull.needforspear.Model.UIModels.NoblePhantasm;
import tr.edu.ku.devnull.needforspear.Model.UIModels.Sphere;
import tr.edu.ku.devnull.needforspear.Model.Util.CollisionData;
import tr.edu.ku.devnull.needforspear.Model.Util.PhysicsEngine;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.PlayViews.Animators.SpellAnimator;
import tr.edu.ku.devnull.needforspear.View.PlayViews.GameView;

import java.awt.*;
import java.awt.geom.AffineTransform;
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

        if (collisionData!= null)  updateSphereMovement(collisionData);

    }

    /**
     * Sphere's bounce from phantasm
     */
    public void bounceSphereFromPhantasm() {
        getSphereCurrentPhysics();
        dy *= -1;
        y = y - 2 * radius;


        // CollisionData collisionData = physicsEngine.reflectFromPhantasm(new CollisionData(new Location(x,y), new Speed(dx,dy)), NoblePhantasm.getInstance());
        //updateSphereMovement(collisionData);
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
                PlayerLivesHandler.getInstance().increasePlayerLives(NeedforSpearGame.getInstance().getPlayer());
            }
            else {
                NeedforSpearGame.getInstance().getPlayer().getListofSpells().add(spell);
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

        dx = bullet.getSpeed()[0];
        dy = -bullet.getSpeed()[1];

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
        x +=collisionData.getCurrentSpeed().getSpeedOnXAxis() ;
        y += collisionData.getCurrentSpeed().getSpeedOnYAxis() ;

        //update location and speed
        Sphere.getInstance().setLocation(new Location(x, y));
        Sphere.getInstance().setSpeed(new Speed(new Double(collisionData.getCurrentSpeed().getSpeedOnXAxis()).longValue(),  new Double(collisionData.getCurrentSpeed().getSpeedOnYAxis()).longValue()));
    }

    /**
     * Sets global variables to current movement values of sphere
     */
    public void getSphereCurrentPhysics() {
        Location loc = Sphere.getInstance().getLocation();
        x = loc.getXCoordinates();
        y = loc.getYCoordinates();

        dx = Sphere.getInstance().getSpeed().getSpeedOnXAxis();
        dy = Sphere.getInstance().getSpeed().getSpeedOnYAxis();
    }

    /**
     * Checks if obstacle is below the noble phantasm
     */
    public void checkIfObstacleBelowPhantasm() {
        NoblePhantasm noblePhantasm = NoblePhantasm.getInstance();
        if (y > noblePhantasm.getLocation().getYCoordinates()) {
            Sphere.getInstance().setMoving(false);
            PlayerLivesHandler.getInstance().notifyPlayerSphereFall(Sphere.getInstance());

        }

    }

    /**
     * @return new Location of NoblePhantasm.
     * This method moves the NoblePhantasm by multiplying time passed with a proper speed, which is L/seconds, according to its direction and the location is updated accordingly.
     * If the NoblePhantasm is speeding, it goes with a 2L/seconds.
     */

    public Location movementOfNoblePhantasm(){
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

        return new Location(currentX, noblePhantasm.getLocation().getYCoordinates());
    }


    /**
     * @return Shape after transformation.
     * This method rotates the NoblePhantasm by creating a newAngle, which is calculated by converting radians to milliseconds,and multiplying it with the time passed.
     * After finding the newAngle, AffineTransform is used to rotate based on direction.
     */

    public Shape rotateOfNoblePhantasm() {

        noblePhantasm = NoblePhantasm.getInstance();
        Long currentTime = System.currentTimeMillis();
        Long lastUpdateTime = noblePhantasm.getLastUpdateTime();
        Long moveTimeDiff = currentTime - lastUpdateTime;
        currentX = noblePhantasm.getLocation().getXCoordinates();
        currentY = noblePhantasm.getLocation().getYCoordinates();
        AffineTransform tx = new AffineTransform();

        lastUpdateTime = noblePhantasm.getLastUpdateTime();
        currentTime = System.currentTimeMillis();

        double angle;
        double newAngle = 0;
        Shape newShape;
        if (noblePhantasm.isRightRotate()) {
            angle = noblePhantasm.getRotationDegree();
            if (lastUpdateTime == 0L) {
                newAngle = angle + 0.01;
            } else {
                newAngle = angle + (0.35 / 1000.0) * (currentTime - lastUpdateTime);  //radian equivalence of 20 degrees is 0.35
            }
            if (newAngle > 0.78) //radian equivalence of 45 degrees is 0.78.
                newAngle = 0.78;


            tx.rotate(newAngle, currentX + noblePhantasm.getSize().getWidth() / 2, currentY + Constants.ProportionConstants.Y_CENTER_OF_NOBLE_PHANTASM);
            Rectangle shape = new Rectangle((int) currentX, (int) currentY, noblePhantasm.getSize().getWidth(), Constants.ProportionConstants.HEIGHT_OF_NOBLE_PHANTASM);
            newShape = tx.createTransformedShape(shape);


        } else if (NoblePhantasm.getInstance().isLeftRotate()) {
            angle = noblePhantasm.getRotationDegree();
            if (lastUpdateTime == 0L) {
                newAngle = angle - 0.01;
            } else {
                newAngle = angle - (0.35 / 1000.0) * (currentTime - lastUpdateTime);
            }
            if (newAngle < -0.78)
                newAngle = -0.78;

            tx.rotate(newAngle, currentX + noblePhantasm.getSize().getWidth() / 2, currentY + Constants.ProportionConstants.Y_CENTER_OF_NOBLE_PHANTASM);
            Rectangle shape = new Rectangle((int) currentX, (int) currentY, noblePhantasm.getSize().getWidth(), Constants.ProportionConstants.HEIGHT_OF_NOBLE_PHANTASM);
            newShape = tx.createTransformedShape(shape);

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

            tx.rotate(noblePhantasm.getRotationDegree(), currentX + noblePhantasm.getSize().getWidth() / 2, currentY + Constants.ProportionConstants.Y_CENTER_OF_NOBLE_PHANTASM);
            Rectangle shape = new Rectangle((int) currentX, (int) currentY, noblePhantasm.getSize().getWidth(), Constants.ProportionConstants.HEIGHT_OF_NOBLE_PHANTASM);
            newShape = tx.createTransformedShape(shape);
        }

        noblePhantasm.setRotationDegree(newAngle);
        noblePhantasm.setLastUpdateTime(currentTime);
        noblePhantasm.setLastUpdateTime(currentTime);

        return newShape;
    }
}



