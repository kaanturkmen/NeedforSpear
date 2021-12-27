package tr.edu.ku.devnull.needforspear.Model.Spell.YmirSpells;

import tr.edu.ku.devnull.needforspear.Model.UIModels.Sphere;

public class DoubleAccelSpell implements YmirPower {

    @Override
    public void triggerYmirEffect() {
        Sphere.getInstance().activateDoubleAccel();
        System.out.println("Double Accel is activated.");
    }

}

