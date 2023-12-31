package net.zepalesque.redux.entity.ai.goal;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.RangedAttackMob;

import java.util.EnumSet;

public class RangedStrafeAttackGoal<T extends net.minecraft.world.entity.Mob & RangedAttackMob> extends Goal {
   protected final T mob;
   protected final double speedModifier;
   protected int attackIntervalMin;
   protected final float attackRadius;
   protected final float attackRadiusSqr;
   protected int attackTime = -1;
   protected LivingEntity target;
   protected int seeTime;
   protected boolean strafingClockwise;
   protected boolean strafingBackwards;
   protected int strafingTime = -1;
   protected final int attackIntervalMax;

   public RangedStrafeAttackGoal(T pMob, double pSpeedModifier, int pAttackIntervalMin, int pAttackIntervalMax, float pAttackRadius) {
      this.mob = pMob;
      this.speedModifier = pSpeedModifier;
      this.attackIntervalMin = pAttackIntervalMin;
      this.attackIntervalMax = pAttackIntervalMax;
      this.attackRadius = pAttackRadius;
      this.attackRadiusSqr = pAttackRadius * pAttackRadius;
      this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
   }
   public RangedStrafeAttackGoal(T pRangedAttackMob, double pSpeedModifier, int pAttackInterval, float pAttackRadius) {
      this(pRangedAttackMob, pSpeedModifier, pAttackInterval, pAttackInterval, pAttackRadius);
   }

   public void setMinAttackInterval(int pAttackCooldown) {
      this.attackIntervalMin = pAttackCooldown;
   }

   /**
    * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
    * method as well.
    */
   public boolean canUse() {
      LivingEntity livingentity = this.mob.getTarget();
      if (livingentity != null && livingentity.isAlive()) {
         this.target = livingentity;
         return true;
      } else {
         return false;
      }
   }

   /**
    * Returns whether an in-progress EntityAIBase should continue executing
    */
   public boolean canContinueToUse() {
      return this.canUse() || (this.target != null && this.target.isAlive()) && !this.mob.getNavigation().isDone();
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void start() {
      super.start();
      this.mob.setAggressive(true);
   }

   /**
    * Reset the task's internal state. Called when this task is interrupted by another one
    */
   public void stop() {
      this.target = null;
      this.seeTime = 0;
      this.attackTime = -1;
   }

   public boolean requiresUpdateEveryTick() {
      return true;
   }

   /**
    * Keep ticking a continuous task that has already been started
    */
   public void tick() {
      LivingEntity livingentity = this.mob.getTarget();
      if (livingentity != null) {
         double d0 = this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
         boolean flag = this.mob.getSensing().hasLineOfSight(livingentity);
         boolean flag1 = this.seeTime > 0;
         if (flag != flag1) {
            this.seeTime = 0;
         }

         if (flag) {
            ++this.seeTime;
         } else {
            --this.seeTime;
         }

         if (!(d0 > (double)this.attackRadiusSqr) && this.seeTime >= 20) {
            this.mob.getNavigation().stop();
            ++this.strafingTime;
         } else {
            this.mob.getNavigation().moveTo(livingentity, this.speedModifier);
            this.strafingTime = -1;
         }

         if (this.strafingTime >= 20) {
            if ((double)this.mob.getRandom().nextFloat() < 0.3D) {
               this.strafingClockwise = !this.strafingClockwise;
            }

            if ((double)this.mob.getRandom().nextFloat() < 0.3D) {
               this.strafingBackwards = !this.strafingBackwards;
            }

            this.strafingTime = 0;
         }

         if (this.strafingTime > -1) {
            if (d0 > (double)(this.attackRadiusSqr * 0.75F)) {
               this.strafingBackwards = false;
            } else if (d0 < (double)(this.attackRadiusSqr * 0.25F)) {
               this.strafingBackwards = true;
            }

            this.mob.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
            this.mob.lookAt(livingentity, 30.0F, 30.0F);
         } else {
            this.mob.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
         }

         if (--this.attackTime == 0) {
            if (!flag) {
               return;
            }

            float f = (float)Math.sqrt(d0) / this.attackRadius;
            float f1 = Mth.clamp(f, 0.1F, 1.0F);
            this.mob.performRangedAttack(this.target, f1);
            this.attackTime = Mth.floor(f * (float)(this.attackIntervalMax - this.attackIntervalMin) + (float)this.attackIntervalMin);
         } else if (this.attackTime < 0) {
            this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0) / (double)this.attackRadius, this.attackIntervalMin, this.attackIntervalMax));
         }

      }
   }
}
