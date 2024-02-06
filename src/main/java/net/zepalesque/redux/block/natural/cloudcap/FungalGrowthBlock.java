package net.zepalesque.redux.block.natural.cloudcap;

import com.aetherteam.aether.block.AetherBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MossBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.zepalesque.redux.data.resource.ReduxConfiguredFeatures;

public class FungalGrowthBlock extends MossBlock {
    public FungalGrowthBlock(Properties p_153790_) {
        super(p_153790_);
        this.registerDefaultState(this.defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AetherBlockStateProperties.DOUBLE_DROPS);
    }

    public void performBonemeal(ServerLevel level, RandomSource rand, BlockPos pos, BlockState state) {
        level.registryAccess().registry(Registries.CONFIGURED_FEATURE).flatMap((configuredFeatures) -> {
            return configuredFeatures.getHolder(ReduxConfiguredFeatures.FUNGAL_PATCH_BONEMEAL);
        }).ifPresent((feature) -> {
            feature.value().place(level, level.getChunkSource().getGenerator(), rand, pos.above());
        });
    }
}
