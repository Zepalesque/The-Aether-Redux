package net.zepalesque.redux.block.dungeon;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.block.natural.CustomBoundsBushBlock;
import net.zepalesque.redux.block.natural.CustomBoundsFlowerBlock;
import net.zepalesque.redux.block.util.CommonPlantBounds;

import java.util.function.Supplier;

public class Flareblossom extends CustomBoundsFlowerBlock {
    public Flareblossom(Supplier<MobEffect> effectSupplier, int pEffectDuration, Properties pProperties) {
        super(CommonPlantBounds.SHORT_FERN, effectSupplier, pEffectDuration, pProperties);
    }


    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
        VoxelShape voxelshape = this.getShape(state, level, pos, CollisionContext.empty());
        Vec3 vec3 = voxelshape.bounds().getCenter();
        double d0 = (double)pos.getX() + vec3.x;
        double d1 = (double)pos.getZ() + vec3.z;

        for(int i = 0; i < 3; ++i) {
            if (random.nextBoolean()) {
                level.addParticle(ParticleTypes.FLAME, d0 + random.nextDouble() / 5.0D, (double)pos.getY() + (0.5D - random.nextDouble()), d1 + random.nextDouble() / 5.0D, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
