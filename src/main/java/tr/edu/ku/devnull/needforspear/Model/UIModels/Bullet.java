package tr.edu.ku.devnull.needforspear.Model.UIModels;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Size;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;

public class Bullet {
    private NoblePhantasm noblePhantasm;
    private Size size;
    private Location location;
    private double angle;
    private int[] speed = new int[2];


    public Bullet() {
        this.noblePhantasm = NoblePhantasm.getInstance();
        this.size = new Size(Constants.ProportionConstants.RADIUS_OF_THE_BULLET, Constants.ProportionConstants.RADIUS_OF_THE_BULLET);
        this.angle = noblePhantasm.getRotationDegree()*57.29;
    }

    public static Bullet createLeftBullet(){
        System.out.println("left bullet created");
        Bullet bulletLeft = new Bullet();
        bulletLeft.setLocation(new Location((NoblePhantasm.getInstance().getLocation().getXCoordinates() + (NoblePhantasm.getInstance().getSize().getWidth() / 8) - (bulletLeft.getSize().getWidth())), (NoblePhantasm.getInstance().getLocation().getYCoordinates() - 2 * bulletLeft.getSize().getWidth())));
        bulletLeft.setSpeed(0,Constants.ProportionConstants.SPEED_OF_THE_BULLET);
        return bulletLeft;
    }


    public static Bullet createRightBullet(){
        System.out.println("right bullet created");
        Bullet bulletRight = new Bullet();
        bulletRight.setLocation(new Location((NoblePhantasm.getInstance().getLocation().getXCoordinates() + ( (NoblePhantasm.getInstance().getSize().getWidth() * 7)/ 8) - (bulletRight.getSize().getWidth())), (NoblePhantasm.getInstance().getLocation().getYCoordinates() - 2 * bulletRight.getSize().getWidth())));
        bulletRight.setSpeed(0,Constants.ProportionConstants.SPEED_OF_THE_BULLET);
        return bulletRight;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int[] getSpeed() {
        return speed;
    }

    public void setSpeed(int speedX, int speedY) {
        this.speed[0] = speedX;
        this.speed[1] = speedY;
    }

    public NoblePhantasm getNoblePhantasm() {
        return noblePhantasm;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public double getAngle() {
        return angle;
    }

}
