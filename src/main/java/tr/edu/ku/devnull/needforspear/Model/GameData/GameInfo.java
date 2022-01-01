package tr.edu.ku.devnull.needforspear.Model.GameData;


import tr.edu.ku.devnull.needforspear.Model.UIModels.Sphere;
import tr.edu.ku.devnull.needforspear.Viewmodel.Database.GameDatabase;
import tr.edu.ku.devnull.needforspear.Model.Player.Player;
import tr.edu.ku.devnull.needforspear.Viewmodel.GameHandlers.DifficultyHandler;

import javax.swing.*;

/**
 * GameInfo is class to apply Information Expert Design Pattern into the game.
 * It is responsible with such logics to change or check the game logic.
 * It is created in a game startup which is NeedforSpearGame.java.
 *
 * @author Kaan Turkmen
 */
public class GameInfo {
    private final JFrame mainFrame = new JFrame();
    private GameMode gameMode = GameMode.BUILDING_MODE;
    private Player player;
    private GameMap gameMap;
    private GameDatabase gameDatabase;
    private boolean muteModeActivated = false, isPaused = true, isGameLoaded = false;
    private long startMillis;
    private final Sphere sphere;
    private final DifficultyHandler difficultyHandler;

    /**
     * Constructor for the GameInfo, only initializing the handler and the sphere.
     * Other properties are created by setters to only create them when needed.
     */
    public GameInfo() {
        difficultyHandler = new DifficultyHandler();
        sphere = new Sphere();
    }

    /**
     * Gets the main frame of the game.
     *
     * @return JFrame to be mainly used.
     */
    public JFrame getMainFrame() {
        return mainFrame;
    }

    /**
     * Gets the game mode of the game.
     *
     * @return GameMode of the game.
     */
    public GameMode getGameMode() {
        return gameMode;
    }

    /**
     * Sets the game mode of the game.
     */
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * Gets the player of the game.
     *
     * @return Player of the current game.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the player of the game.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets the map of the game.
     *
     * @return GameMap to be played on.
     */
    public GameMap getGameMap() {
        return gameMap;
    }

    /**
     * Sets the map of the game.
     */
    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    /**
     * Gets the database of the game.
     *
     * @return GameDatabase to be used for data read and write.
     */
    public GameDatabase getGameDatabase() {
        return gameDatabase;
    }

    /**
     * Sets the database of the game.
     */
    public void setGameDatabase(GameDatabase gameDatabase) {
        this.gameDatabase = gameDatabase;
    }

    /**
     * Gets the mute mode of the game.
     *
     * @return Boolean value of the mute information.
     */
    public boolean isMuteModeActivated() {
        return muteModeActivated;
    }

    /**
     * Sets the mute mode of the game.
     */
    public void setMuteModeActivated(boolean muteModeActivated) {
        this.muteModeActivated = muteModeActivated;
    }

    /**
     * Gets the pause mode of the game.
     *
     * @return Boolean value of pause information.
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * Sets the pause mode of the game.
     */
    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    /**
     * Gets the game loaded mode of the game.
     *
     * @return Boolean value of game loaded information.
     */
    public boolean isGameLoaded() {
        return isGameLoaded;
    }

    /**
     * Sets the game loaded mode of the game.
     */
    public void setGameLoaded(boolean gameLoaded) {
        isGameLoaded = gameLoaded;
    }

    /**
     * Gets the start time in terms of milliseconds of the game.
     *
     * @return Long value of the start time of the game.
     */
    public long getStartMillis() {
        return startMillis;
    }

    /**
     * Gets main frame of the game.
     */
    public void setStartMillis(long startMillis) {
        this.startMillis = startMillis;
    }

    /**
     * Gets the sphere of the game.
     *
     * @return Sphere to be used on the game.
     */
    public Sphere getSphere() {
        return sphere;
    }

    /**
     * Gets the DifficultyHandler of the game.
     *
     * @return DifficultyHandler to be used for the game.
     */
    public DifficultyHandler getDifficultyHandler() {
        return difficultyHandler;
    }
}
