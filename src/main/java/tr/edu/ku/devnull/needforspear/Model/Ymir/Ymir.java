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
            activationProbability = Constants.UIConstants.YMIR_NORMAL_MODE_PROBABILITY;
        } else {
            System.out.println("Activation probability is set to 0.75");
            activationProbability = Constants.UIConstants.YMIR_HARD_MODE_PROBABILITY;
        }
    }

    /**
     * Thread method which runs the Ymir's logic.
     */
    @Override
    public void run() {
        System.out.println("Ymir is calculating!");

        if (r.nextFloat() <= activationProbability) return;
        YmirPower currentPower = determineRandomSpell(ThreadLocalRandom.current().nextInt(0, 3));

        if (currentPower == null) {
            System.err.println("EXCEPTION: YMIR Spell Method returned as a null.");
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
                System.out.println("Double Accel is activated by Ymir.");
                return new DoubleAccelSpell();
            }
            case 1: {
                System.out.println("Hollow Purple is activated by Ymir.");
                return new HollowPurpleSpell();
            }
            case 2: {
                System.out.println("Infinite Void is activated by Ymir.");
                return new InfiniteVoidSpell();
            }
            default:
                return null;
        }
    }
}
