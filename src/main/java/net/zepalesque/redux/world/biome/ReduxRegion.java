package net.zepalesque.redux.world.biome;

import com.aetherteam.aether.data.resources.registries.AetherBiomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraftforge.fml.ModList;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.data.resource.biome.registry.ReduxBiomes;
import teamrazor.aeroblender.aether.AetherRegionType;
import terrablender.api.Region;

import java.util.function.Consumer;

public class ReduxRegion extends Region {

    public ReduxRegion(ResourceLocation name, int weight) {
        super(name, AetherRegionType.THE_AETHER, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        Climate.Parameter fullRange = Climate.Parameter.span(-1.5F, 1.5F);




        if (!Redux.ancientAetherCompat()) {
            ResourceKey<Biome> glacial = ReduxConfig.COMMON.enable_snowy_biomes.get() ? ReduxBiomes.GLACIAL_TAIGA : AetherBiomes.SKYROOT_FOREST;
            ResourceKey<Biome> frosted = ReduxConfig.COMMON.enable_snowy_biomes.get() ? ReduxBiomes.FROSTED_TUNDRA : AetherBiomes.SKYROOT_FOREST;
            ResourceKey<Biome> cloudcaps = ReduxConfig.COMMON.enable_cloudcap_jungle.get() ? ReduxBiomes.CLOUDCAPS : AetherBiomes.SKYROOT_GROVE;
            Climate.Parameter temp1 = Climate.Parameter.span(-1.5F, -0.5F);
            Climate.Parameter temp2 = Climate.Parameter.span(-0.5F, -0.2F);
            Climate.Parameter temp3 = Climate.Parameter.span(-0.2F, 0.2F);
            Climate.Parameter temp4 = Climate.Parameter.span(0.2F, 0.5F);
            Climate.Parameter temp5 = Climate.Parameter.span(0.5F, 1.5F);

            addBiome(mapper, new Climate.ParameterPoint(temp1, Climate.Parameter.span(-1.0F, -0.2F), fullRange, fullRange, fullRange, fullRange, 0),
                    glacial);
            addBiome(mapper, new Climate.ParameterPoint(temp1, Climate.Parameter.span(-0.2F, 0.5F), fullRange, fullRange, fullRange, fullRange, 0),
                    frosted);
            addBiome(mapper, new Climate.ParameterPoint(temp1, Climate.Parameter.span(0.5F, 1.0F), fullRange, fullRange, fullRange, fullRange, 0),
                    cloudcaps);

            addBiome(mapper, new Climate.ParameterPoint(temp2, Climate.Parameter.span(-1.0F, -0.3F), fullRange, fullRange, fullRange, fullRange, 0),
                    ReduxBiomes.THE_BLIGHT);
            addBiome(mapper, new Climate.ParameterPoint(temp2, Climate.Parameter.span(-0.3F, -0.15F), fullRange, fullRange, fullRange, fullRange, 0),
                    AetherBiomes.SKYROOT_FOREST);
            addBiome(mapper, new Climate.ParameterPoint(temp2, Climate.Parameter.span(-0.15F, 0.0F), fullRange, fullRange, fullRange, fullRange, 0),
                    AetherBiomes.SKYROOT_MEADOW);
            addBiome(mapper, new Climate.ParameterPoint(temp2, Climate.Parameter.span(0.0F, 0.2F), fullRange, fullRange, fullRange, fullRange, 0),
                    AetherBiomes.SKYROOT_WOODLAND);
            addBiome(mapper, new Climate.ParameterPoint(temp2, Climate.Parameter.span(0.2F, 1.0F), fullRange, fullRange, fullRange, fullRange, 0),
                    ReduxBiomes.HIGHFIELDS); //ReduxBiomes.SHIMMERING_HILLS);

            addBiome(mapper, new Climate.ParameterPoint(temp3, Climate.Parameter.span(-1.0F, -0.3F), fullRange, fullRange, fullRange, fullRange, 0),
                    ReduxBiomes.HIGHFIELDS);
            addBiome(mapper, new Climate.ParameterPoint(temp3, Climate.Parameter.span(-0.3F, -0.1F), fullRange, fullRange, fullRange, fullRange, 0),
                    AetherBiomes.SKYROOT_FOREST);
            addBiome(mapper, new Climate.ParameterPoint(temp3, Climate.Parameter.span(-0.1F, 0.2F), fullRange, fullRange, fullRange, fullRange, 0),
                    AetherBiomes.SKYROOT_MEADOW);
            addBiome(mapper, new Climate.ParameterPoint(temp3, Climate.Parameter.span(0.2F, 0.5F), fullRange, fullRange, fullRange, fullRange, 0),
                    ReduxBiomes.HIGHFIELDS);
            addBiome(mapper, new Climate.ParameterPoint(temp3, Climate.Parameter.span(0.5F, 1.0F), fullRange, fullRange, fullRange, fullRange, 0),
                    ReduxBiomes.SKYROOT_SHRUBLANDS);

            addBiome(mapper, new Climate.ParameterPoint(temp4, Climate.Parameter.span(-1.0F, -0.6F), fullRange, fullRange, fullRange, fullRange, 0),
                    AetherBiomes.SKYROOT_MEADOW);
            addBiome(mapper, new Climate.ParameterPoint(temp4, Climate.Parameter.span(-0.6F, -0.3F), fullRange, fullRange, fullRange, fullRange, 0),
                    ReduxBiomes.SKYROOT_SHRUBLANDS);
            addBiome(mapper, new Climate.ParameterPoint(temp4, Climate.Parameter.span(-0.3F, -0.05F), fullRange, fullRange, fullRange, fullRange, 0),
                    AetherBiomes.SKYROOT_GROVE);
            addBiome(mapper, new Climate.ParameterPoint(temp4, Climate.Parameter.span(-0.05F, 0.2F), fullRange, fullRange, fullRange, fullRange, 0),
                    ReduxBiomes.SKYROOT_SHRUBLANDS);
            addBiome(mapper, new Climate.ParameterPoint(temp4, Climate.Parameter.span(0.2F, 0.5F), fullRange, fullRange, fullRange, fullRange, 0),
                    AetherBiomes.SKYROOT_GROVE); //ReduxBiomes.QUICKSOIL_DUNES);
            addBiome(mapper, new Climate.ParameterPoint(temp4, Climate.Parameter.span(0.5F, 1.0F), fullRange, fullRange, fullRange, fullRange, 0),
                    ReduxBiomes.SKYROOT_SHRUBLANDS); //ReduxBiomes.QUICKSOIL_OASIS);

            addBiome(mapper, new Climate.ParameterPoint(temp5, Climate.Parameter.span(-1.0F, -0.4F), fullRange, fullRange, fullRange, fullRange, 0),
                    ReduxBiomes.GILDED_GRASSLANDS);
            addBiome(mapper, new Climate.ParameterPoint(temp5, Climate.Parameter.span(-0.4F, 0.3F), fullRange, fullRange, fullRange, fullRange, 0),
                    ReduxBiomes.GILDED_GROVES);
            addBiome(mapper, new Climate.ParameterPoint(temp5, Climate.Parameter.span(0.3F, 1.0F), fullRange, fullRange, fullRange, fullRange, 0),
                    ReduxBiomes.GILDED_GRASSLANDS);

        } else {
            //-----------------------[Ancient Aether Compatibility]-----------------------\\

            Climate.Parameter tempWyndcaps = Climate.Parameter.span(-1.5F, -0.5F);
            Climate.Parameter tempSkyroot = Climate.Parameter.span(-0.5F, 0.5F);
            Climate.Parameter tempSakura = Climate.Parameter.span(0.5F, 1.5F);

            Climate.Parameter erosionDefault = Climate.Parameter.span(0F, 0.5F);
            Climate.Parameter erosionElevated = Climate.Parameter.span(0.5F, 1.5F);

            Climate.Parameter forestDistCrystal1 = Climate.Parameter.span(-1.5F, -0.5F);
            Climate.Parameter forestDistCrystal2 = Climate.Parameter.span(-0.5F, -0.325F);
            Climate.Parameter forestDistSkyroot1 = Climate.Parameter.span(-0.325F, 0.0F);
            Climate.Parameter forestDistSkyroot2 = Climate.Parameter.span(0.0F, 0.325F);
            Climate.Parameter forestDistGolden = Climate.Parameter.span(0.325F, 1.5F);

            //Wyndcaps
            addBiome(mapper, new Climate.ParameterPoint(tempWyndcaps, Climate.Parameter.span(-1.0F, -0.2F), fullRange, erosionDefault, Climate.Parameter.span(-0.15F, 1.5F), fullRange, 0),
                    ReduxBiomes.GLACIAL_TAIGA);
            addBiome(mapper, new Climate.ParameterPoint(tempWyndcaps, Climate.Parameter.span(-0.2F, 0.5F), fullRange, erosionDefault, Climate.Parameter.span(-0.15F, 1.5F), fullRange, 0),
                    ReduxBiomes.FROSTED_TUNDRA);
            addBiome(mapper, new Climate.ParameterPoint(tempWyndcaps, Climate.Parameter.span(0.5F, 1.0F), fullRange, erosionDefault, Climate.Parameter.span(-0.15F, 1.5F), fullRange, 0),
                    ReduxBiomes.CLOUDCAPS);
            addBiome(mapper, new Climate.ParameterPoint(tempWyndcaps, fullRange, fullRange, erosionDefault, Climate.Parameter.span(-1.5F, -0.15F), fullRange, 0),
                    ResourceKey.create(Registries.BIOME, new ResourceLocation("ancient_aether", "wyndcap_peaks")));

            //Skyroot Forests

            //-----[Placeholders]-----\\
            addBiome(mapper, new Climate.ParameterPoint(tempSkyroot, Climate.Parameter.span(-1.5F, -0.1F), fullRange, erosionDefault, fullRange, forestDistCrystal1, 0),
                    ResourceKey.create(Registries.BIOME, new ResourceLocation("ancient_aether", "crystal_skyroot_grove")));
            addBiome(mapper, new Climate.ParameterPoint(tempSkyroot, Climate.Parameter.span(-0.1F, 0.35F), fullRange, erosionDefault, fullRange, forestDistCrystal1, 0),
                    ResourceKey.create(Registries.BIOME, new ResourceLocation("ancient_aether", "crystal_skyroot_forest")));
            addBiome(mapper, new Climate.ParameterPoint(tempSkyroot, Climate.Parameter.span(-0.35F, 0.45F), fullRange, erosionDefault, fullRange, forestDistCrystal2, 0),
                    ResourceKey.create(Registries.BIOME, new ResourceLocation("ancient_aether", "crystal_skyroot_forest")));
            //-----------------------\\

            /*Uncomment this once the Shimmering Hills are implemented and remove Placeholders above*/
            //addBiome(mapper, new Climate.ParameterPoint(tempSkyroot, fullRange, fullRange, erosionDefault, fullRange, forestDistCrystal2, 0),
            //        //ReduxBiomes.SHIMMERING_HILLS);

            addBiome(mapper, new Climate.ParameterPoint(tempSkyroot, Climate.Parameter.span(0.35F, 1.5F), fullRange, erosionDefault, fullRange, forestDistCrystal1, 0),
                    AetherBiomes.SKYROOT_MEADOW);
            addBiome(mapper, new Climate.ParameterPoint(tempSkyroot, Climate.Parameter.span(-1.5F, -0.35F), fullRange, erosionDefault, fullRange, forestDistCrystal2, 0),
                    AetherBiomes.SKYROOT_MEADOW);
            addBiome(mapper, new Climate.ParameterPoint(tempSkyroot, Climate.Parameter.span(0.45F, 1.5F), fullRange, erosionDefault, fullRange, forestDistCrystal2, 0),
                    AetherBiomes.SKYROOT_WOODLAND);

            addBiome(mapper, new Climate.ParameterPoint(tempSkyroot, Climate.Parameter.span(-1.5F, -0.5F), fullRange, erosionDefault, fullRange, forestDistSkyroot1, 0),
                    ReduxBiomes.THE_BLIGHT);
            addBiome(mapper, new Climate.ParameterPoint(tempSkyroot, Climate.Parameter.span(-0.5F, -0.1F), fullRange, erosionDefault, fullRange, forestDistSkyroot1, 0),
                    ReduxBiomes.SKYROOT_SHRUBLANDS);
            addBiome(mapper, new Climate.ParameterPoint(tempSkyroot, Climate.Parameter.span(-0.1F, 0.3F), fullRange, erosionDefault, fullRange, forestDistSkyroot1, 0),
                    AetherBiomes.SKYROOT_WOODLAND);
            addBiome(mapper, new Climate.ParameterPoint(tempSkyroot, Climate.Parameter.span(0.3F, 1.5F), fullRange, erosionDefault, fullRange, forestDistSkyroot1, 0),
                    AetherBiomes.SKYROOT_GROVE);

            addBiome(mapper, new Climate.ParameterPoint(tempSkyroot, Climate.Parameter.span(-1.5F, -0.2F), fullRange, erosionDefault, fullRange, forestDistSkyroot2, 0),
                    AetherBiomes.SKYROOT_FOREST);
            addBiome(mapper, new Climate.ParameterPoint(tempSkyroot, Climate.Parameter.span(-0.2F, -0.15F), fullRange, erosionDefault, fullRange, forestDistSkyroot2, 0),
                    AetherBiomes.SKYROOT_MEADOW);
            addBiome(mapper, new Climate.ParameterPoint(tempSkyroot, Climate.Parameter.span(-0.15F, 0.1F), fullRange, erosionDefault, fullRange, forestDistSkyroot2, 0),
                    AetherBiomes.SKYROOT_FOREST);
            addBiome(mapper, new Climate.ParameterPoint(tempSkyroot, Climate.Parameter.span(0.1F, 1.5F), fullRange, erosionDefault, fullRange, forestDistSkyroot2, 0),
                    ReduxBiomes.SKYROOT_SHRUBLANDS);

            addBiome(mapper, new Climate.ParameterPoint(tempSkyroot, Climate.Parameter.span(-1.5F, -0.4F), fullRange, erosionDefault, fullRange, forestDistGolden, 0),
                    ReduxBiomes.GILDED_GRASSLANDS);
            addBiome(mapper, new Climate.ParameterPoint(tempSkyroot, Climate.Parameter.span(-0.4F, 0.3F), fullRange, erosionDefault, fullRange, forestDistGolden, 0),
                    ReduxBiomes.GILDED_GROVES);
            addBiome(mapper, new Climate.ParameterPoint(tempSkyroot, Climate.Parameter.span(0.3F, 1.5F), fullRange, erosionDefault, fullRange, forestDistGolden, 0),
                    ReduxBiomes.GILDED_GRASSLANDS);

            //Sakura Jungle
            addBiome(mapper, new Climate.ParameterPoint(tempSakura, Climate.Parameter.span(-1.5F, 0.25F), fullRange, erosionDefault, fullRange, fullRange, 0),
                    ResourceKey.create(Registries.BIOME, new ResourceLocation("ancient_aether", "sakura_jungle")));
            addBiome(mapper, new Climate.ParameterPoint(tempSakura, Climate.Parameter.span(0.25F, 1.5F), fullRange, erosionDefault, fullRange, fullRange, 0),
                    ReduxBiomes.HIGHFIELDS);

            //Elevated Islands
            addBiome(mapper, new Climate.ParameterPoint(fullRange, Climate.Parameter.span(-1.5F, -0.25F), fullRange, erosionElevated, fullRange, fullRange, 0),
                    ResourceKey.create(Registries.BIOME, new ResourceLocation("ancient_aether", "elevated_clearing")));
            addBiome(mapper, new Climate.ParameterPoint(fullRange, Climate.Parameter.span(-0.25F, 0.25F), fullRange, erosionElevated, fullRange, fullRange, 0),
                    ResourceKey.create(Registries.BIOME, new ResourceLocation("ancient_aether", "elevated_forest")));
            addBiome(mapper, new Climate.ParameterPoint(fullRange, Climate.Parameter.span(0.25F, 1.5F), fullRange, erosionElevated, fullRange, fullRange, 0),
                    ResourceKey.create(Registries.BIOME, new ResourceLocation("ancient_aether", "elevated_clearing")));
        }
    }
}