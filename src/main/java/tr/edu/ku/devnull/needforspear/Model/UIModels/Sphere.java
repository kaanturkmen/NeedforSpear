package tr.edu.ku.devnull.needforspear.Model.UIModels;

import tr.edu.ku.devnull.needforspear.Model.GameData.*;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.MovementHandler;

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
    private boolean isMoving, isDoubleAccelActivated, isUnstoppable = false;
    private long unstoppableStartTime;

    /**
     * Constructor for Sphere.
     */
    public Sphere() {
        this.noblePhantasm = NoblePhantasm.getInstance();
        this.size = new Size(Constants.ProportionConstants.RADIUS_OF_THE_SPHERE, Constants.ProportionConstants.RADIUS_OF_THE_SPHERE);
        this.location = new Location((noblePhantasm.getLocation().getXCoordinates() + (noblePhantasm.getSize().getWidth() / Constants.UIConstants.DIVISION_CONSTANT_OF_SPHERE) - (size.getWidth())), (noblePhantasm.getLocation().getYCoordinates() - Constants.UIConstants.MULTIPLIER_CONSTANT_OF_SPHERE * size.getWidth()));
        this.speed = new Speed(0, 0);
        this.isMoving = false;
    }

    /**
     * Resets the location of the sphere.
     */
    public void resetLocation() {
        this.noblePhantasm = NoblePhantasm.getInstance();
        this.location = new Location((noblePhantasm.getLocation().getXCoordinates() + (noblePhantasm.getSize().getWidth() / Constants.UIConstants.DIVISION_CONSTANT_OF_SPHERE) - (size.getWidth())), (noblePhantasm.getLocation().getYCoordinates() - Constants.UIConstants.MULTIPLIER_CONSTANT_OF_SPHERE * size.getWidth()));
        this.speed.setSpeedOnXAxis(0.);
        this.speed.setSpeedOnXAxis(0.);
        this.isMoving = false;
    }

    public Size getSize() {
        return size;
    }

    /**
     * Sets the size of the sphere.
     *
     * @param size Size to be set.
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Gets the location of the sphere.
     *
     * @return Location of the sphere.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the location of the sphere.
     *
     * @param location Location to be set.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Gets the speed of the sphere.
     *
     * @return Speed to be sphere.
     */
    public Speed getSpeed() {
        return speed;
    }

    /**
     * Sets the speed of the sphere.
     *
     * @param speed Speed to be set.
     */
    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    /**
     * Doubles up to speed for double accel spell.
     */
    public void doubleSpeedForAccel() {

        speed.doubleSpeed();
        this.setDoubleAccelActivated(false);
    }

    /**
     * Activates double accel spell.
     */
    public void activateDoubleAccel() {
        if (!this.isDoubleAccelActivated) {
            setDoubleAccelActivated(true);
            int stopDelay = Constants.UIConstants.SPELL_DURATION_SECONDS * Constants.UIConstants.MILLISECONDS_TO_SECONDS; //milliseconds
            TimerTask stopTask = new TimerTask() {
                @Override
                public void run() {
                    doubleSpeedForAccel();
                }
            };
            Timer stopTimer = new Timer();
            stopTimer.schedule(stopTask, stopDelay);
            speed.halveSpeed();
        }
    }

    /**
     * Sets activation of the double accel spell.
     *
     * @param state State to be set.
     */
    public void setDoubleAccelActivated(boolean state) {
        this.isDoubleAccelActivated = state;
    }

    /**
     * Gets if sphere is moving.
     *
     * @return Boolean value indicating if sphere is moving.
     */
    public boolean isMoving() {
        return this.isMoving;
    }

    /**
     * Sets the moving state.
     *
     * @param state Boolean value to be set.
     */
    public void setMoving(boolean state) {
        this.isMoving = state;

        if (state) {
            MovementHandler movementHandler = new MovementHandler();
            double h = 2 * Constants.ProportionConstants.RADIUS_OF_THE_SPHERE;
            double xDiff = h * Math.sin(NoblePhantasm.getInstance().getRotationDegree());
            int currX = (int) (this.getLocation().getXCoordinates() + xDiff);
            int currY = (int) (this.getLocation().getYCoordinates());
            double magnitude = Math.pow(Constants.SphereConstantSpeeds.NORMAL_SPEED, 2) - Constants.SphereConstantSpeeds.NORMAL_SPEED / 2.0;
            if (NeedforSpearGame.getInstance().getGameInfo().getDifficultyHandler().getCurrentDifficulty() == Difficulty.HARD) {
                magnitude = Math.pow(Constants.SphereConstantSpeeds.HARD_SPEED, 2) - Constants.SphereConstantSpeeds.HARD_SPEED / 2.0;
            }
            if (NeedforSpearGame.getInstance().getGameInfo().getSphere().isDoubleAccelActivated()) {
                magnitude = magnitude / 2.0;
            }
            double dx = -magnitude * Math.sin(-NoblePhantasm.getInstance().getRotationDegree());
            double dy = -magnitude * Math.cos(-NoblePhantasm.getInstance().getRotationDegree());
            movementHandler.updateSphereMovement(new CollisionData(new Location(currX + this.getSpeed().getSpeedOnXAxis(), currY + this.getSpeed().getSpeedOnYAxis()),
                    new Speed(Double.valueOf(dx).longValue(), Double.valueOf(dy).longValue())));
        }
    }

    /**
     * Activates unstoppable spell.
     */
    public void activateUnstoppable() {
        this.unstoppableStartTime = System.currentTimeMillis();
        this.isUnstoppable = true;
    }

    public boolean isDoubleAccelActivated() {
        return isDoubleAccelActivated;
    }

    /**
     * Deactivates unstoppable spell.
     */
    public void deactivateUnstoppable() {
        this.isUnstoppable = false;
    }

    /**
     * Gets if Unstoppable is active.
     *
     * @return Boolean value indacating if the unstoppable is active.
     */
    public boolean isUnstoppable() {
        return isUnstoppable;
    }

    /**
     * Gets unstoppable star time.
     *
     * @return Long milliseconds value of start time.
     */
    public long getUnstoppableStartTime() {
        return unstoppableStartTime;
    }
}


