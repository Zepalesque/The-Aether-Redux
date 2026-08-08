package net.zepalesque.redux.data.prov;

import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.dungeon.DoorwayBlock;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.TreeMap;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder.PartBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.backport.MossyCarpetBlock;
import net.zepalesque.redux.block.construction.LayeredBookshelfBlock;
import net.zepalesque.redux.block.dungeon.RunelightBlock;
import net.zepalesque.redux.block.redstone.LogicatorBlock;
import net.zepalesque.redux.block.state.ReduxStates;
import net.zepalesque.redux.block.state.enums.LogicatorMode;
import net.zepalesque.unity.Unity;
import net.zepalesque.unity.data.prov.UnityBlockStateProvider;
import net.zepalesque.zenith.util.ArrayUtil;

public abstract class ReduxBlockStateProvider extends UnityBlockStateProvider {
	public ReduxBlockStateProvider(PackOutput output, String id, ExistingFileHelper helper) {
		super(output, id, helper);
	}

	@Override
	public BlockModelBuilder makeWallPostModel(int width, int height, String name) {
		return super.makeWallPostModel(width, height, name);
	}

	@Override
	public BlockModelBuilder makeWallSideModel(
		int length,
		int height,
		String name,
		ModelBuilder.FaceRotation faceRotation,
		int u1,
		int u2
	) {
		return super.makeWallSideModel(length, height, name, faceRotation, u1, u2);
	}

	protected static final Map<Direction, BooleanProperty> DIRECTION_TO_PROPERTY = ImmutableMap.<
		Direction,
		BooleanProperty
	>builder()
		.put(Direction.DOWN, BlockStateProperties.DOWN)
		.put(Direction.UP, BlockStateProperties.UP)
		.put(Direction.WEST, BlockStateProperties.WEST)
		.put(Direction.EAST, BlockStateProperties.EAST)
		.put(Direction.SOUTH, BlockStateProperties.SOUTH)
		.put(Direction.NORTH, BlockStateProperties.NORTH)
		.build();

	// Degrees!!!!!
	protected static final Map<Direction, Vec3i> DIRECTION_TO_ROTATION = ImmutableMap.<
		Direction,
		Vec3i
	>builder()
		.put(Direction.DOWN, new Vec3i(90, 0, 0))
		.put(Direction.UP, new Vec3i(270, 0, 0))
		.put(Direction.WEST, new Vec3i(0, 270, 0))
		.put(Direction.EAST, new Vec3i(0, 90, 0))
		.put(Direction.SOUTH, new Vec3i(0, 180, 0))
		.put(Direction.NORTH, new Vec3i(0, 0, 0))
		.build();

	public void snowableLeaves(Block block, String location) {
		this.getVariantBuilder(block).forAllStatesExcept(
			(state) -> {
				boolean snowy = state.getValue(BlockStateProperties.SNOWY);
				return ConfiguredModel.builder()
					.modelFile(
						snowy
							? this.cubeBottomTop(
									this.name(block) + "_snowy",
									this.extend(this.texture(this.name(block), location), "_snowy"),
									this.texture(this.name(block), location),
									this.mcLoc("blocks/snow")
								)
							: this.cubeAll(block, location)
					)
					.build();
			},
			LeavesBlock.PERSISTENT,
			LeavesBlock.DISTANCE,
			LeavesBlock.WATERLOGGED,
			AetherBlockStateProperties.DOUBLE_DROPS
		);
	}

	public void createCloudcapBlock(Block block, String loc) {
		this.models()
			.withExistingParent(this.name(block), Unity.loc("block/template/cube/cube_all_glow"))
			.texture("all", this.texture(this.name(block) + "4", loc))
			.texture("glow", this.texture(this.name(block) + "4_glow", loc))
			.renderType("cutout");
		var out = ArrayUtil.generateContents(new ModelFile[5], (i) ->
			i == 0
				? this.models()
						.singleTexture(
							this.name(block) + i,
							this.mcLoc("block/template_single_face"),
							this.texture(block, loc).withSuffix(String.valueOf(i))
						)
						.renderType("cutout")
				: this.models()
						.withExistingParent(
							this.name(block) + i,
							Redux.loc("block/template/single_face_gloverlay")
						)
						.texture("texture", this.texture(block, loc).withSuffix(String.valueOf(i)))
						.texture("glow", this.texture(block, loc).withSuffix(i + "_glow"))
						.renderType("cutout")
		);
		ModelFile in = this.models()
			.singleTexture(
				this.name(block) + "_inside",
				this.mcLoc("block/template_single_face"),
				this.texture(block, loc).withSuffix("_inside")
			)
			.renderType("cutout");
		var builder = this.getMultipartBuilder(block);

		for (var d : Direction.values()) {
			var vec = DIRECTION_TO_ROTATION.get(d);
			// exterior
			for (var i = 0; i < out.length; i++) builder
				.part()
				.modelFile(out[i])
				.rotationX(vec.getX())
				.rotationY(vec.getY())
				.addModel()
				.condition(DIRECTION_TO_PROPERTY.get(d), true)
				.condition(ReduxStates.CLOUDCAP_VARIANT, i)
				.end();
			// interior
			builder
				.part()
				.modelFile(in)
				.rotationX(vec.getX())
				.rotationY(vec.getY())
				.addModel()
				.condition(DIRECTION_TO_PROPERTY.get(d), false)
				.end();
		}
	}

	public void permaGrass(
		Block block,
		Block dirt,
		String location,
		String dirtLocation,
		Property<?>... ignored
	) {
		var bottom = this.texture(dirt, dirtLocation);
		var top = this.texture(AetherBlocks.AETHER_GRASS_BLOCK.get(), "natural/", "_top");
		var overlay = this.texture(block, location, "_side_overlay");
		var side = this.texture(block, location, "_side");
		var snow = this.texture(block, location, "_side_snow");
		this.tintableGrassBlock(
			block,
			bottom,
			top,
			overlay,
			side,
			this.models().cubeBottomTop(this.nameID(block, "%s_snow"), snow, bottom, top),
			ignored
		);
	}

	public void leaves(Block block, String loc) {
		var texture = this.texture(block, loc);

		var model = this.models()
			.leaves(this.nameID(block), texture);

		this.getMultipartBuilder(block)
			.part()
			.modelFile(model)
			.addModel()
			.end();
	}

	public void layeredBookshelf(Block block, Block endBlock) {
		ModelFile bookshelf = this.models().cubeColumn(
			this.name(block),
			this.texture(this.name(block), "construction/"),
			this.texture(this.name(endBlock), "construction/")
		);
		ModelFile top = this.models().cubeColumn(
			this.name(block) + "_top",
			this.texture(this.name(block) + "_top", "construction/"),
			this.texture(this.name(endBlock), "construction/")
		);
		ModelFile bottom = this.models().cubeColumn(
			this.name(block) + "_bottom",
			this.texture(this.name(block) + "_bottom", "construction/"),
			this.texture(this.name(endBlock), "construction/")
		);
		ModelFile center = this.models().cubeColumn(
			this.name(block) + "_center",
			this.texture(this.name(block) + "_center", "construction/"),
			this.texture(this.name(endBlock), "construction/")
		);
		this.getVariantBuilder(block).forAllStates((state) -> {
			boolean up = state.getValue(LayeredBookshelfBlock.UP);
			boolean down = state.getValue(LayeredBookshelfBlock.DOWN);
			return ConfiguredModel.builder()
				.modelFile(up && down ? bookshelf : !up && down ? bottom : up ? top : center)
				.build();
		});
	}

	public void pillar(Block block, Block other, String location) {
		this.axisBlock(
			block,
			other,
			this.extend(this.texture(this.name(other), location), "_side"),
			this.extend(this.texture(this.name(other), location), "_top")
		);
	}

	public void pillar(Block block, String location) {
		this.pillar(block, block, location);
	}

	public void axisBlock(Block block, Block other, ResourceLocation side, ResourceLocation end) {
		this.axisBlock(
			block,
			this.models().cubeColumn(this.name(other), side, end),
			this.models().cubeColumnHorizontal(this.name(other) + "_horizontal", side, end)
		);
	}

	public void axisBlock(Block block, ModelFile vertical, ModelFile horizontal) {
		this.getVariantBuilder(block)
			.partialState()
			.with(BlockStateProperties.AXIS, Direction.Axis.Y)
			.modelForState()
			.modelFile(vertical)
			.addModel()
			.partialState()
			.with(BlockStateProperties.AXIS, Direction.Axis.Z)
			.modelForState()
			.modelFile(horizontal)
			.rotationX(90)
			.addModel()
			.partialState()
			.with(BlockStateProperties.AXIS, Direction.Axis.X)
			.modelForState()
			.modelFile(horizontal)
			.rotationX(90)
			.rotationY(90)
			.addModel();
	}

	public void baseBrick(Block block, Block other, String location) {
		ModelFile model = this.cubeBottomTop(
			this.name(other),
			this.extend(this.texture(this.name(other), location), "_side"),
			this.extend(this.texture(this.name(other), location), "_top"),
			this.extend(this.texture(this.name(other), location), "_top")
		);
		this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(model));
	}

	public void baseBrick(Block block, String location) {
		this.baseBrick(block, block, location);
	}

	public void invisibleBaseBrick(Block block, Block baseBlock, String location) {
		ModelFile visible = this.cubeBottomTop(
			this.name(baseBlock),
			this.extend(this.texture(this.name(baseBlock), location), "_side"),
			this.extend(this.texture(this.name(baseBlock), location), "_top"),
			this.extend(this.texture(this.name(baseBlock), location), "_top")
		);
		ModelFile invisible = this.models().getBuilder(this.name(block));
		this.getVariantBuilder(block).forAllStatesExcept((state) -> {
			if (!state.getValue(DoorwayBlock.INVISIBLE)) return ConfiguredModel.builder()
				.modelFile(visible)
				.build();
			else return ConfiguredModel.builder().modelFile(invisible).build();
		});
	}

	public void invisiblePillar(Block block, Block other, String location) {
		ResourceLocation side = this.extend(this.texture(this.name(other), location), "_side");
		ResourceLocation end = this.extend(this.texture(this.name(other), location), "_top");
		ModelFile vertical = this.models().cubeColumn(this.name(other), side, end);
		ModelFile horizontal = this.models().cubeColumnHorizontal(this.name(block) + "_horizontal", side, end);
		ModelFile invisible = this.models().getBuilder(this.name(block));
		this.getVariantBuilder(block)
			.partialState()
			.with(BlockStateProperties.AXIS, Direction.Axis.Y)
			.with(DoorwayBlock.INVISIBLE, false)
			.modelForState()
			.modelFile(vertical)
			.addModel()
			.partialState()
			.with(BlockStateProperties.AXIS, Direction.Axis.Z)
			.with(DoorwayBlock.INVISIBLE, false)
			.modelForState()
			.modelFile(horizontal)
			.rotationX(90)
			.addModel()
			.partialState()
			.with(BlockStateProperties.AXIS, Direction.Axis.X)
			.with(DoorwayBlock.INVISIBLE, false)
			.modelForState()
			.modelFile(horizontal)
			.rotationX(90)
			.rotationY(90)
			.addModel()
			.partialState()
			.with(BlockStateProperties.AXIS, Direction.Axis.Y)
			.with(DoorwayBlock.INVISIBLE, true)
			.modelForState()
			.modelFile(invisible)
			.addModel()
			.partialState()
			.with(BlockStateProperties.AXIS, Direction.Axis.Z)
			.with(DoorwayBlock.INVISIBLE, true)
			.modelForState()
			.modelFile(invisible)
			.addModel()
			.partialState()
			.with(BlockStateProperties.AXIS, Direction.Axis.X)
			.with(DoorwayBlock.INVISIBLE, true)
			.modelForState()
			.modelFile(invisible)
			.addModel();
	}

	public static ResourceLocation extendStatic(ResourceLocation location, String suffix) {
		return ResourceLocation.fromNamespaceAndPath(
			location.getNamespace(),
			location.getPath() + suffix
		);
	}

	public void cubeActivatable(Block block, String location) {
		this.cubeActivatable(block, block, location);
	}

	public void cubeActivatable(Block block, Block other, String location) {
		ModelFile off = this.models().cubeAll(
			this.name(other),
			this.texture(this.name(other), location)
		);
		ModelFile on = this.models().cubeAll(
			this.name(other) + "_on",
			this.texture(this.name(other) + "_on", location)
		);
		this.getVariantBuilder(block)
			.partialState()
			.with(RunelightBlock.LIT, true)
			.modelForState()
			.modelFile(on)
			.addModel()
			.partialState()
			.with(RunelightBlock.LIT, false)
			.modelForState()
			.modelFile(off)
			.addModel();
	}

	public void dungeonBlock(Block block, Block baseBlock, String location) {
		ConfiguredModel dungeonBlock = new ConfiguredModel(
			this.models().cubeAll(this.name(baseBlock), this.texture(this.name(baseBlock), location))
		);
		this.getVariantBuilder(block).partialState().setModels(dungeonBlock);
	}

	// Crop blocks
	public void cropGrowable(Block block, String location, IntegerProperty ageProperty) {
		this.getVariantBuilder(block).forAllStates((state) -> {
			int stage = state.getValue(ageProperty);
			BlockModelBuilder cross = this.models()
				.withExistingParent(
					this.name(block) + "_stage" + stage,
					Redux.loc(ModelProvider.BLOCK_FOLDER + "/template/crop/crop_lowered")
				)
				.texture("plant", this.texture(this.name(block) + "_stage" + stage, location))
				.renderType("cutout");
			return ConfiguredModel.builder().modelFile(cross).build();
		});
	}

	public void cropOccluded(Block block, String location) {
		BlockModelBuilder cross = this.models()
			.withExistingParent(
				this.name(block),
				Redux.loc(ModelProvider.BLOCK_FOLDER + "/template/crop/crop_occluded")
			)
			.texture("plant", this.texture(this.name(block), location))
			.renderType("cutout");
		this.crossBlock(block, cross);
	}

	public void crop(Block block, String location) {
		BlockModelBuilder cross = this.models()
			.withExistingParent(
				this.name(block),
				Redux.loc(ModelProvider.BLOCK_FOLDER + "/template/crop/crop")
			)
			.texture("plant", this.texture(this.name(block), location))
			.renderType("cutout");
		this.crossBlock(block, cross);
	}

	public void doublePlant(DoublePlantBlock block, String location) {
		this.getVariantBuilder(block).forAllStates(state -> {
			var suffix = switch (state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF)) {
				case LOWER -> "_lower";
				case UPPER -> "_upper";
			};
			var texture = this.texture(block, location, suffix);
			var model = this.models()
				.cross(this.name(block) + suffix, texture)
				.renderType(ResourceLocation.withDefaultNamespace("cutout"));

			return ConfiguredModel.builder()
				.modelFile(model)
				.build();
		});
	}

	public void lantern(Block block, String location) {
		BlockModelBuilder lantern = this.models()
			.withExistingParent(this.name(block), this.mcLoc("template_lantern"))
			.texture("lantern", this.texture(this.name(block), location))
			.renderType("cutout");
		BlockModelBuilder hangingLantern = this.models()
			.withExistingParent("hanging_" + this.name(block), this.mcLoc("template_hanging_lantern"))
			.texture("lantern", this.texture(this.name(block), location))
			.renderType("cutout");
		this.getVariantBuilder(block).forAllStates((state) ->
			ConfiguredModel.builder()
				.modelFile(state.getValue(LanternBlock.HANGING) ? hangingLantern : lantern)
				.build()
		);
	}

	public void chain(Block block, String location) {
		BlockModelBuilder chain = this.models()
			.withExistingParent(this.name(block), Redux.loc("block/template/construction/chain"))
			.texture("chain", this.texture(this.name(block), location))
			.renderType("cutout");
		this.getVariantBuilder(block).forAllStates((state) -> {
			Direction.Axis axis = state.getValue(ChainBlock.AXIS);
			ConfiguredModel.Builder<?> builder = ConfiguredModel.builder().modelFile(chain);
			if (axis == Direction.Axis.X) builder = builder.rotationY(90);
			if (axis != Direction.Axis.Y) builder = builder.rotationX(90);
			return builder.build();
		});
	}

	public void metalBars(Block block, String location) {
		final Map<Direction, BooleanProperty> directionProperties = new TreeMap<>(
			Map.ofEntries(
				Map.entry(Direction.NORTH, IronBarsBlock.NORTH),
				Map.entry(Direction.SOUTH, IronBarsBlock.SOUTH),
				Map.entry(Direction.EAST, IronBarsBlock.EAST),
				Map.entry(Direction.WEST, IronBarsBlock.WEST)
			)
		);

		ModelFile cap = this.models()
			.withExistingParent(
				this.name(block) + "_cap",
				Redux.loc("block/template/construction/bars/template_bars_cap")
			)
			.texture("bars", this.texture(this.name(block), location))
			.renderType("cutout");
		ModelFile capAlt = this.models()
			.withExistingParent(
				this.name(block) + "_cap_alt",
				Redux.loc("block/template/construction/bars/template_bars_cap_alt")
			)
			.texture("bars", this.texture(this.name(block), location))
			.renderType("cutout");
		ModelFile post = this.models()
			.withExistingParent(
				this.name(block) + "_post",
				Redux.loc("block/template/construction/bars/template_bars_post")
			)
			.texture("bars", this.texture(this.name(block), location))
			.renderType("cutout");
		ModelFile postEnds = this.models()
			.withExistingParent(
				this.name(block) + "_post_ends",
				Redux.loc("block/template/construction/bars/template_bars_post_ends")
			)
			.texture("bars", this.texture(this.name(block), location))
			.renderType("cutout");
		ModelFile side = this.models()
			.withExistingParent(
				this.name(block) + "_side",
				Redux.loc("block/template/construction/bars/template_bars_side")
			)
			.texture("bars", this.texture(this.name(block), location))
			.renderType("cutout");
		ModelFile sideAlt = this.models()
			.withExistingParent(
				this.name(block) + "_side_alt",
				Redux.loc("block/template/construction/bars/template_bars_side_alt")
			)
			.texture("bars", this.texture(this.name(block), location))
			.renderType("cutout");

		MultiPartBlockStateBuilder builder = this.getMultipartBuilder(block);
		builder.part().modelFile(postEnds).addModel().end();
		builder
			.part()
			.modelFile(post)
			.addModel()
			.condition(CrossCollisionBlock.NORTH, false)
			.condition(CrossCollisionBlock.SOUTH, false)
			.condition(CrossCollisionBlock.EAST, false)
			.condition(CrossCollisionBlock.WEST, false)
			.end();

		for (Map.Entry<Direction, BooleanProperty> entry : directionProperties.entrySet()) {
			Direction d = entry.getKey();
			BooleanProperty b = entry.getValue();
			Direction[] others = { d.getClockWise(), d.getOpposite(), d.getCounterClockWise() };
			boolean useAlt = d == Direction.SOUTH || d == Direction.WEST;
			int rot = d.getAxis() == Direction.Axis.Z ? 0 : 90;

			PartBuilder b1 = builder
				.part()
				.modelFile(useAlt ? capAlt : cap)
				.rotationY(rot)
				.addModel()
				.condition(b, true);
			for (Direction other : others) b1.condition(directionProperties.get(other), false);
			b1.end();

			builder
				.part()
				.modelFile(useAlt ? sideAlt : side)
				.rotationY(rot)
				.addModel()
				.condition(b, true)
				.end();
		}
	}

	// Other

	public void flowerbed(Block block, String location) {
		ModelFile flowerbed1 = this.models()
			.withExistingParent(this.name(block) + "_1", this.mcLoc("flowerbed_1"))
			.texture("flowerbed", this.texture(this.name(block), location))
			.texture("stem", this.extend(this.texture(this.name(block), location), "_stem"))
			.renderType("cutout");
		ModelFile flowerbed2 = this.models()
			.withExistingParent(this.name(block) + "_2", this.mcLoc("flowerbed_2"))
			.texture("flowerbed", this.texture(this.name(block), location))
			.texture("stem", this.extend(this.texture(this.name(block), location), "_stem"))
			.renderType("cutout");
		ModelFile flowerbed3 = this.models()
			.withExistingParent(this.name(block) + "_3", this.mcLoc("flowerbed_3"))
			.texture("flowerbed", this.texture(this.name(block), location))
			.texture("stem", this.extend(this.texture(this.name(block), location), "_stem"))
			.renderType("cutout");
		ModelFile flowerbed4 = this.models()
			.withExistingParent(this.name(block) + "_4", this.mcLoc("flowerbed_4"))
			.texture("flowerbed", this.texture(this.name(block), location))
			.texture("stem", this.extend(this.texture(this.name(block), location), "_stem"))
			.renderType("cutout");
		MultiPartBlockStateBuilder builder = this.getMultipartBuilder(block);
		for (Direction d : new Direction[] {
			Direction.NORTH,
			Direction.EAST,
			Direction.SOUTH,
			Direction.WEST,
		}) {
			builder
				.part()
				.modelFile(flowerbed1)
				.rotationY(d.getOpposite().get2DDataValue() * 90)
				.addModel()
				.condition(PinkPetalsBlock.AMOUNT, 1, 2, 3, 4)
				.condition(PinkPetalsBlock.FACING, d)
				.end();
			builder
				.part()
				.modelFile(flowerbed2)
				.rotationY(d.getOpposite().get2DDataValue() * 90)
				.addModel()
				.condition(PinkPetalsBlock.AMOUNT, 2, 3, 4)
				.condition(PinkPetalsBlock.FACING, d)
				.end();
			builder
				.part()
				.modelFile(flowerbed3)
				.rotationY(d.getOpposite().get2DDataValue() * 90)
				.addModel()
				.condition(PinkPetalsBlock.AMOUNT, 3, 4)
				.condition(PinkPetalsBlock.FACING, d)
				.end();
			builder
				.part()
				.modelFile(flowerbed4)
				.rotationY(d.getOpposite().get2DDataValue() * 90)
				.addModel()
				.condition(PinkPetalsBlock.AMOUNT, 4)
				.condition(PinkPetalsBlock.FACING, d)
				.end();
		}
	}

	public void clover(Block block, String location) {
		ModelFile cross = this.models()
			.withExistingParent(this.name(block), Redux.loc("block/template/cross/large_clover"))
			.texture("stem", this.modLoc("block/" + location + this.name(block) + "_stem"))
			.texture("top", this.modLoc("block/" + location + this.name(block) + "_top"))
			.renderType(ResourceLocation.withDefaultNamespace("cutout"));
		this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(cross));
	}

	public void pottedClover(Block block, Block flower, String location) {
		ModelFile pot = this.models()
			.withExistingParent(this.name(block), Redux.loc("block/template/pot/flower_pot_clover"))
			.texture("stem", this.modLoc("block/" + location + this.name(flower) + "_stem"))
			.texture("top", this.modLoc("block/" + location + this.name(flower) + "_top"))
			.renderType(ResourceLocation.withDefaultNamespace("cutout"));
		this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(pot));
	}

	public void logicator(Block block, String location) {
		String name = this.name(block);
		this.getVariantBuilder(block).forAllStates((state) -> {
			Direction d = state.getValue(DiodeBlock.FACING);
			LogicatorMode mode = state.getValue(LogicatorBlock.MODE);
			String baseTextureName = mode.isExclusive() ? name + "_exclusive" : name;
			String baseModelName = name + '_' + mode.getSerializedName();
			boolean l = state.getValue(LogicatorBlock.LEFT);
			boolean r = state.getValue(LogicatorBlock.RIGHT);
			String configuration = "";
			if (l || r) {
				configuration = configuration + "_in_";

				if (l) configuration = configuration + 'l';
				if (r) configuration = configuration + 'r';
			}

			if (state.getValue(LogicatorBlock.POWERED)) configuration = configuration + "_out";

			return ConfiguredModel.builder()
				.modelFile(
					this.models()
						.singleTexture(
							baseModelName + configuration,
							this.modLoc(
								"block/template/redstone/template_logicator_" + (mode.isOr() ? "or" : "and")
							),
							"top",
							this.texture(baseTextureName, location, configuration)
						)
						.renderType("cutout")
				)
				.rotationY(d.get2DDataValue() * 90)
				.build();
		});
	}

	public void mossyCarpet(Block block, Block base, String location) {
		MultiPartBlockStateBuilder builder = this.getMultipartBuilder(block);

		ModelFile carpet = this.models().singleTexture(
			this.name(block),
			this.mcLoc("block/carpet"),
			"wool",
			this.texture(this.name(base), location)
		);
		ModelFile sideSmall = this.models()
			.singleTexture(
				this.name(block) + "_side_small",
				Redux.loc("block/template/backport/mossy_carpet_side"),
				"side",
				this.texture(this.name(block) + "_side_small", location)
			)
			.renderType("cutout");
		ModelFile sideTall = this.models()
			.singleTexture(
				this.name(block) + "_side_tall",
				Redux.loc("block/template/backport/mossy_carpet_side"),
				"side",
				this.texture(this.name(block) + "_side_tall", location)
			)
			.renderType("cutout");

		builder.part().modelFile(carpet).addModel().condition(MossyCarpetBlock.BASE, true).end();

		builder
			.part()
			.modelFile(carpet)
			.addModel()
			.condition(MossyCarpetBlock.BASE, true)
			.condition(MossyCarpetBlock.NORTH, WallSide.NONE)
			.condition(MossyCarpetBlock.EAST, WallSide.NONE)
			.condition(MossyCarpetBlock.SOUTH, WallSide.NONE)
			.condition(MossyCarpetBlock.WEST, WallSide.NONE)
			.end();

		for (Map.Entry<
			Direction,
			EnumProperty<WallSide>
		> entry : MossyCarpetBlock.PROPERTY_BY_DIRECTION.entrySet()) {
			Direction key = entry.getKey();
			EnumProperty<WallSide> value = entry.getValue();

			var tallBuilder = builder
				.part()
				.modelFile(sideTall)
				.rotationY(key.getOpposite().get2DDataValue() * 90);
			if (key != Direction.NORTH) tallBuilder = tallBuilder.uvLock(true);
			tallBuilder.addModel().condition(value, WallSide.TALL).end();
			var smallBuilder = builder
				.part()
				.modelFile(sideSmall)
				.rotationY(key.getOpposite().get2DDataValue() * 90);
			if (key != Direction.NORTH) smallBuilder = smallBuilder.uvLock(true);
			smallBuilder.addModel().condition(value, WallSide.LOW).end();

			var noneBuilder = builder
				.part()
				.modelFile(sideTall)
				.rotationY(key.getOpposite().get2DDataValue() * 90);
			if (key != Direction.NORTH) noneBuilder = noneBuilder.uvLock(true);
			var modelAdded = noneBuilder.addModel().condition(MossyCarpetBlock.BASE, false);
			for (EnumProperty<WallSide> property : MossyCarpetBlock.PROPERTY_BY_DIRECTION.values())
				modelAdded.condition(property, WallSide.NONE);
			builder = modelAdded.end();
		}
	}

	public void algae(Block block, String location) {
		var model = this.models()
			.withExistingParent(this.name(block), Redux.loc("block/template/algae"))
			.texture("top", this.modLoc("block/" + location + this.name(block) + "_top"))
			.texture("cross", this.modLoc("block/" + location + this.name(block) + "_side"))
			.renderType(ResourceLocation.withDefaultNamespace("cutout"));
		this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(model));
	}

	public void layeredPlant(Block block, String location) {
		var model = this.models()
			.withExistingParent(this.name(block), Redux.loc("block/template/layered_plant"))
			.texture("lower", this.modLoc("block/" + location + this.name(block) + "_lower"))
			.texture("upper", this.modLoc("block/" + location + this.name(block) + "_upper"))
			.texture("stem", this.modLoc("block/" + location + this.name(block) + "_stem"))
			.texture("top", this.modLoc("block/" + location + this.name(block) + "_top"))
			.renderType(ResourceLocation.withDefaultNamespace("cutout"));
		this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(model));
	}

	public void echysia(Block block, String location) {
		var model = this.models()
			.withExistingParent(this.name(block), Redux.loc("block/template/layered_plant"))
			.texture("lower", this.modLoc("block/" + location + this.name(block) + "_lower"))
			.texture("upper", this.modLoc("block/" + location + this.name(block) + "_upper"))
			.texture("stem", this.modLoc("block/" + location + this.name(block) + "_stem"))
			.texture("top", this.modLoc("block/" + location + this.name(block) + "_top"))
			.renderType(ResourceLocation.withDefaultNamespace("cutout"));

		var gilded = this.models()
			.withExistingParent("gilded_" + this.name(block), Redux.loc("block/template/layered_plant"))
			.texture("lower", this.modLoc("block/" + location + "gilded_" + this.name(block) + "_lower"))
			.texture("upper", this.modLoc("block/" + location + "gilded_" + this.name(block) + "_upper"))
			.texture("stem", this.modLoc("block/" + location + "gilded_" + this.name(block) + "_stem"))
			.texture("top", this.modLoc("block/" + location + "gilded_" + this.name(block) + "_top"))
			.renderType(ResourceLocation.withDefaultNamespace("cutout"));
		var bleak = this.models()
			.withExistingParent("bleak_" + this.name(block), Redux.loc("block/template/layered_plant"))
			.texture("lower", this.modLoc("block/" + location + "bleak_" + this.name(block) + "_lower"))
			.texture("upper", this.modLoc("block/" + location + "bleak_" + this.name(block) + "_upper"))
			.texture("stem", this.modLoc("block/" + location + "bleak_" + this.name(block) + "_stem"))
			.texture("top", this.modLoc("block/" + location + "bleak_" + this.name(block) + "_top"))
			.renderType(ResourceLocation.withDefaultNamespace("cutout"));

		this.getVariantBuilder(block).forAllStates((state) ->
			ConfiguredModel.builder()
				.modelFile(
					switch (state.getValue(ReduxStates.AETHER_MOSS_TYPE)) {
						case FLUTEMOSS -> model;
						case BLEAKMOSS -> bleak;
						case GILDENMOSS -> gilded;
					}
				)
				.build()
		);
	}
	
	public ResourceLocation foreignTexture(String namespace, String name, String location) {
		return ResourceLocation.fromNamespaceAndPath(namespace, "block/" + location + name);
	}
	
	public String namespace(Block block) {
		return BuiltInRegistries.BLOCK.getKey(block).getNamespace();
	}
	
	@Override
	public void grassBlock(Block block, Block snow, Block dirt) {
		var grass = this.grassBlock(block, dirt);
		var grassSnowed = this.cubeBottomTop(this.name(snow) + "_snow",
			this.extend(this.texture(this.name(snow), "natural/"), "_snow"),
			this.foreignTexture(this.namespace(dirt), this.name(dirt), "natural/"),
			this.extend(this.texture(this.name(block), "natural/"), "_top"));
		this.getVariantBuilder(block).forAllStatesExcept(state -> {
			boolean snowy = state.getValue(SnowyDirtBlock.SNOWY);
			return ConfiguredModel.allYRotations(snowy ? grassSnowed : grass, 0, false);
		}, AetherBlockStateProperties.DOUBLE_DROPS);
	}
	
	@Override
	public ModelFile grassBlock(Block block, Block dirt) {
		return this.cubeBottomTop(this.name(block),
			this.extend(this.texture(this.name(block), "natural/"), "_side"),
			this.foreignTexture(this.namespace(dirt), this.name(dirt), "natural/"),
			this.extend(this.texture(this.name(block), "natural/"), "_top"));
	}
}
