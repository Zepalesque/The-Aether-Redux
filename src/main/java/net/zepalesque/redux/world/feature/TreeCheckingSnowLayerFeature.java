package net.zepalesque.redux.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.zepalesque.redux.block.util.ReduxStates;

public class TreeCheckingSnowLayerFeature extends Feature<NoneFeatureConfiguration> {
   public TreeCheckingSnowLayerFeature(Codec<NoneFeatureConfiguration> p_66836_) {
      super(p_66836_);
   }

   /**
    * Places the given feature at the given location.
    * During world generation, features are provided with a 3x3 region of chunks, centered on the chunk being generated,
    * that they can safely generate into.
    * @param context A context object with a reference to the level and the position the feature is being placed at
    */
   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_160368_) {
      WorldGenLevel worldgenlevel = p_160368_.level();
      BlockPos blockpos = p_160368_.origin();
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();
      BlockPos.MutableBlockPos blockpos$mutableblockpos2 = new BlockPos.MutableBlockPos();
      BlockPos.MutableBlockPos blockpos$mutableblockpos3 = new BlockPos.MutableBlockPos();

      for(int i = 0; i < 16; ++i) {
         for(int j = 0; j < 16; ++j) {
            int k = blockpos.getX() + i;
            int l = blockpos.getZ() + j;
            int i1 = worldgenlevel.getHeight(Heightmap.Types.MOTION_BLOCKING, k, l);
            blockpos$mutableblockpos.set(k, i1, l);
            blockpos$mutableblockpos1.set(blockpos$mutableblockpos).move(Direction.DOWN, 1);
            if (worldgenlevel.getBlockState(blockpos$mutableblockpos1).is(BlockTags.LEAVES)) {
               int i2 = worldgenlevel.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, k, l);
               blockpos$mutableblockpos2.set(k, i2, l);
               blockpos$mutableblockpos3.set(blockpos$mutableblockpos2).move(Direction.DOWN, 1);
               freeze(worldgenlevel, blockpos$mutableblockpos2, blockpos$mutableblockpos3);
            }
            freeze(worldgenlevel, blockpos$mutableblockpos, blockpos$mutableblockpos1);
         }
      }

      return true;
   }

   private static void freeze(WorldGenLevel worldgenlevel, BlockPos pos, BlockPos below) {
      Biome biome = worldgenlevel.getBiome(pos).value();
      if (biome.shouldFreeze(worldgenlevel, below, false)) {
         worldgenlevel.setBlock(below, Blocks.ICE.defaultBlockState(), 2);
      }

      if (biome.shouldSnow(worldgenlevel, pos)) {
         worldgenlevel.setBlock(pos, Blocks.SNOW.defaultBlockState(), 2);
         BlockState blockstate = worldgenlevel.getBlockState(below);
         if (blockstate.hasProperty(SnowyDirtBlock.SNOWY)) {
            worldgenlevel.setBlock(below, blockstate.setValue(SnowyDirtBlock.SNOWY, Boolean.TRUE), 2);
         }
      }
   }
}