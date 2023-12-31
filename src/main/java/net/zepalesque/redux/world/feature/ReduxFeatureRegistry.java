package net.zepalesque.redux.world.feature;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.world.feature.config.*;

public class ReduxFeatureRegistry {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Redux.MODID);
    /** These are placed by bonemeal */
    public static RegistryObject<Feature<RandomPatchConfiguration>> BIOME_BORDER_PLACEMENT_FLOWER = FEATURES.register("biome_border_placement_flower", () -> new BiomeBorderPlacementPatch(RandomPatchConfiguration.CODEC));
    public static RegistryObject<Feature<TestBelowPlaceConfig>> SUPPORT_TEST_FLOWER = FEATURES.register("support_test_flower", () -> new TestBlockBelowPlacementPatch(TestBelowPlaceConfig.CODEC));
    public static RegistryObject<Feature<TestAtPlaceConfig>> BLOCK_TEST_FLOWER = FEATURES.register("block_test_flower", () -> new TestBlockAtPlacementPatch(TestAtPlaceConfig.CODEC));

    public static RegistryObject<Feature<OreConfiguration>> ORE_WITH_UPDATE = FEATURES.register("ore_with_update", () -> new OreFeatureWithUpdate(OreConfiguration.CODEC));
    public static RegistryObject<Feature<RootedShelfConfiguration>> ROOTED_SHELF = FEATURES.register("rooted_shelf", () -> new RootedShelfFeature(RootedShelfConfiguration.CODEC));
    public static RegistryObject<Feature<HugeAetherMushroomFeatureConfiguration>> LARGE_CLOUDCAP = FEATURES.register("large_cloudcap", () -> new HugeCloudcapMushroomFeature(HugeAetherMushroomFeatureConfiguration.CODEC));
    public static RegistryObject<Feature<HugeAetherMushroomFeatureConfiguration>> LARGE_SPRINGSHROOM = FEATURES.register("large_springshroom", () -> new HugeSpringshroomFeature(HugeAetherMushroomFeatureConfiguration.CODEC));
    public static RegistryObject<Feature<RandomPatchConfiguration>> BIOME_BORDER_PLACEMENT_PATCH = FEATURES.register("biome_border_placement_patch", () -> new BiomeBorderPlacementPatch(RandomPatchConfiguration.CODEC));
    public static RegistryObject<Feature<TestBelowPlaceConfig>> SUPPORT_TEST_PATCH = FEATURES.register("support_test_patch", () -> new TestBlockBelowPlacementPatch(TestBelowPlaceConfig.CODEC));
    public static RegistryObject<Feature<MegaCloudcapFeatureConfiguration>> MEGA_CLOUDCAP = FEATURES.register("mega_cloudcap", () -> new MegaCloudcapFeature(MegaCloudcapFeatureConfiguration.CODEC));
    public static RegistryObject<Feature<SurfaceRuleLakeConfig>> SURFACE_RULE_LAKE = FEATURES.register("surface_rule_lake", () -> new SurfaceRuleLakeFeature(SurfaceRuleLakeConfig.CODEC));
    public static RegistryObject<Feature<TestAtPlaceConfig>> BLOCK_TEST_PATCH = FEATURES.register("block_test_patch", () -> new TestBlockAtPlacementPatch(TestAtPlaceConfig.CODEC));
    public static RegistryObject<Feature<FieldsproutTreeConfig>> FIELDSPROUT_TREE = FEATURES.register("fieldsprout_tree", () -> new FieldsproutTreeFeature(FieldsproutTreeConfig.CODEC));
    public static RegistryObject<Feature<CloudLayerConfig>> CLOUD_LAYER = FEATURES.register("cloud_layer", () -> new CloudLayerFeature(CloudLayerConfig.CODEC));
    public static RegistryObject<Feature<CloudLayerConfig>> STONE_LAYER = FEATURES.register("stone_layer", () -> new StoneLayerFeature(CloudLayerConfig.CODEC));
}
