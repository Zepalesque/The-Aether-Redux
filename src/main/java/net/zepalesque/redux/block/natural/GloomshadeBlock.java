package net.zepalesque.redux.block.natural;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.unity.block.natural.bush.CustomBoundsFlowerBlock;

public class GloomshadeBlock extends CustomBoundsFlowerBlock {

    public GloomshadeBlock(VoxelShape shape, Holder<MobEffect> effect, int duration, Properties properties) {
        super(shape, effect, duration, properties);
    }

    // TODO
    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        super.entityInside(state, level, pos, entity);
    }
}
