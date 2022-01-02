package tr.edu.ku.devnull.needforspear.View.PlayViews.Animators;

import tr.edu.ku.devnull.needforspear.Model.GameData.GameMode;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.Model.Spell.Spell;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.PlayViews.AnimatorStrategy;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.MovementHandler;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers.SpellHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for animation of spells using java Swing graphics
 *
 * @author Melis Oktayoğlu
 */
public class SpellAnimator implements AnimatorStrategy {
    public static List<Spell> listOfMovingSpells;
    private final List<Obstacle> listOfGiftObstacles;
    private final MovementHandler bounceHandler = new MovementHandler();

    public SpellAnimator() {
        listOfMovingSpells = new ArrayList<>();
        this.listOfGiftObstacles = SpellHandler.getInstance().getGiftObstacleList();
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
            for (Spell spell : listOfMovingSpells) {
                if (spell != null) {
                    int width = spell.getSize().getWidth();
                    int length = spell.getSize().getLength();
                    g2.setColor(spell.getColorOfSpell(spell));
                    Location newLoc = bounceHandler.moveSpellDownward(spell);
                    spell.setLocation(newLoc);
                    g2.fillRect((int) newLoc.getXCoordinates(), (int) newLoc.getYCoordinates(), width, length);
                }
            }
        }
    }
}
