package tr.edu.ku.devnull.needforspear.Model.Spell;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Size;
import tr.edu.ku.devnull.needforspear.Model.UIModels.Bullet;
import tr.edu.ku.devnull.needforspear.Model.UIModels.NoblePhantasm;
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
        super(size,location);
        this.spellColor = Constants.UIConstants.DARK_CYAN_COLOR_STRING;
        this.spellType = Constants.SpellNameConstants.HEX;
    }

    /**
     * Triggers an effect of the spell.
     *
     */
    @Override
    public void triggerEffect() {
        super.triggerEffect();
        BulletAnimator.listOfBullets.add(Bullet.createLeftBullet());
        BulletAnimator.listOfBullets.add(Bullet.createRightBullet());
        MagicalHexHandler.getInstance().notifySubscribers();
    }

}
