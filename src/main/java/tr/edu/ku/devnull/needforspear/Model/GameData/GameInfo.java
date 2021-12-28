package tr.edu.ku.devnull.needforspear.Model.GameData;

import tr.edu.ku.devnull.needforspear.Viewmodel.Database.GameDatabase;
import tr.edu.ku.devnull.needforspear.Model.Player.Player;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameHandlers.DifficultyHandler;

import javax.swing.*;

public class GameInfo {
    private final JFrame mainFrame = new JFrame();
    private GameMode gameMode = GameMode.BUILDING_MODE;
    private Player player;
    private GameMap gameMap;
    private GameDatabase gameDatabase;
    private boolean muteModeActivated = false;
    private boolean isPaused = true;
    private boolean isGameLoaded = false;
    private long startMillis;
    private DifficultyHandler difficultyHandler;

    public GameInfo(GameMode gameMode, Player player, GameMap gameMap, GameDatabase gameDatabase, boolean muteModeActivated, boolean isPaused, boolean isGameLoaded, long startMillis) {
        difficultyHandler = new DifficultyHandler();
        this.gameMode = gameMode;
        this.player = player;
        this.gameMap = gameMap;
        this.gameDatabase = gameDatabase;
        this.muteModeActivated = muteModeActivated;
        this.isPaused = isPaused;
        this.isGameLoaded = isGameLoaded;
        this.startMillis = startMillis;
    }

    public GameInfo() {
        difficultyHandler = new DifficultyHandler();
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public GameDatabase getGameDatabase() {
        return gameDatabase;
    }

    public void setGameDatabase(GameDatabase gameDatabase) {
        this.gameDatabase = gameDatabase;
    }

    public boolean isMuteModeActivated() {
        return muteModeActivated;
    }

    public void setMuteModeActivated(boolean muteModeActivated) {
        this.muteModeActivated = muteModeActivated;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public boolean isGameLoaded() {
        return isGameLoaded;
    }

    public void setGameLoaded(boolean gameLoaded) {
        isGameLoaded = gameLoaded;
    }

    public long getStartMillis() {
        return startMillis;
    }

    public void setStartMillis(long startMillis) {
        this.startMillis = startMillis;
    }

    /**
     * To get the current time of the game.
     *
     * @return Long value of the current time in milliseconds.
     */
    public long getCurrentMillis() {
        return System.currentTimeMillis();
    }

    public DifficultyHandler getDifficultyHandler() {
        return difficultyHandler;
    }
}
