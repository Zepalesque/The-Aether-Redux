package net.zepalesque.redux.world.feature;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.world.feature.config.*;

public class ReduxFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Redux.MODID);

    public static RegistryObject<Feature<PredicateStateConfig>> TEST_BELOW_BLOCK = FEATURES.register("test_below_block", () -> new TestBelowBlockFeature(PredicateStateConfig.CODEC));
    public static RegistryObject<Feature<PredicateStateConfig>> TEST_AT_BLOCK = FEATURES.register("test_at_block", () -> new TestAtBlockFeature(PredicateStateConfig.CODEC));
    public static RegistryObject<Feature<PatchRockConfig>> PATCH_ROCK = FEATURES.register("patch_rock", () -> new PatchRockFeature(PatchRockConfig.CODEC));
    public static RegistryObject<Feature<CloudcapFeature.CloudcapConfig>> CLOUDCAP = FEATURES.register("cloudcap", () -> new CloudcapFeature(CloudcapFeature.CloudcapConfig.CODEC));
    public static RegistryObject<Feature<SurfaceRuleLakeConfig>> SURFACE_RULE_LAKE = FEATURES.register("surface_rule_lake", () -> new SurfaceRuleLakeFeature(SurfaceRuleLakeConfig.CODEC));
    public static RegistryObject<Feature<FieldsprootTreeConfig>> FIELDSPROOT_TREE = FEATURES.register("fieldsprout_tree", () -> new FieldsproutTreeFeature(FieldsprootTreeConfig.CODEC));
    public static RegistryObject<Feature<CloudLayerConfig>> CLOUD_LAYER = FEATURES.register("cloud_layer", () -> new CloudLayerFeature(CloudLayerConfig.CODEC));
    public static RegistryObject<Feature<JellyshroomConfig>> JELLYSHROOM = FEATURES.register("jellyshroom", () -> new HugeJellyshroomFeature(JellyshroomConfig.CODEC));
    public static RegistryObject<Feature<NoneFeatureConfiguration>> TREE_AWARE_SNOW = FEATURES.register("tree_aware_snow", () -> new TreeAwareSnowLayerFeature(NoneFeatureConfiguration.CODEC));
    public static RegistryObject<Feature<UpwardVineFeature.UpwardVinesConfig>> UPWARD_VINE = FEATURES.register("upward_vine", () -> new UpwardVineFeature(UpwardVineFeature.UpwardVinesConfig.CODEC));
    public static RegistryObject<Feature<CandyCaneFeature.CandyCaneConfiguration>> CANDY_CANE = FEATURES.register("candy_cane", () -> new CandyCaneFeature(CandyCaneFeature.CandyCaneConfiguration.CODEC));
}
