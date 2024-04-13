package net.zepalesque.redux.data.tags;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether_genesis.entity.GenesisEntityTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.entity.ReduxEntityTypes;
import net.zepalesque.redux.misc.ReduxTags;

import java.util.concurrent.CompletableFuture;

public class ReduxEntityTypeTagData extends EntityTypeTagsProvider {

    public ReduxEntityTypeTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String modid, ExistingFileHelper existingFileHelper) {
        super(output, registries, modid, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ReduxTags.EntityTypes.BLIGHTED_MOBS).add(AetherEntityTypes.COCKATRICE.get(), ReduxEntityTypes.BLIGHTBUNNY.get()).addOptional(GenesisEntityTypes.TEMPEST.getId());
        this.tag(ReduxTags.EntityTypes.SENTRIES).add(AetherEntityTypes.SENTRY.get())
                .addOptional(GenesisEntityTypes.BATTLE_SENTRY.getId())
                .addOptional(GenesisEntityTypes.TRACKING_GOLEM.getId());
        this.tag(ReduxTags.EntityTypes.SWET_PASSTHROUGH).add(
                EntityType.SLIME,
                AetherEntityTypes.ZEPHYR.get(),
                AetherEntityTypes.MOA.get(),
                AetherEntityTypes.COCKATRICE.get(),
                AetherEntityTypes.AECHOR_PLANT.get()
        ).addOptional(new ResourceLocation("aether_genesis", "tempest"));
        this.tag(AetherTags.Entities.SWETS).add(ReduxEntityTypes.VANILLA_SWET.get());
    }
}
