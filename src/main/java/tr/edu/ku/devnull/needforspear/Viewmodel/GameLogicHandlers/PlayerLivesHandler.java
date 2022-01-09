package tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers;

import tr.edu.ku.devnull.needforspear.Model.Player.Player;
import tr.edu.ku.devnull.needforspear.Model.UIModels.NoblePhantasm;
import tr.edu.ku.devnull.needforspear.Model.UIModels.Sphere;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;

import java.util.ArrayList;
import java.util.List;

/**
 * This class does the computations to calculate lives of the player
 * using observer pattern
 *
 * @author Melis Oktayoğlu
 */
public class PlayerLivesHandler {
    private static PlayerLivesHandler onlyInstance;
    //list of players and spheres linked by their indices,
    //preparation for multiple player mode
    private final List<Player> playerList = new ArrayList<>();
    private final List<NoblePhantasm> noblePhantasmList = new ArrayList<>();
    private final List<Sphere> sphereList = new ArrayList<>();

    /**
     * Constructor adds the new player and sphere pair to
     * their corresponding lists
     *
     * @param player        Current player.
     * @param sphere        Sphere of the map.
     * @param noblePhantasm Noble phantasm of the map.
     */
    private PlayerLivesHandler(Player player, Sphere sphere, NoblePhantasm noblePhantasm) {
        playerList.add(player);
        sphereList.add(sphere);
        noblePhantasmList.add(noblePhantasm);
    }

    /**
     * Singleton Design Pattern's getInstance method.
     *
     * @return Single instance of PlayerLivesHandler.
     */
    public static PlayerLivesHandler getInstance() {
        if (onlyInstance == null)
            onlyInstance = new PlayerLivesHandler(NeedforSpearGame.getInstance().getGameInfo().getPlayer(), NeedforSpearGame.getInstance().getGameInfo().getSphere(), NoblePhantasm.getInstance());

        return onlyInstance;
    }


    /**
     * Notify player on sphere's fall
     *
     * @param sphere Sphere data to notify subscribers.
     */
    public void notifyPlayerSphereFall(Sphere sphere) {
        int index = sphereList.indexOf(sphere);
        Player currrentPlayer = playerList.get(index);
        currrentPlayer.decreaseLives();
        NeedforSpearGame.getInstance().getViewData().getGameView().updatePlayerLives(currrentPlayer.getLives());
        //if (currrentPlayer.getLives() <= 0) endGame(currrentPlayer);
    }

    /**
     * Notify player on explosive obstacle.
     *
     * @param noblePhantasm Noble phantasm data to notify subscribers.
     */
    public void notifyPlayerExplosiveFall(NoblePhantasm noblePhantasm) {
        int index = noblePhantasmList.indexOf(noblePhantasm);
        Player currentPlayer = playerList.get(index);
        currentPlayer.decreaseLives();
        NeedforSpearGame.getInstance().getViewData().getGameView().updatePlayerLives(currentPlayer.getLives());
        //if (currentPlayer.getLives() <= 0) endGame(currentPlayer);
    }

    /**
     * Increases player lives by one and updates game view accordingly.
     *
     * @param player Player whose lives will be increased
     */
    public void increasePlayerLives(Player player) {
        player.increaseLives();
        NeedforSpearGame.getInstance().getViewData().getGameView().updatePlayerLives(player.getLives());
    }

    /**
     * Gets players health.
     *
     * @return Integer value of players health.
     */
    public int getPlayerHealth() {
        return playerList.get(0).getLives();
    }

}
