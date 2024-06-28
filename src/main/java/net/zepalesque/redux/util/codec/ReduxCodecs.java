package net.zepalesque.redux.util.codec;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;

import java.util.Map;

public class ReduxCodecs {

    public static final Codec<Map<Holder<Biome>, Integer>> MAP_CODEC = Codec.unboundedMap(Biome.CODEC, Codec.INT);

}
