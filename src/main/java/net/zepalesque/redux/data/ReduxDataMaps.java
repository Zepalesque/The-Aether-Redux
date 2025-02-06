package net.zepalesque.redux.data;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.WeightedParticleEntry;

public class ReduxDataMaps {

    public static DataMapType<Block, WeightedParticleEntry> LEAF_PARTICLES = DataMapType.builder(Redux.loc("leaf_particles"), Registries.BLOCK, WeightedParticleEntry.CODEC).synced(WeightedParticleEntry.CODEC, false).build();
}
