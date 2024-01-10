package net.zepalesque.redux.block.natural.cloudcap;

import com.aetherteam.aether.block.natural.AetherBushBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.block.natural.AetherShortGrassBlock;

public class AeveliumSproutsGrowthBlock extends AetherBushBlock {

    protected static final VoxelShape SHAPE_SPROUTS = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);
    protected static final VoxelShape SHAPE_GROWTH = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D);
    private final boolean isSprouts;
    public AeveliumSproutsGrowthBlock(Properties properties, boolean sprouts) {
        super(properties);
        this.isSprouts = sprouts;
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Vec3 vec3 = pState.getOffset(pLevel, pPos);
        return this.isSprouts ? SHAPE_SPROUTS.move(vec3.x, vec3.y, vec3.z) : SHAPE_GROWTH.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        return super.canBeReplaced(pState, pUseContext) && (pUseContext.getItemInHand().getItem() instanceof BlockItem blockItem && !(blockItem.getBlock() instanceof AeveliumSproutsGrowthBlock));
    }

}
