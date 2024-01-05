package net.zepalesque.redux.block.natural;

import com.aetherteam.aether.block.AetherBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.block.util.ReduxStates;

import java.util.OptionalInt;

public class ExtendedDistanceLeavesBlock extends Block implements SimpleWaterloggedBlock, net.minecraftforge.common.IForgeShearable {
   public static final int DECAY_DISTANCE = 14;
   public static final IntegerProperty EXTENDED_DISTANCE  = ReduxStates.EXTENDED_DISTANCE;
   public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

   public ExtendedDistanceLeavesBlock(BlockBehaviour.Properties properties) {
      super(properties);
      this.registerDefaultState(this.stateDefinition.any().setValue(AetherBlockStateProperties.DOUBLE_DROPS, false).setValue(EXTENDED_DISTANCE, Integer.valueOf(DECAY_DISTANCE)).setValue(PERSISTENT, Boolean.valueOf(false)).setValue(WATERLOGGED, Boolean.valueOf(false)));
   }

   public VoxelShape getBlockSupportShape(BlockState state, BlockGetter reader, BlockPos pos) {
      return Shapes.empty();
   }

   @Override
   public boolean shouldDisplayFluidOverlay(BlockState state, BlockAndTintGetter level, BlockPos pos, FluidState fluidState)
   {
      return true;
   }
   /**
    * @return whether this block needs random ticking.
    */
   public boolean isRandomlyTicking(BlockState state) {
      return state.getValue(EXTENDED_DISTANCE) == DECAY_DISTANCE && !state.getValue(PERSISTENT);
   }

   /**
    * Performs a random tick on a block.
    */
   public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (this.decaying(state)) {
         dropResources(state, level, pos);
         level.removeBlock(pos, false);
      }

   }

   protected boolean decaying(BlockState state) {
      return !state.getValue(PERSISTENT) && state.getValue(EXTENDED_DISTANCE) == DECAY_DISTANCE;
   }

   public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      level.setBlock(pos, updateDistance(state, level, pos), 3);
   }

   public int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
      return 1;
   }

   /**
    * Update the provided state given the provided neighbor direction and neighbor state, returning a new state.
    * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
    * returns its solidified counterpart.
    * Note that this method should ideally consider only the specific direction passed in.
    */
   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
      if (state.getValue(WATERLOGGED)) {
         level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
      }

      int i = getDistanceAt(facingState) + 1;
      if (i != 1 || state.getValue(EXTENDED_DISTANCE) != i) {
         level.scheduleTick(currentPos, this, 1);
      }

      return state;
   }

   private static BlockState updateDistance(BlockState state, LevelAccessor level, BlockPos pos) {
      int i = DECAY_DISTANCE;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(Direction direction : Direction.values()) {
         blockpos$mutableblockpos.setWithOffset(pos, direction);
         i = Math.min(i, getDistanceAt(level.getBlockState(blockpos$mutableblockpos)) + 1);
         if (i == 1) {
            break;
         }
      }

      return state.setValue(EXTENDED_DISTANCE, Integer.valueOf(i));
   }

   private static int getDistanceAt(BlockState neighbor) {
      return getOptionalDistanceAt(neighbor).orElse(DECAY_DISTANCE);
   }

   public static OptionalInt getOptionalDistanceAt(BlockState state) {
      if (state.is(BlockTags.LOGS)) {
         return OptionalInt.of(0);
      } else {
         return state.hasProperty(EXTENDED_DISTANCE) ? OptionalInt.of(state.getValue(EXTENDED_DISTANCE)) : OptionalInt.empty();
      }
   }

   public FluidState getFluidState(BlockState state) {
      return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
   }

   /**
    * Called periodically clientside on blocks near the player to show effects (like furnace fire particles).
    */
   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      if (level.isRainingAt(pos.above())) {
         if (random.nextInt(15) == 1) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            if (!blockstate.canOcclude() || !blockstate.isFaceSturdy(level, blockpos, Direction.UP)) {
               ParticleUtils.spawnParticleBelow(level, pos, random, ParticleTypes.DRIPPING_WATER);
            }
         }
      }
   }

   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
      builder.add(EXTENDED_DISTANCE, PERSISTENT, WATERLOGGED, AetherBlockStateProperties.DOUBLE_DROPS);
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
      BlockState blockstate = this.defaultBlockState().setValue(PERSISTENT, Boolean.valueOf(true)).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
      return updateDistance(blockstate, context.getLevel(), context.getClickedPos());
   }
}
