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
    public void triggerEffect(){
        System.out.println(getSpellType() + " has been triggered!");
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getSpellColor() {
        return spellColor;
    }

    public void setSpellColor(String spellColor) {
        this.spellColor = spellColor;
    }

    public String getSpellType() {
        return spellType;
    }

    public void setSpellType(String spellType) {
        this.spellType = spellType;
    }

}
