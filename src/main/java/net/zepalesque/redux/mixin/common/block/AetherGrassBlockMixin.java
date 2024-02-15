package net.zepalesque.redux.mixin.common.block;

import com.aetherteam.aether.block.natural.AetherGrassBlock;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
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

import java.util.Optional;

@Mixin(AetherGrassBlock.class)
public abstract class AetherGrassBlockMixin extends GrassBlockMixin {

    @WrapOperation(method = "performBonemeal", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/placement/PlacedFeature;place(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;)Z"))
    protected boolean placeGrass(PlacedFeature instance, WorldGenLevel level, ChunkGenerator generator, RandomSource random, BlockPos pos, Operation<Boolean> original) {

        Optional<Holder.Reference<PlacedFeature>> optional = level.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getHolder(VegetationPlacements.GRASS_BONEMEAL);
        if (instance.equals(optional.get().value())) {
            if (level.ensureCanWrite(pos)) {
                level.setBlock(pos, ReduxBlocks.SHORT_AETHER_GRASS.get().defaultBlockState(), 3);
                return true;
            } else {
                return false;
            }
        }
        return original.call(instance, level, generator, random, pos);
    }
}