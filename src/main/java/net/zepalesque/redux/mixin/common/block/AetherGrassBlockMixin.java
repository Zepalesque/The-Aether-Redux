package net.zepalesque.redux.mixin.common.block;

import com.aetherteam.aether.block.natural.AetherGrassBlock;
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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(AetherGrassBlock.class)
public abstract class AetherGrassBlockMixin {

    //@Redirect(method = "performBonemeal", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/placement/PlacedFeature;place(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;)Z"))
    //protected boolean placeGrass(PlacedFeature instance, WorldGenLevel pLevel, ChunkGenerator pGenerator, RandomSource pRandom, BlockPos pPos) {
    //        Optional<Holder.Reference<PlacedFeature>> optional = pLevel.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolder(VegetationPlacements.GRASS_BONEMEAL);
    //        if (instance.equals(optional.get().value())) {
    //            if (pLevel.ensureCanWrite(pPos)) {
    //                pLevel.setBlock(pPos, ReduxBlocks.AETHER_SHORT_GRASS.get().defaultBlockState(), 3);
    //                return true;
    //            } else {
    //                return false;
     //           }
     //       }
    //        return instance.place(pLevel, pGenerator, pRandom, pPos);
   // }
}