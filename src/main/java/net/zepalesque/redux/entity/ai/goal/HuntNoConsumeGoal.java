package net.zepalesque.redux.entity.ai.goal;

import com.aetherteam.aether.entity.monster.Swet;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

public class HuntNoConsumeGoal extends Goal {
    private final Swet swet;

    public HuntNoConsumeGoal(Swet swet) {
        this.swet = swet;
        this.setFlags(EnumSet.of(Flag.LOOK));
    }

    public boolean canUse() {
	    var target = this.swet.getTarget();
        if (!this.swet.hasPrey() && target != null && target.isAlive() && !this.swet.isFriendlyTowardEntity(target)) {
	        if (target instanceof Player player && player.getAbilities().invulnerable) return false;

            return this.swet.getMoveControl() instanceof Swet.SwetMoveControl;
        } else return false;
    }

    public boolean canContinueToUse() {
	    var target = this.swet.getTarget();
        if (!this.swet.hasPrey() && target != null && target.isAlive()) {
	        if (target instanceof Player player && player.getAbilities().invulnerable) return false;

            return !this.swet.isFriendlyTowardEntity(target);
        } else return false;
    }

    public void tick() {
	    var control = this.swet.getMoveControl();
        if (control instanceof Swet.SwetMoveControl smc) {
	        var target = this.swet.getTarget();
            if (target != null) {
                this.swet.lookAt(target, 10.0F, 10.0F);
                smc.setDirection(this.swet.getYRot(), true);
            }
        }

    }
}