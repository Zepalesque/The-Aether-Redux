package net.zepalesque.redux.block.natural;

import com.aetherteam.aether.block.natural.AetherLogBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.zepalesque.redux.block.util.state.ReduxStates;

public class ReduxNaturalLog extends AetherLogBlock {
    public ReduxNaturalLog(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(ReduxStates.NATURAL_GEN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ReduxStates.NATURAL_GEN);
    }
}
