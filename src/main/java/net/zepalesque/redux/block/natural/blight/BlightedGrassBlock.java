package net.zepalesque.redux.block.natural.blight;

import com.aetherteam.aether.data.resources.registries.AetherPlacedFeatures;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.zepalesque.redux.block.util.state.ReduxStates;
import net.zepalesque.redux.block.util.state.enums.BlightGrassColor;

public class BlightedGrassBlock extends CustomAetherGrassBlock {
    public BlightedGrassBlock(Properties properties) {
        super(AetherPlacedFeatures.AETHER_GRASS_BONEMEAL, properties);
        this.registerDefaultState(this.defaultBlockState().setValue(ReduxStates.BLIGHT_GRASS_COLOR, BlightGrassColor.CONSTANT));
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ReduxStates.BLIGHT_GRASS_COLOR);
    }
}
