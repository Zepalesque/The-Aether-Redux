package net.zepalesque.redux.data.resource;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.misc.ReduxTags;
import net.zepalesque.redux.world.carver.ReduxCarvers;

public class ReduxConfiguredCarvers {
	public static final ResourceKey<ConfiguredWorldCarver<?>> AETHER_CAVES = createKey("aether_caves");

	private static ResourceKey<ConfiguredWorldCarver<?>> createKey(String name) {
		return ResourceKey.create(Registries.CONFIGURED_CARVER, new ResourceLocation(Redux.MODID, name));
	}

	public static void bootstrap(BootstapContext<ConfiguredWorldCarver<?>> context) {
		HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);
		context.register(AETHER_CAVES, createAetherCaves(blocks));
	}

	public static ConfiguredWorldCarver<?> createAetherCaves(HolderGetter<Block> blocks) {
		HeightProvider y = UniformHeight.of(VerticalAnchor.aboveBottom(8), VerticalAnchor.absolute(180));
		FloatProvider yScale = UniformFloat.of(0.1F, 0.9F);
		FloatProvider horizRadMult = UniformFloat.of(0.7F, 1.4F);
		FloatProvider vertRadMult = UniformFloat.of(0.8F, 1.3F);
		FloatProvider floor = UniformFloat.of(-1.0F, -0.4F);
		CaveCarverConfiguration config = new CaveCarverConfiguration(
				0.2F,
				y,
				yScale,
				VerticalAnchor.aboveBottom(0),
				blocks.getOrThrow(ReduxTags.Blocks.AETHER_CARVER_REPLACEABLES),
				horizRadMult,
				vertRadMult,
				floor);
		return new ConfiguredWorldCarver<>(ReduxCarvers.AETHER_CAVE.get(), config);
	}
}