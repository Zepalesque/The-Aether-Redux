package net.zepalesque.redux.entity.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.zepalesque.redux.entity.passive.Mykapod;

public class MykapodStareGoal extends LookAtPlayerGoal {

   private final Mykapod mykapod;

   public MykapodStareGoal(Mykapod mykapod, Class<? extends LivingEntity> lookAtType, float lookDistance) {
      super(mykapod, lookAtType, lookDistance);
      this.mykapod = mykapod;
   }

   public MykapodStareGoal(Mykapod mykapod, Class<? extends LivingEntity> lookAtType, float lookDistance, float probability) {
      super(mykapod, lookAtType, lookDistance, probability);
      this.mykapod = mykapod;
   }

   public MykapodStareGoal(Mykapod mykapod, Class<? extends LivingEntity> lookAtType, float lookDistance, float probability, boolean onlyHorizontal) {
      super(mykapod, lookAtType, lookDistance, probability, onlyHorizontal);
      this.mykapod = mykapod;
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