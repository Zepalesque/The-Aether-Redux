package net.zepalesque.redux.block;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.dungeon.DoorwayBlock;
import com.aetherteam.aether.block.dungeon.TrappedBlock;
import com.aetherteam.aether.block.natural.AetherDoubleDropBlock;
import com.aetherteam.aether.block.natural.AetherDoubleDropsLeaves;
import com.aetherteam.aether.entity.AetherEntityTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SeagrassBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.dungeon.DoorwayPillarBlock;
import net.zepalesque.redux.block.dungeon.RunelightBlock;
import net.zepalesque.redux.block.dungeon.TrappedPillarBlock;
import net.zepalesque.redux.block.natural.BloomtailBlock;
import net.zepalesque.redux.block.natural.CaelgaeBlock;
import net.zepalesque.redux.block.natural.BlightedGrassBlock;
import net.zepalesque.redux.block.natural.DoubleDropsMossCarpet;
import net.zepalesque.redux.block.natural.GoldenCloversBlock;
import net.zepalesque.redux.block.natural.HangingAetherVinesBody;
import net.zepalesque.redux.block.natural.HangingAetherVinesHead;
import net.zepalesque.redux.block.natural.TurboVerbenaBlock;
import net.zepalesque.redux.block.natural.crop.WyndoatsBlock;
import net.zepalesque.redux.block.natural.leaves.InfectedLeavesBlock;
import net.zepalesque.redux.block.natural.leaves.ShadedLeavesBlock;
import net.zepalesque.redux.block.redstone.LogicatorBlock;
import net.zepalesque.redux.data.resource.registries.ReduxFeatureConfig;
import net.zepalesque.unity.block.natural.DoubleDropsCarpet;
import net.zepalesque.unity.block.natural.DoubleDropsGrowthBlock;
import net.zepalesque.unity.block.natural.bush.CustomBoundsBushBlock;
import net.zepalesque.unity.block.natural.leaves.LeafPileBlock;
import net.zepalesque.unity.event.hook.BlockHooks;
import net.zepalesque.zenith.api.blockset.type.AbstractWoodSet;
import net.zepalesque.zenith.mixin.mixins.common.accessor.FireAccessor;
import net.zepalesque.zenith.util.block.CommonPlantBounds;

public class ReduxBlocks extends ReduxBlockBuilders {
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Redux.MODID);

	public static final DeferredBlock<AetherDoubleDropsLeaves> GILDENROOT_LEAVES = register(
		"gildenroot_leaves",
		() -> new AetherDoubleDropsLeaves(
			Properties.ofFullCopy(AetherBlocks.SKYROOT_LEAVES.get()).mapColor(MapColor.QUARTZ)
		)
	);

	public static final DeferredBlock<BlightedGrassBlock> BLIGHTED_AETHER_GRASS_BLOCK = register(
		"blighted_aether_grass_block",
		() -> new BlightedGrassBlock(Properties.ofFullCopy(AetherBlocks.AETHER_GRASS_BLOCK.get()))
	);

	public static final DeferredBlock<LeafPileBlock> GILDENROOT_LEAF_PILE = register(
		"gildenroot_leaf_pile",
		() -> new LeafPileBlock(GILDENROOT_LEAVES)
	);

	public static final DeferredBlock<ShadedLeavesBlock> STORMROOT_LEAVES = register(
		"stormroot_leaves",
		() -> new ShadedLeavesBlock(
			3,
			Properties.ofFullCopy(AetherBlocks.SKYROOT_LEAVES.get()).mapColor(
				MapColor.TERRACOTTA_PURPLE
			)
		)
	);

	public static final DeferredBlock<LeafPileBlock> STORMROOT_LEAF_PILE = register(
		"stormroot_leaf_pile",
		() -> new LeafPileBlock(STORMROOT_LEAVES)
	);

	public static final DeferredBlock<ShadedLeavesBlock> BLIGHTWILLOW_LEAVES = register(
		"blightwillow_leaves",
		() -> new ShadedLeavesBlock(
			5,
			Properties.ofFullCopy(AetherBlocks.SKYROOT_LEAVES.get()).mapColor(
				MapColor.TERRACOTTA_PURPLE
			)
		)
	);

	// TODO: custom particles (more, block breaking and stuff) + use real particle other than obsidian????
	// TODO: Rename, add drip particles, make these work like crystal leaves perhaps (maybe, or just ALWAYS use berry bush like behavior)
	public static final DeferredBlock<InfectedLeavesBlock> INFECTED_BLIGHTWILLOW_LEAVES = register(
		"infected_blightwillow_leaves",
		() -> new InfectedLeavesBlock(
			() -> ParticleTypes.DRIPPING_OBSIDIAN_TEAR,
			Properties.ofFullCopy(BLIGHTWILLOW_LEAVES.get())
				.lightLevel(value -> 7)
				.strength(0.3F)
		)
	);

	public static final DeferredBlock<LeafPileBlock> BLIGHTWILLOW_LEAF_PILE = register(
		"blightwillow_leaf_pile",
		() -> new LeafPileBlock(BLIGHTWILLOW_LEAVES)
	);

	public static final DeferredBlock<GoldenCloversBlock> GOLDEN_CLOVERS = register("golden_clovers", () ->
		new GoldenCloversBlock(
			BlockBehaviour.Properties.of()
				.mapColor(MapColor.GOLD)
				.noCollission()
				.sound(SoundType.PINK_PETALS)
				.pushReaction(PushReaction.DESTROY)
				.replaceable()
		)
	);

	// TODO: Moss BlockSet
	public static final DeferredBlock<DoubleDropsGrowthBlock> BLEAKMOSS_BLOCK = register(
		"bleakmoss_block",
		() -> new DoubleDropsGrowthBlock(
			Properties.ofFullCopy(Blocks.MOSS_BLOCK).mapColor(MapColor.TERRACOTTA_MAGENTA),
			ReduxFeatureConfig.BLEAKMOSS_BONEMEAL
		)
	);

	public static final DeferredBlock<DoubleDropsMossCarpet> BLEAKMOSS_CARPET = register(
		"bleakmoss_carpet",
		() -> new DoubleDropsMossCarpet(
			Properties.ofFullCopy(Blocks.MOSS_CARPET).mapColor(MapColor.TERRACOTTA_MAGENTA)
		)
	);

	public static final DeferredBlock<DoubleDropsGrowthBlock> GILDENMOSS_BLOCK = register(
		"gildenmoss_block",
		() -> new DoubleDropsGrowthBlock(
			Properties.ofFullCopy(Blocks.MOSS_BLOCK).mapColor(MapColor.TERRACOTTA_YELLOW),
			ReduxFeatureConfig.GILDENMOSS_BONEMEAL
		)
	);

	public static final DeferredBlock<DoubleDropsCarpet> GILDENMOSS_CARPET = register(
		"gildenmoss_carpet",
		() -> new DoubleDropsCarpet(
			Properties.ofFullCopy(Blocks.MOSS_CARPET).mapColor(MapColor.TERRACOTTA_YELLOW)
		)
	);

	public static final DeferredBlock<Block> CARVED_PILLAR = register("carved_pillar", () ->
		new RotatedPillarBlock(
			Properties.of()
				.mapColor(MapColor.STONE)
				.instrument(NoteBlockInstrument.BASEDRUM)
				.strength(0.5F, 6.0F)
				.requiresCorrectToolForDrops()
		)
	);

	public static final DeferredBlock<Block> SENTRY_PILLAR = register("sentry_pillar", () ->
		new RotatedPillarBlock(Properties.ofFullCopy(CARVED_PILLAR.get()).lightLevel(state -> 11))
	);

	public static final DeferredBlock<Block> CARVED_BASE = register("carved_base", () ->
		new Block(
			Properties.of()
				.mapColor(MapColor.STONE)
				.instrument(NoteBlockInstrument.BASEDRUM)
				.strength(0.5F, 6.0F)
				.requiresCorrectToolForDrops()
		)
	);

	public static final DeferredBlock<Block> SENTRY_BASE = register("sentry_base", () ->
		new Block(Properties.ofFullCopy(CARVED_BASE.get()).lightLevel(state -> 11))
	);

	public static final DeferredBlock<Block> LOCKED_CARVED_PILLAR = register(
		"locked_carved_pillar",
		() -> new RotatedPillarBlock(
			Properties.of()
				.mapColor(MapColor.STONE)
				.instrument(NoteBlockInstrument.BASEDRUM)
				.strength(-1.0F, 3600000.0F)
		)
	);

	public static final DeferredBlock<Block> LOCKED_SENTRY_PILLAR = register(
		"locked_sentry_pillar",
		() -> new RotatedPillarBlock(
			Properties.ofFullCopy(LOCKED_CARVED_PILLAR.get()).lightLevel(state -> 11)
		)
	);

	public static final DeferredBlock<Block> LOCKED_CARVED_BASE = register("locked_carved_base", () ->
		new Block(
			Properties.of()
				.mapColor(MapColor.STONE)
				.instrument(NoteBlockInstrument.BASEDRUM)
				.strength(-1.0F, 3600000.0F)
		)
	);

	public static final DeferredBlock<Block> LOCKED_SENTRY_BASE = register("locked_sentry_base", () ->
		new Block(Properties.ofFullCopy(LOCKED_CARVED_BASE.get()).lightLevel(state -> 11))
	);

	public static final DeferredBlock<Block> TRAPPED_CARVED_PILLAR = register(
		"trapped_carved_pillar",
		() -> new TrappedPillarBlock(
			AetherEntityTypes.SENTRY::get,
			() -> CARVED_PILLAR.get().defaultBlockState(),
			Properties.ofFullCopy(CARVED_PILLAR.get())
		)
	);

	public static final DeferredBlock<Block> TRAPPED_SENTRY_PILLAR = register(
		"trapped_sentry_pillar",
		() -> new TrappedPillarBlock(
			AetherEntityTypes.SENTRY::get,
			() -> SENTRY_PILLAR.get().defaultBlockState(),
			Properties.ofFullCopy(SENTRY_PILLAR.get())
		)
	);

	public static final DeferredBlock<Block> TRAPPED_CARVED_BASE = register(
		"trapped_carved_base",
		() -> new TrappedBlock(
			AetherEntityTypes.SENTRY::get,
			() -> CARVED_BASE.get().defaultBlockState(),
			Properties.ofFullCopy(CARVED_BASE.get())
		)
	);

	public static final DeferredBlock<Block> TRAPPED_SENTRY_BASE = register(
		"trapped_sentry_base",
		() -> new TrappedBlock(
			AetherEntityTypes.SENTRY::get,
			() -> SENTRY_BASE.get().defaultBlockState(),
			Properties.ofFullCopy(SENTRY_BASE.get())
		)
	);

	public static final DeferredBlock<Block> BOSS_DOORWAY_CARVED_PILLAR = register(
		"boss_doorway_carved_pillar",
		() -> new DoorwayPillarBlock(
			AetherEntityTypes.SLIDER::get,
			Properties.ofFullCopy(CARVED_PILLAR.get())
		)
	);

	public static final DeferredBlock<Block> BOSS_DOORWAY_SENTRY_PILLAR = register(
		"boss_doorway_sentry_pillar",
		() -> new DoorwayPillarBlock(
			AetherEntityTypes.SLIDER::get,
			Properties.ofFullCopy(SENTRY_PILLAR.get())
		)
	);

	public static final DeferredBlock<Block> BOSS_DOORWAY_CARVED_BASE = register(
		"boss_doorway_carved_base",
		() -> new DoorwayBlock(AetherEntityTypes.SLIDER::get, Properties.ofFullCopy(CARVED_BASE.get()))
	);

	public static final DeferredBlock<Block> BOSS_DOORWAY_SENTRY_BASE = register(
		"boss_doorway_sentry_base",
		() -> new DoorwayBlock(AetherEntityTypes.SLIDER::get, Properties.ofFullCopy(SENTRY_BASE.get()))
	);

	public static final DeferredBlock<Block> RUNELIGHT = register("runelight", () ->
		new RunelightBlock(
			Properties.of()
				.mapColor(state ->
					state.getValue(RunelightBlock.LIT) ? MapColor.COLOR_LIGHT_BLUE : MapColor.LAPIS
				)
				.lightLevel(state -> state.getValue(RunelightBlock.LIT) ? 13 : 1)
				.strength(0.7F, 6.0F)
				.sound(SoundType.COPPER_BULB)
				.requiresCorrectToolForDrops()
				.instrument(NoteBlockInstrument.IRON_XYLOPHONE),
			false
		)
	);

	public static final DeferredBlock<Block> LOCKED_RUNELIGHT = register("locked_runelight", () ->
		new RunelightBlock(
			Properties.of()
				.mapColor(state ->
					state.getValue(RunelightBlock.LIT) ? MapColor.COLOR_LIGHT_BLUE : MapColor.LAPIS
				)
				.lightLevel(state -> state.getValue(RunelightBlock.LIT) ? 13 : 1)
				.strength(-1.0F, 3600000.0F)
				.sound(SoundType.COPPER_BULB)
				.instrument(NoteBlockInstrument.IRON_XYLOPHONE),
			true
		)
	);

	public static final DeferredBlock<Block> LOCKED_POLISHED_SENTRITE = register(
		"locked_polished_sentrite",
		() -> new Block(
			Properties.of()
				.mapColor(MapColor.DEEPSLATE)
				.strength(-1.0F, 3600000.0F)
				.sound(SoundType.NETHER_BRICKS)
				.instrument(NoteBlockInstrument.BASEDRUM)
		)
	);

	public static final DeferredBlock<Block> WYNDSPROUTS = register("wyndsprouts", () ->
		new CustomBoundsBushBlock.Enchanted(
			CommonPlantBounds.BUSH,
			Properties.ofFullCopy(Blocks.SHORT_GRASS)
				.sound(SoundType.CHERRY_SAPLING)
				.offsetType(OffsetType.XZ)
				.hasPostProcess((s, l, p) -> true)
		)
	);

	public static final DeferredBlock<Block> SKYSPROUTS = register("skysprouts", () ->
		new CustomBoundsBushBlock(
			CommonPlantBounds.BUSH,
			Properties.ofFullCopy(Blocks.SHORT_GRASS)
				.sound(SoundType.CHERRY_SAPLING)
				.offsetType(OffsetType.XZ)
		)
	);

	public static final DeferredBlock<Block> WYNDOATS = BLOCKS.register("wyndoats", () ->
		new WyndoatsBlock(Properties.ofFullCopy(Blocks.WHEAT))
	);

	public static final DeferredBlock<Block> TURBO_VERBENA = register("turbo_verbena", () ->
		new TurboVerbenaBlock(
			MobEffects.MOVEMENT_SPEED,
			3,
			Properties.ofFullCopy(Blocks.TALL_GRASS).sound(SoundType.WET_GRASS)
		)
	);
	
	public static final DeferredBlock<CaelgaeBlock> CAELGAE_PATCH = BLOCKS.register(
		"caelgae_patch",
		() -> new CaelgaeBlock(BlockBehaviour.Properties.of()
			.mapColor(MapColor.TERRACOTTA_GREEN)
			.sound(SoundType.SMALL_DRIPLEAF)
			.noOcclusion()
			.noCollission()
			.replaceable()
			.pushReaction(PushReaction.DESTROY)
			.strength(0.1F)
		)
	);
	
	public static final DeferredBlock<BloomtailBlock> BLOOMTAIL = register(
		"bloomtail",
		() -> new BloomtailBlock(
			BlockBehaviour.Properties.of()
				.mapColor(MapColor.WATER)
				.replaceable()
				.noCollission()
				.instabreak()
				.sound(SoundType.WET_GRASS)
				.pushReaction(PushReaction.DESTROY)
		)
	);

	public static final DeferredBlock<Block> SENTRITE_CHAIN = register("sentrite_chain", () ->
		new ChainBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHAIN))
	);

	public static final DeferredBlock<Block> SENTRITE_LANTERN = register("sentrite_lantern", () ->
		new LanternBlock(
			BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN)
				.mapColor(MapColor.DEEPSLATE)
				.lightLevel(state -> 13)
		)
	);

	public static final DeferredBlock<Block> SENTRITE_BARS = register("sentrite_bars", () ->
		new IronBarsBlock(
			BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.DEEPSLATE)
		)
	);

	public static final DeferredBlock<Block> RUNIC_LANTERN = register(
		// misread this as rustc, the rust brainrot is real
		"runic_lantern",
		() -> new LanternBlock(
			BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN)
				.mapColor(MapColor.DEEPSLATE)
				.lightLevel(state -> 8)
		)
	);

	public static final DeferredBlock<Block> VERIDIUM_ORE = register("veridium_ore", () ->
		new AetherDoubleDropBlock(
			Block.Properties.of()
				.mapColor(MapColor.WOOL)
				.instrument(NoteBlockInstrument.BASEDRUM)
				.strength(3.0F)
				.requiresCorrectToolForDrops()
		)
	);

	public static final DeferredBlock<Block> RAW_VERIDIUM_BLOCK = register("raw_veridium_block", () ->
		new Block(
			BlockBehaviour.Properties.of()
				.mapColor(MapColor.LAPIS)
				.instrument(NoteBlockInstrument.BASEDRUM)
				.requiresCorrectToolForDrops()
				.sound(SoundType.STONE)
				.strength(3.0F, 6.0F)
		)
	);

	public static final DeferredBlock<Block> VERIDIUM_BLOCK = register("veridium_block", () ->
		new Block(
			BlockBehaviour.Properties.of()
				.mapColor(MapColor.LAPIS)
				//.instrument(NoteBlockInstrument.)
				.requiresCorrectToolForDrops()
				.strength(5.0F, 6.0F)
				.sound(SoundType.METAL)
		)
	);

	public static final DeferredBlock<Block> REFINED_SENTRITE_BLOCK = register(
		"refined_sentrite_block",
		() -> new Block(
			BlockBehaviour.Properties.of()
				.mapColor(MapColor.COLOR_GRAY)
				//.instrument(NoteBlockInstrument.IRON_XYLOPHONE)
				.requiresCorrectToolForDrops()
				.strength(6.0F, 6.0F)
				.sound(SoundType.NETHERITE_BLOCK)
		)
	);

	public static final DeferredBlock<HangingAetherVinesHead> GOLDEN_VINES = register("golden_vines", () ->
		new HangingAetherVinesHead(
			BlockBehaviour.Properties.ofFullCopy(Blocks.WEEPING_VINES)
				.mapColor(MapColor.GOLD)
				.sound(SoundType.CAVE_VINES),
			BlockTags.LEAVES,
			ReduxBlocks.GOLDEN_VINES_PLANT
		)
	);

	public static final DeferredBlock<HangingAetherVinesBody> GOLDEN_VINES_PLANT = BLOCKS.register(
		"golden_vines_plant",
		() -> new HangingAetherVinesBody(
			BlockBehaviour.Properties.ofFullCopy(Blocks.WEEPING_VINES_PLANT)
				.mapColor(MapColor.GOLD)
				.sound(SoundType.CAVE_VINES),
			BlockTags.LEAVES,
			ReduxBlocks.GOLDEN_VINES
		)
	);

	public static final DeferredBlock<LogicatorBlock> LOGICATOR = register("logicator", () ->
		new LogicatorBlock(
			BlockBehaviour.Properties.of()
				.instabreak()
				.sound(SoundType.STONE)
				.pushReaction(PushReaction.DESTROY)
		)
	);

	public static void registerFlammability() {
		var fire = (FireAccessor) Blocks.FIRE;
		Redux.BLOCK_SETS.forEach(set -> set.flammables(fire));
	}

	public static void registerToolConversions() {
		Redux.BLOCK_SETS.forEach(set -> {
			if (set instanceof AbstractWoodSet wood) wood.setupStrippables(
				BlockHooks.ToolConversions.STRIPPABLES
			);
		});
	}
}
