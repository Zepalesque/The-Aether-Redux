package net.zepalesque.redux.mixin.common.world;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.zepalesque.redux.world.feature.ReduxFeatureRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(BiomeGenerationSettings.class)
public class BiomeGenerationSettingsMixin {
/*    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void setFlowers(Map<GenerationStep.Carving, HolderSet<ConfiguredWorldCarver<?>>> map, List<HolderSet<PlacedFeature>> list, CallbackInfo ci) {

        ((BiomeGenerationSettings) (Object) this).flowerFeatures = Suppliers.memoize(() -> list.stream().flatMap(HolderSet::stream).map(Holder::value).flatMap(PlacedFeature::getFeatures).filter((configuredFeature) ->
                configuredFeature.feature() == Feature.FLOWER || configuredFeature.feature() == ReduxFeatureRegistry.BIOME_BORDER_PLACEMENT_FLOWER.get() || configuredFeature.feature() == ReduxFeatureRegistry.SUPPORT_TEST_FLOWER.get() || configuredFeature.feature() == ReduxFeatureRegistry.BLOCK_TEST_FLOWER.get()).collect(ImmutableList.toImmutableList()));

    }*/
}
