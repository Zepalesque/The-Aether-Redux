package net.zepalesque.redux.mixin.mixins.client.block;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.natural.AetherDoubleDropsLeaves;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.natural.leaves.LeavesParticleUtil;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.data.ReduxTags;
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

        @Nullable Pair<ParticleOptions, Integer> pair = LeavesParticleUtil.particleFor((AetherDoubleDropsLeaves) (Object) this);
        if (pair != null) {
            LeavesParticleUtil.createParticle(state, level, pos, random, pair.getFirst(), pair.getSecond());
        }

    }
}
