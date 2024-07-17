package net.zepalesque.redux.block.natural.blight;

import net.minecraft.core.BlockPos;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.MossBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.data.resource.ReduxFeatureConfig;

public class BlightmossBlock extends MossBlock {
    public BlightmossBlock(Properties p_153790_) {
        super(p_153790_);
    }

    public void performBonemeal(ServerLevel level, RandomSource rand, BlockPos pos, BlockState state) {
        level.registryAccess().registry(BuiltinRegistries.CONFIGURED_FEATURE.key()).flatMap((configuredFeatures) -> {
            return configuredFeatures.getHolder(ReduxFeatureConfig.BLIGHTMOSS_PATCH_BONEMEAL);
        }).ifPresent((feature) -> {
            feature.value().place(level, level.getChunkSource().getGenerator(), rand, pos.above());
        });
    }
}
