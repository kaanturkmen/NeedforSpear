package tr.edu.ku.devnull.needforspear.Viewmodel.Util;

import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Speed;

public class CollisionData {
    private Location currentLocation;
    private Speed currentSpeed;

    public CollisionData(Location currentLocation, Speed currentSpeed) {
        this.currentLocation = currentLocation;
        this.currentSpeed = currentSpeed;
    }

    public CollisionData() {
        
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Speed getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(Speed currentSpeed) {
        this.currentSpeed = currentSpeed;
    }
}
