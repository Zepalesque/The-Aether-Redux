package net.zepalesque.redux.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.zepalesque.redux.world.feature.config.PredicateStateConfig;

public class TestBelowBlockFeature extends Feature<PredicateStateConfig> {
   public TestBelowBlockFeature(Codec<PredicateStateConfig> codec) {
      super(codec);
   }

   /**
    * Places the given feature at the given location.
    * During world generation, features are provided with a 3x3 region of chunks, centered on the chunk being generated,
    * that they can safely generate into.
    * @param context A context object with a reference to the level and the position the feature is being placed at
    */
   public boolean place(FeaturePlaceContext<PredicateStateConfig> context) {
      PredicateStateConfig config = context.config();
      WorldGenLevel level = context.level();
      BlockPos pos = context.origin();
      BlockState state = config.toPlace().getState(context.random(), pos);
      if (config.predicate().test(level, pos.below()) && state.canSurvive(level, pos)) {
         if (state.getBlock() instanceof DoublePlantBlock) {
            if (!level.isEmptyBlock(pos.above())) {
               return false;
            }

            DoublePlantBlock.placeAt(level, state, pos, 2);
         } else {
            level.setBlock(pos, state, 2);
         }

         return true;
      } else {
         return false;
      }
   }

}