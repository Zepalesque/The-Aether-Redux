package net.zepalesque.redux.api.predicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public record MusicPredicate(Optional<HolderSet<SoundEvent>> sounds, Optional<List<SoundEvent>> soundsAsList, Optional<List<Integer>> minDelays, Optional<List<Integer>> maxDelays, Optional<Boolean> replaceCurrent) implements Predicate<Music> {
    public static final Codec<HolderSet<SoundEvent>> SOUND_EVENT_SET = RegistryCodecs.homogeneousList(Registry.SOUND_EVENT_REGISTRY, SoundEvent.CODEC);

    
    public MusicPredicate(Optional<HolderSet<SoundEvent>> sounds, Optional<List<Integer>> minDelays, Optional<List<Integer>> maxDelays, Optional<Boolean> replaceCurrent) {
        this(sounds, sounds.map(set -> set.stream().filter(Holder::isBound).map(Holder::value).toList()), minDelays, maxDelays, replaceCurrent);
    }
    
    public static final Codec<MusicPredicate> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            SOUND_EVENT_SET.optionalFieldOf("sounds").forGetter(MusicPredicate::sounds),
            Codec.INT.listOf().optionalFieldOf("valid_min_delays").forGetter(MusicPredicate::minDelays),
            Codec.INT.listOf().optionalFieldOf("valid_max_delays").forGetter(MusicPredicate::maxDelays),
            Codec.BOOL.optionalFieldOf("replaces_current").forGetter(MusicPredicate::replaceCurrent)).apply(builder, MusicPredicate::new));

    @Override
    public boolean test(Music music) {
        if (this.soundsAsList.isPresent() && !this.soundsAsList.get().contains(music.getEvent())) return false;
        if (this.minDelays.isPresent() && !this.minDelays.get().isEmpty() && !this.minDelays.get().contains(music.getMinDelay()))
            return false;
        if (this.maxDelays.isPresent() && !this.maxDelays.get().isEmpty() && !this.maxDelays.get().contains(music.getMaxDelay()))
            return false;
        return this.replaceCurrent.isEmpty() || music.replaceCurrentMusic() == this.replaceCurrent.get();
    }
}
