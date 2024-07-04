package net.zepalesque.redux.data.tags;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.data.resources.registries.AetherBiomes;
import com.aetherteam.aether_genesis.data.resources.registries.GenesisBiomes;
import net.builderdog.ancient_aether.data.resources.registries.AncientAetherBiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.data.resource.biome.registry.ReduxBiomes;
import net.zepalesque.redux.misc.ReduxTags;
import org.jetbrains.annotations.Nullable;
import teamrazor.deepaether.world.biomes.DABiomes;

import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;

public class ReduxBiomeTagsData extends BiomeTagsProvider {
    public ReduxBiomeTagsData(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, String modid, UnaryOperator<HolderLookup.Provider> func, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider.thenApply(func), modid, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        this.tag(AetherTags.Biomes.IS_AETHER).add(
                ReduxBiomes.THE_BLIGHT,
                ReduxBiomes.FROSTED_FORESTS,
                ReduxBiomes.GLACIAL_TUNDRA,
                ReduxBiomes.GILDED_GROVES,
                ReduxBiomes.GILDED_GRASSLANDS,
                ReduxBiomes.SKYFIELDS,
                ReduxBiomes.CLOUDCAPS,
                ReduxBiomes.SKYROOT_SHRUBLANDS
        );



        this.tag(ReduxTags.Biomes.AA_SKY_GRASS).remove(ReduxTags.Biomes.IS_GILDED).addTag(
                ReduxTags.Biomes.IS_FROSTED
        ).add(ReduxBiomes.SKYROOT_SHRUBLANDS, ReduxBiomes.SKYFIELDS, ReduxBiomes.THE_BLIGHT);


        this.tag(ReduxTags.Biomes.HAS_AETHER_CAVES).addTag(AetherTags.Biomes.IS_AETHER);
        this.tag(ReduxTags.Biomes.HAS_CLOUD_LAYER).addTag(AetherTags.Biomes.IS_AETHER);
        this.tag(ReduxTags.Biomes.HAS_ENDERMAN).addTag(AetherTags.Biomes.IS_AETHER);
        this.tag(ReduxTags.Biomes.HAS_BLIGHTED_CAVES).addTag(AetherTags.Biomes.IS_AETHER).remove(ReduxBiomes.THE_BLIGHT);
        this.tag(ReduxTags.Biomes.HAS_FUNGAL_CAVES).addTag(AetherTags.Biomes.IS_AETHER).remove(ReduxBiomes.CLOUDCAPS);
        this.tag(ReduxTags.Biomes.HAS_MOSSY_HOLYSTONE_ORE).add(AetherBiomes.SKYROOT_FOREST, AetherBiomes.SKYROOT_GROVE, AetherBiomes.SKYROOT_MEADOW, AetherBiomes.SKYROOT_WOODLAND);
        this.tag(ReduxTags.Biomes.HAS_MOSSY_ROCKS).add(AetherBiomes.SKYROOT_FOREST, AetherBiomes.SKYROOT_WOODLAND);
        this.tag(ReduxTags.Biomes.HAS_WYNDSPROUTS).add(
                AetherBiomes.SKYROOT_GROVE,
                AetherBiomes.SKYROOT_MEADOW,
                AetherBiomes.SKYROOT_WOODLAND);
        this.tag(ReduxTags.Biomes.HAS_BOTH_SPROUTS)
                .addOptional(GenesisBiomes.VIBRANT_GROVE.location())
                .addOptional(GenesisBiomes.VIBRANT_MEADOW.location());
        this.tag(ReduxTags.Biomes.HAS_VANILLA_SWET).add(
                        AetherBiomes.SKYROOT_FOREST,
                        AetherBiomes.SKYROOT_GROVE,
                        AetherBiomes.SKYROOT_MEADOW,
                        AetherBiomes.SKYROOT_WOODLAND)
                .addOptional(DABiomes.AERGLOW_FOREST.location())
                .addOptional(DABiomes.BLUE_AERGLOW_FOREST.location())
                .addOptional(DABiomes.MYSTIC_AERGLOW_FOREST.location())
                .addOptional(DABiomes.AERLAVENDER_FIELDS.location())
                .addOptional(GenesisBiomes.VIBRANT_MEADOW.location())
                .addOptional(GenesisBiomes.VIBRANT_GROVE.location())
                .addOptional(GenesisBiomes.VIBRANT_WOODLAND.location())
                .addOptional(GenesisBiomes.VIBRANT_FOREST.location());
        this.tag(ReduxTags.Biomes.HAS_VERIDIUM_ORE).addTag(AetherTags.Biomes.IS_AETHER);
        this.tag(ReduxTags.Biomes.HAS_DIVINITE).addTag(AetherTags.Biomes.IS_AETHER).remove(ReduxTags.Biomes.IS_GILDED);
        this.tag(ReduxTags.Biomes.HAS_SENTRITE).addTag(AetherTags.Biomes.IS_AETHER).remove(ReduxTags.Biomes.IS_GILDED);
        this.tag(ReduxTags.Biomes.MODIFY_WATER_COLOR).addTag(AetherTags.Biomes.IS_AETHER);

        this.tag(ReduxTags.Biomes.HAS_REDUX_SKY_COLOR).addTag(AetherTags.Biomes.IS_AETHER);
        this.tag(ReduxTags.Biomes.MODIFY_MUSIC).addTag(AetherTags.Biomes.IS_AETHER);

        this.tag(ReduxTags.Biomes.DENSE_LEAF_FALL).add(AetherBiomes.SKYROOT_FOREST, AetherBiomes.SKYROOT_WOODLAND);


        this.tag(ReduxTags.Biomes.IS_GILDED).add(ReduxBiomes.GILDED_GROVES, ReduxBiomes.GILDED_GRASSLANDS);
        this.tag(ReduxTags.Biomes.IS_FROSTED).add(ReduxBiomes.FROSTED_FORESTS, ReduxBiomes.GLACIAL_TUNDRA);

        this.tag(ReduxTags.Biomes.CHANGE_VANILLA_GRASS_COLORS).addTag(AetherTags.Biomes.IS_AETHER);
    }
}
