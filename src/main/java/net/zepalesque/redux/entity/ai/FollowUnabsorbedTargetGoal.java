package net.zepalesque.redux.entity.ai;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class FollowUnabsorbedTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    public FollowUnabsorbedTargetGoal(Mob mob, Class<T> targetClass, int reciprocalChance, boolean checkVisibility, boolean checkCanNavigate, @Nullable Predicate<LivingEntity> targetPredicate) {
        super(mob, targetClass, reciprocalChance, checkVisibility, checkCanNavigate, targetPredicate);
    }

    public static boolean canAbsorb(Entity swet, Entity target) {
        return !target.isShiftKeyDown() && !(target instanceof Player player && player.getAbilities().flying) &&
                swet.getBoundingBox().inflate(0, 0.5, 0).move(0, 0.25, 0).intersects(target.getBoundingBox());
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !(canAbsorb(this.mob, this.mob.getTarget()));
    }


}