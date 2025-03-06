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
        VoxelShape voxelshape = this.getShape(state, level, pos, CollisionContext.empty());
        AABB aabb = voxelshape.bounds();
        double x = (double)pos.getX() + MathUtil.nextDouble(aabb.minX, aabb.maxX, random);
        double y = (double)pos.getY() + MathUtil.nextDouble(aabb.minY + 0.25, aabb.maxY, random);
        double z = (double)pos.getZ() + MathUtil.nextDouble(aabb.minZ, aabb.maxZ, random);

        if (random.nextFloat() > 0.25F)
            level.addParticle(ReduxParticles.BLOSSOM_FLARE.get(), x, y, z, 0.0D, 0.0D, 0.0D);
    }
}
