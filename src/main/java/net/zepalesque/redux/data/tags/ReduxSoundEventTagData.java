package net.zepalesque.redux.data.tags;

import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.data.resources.registries.AetherDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import net.zepalesque.redux.misc.ReduxTags;

import java.util.concurrent.CompletableFuture;

public class ReduxSoundEventTagData extends TagsProvider<SoundEvent> {
    public ReduxSoundEventTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, Registries.SOUND_EVENT, registries, Redux.MODID, existingFileHelper);
    }


    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ReduxTags.Sounds.AETHER_MUSIC).add(AetherSoundEvents.MUSIC_AETHER.getKey(), ReduxSoundEvents.REDUX_MENU.getKey());
    }
}
