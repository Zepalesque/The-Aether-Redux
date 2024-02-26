package net.zepalesque.redux.world.biome.modifier;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.zepalesque.redux.api.condition.AbstractCondition;

import java.util.List;

public record ConditionalSpawnsModifier(HolderSet<Biome> biomes, List<MobSpawnSettings.SpawnerData> spawners, AbstractCondition<?> condition) implements BiomeModifier {
    public static ForgeBiomeModifiers.AddSpawnsBiomeModifier singleSpawn(HolderSet<Biome> biomes, MobSpawnSettings.SpawnerData spawner)
    {
        return new ForgeBiomeModifiers.AddSpawnsBiomeModifier(biomes, List.of(spawner));
    }

    @Override
    public void modify(Holder<Biome> biome, BiomeModifier.Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder)
    {
        if (phase == BiomeModifier.Phase.ADD && this.biomes.contains(biome) && this.condition.isConditionMet())
        {
            MobSpawnSettingsBuilder spawns = builder.getMobSpawnSettings();
            for (MobSpawnSettings.SpawnerData spawner : this.spawners)
            {
                EntityType<?> type = spawner.type;
                spawns.addSpawn(type.getCategory(), spawner);
            }
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec()
    {
        return ReduxBiomeModifierCodecs.CONDITIONAL_SPAWNS.get();
    }
}
