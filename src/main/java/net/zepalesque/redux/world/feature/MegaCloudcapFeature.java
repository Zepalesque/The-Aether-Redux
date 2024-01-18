package net.zepalesque.redux.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.world.feature.config.MegaCloudcapFeatureConfiguration;
import org.jetbrains.annotations.NotNull;

public class MegaCloudcapFeature extends Feature<MegaCloudcapFeatureConfiguration> {
   public MegaCloudcapFeature(Codec<MegaCloudcapFeatureConfiguration> pCodec) {
      super(pCodec);
   }

   protected void placeTrunk(LevelAccessor pLevel, RandomSource pRandom, BlockPos pPos, MegaCloudcapFeatureConfiguration pConfig, int pMaxHeight, BlockPos.MutableBlockPos pMutablePos) {
      for (int xOffset = 0; xOffset <= 1; xOffset++) {
         for (int zOffset = 0; zOffset <= 1; zOffset++) {
            for (int i = 0; i < pMaxHeight; ++i) {
               pMutablePos.setWithOffset(pPos, xOffset, i, zOffset);
               if (!pLevel.getBlockState(pMutablePos).isSolidRender(pLevel, pMutablePos)) {
                  this.setBlock(pLevel, pMutablePos, pConfig.stemProvider.getState(pRandom, pPos));
               }
            }
         }
      }

   }

   protected int getTreeHeight(RandomSource pRandom) {
      return pRandom.nextInt(5) + 18;
   }


   protected void makeBranch(Direction d, boolean mediumNotSmall, LevelAccessor level, RandomSource random, BlockPos pos, int height, BlockPos.MutableBlockPos mutablePos, MegaCloudcapFeatureConfiguration config) {

      if (d.getAxis() == Direction.Axis.Y)
      {
         Redux.LOGGER.warn("Attempted to place y-axis facing branch on Giant Cloudcap! Skipping branch...");
         return;
      }

      int blocksOut = mediumNotSmall ? 3 : 2;
      boolean goesOutToNegative = d.getStepX() == -1 || d.getStepZ() == -1;
      Direction.Axis axis = d.getAxis();

      for (int offset = 1; offset <= blocksOut; offset++)
      {
         int trueOffset = goesOutToNegative ? -offset : offset;
         mutablePos.setWithOffset(pos, axis == Direction.Axis.X ? trueOffset : 0, height, axis == Direction.Axis.Z ? trueOffset : 0);
         if (!level.getBlockState(mutablePos).isSolidRender(level, mutablePos)) {
            BlockState blockstate = offset == blocksOut ? config.hyphaeProvider.getState(random, pos) : config.stemProvider.getState(random, pos);
            if (blockstate.hasProperty(BlockStateProperties.AXIS))
            {
               this.setBlock(level, mutablePos, blockstate.setValue(BlockStateProperties.AXIS, axis));
            } else {
               this.setBlock(level, mutablePos, blockstate);
            }
         }
      }
      int branchEndHeight = mediumNotSmall ? random.nextInt(1) + 3 : 3;

      for (int endHeightIterator = 1; endHeightIterator <= branchEndHeight + 1; endHeightIterator++)
      {
         boolean shouldPlaceStem = endHeightIterator <= branchEndHeight;
         int offsetForEnd = blocksOut + 1;
         int trueOffsetForEnd = goesOutToNegative ? -offsetForEnd : offsetForEnd;
         mutablePos.setWithOffset(pos, axis == Direction.Axis.X ? trueOffsetForEnd : 0, endHeightIterator + height, axis == Direction.Axis.Z ? trueOffsetForEnd : 0);
         if (shouldPlaceStem) {
            if (!level.getBlockState(mutablePos).isSolidRender(level, mutablePos)) {
               BlockState blockstate = endHeightIterator == 1 ? config.hyphaeProvider.getState(random, pos) : config.stemProvider.getState(random, pos);
               this.setBlock(level, mutablePos, blockstate);
            }
         } else {
            if (mediumNotSmall) {
               makeMediumCap(level, random, mutablePos.immutable(), 0, mutablePos, config);
            } else {
               makeSmallCap(level, random, mutablePos.immutable(), 0, mutablePos, config);
            }
         }
      }

   }

   protected boolean isValidPosition(@NotNull LevelAccessor pLevel, BlockPos pPos, int pMaxHeight, BlockPos.MutableBlockPos pMutablePos, MegaCloudcapFeatureConfiguration pConfig) {
      int i = pPos.getY();
      if (i >= pLevel.getMinBuildHeight() + 1 && i + pMaxHeight + 1 < pLevel.getMaxBuildHeight()) {
         boolean allDirt = true;
         for (int x1 = 0; x1 <= 1; x1++) {
            for (int z1 = 0; z1 <= 1; z1++) {
               BlockState blockstate = pLevel.getBlockState(pPos.offset(x1, -1, z1));
               if (!isDirt(blockstate)) {
                  allDirt = false;
               }
            }
         }
         if (!allDirt)
         {
            return false;
         }
         else {
            for(int heightIterator = 0; heightIterator <= pMaxHeight; ++heightIterator) {
               int boundsThickness = heightIterator <= pMaxHeight - 5 ? 0 : 6;

               for(int l = -boundsThickness; l <= boundsThickness + 1; ++l) {
                  for(int i1 = -boundsThickness; i1 <= boundsThickness + 1; ++i1) {
                     BlockState blockstate1 = pLevel.getBlockState(pMutablePos.setWithOffset(pPos, l, heightIterator, i1));
                     if (!isReplaceableBlock(blockstate1) && !blockstate1.is(ReduxBlocks.TALL_CLOUDCAP.get()) && (!blockstate1.isAir() && !blockstate1.is(BlockTags.LEAVES))) {
                        return false;
                     }
                  }
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }
   public static boolean isReplaceableBlock(BlockState state) {
      return state.canBeReplaced();
   }



   /**
    * Places the given feature at the given location.
    * During world generation, features are provided with a 3x3 region of chunks, centered on the chunk being generated,
    * that they can safely generate into.
    * @param pContext A context object with a reference to the level and the position the feature is being placed at
    */
   public boolean place(FeaturePlaceContext<MegaCloudcapFeatureConfiguration> pContext) {
      WorldGenLevel worldgenlevel = pContext.level();
      BlockPos blockpos = pContext.origin();
      RandomSource randomsource = pContext.random();
      MegaCloudcapFeatureConfiguration hugemushroomfeatureconfiguration = pContext.config();
      int height = this.getTreeHeight(randomsource);

      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      if (!this.isValidPosition(worldgenlevel, blockpos, height, blockpos$mutableblockpos, hugemushroomfeatureconfiguration)) {
         return false;
      } else {
         int branchCount = height <= 18 ? randomsource.nextInt(2) : height <= 20 ? randomsource.nextInt(3) : randomsource.nextInt(4);
         Direction firstDirection = null;
         Direction thirdDirection = null;
         for (int i = 1; i <= branchCount; i++) {
            int branchHeight = randomsource.nextInt(height - 17) + 3;
            if (i == 1) {
               firstDirection = Direction.Plane.HORIZONTAL.getRandomDirection(randomsource);
               BlockPos pos = firstDirection.getAxisDirection() == Direction.AxisDirection.NEGATIVE ? blockpos : blockpos.relative(firstDirection);
               BlockPos pos1 = randomsource.nextBoolean() ? pos.relative(getBranchMoveDirection(firstDirection)) : pos;
               this.makeBranch(firstDirection, randomsource.nextBoolean(), worldgenlevel, randomsource, pos1, branchHeight, blockpos$mutableblockpos, hugemushroomfeatureconfiguration);
            }
            if (i == 2) {
               Direction d2 = firstDirection.getOpposite();
               BlockPos pos = d2.getAxisDirection() == Direction.AxisDirection.NEGATIVE ? blockpos : blockpos.relative(d2);
               BlockPos pos1 = randomsource.nextBoolean() ? pos.relative(getBranchMoveDirection(d2)) : pos;
               this.makeBranch(d2, randomsource.nextBoolean(), worldgenlevel, randomsource, pos1, branchHeight, blockpos$mutableblockpos, hugemushroomfeatureconfiguration);
            }
            if (i == 3) {
               Direction.Axis thirdAndFourthAxis = firstDirection.getAxis() == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
               thirdDirection = thirdAndFourthAxis == Direction.Axis.Z ? (randomsource.nextBoolean() ? Direction.NORTH : Direction.SOUTH) : (randomsource.nextBoolean() ? Direction.EAST : Direction.WEST);
               BlockPos pos = thirdDirection.getAxisDirection() == Direction.AxisDirection.NEGATIVE ? blockpos : blockpos.relative(thirdDirection);
               BlockPos pos1 = randomsource.nextBoolean() ? pos.relative(getBranchMoveDirection(thirdDirection)) : pos;
               this.makeBranch(thirdDirection, randomsource.nextBoolean(), worldgenlevel, randomsource, pos1, branchHeight, blockpos$mutableblockpos, hugemushroomfeatureconfiguration);
            }
            if (i == 4) {
               Direction d4 = thirdDirection.getOpposite();
               BlockPos pos = d4.getAxisDirection() == Direction.AxisDirection.NEGATIVE ? blockpos : blockpos.relative(d4);
               BlockPos pos1 = randomsource.nextBoolean() ? pos.relative(getBranchMoveDirection(d4)) : pos;
               this.makeBranch(d4, randomsource.nextBoolean(), worldgenlevel, randomsource, pos1, branchHeight, blockpos$mutableblockpos, hugemushroomfeatureconfiguration);
            }
         }
         this.makeCap(worldgenlevel, randomsource, blockpos, height, blockpos$mutableblockpos, hugemushroomfeatureconfiguration);
         this.placeTrunk(worldgenlevel, randomsource, blockpos, hugemushroomfeatureconfiguration, height, blockpos$mutableblockpos);
         return true;
      }
   }

   protected void makeCap(LevelAccessor level, RandomSource random, BlockPos pos, int height, BlockPos.MutableBlockPos mutablePos, MegaCloudcapFeatureConfiguration config) {
      int radius = 6;

      for(int yOffset = height; yOffset >= height - 7; --yOffset) {
         for (int xOffset = -radius; xOffset <= radius + 1; ++xOffset) {
            for (int zOffset = -radius; zOffset <= radius + 1; ++zOffset) {
               int trueX = xOffset <= 0 ? xOffset : xOffset - 1;
               int trueZ = zOffset <= 0 ? zOffset : zOffset - 1;

               float pyth = (trueX * trueX) + (trueZ * trueZ);
               float dist = Mth.sqrt(pyth);
               boolean shouldSkipForStem = trueX == 0 && trueZ == 0 && yOffset <= height - 5;
               boolean underTopCurve = yOffset - height < (Mth.cos((dist * Mth.PI) / 5.5F) * 3F) - 2.75F;
               boolean aboveBottomCurve = yOffset - height > (Mth.cos((dist * Mth.PI) / 4.5F) * 2.5F) - 4.875F;
               boolean underBottomCurve = !aboveBottomCurve;
               boolean aboveParabola = yOffset - height > (-((dist * dist) / 10F)) - 5.75F;
               boolean aboveEdgeParabola =  yOffset - height > (((dist * dist * Mth.PI) / 25F)) - 7F;
               boolean inRange = dist < 7F;
               boolean inSporeRange = dist < 4F;

               boolean validForCap = underTopCurve && aboveBottomCurve && inRange;
               boolean validForSpores = underBottomCurve && aboveParabola && aboveEdgeParabola && inSporeRange;

               mutablePos.setWithOffset(pos, xOffset, yOffset, zOffset);
               if (validForCap) {
                  if (!level.getBlockState(mutablePos).isSolidRender(level, mutablePos)) {
                     BlockState blockstate = config.capProvider.getState(random, pos);
                     this.setBlock(level, mutablePos, blockstate);
                  }
               } else if (validForSpores) {
                  if (!level.getBlockState(mutablePos).isSolidRender(level, mutablePos)) {
                     BlockState blockstate = config.sporeProvider.getState(random, pos);
                     this.setBlock(level, mutablePos, blockstate);
                  }
               }

            }
         }
      }
   }
   protected void makeMediumCap(LevelAccessor level, RandomSource random, BlockPos pos, int height, BlockPos.MutableBlockPos mutablePos, MegaCloudcapFeatureConfiguration config) {
      int radius = 2;
      for(int heightIterator = height - 2; heightIterator <= height; ++heightIterator) {
         int currentSize = heightIterator < height ? radius : radius - 1;

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


         int currentSporeSize = radius - 1;
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
   protected void makeSmallCap(LevelAccessor level, RandomSource random, BlockPos pos, int height, BlockPos.MutableBlockPos mutablePos, MegaCloudcapFeatureConfiguration config) {
      int radius = 1;
      for(int heightIterator = height; heightIterator <= height + 1; ++heightIterator) {
         int currentSize = radius;

         for(int xOffset = -currentSize; xOffset <= currentSize; ++xOffset) {
            for(int zOffset = -currentSize; zOffset <= currentSize; ++zOffset) {

               if (heightIterator >= height + 1) {
                  boolean isNegX = xOffset == -currentSize;
                  boolean isPosX = xOffset == currentSize;
                  boolean isNegZ = zOffset == -currentSize;
                  boolean isPosZ = zOffset == currentSize;
                  boolean isEdgesX = isNegX || isPosX;
                  boolean isEdgesZ = isNegZ || isPosZ;
                  boolean isValidForTop = !isEdgesX || !isEdgesZ;
                  if (isValidForTop) {
                     mutablePos.setWithOffset(pos, xOffset, heightIterator, zOffset);
                     if (!level.getBlockState(mutablePos).isSolidRender(level, mutablePos)) {
                        BlockState blockstate = config.capProvider.getState(random, pos);

                        this.setBlock(level, mutablePos, blockstate);
                     }
                  }
               }
               else {

                  boolean isCenter = xOffset == 0 && zOffset == 0;
                  boolean placeCap = !isCenter;
                  if (placeCap) {
                     mutablePos.setWithOffset(pos, xOffset, heightIterator, zOffset);
                     if (!level.getBlockState(mutablePos).isSolidRender(level, mutablePos)) {
                        BlockState blockstate = config.capProvider.getState(random, pos);

                        this.setBlock(level, mutablePos, blockstate);
                     }
                  }
               }
            }
         }

         boolean isCorrectHeightLvl = heightIterator == height;
         if (isCorrectHeightLvl) {
            mutablePos.setWithOffset(pos, 0, heightIterator, 0);
            if (!level.getBlockState(mutablePos).isSolidRender(level, mutablePos)) {
               BlockState blockstate = config.sporeProvider.getState(random, pos);
               this.setBlock(level, mutablePos, blockstate);
            }

         }
      }

   }

   private static Direction getBranchMoveDirection(Direction facing) {
      Direction d = facing == Direction.NORTH || facing == Direction.SOUTH ? Direction.EAST : facing == Direction.EAST || facing == Direction.WEST ? Direction.SOUTH : null;
      if (d == null)
      {
         throw new IllegalArgumentException("FACING value for branch move direction should be in the horizontal plane!");
      }
      return d;
   }

   public static class MushroomBranch {

      public final boolean medium;
      public final Direction direction;

      public MushroomBranch(boolean pMedium, Direction pDirection) {
         this.medium = pMedium;
         this.direction = pDirection;
      }
   }

   public static class LargeCapGenerator {

      public static void layer1(MegaCloudcapFeature cloudcap, LevelAccessor level, RandomSource random, BlockPos pos, int height, BlockPos.MutableBlockPos mutablePos, MegaCloudcapFeatureConfiguration config) {
         int currentSize = 1;

         for (int xOffset = -currentSize; xOffset <= currentSize + 1; ++xOffset) {
            for (int zOffset = -currentSize; zOffset <= currentSize + 1; ++zOffset) {
               int trueX = xOffset <= 0 ? xOffset : xOffset - 1;
               int trueZ = zOffset <= 0 ? zOffset : zOffset - 1;
               mutablePos.setWithOffset(pos, xOffset, height, zOffset);
               if (!level.getBlockState(mutablePos).isSolidRender(level, mutablePos)) {
                  BlockState blockstate = config.capProvider.getState(random, pos);
                  cloudcap.setBlock(level, mutablePos, blockstate);
               }

            }
         }
      }
      public static void layer2(MegaCloudcapFeature cloudcap, LevelAccessor level, RandomSource random, BlockPos pos, int height, BlockPos.MutableBlockPos mutablePos, MegaCloudcapFeatureConfiguration config) {
         int currentSize = 2;

         for (int xOffset = -currentSize; xOffset <= currentSize + 1; ++xOffset) {
            for (int zOffset = -currentSize; zOffset <= currentSize + 1; ++zOffset) {
               int trueX = xOffset <= 0 ? xOffset : xOffset - 1;
               int trueZ = zOffset <= 0 ? zOffset : zOffset - 1;
               boolean isNegX = trueX == -currentSize;
               boolean isPosX = trueX == currentSize;
               boolean isNegZ = trueZ == -currentSize;
               boolean isPosZ = trueZ == currentSize;
               boolean isEdgesX = isNegX || isPosX;
               boolean isEdgesZ = isNegZ || isPosZ;
               boolean isEdgeNotCorner = isEdgesX != isEdgesZ;
               boolean isInner = (trueX >= -currentSize + 1 && trueX <= currentSize - 1) && (trueZ >= -currentSize + 1 && trueZ <= currentSize - 1);
               boolean isValidForSpores = isInner && trueX != 0 && trueZ != 0;
               mutablePos.setWithOffset(pos, xOffset, height, zOffset);
               if (isEdgeNotCorner) {
                  if (!level.getBlockState(mutablePos).isSolidRender(level, mutablePos)) {
                     BlockState blockstate = config.capProvider.getState(random, pos);
                     cloudcap.setBlock(level, mutablePos, blockstate);
                  }
               } else if (isValidForSpores) {
                  if (!level.getBlockState(mutablePos).isSolidRender(level, mutablePos)) {
                     BlockState blockstate = config.sporeProvider.getState(random, pos);
                     cloudcap.setBlock(level, mutablePos, blockstate);
                  }
               }

            }
         }
      }


      public static void layer3(MegaCloudcapFeature cloudcap, LevelAccessor level, RandomSource random, BlockPos pos, int height, BlockPos.MutableBlockPos mutablePos, MegaCloudcapFeatureConfiguration config) {
         int currentSize = 3;

         for (int xOffset = -currentSize; xOffset <= currentSize + 1; ++xOffset) {
            for (int zOffset = -currentSize; zOffset <= currentSize + 1; ++zOffset) {
               int trueX = xOffset <= 0 ? xOffset : xOffset - 1;
               int trueZ = zOffset <= 0 ? zOffset : zOffset - 1;
               boolean xNegInner = trueX == -currentSize + 1;
               boolean zNegInner = trueZ == -currentSize + 1;
               boolean xPosInner = trueX == currentSize - 1;
               boolean zPosInner = trueX == currentSize - 1;
               boolean xNeg = trueX == -currentSize;
               boolean zNeg = trueZ == -currentSize;
               boolean xPos = trueX == currentSize;
               boolean zPos = trueZ == currentSize;
               boolean isInInnerRange = (trueX >= -2 && trueX <= 2) && (trueZ >= -2 && trueZ <= 2);
               boolean isInnerSquareRing = (xNegInner || xPosInner) || (zNegInner || zPosInner);
               boolean isOuterSquareRing = (xNeg || xPos) || (zNeg || zPos);
               boolean isCenterCross = (trueX == 0) || (trueZ == 0);
               boolean isCenter = (trueX == 0) &&  (trueZ == 0);
               boolean isValidForCap = (isInnerSquareRing && !isCenterCross && isInInnerRange) || (isOuterSquareRing && isCenterCross);
               boolean inSporeCrossRange = ((trueX >= -2 && trueX <= 2) && (trueZ >= -2 && trueZ <= 2));
               boolean isValidForSpores = (inSporeCrossRange && isCenterCross && !isCenter) || ((trueX == 1 || trueX == -1) && (trueZ == 1 || trueZ == -1));
               mutablePos.setWithOffset(pos, xOffset, height, zOffset);
               if (isValidForCap) {
                  if (!level.getBlockState(mutablePos).isSolidRender(level, mutablePos)) {
                     BlockState blockstate = config.capProvider.getState(random, pos);
                     cloudcap.setBlock(level, mutablePos, blockstate);
                  }
               } else if (isValidForSpores) {
                  if (!level.getBlockState(mutablePos).isSolidRender(level, mutablePos)) {
                     BlockState blockstate = config.sporeProvider.getState(random, pos);
                     cloudcap.setBlock(level, mutablePos, blockstate);
                  }
               }

            }
         }
      }
      public static void layer4(MegaCloudcapFeature cloudcap, LevelAccessor level, RandomSource random, BlockPos pos, int height, BlockPos.MutableBlockPos mutablePos, MegaCloudcapFeatureConfiguration config) {
         int currentSize = 3;
         for (int xOffset = -currentSize; xOffset <= currentSize + 1; ++xOffset) {
            for (int zOffset = -currentSize; zOffset <= currentSize + 1; ++zOffset) {
               int trueX = xOffset <= 0 ? xOffset : xOffset - 1;
               int trueZ = zOffset <= 0 ? zOffset : zOffset - 1;
               boolean inSporeRange = ((trueX >= -2 && trueX <= 2) && (trueZ >= -2 && trueZ <= 2));
               boolean isNegXInner = trueX == -currentSize + 1;
               boolean isPosXInner = trueX == currentSize - 1;
               boolean isNegZInner = trueZ == -currentSize + 1;
               boolean isPosZInner = trueZ == currentSize - 1;
               boolean isInnerSidesX = isNegXInner || isPosXInner;
               boolean isInnerSidesZ = isNegZInner || isPosZInner;
               boolean isNotInnerCorner = !isInnerSidesX || !isInnerSidesZ;
               boolean isCenter = (trueX == 0) &&  (trueZ == 0);

               boolean isValidForSpores = inSporeRange && isNotInnerCorner && !isCenter;

               boolean isNegX = trueX == -currentSize;
               boolean isPosX = trueX == currentSize;
               boolean isNegZ = trueZ == -currentSize;
               boolean isPosZ = trueZ == currentSize;
               boolean isSidesX = isNegX || isPosX;
               boolean isSidesZ = isNegZ || isPosZ;
               boolean isOuterRing = isSidesX || isSidesZ;
               boolean isInOuterRingCross = (trueX >= -1 && trueX <= 1) ||  (trueZ >= -1 && trueZ <= 1);
               boolean isRingCorners = inSporeRange && !isValidForSpores;
               boolean isValidForCap = ((isOuterRing && isInOuterRingCross) || isRingCorners) && !isCenter;
               mutablePos.setWithOffset(pos, xOffset, height, zOffset);
               if (isValidForCap) {
                  if (!level.getBlockState(mutablePos).isSolidRender(level, mutablePos)) {
                     BlockState blockstate = config.capProvider.getState(random, pos);
                     cloudcap.setBlock(level, mutablePos, blockstate);
                  }
               } else if (isValidForSpores) {
                  if (!level.getBlockState(mutablePos).isSolidRender(level, mutablePos)) {
                     BlockState blockstate = config.sporeProvider.getState(random, pos);
                     cloudcap.setBlock(level, mutablePos, blockstate);
                  }
               }
            }
         }
      }
 public static void layer5(MegaCloudcapFeature cloudcap, LevelAccessor level, RandomSource random, BlockPos pos, int height, BlockPos.MutableBlockPos mutablePos, MegaCloudcapFeatureConfiguration config) {
         int currentSize = 3;
         for (int xOffset = -currentSize - 1; xOffset <= currentSize + 2; ++xOffset) {
            for (int zOffset = -currentSize - 1; zOffset <= currentSize + 2; ++zOffset) {
               int trueX = xOffset <= 0 ? xOffset : xOffset - 1;
               int trueZ = zOffset <= 0 ? zOffset : zOffset - 1;
               boolean inSporeRange = ((xOffset >= -2 && xOffset <= 2) && (zOffset >= -2 && zOffset <= 2));
               boolean isNegXInner = trueX == -currentSize + 1;
               boolean isPosXInner = trueX == currentSize - 1;
               boolean isNegZInner = trueZ == -currentSize + 1;
               boolean isPosZInner = trueZ == currentSize - 1;
               boolean isInnerSidesX = isNegXInner || isPosXInner;
               boolean isInnerSidesZ = isNegZInner || isPosZInner;
               boolean isNotInnerCorner = !isInnerSidesX || !isInnerSidesZ;
               boolean isCenter = (trueX == 0) &&  (trueZ == 0);

               boolean isValidForSpores = inSporeRange && isNotInnerCorner && !isCenter;
               boolean isCenterCross = trueX == 0 || trueZ == 0;
               boolean isNegX = trueX == -currentSize;
               boolean isPosX = trueX == currentSize;
               boolean isNegZ = trueZ == -currentSize;
               boolean isPosZ = trueZ == currentSize;
               boolean isSidesX = isNegX || isPosX;
               boolean isSidesZ = isNegZ || isPosZ;
               boolean isOuterRing = isSidesX || isSidesZ;
               boolean isNegXExtra = trueX == -currentSize - 1;
               boolean isPosXExtra = trueX == currentSize + 1;
               boolean isNegZExtra = trueZ == -currentSize - 1;
               boolean isPosZExtra = trueZ == currentSize + 1;
               boolean isSidesXExtra = isNegXExtra || isPosXExtra;
               boolean isSidesZExtra = isNegZExtra || isPosZExtra;
               boolean isOuterRingExtra = isSidesXExtra || isSidesZExtra;
               boolean isOuterExtraBlobs = isCenterCross && isOuterRingExtra;
               boolean isInOuterRingCross = (trueX >= -1 && trueX <= 1) ||  (trueZ >= -1 && trueZ <= 1);
               boolean isRingCorners = inSporeRange && !isValidForSpores;
               boolean isValidForCap = (((isOuterRing && isInOuterRingCross) || isRingCorners) && !isCenter) || isOuterExtraBlobs;
               mutablePos.setWithOffset(pos, xOffset, height, zOffset);
               if (isValidForCap) {
                  if (!level.getBlockState(mutablePos).isSolidRender(level, mutablePos)) {
                     BlockState blockstate = config.capProvider.getState(random, pos);
                     cloudcap.setBlock(level, mutablePos, blockstate);
                  }
               } else if (isValidForSpores) {
                  if (!level.getBlockState(mutablePos).isSolidRender(level, mutablePos)) {
                     BlockState blockstate = config.sporeProvider.getState(random, pos);
                     cloudcap.setBlock(level, mutablePos, blockstate);
                  }
               }
            }
         }
      }

      public static void layer6(MegaCloudcapFeature cloudcap, LevelAccessor level, RandomSource random, BlockPos pos, int height, BlockPos.MutableBlockPos mutablePos, MegaCloudcapFeatureConfiguration config) {
         int currentSize = 4;
         for (int xOffset = -currentSize; xOffset <= currentSize + 1; ++xOffset) {
            for (int zOffset = -currentSize; zOffset <= currentSize + 1; ++zOffset) {
               int trueX = xOffset <= 0 ? xOffset : xOffset - 1;
               int trueZ = zOffset <= 0 ? zOffset : zOffset - 1;
               boolean isInOuterRingCross = (trueX >= -1 && trueX <= 1) ||  (trueZ >= -1 && trueZ <= 1);
               boolean isOuterRingEdges = (trueX >= 3 || trueX <= -3) || (trueZ >= 3 || trueZ <= -3);
               boolean isOuterRing3x2Boxes = isInOuterRingCross && isOuterRingEdges;
               boolean isInnerExclusionXEdge = (trueX == 3 || trueX == -3);
               boolean isInnerExclusionZEdge = (trueZ == 3 || trueZ == -3);
               boolean isInnerExcludedCorners = isInnerExclusionXEdge && isInnerExclusionZEdge;
               boolean isInnerBigCorners = ((trueX >= 2 && trueX <= 3) || (trueX >= -3 && trueX <= -2)) && ((trueZ >= 2 && trueZ <= 3) || (trueZ >= -3 && trueZ <= -2));
               boolean isInnerTriBlocks = isInnerBigCorners && !isInnerExcludedCorners;
               boolean isValidForCap = isInnerTriBlocks || isOuterRing3x2Boxes;
               mutablePos.setWithOffset(pos, xOffset, height, zOffset);
               if (isValidForCap) {
                  if (!level.getBlockState(mutablePos).isSolidRender(level, mutablePos)) {
                     BlockState blockstate = config.capProvider.getState(random, pos);
                     cloudcap.setBlock(level, mutablePos, blockstate);
                  }
               }
            }
         }


      }

   }
}