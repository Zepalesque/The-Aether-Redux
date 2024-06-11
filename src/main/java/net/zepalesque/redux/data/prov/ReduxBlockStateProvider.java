package net.zepalesque.redux.data.prov;

import com.aetherteam.aether.block.dungeon.DoorwayBlock;
import com.aetherteam.aether.data.providers.AetherBlockStateProvider;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.construction.LayeredBookshelfBlock;
import net.zepalesque.redux.block.dungeon.RunelightBlock;
import net.zepalesque.redux.block.natural.AetherShortGrassBlock;
import net.zepalesque.redux.block.state.ReduxStates;

public abstract class ReduxBlockStateProvider extends AetherBlockStateProvider {

    public ReduxBlockStateProvider(PackOutput output, String id, ExistingFileHelper helper) {
        super(output, id, helper);
    }

    public void tintableShortGrass(AetherShortGrassBlock block, String location) {
        this.getVariantBuilder(block).forAllStates((state) -> {
            boolean enchanted = state.getValue(ReduxStates.ENCHANTED);
            if (enchanted) {
                return ConfiguredModel.builder()
                        .modelFile(models()
                                .cross(state.getValue(ReduxStates.GRASS_SIZE).getSerializedName() + "_enchanted_grass",
                                        modLoc("block/natural/enchanted_" + state.getValue(ReduxStates.GRASS_SIZE).getSerializedName()  + "_grass"))
                                .renderType("cutout")).build();
            }

            return ConfiguredModel.builder()
                    .modelFile(models()
                            .singleTexture(state.getValue(ReduxStates.GRASS_SIZE).getSerializedName() + "_aether_grass",
                                    mcLoc("block/tinted_cross"), "cross",
                                    modLoc("block/natural/aether_" + state.getValue(ReduxStates.GRASS_SIZE).getSerializedName() + "_grass"))
                            .renderType("cutout")).build();
        });
    }

    @Override
    public BlockModelBuilder makeWallPostModel(int width, int height, String name) {
        return super.makeWallPostModel(width, height, name);
    }

    @Override
    public BlockModelBuilder makeWallSideModel(int length, int height, String name, ModelBuilder.FaceRotation faceRotation, int u1, int u2) {
        return super.makeWallSideModel(length, height, name, faceRotation, u1, u2);
    }

    public void layeredBookshelf(Block block, Block endBlock) {
        ModelFile bookshelf = this.models().cubeColumn(this.name(block), this.texture(this.name(block), "construction/"), this.texture(this.name(endBlock), "construction/"));
        ModelFile top = this.models().cubeColumn(this.name(block) + "_top", this.texture(this.name(block) + "_top", "construction/"), this.texture(this.name(endBlock), "construction/"));
        ModelFile bottom = this.models().cubeColumn(this.name(block) + "_bottom", this.texture(this.name(block) + "_bottom", "construction/"), this.texture(this.name(endBlock), "construction/"));
        ModelFile center = this.models().cubeColumn(this.name(block) + "_center", this.texture(this.name(block) + "_center", "construction/"), this.texture(this.name(endBlock), "construction/"));
        this.getVariantBuilder(block).forAllStates(state -> {

            boolean up = state.getValue(LayeredBookshelfBlock.UP);
            boolean down = state.getValue(LayeredBookshelfBlock.DOWN);
            return ConfiguredModel.builder().modelFile(up && down ? bookshelf :
                    !up && down ? bottom :
                            up ? top : center).build();
        });
    }


    public void pillar(Block block, Block other, String location) {
        this.axisBlock(block, other, this.extend(this.texture(this.name(other), location), "_side"), this.extend(this.texture(this.name(other), location), "_top"));
    }

    public void pillar(Block block, String location) {
        this.pillar(block, block, location);
    }

    public void axisBlock(Block block, Block other, ResourceLocation side, ResourceLocation end) {
        axisBlock(block,
                models().cubeColumn(name(other), side, end),
                models().cubeColumnHorizontal(name(other) + "_horizontal", side, end));
    }

    public void axisBlock(Block block, ModelFile vertical, ModelFile horizontal) {
        getVariantBuilder(block)
                .partialState().with(BlockStateProperties.AXIS, Direction.Axis.Y)
                .modelForState().modelFile(vertical).addModel()
                .partialState().with(BlockStateProperties.AXIS, Direction.Axis.Z)
                .modelForState().modelFile(horizontal).rotationX(90).addModel()
                .partialState().with(BlockStateProperties.AXIS, Direction.Axis.X)
                .modelForState().modelFile(horizontal).rotationX(90).rotationY(90).addModel();
    }

    public void baseBrick(Block block, Block other, String location) {
        ModelFile model = this.cubeBottomTop(this.name(other),
                this.extend(this.texture(this.name(other), location), "_side"),
                this.extend(this.texture(this.name(other), location), "_top"),
                this.extend(this.texture(this.name(other), location), "_top"));
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(model));
    }

    public void baseBrick(Block block, String location) {
        this.baseBrick(block, block, location);
    }

    public void invisibleBaseBrick(Block block, Block baseBlock, String location) {
        ModelFile visible = this.cubeBottomTop(this.name(baseBlock),
                this.extend(this.texture(this.name(baseBlock), location), "_side"),
                this.extend(this.texture(this.name(baseBlock), location), "_top"),
                this.extend(this.texture(this.name(baseBlock), location), "_top"));
        ModelFile invisible = this.models().getBuilder(this.name(block));
        this.getVariantBuilder(block).forAllStatesExcept(state -> {
            if (!state.getValue(DoorwayBlock.INVISIBLE)) {
                return ConfiguredModel.builder().modelFile(visible).build();
            } else {
                return ConfiguredModel.builder().modelFile(invisible).build();
            }
        });
    }

    public void invisiblePillar(Block block, Block other, String location) {
        ResourceLocation side = this.extend(this.texture(this.name(other), location), "_side");
        ResourceLocation end = this.extend(this.texture(this.name(other), location), "_top");
        ModelFile vertical = models().cubeColumn(name(other), side, end);
        ModelFile horizontal = models().cubeColumnHorizontal(name(block) + "_horizontal", side, end);
        ModelFile invisible = this.models().getBuilder(this.name(block));
        getVariantBuilder(block)
                .partialState().with(BlockStateProperties.AXIS, Direction.Axis.Y).with(DoorwayBlock.INVISIBLE, false)
                .modelForState().modelFile(vertical).addModel()
                .partialState().with(BlockStateProperties.AXIS, Direction.Axis.Z).with(DoorwayBlock.INVISIBLE, false)
                .modelForState().modelFile(horizontal).rotationX(90).addModel()
                .partialState().with(BlockStateProperties.AXIS, Direction.Axis.X).with(DoorwayBlock.INVISIBLE, false)
                .modelForState().modelFile(horizontal).rotationX(90).rotationY(90).addModel()
                .partialState().with(BlockStateProperties.AXIS, Direction.Axis.Y).with(DoorwayBlock.INVISIBLE, true)
                .modelForState().modelFile(invisible).addModel()
                .partialState().with(BlockStateProperties.AXIS, Direction.Axis.Z).with(DoorwayBlock.INVISIBLE, true)
                .modelForState().modelFile(invisible).addModel()
                .partialState().with(BlockStateProperties.AXIS, Direction.Axis.X).with(DoorwayBlock.INVISIBLE, true)
                .modelForState().modelFile(invisible).addModel();
    }

    public static ResourceLocation extendStatic(ResourceLocation location, String suffix) {
        return new ResourceLocation(location.getNamespace(), location.getPath() + suffix);
    }

    public static String nameStatic(Block block) {
        ResourceLocation location = BuiltInRegistries.BLOCK.getKey(block);
        if (location != null) {
            return location.getPath();
        } else {
            throw new IllegalStateException("Unknown block: " + block.toString());
        }
    }

    public void cubeActivatable(Block block, String location) {
        this.cubeActivatable(block, block, location);
    }

    public void cubeActivatable(Block block, Block other, String location) {
        ModelFile off = this.models().cubeAll(this.name(other), this.texture(this.name(other), location));
        ModelFile on = this.models().cubeAll(this.name(other) + "_on", this.texture(this.name(other) + "_on", location));
        this.getVariantBuilder(block)
                .partialState().with(RunelightBlock.LIT, true).modelForState().modelFile(on).addModel()
                .partialState().with(RunelightBlock.LIT, false).modelForState().modelFile(off).addModel();
    }

    public void dungeonBlock(Block block, Block baseBlock, String location) {
        ConfiguredModel dungeonBlock = new ConfiguredModel(this.models().cubeAll(this.name(baseBlock), this.texture(this.name(baseBlock), location)));
        this.getVariantBuilder(block).partialState().setModels(dungeonBlock);
    }

    // Cross blocks
    public void crossGlowOverlay(Block block, String location) {
        BlockModelBuilder cross = models().withExistingParent(this.name(block), Redux.loc(ModelProvider.BLOCK_FOLDER + "/cross/cross_glowing_overlay"))
                .texture("cross", this.texture(this.name(block), location))
                .texture("overlay", this.texture(this.name(block) + "_glow", location)).renderType("cutout");
        this.crossBlock(block, cross);
    }

    public void crossTintedGlow(Block block, String location) {
        BlockModelBuilder cross = models().withExistingParent(this.name(block), Redux.loc(ModelProvider.BLOCK_FOLDER + "/cross/cross_tinted_glow"))
                .texture("cross", this.texture(this.name(block), location))
                .texture("overlay", this.texture(this.name(block) + "_glow", location)).renderType("cutout");
        this.crossBlock(block, cross);
    }

    public void crossTintedDualGloverlay(Block block, String location, boolean useGlowForParticle) {
        BlockModelBuilder cross = models().withExistingParent(this.name(block), Redux.loc(ModelProvider.BLOCK_FOLDER + "/cross/cross_tinted_gloverlay_" + (useGlowForParticle ? "glow" : "overlay")))
                .texture("cross", this.texture(this.name(block), location))
                .texture("glow", this.texture(this.name(block) + "_glow", location)).renderType("cutout")
                .texture("overlay", this.texture(this.name(block) + "_overlay", location)).renderType("cutout");
        this.crossBlock(block, cross);
    }
    public void crossTintedOverlay(Block block, String location) {
        BlockModelBuilder cross = models().withExistingParent(this.name(block), Redux.loc(ModelProvider.BLOCK_FOLDER + "/cross/cross_tinted_overlay"))
                .texture("cross", this.texture(this.name(block), location))
                .texture("overlay", this.texture(this.name(block) + "_overlay", location)).renderType("cutout");
        this.crossBlock(block, cross);
    }

    // Crop blocks
    public void cropGrowable(Block block, String location, IntegerProperty ageProperty) {
        this.getVariantBuilder(block).forAllStates((state) -> {
            int stage = state.getValue(ageProperty);
            BlockModelBuilder cross = models().withExistingParent(this.name(block) + "_stage" + stage, Redux.loc(ModelProvider.BLOCK_FOLDER + "/crop/crop_lowered"))
                    .texture("plant", this.texture(this.name(block) + "_stage" + stage, location)).renderType("cutout");
            return ConfiguredModel.builder().modelFile(cross).build();
        });
    }
}
