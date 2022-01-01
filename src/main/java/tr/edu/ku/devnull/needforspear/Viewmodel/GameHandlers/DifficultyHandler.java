package tr.edu.ku.devnull.needforspear.Viewmodel.GameHandlers;

import tr.edu.ku.devnull.needforspear.Model.GameData.Difficulty;

/**
 * DifficultyHandler is a Controller Design Pattern for the handling difficulty.
 *
 * @author Kaan Turkmen
 */
public class DifficultyHandler {
    private Difficulty currentDifficulty;

    /**
     * Default constructor of the DifficultyHandler.
     */
    public DifficultyHandler() {
        this.currentDifficulty = Difficulty.NORMAL;
    }

    /**
     * Methods that changes difficulty of the game.
     */
    public void changeDifficulty() {
        if (currentDifficulty == Difficulty.NORMAL) currentDifficulty = Difficulty.HARD;
        else currentDifficulty = Difficulty.NORMAL;
    }

    /**
     * Gets current difficulty.
     *
     * @return Difficulty of the game.
     */
    public Difficulty getCurrentDifficulty() {
        return currentDifficulty;
    }

    /**
     * Sets current difficulty.
     *
     * @param currentDifficulty Difficulty to be set.
     */
    public void setCurrentDifficulty(Difficulty currentDifficulty) {
        this.currentDifficulty = currentDifficulty;
    }
}
