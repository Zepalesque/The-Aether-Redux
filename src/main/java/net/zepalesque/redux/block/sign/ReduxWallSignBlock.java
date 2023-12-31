    package net.zepalesque.redux.block.sign;

    import net.minecraft.core.BlockPos;
    import net.minecraft.world.level.block.WallSignBlock;
    import net.minecraft.world.level.block.entity.BlockEntity;
    import net.minecraft.world.level.block.state.BlockState;
    import net.minecraft.world.level.block.state.properties.WoodType;

public abstract class ReduxWallSignBlock extends WallSignBlock {
    public ReduxWallSignBlock(Properties properties, WoodType woodType) {
        super(properties, woodType);
    }

    @Override
    public abstract BlockEntity newBlockEntity(BlockPos pos, BlockState state);
}