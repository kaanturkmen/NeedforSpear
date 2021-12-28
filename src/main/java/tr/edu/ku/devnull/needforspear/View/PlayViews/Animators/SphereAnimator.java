package tr.edu.ku.devnull.needforspear.View.PlayViews.Animators;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Speed;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.Model.UIModels.NoblePhantasm;
import tr.edu.ku.devnull.needforspear.Model.UIModels.Sphere;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.PlayViews.AnimatorStrategy;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.MovementHandler;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.CollisionHandler;

import java.awt.*;
import java.util.List;

/**
 * Class for the animation of sphere with java swing graphics
 *
 * @author Melis Oktayoğlu
 */

public class SphereAnimator implements AnimatorStrategy {

    int radius = Constants.ProportionConstants.RADIUS_OF_THE_SPHERE;
    private List<Obstacle> listofObstacles;

    /**
     * Constructor
     *
     * @param listofObstacles
     */
    public SphereAnimator(List<Obstacle> listofObstacles) {
        if(NeedforSpearGame.getInstance().getGameInfo().getNormalDifficulty()){
            Sphere.getInstance().setSpeed(new Speed(2, 2));
        } else Sphere.getInstance().setSpeed(new Speed(4, 4));
        this.listofObstacles = listofObstacles;

    }

    /**
     * Override on AnimatorStrategy to draw sphere graphics
     * reinvoked in GamePanel
     *
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.red);

        if (Sphere.getInstance().isMoving()) {
            sphereMovement(g);
        } else {
            NoblePhantasm noblePhantasm = NoblePhantasm.getInstance();
            Location followNoblePhantasm = new Location((noblePhantasm.getLocation().getXCoordinates() + (noblePhantasm.getSize().getWidth() / 2) - radius), (noblePhantasm.getLocation().getYCoordinates() - 2 * radius));
            Sphere.getInstance().setLocation(followNoblePhantasm);
            int x_coordinates_loc = (int) followNoblePhantasm.getXCoordinates().doubleValue();
            int y_coordinates_loc = (int) followNoblePhantasm.getYCoordinates().doubleValue();
            g.fillOval(x_coordinates_loc, y_coordinates_loc, radius * 2, radius * 2);
        }
    }

    /**
     * Animation of sphere movement
     *
     * @param g
     */
    private void sphereMovement(Graphics g) {
        MovementHandler bounceHandler = new MovementHandler();

        bounceHandler.bounceSphereFromFrame();

        checkForCollisions(listofObstacles);

        //new image on the new location
        int x_coordinates_loc = (int) Sphere.getInstance().getLocation().getXCoordinates().doubleValue();
        int y_coordinates_loc = (int) Sphere.getInstance().getLocation().getYCoordinates().doubleValue();
        g.setColor(Color.red);
        g.fillOval(x_coordinates_loc, y_coordinates_loc, radius * 2, radius * 2);
    }

    /**
     * To appropriately animate sphere checks for collisions
     * with different game objects
     *
     * @param listofObstacles
     */
    private void checkForCollisions(List<Obstacle> listofObstacles) {
        CollisionHandler collisionHandler = new CollisionHandler();
        MovementHandler bounceHandler = new MovementHandler();

        //collision with Obstacles - Sphere
        for (int i = 0; i < listofObstacles.size(); i++) {
            Obstacle obs = listofObstacles.get(i);

            if (collisionHandler.collision(obs, Sphere.getInstance())) {

                //UNSTOPPABLE
                if (Sphere.getInstance().isUnstoppable() ){
                    long current_time = System.currentTimeMillis();
                    long start_time = Sphere.getInstance().getUnstoppableStartTime();

                    int current_health = obs.getHealth();
                    for (int k = 0; k<current_health; k++){
                        obs.damageObstacle();
                    }

                    if ((current_time-start_time)/1000 >=30){ Sphere.getInstance().deactivateUnstoppable();}
                }else{
                    obs.damageObstacle();
                    bounceHandler.bounceSphereFromObstacle(obs);
                }



                //TODO HANDLING OBSTACLE REMOVALS should be removed from here for better cohesion and less coupling
                if (!collisionHandler.isRemovedObstacle(obs)) {
                    bounceHandler.bounceSphereFromObstacle(obs);
                }else{
                    if(!obs.getObstacleType().equals(Constants.ObstacleNameConstants.EXP)){
                        collisionHandler.removeObstacle(obs, listofObstacles);
                    }

                }
            }

        }




        //collision with Noble Phantasm - Sphere
        if (collisionHandler.collision(NoblePhantasm.getInstance(), Sphere.getInstance())) {
            System.out.println("collision with noble phantasm!");
            bounceHandler.bounceSphereFromPhantasm();
        }

    }



    public void unstoppableAnimation(){


    }

}


