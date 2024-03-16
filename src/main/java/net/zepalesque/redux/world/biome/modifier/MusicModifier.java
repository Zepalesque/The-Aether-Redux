package net.zepalesque.redux.world.biome.modifier;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.zepalesque.redux.util.function.CodecPredicates;

import javax.swing.text.html.Option;
import java.util.Optional;

public record MusicModifier(
        HolderSet<Biome> biomes,
        Optional<Holder<SoundEvent>> event,
        Optional<CodecPredicates.DualInt> delay,
        Optional<Boolean> replaceCurrentMusic,
        Optional<CodecPredicates.Sound> soundPredicate,
        Optional<CodecPredicates.DualInt> delayPredicate,
        Optional<CodecPredicates.Bool> replacePredicate
) implements BiomeModifier {

    public static Codec<CodecPredicates.DualInt> DELAY = CodecPredicates.DualInt.createCodec("min", "max");

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (this.biomes.contains(biome)) {
            biome.get().getBackgroundMusic().map(this::processMusic);
        }
    }


    private Music processMusic(Music music) {
        if (this.soundPredicate.isEmpty() && this.delay.isEmpty() && this.replaceCurrentMusic.isEmpty()) {
            return music;
        }
        Holder<SoundEvent> event = music.getEvent();
        if (this.event.isPresent() && (this.soundPredicate.isEmpty() || this.soundPredicate.get().test(music.getEvent()))) {
            event = this.event.get();
        }
        int minDelay = music.getMinDelay();
        int maxDelay = music.getMaxDelay();
        if ((this.delayPredicate.isEmpty() || this.delayPredicate.get().test(music.getMinDelay(), music.getMaxDelay()))) {
            if (this.delay.isPresent()) {
                minDelay = this.delay.get().arg1;
                maxDelay = this.delay.get().arg2;
            }
        }
        boolean replace = music.replaceCurrentMusic();
        if (this.replaceCurrentMusic.isPresent() && (this.replacePredicate.isEmpty() || this.replacePredicate.get().test(music.replaceCurrentMusic()))) {
            replace = this.replaceCurrentMusic.get();
        }
        return new Music(event, minDelay, maxDelay, replace);
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return ReduxBiomeModifierCodecs.MUSIC.get();
    }
}
