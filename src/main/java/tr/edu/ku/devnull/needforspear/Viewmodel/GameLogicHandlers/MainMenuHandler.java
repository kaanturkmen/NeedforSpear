package tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers;

import tr.edu.ku.devnull.needforspear.Model.Database.DatabaseCredentials;
import tr.edu.ku.devnull.needforspear.Model.Database.DatabaseSaveLoadSubscriber;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMap;
import tr.edu.ku.devnull.needforspear.Model.Player.Player;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.Viewmodel.State.GameViewState;
import tr.edu.ku.devnull.needforspear.Viewmodel.State.MainMenuViewState;

import javax.swing.*;

/**
 * MainMenuHandler is Controller Design Pattern
 * to achieve MVVM Design on authentication actions.
 *
 * @author Kaan Turkmen
 */
public class MainMenuHandler implements DatabaseSaveLoadSubscriber {
    private static MainMenuHandler onlyInstance = null;

    /**
     * Private Constructor for the MainMenuHandler.
     */
    private MainMenuHandler() {
        NeedforSpearGame.getInstance().getGameInfo().getGameDatabase().subscribeToLoadSave(this);
    }

    /**
     * Singleton Design Pattern's getInstance Method for MainMenuHandler.
     */
    public static synchronized MainMenuHandler getInstance() {
        if (onlyInstance == null) {
            onlyInstance = new MainMenuHandler();
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
     * A method for Observer Design Pattern to be called by the database when results are arrived.
     *
     * @param databaseResponse Database response indicating the operation's success or failure.
     * @param gameMap          GameMap instance from the database.
     */
    @Override
    public void gameMapResponseArrived(Integer databaseResponse, GameMap gameMap) {
        if (!(NeedforSpearGame.getInstance().getCurrentState() instanceof GameViewState) && NeedforSpearGame.getInstance().getCurrentState() instanceof MainMenuViewState) {
            if (databaseResponse.equals(DatabaseCredentials.DATABASE_SUCCESS)) {
                if (gameMap != null) {
                    NeedforSpearGame.getInstance().getGameInfo().setGameMap(gameMap);
                    NeedforSpearGame.getInstance().getGameInfo().getMainFrame().getContentPane().removeAll();
                    NeedforSpearGame.getInstance().getGameInfo().getMainFrame().repaint();
                    NeedforSpearGame.getInstance().startGameView();
                    BuildModeHandler.getInstance().setObstacleList(NeedforSpearGame.getInstance().getGameInfo().getGameMap().getListofObstacles());
                    NeedforSpearGame.getInstance().getViewData().getGameView().adjustOverlayPanelForBuildingMode();
                    NeedforSpearGame.getInstance().getViewData().getGameView().loadAMap();
                    NeedforSpearGame.getInstance().getGameInfo().setGameLoaded(true);
                } else {
                    JOptionPane.showMessageDialog(NeedforSpearGame.getInstance().getGameInfo().getMainFrame(), "You have lost in previous game", "Alert", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
}
