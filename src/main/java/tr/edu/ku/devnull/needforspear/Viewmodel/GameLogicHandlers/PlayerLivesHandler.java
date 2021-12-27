package tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers;

import tr.edu.ku.devnull.needforspear.Model.GameData.GameMode;
import tr.edu.ku.devnull.needforspear.Model.UIModels.NoblePhantasm;
import tr.edu.ku.devnull.needforspear.Model.UIModels.Sphere;
import tr.edu.ku.devnull.needforspear.Model.Player.Player;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.PlayViews.GamePanel;
import tr.edu.ku.devnull.needforspear.View.PlayViews.GameView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.currentThread;

/**
 * This class does the computations to calculate lives of the player
 * using observer pattern
 *
 * @author Melis Oktayoğlu
 */

public class PlayerLivesHandler {
    //list of players and spheres linked by their indices,
    //preparation for multiple player mode
    private List<Player> playerList = new ArrayList<>();
    private List<NoblePhantasm> noblePhantasmList = new ArrayList<>();
    private List<Sphere> sphereList = new ArrayList<>();

    /**
     * Constructor adds the new player and sphere pair to
     * their corresponding lists
     *
     * @param player
     * @param sphere
     */
    public PlayerLivesHandler(Player player, Sphere sphere, NoblePhantasm noblePhantasm) {
        playerList.add(player);
        sphereList.add(sphere);
        noblePhantasmList.add(noblePhantasm);
    }

    /**
     * Notify player on sphere's fall
     *
     * @param sphere
     */
    public void notifyPlayerSphereFall(Sphere sphere) {
        int index = sphereList.indexOf(sphere);
        Player currrentPlayer = playerList.get(index);
        currrentPlayer.decreaseLives();
        GameView.updatePlayerLives(currrentPlayer.getLives());
        //if (currrentPlayer.getLives() <= 0) endGame(currrentPlayer);
    }

    public void notifyPlayerExplosiveFall(NoblePhantasm noblePhantasm) {
        int index = noblePhantasmList.indexOf(noblePhantasm);
        Player currentPlayer = playerList.get(index);
        currentPlayer.decreaseLives();
        GameView.updatePlayerLives(currentPlayer.getLives());
        //if (currentPlayer.getLives() <= 0) endGame(currentPlayer);
    }

    /**
     * Increases player lives by one and updates game view accordingly.
     *
     * @param player Player whose lives will be increased
     */
    public static void increasePlayerLives(Player player) {
        player.increaseLives();
        GameView.updatePlayerLives(player.getLives());
    }

    public int getPlayerHealth(){
        return playerList.get(0).getLives();
    }

    /**
     * Ending the game if lives ended
     *
     * @param player
     */
//    public void endGame(Player player) {
//        JOptionPane.showMessageDialog(NeedforSpearGame.getMainFrame(), "You have lost", "Alert", JOptionPane.WARNING_MESSAGE);
//        player.setLives(3);
//        GamePanel.setIsGameLoaded(false);
//        GamePanel.setIsGameStarted(false);
//        NeedforSpearGame.setGameMode(GameMode.BUILDING_MODE);
//        NeedforSpearGame.setIsPaused(false);
//        NeedforSpearGame.getMainFrame().getContentPane().removeAll();
//        NeedforSpearGame.getMainFrame().repaint();
//        NeedforSpearGame.startMainMenu();
//    }
}
