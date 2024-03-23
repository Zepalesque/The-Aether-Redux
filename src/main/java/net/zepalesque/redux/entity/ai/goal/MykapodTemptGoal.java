package net.zepalesque.redux.entity.ai.goal;

import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.crafting.Ingredient;
import net.zepalesque.redux.entity.passive.Mykapod;

public class MykapodTemptGoal extends TemptGoal {

   private final Mykapod mykapod;

   public MykapodTemptGoal(Mykapod mob, double speedModifier, Ingredient items, boolean canScare) {
      super(mob, speedModifier, items, canScare);
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