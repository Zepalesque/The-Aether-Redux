package net.zepalesque.redux.mixin.common.block;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.natural.EnchantedAetherGrassBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.zepalesque.redux.block.ReduxBlocks;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(EnchantedAetherGrassBlock.class)
public class EnchantedAetherGrassBlockMixin extends GrassBlockMixin{

    @Override
    protected boolean placeGrass(PlacedFeature instance, WorldGenLevel pLevel, ChunkGenerator pGenerator, RandomSource pRandom, BlockPos pPos) {

        Optional<Holder.Reference<PlacedFeature>> optional = pLevel.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolder(VegetationPlacements.GRASS_BONEMEAL);
        if ((Object) this == AetherBlocks.AETHER_GRASS_BLOCK.get()) {
            if (instance.equals(optional.get().value())) {
                if (pLevel.ensureCanWrite(pPos)) {
                    pLevel.setBlock(pPos, ReduxBlocks.AETHER_GRASS.get().defaultBlockState(), 3);
                    return true;
                } else {
                    return false;
                }
            }
        } else if ((Object) this == AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get()) {
            if (instance.equals(optional.get().value())) {
                if (pLevel.ensureCanWrite(pPos)) {
                    pLevel.setBlock(pPos, ReduxBlocks.ENCHANTED_AETHER_GRASS.get().defaultBlockState(), 3);
                    return true;
                } else {
                    return false;
                }
            }
        } else if ((Object) this == ReduxBlocks.BLIGHTED_AETHER_GRASS_BLOCK.get()) {
            if (instance.equals(optional.get().value())) {
                if (pLevel.ensureCanWrite(pPos)) {
                    pLevel.setBlock(pPos, ReduxBlocks.ENCHANTED_AETHER_GRASS.get().defaultBlockState(), 3);
                    return true;
                } else {
                    return false;
                }
            }
        }
        return instance.place(pLevel, pGenerator, pRandom, pPos);
    }
}
