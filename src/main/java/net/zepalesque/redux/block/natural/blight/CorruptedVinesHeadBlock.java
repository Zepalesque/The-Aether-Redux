package net.zepalesque.redux.block.natural.blight;

import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class CorruptedVinesHeadBlock extends GrowingPlantHeadBlock {
   public static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D);
   private final Supplier<? extends GrowingPlantBodyBlock> body;

   public CorruptedVinesHeadBlock(BlockBehaviour.Properties properties, Supplier<? extends GrowingPlantBodyBlock> body) {
      super(properties, Direction.UP, SHAPE, false, 0.1D);
      this.body = body;
   }


   protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
      double d0 = 1.0D;

      int i;
      for(i = 0; random.nextDouble() < d0; ++i) {
         d0 *= 0.826D;
      }

      return i;
   }

   protected Block getBodyBlock() {
      return body.get();
   }

   protected boolean canGrowInto(BlockState state) {
      return state.isAir();
   }

}