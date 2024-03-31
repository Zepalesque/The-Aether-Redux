package net.zepalesque.redux.world.biome.modifier;

import com.aetherteam.aether.data.resources.AetherMobCategory;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.zepalesque.redux.api.condition.AbstractCondition;

import javax.xml.crypto.Data;
import java.util.List;

public record ZephyrSpawnsModifier(HolderSet<Biome> biomes, AbstractCondition<?> condition) implements BiomeModifier {

    @Override
    public void modify(Holder<Biome> biome, BiomeModifier.Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder)
    {
        if (phase == BiomeModifier.Phase.ADD && this.biomes.contains(biome) && this.condition.isConditionMet())
        {
            MobSpawnSettingsBuilder spawns = builder.getMobSpawnSettings();
            spawns.getSpawner(AetherMobCategory.AETHER_SKY_MONSTER).replaceAll(data -> data.type != AetherEntityTypes.ZEPHYR.get() ? data :
                    new MobSpawnSettings.SpawnerData(data.type, data.getWeight().asInt() * 4, data.minCount, data.maxCount + 2));

        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec()
    {
        return ReduxBiomeModifierCodecs.ZEPHYR_MODIFY.get();
    }
}
