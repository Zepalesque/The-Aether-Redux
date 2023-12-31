package net.zepalesque.redux.world.tree.root;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;

public record BlightwillowRootConfig(HolderSet<Block> canGrowThrough, int maxRootLength) {
   public static final Codec<BlightwillowRootConfig> CODEC = RecordCodecBuilder.create((p_225789_) -> {
      return p_225789_.group(RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("can_grow_through").forGetter((p_225808_) -> {
         return p_225808_.canGrowThrough;
      }), Codec.intRange(1, 64).fieldOf("max_root_length").forGetter((p_225794_) -> {
         return p_225794_.maxRootLength;
      })).apply(p_225789_, BlightwillowRootConfig::new);
   });
}