package net.zepalesque.redux.mixin.mixins.client.block;

import com.aetherteam.aether.block.natural.AetherDoubleDropsLeaves;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.api.LeafChanceEntry;
import net.zepalesque.redux.block.natural.leaves.LeavesParticleUtil;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AetherDoubleDropsLeaves.class)
public class AetherLeavesClientMixin extends LeafBlockClientMixin {

    @Override
    protected void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        super.animateTick(state, level, pos, random, ci);

        @Nullable LeafChanceEntry entry = LeavesParticleUtil.findEntry((AetherDoubleDropsLeaves) (Object) this);
        if (entry != null) {
            LeavesParticleUtil.createParticle(state, level, pos, random, entry);
        }

    }
}
