package tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers;

import tr.edu.ku.devnull.needforspear.Model.Database.DatabaseCredentials;
import tr.edu.ku.devnull.needforspear.Model.Database.DatabaseSaveLoadSubscriber;
import tr.edu.ku.devnull.needforspear.Model.Database.GameDatabase;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMap;
import tr.edu.ku.devnull.needforspear.Model.Player.Player;
import tr.edu.ku.devnull.needforspear.View.PlayViews.GamePanel;
import tr.edu.ku.devnull.needforspear.View.PlayViews.GameView;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.PlayViews.MainMenuView;
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
        NeedforSpearGame.getInstance().getGameData().getGameDatabase().subscribeToLoadSave(this);
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
        NeedforSpearGame.getInstance().getGameData().getGameDatabase().loadGame(player);
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
                    NeedforSpearGame.getInstance().getGameData().setGameMap(gameMap);
                    NeedforSpearGame.getInstance().getGameData().getMainFrame().getContentPane().removeAll();
                    NeedforSpearGame.getInstance().getGameData().getMainFrame().repaint();
                    NeedforSpearGame.getInstance().startGameView();
                    BuildModeHandler.getInstance().setObstacleList(NeedforSpearGame.getInstance().getGameData().getGameMap().getListofObstacles());
                    NeedforSpearGame.getInstance().getViewData().getGameView().adjustOverlayPanelForBuildingMode();
                    NeedforSpearGame.getInstance().getViewData().getGameView().loadAMap();
                    NeedforSpearGame.getInstance().setGameLoaded(true);
                } else {
                    JOptionPane.showMessageDialog(NeedforSpearGame.getInstance().getGameData().getMainFrame(), "You have lost in previous game", "Alert", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
}
