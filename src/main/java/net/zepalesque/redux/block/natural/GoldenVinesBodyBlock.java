package net.zepalesque.redux.block.natural;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.block.ReduxBlocks;

public class GoldenVinesBodyBlock extends GrowingPlantBodyBlock {

    public static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    private final TagKey<Block> leafTag;

    public GoldenVinesBodyBlock(Properties properties, TagKey<Block> leafTag) {
        super(properties, Direction.DOWN, SHAPE, false);
        this.leafTag = leafTag;
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.relative(this.growthDirection.getOpposite());
        BlockState blockstate = pLevel.getBlockState(blockpos);
        return !this.canAttachTo(blockstate) ? super.canSurvive(pState, pLevel, pPos) : super.canSurvive(pState, pLevel, pPos) || blockstate.is(this.leafTag);
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return ReduxBlocks.GOLDEN_VINES.get();
    }

    public static final MapCodec<GoldenVinesBodyBlock> CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                            propertiesCodec(),
                            TagKey.codec(Registries.BLOCK).fieldOf("leaf_tag").forGetter(block -> block.leafTag))
                    .apply(builder, GoldenVinesBodyBlock::new));


    @Override
    protected MapCodec<? extends GrowingPlantBodyBlock> codec() {
        return CODEC;
    }
}
