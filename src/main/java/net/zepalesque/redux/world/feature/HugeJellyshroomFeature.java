package net.zepalesque.redux.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.zepalesque.redux.misc.ReduxTags;
import net.zepalesque.redux.world.feature.config.JellyshroomConfig;

import java.util.HashMap;
import java.util.Map;

public class HugeJellyshroomFeature extends Feature<JellyshroomConfig> {
   public HugeJellyshroomFeature(Codec<JellyshroomConfig> p_65975_) {
      super(p_65975_);
   }

   @Override
   public boolean place(FeaturePlaceContext<JellyshroomConfig> context) {
      int height = context.config().height.sample(context.random());
      Map<BlockPos, BlockState> toPlace = new HashMap<>();
      BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
      BlockPos origin = context.origin();
      if (context.level().isStateAtPosition(origin.below(), state -> !isDirt(state))) {
         return false;
      }
      // Place the stem
      for (int i = 0; i < height; i++) {
         mutable.setWithOffset(origin, 0, i, 0);
         BlockPos immutable1 = mutable.immutable();
         toPlace.putIfAbsent(immutable1, context.config().stem.getState(context.random(), immutable1));
      }
      // Place the jelly top. Note that this is 4 blocks tall, with a diameter of 5 blocks.
      BlockPos jellyOrigin = origin.above(height);
      int radius = 2;
      for (int y = 0; y >= -3; y--) {
         for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
               boolean center = y > -3 && y < 0;
               boolean middlecorners = (x != -radius && x != radius) || (z != -radius && z != radius);
               boolean isDiamond = Mth.abs(x) + Mth.abs(z) <= radius;
               if ((center && middlecorners) || isDiamond) {
                  mutable.setWithOffset(jellyOrigin, x, y, z);
                  BlockPos immutable2 = mutable.immutable();
                  toPlace.putIfAbsent(immutable2, context.config().jelly.getState(context.random(), immutable2));
               }
            }
         }
      }

      // if tall enough, add two side shrooms
      if (height >= 7) {
         Direction d1 = Direction.Plane.HORIZONTAL.getRandomDirection(context.random());
         Direction d2 = Util.getRandom(Direction.Plane.HORIZONTAL.stream().filter(direction -> direction != d1).toList(), context.random());
         int minAbove = 1;
         int max = height - 5;
         int h1 = context.random().nextInt(max - minAbove) + minAbove;
         int h2 = context.random().nextInt(max - minAbove) + minAbove;
         mutable.setWithOffset(origin, d1.getStepX(), h1, d1.getStepZ());
         BlockPos immutable3 = mutable.immutable();

         toPlace.putIfAbsent(immutable3, context.config().jelly.getState(context.random(), immutable3));

         mutable.setWithOffset(origin, d2.getStepX(), h2, d2.getStepZ());
         BlockPos immutable4 = mutable.immutable();
         toPlace.putIfAbsent(immutable4, context.config().jelly.getState(context.random(), immutable4));
      }

      for (Map.Entry<BlockPos, BlockState> entry : toPlace.entrySet()) {
         if (!canPlaceBlockHere(context.level(), entry.getKey())) {
            return false;
         }
      }
      for (Map.Entry<BlockPos, BlockState> entry : toPlace.entrySet()) {
         this.setBlock(context.level(), entry.getKey(), entry.getValue());
      }
      return true;
   }


   protected boolean canPlaceBlockHere(LevelAccessor level, BlockPos pos) {
      int i = pos.getY();
      if (i >= level.getMinBuildHeight() + 1 && i + 1 < level.getMaxBuildHeight()) {
         return level.isStateAtPosition(pos, state -> state.isAir() || state.is(BlockTags.LOGS) || state.getMaterial().isReplaceable() || state.is(ReduxTags.Blocks.MUSHROOM_CAPS));
      }
      return false;
   }


/*   protected boolean isValidPosition(LevelAccessor pLevel, BlockPos pPos, int height) {
      int i = pPos.getY();
      if (i >= pLevel.getMinBuildHeight() + 1 && i + height + 1 < pLevel.getMaxBuildHeight()) {
         BlockState blockstate = pLevel.getBlockState(pPos.below());
         return isDirt(blockstate);
      } else {
         return false;
      }
   }*/
}