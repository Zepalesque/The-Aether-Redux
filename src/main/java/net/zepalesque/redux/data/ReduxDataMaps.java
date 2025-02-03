package net.zepalesque.redux.data;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.zepalesque.redux.Redux;

public class ReduxDataMaps {

    private static Codec<Pair<ParticleOptions, Integer>> PARTICLE_AND_CHANCE_CODEC = Codec.pair(ParticleTypes.CODEC, Codec.intRange(0, Integer.MAX_VALUE));


    public static DataMapType<Block, Pair<ParticleOptions, Integer>> LEAF_PARTICLES = DataMapType.builder(Redux.loc("leaf_particles"), Registries.BLOCK, PARTICLE_AND_CHANCE_CODEC).synced(PARTICLE_AND_CHANCE_CODEC, false).build();
}
