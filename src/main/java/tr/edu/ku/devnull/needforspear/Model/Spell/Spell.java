package tr.edu.ku.devnull.needforspear.Model.Spell;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Size;

import java.awt.*;

/**
 * Spell is a parent class of all the spell types.
 */
public class Spell {
    protected Size size;
    protected Location location;
    protected String spellColor;
    protected String spellType;

    /**
     * Constructor of the Spell.
     *
     * @param size Size of the spell in the game map.
     */
    public Spell(Size size, Location location) {
        this.size = size;
        this.location = location;
    }

    public Spell() {
    }

    public Spell(Size size, Location location, String spellColor, String spellType) {
        this.size = size;
        this.location = location;
        this.spellColor = spellColor;
        this.spellType = spellType;
    }

    /**
     * Triggers an effect of the spell.
     *
     * @return Spell which their effect is triggered.
     */
    public void triggerEffect() {
        System.out.println(getSpellType() + " has been triggered!");
    }

    /**
     * Gets size of the spell.
     *
     * @return Size of the spell.
     */
    public Size getSize() {
        return size;
    }

    /**
     * Sets size of the spell.
     *
     * @param size Size of the spell to be set.
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Gets location of the spell.
     *
     * @return Location of the spell.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Sets location of the spell.
     *
     * @param location Location to be set.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Gets spell color.
     *
     * @return String of spell color.
     */
    public String getSpellColor() {
        return spellColor;
    }

    /**
     * Sets the spell color.
     *
     * @param spellColor Spell color string to be set
     */
    public void setSpellColor(String spellColor) {
        this.spellColor = spellColor;
    }

    /**
     * Gets the spell type.
     *
     * @return Spell type String.
     */
    public String getSpellType() {
        return spellType;
    }

    /**
     * Sets the spell type.
     *
     * @param spellType Spell type String to be set.
     */
    public void setSpellType(String spellType) {
        this.spellType = spellType;
    }


    /**
     * Gets the color of the spell.
     *
     * @return Color that indicates the color of the spell from given color string.
     */
    public Color getColorOfSpell(Spell spell) {
        Color color;
        switch (spell.getSpellColor()) {
            case Constants.UIConstants.DARK_GREEN_COLOR_STRING:
                color = Constants.UIConstants.GREEN_SPELL;
                break;
            case Constants.UIConstants.DARK_CYAN_COLOR_STRING:
                color = Constants.UIConstants.CYAN_SPELL;
                break;
            case Constants.UIConstants.DARK_YELLOW_COLOR_STRING:
                color = Constants.UIConstants.YELLOW_SPELL;
                break;
            case Constants.UIConstants.PINK_COLOR_STRING:
                color = Constants.UIConstants.PINK_SPELL;
                break;
            default:
                color = null;
        }
        return color;
    }
}
