package net.zepalesque.redux.world.tree.root;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;

public record LegacyBlightwillowRootPlacement(HolderSet<Block> canGrowThrough, int maxRootWidth, int maxRootLength, float randomSkewChance) {
   public static final Codec<LegacyBlightwillowRootPlacement> CODEC = RecordCodecBuilder.create((p_225789_) -> {
      return p_225789_.group(RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("can_grow_through").forGetter((p_225808_) -> {
         return p_225808_.canGrowThrough;
      }), Codec.intRange(1, 12).fieldOf("max_root_width").forGetter((p_225797_) -> {
         return p_225797_.maxRootWidth;
      }), Codec.intRange(1, 64).fieldOf("max_root_length").forGetter((p_225794_) -> {
         return p_225794_.maxRootLength;
      }), Codec.floatRange(0.0F, 1.0F).fieldOf("random_skew_chance").forGetter((p_225791_) -> {
         return p_225791_.randomSkewChance;
      })).apply(p_225789_, LegacyBlightwillowRootPlacement::new);
   });
}