package net.zepalesque.redux.mixin.common.world;

import net.minecraft.world.level.biome.BiomeGenerationSettings;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BiomeGenerationSettings.class)
public class BiomeGenerationSettingsMixin {
/*    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void setFlowers(Map<GenerationStep.Carving, HolderSet<ConfiguredWorldCarver<?>>> map, List<HolderSet<PlacedFeature>> list, CallbackInfo ci) {

        ((BiomeGenerationSettings) (Object) this).flowerFeatures = Suppliers.memoize(() -> list.stream().flatMap(HolderSet::stream).map(Holder::value).flatMap(PlacedFeature::getFeatures).filter((configuredFeature) ->
                configuredFeature.feature() == Feature.FLOWER || configuredFeature.feature() == ReduxFeatureRegistry.BIOME_BORDER_PLACEMENT_FLOWER.get() || configuredFeature.feature() == ReduxFeatureRegistry.SUPPORT_TEST_FLOWER.get() || configuredFeature.feature() == ReduxFeatureRegistry.BLOCK_TEST_FLOWER.get()).collect(ImmutableList.toImmutableList()));

    }*/
}
