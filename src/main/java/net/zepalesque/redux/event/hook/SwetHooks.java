package net.zepalesque.redux.event.hook;

import com.aetherteam.aether.entity.monster.Swet;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.zepalesque.redux.capability.swet.SwetMass;

public class SwetHooks {


    public static void modifySwetAI(Swet swet) {
        final WrappedGoal[] toRemove = new WrappedGoal[2];
        swet.goalSelector.getAvailableGoals().forEach((goal) -> {
            if ((goal.getGoal().getClass().equals(Swet.ConsumeGoal.class) && goal.getPriority() == 0)) {
                toRemove[0] = goal;
            }
            if ((goal.getGoal().getClass().equals(Swet.HuntGoal.class) && goal.getPriority() == 1)) {
                toRemove[1] = goal;
            }
        });
        if (toRemove[0] != null && toRemove[1] != null) {
            swet.goalSelector.getAvailableGoals().removeIf((wrappedGoal -> wrappedGoal == toRemove[0] || wrappedGoal == toRemove[1]));
        }
    }


    public static void swetTick(Swet swet) {
/*        SwetMass
        if (!this.isDead()) {
            massStuck = 0;
            world.getOtherEntities(this, this.getBoundingBox().stretch(0.9, 0.9, 0.9)).forEach((entity) -> {
                Box box = entity.getBoundingBox();
                massStuck += box.getXLength() * box.getYLength() * box.getZLength();
            });
            world.getOtherEntities(this, this.getBoundingBox()).forEach(this::onEntityCollision);
        }*/
    }
}
