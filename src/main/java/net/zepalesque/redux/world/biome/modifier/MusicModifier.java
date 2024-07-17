package net.zepalesque.redux.world.biome.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.zepalesque.redux.api.predicate.MusicPredicate;

import java.util.Optional;
import java.util.function.UnaryOperator;

public record MusicModifier(HolderSet<Biome> biomes, MusicOperator newMusic, Optional<MusicPredicate> predicate) implements BiomeModifier {


    public static final Codec<MusicModifier> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Biome.LIST_CODEC.fieldOf("biomes").forGetter(MusicModifier::biomes),
            MusicOperator.CODEC.fieldOf("new_track").forGetter(MusicModifier::newMusic),
            MusicPredicate.CODEC.optionalFieldOf("predicate").forGetter(MusicModifier::predicate)).apply(builder, MusicModifier::new));


    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.AFTER_EVERYTHING && this.biomes.contains(biome) && builder.getSpecialEffects().getBackgroundMusic().isPresent()) {
            Music music = builder.getSpecialEffects().getBackgroundMusic().get();
            if (predicate.isEmpty() || predicate.get().test(music)) {
                Music transformed = newMusic.apply(music);
                if (transformed != music) {
                    builder.getSpecialEffects().backgroundMusic(transformed);
                }
            }
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return CODEC;
    }

    public record MusicOperator(Optional<SoundEvent> sound, Optional<Integer> minDelay, Optional<Integer> maxDelay, Optional<Boolean> replaceCurrent) implements UnaryOperator<Music> {

        public static final Codec<MusicOperator> CODEC = RecordCodecBuilder.create(builder -> builder.group(
                SoundEvent.CODEC.optionalFieldOf("sound").forGetter(MusicOperator::sound),
                Codec.INT.optionalFieldOf("min_delay").forGetter(MusicOperator::minDelay),
                Codec.INT.optionalFieldOf("max_delay").forGetter(MusicOperator::maxDelay),
                Codec.BOOL.optionalFieldOf("replace_current").forGetter(MusicOperator::replaceCurrent)).apply(builder, MusicOperator::new));

        @Override
        public Music apply(Music music) {
            if (sound.isEmpty() && minDelay.isEmpty() && maxDelay.isEmpty() && replaceCurrent.isEmpty()) {
                return music;
            }
            SoundEvent soundEvent = music.getEvent();
            int minimum = music.getMinDelay();
            int maximum = music.getMaxDelay();
            boolean replace = music.replaceCurrentMusic();
            if (sound.isPresent()) { soundEvent = sound.get(); }
            if (minDelay.isPresent()) { minimum = minDelay.get(); }
            if (maxDelay.isPresent()) { maximum = maxDelay.get(); }
            if (replaceCurrent.isPresent()) { replace = replaceCurrent.get(); }
            return new Music(soundEvent, minimum, maximum, replace);
        }
    }
}
