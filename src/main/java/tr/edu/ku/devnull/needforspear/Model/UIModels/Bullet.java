package tr.edu.ku.devnull.needforspear.Model.UIModels;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Size;
import tr.edu.ku.devnull.needforspear.Model.GameData.Speed;

/**
 * Bullet object is created for the MagicalHexSpell.
 */
public class Bullet {
    private NoblePhantasm noblePhantasm;
    private Size size;
    private Location location;
    private double angle;
    private Speed speed;


    /**
     * Bullet's constructor.
     */
    public Bullet() {
        this.noblePhantasm = NoblePhantasm.getInstance();
        this.size = new Size(Constants.ProportionConstants.RADIUS_OF_THE_BULLET, Constants.ProportionConstants.RADIUS_OF_THE_BULLET);
        this.angle = noblePhantasm.getRotationDegree() * Constants.UIConstants.BULLET_MULTIPLIER_CONSTANT;
    }

    /**
     * Creates the left bullet.
     *
     * @return Created bullet object.
     */
    public static Bullet createLeftBullet() {
        System.out.println("left bullet created");
        Bullet bulletLeft = new Bullet();
        bulletLeft.setLocation(new Location((NoblePhantasm.getInstance().getLocation().getXCoordinates() + (NoblePhantasm.getInstance().getSize().getWidth() / Constants.UIConstants.BULLET_DIVISION_CONSTANT) - (bulletLeft.getSize().getWidth())), (NoblePhantasm.getInstance().getLocation().getYCoordinates() - Constants.UIConstants.BULLET_MULTIPLIER_CONSTANT_OF_LOCATION * bulletLeft.getSize().getWidth())));
        bulletLeft.setSpeed(new Speed(0, Constants.ProportionConstants.SPEED_OF_THE_BULLET));
        return bulletLeft;
    }

    /**
     * Creates the right bullet.
     *
     * @return Created bullet object.
     */
    public static Bullet createRightBullet() {
        System.out.println("right bullet created");
        Bullet bulletRight = new Bullet();
        bulletRight.setLocation(new Location((NoblePhantasm.getInstance().getLocation().getXCoordinates() + ((NoblePhantasm.getInstance().getSize().getWidth() * Constants.UIConstants.BULLET_MULTIPLIER_CONSTANT_OF_LOCATION_SECOND) / Constants.UIConstants.BULLET_DIVISION_CONSTANT) - (bulletRight.getSize().getWidth())), (NoblePhantasm.getInstance().getLocation().getYCoordinates() - Constants.UIConstants.BULLET_MULTIPLIER_CONSTANT_OF_LOCATION * bulletRight.getSize().getWidth())));
        bulletRight.setSpeed(new Speed(0, Constants.ProportionConstants.SPEED_OF_THE_BULLET));
        return bulletRight;
    }

    /**
     * Gets the location of the bullet.
     *
     * @return Location of the bullet.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets location of the bullet.
     *
     * @param location Location of the bullet.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Gets the speed of the bullet.
     *
     * @return Speed of the bullet.
     */
    public Speed getSpeed() {
        return speed;
    }

    /**
     * Sets the speed of the bullets.
     *
     * @param speed Speed to be set.
     */
    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    /**
     * Gets the noble phantasm.
     *
     * @return NoblePhantasm of the current map.
     */
    public NoblePhantasm getNoblePhantasm() {
        return noblePhantasm;
    }

    /**
     * Gets the size of the bullet.
     *
     * @return Size of the bullet.
     */
    public Size getSize() {
        return size;
    }

    /**
     * Sets the size of the bullet.
     *
     * @param size Size to be set.
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Gets the angle of the bullet.
     *
     * @return Double angle value of the bullet.
     */
    public double getAngle() {
        return angle;
    }

}
