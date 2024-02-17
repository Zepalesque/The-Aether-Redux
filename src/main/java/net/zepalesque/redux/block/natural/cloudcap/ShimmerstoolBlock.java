package net.zepalesque.redux.block.natural.cloudcap;

import com.aetherteam.aether.block.natural.AetherBushBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.client.particle.ReduxParticleTypes;

// TODO: improve method of randomizing particle location
public class ShimmerstoolBlock extends AetherBushBlock {
    private static final double MIN_X = 0.1875;
    private static final double MIN_Y = 0.0D;
    private static final double MIN_Z = 0.1875;
    private static final double MAX_X = 0.8125;
    private static final double MAX_Y = 0.375;
    private static final double MAX_Z = 0.8125;

    private static final VoxelShape box = Block.box(MIN_X * 16.0D, MIN_Y * 16.0D, MIN_Z * 16.0D, MAX_X * 16.0D, MAX_Y * 16.0D, MAX_Z * 16.0D);
    public ShimmerstoolBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Vec3 vec3 = state.getOffset(level, pos);
        return box.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);
        Vec3 vec3 = state.getOffset(level, pos);
        double x = pos.getX() + MIN_X - 0.1875 + (random.nextDouble()) + vec3.x;
        double y = pos.getY() + MIN_Y + 0.125 + (random.nextDouble() * (MAX_Y - MIN_Y)) + vec3.y;
        double z = pos.getZ() + MIN_Z - 0.1875 + (random.nextDouble()) + vec3.z;
        level.addParticle(ReduxParticleTypes.SHIMMERSTAR.get(), x, y, z, 0D, 0D, 0D);
    }
}
