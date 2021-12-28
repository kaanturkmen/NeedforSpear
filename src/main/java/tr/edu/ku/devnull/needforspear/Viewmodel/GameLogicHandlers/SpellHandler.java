package tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers;


import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.Location;
import tr.edu.ku.devnull.needforspear.Model.GameData.Size;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.GiftObstacle;
import tr.edu.ku.devnull.needforspear.Model.Obstacle.Obstacle;
import tr.edu.ku.devnull.needforspear.Model.Spell.*;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.PlayViews.GameView;
import tr.edu.ku.devnull.needforspear.Viewmodel.AuthHandler.LoginHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * SpellHandler computes the operations related to spells
 * such as assigning them to giftObstacles
 *
 * @author Can Usluel
 */
public class SpellHandler{

    Obstacle currentGiftObstacle;
    private static SpellHandler onlyInstance;
    private Random r = new Random();

    private SpellHandler(){
    }
    /**
     * Singleton design pattern implementation for the creation of SpellHandler.
     *
     * @return new SpellHandler if no previous instances present.
     */
    public static SpellHandler getInstance() {
        if (onlyInstance == null) {
            onlyInstance = new SpellHandler();
        }
        return onlyInstance;
    }
    /**
     *
     * @return List<GiftObstacle> containing only the gift obstacles on the map
     */
    public List<Obstacle> getGiftObstacleList(){
        List<Obstacle> giftObstacleList= new ArrayList<>();
        for(Obstacle obstacle : NeedforSpearGame.getInstance().getGameMap().getListofObstacles()){
            if(obstacle.getObstacleType().equals(Constants.ObstacleNameConstants.GIFT)){
                giftObstacleList.add(obstacle);
            }
        }
        return giftObstacleList;
    }
    /**
     * assigns spells for all giftObstacles on the map
     *
     */
    public void determineGiftObstaclesSpells(){
        //ensuring all the spells will be included
        List<Obstacle> giftObstacleList= getGiftObstacleList();
        currentGiftObstacle = giftObstacleList.get(0);
        System.out.println(currentGiftObstacle);
        currentGiftObstacle.setSpell(SpellFactory.getInstance().getSpell(Constants.SpellNameConstants.CHANCE, currentGiftObstacle));
        currentGiftObstacle = giftObstacleList.get(1);
        currentGiftObstacle.setSpell(SpellFactory.getInstance().getSpell(Constants.SpellNameConstants.HEX, currentGiftObstacle));
        currentGiftObstacle = giftObstacleList.get(2);
        currentGiftObstacle.setSpell(SpellFactory.getInstance().getSpell(Constants.SpellNameConstants.EXPANSION, currentGiftObstacle));
        currentGiftObstacle = giftObstacleList.get(3);
        currentGiftObstacle.setSpell(SpellFactory.getInstance().getSpell(Constants.SpellNameConstants.UNSTOPPABLE, currentGiftObstacle));
        for(int i = 4; i<giftObstacleList.size(); i++){
            currentGiftObstacle = giftObstacleList.get(i);
            currentGiftObstacle.setSpell(setRandomSpell(currentGiftObstacle));
        }
    }
    /**
     *
     * @param giftObstacle
     * @return Spell to be assigned to the given giftObstacle
     */
    public Spell setRandomSpell(Obstacle giftObstacle){
        int randNum = r.nextInt(4);
        Spell spell;
        switch (randNum) {
            case 0:
                spell = SpellFactory.getInstance().getSpell(Constants.SpellNameConstants.CHANCE,giftObstacle);
                break;
            case 1:
                spell = SpellFactory.getInstance().getSpell(Constants.SpellNameConstants.HEX,giftObstacle);
                break;
            case 2:
                spell = SpellFactory.getInstance().getSpell(Constants.SpellNameConstants.EXPANSION,giftObstacle);
                break;
            case 3:
                spell = SpellFactory.getInstance().getSpell(Constants.SpellNameConstants.UNSTOPPABLE,giftObstacle);
                break;
            default:
                spell = null;
        }
        return spell;
    }
    /**
     * gets the available spell of given type
     * @param spellType
     * @return List of spells
     */
    public Spell getAvailableSpell(String spellType){
        if(NeedforSpearGame.getInstance().getPlayer().getListofSpells().size() > 0){
            for(Spell spell: NeedforSpearGame.getInstance().getPlayer().getListofSpells()){
                System.out.println(spell.getSpellType());
                if(spell.getSpellType().equals(spellType)){
                    return spell;
                }
            }
        }
        return null;
    }
    /** Activates the given spell by calling its triggerEffect()
     * @param spell
     */
    public void activateSpell(Spell spell){
        if(spell != null){
            switch (spell.getSpellType()) {
                case Constants.SpellNameConstants.CHANCE:
                    ChanceGivingSpell chanceGivingSpell = new ChanceGivingSpell(new Size(0, 0), new Location(0, 0));
                    chanceGivingSpell.triggerEffect();
                    break;
                case Constants.SpellNameConstants.EXPANSION:
                    ExpansionSpell expansionSpell = new ExpansionSpell(new Size(0, 0), new Location(0, 0));
                    expansionSpell.triggerEffect();
                    break;
                case Constants.SpellNameConstants.HEX:
                    MagicalHexSpell magicalHexSpell = new MagicalHexSpell(new Size(0, 0), new Location(0, 0));
                    magicalHexSpell.triggerEffect();
                    break;
                default:
                    UnstoppableSpell unstoppableSpell = new UnstoppableSpell(new Size(0, 0), new Location(0, 0));
                    unstoppableSpell.triggerEffect();

            }
            NeedforSpearGame.getInstance().getPlayer().getListofSpells().remove(spell);
            NeedforSpearGame.getInstance().getViewData().getGameView().updateSpellNumbers();
        }
    }

    /**
     * returns the number of spells that player have from given type
     * @param spellType
     * @return number of spells that is given spellType
     */
    public int getSpellNumber(String spellType){
        int x = 0;
        for(Spell spell: NeedforSpearGame.getInstance().getPlayer().getListofSpells()){
            if(spell != null) {
                if (spell.getSpellType().equals(spellType)) {
                    x+=1;
               }
            }
        }
        return x;
    }
}
