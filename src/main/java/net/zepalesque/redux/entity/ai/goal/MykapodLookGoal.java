package net.zepalesque.redux.entity.ai.goal;

import java.util.EnumSet;

import net.minecraft.world.entity.ai.goal.Goal;
import net.zepalesque.redux.entity.passive.Mykapod;

public class MykapodLookGoal extends Goal {
   protected final Mykapod mykapod;
   protected double relX;
   protected double relZ;
   protected int lookTime;

   public MykapodLookGoal(Mykapod mykapod) {
      this.mykapod = mykapod;
      this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
   }

   /**
    * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
    * method as well.
    */
   public boolean canUse() {
      return this.mykapod.getRandom().nextFloat() < 0.02F && !mykapod.isHiding();
   }

   /**
    * Returns whether an in-progress EntityAIBase should continue executing
    */
   public boolean canContinueToUse() {
      return this.lookTime >= 0 && !mykapod.isHiding();
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void start() {
      double d0 = (Math.PI * 2D) * this.mykapod.getRandom().nextDouble();
      this.relX = Math.cos(d0);
      this.relZ = Math.sin(d0);
      this.lookTime = 20 + this.mykapod.getRandom().nextInt(20);
   }

   public boolean requiresUpdateEveryTick() {
      return true;
   }

   /**
    * Keep ticking a continuous task that has already been started
    */
   public void tick() {
      --this.lookTime;
      this.mykapod.getLookControl().setLookAt(this.mykapod.getX() + this.relX, this.mykapod.getEyeY(), this.mykapod.getZ() + this.relZ);
   }
}