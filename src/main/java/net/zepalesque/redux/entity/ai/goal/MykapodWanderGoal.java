package net.zepalesque.redux.entity.ai.goal;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.zepalesque.redux.entity.passive.Mykapod;

public class MykapodWanderGoal extends WaterAvoidingRandomStrollGoal {

    private final Mykapod mykapod;
    public MykapodWanderGoal(Mykapod mob, double speedModifier) {
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
