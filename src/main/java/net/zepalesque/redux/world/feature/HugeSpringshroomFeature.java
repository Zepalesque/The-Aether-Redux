package net.zepalesque.redux.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.world.feature.config.HugeAetherMushroomFeatureConfiguration;

public class HugeSpringshroomFeature extends AbstractHugeAetherMushroomFeature {
   public HugeSpringshroomFeature(Codec<HugeAetherMushroomFeatureConfiguration> p_65975_) {
      super(p_65975_);
   }

   @Override
   protected void makeCap(LevelAccessor level, RandomSource random, BlockPos pos, int height, BlockPos.MutableBlockPos mutablePos, HugeAetherMushroomFeatureConfiguration config) {
      for(int heightIterator = height - 1; heightIterator <= height; ++heightIterator) {
         int currentSize = config.foliageRadius;

         for(int xOffset = -currentSize; xOffset <= currentSize; ++xOffset) {
            for(int zOffset = -currentSize; zOffset <= currentSize; ++zOffset) {
               boolean isNegX = xOffset == -currentSize;
               boolean isPosX = xOffset == currentSize;
               boolean isNegZ = zOffset == -currentSize;
               boolean isPosZ = zOffset == currentSize;
               boolean isSidesX = isNegX || isPosX;
               boolean isSidesZ = isNegZ || isPosZ;
               boolean isTop = heightIterator >= height;
               boolean isNotCorner = !isSidesX || !isSidesZ;
               boolean shouldPlaceCapBlock = (isTop && isNotCorner);
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
               boolean isNegX = xOffset == -currentSporeSize;
               boolean isPosX = xOffset == currentSporeSize;
               boolean isNegZ = zOffset == -currentSporeSize;
               boolean isPosZ = zOffset == currentSporeSize;
               boolean isValid = (isNegX || isPosX) != (isNegZ || isPosZ);
               boolean shouldPlaceSpores = isCorrectHeightLvl && isValid;
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

      int i = pRandom.nextInt(2) + 3;
      return i;
   }
   protected int getTreeRadiusForHeight(int p_65977_, int p_65978_, int p_65979_, int p_65980_) {
      int i = 0;
      if (p_65980_ < p_65978_ && p_65980_ >= p_65978_ - 1) {
         i = p_65979_;
      } else if (p_65980_ == p_65978_) {
         i = p_65979_;
      }

      return i;
   }
}