package net.zepalesque.redux.data.resource.biome.registry;

import com.aetherteam.aether.data.resources.AetherMobCategory;
import com.aetherteam.aether.entity.AetherEntityTypes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.sounds.Music;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.zepalesque.redux.client.audio.ReduxMusic;

public interface BiomeGenerator {



    default Biome generate(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> placed = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> config = context.lookup(Registries.CONFIGURED_CARVER);
        return biomeBase(features(new BiomeGenerationSettings.Builder(placed, config)), mobspawns(new MobSpawnSettings.Builder()),
                music(), water(), waterFog(), ReduxBiomes.AETHER_GRASS_COLOR, sky(), skyFog(), precipitation(), temperature(), downfall());
    }


    default Music music() {
        return ReduxMusic.DEFAULT_AETHER_MUSIC;
    }
    default int water() {
        return ReduxBiomes.WATER;
    }
    default int waterFog() {
        return ReduxBiomes.WATER_FOG;
    }


    default int sky() {
        return 0xc0_c0_ff;
    }

    default int skyFog() {
        return 0x93_93_bc;
    }

    default boolean precipitation() {
        return false;
    }

    default float temperature() {
        return 0.8F;
    }
    default float downfall() {
        return 0.0F;
    }





    public MobSpawnSettings.Builder mobspawns(MobSpawnSettings.Builder builder);

    public BiomeGenerationSettings.Builder features(BiomeGenerationSettings.Builder builder);



    static Biome biomeBase(BiomeGenerationSettings.Builder gen, MobSpawnSettings.Builder mobSpawns, Music music, int waterColor, int waterFogColor, int grassColor, int skyColor, int skyFogColor, boolean precip, float temp, float downfall) {
        return ReduxBiomes.fullDefinition(
                precip,
                temp,
                downfall,
                new BiomeSpecialEffects.Builder()
                        .fogColor(skyFogColor)
                        .skyColor(skyColor)
                        .waterColor(waterColor)
                        .waterFogColor(waterFogColor)
                        .grassColorOverride(grassColor)
                        .foliageColorOverride(0xb1_ff_cb)
                        .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.NONE)
                        .backgroundMusic(music)
                        .build(),
                mobSpawns.build(),
                gen.build(),
                Biome.TemperatureModifier.NONE
        );
    }


    static MobSpawnSettings.Builder defaultMobs(MobSpawnSettings.Builder builder) {
        return builder
                .addMobCharge(AetherEntityTypes.COCKATRICE.get(), 0.5, 0.15)
                .addMobCharge(AetherEntityTypes.ZEPHYR.get(), 0.6, 0.16)
                .addMobCharge(AetherEntityTypes.AECHOR_PLANT.get(), 0.4,0.11)
                .addMobCharge(AetherEntityTypes.BLUE_SWET.get(), 0.5, 0.1)
                .addMobCharge(AetherEntityTypes.GOLDEN_SWET.get(), 0.5, 0.1)
                .addMobCharge(AetherEntityTypes.WHIRLWIND.get(), 0.4, 0.1)
                .addMobCharge(AetherEntityTypes.EVIL_WHIRLWIND.get(), 0.4, 0.1)
                .addMobCharge(AetherEntityTypes.AERWHALE.get(), 0.5, 0.11)

                .addSpawn(AetherMobCategory.AETHER_DARKNESS_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.COCKATRICE.get(), 8, 1, 1))
                .addSpawn(AetherMobCategory.AETHER_SKY_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.ZEPHYR.get(), 20, 1, 1))
                .addSpawn(AetherMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.AECHOR_PLANT.get(), 7, 1, 1))
                .addSpawn(AetherMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.BLUE_SWET.get(), 6, 1, 1))
                .addSpawn(AetherMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.GOLDEN_SWET.get(), 6, 1, 1))
                .addSpawn(AetherMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.WHIRLWIND.get(), 3, 1, 1))
                .addSpawn(AetherMobCategory.AETHER_SURFACE_MONSTER, new MobSpawnSettings.SpawnerData(AetherEntityTypes.EVIL_WHIRLWIND.get(), 1, 1, 1))
                .addSpawn(AetherMobCategory.AETHER_AERWHALE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.AERWHALE.get(), 10, 1, 1))

                .creatureGenerationProbability(0.25F)
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.PHYG.get(), 10, 3, 4))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.SHEEPUFF.get(), 12, 3, 4))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.FLYING_COW.get(), 12, 2, 5))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.AERBUNNY.get(), 11, 3, 3))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(AetherEntityTypes.MOA.get(), 8, 1, 3));
    }
}
