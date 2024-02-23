package net.zepalesque.redux.entity.ai.goal;

import com.aetherteam.aether.entity.monster.Cockatrice;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraftforge.common.util.LazyOptional;
import net.zepalesque.redux.capability.cockatrice.CockatriceExtension;
import net.zepalesque.redux.entity.ai.SourceCodeDoesNotMatchBytecode;


public class CockatriceMeleeAttackGoal extends SourceCodeDoesNotMatchBytecode {

    public CockatriceMeleeAttackGoal(Cockatrice pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !this.isShooting();
    }

    protected boolean isShooting()
    {
        LazyOptional<CockatriceExtension> capability = CockatriceExtension.get((Cockatrice) this.mob);
        return capability.isPresent() && capability.orElseThrow(() -> new IllegalStateException("Cockatrice capability was missing!")).isShooting();
    }


    @Override
    public boolean canUse() {
        return  super.canUse() && !this.isShooting();
    }

}
