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
     * Finds the number of simple obstacles in game map.
     *
     * @return Integer value of number of simple obstacles in map.
     */
    public int retrieveSimpleObstacleNumber() {
        int x = 0;
        for (Obstacle obs : listofObstacles) {
            if (obs.getObstacleType().equals(Constants.ObstacleNameConstants.SIMPLE)) {
                x++;
            }
        }
        return x;
    }

    /**
     * Finds the number of firm obstacles in game map.
     *
     * @return Integer value of number of firm obstacles in map.
     */
    public int retrieveFirmObstacleNumber() {
        int x = 0;
        for (Obstacle obs : listofObstacles) {
            if (obs.getObstacleType().equals(Constants.ObstacleNameConstants.FIRM)) {
                x++;
            }
        }
        return x;
    }

    /**
     * Finds the number of explosive obstacles in game map.
     *
     * @return Integer value of number of explosive obstacles in map.
     */
    public int retrieveExplosiveObstacleNumber() {
        int x = 0;
        for (Obstacle obs : listofObstacles) {
            if (obs.getObstacleType().equals(Constants.ObstacleNameConstants.EXP)) {
                x++;
            }
        }
        return x;
    }

    /**
     * Finds the number of gift obstacles in game map.
     *
     * @return Integer value of number of gift obstacles in map.
     */
    public int retrieveGiftObstacleNumber() {
        int x = 0;
        for (Obstacle obs : listofObstacles) {
            if (obs.getObstacleType().equals(Constants.ObstacleNameConstants.GIFT)) {
                x++;
            }
        }
        return x;
    }

    /**
     * Adds obstacles to the list which contains all the obstacles in game map.
     *
     * @param obstacle the obstacle to be added in the list.
     */
    public void addToListOfObstacles(Obstacle obstacle) {
        listofObstacles.add(obstacle);
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
     * Changes the list of obstacles the game map has.
     *
     * @param listofObstacles List of obstacles to be given to the game map.
     */
    public void setListofObstacles(List<Obstacle> listofObstacles) {
        this.listofObstacles = listofObstacles;
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