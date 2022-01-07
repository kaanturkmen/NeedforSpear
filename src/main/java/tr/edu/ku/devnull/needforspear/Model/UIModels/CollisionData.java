package tr.edu.ku.devnull.needforspear.Model.UIModels;

import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Speed;

/**
 * CollisionData is created to keep track of the data easily.
 */
public class CollisionData {
    private Location currentLocation;
    private Speed currentSpeed;

    /**
     * Constructor of collision data.
     *
     * @param currentLocation Current location of incoming collision.
     * @param currentSpeed    Current speed of incoming collision.
     */
    public CollisionData(Location currentLocation, Speed currentSpeed) {
        this.currentLocation = currentLocation;
        this.currentSpeed = currentSpeed;
    }

    /**
     * Empty constructor of the collision data.
     */
    public CollisionData() {

    }

    /**
     * Gets the current location.
     *
     * @return Location of the collision.
     */
    public Location getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Sets the current location.
     *
     * @param currentLocation Location to be set.
     */
    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    /**
     * Gets the speed.
     *
     * @return Speed of the collision.
     */
    public Speed getCurrentSpeed() {
        return currentSpeed;
    }

    /**
     * Sets the speed.
     *
     * @param currentSpeed Speed to be set.
     */
    public void setCurrentSpeed(Speed currentSpeed) {
        this.currentSpeed = currentSpeed;
    }
}
