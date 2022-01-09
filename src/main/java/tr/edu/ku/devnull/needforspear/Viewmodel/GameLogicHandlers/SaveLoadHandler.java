package tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMap;
import tr.edu.ku.devnull.needforspear.Model.Player.Player;
import tr.edu.ku.devnull.needforspear.Model.Spell.Spell;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.Viewmodel.Database.DatabaseCredentials;
import tr.edu.ku.devnull.needforspear.Viewmodel.Database.DatabaseSaveLoadSubscriber;
import tr.edu.ku.devnull.needforspear.Viewmodel.State.GameViewState;
import tr.edu.ku.devnull.needforspear.Viewmodel.State.MainMenuViewState;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SaveLoadHandler is Controller Design Pattern
 * to achieve MVVM Design on map loading actions.
 *
 * @author Kaan Turkmen
 */
public class SaveLoadHandler implements DatabaseSaveLoadSubscriber {
    private static SaveLoadHandler onlyInstance = null;
    private int previousScore = 0;
    private int previousLives = 3;
    private List<Spell> previousSpells;

    /**
     * Private Constructor for the SaveLoadHandler.
     */
    private SaveLoadHandler() {
        NeedforSpearGame.getInstance().getGameInfo().getGameDatabase().subscribeToLoadSave(this);
    }

    /**
     * Singleton Design Pattern's getInstance Method for SaveLoadHandler.
     */
    public static synchronized SaveLoadHandler getInstance() {
        if (onlyInstance == null) {
            onlyInstance = new SaveLoadHandler();
        }

        return onlyInstance;
    }

    /**
     * Loads game from the database.
     *
     * @param player Takes valid Player instance to read their map from the database.
     */
    public void loadGame(Player player) {
        NeedforSpearGame.getInstance().getGameInfo().getGameDatabase().loadGame(player);
    }

    /**
     * Saves map to the database.
     *
     * @param player  Take's valid Player instance to assign with their map with that instance while writing to the database.
     * @param gameMap Take's valid GameMap instance to assign with their player with that instance while writing to the database.
     */
    public void saveGame(Player player, GameMap gameMap) {
        NeedforSpearGame.getInstance().getGameInfo().getGameDatabase().saveGame(player, gameMap);
    }

    /**
     * A method for Observer Design Pattern to be called by the database when results are arrived.
     *
     * @param databaseResponse Database response indicating the operation's success or failure.
     * @param gameMap          GameMap response for the player.
     */
    @Override
    public void gameMapResponseArrived(Integer databaseResponse, GameMap gameMap) {
        if (NeedforSpearGame.getInstance().getCurrentState() instanceof GameViewState && !(NeedforSpearGame.getInstance().getCurrentState() instanceof MainMenuViewState)) {
            if (databaseResponse.equals(DatabaseCredentials.DATABASE_SUCCESS)) {
                if (gameMap != null) {
                    if (NeedforSpearGame.getInstance().getGameInfo().getGameMap() != null) {
                        NeedforSpearGame.getInstance().getViewData().getGameView().removeGamePanel();
                    }
                    NeedforSpearGame.getInstance().getGameInfo().setGameMap(gameMap);
                    BuildModeHandler.getInstance().setObstacleList(NeedforSpearGame.getInstance().getGameInfo().getGameMap().getListofObstacles());
                    NeedforSpearGame.getInstance().getGameInfo().setGameLoaded(true);
                    NeedforSpearGame.getInstance().getViewData().getGameView().adjustOverlayPanelForBuildingMode();
                    NeedforSpearGame.getInstance().getViewData().getGameView().loadAMap();
                } else {
                    JOptionPane.showMessageDialog(NeedforSpearGame.getInstance().getGameInfo().getMainFrame(), "You have lost in previous game.", Constants.UIConstants.ALERT_TEXT, JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(NeedforSpearGame.getInstance().getGameInfo().getMainFrame(), "There isn't a previously saved map", Constants.UIConstants.ALERT_TEXT, JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /**
     * Returns the score of player that is saved to database
     *
     * @return int indicating the previous score of player
     */
    public int getPreviousScore() {
        return previousScore;
    }

    /**
     * Sets the previous score of player to the given value
     *
     * @param previousScore Previous score of the player.
     */
    public void setPreviousScore(int previousScore) {
        this.previousScore = previousScore;
    }

    /**
     * Returns the number of lives of player that is saved to database
     *
     * @return Integer indicating the previous number of lives of player
     */
    public int getPreviousLives() {
        return previousLives;
    }

    /**
     * sets the previous lives of player to the given value
     *
     * @param previousLives Previous lives of the player.
     */
    public void setPreviousLives(int previousLives) {
        this.previousLives = previousLives;
    }

    /**
     * sets the previous spells of player to the given value
     *
     * @param previousSpells Previous spells of the player.
     */
    public void setPreviousSpells(List<Spell> previousSpells) {
        this.previousSpells = previousSpells;
    }

    /**
     * creates new instances of spells that are saved in database to copy the spells that player has.
     *
     * @param prevSpellList Previous spell list of the player.
     */
    public void initializePreviousSpells(List<Spell> prevSpellList) {
        if (prevSpellList.size() > 0) {
            previousSpells = new ArrayList<>();
            for (Spell item : prevSpellList) {
                previousSpells.add(new Spell(item.getSize(), item.getLocation(), item.getSpellColor(), item.getSpellType()));
            }
        }
    }

    /**
     * copies previousSpells and returns new list of spells so that original spells
     * that are saved to the database are preserved
     *
     * @return List<Spell> indicating a copy of the spells that are saved in database
     */
    public List<Spell> copyPreviousSpells() {
        List<Spell> copy = new ArrayList<>();
        if (previousSpells != null) {
            for (Spell item : previousSpells) {
                copy.add(new Spell(item.getSize(), item.getLocation(), item.getSpellColor(), item.getSpellType()));
            }
        }
        return copy;
    }
}
