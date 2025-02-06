package net.zepalesque.redux.data;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.WeightedParticleEntry;

import java.util.HashSet;
import java.util.Set;

public class ReduxDataMaps {
    public static final Set<DataMapType<?, ?>> TYPES = new HashSet<>();

    public static final DataMapType<Block, WeightedParticleEntry> LEAF_PARTICLES = register(
            DataMapType.builder(Redux.loc("leaf_particles"), Registries.BLOCK, WeightedParticleEntry.CODEC).synced(WeightedParticleEntry.CODEC, false).build()
    );

    public static <A, B> DataMapType<A, B> register(DataMapType<A, B> map) {
        TYPES.add(map);
        return map;
    }

}
