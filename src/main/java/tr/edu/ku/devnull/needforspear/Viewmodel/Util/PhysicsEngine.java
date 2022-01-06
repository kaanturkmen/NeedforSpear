package tr.edu.ku.devnull.needforspear.Viewmodel.Util;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.Difficulty;
import tr.edu.ku.devnull.needforspear.Model.GameData.Speed;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.Model.UIModels.NoblePhantasm;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameHandlers.SoundHandler;

/**
 * PhysicsEngine is created to deal with the collisions and hits.
 */
public class PhysicsEngine {

    /**
     * Creates a reflection of the given data about the sphere.
     * <p>
     * REQUIRES: incident != null.
     * EFFECTS: Creates a new data with the new location and speed to be reflected.
     * MODIFIES: None
     *
     * @param incident Data of the incident vector.
     * @return Returns a collision data of how it is going to be reflected.
     */
    public CollisionData reflect(CollisionData incident) {

        if (incident == null) return null;

        CollisionData result = new CollisionData(incident.getCurrentLocation(), incident.getCurrentSpeed());

        if (incident.getCurrentLocation().getXCoordinates() + incident.getCurrentSpeed().getSpeedOnXAxis() < 0
                || incident.getCurrentLocation().getXCoordinates() + incident.getCurrentSpeed().getSpeedOnXAxis() > Constants.UIConstants.INITIAL_SCREEN_WIDTH - 2 * Constants.ProportionConstants.RADIUS_OF_THE_SPHERE) {
            SoundHandler.getInstance().playSound("frameHitEffect.wav");
            result.getCurrentSpeed().setSpeedOnXAxis(-1 * incident.getCurrentSpeed().getSpeedOnXAxis());
        }
        if (incident.getCurrentLocation().getYCoordinates() + incident.getCurrentSpeed().getSpeedOnYAxis() < 0
                || incident.getCurrentLocation().getYCoordinates() + incident.getCurrentSpeed().getSpeedOnYAxis() > Constants.UIConstants.INITIAL_SCREEN_HEIGHT) {
            SoundHandler.getInstance().playSound("frameHitEffect.wav");
            result.getCurrentSpeed().setSpeedOnYAxis(-1 * incident.getCurrentSpeed().getSpeedOnYAxis());
        }
        return result;
    }

    /**
     * Creates a reflection of the given data about the sphere.
     * <p>
     * REQUIRES: incident != null, obstacle's X and Y coordinates should be inside of the gameMap.
     * EFFECTS: Creates a new data with the new location and speed to be reflected.
     * MODIFIES: None
     *
     * @param incident Data of the incident vector.
     * @return Returns a collision data of how it is going to be reflected.
     */
    public CollisionData reflect(CollisionData incident, Obstacle obstacle) throws Exception {

        if (incident == null) return null;

        if (obstacle == null) return null;

        if (obstacle.getLocation().getXCoordinates() < 0 || obstacle.getLocation().getXCoordinates() > 1280)
            throw new Exception("Illegal placement of obstacle.");

        if (obstacle.getLocation().getYCoordinates() < 0 || obstacle.getLocation().getYCoordinates() > 720)
            throw new Exception("Illegal placement of obstacle.");

        CollisionData result = new CollisionData(incident.getCurrentLocation(), incident.getCurrentSpeed());

        if (obstacle.getSpeed() == .0) {

            if (incident.getCurrentLocation().getXCoordinates() + 2 * Constants.ProportionConstants.RADIUS_OF_THE_SPHERE + incident.getCurrentSpeed().getSpeedOnXAxis() > obstacle.getLocation().getXCoordinates() &&
                    incident.getCurrentLocation().getXCoordinates() + incident.getCurrentSpeed().getSpeedOnXAxis() < obstacle.getLocation().getXCoordinates() + obstacle.getSize().getWidth() &&
                    incident.getCurrentLocation().getYCoordinates() + 2 * Constants.ProportionConstants.RADIUS_OF_THE_SPHERE > obstacle.getLocation().getYCoordinates() &&
                    incident.getCurrentLocation().getYCoordinates() < obstacle.getLocation().getYCoordinates() + obstacle.getSize().getLength()) {
                SoundHandler.getInstance().playSound("obstacleHitEffect.wav");
                result.getCurrentSpeed().setSpeedOnXAxis(-1 * incident.getCurrentSpeed().getSpeedOnXAxis());

                System.out.println("1st if");
            }

            if (incident.getCurrentLocation().getXCoordinates() + 2 * Constants.ProportionConstants.RADIUS_OF_THE_SPHERE > obstacle.getLocation().getXCoordinates() &&
                    incident.getCurrentLocation().getXCoordinates() < obstacle.getLocation().getXCoordinates() + obstacle.getSize().getWidth() &&
                    incident.getCurrentLocation().getYCoordinates() + Constants.ProportionConstants.RADIUS_OF_THE_SPHERE + incident.getCurrentSpeed().getSpeedOnYAxis() > obstacle.getLocation().getYCoordinates() &&
                    incident.getCurrentLocation().getYCoordinates() + incident.getCurrentSpeed().getSpeedOnYAxis() < obstacle.getLocation().getYCoordinates() + obstacle.getSize().getLength()) {
                SoundHandler.getInstance().playSound("obstacleHitEffect.wav");
                result.getCurrentSpeed().setSpeedOnYAxis(-1 * incident.getCurrentSpeed().getSpeedOnYAxis());
                result.getCurrentSpeed().setSpeedOnXAxis(-1 * incident.getCurrentSpeed().getSpeedOnXAxis());
                System.out.println("2nd if");
            } else {
                return null;
            }
        } else {
            if (incident.getCurrentLocation().getXCoordinates() + 2 * Constants.ProportionConstants.RADIUS_OF_THE_SPHERE + incident.getCurrentSpeed().getSpeedOnXAxis() > obstacle.getLocation().getXCoordinates() &&
                    incident.getCurrentLocation().getXCoordinates() + incident.getCurrentSpeed().getSpeedOnXAxis() < obstacle.getLocation().getXCoordinates() + obstacle.getSize().getWidth() &&
                    incident.getCurrentLocation().getYCoordinates() + 2 * Constants.ProportionConstants.RADIUS_OF_THE_SPHERE > obstacle.getLocation().getYCoordinates() &&
                    incident.getCurrentLocation().getYCoordinates() < obstacle.getLocation().getYCoordinates() + obstacle.getSize().getLength()) {
                SoundHandler.getInstance().playSound("obstacleHitEffect.wav");
                result.getCurrentSpeed().setSpeedOnXAxis(-1 * incident.getCurrentSpeed().getSpeedOnXAxis());
                result.getCurrentLocation().setXCoordinates(result.getCurrentLocation().getXCoordinates() + obstacle.getSpeed());
            } else if (incident.getCurrentLocation().getXCoordinates() + 2 * Constants.ProportionConstants.RADIUS_OF_THE_SPHERE > obstacle.getLocation().getXCoordinates() &&
                    incident.getCurrentLocation().getXCoordinates() < obstacle.getLocation().getXCoordinates() + obstacle.getSize().getWidth() &&
                    incident.getCurrentLocation().getYCoordinates() + Constants.ProportionConstants.RADIUS_OF_THE_SPHERE + incident.getCurrentSpeed().getSpeedOnYAxis() > obstacle.getLocation().getYCoordinates() &&
                    incident.getCurrentLocation().getYCoordinates() + incident.getCurrentSpeed().getSpeedOnYAxis() < obstacle.getLocation().getYCoordinates() + obstacle.getSize().getLength()) {
                SoundHandler.getInstance().playSound("obstacleHitEffect.wav");
                result.getCurrentSpeed().setSpeedOnYAxis(-1 * incident.getCurrentSpeed().getSpeedOnYAxis());
                result.getCurrentSpeed().setSpeedOnXAxis(-1 * incident.getCurrentSpeed().getSpeedOnXAxis());
                result.getCurrentLocation().setXCoordinates(result.getCurrentLocation().getXCoordinates() + obstacle.getSpeed());
            } else {
                return null;
            }
        }

        return result;
    }

    /**
     * Performs collision event between sphere and the noble phantasm.
     *
     * @param incident      Incident data.
     * @param noblePhantasm Noble phantasm of the map.
     * @return CollisionData of the resultant vector.
     */
    public CollisionData reflect(CollisionData incident, NoblePhantasm noblePhantasm) {
        CollisionData result = new CollisionData(incident.getCurrentLocation(), incident.getCurrentSpeed());

        if (noblePhantasm.getRotationDegree().equals(0.0)) {
            if (noblePhantasm.isSpeeding()) {

                // If noble phantasm not rotating but speeding in either direction, opposing the speed in Y-axis and changing location speed * 1 second.
                result.getCurrentSpeed().setSpeedOnYAxis(-1 * incident.getCurrentSpeed().getSpeedOnYAxis());
                result.getCurrentLocation().setXCoordinates(result.getCurrentLocation().getXCoordinates() + noblePhantasm.getSpeed());
            } else {

                // If noble phantasm not rotating or speeding in any direction, opposing the speed in Y-axis.
                result.getCurrentSpeed().setSpeedOnYAxis(-1 * incident.getCurrentSpeed().getSpeedOnYAxis());
            }
        } else {
            if (noblePhantasm.isSpeeding()) {
                /*double rotationDegree = Math.toRadians(-NoblePhantasm.getInstance().getRotationDegree());
                double dx = result.getCurrentSpeed().getSpeedOnXAxis() * Math.cos(-2 * rotationDegree) - result.getCurrentSpeed().getSpeedOnYAxis() * Math.sin(-2 * rotationDegree);
                double dy = result.getCurrentSpeed().getSpeedOnXAxis() * Math.cos(-2 * rotationDegree) + result.getCurrentSpeed().getSpeedOnYAxis() * Math.sin(-2 * rotationDegree);
                result.getCurrentSpeed().setSpeedOnXAxis(dx);
                result.getCurrentSpeed().setSpeedOnYAxis(dy);*/
                // result.getCurrentSpeed().setSpeedOnXAxis((incident.getCurrentSpeed().getSpeedOnXAxis() - (2 * (getResultant(incident.getCurrentSpeed().getSpeedOnXAxis(), incident.getCurrentSpeed().getSpeedOnXAxis())) * Math.pow((getResultant(incident.getCurrentLocation().getXCoordinates(), incident.getCurrentLocation().getXCoordinates())), 2)) / (Math.pow(getResultant(incident.getCurrentLocation().getXCoordinates(), incident.getCurrentLocation().getXCoordinates()), 2))));
                //result.getCurrentSpeed().setSpeedOnYAxis((incident.getCurrentSpeed().getSpeedOnYAxis() - (2 * (getResultant(incident.getCurrentSpeed().getSpeedOnYAxis(), incident.getCurrentSpeed().getSpeedOnYAxis())) * Math.pow((getResultant(incident.getCurrentLocation().getYCoordinates(), incident.getCurrentLocation().getYCoordinates())), 2)) / (Math.pow(getResultant(incident.getCurrentLocation().getYCoordinates(), incident.getCurrentLocation().getYCoordinates()), 2))));
                //result.getCurrentLocation().setXCoordinates((incident.getCurrentLocation().getXCoordinates() + noblePhantasm.getSpeed()));
            } else {
/*
                    double rotationDegree  = - NoblePhantasm.getInstance().getRotationDegree();
                    System.out.println("REFLECTION");
                    double dx = incident.getCurrentSpeed().getSpeedOnXAxis();
                    double dy = -Math.abs(incident.getCurrentSpeed().getSpeedOnYAxis());

                    double newdx = dx * Math.cos(-2 * rotationDegree) - dy * Math.sin(-2 * rotationDegree);
                    double newdy = dy * Math.cos(-2 * rotationDegree) + dx * Math.sin(-2 * rotationDegree);

                    result.getCurrentSpeed().setSpeedOnXAxis(newdx);
                    result.getCurrentSpeed().setSpeedOnYAxis(newdy);

                    //update new location
                    result.getCurrentLocation().setXCoordinates((result.getCurrentLocation().getXCoordinates() + newdx));
                    result.getCurrentLocation().setYCoordinates((result.getCurrentLocation().getYCoordinates() + newdy));
                    System.out.println(result.getCurrentSpeed().getSpeedOnXAxis() + " " + result.getCurrentSpeed().getSpeedOnYAxis());
*/
/*                   double rotationRadians = Math.toRadians(-NoblePhantasm.getInstance().getRotationDegree());

                    Speed currentSpeed = result.getCurrentSpeed();
                    Speed normal = new Speed(new Double(Math.cos(2 * rotationRadians)).longValue(), new Double(Math.sin(2 * rotationRadians)).longValue());
                    Speed newSpeed = normal.scale(currentSpeed.dot(normal) * 2).subtract(currentSpeed);

                    result.setCurrentSpeed(newSpeed);
                    System.out.println("he" + newSpeed.getSpeedOnXAxis() + " " + newSpeed.getSpeedOnYAxis());
                    //result.getCurrentSpeed().setSpeedOnXAxis((normal.getSpeedOnXAxis()- (2 * (getResultant(incident.getCurrentSpeed().getSpeedOnXAxis(), incident.getCurrentSpeed().getSpeedOnXAxis())) * Math.pow((getResultant(incident.getCurrentLocation().getXCoordinates(), incident.getCurrentLocation().getXCoordinates())), 2)) / (Math.pow(getResultant(incident.getCurrentLocation().getXCoordinates(), incident.getCurrentLocation().getXCoordinates()), 2))));
                    // result.getCurrentSpeed().setSpeedOnYAxis(((normal.getSpeedOnYAxis()- (2 * (getResultant(incident.getCurrentSpeed().getSpeedOnYAxis(), incident.getCurrentSpeed().getSpeedOnYAxis())) * Math.pow((getResultant(incident.getCurrentLocation().getYCoordinates(), incident.getCurrentLocation().getYCoordinates())), 2)) / (Math.pow(getResultant(incident.getCurrentLocation().getYCoordinates(), incident.getCurrentLocation().getYCoordinates()), 2)))));
 */
                //}
                double h = 2*Constants.ProportionConstants.RADIUS_OF_THE_SPHERE;
                double xDiff = h*Math.sin(NoblePhantasm.getInstance().getRotationDegree());
                double yDiff = h*(1.0-Math.cos(NoblePhantasm.getInstance().getRotationDegree()));
                System.out.println("Location Check"+ NoblePhantasm.getInstance().getLocation().getXCoordinates() + " "+incident.getCurrentLocation().getXCoordinates());

                System.out.println(xDiff + "   "+ yDiff+" hhh");
                int currX = (int) (incident.getCurrentLocation().getXCoordinates() + xDiff);
                int currY = (int) (incident.getCurrentLocation().getYCoordinates());
                //this.getLocation().setXCoordinates(currX);
                //this.getLocation().setYCoordinates(currY);
                System.out.println(NoblePhantasm.getInstance().getRotationDegree());
                System.out.println("setMoving: speed before change "+incident.getCurrentSpeed().getSpeedOnXAxis() + " " + incident.getCurrentSpeed().getSpeedOnXAxis());
                //double magnitude=Math.sqrt(this.getSpeed().getSpeedOnXAxis()*this.getSpeed().getSpeedOnXAxis()+this.getSpeed().getSpeedOnYAxis()*this.getSpeed().getSpeedOnYAxis());
                double magnitude =Math.pow(Constants.SphereConstantSpeeds.NORMAL_SPEED,2);
                if (NeedforSpearGame.getInstance().getGameInfo().getDifficultyHandler().getCurrentDifficulty() == Difficulty.HARD){
                    magnitude =  Math.pow(Constants.SphereConstantSpeeds.HARD_SPEED,2);
                }

                //this.setSpeed(new Speed(-magnitude*Math.sin(-NoblePhantasm.getInstance().getRotationDegree()), -magnitude*Math.cos(NoblePhantasm.getInstance().getRotationDegree())));
                double dx = -magnitude*Math.sin(-NoblePhantasm.getInstance().getRotationDegree());
                double dy = -magnitude*Math.cos(-NoblePhantasm.getInstance().getRotationDegree());
                result.getCurrentSpeed().setSpeedOnXAxis(dx);
                result.getCurrentSpeed().setSpeedOnYAxis(dy);

                //update new location
                result.getCurrentLocation().setXCoordinates(currX);
                result.getCurrentLocation().setYCoordinates((result.getCurrentLocation().getYCoordinates())); //TODO might add dx dy
                System.out.println(result.getCurrentSpeed().getSpeedOnXAxis() + " " + result.getCurrentSpeed().getSpeedOnYAxis());
            }
        }


        return result;
    }
    /*public static Speed Reflect(Speed vector, Speed normal)
    {
        return vector - 2 * Sped.dot(normal) * normal;
    }*/
    /*private CollisionData updateRotationResult(CollisionData result, double dx, double dy, double rotationDegree){
        //Update speed


        return result;
    }*/

    /**
     * Gets the resultant vector.
     *
     * @param x X value of the vector.
     * @param y Y value of the vector.
     * @return Result of the operation.
     */
    private Double getResultant(Double x, Double y) {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
}

