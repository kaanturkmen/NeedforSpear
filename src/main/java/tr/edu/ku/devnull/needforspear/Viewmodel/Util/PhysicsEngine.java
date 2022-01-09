package tr.edu.ku.devnull.needforspear.Viewmodel.Util;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.Difficulty;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.Model.UIModels.CollisionData;
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
            SoundHandler.getInstance().playSound(Constants.SoundConstants.FRAME_HIT_SOUND);
            result.getCurrentSpeed().setSpeedOnXAxis(-1 * incident.getCurrentSpeed().getSpeedOnXAxis());
        }
        if (incident.getCurrentLocation().getYCoordinates() + incident.getCurrentSpeed().getSpeedOnYAxis() < 0
                || incident.getCurrentLocation().getYCoordinates() + incident.getCurrentSpeed().getSpeedOnYAxis() > Constants.UIConstants.INITIAL_SCREEN_HEIGHT) {
            SoundHandler.getInstance().playSound(Constants.SoundConstants.FRAME_HIT_SOUND);
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
                result.getCurrentSpeed().setSpeedOnYAxis(-1 * incident.getCurrentSpeed().getSpeedOnYAxis());
            } else if (incident.getCurrentLocation().getXCoordinates() + 2 * Constants.ProportionConstants.RADIUS_OF_THE_SPHERE > obstacle.getLocation().getXCoordinates() &&
                    incident.getCurrentLocation().getXCoordinates() < obstacle.getLocation().getXCoordinates() + obstacle.getSize().getWidth() &&
                    incident.getCurrentLocation().getYCoordinates() + Constants.ProportionConstants.RADIUS_OF_THE_SPHERE + incident.getCurrentSpeed().getSpeedOnYAxis() > obstacle.getLocation().getYCoordinates() &&
                    incident.getCurrentLocation().getYCoordinates() + incident.getCurrentSpeed().getSpeedOnYAxis() < obstacle.getLocation().getYCoordinates() + obstacle.getSize().getLength()) {
                result.getCurrentSpeed().setSpeedOnYAxis(-1 * incident.getCurrentSpeed().getSpeedOnYAxis());
                result.getCurrentSpeed().setSpeedOnXAxis(-1 * incident.getCurrentSpeed().getSpeedOnXAxis());
            } else {
                return null;
            }
        } else {
            if (incident.getCurrentLocation().getXCoordinates() + 2 * Constants.ProportionConstants.RADIUS_OF_THE_SPHERE + incident.getCurrentSpeed().getSpeedOnXAxis() > obstacle.getLocation().getXCoordinates() &&
                    incident.getCurrentLocation().getXCoordinates() + incident.getCurrentSpeed().getSpeedOnXAxis() < obstacle.getLocation().getXCoordinates() + obstacle.getSize().getWidth() &&
                    incident.getCurrentLocation().getYCoordinates() + 2 * Constants.ProportionConstants.RADIUS_OF_THE_SPHERE > obstacle.getLocation().getYCoordinates() &&
                    incident.getCurrentLocation().getYCoordinates() < obstacle.getLocation().getYCoordinates() + obstacle.getSize().getLength()) {
                result.getCurrentSpeed().setSpeedOnYAxis(-1 * incident.getCurrentSpeed().getSpeedOnYAxis());
                result.getCurrentLocation().setXCoordinates(result.getCurrentLocation().getXCoordinates() + obstacle.getSpeed());
            } else if (incident.getCurrentLocation().getXCoordinates() + 2 * Constants.ProportionConstants.RADIUS_OF_THE_SPHERE > obstacle.getLocation().getXCoordinates() &&
                    incident.getCurrentLocation().getXCoordinates() < obstacle.getLocation().getXCoordinates() + obstacle.getSize().getWidth() &&
                    incident.getCurrentLocation().getYCoordinates() + Constants.ProportionConstants.RADIUS_OF_THE_SPHERE + incident.getCurrentSpeed().getSpeedOnYAxis() > obstacle.getLocation().getYCoordinates() &&
                    incident.getCurrentLocation().getYCoordinates() + incident.getCurrentSpeed().getSpeedOnYAxis() < obstacle.getLocation().getYCoordinates() + obstacle.getSize().getLength()) {
                result.getCurrentSpeed().setSpeedOnYAxis(-1 * incident.getCurrentSpeed().getSpeedOnYAxis());
                result.getCurrentSpeed().setSpeedOnXAxis(-1 * incident.getCurrentSpeed().getSpeedOnXAxis());
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
                result.getCurrentSpeed().setSpeedOnYAxis(-1 * incident.getCurrentSpeed().getSpeedOnYAxis());
                result.getCurrentLocation().setXCoordinates(result.getCurrentLocation().getXCoordinates() + noblePhantasm.getSpeed());
            } else {
                result.getCurrentSpeed().setSpeedOnYAxis(-1 * incident.getCurrentSpeed().getSpeedOnYAxis());
            }
        } else {
            double h = 2 * Constants.ProportionConstants.RADIUS_OF_THE_SPHERE;
            double xDiff = h * Math.sin(NoblePhantasm.getInstance().getRotationDegree());
            double yDiff = h * (1.0 - Math.cos(NoblePhantasm.getInstance().getRotationDegree()));
            int currX = (int) (incident.getCurrentLocation().getXCoordinates() + xDiff);
            double magnitude = Math.pow(Constants.SphereConstantSpeeds.NORMAL_SPEED, 2) - Constants.SphereConstantSpeeds.NORMAL_SPEED / 2.0;
            if (NeedforSpearGame.getInstance().getGameInfo().getDifficultyHandler().getCurrentDifficulty() == Difficulty.HARD) {
                magnitude = Math.pow(Constants.SphereConstantSpeeds.HARD_SPEED, 2) - Constants.SphereConstantSpeeds.HARD_SPEED / 2.0;
            }

            double dx = -magnitude * Math.sin(-NoblePhantasm.getInstance().getRotationDegree());
            double dy = -magnitude * Math.cos(-NoblePhantasm.getInstance().getRotationDegree());
            result.getCurrentSpeed().setSpeedOnXAxis(dx);
            result.getCurrentSpeed().setSpeedOnYAxis(dy);

            //update new location
            result.getCurrentLocation().setXCoordinates(currX);
            result.getCurrentLocation().setYCoordinates((result.getCurrentLocation().getYCoordinates()));
        }


        return result;
    }

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

