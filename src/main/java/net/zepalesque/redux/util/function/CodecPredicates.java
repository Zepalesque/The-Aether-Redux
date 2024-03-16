package net.zepalesque.redux.util.function;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.zepalesque.redux.world.biome.modifier.WaterColorReplacementBiomeModifier;

public class CodecPredicates {

    public static class DualInt {

        public final int arg1, arg2;
        DualInt(int arg1, int arg2) {
            this.arg1 = arg1;
            this.arg2 = arg2;
        }

        public static DualInt of(int water, int fog) {
            return new DualInt(water, fog);
        }

        public static Codec<DualInt> createCodec(String arg1, String arg2) {
            return RecordCodecBuilder.create((condition) ->
                    condition.group(
                                    Codec.INT.fieldOf(arg1).forGetter((config) -> config.arg1),
                                    Codec.INT.fieldOf(arg2).forGetter((config) -> config.arg2)
                            )
                            .apply(condition, DualInt::new));
        }

        public boolean test(int arg1, int arg2) {
            return arg1 == this.arg1 && arg2 == this.arg2;
        }
    }


    public static class Bool {

        public final boolean arg;
        Bool(boolean arg) {
            this.arg = arg;

        }

        public static Bool of(boolean arg) {
            return new Bool(arg);
        }

        public static Codec<Bool> CODEC = RecordCodecBuilder.create((condition) -> condition.group(
                        Codec.BOOL.fieldOf("val").forGetter((config) -> config.arg))
                .apply(condition, Bool::new));


        public boolean test(boolean bool) {
            return this.arg == bool;
        }
    }
    public static class Sound {

        public final Holder<SoundEvent> arg;
        Sound(Holder<SoundEvent> arg) {
            this.arg = arg;
        }

        public static Sound of(Holder<SoundEvent> arg) {
            return new Sound(arg);
        }

        public static Codec<Sound> CODEC = RecordCodecBuilder.create((condition) -> condition.group(
                                SoundEvent.CODEC.fieldOf("sound").forGetter((config) -> config.arg))
                        .apply(condition, Sound::new));

        public boolean test(Holder<SoundEvent> holder) {
            return this.arg == holder;
        }
    }
}