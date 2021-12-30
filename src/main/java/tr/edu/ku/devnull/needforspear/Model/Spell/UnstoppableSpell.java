package tr.edu.ku.devnull.needforspear.Model.Spell;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Size;
import tr.edu.ku.devnull.needforspear.Model.UIModels.Sphere;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;

/**
 * UnstoppableSpell is a spell type which extends from Spell class.
 */
public class UnstoppableSpell extends Spell {

    /**
     * Constructor of the UnstoppableSpell.
     *
     * @param size Size of the spell in the game map.
     */
    public UnstoppableSpell(Size size, Location location) {
        super(size,location);
        this.spellColor = Constants.UIConstants.DARK_YELLOW_COLOR_STRING;
        this.spellType = Constants.SpellNameConstants.UNSTOPPABLE;
    }

    /**
     * Triggers an effect of the spell.
     *
     * @return Spell which their effect is triggered.
     */
    @Override
    public void triggerEffect() {
        super.triggerEffect();
        NeedforSpearGame.getInstance().getGameInfo().getSphere().activateUnstoppable();
    }

    @Override
    public Size getSize() {
        return super.getSize();
    }

    @Override
    public void setSize(Size size) {
        super.setSize(size);
    }

    @Override
    public Location getLocation() {
        return super.getLocation();
    }

    @Override
    public void setLocation(Location location) {
        super.setLocation(location);
    }

    @Override
    public String getSpellColor() {
        return super.getSpellColor();
    }

    @Override
    public void setSpellColor(String spellColor) {
        super.setSpellColor(spellColor);
    }

    @Override
    public String getSpellType() {
        return super.getSpellType();
    }

    @Override
    public void setSpellType(String spellType) {
        super.setSpellType(spellType);
    }
}
