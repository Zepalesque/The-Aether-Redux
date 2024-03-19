package net.zepalesque.redux.data.tags;

import com.aetherteam.aether.api.AetherAdvancementSoundOverrides;
import com.aetherteam.aether.api.registers.AdvancementSoundOverride;
import com.aetherteam.aether.client.AetherSoundEvents;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.misc.ReduxTags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ReduxAdvancementOverrideTagData extends TagsProvider<AdvancementSoundOverride> {
    public ReduxAdvancementOverrideTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, AetherAdvancementSoundOverrides.ADVANCEMENT_SOUND_OVERRIDE_REGISTRY_KEY, registries, Redux.MODID, existingFileHelper);
    }


    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        this.tag(ReduxTags.Adverrides.LOW_PRIORITY).add(AetherAdvancementSoundOverrides.GENERAL.getKey());
    }
}
