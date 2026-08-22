package net.zepalesque.redux.event.hook;

import com.aetherteam.aether.entity.monster.Cockatrice;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.entity.ai.goal.CockatriceMeleeAttackGoal;
import net.zepalesque.redux.entity.ai.goal.CockatriceRangedStrafeGoal;
import net.zepalesque.redux.entity.ai.target.HurtByOtherTypeTargetGoal;

import java.util.Objects;
import java.util.Optional;

public class MobHooks {

	public static void modifyCockatriceAI(Cockatrice cockatrice) {
		if (ReduxConfig.SERVER.cockatrice_burn_in_daylight.get()) {
			cockatrice.goalSelector.addGoal(2, new RestrictSunGoal(cockatrice));
			cockatrice.goalSelector.addGoal(3, new FleeSunGoal(cockatrice, 1.0D));
		}
		cockatrice.goalSelector.addGoal(
			1,
			new CockatriceMeleeAttackGoal(cockatrice, 1.5, false)
		);
		final var ref = new GoalsRef();
		
		cockatrice.targetSelector.getAvailableGoals().forEach(goal -> {
			if (
				goal.getGoal().getClass().equals(HurtByTargetGoal.class) &&
				goal.getPriority() == 1
			) ref.target = goal;
		});
		if (ref.target != null) {
			cockatrice.targetSelector
				.getAvailableGoals()
				.removeIf(wrappedGoal -> wrappedGoal == ref.target);
			cockatrice.targetSelector.addGoal(
				1,
				new HurtByOtherTypeTargetGoal(cockatrice)
			);
		}
		cockatrice.goalSelector.addGoal(
			1,
			new CockatriceMeleeAttackGoal(cockatrice, 1.5, false)
		);

		cockatrice.goalSelector.getAvailableGoals().forEach(goal -> {
			if (
				goal.getGoal().getClass().equals(RangedAttackGoal.class) &&
				goal.getPriority() == 2
			) {
				ref.goal = goal;
			}
		});
		if (ref.goal != null) {
			cockatrice.goalSelector
				.getAvailableGoals()
				.removeIf(wrappedGoal -> wrappedGoal == ref.goal);
			cockatrice.goalSelector.addGoal(
				2,
				new CockatriceRangedStrafeGoal(cockatrice, 1.0, 60, 10.0F)
			);
		}
	}
	
	private static final class GoalsRef {
		WrappedGoal target = null;
		WrappedGoal goal = null;
		
		@Override
		public boolean equals(Object o) {
			if (o == null || this.getClass() != o.getClass()) return false;
			var goalsRef = (GoalsRef) o;
			return Objects.equals(this.target, goalsRef.target) && Objects.equals(this.goal, goalsRef.goal);
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(this.target, this.goal);
		}
		
		@Override
		public String toString() {
			return "GoalsRef[" +
				"target=" + this.target +
				", goal=" + this.goal +
				']';
		}
	}
}
