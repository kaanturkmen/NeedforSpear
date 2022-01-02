package tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.Player.Player;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;

import java.util.concurrent.TimeUnit;

/**
 * PlayerScoreHandler is a class that contains information regarding the player
 * and their score and updates it accordingly.
 */
public class PlayerScoreHandler {
    private static PlayerScoreHandler onlyInstance;
    private int score = Constants.UIConstants.INIT_SCORE;
    //0 if new map is created, 1 if a map is loaded from db
    private int newMapCreated = 0;

    /**
     * Private Constructor for PlayerScoreHandler.
     */
    private PlayerScoreHandler() {
    }

    /**
     * Singleton Design Pattern's getInstance method.
     *
     * @return Single instance of PlayerScoreHandler.
     */
    public static PlayerScoreHandler getInstance() {
        if (onlyInstance == null) onlyInstance = new PlayerScoreHandler();

        return onlyInstance;
    }

    /**
     * Updates the score of player with the equation
     * NewScore = OldScore + 300/(CurrentTime-GameStartingTime)
     *
     * @param player Player whose score will be updated
     */
    public void updateScore(Player player) {
        long currSec = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        long startSec = TimeUnit.MILLISECONDS.toSeconds(NeedforSpearGame.getInstance().getGameInfo().getStartMillis());
        long division = currSec - startSec;
        if (division != 0) {
            score += 300 / division;
        }
        System.out.println(SaveLoadHandler.getInstance().getPreviousScore());
        player.setScore(SaveLoadHandler.getInstance().getPreviousScore() * newMapCreated + score);
        NeedforSpearGame.getInstance().getViewData().getGameView().updatePlayerScore(player.getScore());
    }

    /**
     * Sets score of the game.
     *
     * @param score Score to be set.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Sets if the new map is created.
     *
     * @param newMapCreated NewMapCreated value to be set.
     */
    public void setNewMapCreated(int newMapCreated) {
        this.newMapCreated = newMapCreated;
    }
}
