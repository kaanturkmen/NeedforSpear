package tr.edu.ku.devnull.needforspear.Model.UIModels;

import tr.edu.ku.devnull.needforspear.Model.GameData.*;
import tr.edu.ku.devnull.needforspear.Model.Spell.Spell;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * NoblePhantasm is a class which contains all the
 * size, movement and map specifications for
 * the noble phantasm to operate within the game, as well
 * as initializing the noble phantasm itself.
 */
public class NoblePhantasm {
    private static NoblePhantasm onlyInstance = null;
    private GameMap gameMap;
    private Size size;
    private Location location;
    private Double speed;
    private Double rotationDegree;
    private Double rotationSpeed;
    private Long lastUpdateTime;
    private boolean rotatingLeft = false;
    private boolean rotatingRight = false;
    private boolean isMovingRight = false;
    private boolean isMovingLeft = false;
    private boolean isSpeeding = false;
    private Long startTimeMoving = 0L;
    private boolean doubleSize = false;
    private boolean isMagicActivated = false;
    private boolean isExpansionSpell =false;
    private long expansionStartTime;
    private boolean isMagicalHex = false;
    private long magicalHexStartTime;
    private List<Bullet> listOfBullets;


    /**
     * Constructor for NoblePhantasm.
     */
    private NoblePhantasm() {
        this.gameMap = new GameMap(new Size(Constants.UIConstants.INITIAL_SCREEN_WIDTH, Constants.UIConstants.INITIAL_SCREEN_HEIGHT));
        this.size = new Size((int) (Constants.UIConstants.INITIAL_SCREEN_WIDTH * Constants.ProportionConstants.RATIO_OF_NOBLE_PHANTASM), Constants.ProportionConstants.HEIGHT_OF_NOBLE_PHANTASM);
        this.location = new Location((Constants.UIConstants.INITIAL_SCREEN_WIDTH - this.size.getWidth()) / 2.0, Constants.UIConstants.INITIAL_SCREEN_HEIGHT - (Constants.UIConstants.OVERLAY_PANEL_HEIGHT + Constants.UIConstants.OVERLAY_PANEL_HEIGHT / 2.0));
        this.speed = 0.0;
        this.rotationDegree = 0.0;
        this.rotationSpeed = 0.0;
        this.lastUpdateTime = 0L;
    }

    /**
     * Singleton design pattern for creation of NoblePhantasm.
     *
     * @return new NoblePhantasm if no previous instances present.
     */
    public static synchronized NoblePhantasm getInstance() {
        if (onlyInstance == null) onlyInstance = new NoblePhantasm();
        return onlyInstance;
    }

    public void resetLocation() {
        this.size = new Size((int) (Constants.UIConstants.INITIAL_SCREEN_WIDTH * Constants.ProportionConstants.RATIO_OF_NOBLE_PHANTASM), Constants.ProportionConstants.HEIGHT_OF_NOBLE_PHANTASM);
        this.location = new Location((Constants.UIConstants.INITIAL_SCREEN_WIDTH - this.size.getWidth()) / 2.0,
                Constants.UIConstants.INITIAL_SCREEN_HEIGHT - (Constants.UIConstants.OVERLAY_PANEL_HEIGHT + Constants.UIConstants.OVERLAY_PANEL_HEIGHT / 2.0));
        this.speed = 0.0;
        this.rotationDegree = 0.0;
    }


    public boolean isSpeeding() {
        return isSpeeding;
    }

    public void setSpeeding(boolean speeding) {
        isSpeeding = speeding;
    }

    public boolean isMovingRight() {
        return isMovingRight;
    }

    public void setMovingRight(boolean movingRight) {
        isMovingRight = movingRight;
    }

    public boolean isMovingLeft() {
        return isMovingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        isMovingLeft = movingLeft;
    }

    public Long getStartTimeMoving() {
        return startTimeMoving;
    }

    public void setStartTimeMoving(Long startTimeMoving) {
        this.startTimeMoving = startTimeMoving;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
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

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getRotationDegree() {
        return rotationDegree;
    }

    public Double getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationDegree(Double rotationDegree) {
        this.rotationDegree = rotationDegree;
    }

    public boolean isLeftRotate() {
        return rotatingLeft;
    }

    public void setLeftRotate(boolean leftRotate) {
        this.rotatingLeft = leftRotate;
    }

    public boolean isRightRotate() {
        return rotatingRight;
    }

    public void setRightRotate(boolean rightRotate) {
        this.rotatingRight = rightRotate;
    }

    public void setLastUpdateTime(Long time) {
        this.lastUpdateTime = time;
    }

    public Long getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public boolean isDoubleSize() {
        return doubleSize;
    }

    public void setDoubleSize(boolean doubleSize) {
        this.doubleSize = doubleSize;
    }

    public boolean isMagicActivated() {
        return isMagicActivated;
    }

    public void setMagicActivated(boolean activated) {
        this.isMagicActivated = activated;
    }

    public void activateExpansionSpell(){
        //long current_time = System.currentTimeMillis();
        //long start_time = noblePhantasm.getExpansionStartTime();
        if (!this.isMagicActivated) {
            int stopDelay = 30 * 1000; //milliseconds
            TimerTask stopTask = new TimerTask() {
                @Override
                public void run() {
                    deactivateExpansionSpell();
                }
            };
            Timer stopTimer = new Timer();
            stopTimer.schedule(stopTask, stopDelay);
            double oldx = this.getLocation().getXCoordinates();
            double oldy = this.getLocation().getYCoordinates();
            int oldWidth = Constants.ProportionConstants.WIDTH_OF_NOBLE_PHANTASM;
            double newx = oldx - this.getSize().getWidth() / 2;
            int newWidth = oldWidth * 2;
            this.setLocation(new Location(newx, oldy));
            this.getSize().setWidth(newWidth);
            this.setMagicActivated(true);
        }
    }

    public void deactivateExpansionSpell(){
        double oldx = this.getLocation().getXCoordinates();
        double oldy  = this.getLocation().getYCoordinates();
        int oldWidth = Constants.ProportionConstants.WIDTH_OF_NOBLE_PHANTASM;
        double newx = oldx+this.getSize().getWidth()/4;
        int newWidth = oldWidth;
        this.setLocation(new Location(newx, oldy));
        this.getSize().setWidth(newWidth);
        this.setMagicActivated(false);
    }
}

