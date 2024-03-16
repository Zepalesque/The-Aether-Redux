package net.zepalesque.redux.block.natural.skyfields;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import net.minecraft.world.level.material.MapColor;
import net.zepalesque.redux.block.natural.ExtendedDistanceLeavesBlock;
import net.zepalesque.redux.block.util.state.ReduxStates;
import net.zepalesque.redux.client.particle.ReduxParticleTypes;

import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class FieldsprootLeafBlock extends ExtendedDistanceLeavesBlock {
    private final BiFunction<RandomSource, BlockState, ? extends ParticleOptions> particle;

    public static final PerlinNoise PERLIN = PerlinNoise.create(new XoroshiroRandomSource(2743), IntStream.of(0));

    public FieldsprootLeafBlock(BiFunction<RandomSource, BlockState, ? extends ParticleOptions> particle, Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(ReduxStates.PRISMATICNESS, 3));
        this.particle = particle;

    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ReduxStates.PRISMATICNESS);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        if (state == null) {
            return null;
        }
        return this.getState(state, context.getLevel(), context.getClickedPos());
    }

    public static ParticleOptions particleFromState(RandomSource rand, BlockState state) {

        if (!state.hasProperty(ReduxStates.PRISMATICNESS) || rand.nextFloat() < 0.2F) {
            return ReduxParticleTypes.FALLING_PRISMATIC_LEAVES.get();
        } else {
            int i = state.getValue(ReduxStates.PRISMATICNESS);
            return
              i == 0 ? ReduxParticleTypes.FIELDSPROUT_PETALS_0.get() :
              i == 1 ? ReduxParticleTypes.FIELDSPROUT_PETALS_1.get() :
              i == 2 ? ReduxParticleTypes.FIELDSPROUT_PETALS_2.get() :
              i == 3 ? ReduxParticleTypes.FIELDSPROUT_PETALS_3.get() :
              i == 4 ? ReduxParticleTypes.FIELDSPROUT_PETALS_4.get() :
              i == 5 ? ReduxParticleTypes.FIELDSPROUT_PETALS_5.get() :
              i == 6 ? ReduxParticleTypes.FIELDSPROUT_PETALS_6.get() :
                       ReduxParticleTypes.FALLING_PRISMATIC_LEAVES.get();
        }
    }
    public static MapColor colorFromState(BlockState state) {

        if (!state.hasProperty(ReduxStates.PRISMATICNESS)) {
            return MapColor.COLOR_BLUE;
        } else {
            int i = state.getValue(ReduxStates.PRISMATICNESS);
            return i <= 1 ? MapColor.COLOR_LIGHT_BLUE : i >= 5 ? MapColor.COLOR_PINK : MapColor.COLOR_PURPLE;
        }
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        BlockState newState = this.getState(pState, pLevel, pCurrentPos);
        if (newState != pState)
        {
            pLevel.setBlock(pCurrentPos, newState, 3);
        }
        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    private BlockState getState(BlockState state, LevelAccessor level, BlockPos pos) {
        double scale = 0.1D;
        double x = pos.getX() * scale;
        double y = pos.getY() * scale;
        double z = pos.getZ() * scale;
        double noiseVal = PERLIN.getValue(x, y, z);
        double clamped = Mth.clamp(noiseVal, -0.5D, 0.5D);
        int prism = Mth.lerpInt((float) clamped + 0.5F, 0, 6);
        return state.setValue(ReduxStates.PRISMATICNESS, prism);
    }


    /**
     * Called periodically clientside on blocks near the player to show effects (like furnace fire particles).
     */
    public void animateTick(BlockState block, Level world, BlockPos position, RandomSource rand) {
        super.animateTick(block, world, position, rand);
        if (rand.nextInt(15) == 0) {
            BlockPos blockpos = position.below();
            BlockState blockstate = world.getBlockState(blockpos);
            if (!blockstate.canOcclude() || !blockstate.isFaceSturdy(world, blockpos, Direction.UP)) {
                ParticleUtils.spawnParticleBelow(world, position, rand, this.particle.apply(rand, block));
            }
        }
    }
}