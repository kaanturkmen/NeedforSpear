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

import javax.swing.*;

/**
 * MainMenuHandler is Controller Design Pattern
 * to achieve MVVM Design on authentication actions.
 *
 * @author Kaan Turkmen
 */
public class MainMenuHandler implements DatabaseSaveLoadSubscriber {
    private static MainMenuHandler onlyInstance = null;
    private static GameDatabase gAuth = NeedforSpearGame.getGameDatabase();

    /**
     * Private Constructor for the MainMenuHandler.
     */
    private MainMenuHandler() {
        gAuth.subscribeToLoadSave(this);
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
        gAuth.loadGame(player);
    }

    /**
     * A method for Observer Design Pattern to be called by the database when results are arrived.
     *
     * @param databaseResponse Database response indicating the operation's success or failure.
     * @param gameMap          GameMap instance from the database.
     */
    @Override
    public void gameMapResponseArrived(Integer databaseResponse, GameMap gameMap) {
        if (!GameView.getIsGameViewOpened() && MainMenuView.isGameViewOpened()) {
            if (databaseResponse.equals(DatabaseCredentials.DATABASE_SUCCESS)) {
                if (gameMap != null) {
                    NeedforSpearGame.setGameMap(gameMap);
                    NeedforSpearGame.getMainFrame().getContentPane().removeAll();
                    NeedforSpearGame.getMainFrame().repaint();
                    NeedforSpearGame.startGameView();
                    BuildModeHandler.getInstance().setObstacleList(NeedforSpearGame.getGameMap().getListofObstacles());
                    GameView.adjustOverlayPanelForBuildingMode();
                    GamePanel.setIsGameLoaded(true);
                    GameView.loadAMap();
                } else {
                    JOptionPane.showMessageDialog(NeedforSpearGame.getMainFrame(), "You have lost in previous game", "Alert", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    /**
     * To set a game database to access its methods via database calls.
     *
     * @param gameDatabase Database to be set.
     */
    public void setGameDatabase(GameDatabase gameDatabase) {
        this.gAuth = gameDatabase;
    }
}
