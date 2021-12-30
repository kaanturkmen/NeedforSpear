package tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMap;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Size;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.ExplosiveObstacle;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.GiftObstacle;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.ObstacleFactory;
import tr.edu.ku.devnull.needforspear.Model.UIModels.NoblePhantasm;
import tr.edu.ku.devnull.needforspear.Model.UIModels.Sphere;
import tr.edu.ku.devnull.needforspear.View.PlayViews.Animators.ObstacleAnimator;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * BuildModeHandler computes the operations related to the building mode
 * such as creating obstacle instances and setting their locations
 *
 * @author Can Usluel
 */
public class BuildModeHandler {
    private static BuildModeHandler onlyInstance = null;
    //Test variables
    private ObstacleFactory obstacleFactory;
    private String addedObstacleType;
    private Obstacle selectedObstacle;
    private Location previousLocation;
    private List<Obstacle> obstacleList;

    /**
     * Private Constructor for the BuildModeHandler.
     */
    private BuildModeHandler() {
        addedObstacleType = Constants.ObstacleNameConstants.SIMPLE;
        obstacleFactory = ObstacleFactory.getInstance();
    }

    /**
     * This method resets the Noble Phantasm's and Sphere's location
     */
    public void resetPhantasmAndSphereLocation() {
        NoblePhantasm.getInstance().resetLocation();
        NeedforSpearGame.getInstance().getGameInfo().getSphere().resetLocation();
    }

    /**
     * Singleton Design Pattern's getInstance Method for BuildModeHandler.
     */
    public static synchronized BuildModeHandler getInstance() {
        if (onlyInstance == null) onlyInstance = new BuildModeHandler();
        return onlyInstance;
    }

    /**
     * This method sets the type of the added obstacle which added via mouse click
     */
    public void setAddedObstacleType(String addedObstacleType) {
        this.addedObstacleType = addedObstacleType;
    }

    /**
     * @return Obstacle indicating the current clicked obstacle.
     */
    public Obstacle getSelectedObstacle() {
        return selectedObstacle;
    }

    /**
     * @return Location indicating the location of the current clicked obstacle.
     */
    public Location getPreviousLocation() {
        return previousLocation;
    }

    /**
     * This method sets the current obstacle to the obstacle which is clicked on
     */
    public void setSelectedObstacle(Obstacle obstacle) {
        this.selectedObstacle = obstacle;
    }

    /**
     * This method sets the current obstacle's location to the obstacle's location which is clicked on
     */
    public void setPreviousLocation(Location location) {
        this.previousLocation = location;
    }

    /**
     * This method sets game map to a new one and sets obstacleList to its list of obstacles
     */
    public void prepGameMap() {
        NeedforSpearGame.getInstance().getGameInfo().setGameMap(new GameMap(new Size(Constants.UIConstants.INITIAL_SCREEN_WIDTH, Constants.UIConstants.INITIAL_SCREEN_HEIGHT)));
        obstacleList = NeedforSpearGame.getInstance().getGameInfo().getGameMap().getListofObstacles();
    }

    /**
     * This method sets obstacle locations by starting from an initial location and placing gap between each other
     */
    public void setObstacleLocation(int startLocationY, int mapWidth) {
        Obstacle firstObstacle = NeedforSpearGame.getInstance().getGameInfo().getGameMap().getListofObstacles().get(0);
        firstObstacle.setLocation(new Location(2.0 * Constants.UIConstants.OBSTACLE_HGAP, 1.0*(startLocationY + Constants.UIConstants.OBSTACLE_VGAP)));
        setExplosiveOrbit(firstObstacle);
        double k;
        int j = 0;
        int m = 1;
        for (int i = 1; i < NeedforSpearGame.getInstance().getGameInfo().getGameMap().getListofObstacles().size(); i++) {
            k = (Constants.UIConstants.OBSTACLE_HGAP + firstObstacle.getSize().getWidth()) * m + firstObstacle.getLocation().getXCoordinates();
            Obstacle nextObstacle = NeedforSpearGame.getInstance().getGameInfo().getGameMap().getListofObstacles().get(i);
            if (k + nextObstacle.getSize().getWidth() + Constants.UIConstants.OBSTACLE_HGAP > mapWidth) {
                m = 1;
                j += Constants.UIConstants.OBSTACLE_VGAP + Constants.ProportionConstants.HEIGHT_OF_THE_OBSTACLE;
                k = firstObstacle.getLocation().getXCoordinates();
            } else {
                m++;

            }
            nextObstacle.setLocation(new Location(k, firstObstacle.getLocation().getYCoordinates() + j));
            setExplosiveOrbit(nextObstacle);
        }
    }

    /**
     * This method creates obstacles according to user input number and places them into game's obstacleList
     */
    public void createObstacles(int simpNum, int firmNum, int expNum, int giftNum) {
        //creating obstacles and adding them to obstacle list

        int i;
        for (i = 0; i < simpNum; i++) {
            Obstacle obstacle = obstacleFactory.getObstacle(Constants.ObstacleNameConstants.SIMPLE);
            NeedforSpearGame.getInstance().getGameInfo().getGameMap().getListofObstacles().add(obstacle);
        }
        for (i = 0; i < firmNum; i++) {
            Obstacle obstacle = obstacleFactory.getObstacle(Constants.ObstacleNameConstants.FIRM);
            NeedforSpearGame.getInstance().getGameInfo().getGameMap().getListofObstacles().add(obstacle);
        }
        for (i = 0; i < expNum; i++) {
            Obstacle obstacle = obstacleFactory.getObstacle(Constants.ObstacleNameConstants.EXP);
            NeedforSpearGame.getInstance().getGameInfo().getGameMap().getListofObstacles().add(obstacle);
        }
        for (i = 0; i < giftNum; i++) {
            Obstacle obstacle = obstacleFactory.getObstacle(Constants.ObstacleNameConstants.GIFT);
            NeedforSpearGame.getInstance().getGameInfo().getGameMap().getListofObstacles().add(obstacle);
        }
        //shuffling obstacle list for random placement
        Collections.shuffle(NeedforSpearGame.getInstance().getGameInfo().getGameMap().getListofObstacles());
    }

    /**
     * This method checks if the given numbers satisfy the minimum obstacle number condition
     *
     * @return boolean
     */
    public boolean checkObstacleNumbers(int simpObstacleNum, int firmObstacleNum, int explosiveObstacleNum, int giftObstacleNum) {
        return checkSimpleObstacleNum(simpObstacleNum) &&
                checkFirmObstacleNum(firmObstacleNum) &&
                checkExplosiveObstacleNum(explosiveObstacleNum) &&
                checkGiftObstacleNum(giftObstacleNum);
    }
    /**
     * This method checks if the given obstacle is far enough from noble phantasm
     * returns true if the obstacle is too close to phantasm, otherwise returns false
     * @return boolean
     */
    public boolean checkObstacleLocation(Obstacle obstacle){
        return !(obstacle.getLocation().getYCoordinates() <= NoblePhantasm.getInstance().getLocation().getYCoordinates() - Constants.UIConstants.OBSTACLE_DISTANCE_FROM_PHANTASM);
    }
    /**
     * This method checks if the given numbers satisfy the minimum obstacle number condition
     * for simple obstacles
     *
     * @return boolean
     */
    public boolean checkSimpleObstacleNum(int simpObstacleNum) {
        return simpObstacleNum >= Constants.ObstacleNumberConstants.MIN_SIMPLE_OBSTACLE_NUM &&
                simpObstacleNum <= Constants.ObstacleNumberConstants.MAX_SIMPLE_OBSTACLE_NUM;
    }

    /**
     * This method checks if the given numbers satisfy the minimum obstacle number condition
     * for firm obstacles
     *
     * @return boolean
     */
    public boolean checkFirmObstacleNum(int firmObstacleNum) {
        return firmObstacleNum >= Constants.ObstacleNumberConstants.MIN_FIRM_OBSTACLE_NUM &&
                firmObstacleNum <= Constants.ObstacleNumberConstants.MAX_FIRM_OBSTACLE_NUM;
    }

    /**
     * This method checks if the given numbers satisfy the minimum obstacle number condition
     * for explosive obstacles
     *
     * @return boolean
     */
    public boolean checkExplosiveObstacleNum(int explosiveObstacleNum) {
        return explosiveObstacleNum >= Constants.ObstacleNumberConstants.MIN_EXPLOSIVE_OBSTACLE_NUM &&
                explosiveObstacleNum <= Constants.ObstacleNumberConstants.MAX_EXPLOSIVE_OBSTACLE_NUM;
    }

    /**
     * This method checks if the given numbers satisfy the minimum obstacle number condition
     * for gift obstacles
     *
     * @return boolean
     */
    public boolean checkGiftObstacleNum(int giftObstacleNum) {
        return giftObstacleNum >= Constants.ObstacleNumberConstants.MIN_GIFT_OBSTACLE_NUM &&
                giftObstacleNum <= Constants.ObstacleNumberConstants.MAX_GIFT_OBSTACLE_NUM;
    }

    /**
     * @return Obstacle object from the given x y location
     */
    public Obstacle getObstacleByLocation(int x, int y) {
        for (int i = 0; i < obstacleList.size(); i++) {
            if (obstacleList.get(i).getLocation().getXCoordinates() <= x &&
                    obstacleList.get(i).getLocation().getXCoordinates() + obstacleList.get(i).getSize().getWidth() >= x &&
                    obstacleList.get(i).getLocation().getYCoordinates() <= y &&
                    obstacleList.get(i).getLocation().getYCoordinates() + obstacleList.get(i).getSize().getLength() >= y) {
                return obstacleList.get(i);
            }
        }
        return null;
    }

    /**
     * This method checks if the given obstacle collides with other obstacles
     *
     * @return boolean
     */
    public boolean doesObstacleCollide(Obstacle obstacle) {
        //if an obstacle collides with another, returns true
        //otherwise returns false
        if (obstacle != null) {
            double obstacleX = obstacle.getLocation().getXCoordinates();
            double obstacleY = obstacle.getLocation().getYCoordinates();
            int obstacleWidth = obstacle.getSize().getWidth();
            int obstacleLength = obstacle.getSize().getLength();
            Rectangle selectedObstacle = new Rectangle((int)obstacleX, (int)obstacleY, obstacleWidth, obstacleLength);
            for (int i = 0; i < obstacleList.size(); i++) {
                if (!obstacleList.get(i).equals(obstacle)) {
                    double otherObstacleX = obstacleList.get(i).getLocation().getXCoordinates();
                    double otherObstacleY = obstacleList.get(i).getLocation().getYCoordinates();
                    int otherObstacleWidth = obstacleList.get(i).getSize().getWidth();
                    int otherObstacleLength = obstacleList.get(i).getSize().getLength();
                    Rectangle otherDrawnObstacle = new Rectangle((int)otherObstacleX, (int)otherObstacleY, otherObstacleWidth, otherObstacleLength);
                    if (selectedObstacle.intersects(otherDrawnObstacle)) {
                        System.out.println("Intersection with " + selectedObstacle + otherDrawnObstacle);
                        System.out.println(i);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * This method redraws an obstacle in given location if mode is true,
     * otherwise draws the obstacle on its previous location
     */
    public void relocateObstacle(double x, double y, Graphics graphics, ObstacleAnimator obstacleAnimator, boolean mode) {
        if (mode) {
            Location newLocation = new Location(x, y);
            getSelectedObstacle().setLocation(newLocation);
            setExplosiveOrbit(getSelectedObstacle());
            obstacleAnimator.drawSingleObstacle(graphics, getSelectedObstacle());
            graphics.dispose();
        } else {
            getSelectedObstacle().setLocation(getPreviousLocation());
            setExplosiveOrbit(getSelectedObstacle());
            obstacleAnimator.drawSingleObstacle(graphics, getSelectedObstacle());
        }


    }

    /**
     * This method creates and and draws new obstacle in given location
     */
    public void createNewObstacle(double x, double y, String obstacleType, Graphics graphics, ObstacleAnimator obstacleAnimator) {
        Obstacle obstacle = obstacleFactory.getObstacle(obstacleType);
        NeedforSpearGame.getInstance().getGameInfo().getGameMap().getListofObstacles().add(obstacle);
        Location loc = new Location(x, y);
        obstacle.setLocation(loc);
        setExplosiveOrbit( getSelectedObstacle());

        if(obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.GIFT)){
            GiftObstacle giftObstacle = (GiftObstacle) obstacle;
            giftObstacle.setSpell(SpellHandler.getInstance().setRandomSpell(giftObstacle));
        }
        
        obstacleAnimator.drawSingleObstacle(graphics, obstacle);

    }

    /**
     * This method removes obstacle in given location
     */
    public void removeObstacle(int x, int y, Graphics graphics, Obstacle obstacle) {
        graphics.clearRect(x, y, obstacle.getSize().getWidth(), obstacle.getSize().getLength());
        NeedforSpearGame.getInstance().getGameInfo().getGameMap().getListofObstacles().remove(obstacle);
    }

    /**
     * @return String type of the obstacle that is added via mouse click
     */
    public String getAddedObstacleType() {
        return addedObstacleType;
    }

    /**
     * This method sets the obstacleList to the given list of obstacles
     */
    public void setObstacleList(List<Obstacle> obstacleList) {
        this.obstacleList = obstacleList;
    }

    public void setExplosiveOrbit(Obstacle obstacle){
        if(obstacle !=null){
            if (obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.EXP)) {
                ExplosiveObstacle explosive = (ExplosiveObstacle) obstacle;
                explosive.alterOrbitCenter();
            }
        }
    }

    /**
     * creates hollow obstacles and places them
     * @param graphics
     * @param obstacleAnimator
     */
    public void createHollowObstacles(Graphics graphics, ObstacleAnimator obstacleAnimator){
        List<Location> availableLocations = getAvailableLocations();
        Collections.shuffle(availableLocations);
        for(int i = 0; i<Constants.ObstacleNumberConstants.HOLLOW_OBSTACLE_NUM; i++){
            System.out.println(availableLocations.get(i).getXCoordinates() + " " + availableLocations.get(i).getYCoordinates());
            createNewObstacle(availableLocations.get(i).getXCoordinates(), availableLocations.get(i).getYCoordinates(),
                    Constants.ObstacleNameConstants.HOLLOW, graphics,obstacleAnimator);
        }
    }

    /**
     * returns a list of available locations for placing obstacles
     * @return
     */
    private List<Location> getAvailableLocations(){
        Obstacle dummyObstacle = ObstacleFactory.getInstance().getObstacle(Constants.ObstacleNameConstants.SIMPLE);
        List<Location> availableLocations = new ArrayList<>();
        for(int y = 0; y<NoblePhantasm.getInstance().getLocation().getYCoordinates() - Constants.UIConstants.OBSTACLE_DISTANCE_FROM_PHANTASM; y++){
            for(int x = 0; x<Constants.UIConstants.INITIAL_SCREEN_WIDTH-dummyObstacle.getSize().getWidth(); x++){
                if(checkIfPointAvailable(x,y)){
                    availableLocations.add(new Location(x,y));
                    if(!(x+dummyObstacle.getSize().getWidth() > Constants.UIConstants.INITIAL_SCREEN_WIDTH)){
                        x+=dummyObstacle.getSize().getWidth();
                    }
                }
            }
        }
        return availableLocations;
    }

    /**
     * returns true if the given point is available for putting obstacle
     * @param x
     * @param y
     * @return boolean
     */
    private boolean checkIfPointAvailable(int x, int y){
        for (int i = 0; i < obstacleList.size(); i++) {
            if (obstacleList.get(i).getLocation().getXCoordinates() - obstacleList.get(i).getSize().getWidth() <= x &&
                    obstacleList.get(i).getLocation().getXCoordinates() + obstacleList.get(i).getSize().getWidth() >= x &&
                    obstacleList.get(i).getLocation().getYCoordinates() - obstacleList.get(i).getSize().getLength()<= y &&
                    obstacleList.get(i).getLocation().getYCoordinates() + obstacleList.get(i).getSize().getLength() >= y){
                return false;
            }
        }
        return true;
    }
}
