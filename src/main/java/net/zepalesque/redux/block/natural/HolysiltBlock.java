package net.zepalesque.redux.block.natural;

import com.aetherteam.aether.block.natural.AetherDoubleDropBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class HolysiltBlock extends AetherDoubleDropBlock implements Fallable {
    public HolysiltBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        super.stepOn(pLevel, pPos, pState, pEntity);
        if (!pLevel.isClientSide()) {
            if (!pEntity.isSteppingCarefully() && isFree(pLevel.getBlockState(pPos.below())) && pPos.getY() >= pLevel.getMinBuildHeight()) {
                FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(pLevel, pPos, pState);
                this.falling(fallingblockentity);
            }
        }
    }

    public static boolean isFree(BlockState pState) {
        Material material = pState.getMaterial();
        return pState.isAir() || pState.is(BlockTags.FIRE) || material.isLiquid() || material.isReplaceable();
    }
    protected void falling(FallingBlockEntity pEntity) {
    }
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextInt(16) == 0) {
            BlockPos blockpos = pPos.below();
            if (isFree(pLevel.getBlockState(blockpos))) {
                double d0 = (double)pPos.getX() + pRandom.nextDouble();
                double d1 = (double)pPos.getY() - 0.05D;
                double d2 = (double)pPos.getZ() + pRandom.nextDouble();
                pLevel.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, pState), d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }

    }
}
