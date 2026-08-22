package net.zepalesque.redux.entity.ai.goal;

import com.aetherteam.aether.entity.monster.Cockatrice;
import java.util.EnumSet;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;

public class CockatriceMeleeAttackGoal extends Goal {
	protected final Cockatrice cock;
	private final double speedModifier;
	private final boolean followingTargetEvenIfNotSeen;
	private Path path;
	private double pathedTargetX;
	private double pathedTargetY;
	private double pathedTargetZ;
	private int ticksUntilNextPathRecalculation;
	private int ticksUntilNextAttack;
	private long lastCanUseCheck;
	private int failedPathFindingPenalty = 0;
	private boolean canPenalize = false;

	public CockatriceMeleeAttackGoal(
		Cockatrice cock,
		double speedModifier,
		boolean followingTargetEvenIfNotSeen
	) {
		this.cock = cock;
		this.speedModifier = speedModifier;
		this.followingTargetEvenIfNotSeen = followingTargetEvenIfNotSeen;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
	 * method as well.
	 */
	public boolean canUse() {
		var i = this.cock.level().getGameTime();
		if (i - this.lastCanUseCheck < 20L) {
			return false;
		} else {
			this.lastCanUseCheck = i;
			var livingentity = this.cock.getTarget();
			if (livingentity == null) {
				return false;
			} else if (!livingentity.isAlive()) {
				return false;
			} else {
				if (canPenalize) {
					if (--this.ticksUntilNextPathRecalculation <= 0) {
						this.path = this.cock.getNavigation().createPath(livingentity, 0);
						this.ticksUntilNextPathRecalculation =
							4 + this.cock.getRandom().nextInt(7);
						return this.path != null && !this.isShooting();
					} else {
						return !this.isShooting();
					}
				}
				this.path = this.cock.getNavigation().createPath(livingentity, 0);
				if (this.path != null) {
					return !this.isShooting();
				} else {
					return (
						this.getAttackReachSqr(livingentity) >=
							this.cock.distanceToSqr(
								livingentity.getX(),
								livingentity.getY(),
								livingentity.getZ()
							) &&
						!this.isShooting()
					);
				}
			}
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean canContinueToUse() {
		var livingentity = this.cock.getTarget();
		if (livingentity == null) {
			return false;
		} else if (!livingentity.isAlive()) {
			return false;
		} else if (!this.followingTargetEvenIfNotSeen) {
			return !this.cock.getNavigation().isDone();
		} else if (!this.cock.isWithinRestriction(livingentity.blockPosition())) {
			return false;
		} else {
			return (
				(!(livingentity instanceof Player) ||
					(!livingentity.isSpectator() &&
						!((Player) livingentity).isCreative())) &&
				!this.isShooting()
			);
		}
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void start() {
		this.cock.getNavigation().moveTo(this.path, this.speedModifier);
		this.cock.setAggressive(true);
		this.ticksUntilNextPathRecalculation = 0;
		this.ticksUntilNextAttack = 0;
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	public void stop() {
		var livingentity = this.cock.getTarget();
		if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
			this.cock.setTarget((LivingEntity) null);
		}

		this.cock.setAggressive(false);
		this.cock.getNavigation().stop();
	}

	public boolean requiresUpdateEveryTick() {
		return true;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	public void tick() {
		var livingentity = this.cock.getTarget();
		if (livingentity != null) {
			this.cock.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
			double d0 = /* this.cock.getPerceivedTargetDistanceSquareForMeleeAttack(
				livingentity
			); */ 0;

			this.ticksUntilNextPathRecalculation = Math.max(
				this.ticksUntilNextPathRecalculation - 1,
				0
			);
			if (
				(this.followingTargetEvenIfNotSeen ||
					this.cock.getSensing().hasLineOfSight(livingentity)) &&
				this.ticksUntilNextPathRecalculation <= 0 &&
				((this.pathedTargetX == 0.0D &&
					this.pathedTargetY == 0.0D &&
					this.pathedTargetZ == 0.0D) ||
					livingentity.distanceToSqr(
						this.pathedTargetX,
						this.pathedTargetY,
						this.pathedTargetZ
					) >= 1.0D ||
					this.cock.getRandom().nextFloat() < 0.05F)
			) {
				this.pathedTargetX = livingentity.getX();
				this.pathedTargetY = livingentity.getY();
				this.pathedTargetZ = livingentity.getZ();
				this.ticksUntilNextPathRecalculation = 4 + this.cock.getRandom().nextInt(7);
				
				if (this.canPenalize) {
					this.ticksUntilNextPathRecalculation += failedPathFindingPenalty;
					if (this.cock.getNavigation().getPath() != null) {
						var finalPathPoint = this.cock
							.getNavigation()
							.getPath()
							.getEndNode();
						if (
							finalPathPoint != null &&
							livingentity.distanceToSqr(
								finalPathPoint.x,
								finalPathPoint.y,
								finalPathPoint.z
							) < 1
						) failedPathFindingPenalty = 0;
						else failedPathFindingPenalty += 10;
					} else {
						failedPathFindingPenalty += 10;
					}
				}

				if (d0 > 1024.0D) {
					this.ticksUntilNextPathRecalculation += 10;
				} else if (d0 > 256.0D) {
					this.ticksUntilNextPathRecalculation += 5;
				}

				if (!this.cock.getNavigation().moveTo(livingentity, this.speedModifier)) {
					this.ticksUntilNextPathRecalculation += 15;
				}

				this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(
					this.ticksUntilNextPathRecalculation
				);
			}

			this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
			this.checkAndPerformAttack(livingentity, d0);
		}
	}

	protected void checkAndPerformAttack(
		LivingEntity enemy,
		double distToEnemySqr
	) {
		double d0 = this.getAttackReachSqr(enemy);
		if (distToEnemySqr <= d0 && this.ticksUntilNextAttack <= 0) {
			this.resetAttackCooldown();
			this.cock.swing(InteractionHand.MAIN_HAND);
			this.cock.doHurtTarget(enemy);
		}
	}

	protected void resetAttackCooldown() {
		this.ticksUntilNextAttack = this.adjustedTickDelay(20);
	}

	protected boolean isTimeToAttack() {
		return this.ticksUntilNextAttack <= 0;
	}

	protected int getTicksUntilNextAttack() {
		return this.ticksUntilNextAttack;
	}

	protected int getAttackInterval() {
		return this.adjustedTickDelay(20);
	}

	protected double getAttackReachSqr(LivingEntity attackTarget) {
		return (double) (this.cock.getBbWidth() *
			2.0F *
			this.cock.getBbWidth() *
			2.0F +
			attackTarget.getBbWidth());
	}

	protected boolean isShooting() {
		// TODO: implement capability or whatever neoforge changed it to
		return false;
	}
}
