package net.zepalesque.redux.entity.ai.goal;

import com.aetherteam.aether.entity.monster.Cockatrice;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.zepalesque.redux.attachment.CockatriceShootingAttachment;

public class CockatriceMeleeAttackGoal extends MeleeAttackGoal {
	
	public CockatriceMeleeAttackGoal(Cockatrice mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
		super(mob, speedModifier, followingTargetEvenIfNotSeen);
	}
	
	@Override
	public boolean canUse() {
		return super.canUse() && !this.isShooting();
	}
	
	@Override
	public boolean canContinueToUse() {
		return super.canContinueToUse() && !this.isShooting();
	}
	
	protected boolean isShooting() {
		var cock = (Cockatrice) this.mob;
		var attach = CockatriceShootingAttachment.get(cock);
		return attach.isShooting();
	}
}
