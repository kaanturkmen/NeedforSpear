package tr.edu.ku.devnull.needforspear.Model.GameData;

import tr.edu.ku.devnull.needforspear.Model.Player.Player;

/**
 * SavedMapEntry is a class which contains the information
 * of a player and the map they want to save.
 */
public class SavedMapEntry {
    private Player player;
    private GameMap gameMap;

    /**
     * Constructor for SavedMapEntry
     *
     * @param player  Player value of the entry.
     * @param gameMap Map value of the entry.
     */
    public SavedMapEntry(Player player, GameMap gameMap) {
        this.player = player;
        this.gameMap = gameMap;
    }

    /**
     * Constructor for SavedMapEntry
     */
    public SavedMapEntry() {
    }

    /**
     * Gets the player who is trying to save their map.
     *
     * @return Player value of the entry.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the player value of entry.
     *
     * @param player Player value to be saved to the entry.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets the map trying to be saved by the player.
     *
     * @return Map value of the entry.
     */
    public GameMap getGameMap() {
        return gameMap;
    }

    /**
     * Sets the map value of entry.
     *
     * @param gameMap GameMap value to be saved to the entry.
     */
    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }
}
