package tr.edu.ku.devnull.needforspear.Model.GameData;


/*

    This kind of documentation is required by the COMP302 Tasks:

    1) Abstract Functions.
    2) Representation Invariant.
    3) RepOK.

    Also, it includes some logic to ability to test the code as requested.


 */

/**
 * OVERVIEW: Speed is a class which contains the value of speed on the x and y-axis of an object.
 * <p>
 * The abstraction function is
 * AF(x) = {x.speedOnXAxis & x.speedOnYAxis | Double.MIN_VALUE <= x.speedOnXAxis, x.speedOnYAxis <= Double.MAX_VALUE}
 * <p>
 * The rep invariant is
 * x != null && Double.MIN_VALUE <= x.speedOnXAxis, x.speedOnYAxis <= Double.MAX_VALUE &&
 * x.speedOnXAxis & x.speedOnYAxis should be Double.
 */
public class Speed {
    private double speedOnXAxis;
    private double speedOnYAxis;
    private long xLong;
    private long yLong;

    /**
     * Constructor for the Speed.
     *
     * @param SpeedOnXAxis Integer value of speed on X-axis.
     * @param SpeedOnYAxis Integer value of speed on Y-axis
     */

    public Speed(int SpeedOnXAxis, int SpeedOnYAxis) {
        this.speedOnXAxis = SpeedOnXAxis;
        this.speedOnYAxis = SpeedOnYAxis;
    }

    /**
     * Constructor for the Speed.
     *
     * @param SpeedOnXAxis Double value of speed on X-axis.
     * @param SpeedOnYAxis Double value of speed on Y-axis.
     */
    public Speed(Long SpeedOnXAxis, Long SpeedOnYAxis) {
        this.xLong = SpeedOnXAxis;
        this.yLong = SpeedOnYAxis;
        this.speedOnXAxis = SpeedOnXAxis.doubleValue();
        this.speedOnYAxis = SpeedOnYAxis.doubleValue();
    }

    /**
     * Empty constructor for the database operations.
     */
    public Speed() {
    }

    /**
     * Gets the speed on X-axis.
     *
     * @return Double value of speed on X-axis.
     */
    public double getSpeedOnXAxis() {
        return speedOnXAxis;
    }

    /**
     * Sets the speed on X-axis.
     *
     * @param speedOnXAxis Double value to be set for speed on X-axis.
     */
    public void setSpeedOnXAxis(double speedOnXAxis) {
        this.speedOnXAxis = speedOnXAxis;
    }

    /**
     * Gets the speed on Y-axis.
     *
     * @return Double value of speed on Y-axis.
     */
    public double getSpeedOnYAxis() {
        return speedOnYAxis;
    }

    /**
     * Sets the speed on Y-axis.
     *
     * @param speedOnYAxis Double value to be set for speed on Y-axis.
     */
    public void setSpeedOnYAxis(double speedOnYAxis) {
        this.speedOnYAxis = speedOnYAxis;
    }

    /**
     * Updates the speed according to the given value.
     * <p>
     * REQUIRES: speed != null.
     * MODIFIES: None.
     * EFFECTS: Multiplies the current speed with given speed and adds the x and y values of speeds.
     *
     * @param speed Speed to be dotted.
     * @return Result of the dot operation.
     */
    public double dot(Speed speed) {
        return speed.getSpeedOnXAxis() * this.getSpeedOnXAxis() + speed.getSpeedOnYAxis() * this.getSpeedOnYAxis();
    }

    /**
     * Subtracts the given speed value from current speed value.
     * <p>
     * REQUIRES: speed != null.
     * MODIFIES: None.
     * EFFECTS: subtracts the current speed from the given speed.
     *
     * @param speed Speed to be subtracted.
     * @return Result of the subtract operation.
     */
    public Speed subtract(Speed speed) {
        return new Speed(
                Double.valueOf(this.getSpeedOnXAxis() - speed.getSpeedOnXAxis()).longValue(),
                Double.valueOf(this.getSpeedOnYAxis() - speed.getSpeedOnYAxis()).longValue());
    }

    /**
     * Scales the current speed with the given.
     * <p>
     * REQUIRES: speed != null && factor != null.
     * MODIFIES: None.
     * EFFECTS: scales the current speed with the given value.
     *
     * @param factor Double value to be applied to the Speed.
     * @return Scaled Speed value.
     */
    public Speed scale(double factor) {
        return new Speed(Double.valueOf(factor * this.getSpeedOnXAxis()).longValue(), Double.valueOf(factor * this.getSpeedOnYAxis()).longValue());
    }

    /**
     * Checks if the speed values are valid.
     * <p>
     * REQUIRES: this.
     * MODIFIES: None.
     * EFFECTS: returns true if there is no underflow or overflow and object is not null.
     *
     * @return Boolean indicates that if the representation is valid.
     */
    public boolean repOk() {
        if (xLong > Double.MAX_VALUE || xLong < Double.MAX_VALUE * -1) return false;
        return !(yLong > Double.MAX_VALUE) && !(yLong < Double.MAX_VALUE * -1);
    }

    /**
     * Halves the current speed.
     * <p>
     * REQUIRES: speed != null.
     * MODIFIES: speedOnXAxis, speedOnYAxis.
     * EFFECTS: halves the current speed.
     */
    public void halveSpeed() {
        setSpeedOnXAxis(getSpeedOnXAxis() / 2);
        setSpeedOnYAxis(getSpeedOnYAxis() / 2);

    }

    /**
     * Doubles the current speed.
     * <p>
     * REQUIRES: speed != null.
     * MODIFIES: speedOnXAxis, speedOnYAxis.
     * EFFECTS: doubles the current speed.
     */
    public void doubleSpeed() {
        setSpeedOnXAxis(getSpeedOnXAxis() * 2);
        setSpeedOnYAxis(getSpeedOnYAxis() * 2);
    }
}

