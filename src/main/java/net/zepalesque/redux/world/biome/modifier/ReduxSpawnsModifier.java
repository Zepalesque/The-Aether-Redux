package net.zepalesque.redux.world.biome.modifier;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.zepalesque.redux.api.condition.AbstractCondition;

import java.util.List;
import java.util.function.Function;

public record ReduxSpawnsModifier(HolderSet<Biome> biomes, List<MobSpawnSettings.SpawnerData> spawners, AbstractCondition<?> condition, double charge, double energyBudget) implements BiomeModifier {

    public static final Codec<ReduxSpawnsModifier> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Biome.LIST_CODEC.fieldOf("biomes").forGetter(ReduxSpawnsModifier::biomes),
    // Allow either a list or single spawner, attempting to decode the list format first.
    // Uses the better EitherCodec that logs both errors if both formats fail to parse.
                    new ExtraCodecs.EitherCodec<>(MobSpawnSettings.SpawnerData.CODEC.listOf(), MobSpawnSettings.SpawnerData.CODEC).xmap(
            either -> either.map(Function.identity(), List::of), // convert list/singleton to list when decoding
    list -> list.size() == 1 ? Either.right(list.get(0)) : Either.left(list) // convert list to singleton/list when encoding
            ).fieldOf("spawners").forGetter(ReduxSpawnsModifier::spawners),
                    AbstractCondition.CODEC.fieldOf("condition").forGetter(ReduxSpawnsModifier::condition),
                    Codec.DOUBLE.fieldOf("charge").forGetter(ReduxSpawnsModifier::charge),
                    Codec.DOUBLE.fieldOf("energy_budget").forGetter(ReduxSpawnsModifier::energyBudget)
            ).apply(builder, ReduxSpawnsModifier::new));

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
                spawns.addMobCharge(type, this.charge, this.energyBudget);
            }
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec()
    {
        return ReduxBiomeModifierCodecs.MOB_SPAWN_CONFIG.get();
    }
}
