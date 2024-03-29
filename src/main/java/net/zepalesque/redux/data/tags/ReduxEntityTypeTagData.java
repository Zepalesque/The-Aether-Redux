package net.zepalesque.redux.data.tags;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether_genesis.entity.GenesisEntityTypes;
import net.builderdog.ancient_aether.entity.AncientAetherEntityTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.entity.ReduxEntityTypes;
import net.zepalesque.redux.misc.ReduxTags;
import teamrazor.deepaether.init.DAEntities;

import java.util.concurrent.CompletableFuture;

public class ReduxEntityTypeTagData extends EntityTypeTagsProvider {

    public ReduxEntityTypeTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, Redux.MODID, existingFileHelper);
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
        this.tag(ReduxTags.EntityTypes.DROPS_REBUX).add(
                AetherEntityTypes.AECHOR_PLANT.get(),
                AetherEntityTypes.AERBUNNY.get(),
                AetherEntityTypes.AERWHALE.get(),
                AetherEntityTypes.COCKATRICE.get(),
                AetherEntityTypes.FIRE_MINION.get(),
                AetherEntityTypes.FLYING_COW.get(),
                AetherEntityTypes.MIMIC.get(),
                AetherEntityTypes.MOA.get(),
                AetherEntityTypes.PHYG.get(),
                AetherEntityTypes.SENTRY.get(),
                AetherEntityTypes.SHEEPUFF.get(),
                AetherEntityTypes.SLIDER.get(),
                AetherEntityTypes.SUN_SPIRIT.get(),
                AetherEntityTypes.VALKYRIE.get(),
                AetherEntityTypes.VALKYRIE_QUEEN.get(),
                AetherEntityTypes.ZEPHYR.get(),
                ReduxEntityTypes.SHIMMERCOW.get(),
                ReduxEntityTypes.MYKAPOD.get()
        ).addTag(AetherTags.Entities.SWETS)
                .addOptional(DAEntities.QUAIL.getId())
                .addOptional(DAEntities.AETHER_FISH.getId())
                .addOptional(DAEntities.VENOMITE.getId())
                .addOptional(AncientAetherEntityTypes.FLUFFALO.getId())
                .addOptional(AncientAetherEntityTypes.ROOTHYRN.getId())
                .addOptional(AncientAetherEntityTypes.ANCIENT_GUARDIAN.getId())
                .addOptional(AncientAetherEntityTypes.AERONAUTIC_LEAPER.getId())
                .addOptional(GenesisEntityTypes.BATTLE_SENTRY.getId())
                .addOptional(GenesisEntityTypes.TEMPEST.getId())
                .addOptional(GenesisEntityTypes.SKYROOT_MIMIC.getId())
                .addOptional(GenesisEntityTypes.LABYRINTH_EYE.getId())
                .addOptional(GenesisEntityTypes.TRACKING_GOLEM.getId())
                .addOptional(GenesisEntityTypes.SLIDER_HOST_MIMIC.getId())
                .addOptional(GenesisEntityTypes.CARRION_SPROUT.getId())
        ;
    }
}
