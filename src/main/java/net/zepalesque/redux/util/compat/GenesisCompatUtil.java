package net.zepalesque.redux.util.compat;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.natural.AercloudBlock;
import com.aetherteam.aether.data.resources.registries.AetherBiomes;
import com.aetherteam.aether_genesis.GenesisConfig;
import com.aetherteam.aether_genesis.block.GenesisBlocks;
import com.aetherteam.aether_genesis.client.particle.GenesisParticleTypes;
import com.aetherteam.aether_genesis.data.resources.registries.GenesisBiomes;
import com.aetherteam.aether_genesis.entity.GenesisEntityTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.util.Lazy;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.advancement.predicate.StatePredicate;
import net.zepalesque.redux.client.particle.ReduxParticleTypes;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

public class GenesisCompatUtil {

    private static final Lazy<Map<Block, Supplier<? extends ParticleOptions>>> PARTICLE_MAP = Lazy.of(() -> new ImmutableMap.Builder<Block, Supplier<? extends ParticleOptions>>()
            .put(GenesisBlocks.BLUE_SKYROOT_LEAVES.get(), ReduxParticleTypes.FALLING_BLUE_SKYROOT_LEAVES)
            .put(GenesisBlocks.DARK_BLUE_SKYROOT_LEAVES.get(), ReduxParticleTypes.FALLING_DARK_BLUE_SKYROOT_LEAVES).build());

    public static @Nullable Supplier<? extends ParticleOptions> getParticle(Block self) {
        return PARTICLE_MAP.get().get(self);
    }

    public static boolean isPurpleCrystal(Supplier<? extends ParticleOptions> particle) {
        return particle == GenesisParticleTypes.PURPLE_CRYSTAL_LEAVES;
    }
    public static boolean isPurpleCrystal(ParticleOptions particle) {
        return particle == GenesisParticleTypes.PURPLE_CRYSTAL_LEAVES.get();
    }

    public static boolean isSkyrootMimic(EntityType<?> type) {
        return Redux.aetherGenesisCompat() && type == GenesisEntityTypes.SKYROOT_MIMIC.get();
    }

    public static boolean isBattleSentry(EntityType<?> type) {
        return Redux.aetherGenesisCompat() && type == GenesisEntityTypes.BATTLE_SENTRY.get();
    }

    public static boolean goldCloudSFX(AercloudBlock block) {
        if (!Redux.aetherGenesisCompat()) {
            return false;
        }
        return block == AetherBlocks.GOLDEN_AERCLOUD.get() && GenesisConfig.COMMON.gold_aercloud_ability.get();
    }

    public static ImmutableMap.Builder<ResourceKey<Biome>, Integer> addBiomesToMap(ImmutableMap.Builder<ResourceKey<Biome>, Integer> builder) {
        if (!Redux.aetherGenesisCompat()) {
            return builder;
        }
        return builder
                .put(GenesisBiomes.VIBRANT_FOREST, 0xA2F2BC)
                .put(GenesisBiomes.VIBRANT_WOODLAND, 0x96E8B0)
                .put(GenesisBiomes.VIBRANT_MEADOW, 0xBAFFCB);
    }
}
