package net.zepalesque.redux.entity.ai.goal;

import com.aetherteam.aether.entity.monster.Cockatrice;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraftforge.common.util.LazyOptional;
import net.zepalesque.redux.capability.cockatrice.ReduxCockatrice;


public class CockatriceMeleeAttackGoal extends MeleeAttackGoal {

    public CockatriceMeleeAttackGoal(Cockatrice pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !this.isShooting();
    }

    protected boolean isShooting()
    {
        LazyOptional<ReduxCockatrice> capability = ReduxCockatrice.get((Cockatrice) this.mob);
        return !capability.isPresent() || capability.orElseThrow(() -> new IllegalStateException("Cockatrice capability was missing!")).isShooting();
    }


    @Override
    public boolean canUse() {
        return  super.canUse() && !this.isShooting();
    }

}
