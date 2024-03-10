package net.zepalesque.redux.event.hook;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.entity.monster.Swet;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.zepalesque.redux.capability.swet.SwetMass;
import net.zepalesque.redux.client.particle.ReduxParticleTypes;
import net.zepalesque.redux.entity.ai.goal.HuntNoConsumeGoal;
import net.zepalesque.redux.entity.ai.target.FollowUnabsorbedTargetGoal;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

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
        swet.targetSelector.removeAllGoals(goal -> true);
        swet.targetSelector.addGoal(1, new FollowUnabsorbedTargetGoal<>(
                swet, Player.class, 10, true, false, (player) ->
                Math.abs(player.getY() - swet.getY()) <= 4.0D &&
                        !(FollowUnabsorbedTargetGoal.withinAbsorbingRange(swet, player))
        ));
        swet.goalSelector.addGoal(1, new HuntNoConsumeGoal(swet));
    }


    public static boolean shouldGrow(ItemStack stack, EntityType<?> swet) {
        return stack.is(AetherTags.Items.SWET_BALLS) || stack.getItem() instanceof SpawnEggItem spawnEggItem && spawnEggItem.getType(stack.getTag()) == swet;
    }


    public static void swetTick(Swet swet) {
        SwetMass.get(swet).ifPresent(SwetMass::tick);
    }

    public static double getTrueScale(Swet swet) {
        int scaleIndex = swet.getSize();
        float waterScale = 1F - swet.getWaterDamageScale();
        return scaleIndex <= 0 ? waterScale : (1D + (0.5D * (scaleIndex - 1))) * waterScale;
    }

    public static double getAbsorbVectorScale(Swet swet) {
        return getTrueScale(swet)/* * 0.25*/;
    }

    public static boolean canAbsorbEntities(Swet swet) {
        return getTrueScale(swet) > 1.1D;
    }

    public static boolean canGrow(Swet swet) {
        return swet.getSize() < 20;
    }

    public static boolean canBeControlled(Swet swet) {
        return getTrueScale(swet) > 1.1D;
    }

    public static float getDamage(Swet swet) {
        return (swet.getSize() + Mth.sqrt(swet.getSize())) * 0.25F * (1F - swet.getWaterDamageScale());
    }
    
    protected static Map<EntityType<?>, Item> PARTICLE_ITEM_MAP = new HashMap<>();
    
    /** Should be called in {@link net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent#enqueueWork(Runnable)} */
    public static void registerParticle(EntityType<?> type, Item particle) {
        PARTICLE_ITEM_MAP.put(type, particle);
    }
    
    public static @Nullable ParticleOptions getSquelchParticles(Swet swet) {
        return PARTICLE_ITEM_MAP.containsKey(swet.getType()) ? new ItemParticleOption(ReduxParticleTypes.RANDOM_MOVEMENT_ITEM.get(), new ItemStack(PARTICLE_ITEM_MAP.get(swet.getType()))) : null;
    }
}
