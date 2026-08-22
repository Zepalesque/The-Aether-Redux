package net.zepalesque.redux.entity.ai.goal;

import com.aetherteam.aether.entity.monster.Cockatrice;

public class CockatriceRangedStrafeGoal
	extends RangedStrafeAttackGoal<Cockatrice>
{
	public CockatriceRangedStrafeGoal(
		Cockatrice cock,
		double speedModifier,
		int intervalMin,
		int intervalMax,
		float attackRadius
	) {
		super(
			cock,
			speedModifier,
			intervalMin,
			intervalMax,
			attackRadius
		);
	}

	public CockatriceRangedStrafeGoal(
		Cockatrice cock,
		double speedModifier,
		int attackInterval,
		float attackRadius
	) {
		super(cock, speedModifier, attackInterval, attackRadius);
	}

	@Override
	public boolean canUse() {
		return super.canUse() && this.isShooting();
	}

	@Override
	public boolean canContinueToUse() {
		return super.canContinueToUse() && this.isShooting();
	}

	protected boolean isShooting() {
		// TODO: implement capability or whatever neoforge changed it to
		return false;
	}
}
