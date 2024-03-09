package net.zepalesque.redux.event.hook;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.entity.monster.Swet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
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


    public static boolean shouldGrow(ItemStack stack, EntityType<?> swet) {
        return stack.is(AetherTags.Items.SWET_BALLS) || stack.getItem() instanceof SpawnEggItem spawnEggItem && spawnEggItem.getType(stack.getTag()) == swet;
    }


    public static void swetTick(Swet swet) {
        SwetMass.get(swet).ifPresent(SwetMass::tick);
    }

    public static double getSwetScale(int size) {
        return 1D + (0.25D * (size - 1));
    }
}
