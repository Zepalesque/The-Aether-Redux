package net.zepalesque.redux.block.dungeon;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.client.particle.ReduxParticles;
import net.zepalesque.unity.block.natural.bush.CustomBoundsFlowerBlock;
import net.zepalesque.zenith.util.math.MathUtil;

public class Flareblossom extends CustomBoundsFlowerBlock {
    public static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 13.0D, 12.0D);
    public Flareblossom(Holder<MobEffect> effectSupplier, int pEffectDuration, Properties pProperties) {
        super(SHAPE, effectSupplier, pEffectDuration, pProperties);
    }


    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
        var shape = this.getShape(state, level, pos, CollisionContext.empty());
        var bb = shape.bounds();
        var x = (double)pos.getX() + MathUtil.nextDouble(bb.minX, bb.maxX, random);
        var y = (double)pos.getY() + MathUtil.nextDouble(bb.minY + 0.25, bb.maxY, random);
        var z = (double)pos.getZ() + MathUtil.nextDouble(bb.minZ, bb.maxZ, random);

        if (random.nextFloat() > 0.25F)
            level.addParticle(ReduxParticles.BLOSSOM_FLARE.get(), x, y, z, 0.0D, 0.0D, 0.0D);
    }
}
