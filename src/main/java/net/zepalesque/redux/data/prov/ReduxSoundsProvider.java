package net.zepalesque.redux.data.prov;

import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class ReduxSoundsProvider extends SoundDefinitionsProvider {
    protected ReduxSoundsProvider(PackOutput output, String modId, ExistingFileHelper helper) {
        super(output, modId, helper);
    }
    protected void add(final Supplier<SoundEvent> soundEvent, final Function<SoundEvent, SoundDefinition> definition) {
        this.add(soundEvent.get(), definition.apply(soundEvent.get()));
    }
}
