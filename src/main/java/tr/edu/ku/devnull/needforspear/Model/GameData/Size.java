package tr.edu.ku.devnull.needforspear.Model.GameData;

/**
 * Size is a class which contains the dimensions of an object.
 */
public class Size {
    private Integer width;
    private Integer length;

    /**
     * Constructor for Size.
     *
     * @param width  Integer value of width of size.
     * @param length Integer value of length of size.
     */
    public Size(Integer width, Integer length) {
        this.width = width;
        this.length = length;
    }

    /**
     * Constructor for Size
     */
    public Size() {
    }

    /**
     * Gets the width attribute of size.
     *
     * @return Integer value of width.
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * Sets the width of size.
     *
     * @param width Integer value to be set for width of size.
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * Gets the length attribute of size.
     *
     * @return Integer value length.
     */
    public Integer getLength() {
        return length;
    }

    /**
     * Sets the length of size.
     *
     * @param length Integer value to be set for length of size.
     */
    public void setLength(Integer length) {
        this.length = length;
    }
}
