package tr.edu.ku.devnull.needforspear.Viewmodel.GameHandlers;

import tr.edu.ku.devnull.needforspear.Model.GameData.Difficulty;

public class DifficultyHandler {
    private Difficulty currentDifficulty;

    public DifficultyHandler(Difficulty currentDifficulty) {
        this.currentDifficulty = currentDifficulty;
    }

    public DifficultyHandler() {
        this.currentDifficulty = Difficulty.NORMAL;
    }

    public void changeDifficulty() {
        if(currentDifficulty == Difficulty.NORMAL) currentDifficulty = Difficulty.HARD;
        else currentDifficulty = Difficulty.NORMAL;
    }

    public Difficulty getCurrentDifficulty() {
        return currentDifficulty;
    }

    public void setCurrentDifficulty(Difficulty currentDifficulty) {
        this.currentDifficulty = currentDifficulty;
    }
}
