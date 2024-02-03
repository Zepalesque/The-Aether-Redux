package net.zepalesque.redux.entity.ai.goal;

import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.zepalesque.redux.entity.passive.Mykapod;

public class MykapodPanicGoal extends PanicGoal {

    private final Mykapod mykapod;
    public MykapodPanicGoal(Mykapod mob, double speedModifier) {
        super(mob, speedModifier);
        this.mykapod = mob;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !mykapod.isHiding();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !mykapod.isHiding();
    }
}
