package tr.edu.ku.devnull.needforspear.Viewmodel.GameLogicHandlers;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.Player.Player;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;
import tr.edu.ku.devnull.needforspear.View.PlayViews.GameView;

import java.util.concurrent.TimeUnit;

/**
 * PlayerScoreHandler is a class that contains information regarding the player
 * and their score and updates it accordingly.
 */
public class PlayerScoreHandler {
    private static int score= Constants.UIConstants.INIT_SCORE;
    //0 if new map is created, 1 if a map is loaded from db
    private static int newMapCreated = 0;

    /**
     * Constructor for PlayerScoreHandler.

     */
    public PlayerScoreHandler() {}

    /**
     * Updates the score of player with the equation
     * NewScore = OldScore + 300/(CurrentTime-GameStartingTime)
     *
     * @param player Player whose score will be updated
     */
    public static void updateScore(Player player) {
     long currSec= TimeUnit.MILLISECONDS.toSeconds(NeedforSpearGame.getCurrentMillis());
     long startSec= TimeUnit.MILLISECONDS.toSeconds(NeedforSpearGame.getStartMillis());
     long division = currSec-startSec;
     if(division != 0){
         score+= 300/division;
     }
        System.out.println(SaveLoadHandler.getInstance().getPreviousScore());
     player.setScore(SaveLoadHandler.getInstance().getPreviousScore() * newMapCreated + score);
     GameView.updatePlayerScore(player.getScore());
    }

    public static void setScore(int score) {
        PlayerScoreHandler.score = score;
    }


    public static void setNewMapCreated(int newMapCreated) {
        PlayerScoreHandler.newMapCreated = newMapCreated;
    }
}
