package tr.edu.ku.devnull.needforspear.Model.Spell;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Size;
import tr.edu.ku.devnull.needforspear.Model.UIModels.Bullet;
import tr.edu.ku.devnull.needforspear.View.PlayViews.Animators.BulletAnimator;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.MagicalHexHandler;

/**
 * MagicalHexSpell is a spell type which extends from Spell class.
 */
public class MagicalHexSpell extends Spell {

    /**
     * Constructor of the MagicalHexSpell.
     *
     * @param size Size of the spell in the game map.
     */
    public MagicalHexSpell(Size size, Location location) {
        super(size, location);
        this.spellColor = Constants.UIConstants.DARK_CYAN_COLOR_STRING;
        this.spellType = Constants.SpellNameConstants.HEX;
    }

    /**
     * Triggers an effect of the spell.
     */
    @Override
    public void triggerEffect() {
        super.triggerEffect();
        BulletAnimator.getListOfBullets().add(Bullet.createLeftBullet());
        BulletAnimator.getListOfBullets().add(Bullet.createRightBullet());
        MagicalHexHandler.getInstance().notifySubscribers();
    }

    /**
     * Gets size of the spell.
     *
     * @return Size of the spell.
     */
    @Override
    public Size getSize() {
        return super.getSize();
    }

    /**
     * Sets size of the spell.
     *
     * @param size Size of the spell to be set.
     */
    @Override
    public void setSize(Size size) {
        super.setSize(size);
    }

    /**
     * Gets location of the spell.
     *
     * @return Location of the spell.
     */
    @Override
    public Location getLocation() {
        return super.getLocation();
    }

    /**
     * Sets location of the spell.
     *
     * @param location Location to be set.
     */
    @Override
    public void setLocation(Location location) {
        super.setLocation(location);
    }

    /**
     * Gets spell color.
     *
     * @return String of spell color.
     */
    @Override
    public String getSpellColor() {
        return super.getSpellColor();
    }

    /**
     * Sets the spell color.
     *
     * @param spellColor Spell color string to be set
     */
    @Override
    public void setSpellColor(String spellColor) {
        super.setSpellColor(spellColor);
    }

    /**
     * Gets the spell type.
     *
     * @return Spell type String.
     */
    @Override
    public String getSpellType() {
        return super.getSpellType();
    }

    /**
     * Sets the spell type.
     *
     * @param spellType Spell type String to be set.
     */
    @Override
    public void setSpellType(String spellType) {
        super.setSpellType(spellType);
    }
}
