package net.zepalesque.redux.entity.ai.goal;

import com.aetherteam.aether.entity.monster.Swet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class HuntNoConsumeGoal extends Goal {
    private final Swet swet;

    public HuntNoConsumeGoal(Swet swet) {
        this.swet = swet;
        this.setFlags(EnumSet.of(Flag.LOOK));
    }

    public boolean canUse() {
        LivingEntity target = this.swet.getTarget();
        if (!this.swet.hasPrey() && target != null && target.isAlive() && !this.swet.isFriendlyTowardEntity(target)) {
            if (target instanceof Player) {
                Player player = (Player)target;
                if (player.getAbilities().invulnerable) {
                    return false;
                }
            }

            return this.swet.getMoveControl() instanceof Swet.SwetMoveControl;
        } else {
            return false;
        }
    }

    public boolean canContinueToUse() {
        LivingEntity target = this.swet.getTarget();
        if (!this.swet.hasPrey() && target != null && target.isAlive()) {
            if (target instanceof Player) {
                Player player = (Player)target;
                if (player.getAbilities().invulnerable) {
                    return false;
                }
            }

            return !this.swet.isFriendlyTowardEntity(target);
        } else {
            return false;
        }
    }

    public void tick() {
        MoveControl var2 = this.swet.getMoveControl();
        if (var2 instanceof Swet.SwetMoveControl swetMoveControl) {
            LivingEntity target = this.swet.getTarget();
            if (target != null) {
                this.swet.lookAt(target, 10.0F, 10.0F);
                swetMoveControl.setDirection(this.swet.getYRot(), true);
            }
        }

    }
}