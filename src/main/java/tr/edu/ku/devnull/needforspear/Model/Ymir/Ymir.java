package tr.edu.ku.devnull.needforspear.Model.Ymir;

import tr.edu.ku.devnull.needforspear.Model.Spell.YmirSpells.DoubleAccelSpell;
import tr.edu.ku.devnull.needforspear.Model.Spell.YmirSpells.HollowPurpleSpell;
import tr.edu.ku.devnull.needforspear.Model.Spell.YmirSpells.InfiniteVoidSpell;
import tr.edu.ku.devnull.needforspear.Model.Spell.YmirSpells.YmirPower;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Ymir extends Thread {

    Random r = new Random();
    YmirPower currentPower;

    @Override
    public void run() {
        System.out.println("Ymir is calculating!");

        if(r.nextFloat() <= 0.5) return;
        currentPower = determineRandomSpell(ThreadLocalRandom.current().nextInt(0, 3));

        if(currentPower == null) {
            System.err.println("EXCEPTION: YMIR Spell Method returned as a null.");
            return;
        }

        currentPower.triggerYmirEffect();
    }

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
            default: return null;
        }
    }
}
