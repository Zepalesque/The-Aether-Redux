package net.zepalesque.redux.data.resource;

import com.aetherteam.aether.data.resources.registries.AetherStructures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.common.world.StructureModifier;
import net.neoforged.neoforge.common.world.StructureModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.audio.ReduxMusic;
import net.zepalesque.redux.data.ReduxTags;
import net.zepalesque.zenith.Zenith;
import net.zepalesque.zenith.api.condition.Condition;
import net.zepalesque.zenith.util.codec.CodecPredicates;
import net.zepalesque.zenith.world.biome.modifier.ConditionalBiomeModifier;
import net.zepalesque.zenith.world.biome.modifier.MusicModifier;
import net.zepalesque.zenith.world.biome.modifier.SkyModifier;
import net.zepalesque.zenith.world.structure.modifier.ConditionalStructureModifier;
import net.zepalesque.zenith.world.structure.modifier.RemoveStructureModifier;

import java.util.Optional;

public class ReduxStructureModifiers {
    public static final ResourceKey<StructureModifier> REMOVE_LARGE_CLOUDS = createKey("remove_large_clouds");

    private static ResourceKey<StructureModifier> createKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.STRUCTURE_MODIFIERS, Redux.loc(name));
    }

    public static void bootstrap(BootstapContext<StructureModifier> context) {
        HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);
        HolderGetter<Condition<?>> conditions = context.lookup(Zenith.Keys.CONDITION);

        StructureModifier largeCloud = new RemoveStructureModifier(HolderSet.direct(structures.getOrThrow(AetherStructures.LARGE_AERCLOUD)));
        context.register(REMOVE_LARGE_CLOUDS, new ConditionalStructureModifier(Holder.direct(largeCloud), conditions.get(ReduxConditions.CLOUDBED).orElseThrow()));
    }
}
