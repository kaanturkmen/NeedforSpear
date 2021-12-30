package tr.edu.ku.devnull.needforspear.Model.UIModels;

//import javafx.beans.binding.BooleanExpression;
import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Size;
import tr.edu.ku.devnull.needforspear.Model.GameData.Speed;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Sphere is a class which contains all the
 * size and movement specifications for
 * the sphere to operate within the game, as
 * well as initializing the sphere itself.
 */
public class Sphere {
    private NoblePhantasm noblePhantasm;
    private Size size;
    private Location location;
    private Speed speed;
    private boolean isMoving;
    private boolean isDoubleAccelActivated;
    private boolean isUnstoppable =false;
    private long unstoppableStartTime;

    /**
     * Constructor for Sphere.
     */
    public Sphere() {
        this.noblePhantasm = NoblePhantasm.getInstance();
        this.size = new Size(Constants.ProportionConstants.RADIUS_OF_THE_SPHERE, Constants.ProportionConstants.RADIUS_OF_THE_SPHERE);
        this.location = new Location((noblePhantasm.getLocation().getXCoordinates() + (noblePhantasm.getSize().getWidth() / 2) - (size.getWidth())), (noblePhantasm.getLocation().getYCoordinates() - 2 * size.getWidth()));
        this.speed = new Speed(0,0);
        this.isMoving = false;
    }

    public void resetLocation() {
        this.noblePhantasm = NoblePhantasm.getInstance();
        this.location = new Location((noblePhantasm.getLocation().getXCoordinates() + (noblePhantasm.getSize().getWidth() / 2) - (size.getWidth())), (noblePhantasm.getLocation().getYCoordinates() - 2 * size.getWidth()));
        this.speed.setSpeedOnXAxis(0.);
        this.speed.setSpeedOnXAxis(0.);
        this.isMoving = false;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public void doubleSpeedForAccel(){
        this.setDoubleAccelActivated(false);
    }

    public void activateDoubleAccel(){
        if(!this.isDoubleAccelActivated){
            setDoubleAccelActivated(true);
            int stopDelay = 15 * 1000; //milliseconds
            TimerTask stopTask = new TimerTask() {
                @Override
                public void run() {
                    speed.doubleSpeed();
                    doubleSpeedForAccel();
                }
            };
            Timer stopTimer = new Timer();
            stopTimer.schedule(stopTask, stopDelay);
            speed.halveSpeed();
        }
    }

    public void setDoubleAccelActivated(boolean state) {
        this.isDoubleAccelActivated = state;
    }

    public void setMoving(boolean state) {
        this.isMoving = state;
    }

    public boolean isMoving() {
        return this.isMoving;
    }

    public void activateUnstoppable(){
        this.unstoppableStartTime = System.currentTimeMillis();
        this.isUnstoppable = true;
    }


    public void deactivateUnstoppable(){
        this.isUnstoppable = false;
    }

    public boolean isUnstoppable() {
        return isUnstoppable;
    }

    public long getUnstoppableStartTime(){
        return unstoppableStartTime;
    }
}


