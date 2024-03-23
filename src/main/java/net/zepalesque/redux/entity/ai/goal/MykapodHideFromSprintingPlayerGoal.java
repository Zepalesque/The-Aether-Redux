package net.zepalesque.redux.entity.ai.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.zepalesque.redux.entity.passive.Mykapod;

import javax.annotation.Nullable;

public class MykapodHideFromSprintingPlayerGoal extends Goal {

    private static final TargetingConditions TEMP_TARGETING = TargetingConditions.forNonCombat().range(10.0D).ignoreLineOfSight();

    protected final Mykapod mykapod;
    @Nullable
    protected Player player;
    private final TargetingConditions targetingConditions;

    public MykapodHideFromSprintingPlayerGoal(Mykapod mykapod) {
        this.mykapod = mykapod;
        this.targetingConditions = TEMP_TARGETING.copy().selector(this::scaredOf);
    }

    private boolean scaredOf(LivingEntity entity) {
        return entity.isSprinting();
    }

        @Override
    public boolean canUse() {
        this.player = this.mykapod.level().getNearestPlayer(this.targetingConditions, this.mykapod);
        return this.player != null && this.mykapod.hideCooldown <= 0;
    }
    public boolean canContinueToUse() {
        return this.canUse();
    }

    @Override
    public void start() {
        super.start();
        if (!this.mykapod.isHiding()) {
            this.mykapod.setHiding(Mykapod.HideStatus.SCARED);
            this.mykapod.hideCounter = 120 + this.mykapod.getRandom().nextInt(60);
        }
    }
}

