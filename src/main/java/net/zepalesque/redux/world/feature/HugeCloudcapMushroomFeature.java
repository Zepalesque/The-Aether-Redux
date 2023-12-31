package net.zepalesque.redux.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.world.feature.config.HugeAetherMushroomFeatureConfiguration;

public class HugeCloudcapMushroomFeature extends AbstractHugeAetherMushroomFeature {
   public HugeCloudcapMushroomFeature(Codec<HugeAetherMushroomFeatureConfiguration> p_65975_) {
      super(p_65975_);
   }

   @Override
   protected void makeCap(LevelAccessor level, RandomSource random, BlockPos pos, int height, BlockPos.MutableBlockPos mutablePos, HugeAetherMushroomFeatureConfiguration config) {
      for(int heightIterator = height - 2; heightIterator <= height; ++heightIterator) {
         int currentSize = heightIterator < height ? config.foliageRadius : config.foliageRadius - 1;

         for(int xOffset = -currentSize; xOffset <= currentSize; ++xOffset) {
            for(int zOffset = -currentSize; zOffset <= currentSize; ++zOffset) {
               boolean isNegX = xOffset == -currentSize;
               boolean isPosX = xOffset == currentSize;
               boolean isNegZ = zOffset == -currentSize;
               boolean isPosZ = zOffset == currentSize;
               boolean isEdgesX = isNegX || isPosX;
               boolean isEdgesZ = isNegZ || isPosZ;
               boolean isTop = heightIterator >= height;
               boolean isEdgeNotCorner = isEdgesX != isEdgesZ;
               boolean shouldPlaceCapBlock = isTop || isEdgeNotCorner;
               if (shouldPlaceCapBlock) {
                  mutablePos.setWithOffset(pos, xOffset, heightIterator, zOffset);
                  if (!level.getBlockState(mutablePos).isSolidRender(level, mutablePos)) {
                     BlockState blockstate = config.capProvider.getState(random, pos);

                     this.setBlock(level, mutablePos, blockstate);
                  }
               }
            }
         }

         int currentSporeSize = config.foliageRadius - 1;
         boolean isCorrectHeightLvl = heightIterator == height - 1;

         for(int xOffset = -currentSporeSize; xOffset <= currentSporeSize; ++xOffset) {
            for (int zOffset = -currentSporeSize; zOffset <= currentSporeSize; ++zOffset) {
               boolean isCenter = xOffset == 0 && zOffset == 0;
               boolean shouldPlaceSpores = isCorrectHeightLvl && !isCenter;
               if (shouldPlaceSpores)
               {
                  mutablePos.setWithOffset(pos, xOffset, heightIterator, zOffset);
                  if (!level.getBlockState(mutablePos).isSolidRender(level, mutablePos)) {
                     BlockState blockstate = config.sporeProvider.getState(random, pos);
                     this.setBlock(level, mutablePos, blockstate);
                  }
               }

            }
         }


      }

   }

   protected int getTreeHeight(RandomSource pRandom) {
      int i = pRandom.nextInt(4) + 8;
      if (pRandom.nextInt(12) == 0) {
         i = Mth.floor(i * 1.5F);
      }

      return i;
   }

   protected int getTreeRadiusForHeight(int sorryIDK, int yStart, int radius, int y) {
      int i = 0;
      if (y < yStart && y >= yStart - 2) {
         i = radius;
      } else if (y == yStart) {
         i = radius;
      }

      return i;
   }
}