package tr.edu.ku.devnull.needforspear.Model.GameData;

/**
 * Size is a class which contains the dimensions of an object.
 */
public class Size {
    private int width;
    private int length;

    /**
     * Constructor for Size.
     *
     * @param width  Integer value of width of size.
     * @param length Integer value of length of size.
     */
    public Size(int width, int length) {
        this.width = width;
        this.length = length;
    }

    /**
     * Empty constructor for database operations.
     */
    public Size() {
    }

    /**
     * Gets the width attribute of size.
     *
     * @return Integer value of width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width of size.
     *
     * @param width Integer value to be set for width of size.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets the length attribute of size.
     *
     * @return Integer value length.
     */
    public int getLength() {
        return length;
    }
}
