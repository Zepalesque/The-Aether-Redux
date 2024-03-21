package net.zepalesque.redux.entity.ai.goal;

import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.animal.Animal;
import net.zepalesque.redux.entity.passive.Mykapod;

public class MykapodBreedGoal extends BreedGoal {

   private final Mykapod mykapod;

   public MykapodBreedGoal(Mykapod animal, double speedModifier) {
      super(animal, speedModifier);
      this.mykapod = animal;
   }

   public MykapodBreedGoal(Mykapod animal, double speedModifier, Class<? extends Animal> partnerClass) {
      super(animal, speedModifier, partnerClass);
      this.mykapod = animal;
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