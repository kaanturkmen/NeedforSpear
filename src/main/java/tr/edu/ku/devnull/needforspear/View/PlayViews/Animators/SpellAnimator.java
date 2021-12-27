package tr.edu.ku.devnull.needforspear.View.PlayViews.Animators;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
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
    private List<Obstacle> listOfGiftObstacles;
    public static List<Spell> listOfMovingSpells;
    private MovementHandler bounceHandler = new MovementHandler();

    public SpellAnimator(){
        listOfMovingSpells = new ArrayList<>();
        this.listOfGiftObstacles = SpellHandler.getInstance().getGiftObstacleList();
    }
    /**
     * Override on AnimatorStrategy to draw spell graphics
     * reinvoked in GamePanel
     *
     * @param g
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
     * @param g2
     */
    public void movementOfSpells(Graphics2D g2) {
        if (NeedforSpearGame.getGameMode() != GameMode.BUILDING_MODE) {
            for (int i = 0; i < listOfMovingSpells.size(); i++) {
                Spell spell = listOfMovingSpells.get(i);
                if (spell!=null) {
                    int width = spell.getSize().getWidth();
                    int length = spell.getSize().getLength();
                    g2.setColor(getColorOfSpell(spell));
                    Location newLoc = bounceHandler.moveSpellDownward(spell);
                    spell.setLocation(newLoc);
                    g2.fillRect(newLoc.getXCoordinates().intValue(), newLoc.getYCoordinates().intValue(), width, length);
                }
            }
        }
    }
    /**
     * @return Color that indicates the color of the spell from given color string.
     */
    public Color getColorOfSpell(Spell spell){
        Color color;
        switch (spell.getSpellColor()) {
            case Constants.UIConstants.DARK_GREEN_COLOR_STRING:
                color = Color.GREEN.darker().darker();
                break;
            case Constants.UIConstants.DARK_CYAN_COLOR_STRING:
                color = Color.CYAN.darker();
                break;
            case Constants.UIConstants.DARK_YELLOW_COLOR_STRING:
                color = Color.YELLOW.darker();
                break;
            case Constants.UIConstants.PINK_COLOR_STRING:
                color = Color.PINK;
                break;
            default:
                color = null;
        }
        return color;
    }

}
