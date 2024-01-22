package net.zepalesque.redux.world.tree.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.zepalesque.redux.api.condition.AbstractCondition;
import net.zepalesque.redux.misc.ReduxTags;

public class EnchantedVineDecorator extends TreeDecorator {


   public static final Codec<EnchantedVineDecorator> CODEC = RecordCodecBuilder.create((vines) ->
           vines.group(Codec.floatRange(0.0F, 1.0F).fieldOf("probability_min").forGetter((config) -> config.probabilityMin),
                           Codec.floatRange(0.0F, 1.0F).fieldOf("probability_max").forGetter((config) -> config.probabilityMax),
                           BlockStateProvider.CODEC.fieldOf("plant_body_provider").forGetter((config) -> config.bodyBlock),
                           BlockStateProvider.CODEC.fieldOf("plant_head_provider").forGetter((config) -> config.headBlock),
                           IntProvider.codec(1,128).fieldOf("length_min").forGetter((config) -> config.lengthMin),
                           IntProvider.codec(1,128).fieldOf("length_max").forGetter((config) -> config.lengthMax),
                           AbstractCondition.CODEC.fieldOf("condition").forGetter((config) -> config.condition))
                   .apply(vines, EnchantedVineDecorator::new));
   private final float probabilityMin;
   private final float probabilityMax;
   private final BlockStateProvider bodyBlock;
   private final BlockStateProvider headBlock;
   private final IntProvider lengthMin;
   private final IntProvider lengthMax;
   private final AbstractCondition<?> condition;

   protected TreeDecoratorType<?> type() {
      return ReduxTreeDecorators.ENCHANTED_VINE_DECORATOR.get();
   }

   public EnchantedVineDecorator(float pProbabilityMin, float pProbabilityMax, BlockStateProvider pBodyBlock, BlockStateProvider pHeadBlock, IntProvider pLengthMin, IntProvider pLengthMax, AbstractCondition<?> condition) {
      this.probabilityMin = pProbabilityMin;
      this.probabilityMax = pProbabilityMax;
      this.bodyBlock = pBodyBlock;
      this.headBlock = pHeadBlock;
      this.lengthMin = pLengthMin;
      this.lengthMax = pLengthMax;
      this.condition = condition;
   }

   public void place(TreeDecorator.Context pContext) {
      if (this.condition.isConditionMet()) {
         RandomSource randomsource = pContext.random();
         float maxDistance = 0;
         BlockPos trunk = pContext.logs().get(0);
         for (BlockPos leafPos : pContext.leaves()) {
            float pyth = ((trunk.getX() - leafPos.getX()) * (trunk.getX() - leafPos.getX())) + ((trunk.getZ() - leafPos.getZ()) * (trunk.getZ() - leafPos.getZ()));
            float dist = Mth.sqrt(pyth);
            if (dist > maxDistance) {
               maxDistance = dist;
            }
         }
         for (BlockPos leafPos : pContext.leaves()) {
            float pyth = ((trunk.getX() - leafPos.getX()) * (trunk.getX() - leafPos.getX())) + ((trunk.getZ() - leafPos.getZ()) * (trunk.getZ() - leafPos.getZ()));
            float dist = Mth.sqrt(pyth);
            float interpolate = maxDistance != 0 ? (dist / maxDistance) : 1;
            float probability = Mth.lerp(interpolate, this.probabilityMin, this.probabilityMax);
            int length = Mth.ceil(Mth.lerp(interpolate, this.lengthMin.sample(pContext.random()), this.lengthMax.sample(pContext.random())));
            if (randomsource.nextFloat() < probability) {
               BlockPos blockpos = leafPos.below();
               if (pContext.isAir(blockpos)) {
                  this.addVine(blockpos, pContext, length);
               }
            }

         }
      }
   }

   private void addVine(BlockPos pPos, TreeDecorator.Context pContext, int length) {



      for (int i = 1; i <= length; i++)
      {
         BlockPos placement = pPos.offset(0, -(i - 1), 0);
         if (!pContext.isAir(placement) || pContext.level().isStateAtPosition(placement, (state) -> state.is(ReduxTags.Blocks.ENCHANTED_VINES_SKIP_PLACEMENT)))
         {
            break;
         }

         if (!pContext.isAir(placement.below()) || i >= length || pContext.level().isStateAtPosition(placement.below(), (state) -> state.is(ReduxTags.Blocks.ENCHANTED_VINES_SKIP_PLACEMENT)))
         {
            pContext.setBlock(placement, this.headBlock.getState(pContext.random(), pPos));
            break;
         } else {
            pContext.setBlock(placement, this.bodyBlock.getState(pContext.random(), pPos));
         }
      }

   }

   private void placeBlockAt(TreeDecorator.Context context, BlockPos pos, BlockState state) {
      if (TreeFeature.validTreePos(context.level(), pos) && !TreeFeature.validTreePos(context.level(), pos.below())) {
         context.setBlock(pos, state);
      }
   }
}