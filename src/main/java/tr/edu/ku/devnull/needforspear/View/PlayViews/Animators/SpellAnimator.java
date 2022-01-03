package tr.edu.ku.devnull.needforspear.View.PlayViews.Animators;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMode;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.Spell.Spell;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.PlayViews.AnimatorStrategy;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.BackgroundHandler;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.MovementHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for animation of spells using java Swing graphics
 *
 * @author Melis Oktayoğlu
 */
public class SpellAnimator implements AnimatorStrategy {
    private static List<Spell> listOfMovingSpells;
    private final MovementHandler bounceHandler = new MovementHandler();
    private final Image chanceGivingAbility;
    private final Image expansionSpell;
    private final Image hexSpell;
    private final Image unstoppableSpell;

    public SpellAnimator() {
        listOfMovingSpells = new ArrayList<>();
        chanceGivingAbility = new BackgroundHandler().getBackgroundImage(Constants.UIConstants.CHANCE_GIVING_ABILITY);
        expansionSpell = new BackgroundHandler().getBackgroundImage(Constants.UIConstants.EXPANSION_SPELL);
        hexSpell = new BackgroundHandler().getBackgroundImage(Constants.UIConstants.HEX_SPELL);
        unstoppableSpell = new BackgroundHandler().getBackgroundImage(Constants.UIConstants.EXPANSION_SPELL);
    }

    /**
     * Gets the list of spell which is moving.
     *
     * @return List of the spells.
     */
    public static List<Spell> getListOfMovingSpells() {
        return listOfMovingSpells;
    }

    /**
     * Override on AnimatorStrategy to draw spell graphics
     * reinvoked in GamePanel
     *
     * @param g Graphics to be drawn.
     */
    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        movementOfSpells(g2);
    }

    /**
     * For displaying the movement of the spells after their giftObstacle
     * is destroyed
     *
     * @param g2 Graphics2D to be rendered.
     */
    public void movementOfSpells(Graphics2D g2) {
        if (NeedforSpearGame.getInstance().getGameInfo().getGameMode() != GameMode.BUILDING_MODE) {

            // Do not use enhanced for: Since enhanced for uses iterator to iterate, it is not compatible with Java Swing Thread.
            for (int i = 0; i < listOfMovingSpells.size(); i++) {
                Spell spell = listOfMovingSpells.get(i);
                if (spell != null) {
                    int width = spell.getSize().getWidth();
                    int length = spell.getSize().getLength();
                    g2.setColor(spell.getColorOfSpell(spell));
                    Location newLoc = bounceHandler.moveSpellDownward(spell);
                    spell.setLocation(newLoc);
                    if (spell.getSpellType().equals(Constants.SpellNameConstants.CHANCE)) {
                        g2.drawImage(chanceGivingAbility, (int) newLoc.getXCoordinates(), (int) newLoc.getYCoordinates(), width, length, null );
                    }else if (spell.getSpellType().equals(Constants.SpellNameConstants.EXPANSION)) {
                        g2.drawImage(expansionSpell, (int) newLoc.getXCoordinates(), (int) newLoc.getYCoordinates(), width, length, null);
                    }else if (spell.getSpellType().equals(Constants.SpellNameConstants.HEX)) {
                        g2.drawImage(hexSpell, (int) newLoc.getXCoordinates(), (int) newLoc.getYCoordinates(), width, length, null);
                    }else if (spell.getSpellType().equals(Constants.SpellNameConstants.UNSTOPPABLE)) {
                        g2.drawImage(unstoppableSpell, (int) newLoc.getXCoordinates(), (int) newLoc.getYCoordinates(), width, length, null);
                    }
                }
            }
        }
    }
}
