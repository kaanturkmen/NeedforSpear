package tr.edu.ku.devnull.needforspear.Model.GameData;

/**
 * Location is a class which contains the value of the x and y
 * coordinates of an object.
 */
public class Location {
    private double XCoordinates;
    private double YCoordinates;

    /**
     * Constructor of the Location for the int values.
     *
     * @param XCoordinates Integer value of x coordinate of location.
     * @param YCoordinates Integer value of y coordinate of location.
     */
    public Location(int XCoordinates, int YCoordinates) {
        this.XCoordinates = XCoordinates;
        this.YCoordinates = YCoordinates;
    }

    /**
     * Constructor of the Location for the double values.
     *
     * @param XCoordinates Double value of x coordinate of location.
     * @param YCoordinates Double value of y coordinate of location.
     */
    public Location(double XCoordinates, double YCoordinates) {
        this.XCoordinates = XCoordinates;
        this.YCoordinates = YCoordinates;
    }

    /**
     * Empty constructor for the database operations.
     */
    public Location() {
    }

    /**
     * Gets the x coordinate of location.
     *
     * @return double value of x coordinate.
     */
    public double getXCoordinates() {
        return XCoordinates;
    }

    /**
     * Sets the x coordinate of location.
     *
     * @param XCoordinates double value to be set for x coordinate of Location.
     */
    public void setXCoordinates(double XCoordinates) {
        this.XCoordinates = XCoordinates;
    }

    /**
     * Gets the y coordinate of location.
     *
     * @return Double value of y coordinate.
     */
    public double getYCoordinates() {
        return YCoordinates;
    }

    /**
     * Sets the y coordinate of location.
     *
     * @param YCoordinates Double value to be set for y coordinate of Location.
     */
    public void setYCoordinates(double YCoordinates) {
        this.YCoordinates = YCoordinates;
    }
}
