package tr.edu.ku.devnull.needforspear.Model.UIModels;

import tr.edu.ku.devnull.needforspear.Model.GameData.*;

import java.util.Timer;
import java.util.TimerTask;

/**
 * NoblePhantasm is a class which contains all the
 * size, movement and map specifications for
 * the noble phantasm to operate within the game, as well
 * as initializing the noble phantasm itself.
 */
public class NoblePhantasm {
    private static NoblePhantasm onlyInstance;
    private GameMap gameMap;
    private Size size;
    private Location location;
    private double speed, rotationDegree, rotationSpeed;
    private Long lastUpdateTime;
    private boolean rotatingLeft = false, rotatingRight = false, isMovingRight = false, isMovingLeft = false,
            isSpeeding = false, isMagicActivated = false, isExpansionSpell = false, isMagicalHex = false;
    private Long startTimeMoving = 0L;
    private boolean doubleSize = false;
    private long expansionStartTime;


    /**
     * Constructor for NoblePhantasm.
     */
    private NoblePhantasm() {
        this.gameMap = new GameMap(new Size(Constants.UIConstants.INITIAL_SCREEN_WIDTH, Constants.UIConstants.INITIAL_SCREEN_HEIGHT));
        this.size = new Size((int) (Constants.UIConstants.INITIAL_SCREEN_WIDTH * Constants.ProportionConstants.RATIO_OF_NOBLE_PHANTASM), Constants.ProportionConstants.HEIGHT_OF_NOBLE_PHANTASM);
        this.location = new Location((Constants.UIConstants.INITIAL_SCREEN_WIDTH - this.size.getWidth()) / Constants.UIConstants.DIVISION_CONSTANT_OF_PHANTASM, Constants.UIConstants.INITIAL_SCREEN_HEIGHT - (Constants.UIConstants.OVERLAY_PANEL_HEIGHT + Constants.UIConstants.OVERLAY_PANEL_HEIGHT / Constants.UIConstants.DIVISION_CONSTANT_OF_PHANTASM));
        this.speed = 0.0;
        this.rotationDegree = 0.0;
        this.rotationSpeed = 0.0;
        this.lastUpdateTime = 0L;
    }

    /**
     * Singleton Design Pattern's getInstance methods. Gets the single instance of the object.
     *
     * @return Single instance of the NoblePhantasm.
     */
    public static NoblePhantasm getInstance() {
        if (onlyInstance == null) onlyInstance = new NoblePhantasm();

        return onlyInstance;
    }

    /**
     * Resets the location of the noble phantasm.
     */
    public void resetLocation() {
        this.size = new Size((int) (Constants.UIConstants.INITIAL_SCREEN_WIDTH * Constants.ProportionConstants.RATIO_OF_NOBLE_PHANTASM), Constants.ProportionConstants.HEIGHT_OF_NOBLE_PHANTASM);
        this.location = new Location((Constants.UIConstants.INITIAL_SCREEN_WIDTH - this.size.getWidth()) / Constants.UIConstants.DIVISION_CONSTANT_OF_PHANTASM,
                Constants.UIConstants.INITIAL_SCREEN_HEIGHT - (Constants.UIConstants.OVERLAY_PANEL_HEIGHT + Constants.UIConstants.OVERLAY_PANEL_HEIGHT / Constants.UIConstants.DIVISION_CONSTANT_OF_PHANTASM));
        this.speed = 0.0;
        this.rotationDegree = 0.0;
    }

    /**
     * Checks if the noble phantasm is speeding.
     *
     * @return Boolean value indicating if the noble phantasm is speeding.
     */
    public boolean isSpeeding() {
        return isSpeeding;
    }

    /**
     * Sets speeding.
     *
     * @param speeding Speeding boolean value to be set.
     */
    public void setSpeeding(boolean speeding) {
        isSpeeding = speeding;
    }

    /**
     * Checks if the noble phantasm is moving right.
     *
     * @return Boolean value indicating if the noble phantasm is moving right.
     */
    public boolean isMovingRight() {
        return isMovingRight;
    }

    /**
     * Sets moving right property.
     *
     * @param movingRight Moving right boolean to be set.
     */
    public void setMovingRight(boolean movingRight) {
        isMovingRight = movingRight;
    }

    /**
     * Checks if the noble phantasm is moving left.
     *
     * @return Boolean value indicating if the noble phantasm is moving left.
     */
    public boolean isMovingLeft() {
        return isMovingLeft;
    }

    /**
     * Sets moving left property.
     *
     * @param movingLeft Moving left boolean to be set.
     */
    public void setMovingLeft(boolean movingLeft) {
        isMovingLeft = movingLeft;
    }

    /**
     * Gets the moving start time.
     *
     * @return Start time in terms of milliseconds.
     */
    public Long getStartTimeMoving() {
        return startTimeMoving;
    }

    /**
     * Sets start time of moving.
     *
     * @param startTimeMoving Start time to be set.
     */
    public void setStartTimeMoving(Long startTimeMoving) {
        this.startTimeMoving = startTimeMoving;
    }

    /**
     * Gets the game map.
     *
     * @return GameMap which has current noble phantasm.
     */
    public GameMap getGameMap() {
        return gameMap;
    }

    /**
     * Sets the game map.
     *
     * @param gameMap GameMap which will be associated with this noble phantasm.
     */
    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    /**
     * Gets the size of noble phantasm
     *
     * @return Size of the noble phantasm.
     */
    public Size getSize() {
        return size;
    }

    /**
     * Sets the size of the noble phantasm.
     *
     * @param size Size to be set.
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Gets the location of the noble phantasm.
     *
     * @return Location of the noble phantasm.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the location of the noble phantasm.
     *
     * @param location Location of the noble phantasm.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Gets the speed of the noble phantasm.
     *
     * @return Speed of the noble phantasm.
     */
    public Double getSpeed() {
        return speed;
    }

    /**
     * Sets the speed of the noble phantasm.
     *
     * @param speed Speed to be set.
     */
    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    /**
     * Gets the rotation degree of the noble phantasm.
     *
     * @return Rotation degree of the noble phantasm.
     */
    public Double getRotationDegree() {
        return rotationDegree;
    }

    /**
     * Gets the rotation speed of the noble phantasm.
     *
     * @return Rotation speed of the noble phantasm.
     */
    public Double getRotationSpeed() {
        return rotationSpeed;
    }

    /**
     * Sets the rotation degree of the noble phantasm.
     *
     * @param rotationDegree Rotation degree to be set.
     */
    public void setRotationDegree(Double rotationDegree) {
        this.rotationDegree = rotationDegree;
    }

    /**
     * Checks if rotating left.
     *
     * @return Boolean indicating if noble phantasm rotating left.
     */
    public boolean isLeftRotate() {
        return rotatingLeft;
    }

    /**
     * Sets if noble phantasm rotating left.
     *
     * @param leftRotate Boolean to be set.
     */
    public void setLeftRotate(boolean leftRotate) {
        this.rotatingLeft = leftRotate;
    }

    /**
     * Checks if rotating right.
     *
     * @return Boolean indicating if noble phantasm rotating right.
     */
    public boolean isRightRotate() {
        return rotatingRight;
    }

    /**
     * Sets if noble phantasm rotating right.
     *
     * @param rightRotate Boolean to be set.
     */
    public void setRightRotate(boolean rightRotate) {
        this.rotatingRight = rightRotate;
    }

    /**
     * Sets the last update time.
     *
     * @param time Time to be set.
     */
    public void setLastUpdateTime(Long time) {
        this.lastUpdateTime = time;
    }

    /**
     * Gets the last update time.
     *
     * @return Last update time in terms of milliseconds.
     */
    public Long getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    /**
     * Gets if it is doubled in size.
     *
     * @return Boolean value indicating if it is doubled.
     */
    public boolean isDoubleSize() {
        return doubleSize;
    }

    /**
     * Sets the mode which noble phantasm is double.
     *
     * @param doubleSize Boolean to be set.
     */
    public void setDoubleSize(boolean doubleSize) {
        this.doubleSize = doubleSize;
    }

    /**
     * Checks if the magic activated.
     *
     * @return Boolean indicating if the magic is activated.
     */
    public boolean isMagicActivated() {
        return isMagicActivated;
    }

    /**
     * Sets the magic activated property.
     *
     * @param activated Boolean to be set.
     */
    public void setMagicActivated(boolean activated) {
        this.isMagicActivated = activated;
    }

    /**
     * Activates the expension spell.
     */
    public void activateExpansionSpell() {
        if (!this.isMagicActivated) {
            int stopDelay = Constants.UIConstants.TWICE_SPELL_DURATION_SECONDS * Constants.UIConstants.MILISECONDS_TO_SECONDS; //milliseconds
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
            double newx = oldx - this.getSize().getWidth() / Constants.UIConstants.DIVISION_CONSTANT_OF_PHANTASM;
            int newWidth = oldWidth * 2;
            this.setLocation(new Location(newx, oldy));
            this.getSize().setWidth(newWidth);
            this.setMagicActivated(true);
        }
    }

    /**
     * Deactivates the expansion spell.
     */
    public void deactivateExpansionSpell() {
        double oldx = this.getLocation().getXCoordinates();
        double oldy = this.getLocation().getYCoordinates();
        int oldWidth = Constants.ProportionConstants.WIDTH_OF_NOBLE_PHANTASM;
        double newx = oldx + this.getSize().getWidth() / 4.0;
        this.setLocation(new Location(newx, oldy));
        this.getSize().setWidth(oldWidth);
        this.setMagicActivated(false);
    }
}

