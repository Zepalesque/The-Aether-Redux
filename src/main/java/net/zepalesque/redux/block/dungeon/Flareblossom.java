package net.zepalesque.redux.block.dungeon;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.block.natural.CustomBoundsBushBlock;
import net.zepalesque.redux.block.natural.CustomBoundsFlowerBlock;
import net.zepalesque.redux.block.util.CommonPlantBounds;
import net.zepalesque.redux.util.math.MathUtil;

import java.util.function.Supplier;

public class Flareblossom extends CustomBoundsFlowerBlock {
    public Flareblossom(Supplier<MobEffect> effectSupplier, int pEffectDuration, Properties pProperties) {
        super(CommonPlantBounds.SHORT_FERN, effectSupplier, pEffectDuration, pProperties);
    }


    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
        VoxelShape voxelshape = this.getShape(state, level, pos, CollisionContext.empty());
        AABB aabb = voxelshape.bounds();
        double x = (double)pos.getX() + MathUtil.nextDouble(aabb.minX, aabb.maxX, random);
        double y = (double)pos.getY() + MathUtil.nextDouble(aabb.minY, aabb.maxY, random);
        double z = (double)pos.getZ() + MathUtil.nextDouble(aabb.minZ, aabb.maxZ, random);

        for(int i = 0; i < 3; ++i) {
            if (random.nextBoolean()) {
                level.addParticle(ParticleTypes.FLAME, x, y, z, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
