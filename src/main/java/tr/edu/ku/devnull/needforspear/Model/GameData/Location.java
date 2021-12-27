package tr.edu.ku.devnull.needforspear.Model.GameData;

/**
 * Location is a class which contains the value of the x and y
 * coordinates of an object.
 */
public class Location {
    private Double XCoordinates;
    private Double YCoordinates;

    /**
     * Constructor for Location.
     *
     * @param XCoordinates Integer value of x coordinate of location.
     * @param YCoordinates Integer value of y coordinate of location.
     */

    public Location(int XCoordinates, int YCoordinates) {
        this.XCoordinates = (double)XCoordinates;
        this.YCoordinates = (double)YCoordinates;
    }

    public Location(Double XCoordinates, Double YCoordinates) {
        this.XCoordinates = XCoordinates;
        this.YCoordinates = YCoordinates;
    }

    /**
     * Constructor for Location.
     */
    public Location() {
    }

    /**
     * Gets the x coordinate of location.
     *
     * @return Integer value of x coordinate.
     */
    public Double getXCoordinates() {
        return XCoordinates;
    }

    /**
     * Sets the x coordinate of location.
     *
     * @param XCoordinates Integer value to be set for x coordinate of Location.
     */
    public void setXCoordinates(Double XCoordinates) {
        this.XCoordinates = XCoordinates;
    }

    /**
     * Gets the y coordinate of location.
     *
     * @return Integer value of y coordinate.
     */
    public Double getYCoordinates() {
        return YCoordinates;
    }

    /**
     * Sets the y coordinate of location.
     *
     * @param YCoordinates Integer value to be set for y coordinate of Location.
     */
    public void setYCoordinates(Double YCoordinates) {
        this.YCoordinates = YCoordinates;
    }
}
