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
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class MusicPredicate implements Predicate<Music> {
    public static final Codec<HolderSet<SoundEvent>> SOUND_EVENT_SET = RegistryCodecs.homogeneousList(Registry.SOUND_EVENT.key(), SoundEvent.CODEC);

    public static final Codec<MusicPredicate> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            SOUND_EVENT_SET.optionalFieldOf("sounds").forGetter(MusicPredicate::sounds),
            Codec.INT.listOf().optionalFieldOf("valid_min_delays").forGetter(MusicPredicate::minDelays),
            Codec.INT.listOf().optionalFieldOf("valid_max_delays").forGetter(MusicPredicate::maxDelays),
            Codec.BOOL.optionalFieldOf("replaces_current").forGetter(MusicPredicate::replaceCurrent)).apply(builder, MusicPredicate::new));
    private final Optional<HolderSet<SoundEvent>> sounds;
    private final Optional<List<SoundEvent>> soundList;
    private final Optional<List<Integer>> minDelays;
    private final Optional<List<Integer>> maxDelays;
    private final Optional<Boolean> replaceCurrent;

    public MusicPredicate(Optional<HolderSet<SoundEvent>> sounds, Optional<List<Integer>> minDelays, Optional<List<Integer>> maxDelays, Optional<Boolean> replaceCurrent) {
        this.sounds = sounds;
        this.minDelays = minDelays;
        this.maxDelays = maxDelays;
        this.replaceCurrent = replaceCurrent;
        this.soundList = sounds.map(set -> set.stream().filter(Holder::isBound).map(Holder::value).toList());
    }

    @Override
    public boolean test(Music music) {
        if (this.soundList.isPresent() && !this.soundList.get().contains(music.getEvent())) {
            return false;
        }
        if (this.minDelays.isPresent() && !this.minDelays.get().isEmpty() && !this.minDelays.get().contains(music.getMinDelay())) {
            return false;
        }
        if (this.maxDelays.isPresent() && !this.maxDelays.get().isEmpty() && !this.maxDelays.get().contains(music.getMaxDelay())) {
            return false;
        }
        return this.replaceCurrent.isEmpty() || music.replaceCurrentMusic() == this.replaceCurrent.get();
    }

    public Optional<HolderSet<SoundEvent>> sounds() {
        return sounds;
    }

    public Optional<List<Integer>> minDelays() {
        return minDelays;
    }

    public Optional<List<Integer>> maxDelays() {
        return maxDelays;
    }

    public Optional<Boolean> replaceCurrent() {
        return replaceCurrent;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (MusicPredicate) obj;
        return Objects.equals(this.sounds, that.sounds) &&
                Objects.equals(this.minDelays, that.minDelays) &&
                Objects.equals(this.maxDelays, that.maxDelays) &&
                Objects.equals(this.replaceCurrent, that.replaceCurrent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sounds, minDelays, maxDelays, replaceCurrent);
    }

    @Override
    public String toString() {
        return "MusicPredicate[" +
                "sounds=" + sounds + ", " +
                "minDelays=" + minDelays + ", " +
                "maxDelays=" + maxDelays + ", " +
                "replaceCurrent=" + replaceCurrent + ']';
    }

}
