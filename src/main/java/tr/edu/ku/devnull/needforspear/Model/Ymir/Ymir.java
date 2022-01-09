package tr.edu.ku.devnull.needforspear.Model.Ymir;

import tr.edu.ku.devnull.needforspear.Model.GameData.Constants;
import tr.edu.ku.devnull.needforspear.Model.GameData.Difficulty;
import tr.edu.ku.devnull.needforspear.Model.Spell.YmirSpells.DoubleAccelSpell;
import tr.edu.ku.devnull.needforspear.Model.Spell.YmirSpells.HollowPurpleSpell;
import tr.edu.ku.devnull.needforspear.Model.Spell.YmirSpells.InfiniteVoidSpell;
import tr.edu.ku.devnull.needforspear.Model.Spell.YmirSpells.YmirPower;
import tr.edu.ku.devnull.needforspear.NeedforSpearGame;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Ymir is a concept that will run while player plays the game.
 * It will create random spells to make player's gameplay experience tough.
 *
 * @author Kaan Turkmen
 */
public class Ymir extends Thread {

    private final Random r = new Random();
    private final double activationProbability;

    /**
     * Constructor of Ymir.
     */
    public Ymir() {
        if (NeedforSpearGame.getInstance().getGameInfo().getDifficultyHandler().getCurrentDifficulty() == Difficulty.NORMAL) {
           // activationProbability = Constants.UIConstants.YMIR_NORMAL_MODE_PROBABILITY;
            activationProbability = 1;
        } else {
            System.out.println(Constants.MessageConstants.YMIR_ACTIVATION_MESSAGE);
           // activationProbability = Constants.UIConstants.YMIR_HARD_MODE_PROBABILITY;
            activationProbability = 1;
        }
    }

    /**
     * Thread method which runs the Ymir's logic.
     */
    @Override
    public void run() {
        System.out.println(Constants.MessageConstants.YMIR_CALCULATION_MESSAGE);

        if (r.nextFloat() > activationProbability) return;
        YmirPower currentPower = determineRandomSpell(ThreadLocalRandom.current().nextInt(0, 3));

        if (currentPower == null) {
            System.err.println(Constants.MessageConstants.YMIR_EXCEPTION_MESSAGE);
            return;
        }

        currentPower.triggerYmirEffect();
    }

    /**
     * Determines the random spell.
     *
     * @param index Index to be selected.
     * @return Random Ymir Power.
     */
    private YmirPower determineRandomSpell(Integer index) {
        switch (index) {
            case 0: {
                System.out.println(Constants.MessageConstants.DOUBLE_ACCEL_TRIGGERED);
                return new DoubleAccelSpell();
            }
            case 1: {
                System.out.println(Constants.MessageConstants.HOLLOW_PURPLE_TRIGGERED);
                //return new HollowPurpleSpell();
                return new DoubleAccelSpell();
            }
            case 2: {
                System.out.println(Constants.MessageConstants.INFINITE_VOID_TRIGGERED);
                //return new InfiniteVoidSpell();
                return new DoubleAccelSpell();
            }
            default:
                return null;
        }
    }
}
