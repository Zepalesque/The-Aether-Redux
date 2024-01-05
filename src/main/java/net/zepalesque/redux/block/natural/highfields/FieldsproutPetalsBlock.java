package net.zepalesque.redux.block.natural.highfields;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.block.util.PetalPrismaticness;
import net.zepalesque.redux.block.util.ReduxStates;

import java.util.List;

public class FieldsproutPetalsBlock extends BushBlock implements BonemealableBlock {
   public static final int MIN_FLOWERS = 1;
   public static final int MAX_FLOWERS = 4;


   public FieldsproutPetalsBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(this.stateDefinition.any()
              .setValue(ReduxStates.PETAL_1, PetalPrismaticness.ZERO)
              .setValue(ReduxStates.PETAL_2, PetalPrismaticness.NONE)
              .setValue(ReduxStates.PETAL_3, PetalPrismaticness.NONE)
              .setValue(ReduxStates.PETAL_4, PetalPrismaticness.NONE)
              .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)
      );
   }

   public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
      return !context.isSecondaryUseActive() && context.getItemInHand().is(this.asItem()) && getFlowerCount(state) < 4 || super.canBeReplaced(state, context);
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      int r = (int) (Mth.getSeed(context.getClickedPos()) % 4);
      Direction d = r == 0 ? Direction.NORTH : r == 1 ? Direction.EAST : r == 2 ? Direction.SOUTH : Direction.EAST;
      BlockState b = this.increaseFlowers(context.getClickedPos(), context.getLevel());


      return b.hasProperty(BlockStateProperties.HORIZONTAL_FACING) ? b.setValue(BlockStateProperties.HORIZONTAL_FACING, d) : b;
   }

   @Override
   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
      BlockState b = state;
      int r = (int) (Mth.getSeed(currentPos) % 4);
      Direction d = r == 0 ? Direction.NORTH : r == 1 ? Direction.EAST : r == 2 ? Direction.SOUTH : Direction.EAST;
      if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING) && state.getValue(BlockStateProperties.HORIZONTAL_FACING) != d) {
         b = state.setValue(BlockStateProperties.HORIZONTAL_FACING, d);
      }
      List<Property<PetalPrismaticness>> list = List.of(ReduxStates.PETAL_1, ReduxStates.PETAL_2, ReduxStates.PETAL_3, ReduxStates.PETAL_4);
      for (Property<PetalPrismaticness> prop : list) {
         if (b.hasProperty(prop) && b.getValue(prop) != PetalPrismaticness.NONE) {
            RandomSource rand = new XoroshiroRandomSource(Mth.getSeed(currentPos) + getCountFromProperty(prop));
            PetalPrismaticness val = PetalPrismaticness.getFromIndex(rand.nextInt(7));
            b = b.setValue(prop, val);
         }
      }
      if (b != state && state.canSurvive(level, currentPos)) {
         return b;
      }


      return super.updateShape(state, facing, facingState, level, currentPos, facingPos);

   }


   public BlockState increaseFlowers(BlockPos pos, Level level) {
      return this.increaseFlowers(pos, level.getBlockState(pos));

   }
   public BlockState increaseFlowers(BlockPos pos, BlockState state) {
      int flowerCount = getFlowerCount(state) + 1;
      RandomSource rand = new XoroshiroRandomSource(Mth.getSeed(pos) + flowerCount);
      Property<PetalPrismaticness> p = getPropertyFromCount(flowerCount);
      PetalPrismaticness val = PetalPrismaticness.getFromIndex(rand.nextInt(7));
      if (state.hasProperty(p)) {
         return state.setValue(p, val);
      } else {
         return this.defaultBlockState().setValue(p, val);
      }
   }


   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
      builder.add(ReduxStates.PETAL_1, ReduxStates.PETAL_2, ReduxStates.PETAL_3, ReduxStates.PETAL_4, BlockStateProperties.HORIZONTAL_FACING);
   }

   /**
    * @return whether bonemeal can be used on this block
    */
   public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean client) {
      return true;
   }

   public boolean isBonemealSuccess(Level level, RandomSource rand, BlockPos pos, BlockState state) {
      return true;
   }

   public static int getFlowerCount(BlockState b) {
      if (!b.hasProperty(ReduxStates.PETAL_1) || !b.hasProperty(ReduxStates.PETAL_2) || !b.hasProperty(ReduxStates.PETAL_3) || !b.hasProperty(ReduxStates.PETAL_4)) {
         return 0;
      }
      boolean hasFirst = b.getValue(ReduxStates.PETAL_1) != PetalPrismaticness.NONE;
      boolean hasSecond = b.getValue(ReduxStates.PETAL_2) != PetalPrismaticness.NONE;
      boolean hasThird = b.getValue(ReduxStates.PETAL_3) != PetalPrismaticness.NONE;
      boolean hasFourth = b.getValue(ReduxStates.PETAL_4) != PetalPrismaticness.NONE;

      return hasFirst ? (hasSecond ? (hasThird ? (hasFourth ? 4 : 3) : 2) : 1) : 0;
   }

   public static Property<PetalPrismaticness> getPropertyFromCount(int i) {
      return i <= 1 ? ReduxStates.PETAL_1 :
              i == 2 ? ReduxStates.PETAL_2 :
              i == 3 ? ReduxStates.PETAL_3 :
                      ReduxStates.PETAL_4;
   }
   public static int getCountFromProperty(Property<PetalPrismaticness> i) {
      return i == ReduxStates.PETAL_1 ? 1:
             i == ReduxStates.PETAL_2 ? 2 :
             i == ReduxStates.PETAL_3 ? 3 :
             i == ReduxStates.PETAL_4 ? 4 : 0;
   }

   public void performBonemeal(ServerLevel level, RandomSource rand, BlockPos pos, BlockState state) {
      int amount = getFlowerCount(state);
      if (amount < 4) {
         level.setBlock(pos, this.increaseFlowers(pos, level), 2);
      } else {
         popResource(level, pos, new ItemStack(this));
      }
   }
}