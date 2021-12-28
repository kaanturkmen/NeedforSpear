package tr.edu.ku.devnull.needforspear.Model.GameData;

import tr.edu.ku.devnull.needforspear.Model.UIModels.Sphere;

/**
 * OVERVIEW: Speed is a class which contains the value of speed on the x and y
 * axis of an object.
 */

// This method shows how to create tests for the Class. Thus, we are not creating Handler for it.

// The abstraction function is
// AF(x) = {x.speedOnXAxis & x.speedOnYAxis | Double.MIN_VALUE <= x.speedOnXAxis, x.speedOnYAxis <= Double.MAX_VALUE}

// The rep invariant is
// x != null && Double.MIN_VALUE <= x.speedOnXAxis, x.speedOnYAxis <= Double.MAX_VALUE &&
// x.speedOnXAxis & x.speedOnYAxis should be Double

public class Speed {
    private Double speedOnXAxis;
    private Double speedOnYAxis;
    private Long xLong;
    private Long yLong;

    /**
     * Constructor for Speed.
     *
     * @param SpeedOnXAxis Integer value of speed on X-axis.
     * @param SpeedOnYAxis Integer value of speed on Y-axis
     */

    public Speed(int SpeedOnXAxis, int SpeedOnYAxis) {
        this.speedOnXAxis = (double) SpeedOnXAxis;
        this.speedOnYAxis = (double) SpeedOnYAxis;
    }

    /**
     * Constructor for Speed.
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
     * Constructor for Speed.
     */
    public Speed() {
    }

    /**
     * Gets the speed on X-axis.
     *
     * @return Double value of speed on X-axis.
     */
    public Double getSpeedOnXAxis() {
        return speedOnXAxis;
    }

    /**
     * Sets the speed on X-axis.
     *
     * @param speedOnXAxis Double value to be set for speed on X-axis.
     */
    public void setSpeedOnXAxis(Double speedOnXAxis) {
        this.speedOnXAxis = speedOnXAxis;
    }

    /**
     * Gets the speed on Y-axis.
     *
     * @return Double value of speed on Y-axis.
     */
    public Double getSpeedOnYAxis() {
        return speedOnYAxis;
    }

    /**
     * Sets the speed on Y-axis.
     *
     * @param speedOnYAxis Double value to be set for speed on Y-axis.
     */
    public void setSpeedOnYAxis(Double speedOnYAxis) {
        this.speedOnYAxis = speedOnYAxis;
    }

    /**
     * Updates the speed according to the given value
     * @param speed
     * @return
     */
    public double dot(Speed speed) {
        /*
         * REQUIRES: speed != null
         * MODIFIES: none
         * EFFECTS: Multiplies the current speed with given speed and adds the x and y values of speeds
         */
        return speed.getSpeedOnXAxis() * this.getSpeedOnXAxis()+ speed.getSpeedOnYAxis() * this.getSpeedOnYAxis();
    }

    /**
     * Substracts the given speed value from current speed value
     * @param speed
     * @return Speed
     */
    public Speed subtract(Speed speed) {
        /*
         * REQUIRES: speed != null
         * MODIFIES: none
         * EFFECTS: subtracts the current speed from the given speed
         */
        return new Speed(
                new Double(this.getSpeedOnXAxis() - speed.getSpeedOnXAxis()).longValue(),
                new Double(this.getSpeedOnYAxis() - speed.getSpeedOnYAxis()).longValue());

    }

    /**
     * Scales the current speed with the given
     * @param factor
     * @return Speed
     */
    public Speed scale(double factor){
        /*
         * REQUIRES: speed != null && factor != null
         * MODIFIES: none
         * EFFECTS: scales the current speed with the given value
         */
        return new Speed(new Double(factor * this.getSpeedOnXAxis()).longValue(), new Double(factor*this.getSpeedOnYAxis()).longValue());
    }

    /**
     * Checks if the speed values are valid
     * @return boolean
     */
    public boolean repOk(){
        /*
         * REQUIRES: this
         * MODIFIES: none
         * EFFECTS: returns true if there is no underflow or overflow and object is not null
         */
        if(this.speedOnXAxis == null || this.speedOnYAxis == null) return false;
        if(xLong > Double.MAX_VALUE || xLong < Double.MAX_VALUE * -1) return false;
        if(yLong > Double.MAX_VALUE || yLong < Double.MAX_VALUE * -1) return false;
        return true;
    }

    /**
     * Halves the current speed
     */
    public void halveSpeed(){
        /*
         * REQUIRES: speed != null
         * MODIFIES: speedOnXAxis, speedOnYAxis
         * EFFECTS: halves the current speed
         */
        setSpeedOnXAxis(getSpeedOnXAxis()/2);
        setSpeedOnYAxis(getSpeedOnYAxis()/2);

    }

    /**
     * Doubles the current speed
     */
    public void doubleSpeed(){
         /*
         * REQUIRES: speed != null
         * MODIFIES: speedOnXAxis, speedOnYAxis
         * EFFECTS: doubles the current speed
         */
        setSpeedOnXAxis(getSpeedOnXAxis()*2);
        setSpeedOnYAxis(getSpeedOnYAxis()*2);
    }
}

