package tr.edu.ku.devnull.needforspear.Model.GameData;

import tr.edu.ku.devnull.needforspear.Model.Database.GameDatabase;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMap;
import tr.edu.ku.devnull.needforspear.Model.GameData.GameMode;
import tr.edu.ku.devnull.needforspear.Model.Player.Player;

import javax.swing.*;

public class GameInfo {
    private final JFrame mainFrame = new JFrame();
    private GameMode gameMode = GameMode.BUILDING_MODE;
    private Player player;
    private GameMap gameMap;
    private GameDatabase gameDatabase;
    private boolean muteModeActivated = false;

    public GameInfo(GameMode gameMode, Player player, GameMap gameMap, GameDatabase gameDatabase, boolean muteModeActivated) {
        this.gameMode = gameMode;
        this.player = player;
        this.gameMap = gameMap;
        this.gameDatabase = gameDatabase;
        this.muteModeActivated = muteModeActivated;
    }

    public GameInfo() {}

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
}
