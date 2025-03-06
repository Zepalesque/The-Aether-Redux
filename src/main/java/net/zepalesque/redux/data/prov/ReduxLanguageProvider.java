package net.zepalesque.redux.data.prov;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.zepalesque.unity.data.prov.UnityLanguageProvider;
import net.zepalesque.zenith.util.data.DatagenUtil;

import java.util.function.Supplier;

public abstract class ReduxLanguageProvider extends UnityLanguageProvider {
    public ReduxLanguageProvider(PackOutput output, String id) {
        super(output, id);
    }

    public void addJukeboxSong(ResourceKey<JukeboxSong> songName, String name) {
        this.addJukeboxSong(songName.location().getPath(), name);
    }

    public void addAdvancement(String key, String name, String desc) {
        this.addAdvancement(key, name);
        this.addAdvancementDesc(key, desc);
    }

    public void addSubtitle(Supplier<SoundEvent> sound, String subtitle) {
        super.addSubtitle(sound, DatagenUtil::subtitleFor, subtitle);
    }
}
