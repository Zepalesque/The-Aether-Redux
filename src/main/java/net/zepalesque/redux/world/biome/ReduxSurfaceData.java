package net.zepalesque.redux.world.biome;

import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.AetherFeatureStates;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.condition.Conditions;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.data.resource.biome.registry.ReduxBiomes;
import net.zepalesque.redux.world.biome.surfacerule.ConditionCondition;
import teamrazor.deepaether.init.DABlocks;

import java.util.function.Supplier;

public class ReduxSurfaceData {
    public static SurfaceRules.RuleSource makeRules() {
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.THE_BLIGHT), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.SWAMP, 0.4), SurfaceRules.state((getCoarseDirtBlock().get()).defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true))))),
//                SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.THE_BLIGHT), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.state((ReduxBlocks.BLIGHTED_AETHER_GRASS_BLOCK.get()).defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true)))),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.FROSTED_FORESTS), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.POWDER_SNOW,  0.35D, 0.45D), SurfaceRules.state(Blocks.POWDER_SNOW.defaultBlockState())))),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.FROSTED_FORESTS), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.ICE, 0.0, 0.4), SurfaceRules.state(Blocks.SNOW_BLOCK.defaultBlockState())))),
//                SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.GLACIAL_TAIGA), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.state((ReduxBlocks.FROSTED_AETHER_GRASS_BLOCK.get()).defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true)))),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.GLACIAL_TUNDRA), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.ICE, 0.0, 0.15), SurfaceRules.state(getCoarseDirtBlock().get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true))))),
//                SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.FROSTED_TUNDRA), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.state((ReduxBlocks.FROSTED_AETHER_GRASS_BLOCK.get()).defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true)))),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.GILDED_GROVES), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.ICE, 0.0, 0.2), SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.SWAMP, 0.3, 0.4), SurfaceRules.state(ReduxBlocks.GILDED_HOLYSTONE.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true)))))),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.GILDED_GROVES), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.ICE, 0.0, 0.2), SurfaceRules.state(AetherFeatureStates.HOLYSTONE)))),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.GILDED_GROVES), SurfaceRules.ifTrue(new ConditionCondition(Conditions.ENCHGRASS), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.state((AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get().defaultBlockState()))))),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.GILDED_GRASSLANDS), SurfaceRules.ifTrue(new ConditionCondition(Conditions.ENCHGRASS), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.state((AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get().defaultBlockState()))))),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.CLOUDCAPS), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.ICE, 0.0, 0.2), SurfaceRules.state(getCoarseDirtBlock().get().defaultBlockState())))),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.CLOUDCAPS), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.state((ReduxBlocks.AVELIUM.get()).defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true)))),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.QUICKSOIL_DUNES), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.state((AetherBlocks.QUICKSOIL.get()).defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true)))),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.QUICKSOIL_OASIS), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.state((AetherBlocks.QUICKSOIL.get()).defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true)))),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.SKYROOT_SHRUBLANDS), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.ICE, 0.0, 0.2), SurfaceRules.state(getCoarseDirtBlock().get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true)))))
                );
    }
    
    private static Supplier<Block> getCoarseDirtBlock() {
        return Redux.deepAetherCompat() ? DABlocks.AETHER_COARSE_DIRT : ReduxBlocks.COARSE_AETHER_DIRT;
    }
}
