package tr.edu.ku.devnull.needforspear.Viewmodel.Util;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.Speed;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.Model.UIModels.NoblePhantasm;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;

public class PhysicsEngine {

    /**
     * Creates a reflection of the given data about the sphere.
     *
     * @requires incident != null.
     * @effects Creates a new data with the new location and speed to be reflected.
     * @modifies None
     * @param incident Data of the incident vector.
     * @return Returns a collision data of how it is going to be reflected.
     */
    public CollisionData reflect(CollisionData incident) {

        if(incident == null) return null;

        CollisionData result = new CollisionData(incident.getCurrentLocation(), incident.getCurrentSpeed());

        if (incident.getCurrentLocation().getXCoordinates() + incident.getCurrentSpeed().getSpeedOnXAxis() < 0
                || incident.getCurrentLocation().getXCoordinates() + incident.getCurrentSpeed().getSpeedOnXAxis() > Constants.UIConstants.INITIAL_SCREEN_WIDTH - 2 * Constants.ProportionConstants.RADIUS_OF_THE_SPHERE){
            result.getCurrentSpeed().setSpeedOnXAxis(-1 * incident.getCurrentSpeed().getSpeedOnXAxis());
        }
        if (incident.getCurrentLocation().getYCoordinates() + incident.getCurrentSpeed().getSpeedOnYAxis() < 0
                || incident.getCurrentLocation().getYCoordinates() + incident.getCurrentSpeed().getSpeedOnYAxis()> Constants.UIConstants.INITIAL_SCREEN_HEIGHT){
            result.getCurrentSpeed().setSpeedOnYAxis(-1 * incident.getCurrentSpeed().getSpeedOnYAxis());
        }
        return result;
    }

    /**
     * Creates a reflection of the given data about the sphere.
     *
     * @requires incident != null, obstacle's X and Y coordinates should be inside of the gameMap.
     * @effects Creates a new data with the new location and speed to be reflected.
     * @modifies None
     * @param incident Data of the incident vector.
     * @return Returns a collision data of how it is going to be reflected.
     */
    public CollisionData reflect(CollisionData incident, Obstacle obstacle) throws Exception {

        if(incident == null) return null;

        if(obstacle == null) return null;

        if(obstacle.getLocation().getXCoordinates() < 0 || obstacle.getLocation().getXCoordinates() > 1280) throw new Exception("Illegal placement of obstacle.");

        if(obstacle.getLocation().getYCoordinates() < 0 || obstacle.getLocation().getYCoordinates() > 720) throw new Exception("Illegal placement of obstacle.");

        CollisionData result = new CollisionData(incident.getCurrentLocation(), incident.getCurrentSpeed());

        if (obstacle.getSpeed().equals(0.0)) {

            if (incident.getCurrentLocation().getXCoordinates() + 2 * Constants.ProportionConstants.RADIUS_OF_THE_SPHERE + incident.getCurrentSpeed().getSpeedOnXAxis() > obstacle.getLocation().getXCoordinates() &&
                    incident.getCurrentLocation().getXCoordinates() + incident.getCurrentSpeed().getSpeedOnXAxis() < obstacle.getLocation().getXCoordinates() + obstacle.getSize().getWidth() &&
                    incident.getCurrentLocation().getYCoordinates() + 2 * Constants.ProportionConstants.RADIUS_OF_THE_SPHERE > obstacle.getLocation().getYCoordinates() &&
                    incident.getCurrentLocation().getYCoordinates() < obstacle.getLocation().getYCoordinates() + obstacle.getSize().getLength()) {
                result.getCurrentSpeed().setSpeedOnXAxis(-1 * incident.getCurrentSpeed().getSpeedOnXAxis());
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
                result.getCurrentSpeed().setSpeedOnXAxis(-1 * incident.getCurrentSpeed().getSpeedOnXAxis());
                result.getCurrentLocation().setXCoordinates(result.getCurrentLocation().getXCoordinates() + obstacle.getSpeed());
            } else if (incident.getCurrentLocation().getXCoordinates() + 2 * Constants.ProportionConstants.RADIUS_OF_THE_SPHERE > obstacle.getLocation().getXCoordinates() &&
                    incident.getCurrentLocation().getXCoordinates() < obstacle.getLocation().getXCoordinates() + obstacle.getSize().getWidth() &&
                    incident.getCurrentLocation().getYCoordinates() + Constants.ProportionConstants.RADIUS_OF_THE_SPHERE + incident.getCurrentSpeed().getSpeedOnYAxis() > obstacle.getLocation().getYCoordinates() &&
                    incident.getCurrentLocation().getYCoordinates() + incident.getCurrentSpeed().getSpeedOnYAxis() < obstacle.getLocation().getYCoordinates() + obstacle.getSize().getLength()) {
                result.getCurrentSpeed().setSpeedOnYAxis(-1 * incident.getCurrentSpeed().getSpeedOnYAxis());
                result.getCurrentSpeed().setSpeedOnXAxis(-1 * incident.getCurrentSpeed().getSpeedOnXAxis());
                result.getCurrentLocation().setXCoordinates(result.getCurrentLocation().getXCoordinates() + obstacle.getSpeed());
            } else {
                return null;
            }
        }

        return result;
    }

    public CollisionData reflect(CollisionData incident, NoblePhantasm noblePhantasm) {
        CollisionData result = new CollisionData(incident.getCurrentLocation(), incident.getCurrentSpeed());

        if(noblePhantasm.getRotationDegree().equals(0.0)) {
            if(noblePhantasm.isSpeeding()) {

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

                double rotationDegree = -NoblePhantasm.getInstance().getRotationDegree();

                double edgeDX = NoblePhantasm.getInstance().getSize().getWidth() / 2;
                double edgeDY = NoblePhantasm.getInstance().getSize().getLength() / 2;

                double rotatedUpperLeftX = -edgeDX * Math.cos(rotationDegree) - edgeDY * Math.sin(rotationDegree);
                double rotatedUpperLeftY = edgeDY * Math.cos(rotationDegree) - edgeDX * Math.sin(rotationDegree);

                double rotatedUpperRightX = edgeDX * Math.cos(rotationDegree) - edgeDY * Math.sin(rotationDegree);
                double rotatedUpperRightY = edgeDY * Math.cos(rotationDegree) + edgeDX * Math.sin(rotationDegree);

                double rotatedLowerLeftX= -edgeDX * Math.cos(rotationDegree) + edgeDY * Math.sin(rotationDegree);

                double rotatedLowerRightX = edgeDX * Math.cos(rotationDegree) + edgeDY* Math.sin(rotationDegree);

                double ballX = incident.getCurrentLocation().getXCoordinates() + (double) 2* Constants.ProportionConstants.RADIUS_OF_THE_SPHERE / 2 - NoblePhantasm.getInstance().getLocation().getXCoordinates() - edgeDX;
                double ballY = -incident.getCurrentLocation().getYCoordinates() - (double)2* Constants.ProportionConstants.RADIUS_OF_THE_SPHERE / 2 + NoblePhantasm.getInstance().getLocation().getYCoordinates() + edgeDY;

                double middleUpper = (rotatedUpperLeftY - rotatedUpperRightY) / (rotatedUpperLeftX - rotatedUpperRightX);


                if ((ballY - (middleUpper * ballX + edgeDY) <= 2* Constants.ProportionConstants.RADIUS_OF_THE_SPHERE  / 2)
                        && (ballX > rotatedUpperLeftX)
                        && (ballX <= rotatedUpperRightX)
                        && (ballY - (middleUpper * ballX + edgeDY) > 0)) {
               /* System.out.println("HEEY");
                double dx = incident.getCurrentSpeed().getSpeedOnXAxis();
                double dy = -Math.abs(incident.getCurrentSpeed().getSpeedOnYAxis());

                double newdx = dx * Math.cos(-2 * rotationDegree) - dy * Math.sin(-2 * rotationDegree);
                double newdy = dy* Math.cos(-2 * rotationDegree) + dx * Math.sin(-2 * rotationDegree);

                result.getCurrentSpeed().setSpeedOnXAxis(newdx);
                result.getCurrentSpeed().setSpeedOnYAxis(newdy);

                //update new location
                result.getCurrentLocation().setXCoordinates((result.getCurrentLocation().getXCoordinates() + dx));
                result.getCurrentLocation().setYCoordinates((result.getCurrentLocation().getYCoordinates() + dy));
                System.out.println(result.getCurrentSpeed().getSpeedOnXAxis() + " " + result.getCurrentSpeed().getSpeedOnYAxis() );
            }
                /*double dx = incident.getCurrentSpeed().getSpeedOnXAxis();
                double dy = -Math.abs(incident.getCurrentSpeed().getSpeedOnYAxis());
                double newdx = dx * Math.cos(-2 * rotationDegree) - dy * Math.sin(-2 * rotationDegree);
                double newdy = dy * Math.cos(-2 * rotationDegree) + dx * Math.sin(-2 * rotationDegree);

                result.getCurrentSpeed().setSpeedOnXAxis(newdx);
                result.getCurrentSpeed().setSpeedOnYAxis(newdy);

                //update new location
                result.getCurrentLocation().setXCoordinates((result.getCurrentLocation().getXCoordinates() + dx));
                result.getCurrentLocation().setYCoordinates((result.getCurrentLocation().getYCoordinates() + dy));
*/
                    double rotationRadians = Math.toRadians(-NoblePhantasm.getInstance().getRotationDegree());

                    Speed currentSpeed = result.getCurrentSpeed();
                    Speed normal = new Speed(new Double(Math.cos(2*rotationRadians)).longValue(), new Double(Math.sin(2*rotationRadians)).longValue());
                    Speed newSpeed = normal.scale(currentSpeed.dot(normal) * 2).subtract(currentSpeed);

                    result.setCurrentSpeed(newSpeed);
                    System.out.println("he" + newSpeed.getSpeedOnXAxis() + " " +newSpeed.getSpeedOnYAxis());
                    //result.getCurrentSpeed().setSpeedOnXAxis((normal.getSpeedOnXAxis()- (2 * (getResultant(incident.getCurrentSpeed().getSpeedOnXAxis(), incident.getCurrentSpeed().getSpeedOnXAxis())) * Math.pow((getResultant(incident.getCurrentLocation().getXCoordinates(), incident.getCurrentLocation().getXCoordinates())), 2)) / (Math.pow(getResultant(incident.getCurrentLocation().getXCoordinates(), incident.getCurrentLocation().getXCoordinates()), 2))));
                    // result.getCurrentSpeed().setSpeedOnYAxis(((normal.getSpeedOnYAxis()- (2 * (getResultant(incident.getCurrentSpeed().getSpeedOnYAxis(), incident.getCurrentSpeed().getSpeedOnYAxis())) * Math.pow((getResultant(incident.getCurrentLocation().getYCoordinates(), incident.getCurrentLocation().getYCoordinates())), 2)) / (Math.pow(getResultant(incident.getCurrentLocation().getYCoordinates(), incident.getCurrentLocation().getYCoordinates()), 2)))));
                }
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

    private Double getResultant(Double x, Double y) {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }


}

