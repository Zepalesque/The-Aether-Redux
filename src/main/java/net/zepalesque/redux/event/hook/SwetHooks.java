package net.zepalesque.redux.event.hook;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.entity.monster.Swet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.zepalesque.redux.client.particle.ReduxParticles;
import net.zepalesque.redux.entity.ai.goal.HuntNoConsumeGoal;
import net.zepalesque.redux.entity.ai.target.FollowUnabsorbedTargetGoal;

public class SwetHooks {
    public static void modifySwetAI(Swet swet) {
        final var toRemove = new GoalsRef();
        var goals = swet.goalSelector.getAvailableGoals();
        goals.forEach(goal -> {
            if (goal.getGoal().getClass().equals(Swet.ConsumeGoal.class)
                && goal.getPriority() == 0
            ) toRemove.consume = goal;
            else if (goal.getGoal().getClass().equals(Swet.HuntGoal.class)
                && goal.getPriority() == 1
            ) toRemove.hunt = goal;
        });
        
        if (toRemove.consume != null) goals.remove(toRemove.consume);
        if (toRemove.hunt != null) goals.remove(toRemove.hunt);
        
        // todo: is the above even necessary?
        swet.targetSelector.removeAllGoals(goal -> true);
        swet.targetSelector.addGoal(1, new FollowUnabsorbedTargetGoal<>(
                swet, Player.class, 10, true, false, player ->
                Math.abs(player.getY() - swet.getY()) <= 4.0D &&
                        !FollowUnabsorbedTargetGoal.withinAbsorbingRange(swet, player)
        ));
        swet.goalSelector.addGoal(1, new HuntNoConsumeGoal(swet));
    }
    
    // if only java actually had &T
    private static final class GoalsRef {
        WrappedGoal hunt = null;
        WrappedGoal consume = null;
        
        @Override
        public boolean equals(Object o) {
            if (o == null || this.getClass() != o.getClass()) return false;
            var goalsRef = (GoalsRef) o;
            return Objects.equals(this.hunt, goalsRef.hunt) && Objects.equals(this.consume, goalsRef.consume);
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(this.hunt, this.consume);
        }
        
        @Override
        public String toString() {
            return "GoalsRef[" +
                "hunt=" + this.hunt +
                ", consume=" + this.consume +
                ']';
        }
    }

    public static double getTrueScale(Swet swet) {
	    var scaleIndex = swet.getSize();
	    var waterScale = 1F - swet.getWaterDamageScale();
        return scaleIndex <= 0 ? waterScale : (1D + 0.5D * (scaleIndex - 1)) * waterScale;
    }

    public static double getAbsorbVectorScale(Swet swet) {
        return getTrueScale(swet)/* * 0.25*/;
    }

    public static boolean canAbsorbEntities(Swet swet) {
        return getTrueScale(swet) > 1.1D;
    }

    public static boolean canDamageEntities(Swet swet) {
        return getTrueScale(swet) > 2.1D;
    }

    public static boolean canGrow(Swet swet, ItemStack stack) {
        return stack.is(AetherTags.Items.SWET_BALLS)
            ? swet.getSize() < 8
            : stack.getItem() instanceof SpawnEggItem egg
              && egg.getType(stack) == swet.getType()
              && swet.getSize() < 16;
    }

    public static boolean canBeControlled(Swet swet) {
        return getTrueScale(swet) > 1.1D;
    }

    public static float getDamage(Swet swet) {
        return (swet.getSize()
            + Mth.sqrt(swet.getSize())
        ) * 0.25F * (1F - swet.getWaterDamageScale());
    }
    
    protected static ConcurrentMap<EntityType<?>, Item> PARTICLE_ITEM_MAP =
        new ConcurrentHashMap<>();
    
    /** Should be called in {@link net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent#enqueueWork(Runnable)} */
    public static void registerParticle(EntityType<?> type, Item particle) {
        PARTICLE_ITEM_MAP.put(type, particle);
    }
    
    @Nullable
    // TODO: replace with optional
    public static ParticleOptions getSquelchParticles(Swet swet) {
        return PARTICLE_ITEM_MAP.containsKey(swet.getType()) ? new ItemParticleOption(ReduxParticles.RANDOM_MOVEMENT_ITEM.get(), new ItemStack(PARTICLE_ITEM_MAP.get(swet.getType()))) : null;
    }
}
