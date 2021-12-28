package tr.edu.ku.devnull.needforspear.Model.GameData;

import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;

import java.util.ArrayList;
import java.util.List;

/**
 * GameMap is a class which contains the information required
 * to create the map to be played on.
 */
public class GameMap {
    private List<Obstacle> listofObstacles;
    private Size size;

    /**
     * Constructor for the GameMap.
     *
     * @param size Integer dimensions of the map.
     */
    public GameMap(Size size) {
        this.listofObstacles = new ArrayList<Obstacle>();
        this.size = size;
    }

    /**
     * Constructor for the GameMap.
     */
    public GameMap() {
    }

    /**
     * Gets the list which contains all the obstacles in game map.
     *
     * @return List of obstacles in the game map.
     */
    public List<Obstacle> getListofObstacles() {
        return listofObstacles;
    }

    /**
     * Gets the size of game map.
     *
     * @return Size value of game map.
     */
    public Size getSize() {
        return size;
    }

    /**
     * Sets the size of game map.
     *
     * @param size Size value to be set of game map.
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Textually represents the attributes of game map.
     *
     * @return String containing information of GameMap.
     */
    @Override
    public String toString() {
        return "GameMap{" +
                "listofObstacles=" + listofObstacles +
                ", size=" + size +
                '}';
    }
}