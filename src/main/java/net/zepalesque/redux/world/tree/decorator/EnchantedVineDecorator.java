package net.zepalesque.redux.world.tree.decorator;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.condition.AbstractCondition;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class EnchantedVineDecorator extends TreeDecorator {

   public static final Codec<EnchantedVineDecorator> BASE_CODEC = RecordCodecBuilder.create(builder ->
           builder.group(Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(config -> config.probability),
                           BlockStateProvider.CODEC.fieldOf("plant_body_provider").forGetter(config -> config.bodyBlock),
                           BlockStateProvider.CODEC.fieldOf("plant_head_provider").forGetter(config -> config.headBlock),
                           IntProvider.codec(1,128).fieldOf("length").forGetter(config -> config.length),
                           AbstractCondition.CODEC.optionalFieldOf("condition").forGetter(config -> config.condition))
                   .apply(builder, EnchantedVineDecorator::new));

   public static final Codec<EnchantedVineDecorator> COMPAT_CODEC = RecordCodecBuilder.create((vines) ->
           vines.group(Codec.floatRange(0.0F, 1.0F).fieldOf("probability_min").forGetter(config -> 0F),
                           Codec.floatRange(0.0F, 1.0F).fieldOf("probability_max").forGetter((config) -> 0F),
                           BlockStateProvider.CODEC.fieldOf("plant_body_provider").forGetter((config) -> config.bodyBlock),
                           BlockStateProvider.CODEC.fieldOf("plant_head_provider").forGetter((config) -> config.headBlock),
                           IntProvider.codec(1,128).fieldOf("length_min").forGetter((config) -> ConstantInt.ZERO),
                           IntProvider.codec(1,128).fieldOf("length_max").forGetter((config) -> ConstantInt.ZERO),
                           AbstractCondition.CODEC.optionalFieldOf("condition").forGetter((config) -> config.condition))
                   .apply(vines, (unused1, unused2, body, head, unused3, unused4, condition) -> forBackwardsCompat(body, head, condition)));
   
   // hopefully will work?
   public static final Codec<EnchantedVineDecorator> CODEC = Codec.either(BASE_CODEC, COMPAT_CODEC).xmap(either -> either.left().orElseGet(() -> either.right().get()), vine -> Either.left(vine));

   private final float probability;
   private final BlockStateProvider bodyBlock;
   private final BlockStateProvider headBlock;
   private final IntProvider length;
   private final Optional<AbstractCondition<?>> condition;


   static EnchantedVineDecorator forBackwardsCompat(BlockStateProvider body, BlockStateProvider head, Optional<AbstractCondition<?>> condition) {
      return new EnchantedVineDecorator(0.25F, body, head, UniformInt.of(1, 3), condition);
   }

   public EnchantedVineDecorator(float vineProbability, BlockStateProvider bodyBlock, BlockStateProvider headBlock, IntProvider vineLength, Optional<AbstractCondition<?>> condition) {
      this.probability = vineProbability;
      this.bodyBlock = bodyBlock;
      this.headBlock = headBlock;
      this.length = vineLength;
      this.condition = condition;
   }

   public void place(TreeDecorator.Context pContext) {
      Table<Integer, Integer, Integer> xzyLowestMap = HashBasedTable.create();
      List<BlockPos> l = pContext.leaves().clone();
      Collections.reverse(l);
      for (BlockPos leafPos : l) {
         int x = leafPos.getX();
         int y = leafPos.getY();
         int z = leafPos.getZ();
         try {
            if (!xzyLowestMap.contains(x, z)) {
               xzyLowestMap.put(x, z, y);
            } else if (y < xzyLowestMap.get(x, z)) {
               xzyLowestMap.put(x, z, y);
            }
         } catch (NullPointerException exception) {
            Redux.LOGGER.error("Caught error when trying to add leaf to table!", exception);
         }
      }
      RandomSource randomsource = pContext.random();
      for (Table.Cell<Integer, Integer, Integer> leafPos : xzyLowestMap.cellSet()) {
         BlockPos pos = new BlockPos(leafPos.getRowKey(), leafPos.getValue(), leafPos.getColumnKey());
         int length = this.length.sample(randomsource);
         if (randomsource.nextFloat() < probability) {
            BlockPos blockpos = pos.below();
            if (pContext.isAir(blockpos)) {
               this.addVine(blockpos, pContext, length);
            }
         }
      }
   }

   private void addVine(BlockPos pPos, TreeDecorator.Context pContext, int length) {
      for (int i = 1; i <= length; i++) {
         BlockPos placement = pPos.offset(0, 1 - i, 0);
         boolean notAirBelow = !pContext.isAir(placement.below());
         boolean maxLength = i >= length;
         if (notAirBelow || maxLength) {
            pContext.setBlock(placement, this.headBlock.getState(pContext.random(), pPos));
            break;
         } else {
            pContext.setBlock(placement, this.bodyBlock.getState(pContext.random(), pPos));
         }
      }
   }

   @Override
   protected TreeDecoratorType<?> type() {
      return ReduxTreeDecorators.ENCHANTED_VINES.get();
   }
}

