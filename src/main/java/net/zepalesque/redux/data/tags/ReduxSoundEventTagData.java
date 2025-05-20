package net.zepalesque.redux.data.tags;

import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether_genesis.client.GenesisSoundEvents;
import com.legacy.lost_aether.registry.LCSounds;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.misc.ReduxTags;
import org.jetbrains.annotations.NotNull;
import teamrazor.deepaether.init.DASounds;

import java.util.concurrent.CompletableFuture;

public class ReduxSoundEventTagData extends TagsProvider<SoundEvent> {
    public ReduxSoundEventTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String modid, ExistingFileHelper existingFileHelper) {
        super(output, Registries.SOUND_EVENT, registries, modid, existingFileHelper);
    }


    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ReduxTags.Sounds.ALWAYS_ALLOW).add()
                .add(
                    AetherSoundEvents.MUSIC_BOSS_SLIDER.getKey(),
                    AetherSoundEvents.MUSIC_BOSS_VALKYRIE_QUEEN.getKey(),
                    AetherSoundEvents.MUSIC_BOSS_SUN_SPIRIT.getKey(),
                    AetherSoundEvents.UI_TOAST_AETHER_GENERAL.getKey(),
                    AetherSoundEvents.UI_TOAST_AETHER_BRONZE.getKey(),
                    AetherSoundEvents.UI_TOAST_AETHER_SILVER.getKey(),
                    AetherSoundEvents.UI_TOAST_AETHER_GOLD.getKey()
                ).addOptional(LCSounds.MUSIC_PLATINUM_BOSS.getLocation())
                .addOptional(LCSounds.MUSIC_PLATINUM_BOSS_FOGGY.getLocation())
                .addOptional(DASounds.LOCUS_FOR_WINDS.getId());
        this.tag(ReduxTags.Sounds.AETHER_MUSIC)
                .add(AetherSoundEvents.MUSIC_AETHER.getHolder().flatMap(Holder::unwrapKey).orElseThrow())
                .addOptional(GenesisSoundEvents.MUSIC_AETHER_NIGHT.getId())
                .addOptional(DASounds.DEEP_AETHER_MUSIC.getId())
                .addTag(ReduxTags.Sounds.ALWAYS_ALLOW);
    }
}
