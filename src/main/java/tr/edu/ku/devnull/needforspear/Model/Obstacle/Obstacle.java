package tr.edu.ku.devnull.needforspear.Model.Obstacle;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMap;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Size;
import tr.edu.ku.devnull.needforspear.Model.Spell.Spell;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Obstacle is a class that extends ObstacleDescription which contains
 * information regarding an obstacle's attributes.
 */
public class Obstacle {
    protected int health;
    protected double speed;
    protected List<ObstacleSubscriber> obstacleSubscribers = new ArrayList<>();
    protected String obstacleType;
    protected String color;
    protected Location orbitCenter;
    protected Location location;
    protected GameMap gameMap;
    protected Size size;
    protected Spell spell;
    protected boolean isInfiniteVoidActivated;
    protected boolean isInvincible = false;

    /**
     * Empty constructor of Obstacle for the database operations.
     */
    public Obstacle() {
    }

    /**
     * Reduces the health of an obstacle by one and notifies the subscriber method
     * if obstacle is destroyed to update the game state accordingly.
     */
    public void damageObstacle() {
        if (!isInfiniteVoidActivated || NeedforSpearGame.getInstance().getGameInfo().getSphere().isUnstoppable()) {
            health -= Constants.UIConstants.ONE_HIT_DAMAGE;
            System.out.println("Damage registered on obstacle! \n");
        } else {
            System.out.println("couldn't damage obstacle due to infinite void! \n");
        }

        if (health <= 0) {
            notifySubscribers();
        }
    }

    /**
     * Gets the Orbit Location.
     *
     * @return Location of the orbit.
     */
    public Location getOrbitCenter() {
        return orbitCenter;
    }

    /**
     * Sets the orbit center.
     *
     * @param orbitCenter Orbit center to be set.
     */
    public void setOrbitCenter(Location orbitCenter) {
        this.orbitCenter = orbitCenter;
    }

    /**
     * Gets the subscriber method of obstacle.
     *
     * @return ObstacleSubscriber of obstacle.
     */
    public List<ObstacleSubscriber> getObstacleSubscribers() {
        return obstacleSubscribers;
    }

    /**
     * Sets the subscriber method for obstacle.
     *
     * @param obstacleSubscribers ObstacleSubscriber value to be set for obstacle.
     */
    public void setObstacleSubscribers(List<ObstacleSubscriber> obstacleSubscribers) {
        this.obstacleSubscribers = obstacleSubscribers;
    }

    /**
     * Gets the type of obstacle.
     *
     * @return String containing value of obstacleType for Obstacle.
     */
    public String getObstacleType() {
        return obstacleType;
    }

    /**
     * Sets the type of obstacle.
     *
     * @param obstacleType obstacleType value to be set for Obstacle.
     */
    public void setObstacleType(String obstacleType) {
        this.obstacleType = obstacleType;
    }

    /**
     * Sets the map the obstacle will be in.
     *
     * @param gameMap GameMap to be set for Obstacle.
     */
    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    /**
     * Gets the health of obstacle.
     *
     * @return Integer value of health of Obstacle.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the health of obstacle.
     *
     * @param health health value to be set for Obstacle.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Gets the speed of obstacle.
     *
     * @return Double value of speed of Obstacle.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Sets the speed of obstacle.
     *
     * @param speed speed value to be set for Obstacle.
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Gets the size of obstacle.
     *
     * @return Size value of Obstacle.
     */
    public Size getSize() {
        return size;
    }

    /**
     * Sets the size of obstacle.
     *
     * @param size Size value to be set for Obstacle.
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Gets the location of obstacle.
     *
     * @return Location value of Obstacle.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets the location of obstacle.
     *
     * @param location location value to be set for Obstacle.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Subscribes the obstacle to given subscriber.
     *
     * @param obstacleSubscriber ObstacleSubscriber to be subscribed for Obstacle.
     */
    public void subscribe(ObstacleSubscriber obstacleSubscriber) {
        obstacleSubscribers.add(obstacleSubscriber);
    }

    /**
     * Unsubscribes the obstacle from given subscriber.
     *
     * @param obstacleSubscriber ObstacleSubscriber to be unsubscribed from Obstacle.
     */
    public void unsubscribe(ObstacleSubscriber obstacleSubscriber) {
        obstacleSubscribers.remove(obstacleSubscriber);
    }

    /**
     * Notifies every ObstacleSubscriber of obstacle, which updates game state accordingly
     */
    public void notifySubscribers() {
        for (ObstacleSubscriber obstacleSubscriber : obstacleSubscribers) {
            obstacleSubscriber.update(this);
        }
    }

    /**
     * Gets the color of Obstacle.
     *
     * @return String containing color of obstacle.
     */
    public String getColor() {
        return this.color;
    }

    /**
     * Sets the color of obstacle.
     *
     * @param color String containing color value to be set for Obstacle.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Gets the spell.
     *
     * @return Spell to be used.
     */
    public Spell getSpell() {
        return spell;
    }

    /**
     * Sets the spell.
     *
     * @param spell Spell to be set.
     */
    public void setSpell(Spell spell) {
        this.spell = spell;
    }

    /**
     * Gets the orbit bounds.
     *
     * @return Rectangle of orbit bounds.
     */
    public Rectangle retrieveOrbitBounds() {
        int center_x = (int) orbitCenter.getXCoordinates();
        int center_y = (int) orbitCenter.getYCoordinates();

        double rect_x = center_x - Constants.ProportionConstants.EXPLOSIVE_ORBIT_RADIUS;
        double rect_y = center_y - center_x - Constants.ProportionConstants.EXPLOSIVE_ORBIT_RADIUS;

        return new Rectangle((int) rect_x, (int) rect_y, (int) (Constants.ProportionConstants.EXPLOSIVE_ORBIT_RADIUS), (int) (Constants.ProportionConstants.EXPLOSIVE_ORBIT_RADIUS));
    }

    /**
     * Gets the color equivalent of the given string.
     *
     * @param color String name of the color.
     * @return Color class equivalent of the given string.
     */
    public Color retrieveColorEquivalent(String color) {

        Color colorSelection = Color.BLACK;

        switch (color) {
            case Constants.UIConstants.RED_COLOR_STRING:
                colorSelection = Color.RED;
                break;
            case Constants.UIConstants.BLUE_COLOR_STRING:
                colorSelection = Color.BLUE;
                break;
            case Constants.UIConstants.ORANGE_COLOR_STRING:
                colorSelection = Color.ORANGE;
                break;
            case Constants.UIConstants.GREEN_COLOR_STRING:
                colorSelection = Color.GREEN;
                break;
            case Constants.UIConstants.PURPLE_COLOR_STRING:
                colorSelection = Constants.UIConstants.PURPLE_COLOR;
                break;
            default:
                break;
        }

        return colorSelection;
    }

    /**
     * Triggers the freeze effect.
     */
    public void freeze() {
        if (!this.isInfiniteVoidActivated) {
            int stopDelay = Constants.UIConstants.SPELL_DURATION_SECONDS * Constants.UIConstants.MILISECONDS_TO_SECONDS;
            TimerTask stopTask = new TimerTask() {
                @Override
                public void run() {
                    deactivateInfiniteVoidSpell();
                }
            };
            Timer stopTimer = new Timer();
            stopTimer.schedule(stopTask, stopDelay);
        }
    }

    /**
     * Activates the Infinite Void spell.
     *
     * @param activated Boolean value indicating if it is activated.
     */
    public void activateInfiniteVoid(boolean activated) {
        this.isInfiniteVoidActivated = activated;
    }

    /**
     * Deactivates the Infinite Void spell.
     */
    public void deactivateInfiniteVoidSpell() {
        this.isInfiniteVoidActivated = false;
        System.out.println("infinite void deactivated");
    }

    /**
     * Gets if the obstacle is invincible.
     *
     * @return Boolean value indicating if the obstacle is invincible.
     */
    public boolean isInvincible() {
        return isInvincible;
    }

    /**
     * Sets if the obstacle is invincible.
     *
     * @param invincible Boolean value to be set.
     */
    public void setInvincible(boolean invincible) {
        isInvincible = invincible;
    }
}