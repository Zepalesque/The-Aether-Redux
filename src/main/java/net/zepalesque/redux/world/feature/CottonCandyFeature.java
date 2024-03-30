package net.zepalesque.redux.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.zepalesque.redux.misc.ReduxTags;

import java.util.HashMap;
import java.util.Map;

public class CottonCandyFeature extends Feature<CottonCandyFeature.Config> {
   public CottonCandyFeature(Codec<Config> p_65975_) {
      super(p_65975_);
   }

   @Override
   public boolean place(FeaturePlaceContext<Config> context) {
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
         toPlace.putIfAbsent(immutable1, context.config().pillar.getState(context.random(), immutable1));
      }
      // Place the candy part. Note that this is 7 blocks tall, with a diameter of 3 blocks.
      BlockPos candyOrigin = origin.above(height);
      for (int y = 0; y >= -6; y--) {
         boolean outer = y < -5 || y > -1;
         boolean center = y > -5 && y < -1;
         int radius = outer ? 2 : 3;
         for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
               boolean middlecorners = (x != -radius && x != radius) || (z != -radius && z != radius);
               boolean isDiamondIsh = Mth.abs(x) + Mth.abs(z) <= radius + 1;
               if (center && middlecorners || isDiamondIsh && !outer || outer && middlecorners) {
                  mutable.setWithOffset(candyOrigin, x, y, z);
                  BlockPos immutable2 = mutable.immutable();
                  toPlace.putIfAbsent(immutable2, context.config().candy.getState(context.random(), immutable2));
               }
            }
         }
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
         return level.isStateAtPosition(pos, state -> state.isAir() || state.is(ReduxTags.Blocks.COTTON_CANDY_CONSTRUCTION) || state.canBeReplaced());
      }
      return false;
   }

   public static class Config implements FeatureConfiguration {
      public static final Codec<Config> CODEC = RecordCodecBuilder.create((candyfloss) ->
              candyfloss.group(BlockStateProvider.CODEC.fieldOf("candyfloss").forGetter((config) -> config.candy),
                              BlockStateProvider.CODEC.fieldOf("pillar").forGetter((config) -> config.pillar),
                              IntProvider.CODEC.fieldOf("height").forGetter((config) -> config.height))
                      .apply(candyfloss, Config::new));
      public final BlockStateProvider candy;
      public final BlockStateProvider pillar;
      public final IntProvider height;


      public Config(BlockStateProvider candy, BlockStateProvider pillar, IntProvider height) {
         this.candy = candy;
         this.pillar = pillar;
         this.height = height;
      }
   }
}