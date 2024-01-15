package net.zepalesque.redux.data.tags;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether_genesis.entity.GenesisEntityTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.misc.ReduxTags;

import java.util.concurrent.CompletableFuture;

public class ReduxEntityTypeTagData extends EntityTypeTagsProvider {

    public ReduxEntityTypeTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, Redux.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ReduxTags.EntityTypes.BLIGHTED_MOBS).add(AetherEntityTypes.COCKATRICE.get()).addOptional(GenesisEntityTypes.TEMPEST.getId());
        this.tag(ReduxTags.EntityTypes.SENTRIES).add(AetherEntityTypes.SENTRY.get())
                .addOptional(GenesisEntityTypes.BATTLE_SENTRY.getId())
                .addOptional(GenesisEntityTypes.TRACKING_GOLEM.getId());
    }
}
