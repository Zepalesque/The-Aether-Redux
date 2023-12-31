package net.zepalesque.redux.data.tags;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.data.resources.registries.AetherBiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.data.ReduxRegistrySets;
import net.zepalesque.redux.data.resource.ReduxBiomes;
import net.zepalesque.redux.misc.ReduxTags;
import org.jetbrains.annotations.Nullable;
import teamrazor.deepaether.world.biomes.DABiomes;

import java.util.concurrent.CompletableFuture;

public class ReduxBiomeTagsData extends BiomeTagsProvider {
    public ReduxBiomeTagsData(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider.thenApply(ReduxRegistrySets::patchLookup), Redux.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        this.tag(ReduxTags.Biomes.IS_HIGHLANDS).add(AetherBiomes.SKYROOT_FOREST, AetherBiomes.SKYROOT_GROVE, AetherBiomes.SKYROOT_MEADOW, AetherBiomes.SKYROOT_WOODLAND)
                .addOptional(DABiomes.AERGLOW_FOREST.location())
                .addOptional(DABiomes.BLUE_AERGLOW_FOREST.location())
                .addOptional(DABiomes.MYSTIC_AERGLOW_FOREST.location())
                .addOptional(DABiomes.AERLAVENDER_FIELDS.location());
        this.tag(ReduxTags.Biomes.IS_FORGOTTEN).add(AetherBiomes.SKYROOT_FOREST, AetherBiomes.SKYROOT_GROVE, AetherBiomes.SKYROOT_MEADOW, AetherBiomes.SKYROOT_WOODLAND);
        this.tag(AetherTags.Biomes.IS_AETHER).add(
                ReduxBiomes.THE_BLIGHT,
                ReduxBiomes.FROSTED_FORESTS,
                ReduxBiomes.GILDED_GROVES,
                ReduxBiomes.HIGHFIELDS,
                ReduxBiomes.CLOUDCAP_JUNGLE
        );

        this.tag(ReduxTags.Biomes.HAS_AETHER_CAVES).addTag(AetherTags.Biomes.IS_AETHER);
        this.tag(ReduxTags.Biomes.HAS_BLIGHTED_CAVES).addTag(ReduxTags.Biomes.IS_HIGHLANDS);
        this.tag(ReduxTags.Biomes.HAS_MOSSY_HOLYSTONE_ORE).addTag(ReduxTags.Biomes.IS_FORGOTTEN);
        this.tag(ReduxTags.Biomes.HAS_MOSSY_ROCKS).add(AetherBiomes.SKYROOT_FOREST, AetherBiomes.SKYROOT_WOODLAND);
        this.tag(ReduxTags.Biomes.HAS_SKYSPROUTS).addTag(ReduxTags.Biomes.IS_HIGHLANDS);
        this.tag(ReduxTags.Biomes.HAS_SWEETBLOSSOM).addTag(ReduxTags.Biomes.IS_FORGOTTEN);
        this.tag(ReduxTags.Biomes.HAS_VANILLA_SWET).addTag(ReduxTags.Biomes.IS_HIGHLANDS);
        this.tag(ReduxTags.Biomes.HAS_VERIDIUM_ORE).addTag(AetherTags.Biomes.IS_AETHER);
        this.tag(ReduxTags.Biomes.HAS_DIVINITE).addTag(AetherTags.Biomes.IS_AETHER);
        this.tag(ReduxTags.Biomes.HAS_REDUX_WATER_COLOR).addTag(ReduxTags.Biomes.IS_HIGHLANDS);

        this.tag(ReduxTags.Biomes.DENSE_LEAF_FALL).add(AetherBiomes.SKYROOT_FOREST, AetherBiomes.SKYROOT_WOODLAND);

        this.tag(ReduxTags.Biomes.HAS_SENTRY_LAB).add(ReduxBiomes.FROSTED_FORESTS);
    }
}
