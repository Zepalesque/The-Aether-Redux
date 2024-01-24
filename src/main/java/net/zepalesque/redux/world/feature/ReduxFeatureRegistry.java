package net.zepalesque.redux.world.feature;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.world.feature.config.*;

public class ReduxFeatureRegistry {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Redux.MODID);

    public static RegistryObject<Feature<PredicateStateConfig>> TEST_BELOW_BLOCK = FEATURES.register("test_below_block", () -> new TestBelowBlockFeature(PredicateStateConfig.CODEC));
    public static RegistryObject<Feature<PredicateStateConfig>> TEST_AT_BLOCK = FEATURES.register("test_at_block", () -> new TestAtBlockFeature(PredicateStateConfig.CODEC));
    public static RegistryObject<Feature<OreConfiguration>> ORE_WITH_UPDATE = FEATURES.register("ore_with_update", () -> new OreFeatureWithUpdate(OreConfiguration.CODEC));
    public static RegistryObject<Feature<RootedShelfConfiguration>> ROOTED_SHELF = FEATURES.register("rooted_shelf", () -> new RootedShelfFeature(RootedShelfConfiguration.CODEC));
    public static RegistryObject<Feature<HugeAetherMushroomFeatureConfiguration>> LARGE_CLOUDCAP = FEATURES.register("large_cloudcap", () -> new HugeCloudcapMushroomFeature(HugeAetherMushroomFeatureConfiguration.CODEC));
    public static RegistryObject<Feature<HugeAetherMushroomFeatureConfiguration>> LARGE_SPRINGSHROOM = FEATURES.register("large_springshroom", () -> new HugeSpringshroomFeature(HugeAetherMushroomFeatureConfiguration.CODEC));
    public static RegistryObject<Feature<MegaCloudcapFeatureConfiguration>> MEGA_CLOUDCAP = FEATURES.register("mega_cloudcap", () -> new MegaCloudcapFeature(MegaCloudcapFeatureConfiguration.CODEC));
    public static RegistryObject<Feature<SurfaceRuleLakeConfig>> SURFACE_RULE_LAKE = FEATURES.register("surface_rule_lake", () -> new SurfaceRuleLakeFeature(SurfaceRuleLakeConfig.CODEC));
    public static RegistryObject<Feature<FieldsproutTreeConfig>> FIELDSPROUT_TREE = FEATURES.register("fieldsprout_tree", () -> new FieldsproutTreeFeature(FieldsproutTreeConfig.CODEC));
    public static RegistryObject<Feature<CloudLayerConfig>> CLOUD_LAYER = FEATURES.register("cloud_layer", () -> new CloudLayerFeature(CloudLayerConfig.CODEC));
    public static RegistryObject<Feature<JellyshroomConfig>> JELLYSHROOM = FEATURES.register("jellyshroom", () -> new HugeJellyshroomFeature(JellyshroomConfig.CODEC));
    public static RegistryObject<Feature<NoneFeatureConfiguration>> TREE_AWARE_SNOW = FEATURES.register("tree_aware_snow", () -> new TreeAwareSnowLayerFeature(NoneFeatureConfiguration.CODEC));
}
