package net.zepalesque.redux.block.natural.cloudcap;

import com.aetherteam.aether.block.natural.AetherDoubleDropBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.client.particle.ReduxParticleTypes;

public class CloudcapSporesBlock extends AetherDoubleDropBlock {
    public CloudcapSporesBlock(Properties properties) {
        super(properties);
    }

    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (level.random.nextInt(3) == 0 && level.isStateAtPosition(pos.below(), bs -> (!bs.canOcclude() || !bs.isFaceSturdy(level, pos, Direction.UP)))) {

            double d0 = (double) x + random.nextDouble();
            double d1 = (double) y + 0.7D;
            double d2 = (double) z + random.nextDouble();
            level.addParticle(ReduxParticleTypes.FALLING_CLOUDCAP_SPORE.get(), d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            blockpos$mutableblockpos.set(x + Mth.nextInt(random, -10, 10), y - random.nextInt(10), z + Mth.nextInt(random, -10, 10));
            BlockState blockstate = level.getBlockState(blockpos$mutableblockpos);
            if (!blockstate.isCollisionShapeFullBlock(level, blockpos$mutableblockpos)) {
                level.addParticle(ReduxParticleTypes.CLOUDCAP_AIR_SPORE.get(), (double)blockpos$mutableblockpos.getX() + random.nextDouble(), (double)blockpos$mutableblockpos.getY() + random.nextDouble(), (double)blockpos$mutableblockpos.getZ() + random.nextDouble(), 0.0D, 0.0D, 0.0D);
            }

    }
}
