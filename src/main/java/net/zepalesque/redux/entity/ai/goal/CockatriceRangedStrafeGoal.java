package net.zepalesque.redux.entity.ai.goal;

import com.aetherteam.aether.entity.monster.Cockatrice;
import net.minecraftforge.common.util.LazyOptional;
import net.zepalesque.redux.capability.cockatrice.ReduxCockatrice;

public class CockatriceRangedStrafeGoal extends RangedStrafeAttackGoal<Cockatrice> {

   public CockatriceRangedStrafeGoal(Cockatrice pMob, double pSpeedModifier, int pAttackIntervalMin, int pAttackIntervalMax, float pAttackRadius) {
      super(pMob, pSpeedModifier, pAttackIntervalMin, pAttackIntervalMax, pAttackRadius);
   }

   public CockatriceRangedStrafeGoal(Cockatrice pRangedAttackMob, double pSpeedModifier, int pAttackInterval, float pAttackRadius) {
      super(pRangedAttackMob, pSpeedModifier, pAttackInterval, pAttackRadius);
   }

   @Override
   public boolean canUse() {
      return super.canUse() && this.isShooting();
   }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.isShooting();
    }

    protected boolean isShooting()
    {
        LazyOptional<ReduxCockatrice> capability = ReduxCockatrice.get(this.mob);
        return !capability.isPresent() || capability.orElseThrow(() -> new IllegalStateException("Cockatrice capability was missing!")).isShooting();
    }

}
