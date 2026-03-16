package net.zepalesque.redux.world.feature.gen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class TreeAwareSnowLayerFeature extends Feature<NoneFeatureConfiguration> {
   public TreeAwareSnowLayerFeature(Codec<NoneFeatureConfiguration> codec) {
      super(codec);
   }

   /**
    * Places the given feature at the given location.
    * During world generation, features are provided with a 3x3 region of chunks, centered on the chunk being generated,
    * that they can safely generate into.
    * @param context A context object with a reference to the level and the position the feature is being placed at
    */
   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
	   var lvl = ctx.level();
	   var blockpos = ctx.origin();
	   var mut0 = new BlockPos.MutableBlockPos();
	   var mut1 = new BlockPos.MutableBlockPos();
	   var mut2 = new BlockPos.MutableBlockPos();
	   var mut3 = new BlockPos.MutableBlockPos();

      for(var inChunkX = 0; inChunkX < 16; ++inChunkX) {
         for(var inChunkZ = 0; inChunkZ < 16; ++inChunkZ) {
	         var x = blockpos.getX() + inChunkX;
	         var z = blockpos.getZ() + inChunkZ;
	         var height = lvl.getHeight(Heightmap.Types.MOTION_BLOCKING, x, z);
            mut0.set(x, height, z);
            mut1.set(mut0).move(Direction.DOWN, 1);
            if (lvl.getBlockState(mut1).is(BlockTags.LEAVES)) {
	            var leavesHeight = lvl.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
               mut2.set(x, leavesHeight, z);
               mut3.set(mut2).move(Direction.DOWN, 1);
               freeze(lvl, mut2, mut3);
            }
            freeze(lvl, mut0, mut1);
         }
      }

      return true;
   }

   private static void freeze(WorldGenLevel lvl, BlockPos pos, BlockPos below) {
	   var biome = lvl.getBiome(pos).value();
      if (biome.shouldFreeze(lvl, below, false)) {
         lvl.setBlock(below, Blocks.ICE.defaultBlockState(), 2);
      }

      if (biome.shouldSnow(lvl, pos)) {
         lvl.setBlock(pos, Blocks.SNOW.defaultBlockState(), 2);
	      var blockstate = lvl.getBlockState(below);
         if (blockstate.hasProperty(SnowyDirtBlock.SNOWY)) {
            lvl.setBlock(below, blockstate.setValue(SnowyDirtBlock.SNOWY, Boolean.TRUE), 2);
         }
      }
   }
}