package net.zepalesque.redux.mixin.client.block;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.natural.AetherDoubleDropsLeaves;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.Lazy;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.natural.ExtendedDistanceLeavesBlock;
import net.zepalesque.redux.client.particle.ReduxParticleTypes;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.misc.ReduxTags;
import net.zepalesque.redux.util.compat.DeepCompatUtil;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.function.Supplier;

@Mixin(AetherDoubleDropsLeaves.class)
public class AetherLeavesClientMixin extends LeafBlockClientMixin {

    @Override
    protected void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        super.animateTick(state, level, pos, random, ci);

        // we are already on the client side
        @Nullable Supplier<? extends ParticleOptions> supplier = redux$getParticle((AetherDoubleDropsLeaves) (Object) this);
        int chance = level.getBiome(pos).is(ReduxTags.Biomes.DENSE_LEAF_FALL) ? 20 : 30;
        if (supplier != null && random.nextInt(chance) == 0) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            if ((!blockstate.canOcclude() || !blockstate.isFaceSturdy(level, blockpos, Direction.UP)) && !(blockstate.getBlock() instanceof LeavesBlock) && !(blockstate.getBlock() instanceof ExtendedDistanceLeavesBlock)) {
                ParticleUtils.spawnParticleBelow(level, pos, random, supplier.get());
            }
        }
    }

    @Unique
    private static @Nullable Supplier<? extends ParticleOptions> redux$getParticle(Block self) {
        if (ReduxConfig.CLIENT.better_leaf_particles.get()) {
            @Nullable Supplier<? extends ParticleOptions> particle = PARTICLE_MAP.get().get(self);
            if (particle == null) {
                if (Redux.deepAetherCompat()) {
                    @Nullable Supplier<? extends ParticleOptions> deep = DeepCompatUtil.getParticle(self);
                    if (deep != null) {
                        particle = deep;
                    }
                }
                if (Redux.aetherGenesisCompat()) {
                    @Nullable Supplier<? extends ParticleOptions> genesis = GenesisCompatUtil.getParticle(self);
                    if (genesis != null) {
                        particle = genesis;
                    }
                }
                if (Redux.ancientAetherCompat()) {
                    @Nullable Supplier<? extends ParticleOptions> ancient = AncientCompatUtil.getParticle(self);
                    if (ancient != null) {
                        particle = ancient;
                    }
                }
            }
            return particle;
        } else {
            return null;
        }
    }

    @Unique
    private static final Lazy<Map<Block, Supplier<? extends ParticleOptions>>> PARTICLE_MAP = Lazy.of(() -> new ImmutableMap.Builder<Block, Supplier<? extends ParticleOptions>>()
            .put(AetherBlocks.SKYROOT_LEAVES.get(), ReduxParticleTypes.FALLING_SKYROOT_LEAVES)
            .put(ReduxBlocks.BLIGHTWILLOW_LEAVES.get(), ReduxParticleTypes.FALLING_BLIGHTWILLOW_LEAVES)
            .put(ReduxBlocks.BLIGHTED_SKYROOT_LEAVES.get(), ReduxParticleTypes.FALLING_BLIGHTED_SKYROOT_LEAVES)
            .build());


}
