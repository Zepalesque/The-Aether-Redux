package net.zepalesque.redux.data;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.miscellaneous.FacingPillarBlock;
import com.aetherteam.aether.data.providers.AetherBlockStateProvider;
import net.builderdog.ancient_aether.block.blockstate.AncientAetherBlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.blockhandler.WoodHandler;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.construction.LayeredBookshelfBlock;
import net.zepalesque.redux.block.construction.VeridiumLanternBlock;
import net.zepalesque.redux.block.natural.AetherShortGrassBlock;
import net.zepalesque.redux.block.natural.ExtendedDistanceLeavesBlock;
import net.zepalesque.redux.block.natural.LeafPileBlock;
import net.zepalesque.redux.block.natural.SproutsCropBlock;
import net.zepalesque.redux.block.util.state.ReduxStates;
import net.zepalesque.redux.block.util.state.enums.PetalPrismaticness;

import java.util.Map;
import java.util.function.Supplier;

import static net.minecraftforge.client.model.generators.ModelProvider.BLOCK_FOLDER;

public class ReduxBlockstateData extends AetherBlockStateProvider {

    public ReduxBlockstateData(PackOutput output, ExistingFileHelper helper) {
        super(output, Redux.MODID, helper);
    }

    @Override
    public void registerStatesAndModels() {

        this.blockDoubleDrops(ReduxBlocks.HOLYSILT, "natural/");

        this.blockDoubleDrops(ReduxBlocks.DIVINITE.get(), "natural/");
        this.slab(ReduxBlocks.DIVINITE_SLAB.get(), ReduxBlocks.DIVINITE.get(), "natural/");
        this.stairs(ReduxBlocks.DIVINITE_STAIRS.get(), ReduxBlocks.DIVINITE.get(), "natural/");
        this.wallBlock(ReduxBlocks.DIVINITE_WALL.get(), ReduxBlocks.DIVINITE.get(), "natural/");


        this.blockDoubleDrops(ReduxBlocks.SENTRITE.get(), "natural/");
        this.slab(ReduxBlocks.SENTRITE_SLAB.get(), ReduxBlocks.SENTRITE.get(), "natural/");
        this.stairs(ReduxBlocks.SENTRITE_STAIRS.get(), ReduxBlocks.SENTRITE.get(), "natural/");
        this.wallBlock(ReduxBlocks.SENTRITE_WALL.get(), ReduxBlocks.SENTRITE.get(), "natural/");

        this.blockDoubleDrops(ReduxBlocks.SENTRITE_BRICKS.get(), "construction/");
        this.slab(ReduxBlocks.SENTRITE_BRICK_SLAB.get(), ReduxBlocks.SENTRITE_BRICKS.get(), "construction/");
        this.stairs(ReduxBlocks.SENTRITE_BRICK_STAIRS.get(), ReduxBlocks.SENTRITE_BRICKS.get(), "construction/");
        this.wallBlock(ReduxBlocks.SENTRITE_BRICK_WALL.get(), ReduxBlocks.SENTRITE_BRICKS.get(), "construction/");

        this.block(ReduxBlocks.HOLEFIRE_STONE.get(), "dungeon/");
        this.block(ReduxBlocks.GLOWY_HOLEFIRE_STONE.get(), "dungeon/");
        this.slab(ReduxBlocks.HOLEFIRE_SLAB.get(), ReduxBlocks.HOLEFIRE_STONE.get(), "dungeon/");
        this.stairs(ReduxBlocks.HOLEFIRE_STAIRS.get(), ReduxBlocks.HOLEFIRE_STONE.get(), "dungeon/");
        this.wallBlock(ReduxBlocks.HOLEFIRE_WALL.get(), ReduxBlocks.HOLEFIRE_STONE.get(), "dungeon/");
        this.pillar(ReduxBlocks.HOLEFIRE_PILLAR, "dungeon/");
        this.holefirepillarTop(ReduxBlocks.HOLEFIRE_PILLAR_TOP.get());

        this.other(ReduxBlocks.GRAVITITE_BLOCK, AetherBlocks.ENCHANTED_GRAVITITE, "construction/");
        this.block(ReduxBlocks.RAW_GRAVITITE_BLOCK, "natural/");

        this.cropGrowable(ReduxBlocks.WYNDSPROUTS_CROP.get(), "crop/wyndsprouts/", SproutsCropBlock.AGE);
        this.cropGrowable(ReduxBlocks.SKYSPROUTS_CROP.get(), "crop/skysprouts/", SproutsCropBlock.AGE);

        this.crossBlock(ReduxBlocks.AVELIUM_SPROUTS.get(), "natural/");
        this.crossBlock(ReduxBlocks.AVELIUM_ROOTS.get(), "natural/");
        this.potAlt(ReduxBlocks.POTTED_AVELIUM_ROOTS.get(), ReduxBlocks.AVELIUM_ROOTS.get(), "natural/");

        this.crossBlock(ReduxBlocks.SHORT_DEEP_GRASS.get(), "natural/");

        this.crossTintedOverlay(ReduxBlocks.IRIDIA.get(), "natural/");
        this.crossTintedOverlay(ReduxBlocks.XAELIA_PATCH.get(), "natural/");
        this.tintableShortGrass(ReduxBlocks.SHORT_AETHER_GRASS.get(), "natural/");
        this.tintedPotOverlay(ReduxBlocks.POTTED_IRIDIA.get(), ReduxBlocks.IRIDIA.get(), "natural/");
        block(ReduxBlocks.GILDED_HOLYSTONE.get(), "natural/");
        wallBlock(ReduxBlocks.GILDED_HOLYSTONE_WALL.get(), ReduxBlocks.GILDED_HOLYSTONE.get(), "natural/");
        stairs(ReduxBlocks.GILDED_HOLYSTONE_STAIRS.get(), ReduxBlocks.GILDED_HOLYSTONE.get(), "natural/");
        slab(ReduxBlocks.GILDED_HOLYSTONE_SLAB.get(), ReduxBlocks.GILDED_HOLYSTONE.get(), "natural/");
        block(ReduxBlocks.COARSE_AETHER_DIRT.get(), "natural/");
        block(ReduxBlocks.BLIGHTMOSS_HOLYSTONE.get(), "natural/");
        wallBlock(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_WALL.get(), ReduxBlocks.BLIGHTMOSS_HOLYSTONE.get(), "natural/");
        stairs(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_STAIRS.get(), ReduxBlocks.BLIGHTMOSS_HOLYSTONE.get(), "natural/");
        slab(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_SLAB.get(), ReduxBlocks.BLIGHTMOSS_HOLYSTONE.get(), "natural/");
        this.crossTintedOverlayEnchantable(ReduxBlocks.AURUM.get(), "natural/");
        this.crossTintedOverlayEnchantable(ReduxBlocks.ZYATRIX.get(), "natural/");
        this.crossBlock(ReduxBlocks.ENCHANTED_WHITE_FLOWER.get(), "natural/");
        // TODO: Enchanted pots somehow
        this.tintedPotOverlay(ReduxBlocks.POTTED_AURUM.get(), ReduxBlocks.AURUM.get(), "natural/");
        this.tintedPotOverlay(ReduxBlocks.POTTED_ZYATRIX.get(), ReduxBlocks.ZYATRIX.get(), "natural/");
        block(ReduxBlocks.CARVED_STONE_BRICKS.get(), "construction/");
        wallBlock(ReduxBlocks.CARVED_STONE_BRICK_WALL.get(), ReduxBlocks.CARVED_STONE_BRICKS.get(), "construction/");
        stairs(ReduxBlocks.CARVED_STONE_BRICK_STAIRS.get(), ReduxBlocks.CARVED_STONE_BRICKS.get(), "construction/");
        slab(ReduxBlocks.CARVED_STONE_BRICK_SLAB.get(), ReduxBlocks.CARVED_STONE_BRICKS.get(), "construction/");
        this.pillar(ReduxBlocks.CARVED_STONE_PILLAR::get, "construction/");

        this.block(ReduxBlocks.BLIGHTWILLOW_LEAVES.get(), "natural/");
        this.crossBlock(ReduxBlocks.BLIGHTWILLOW_SAPLING.get(), "natural/");

        this.floweringFieldsprootLeafBlock(ReduxBlocks.FIELDSPROOT_LEAVES, "natural/");
        this.fieldsprootPetals(ReduxBlocks.FIELDSPROOT_PETALS.get(), "natural/fieldsproot_petals_stem");

        this.classicFieldsprootLeafBlock(ReduxBlocks.PRISMATIC_FIELDSPROOT_LEAVES, ReduxBlocks.FIELDSPROOT_LEAVES, "natural/", true);
        this.classicFieldsprootLeafBlock(ReduxBlocks.SPECTRAL_FIELDSPROOT_LEAVES, ReduxBlocks.FIELDSPROOT_LEAVES, "natural/", false);
        this.existingModel(ReduxBlocks.AZURE_FIELDSPROOT_LEAVES, "fieldsproot_leaves_3");

        this.crossBlock(ReduxBlocks.FIELDSPROOT_SAPLING.get(), "natural/");
        this.pottedPlant(ReduxBlocks.POTTED_FIELDSPROOT_SAPLING.get(), ReduxBlocks.FIELDSPROOT_SAPLING.get(), "natural/");

        this.crossBlock(ReduxBlocks.PRISMATIC_FIELDSPROOT_SAPLING.get(), "natural/");
        this.pottedPlant(ReduxBlocks.POTTED_PRISMATIC_FIELDSPROOT_SAPLING.get(), ReduxBlocks.PRISMATIC_FIELDSPROOT_SAPLING.get(), "natural/");

        this.crossBlock(ReduxBlocks.AZURE_FIELDSPROOT_SAPLING.get(), "natural/");
        this.pottedPlant(ReduxBlocks.POTTED_AZURE_FIELDSPROOT_SAPLING.get(), ReduxBlocks.AZURE_FIELDSPROOT_SAPLING.get(), "natural/");

        this.crossBlock(ReduxBlocks.SPECTRAL_FIELDSPROOT_SAPLING.get(), "natural/");
        this.pottedPlant(ReduxBlocks.POTTED_SPECTRAL_FIELDSPROOT_SAPLING.get(), ReduxBlocks.SPECTRAL_FIELDSPROOT_SAPLING.get(), "natural/");

        this.leafPile(ReduxBlocks.GOLDEN_LEAF_PILE, AetherBlocks.GOLDEN_OAK_LEAVES, "natural/");
        this.leafPile(ReduxBlocks.GILDED_LEAF_PILE, ReduxBlocks.GILDED_OAK_LEAVES, "natural/");
        this.leafPile(ReduxBlocks.BLIGHTWILLOW_LEAF_PILE, ReduxBlocks.BLIGHTWILLOW_LEAVES, "natural/");

        this.grass(ReduxBlocks.AVELIUM, AetherBlocks.AETHER_DIRT);
        this.grass(ReduxBlocks.DEEP_GRASS_BLOCK, AetherBlocks.AETHER_DIRT);

        this.snowableLeaves(ReduxBlocks.GLACIA_LEAVES.get(), "natural/");
        this.crossBlock(ReduxBlocks.GLACIA_SAPLING.get(), "natural/");
        this.pottedPlant(ReduxBlocks.POTTED_GLACIA_SAPLING.get(), ReduxBlocks.GLACIA_SAPLING.get(), "natural/");

        this.crossBlock(ReduxBlocks.CRYSTAL_SAPLING.get(), "natural/");
        this.pottedPlant(ReduxBlocks.POTTED_CRYSTAL_SAPLING.get(), ReduxBlocks.CRYSTAL_SAPLING.get(), "natural/");

        this.crossBlock(ReduxBlocks.CRYSTAL_FRUIT_SAPLING.get(), "natural/");
        this.pottedPlant(ReduxBlocks.POTTED_CRYSTAL_FRUIT_SAPLING.get(), ReduxBlocks.CRYSTAL_FRUIT_SAPLING.get(), "natural/");

        this.crossBlock(ReduxBlocks.PURPLE_CRYSTAL_FRUIT_SAPLING.get(), "natural/");
        this.pottedPlant(ReduxBlocks.POTTED_PURPLE_CRYSTAL_FRUIT_SAPLING.get(), ReduxBlocks.PURPLE_CRYSTAL_FRUIT_SAPLING.get(), "natural/");


        this.snowableLeaves(ReduxBlocks.PURPLE_GLACIA_LEAVES.get(), "natural/");
        this.crossBlock(ReduxBlocks.PURPLE_GLACIA_SAPLING.get(), "natural/");
        this.pottedPlant(ReduxBlocks.POTTED_PURPLE_GLACIA_SAPLING.get(), ReduxBlocks.PURPLE_GLACIA_SAPLING.get(), "natural/");

        this.createCloudcapBlock(ReduxBlocks.CLOUD_CAP_BLOCK, "natural/");
        this.block(ReduxBlocks.CLOUDCAP_SPORES.get(), "natural/");

        this.crossTintedGlow(ReduxBlocks.LUXWEED.get(), "natural/");
        this.tintedPotGlowOverlayAlt(ReduxBlocks.POTTED_LUXWEED.get(), ReduxBlocks.LUXWEED.get(), "natural/");
        this.crossTintedOverlay(ReduxBlocks.SPIROLYCTIL.get(), "natural/");
        this.tintedPotOverlay(ReduxBlocks.POTTED_SPIROLYCTIL.get(), ReduxBlocks.SPIROLYCTIL.get(), "natural/");
        this.crossTintedGlow(ReduxBlocks.BLIGHTSHADE.get(), "natural/");
        this.tintedPotOverlay(ReduxBlocks.POTTED_BLIGHTSHADE.get(), ReduxBlocks.BLIGHTSHADE.get(), "natural/");
        this.crossGlowOverlay(ReduxBlocks.CLOUDCAP_MUSHLING.get(), "natural/");
        this.potGlow(ReduxBlocks.POTTED_CLOUDCAP_MUSHLING.get(), ReduxBlocks.CLOUDCAP_MUSHLING.get(), "natural/");
        this.crossBlock(ReduxBlocks.ZANBERRY_BUSH_STEM.get(), "natural/");
        this.berryBush(ReduxBlocks.ZANBERRY_BUSH.get(), ReduxBlocks.ZANBERRY_BUSH_STEM.get());
        this.pottedStem(ReduxBlocks.POTTED_ZANBERRY_BUSH_STEM.get(), "natural/");
        this.pottedBush(ReduxBlocks.POTTED_ZANBERRY_BUSH.get(), ReduxBlocks.ZANBERRY_BUSH_STEM.get(), "natural/");

        this.block(ReduxBlocks.SHELL_SHINGLES.get(), "construction/");
        this.slab(ReduxBlocks.SHELL_SHINGLE_SLAB.get(), ReduxBlocks.SHELL_SHINGLES.get(), "construction/");
        this.stairs(ReduxBlocks.SHELL_SHINGLE_STAIRS.get(), ReduxBlocks.SHELL_SHINGLES.get(), "construction/");
        this.wallBlock(ReduxBlocks.SHELL_SHINGLE_WALL.get(), ReduxBlocks.SHELL_SHINGLES.get(), "construction/");

        this.block(ReduxBlocks.ENCHANTED_SHELL_SHINGLES.get(), "construction/");
        this.slab(ReduxBlocks.ENCHANTED_SHELL_SHINGLE_SLAB.get(), ReduxBlocks.ENCHANTED_SHELL_SHINGLES.get(), "construction/");
        this.stairs(ReduxBlocks.ENCHANTED_SHELL_SHINGLE_STAIRS.get(), ReduxBlocks.ENCHANTED_SHELL_SHINGLES.get(), "construction/");
        this.wallBlock(ReduxBlocks.ENCHANTED_SHELL_SHINGLE_WALL.get(), ReduxBlocks.ENCHANTED_SHELL_SHINGLES.get(), "construction/");

        block(ReduxBlocks.BLIGHTMOSS_BLOCK.get(), "natural/");
        carpet(ReduxBlocks.BLIGHTMOSS_CARPET.get(), ReduxBlocks.BLIGHTMOSS_BLOCK.get(), "natural/");
        block(ReduxBlocks.FUNGAL_GROWTH.get(), "natural/");
        carpet(ReduxBlocks.FUNGAL_CARPET.get(), ReduxBlocks.FUNGAL_GROWTH.get(), "natural/");
        this.crossTintedDualGloverlay(ReduxBlocks.LUMINA.get(), "natural/");
        this.tintedPotDualGloverlay(ReduxBlocks.POTTED_LUMINA.get(), ReduxBlocks.LUMINA.get(), "natural/");

        this.crossTintedDualGloverlay(ReduxBlocks.FLAREBLOSSOM.get(), "natural/");
        this.tintedPotDualGloverlay(ReduxBlocks.POTTED_FLAREBLOSSOM.get(), ReduxBlocks.FLAREBLOSSOM.get(), "natural/");

        this.crossTintedOverlay(ReduxBlocks.INFERNIA.get(), "natural/");
        this.tintedPotOverlay(ReduxBlocks.POTTED_INFERNIA.get(), ReduxBlocks.INFERNIA.get(), "natural/");

        this.pottedPlant(ReduxBlocks.POTTED_BLIGHTWILLOW_SAPLING.get(), ReduxBlocks.BLIGHTWILLOW_SAPLING.get(), "natural/");

        this.block(ReduxBlocks.GILDED_OAK_LEAVES.get(), "natural/");
        this.crossBlock(ReduxBlocks.GILDED_OAK_SAPLING.get(), "natural/");
        this.pottedPlant(ReduxBlocks.POTTED_GILDED_OAK_SAPLING.get(), ReduxBlocks.GILDED_OAK_SAPLING.get(), "natural/");



        this.block(ReduxBlocks.BLIGHTED_SKYROOT_LEAVES.get(), "natural/");
        this.crossBlock(ReduxBlocks.BLIGHTED_SKYROOT_SAPLING.get(), "natural/");
        this.pottedPlant(ReduxBlocks.POTTED_BLIGHTED_SKYROOT_SAPLING.get(), ReduxBlocks.BLIGHTED_SKYROOT_SAPLING.get(), "natural/");

        this.crossTintedOverlay(ReduxBlocks.DAGGERBLOOM.get(), "natural/");
        this.tintedPotOverlay(ReduxBlocks.POTTED_DAGGERBLOOM.get(), ReduxBlocks.DAGGERBLOOM.get(), "natural/");

        this.crossTintedOverlay(ReduxBlocks.THERATIP.get(), "natural/");
        this.tintedPotOverlay(ReduxBlocks.POTTED_THERATIP.get(), ReduxBlocks.THERATIP.get(), "natural/");

        this.crossTintedOverlay(ReduxBlocks.WYNDSPROUTS.get(), "natural/");
        this.crossTintedOverlay(ReduxBlocks.SKYSPROUTS.get(), "natural/");
        this.tintedPotOverlayAlt(ReduxBlocks.POTTED_WYNDSPROUTS.get(), ReduxBlocks.WYNDSPROUTS.get(), "natural/");
        this.tintedPotOverlayAlt(ReduxBlocks.POTTED_SKYSPROUTS.get(), ReduxBlocks.SKYSPROUTS.get(), "natural/");

        this.crossTintedEnchantable(ReduxBlocks.SPLITFERN.get(), "natural/");
        this.tintedPot(ReduxBlocks.POTTED_SPLITFERN.get(), ReduxBlocks.SPLITFERN.get(), "natural/");
        this.blockDoubleDrops(ReduxBlocks.VERIDIUM_ORE.get(), "natural/");
        this.block(ReduxBlocks.RAW_VERIDIUM_BLOCK.get(), "natural/");
        this.block(ReduxBlocks.RAW_VALKYRUM_BLOCK.get(), "natural/");
        this.block(ReduxBlocks.VERIDIUM_BLOCK.get(), "construction/");




        this.veridiumLantern(ReduxBlocks.VERIDIUM_LANTERN.get(), "construction/");
        this.crossBlock(ReduxBlocks.JELLYSHROOM, "natural/");
        this.pottedPlant(ReduxBlocks.POTTED_JELLYSHROOM.get(), ReduxBlocks.JELLYSHROOM.get(), "natural/");
        this.crossGlowOverlay(ReduxBlocks.SHIMMERSTOOL.get(), "natural/");
        this.pottedPlant(ReduxBlocks.POTTED_SHIMMERSTOOL.get(), ReduxBlocks.SHIMMERSTOOL.get(), "natural/");
        this.aercloudAll(ReduxBlocks.JELLYSHROOM_JELLY_BLOCK.get(), "natural/");
        this.aercloudAll(ReduxBlocks.BLIGHTED_AERCLOUD.get(), "natural/");
        this.chain(ReduxBlocks.VERIDIUM_CHAIN.get(), "construction/");
        this.cropOccluded(ReduxBlocks.GOLDEN_VINES_PLANT.get(), "natural/");
        this.cropOccluded(ReduxBlocks.GOLDEN_VINES.get(), "natural/");
        this.cropOccluded(ReduxBlocks.GILDED_VINES_PLANT.get(), "natural/");
        this.cropOccluded(ReduxBlocks.GILDED_VINES.get(), "natural/");

        this.crossGlowOverlay(ReduxBlocks.CORRUPTED_VINES.get(), "natural/");
        this.crossGlowOverlay(ReduxBlocks.CORRUPTED_VINES_PLANT.get(), "natural/");


        for (WoodHandler woodHandler : Redux.WoodHandlers.WOOD_HANDLERS)        {
            woodHandler.generateBlockstateData(this);
        }

    }


    private void createMushroomBlock(RegistryObject<Block> block, String loc) {
        this.models().cubeAll(this.name(block), this.texture(this.name(block), loc));
        ModelFile out = this.models().singleTexture(this.name(block), mcLoc("block/template_single_face"), this.texture(block, loc));
        ModelFile in = this.models().singleTexture(this.name(block) + "_inside", mcLoc("block/template_single_face"), this.texture(block, loc).withSuffix("_inside"));
        MultiPartBlockStateBuilder builder = this.getMultipartBuilder(block.get());
        for (Direction d : Direction.values())
        {
            Vec3i v3 = rot(d);
            // exterior
            builder.part().modelFile(out).rotationX(v3.getX()).rotationY(v3.getY()).addModel().condition(dirToProp(d), true).end();
            // interior
            builder.part().modelFile(in).rotationX(v3.getX()).rotationY(v3.getY()).addModel().condition(dirToProp(d), false).end();
        }
    }

    private void createCloudcapBlock(RegistryObject<Block> block, String loc) {
        this.models().withExistingParent(this.name(block), Redux.locate("block/cube_all_glow")).texture("all", this.texture(this.name(block) + "4", loc)).texture("glow", this.texture(this.name(block) + "4_glow", loc)).renderType("cutout");
        ModelFile out0 = this.models().singleTexture(this.name(block) + "0", mcLoc("block/template_single_face"), this.texture(block, loc).withSuffix("0")).renderType("cutout");
        ModelFile out1 = this.models().withExistingParent(this.name(block) + "1", Redux.locate("block/template_single_face_gloverlay")).texture("texture", this.texture(block, loc).withSuffix("1")).texture("glow", this.texture(block, loc).withSuffix("1_glow")).renderType("cutout");
        ModelFile out2 = this.models().withExistingParent(this.name(block) + "2", Redux.locate("block/template_single_face_gloverlay")).texture("texture", this.texture(block, loc).withSuffix("2")).texture("glow", this.texture(block, loc).withSuffix("2_glow")).renderType("cutout");
        ModelFile out3 = this.models().withExistingParent(this.name(block) + "3", Redux.locate("block/template_single_face_gloverlay")).texture("texture", this.texture(block, loc).withSuffix("3")).texture("glow", this.texture(block, loc).withSuffix("3_glow")).renderType("cutout");
        ModelFile out4 = this.models().withExistingParent(this.name(block) + "4", Redux.locate("block/template_single_face_gloverlay")).texture("texture", this.texture(block, loc).withSuffix("4")).texture("glow", this.texture(block, loc).withSuffix("4_glow")).renderType("cutout");
        ModelFile in = this.models().singleTexture(this.name(block) + "_inside", mcLoc("block/template_single_face"), this.texture(block, loc).withSuffix("_inside")).renderType("cutout");
        MultiPartBlockStateBuilder builder = this.getMultipartBuilder(block.get());
        for (Direction d : Direction.values())
        {
            Vec3i v3 = rot(d);
            // exterior
            builder.part()
                    .modelFile(out0).weight(10).rotationX(v3.getX()).rotationY(v3.getY()).nextModel()
                    .modelFile(out1).weight(7).rotationX(v3.getX()).rotationY(v3.getY()).nextModel()
                    .modelFile(out2).weight(5).rotationX(v3.getX()).rotationY(v3.getY()).nextModel()
                    .modelFile(out3).weight(5).rotationX(v3.getX()).rotationY(v3.getY()).nextModel()
                    .modelFile(out4).weight(3).rotationX(v3.getX()).rotationY(v3.getY())
                    .addModel().condition(dirToProp(d), true).end();
            // interior
            builder.part().modelFile(in).rotationX(v3.getX()).rotationY(v3.getY()).addModel().condition(dirToProp(d), false).end();
        }
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



    private Vec3i rot(Direction d) {
        return d == Direction.NORTH ? new Vec3i(0, 0, 0) :
               d == Direction.EAST ? new Vec3i(0, 90, 0) :
               d == Direction.SOUTH ? new Vec3i(0, 180, 0) :
               d == Direction.WEST ? new Vec3i(0, 270, 0) :
               d == Direction.UP ? new Vec3i(270, 0, 0) :
               new Vec3i(90, 0, 0);
    }
    private BooleanProperty dirToProp(Direction d) {
        return d == Direction.DOWN ? BlockStateProperties.DOWN :
               d == Direction.UP ? BlockStateProperties.UP :
               d == Direction.WEST ? BlockStateProperties.WEST :
               d == Direction.EAST ? BlockStateProperties.EAST :
               d == Direction.SOUTH ? BlockStateProperties.SOUTH :
                BlockStateProperties.NORTH;
    }
    public void grass(Block block, RegistryObject<Block> dirtBlock) {
        ModelFile grass = this.grassBlock(block, dirtBlock);
        ModelFile grassSnowed = this.cubeBottomTop(this.name(block) + "_snow", this.extend(this.texture(this.name(block), "natural/"), "_snow"), this.texture(dirtBlock, "natural/"), this.extend(this.texture(this.name(block), "natural/"), "_top"));
        this.getVariantBuilder(block).forAllStatesExcept((state) -> {
            boolean snowy = state.getValue(SnowyDirtBlock.SNOWY);
            return ConfiguredModel.allYRotations(snowy ? grassSnowed : grass, 0, false);
        }, AetherBlockStateProperties.DOUBLE_DROPS);
    }
    public void grassGlow(Block block, RegistryObject<Block> dirtBlock, boolean glowDirt, boolean topGlow) {
        ModelFile grass = this.models().withExistingParent(this.name(block), Redux.locate(BLOCK_FOLDER + "/cube_bottom_top_glow"))
                .texture("side", this.extend(this.texture(this.name(block), "natural/"), "_side"))
                .texture("bottom", this.texture(this.name(dirtBlock), "natural/"))
                .texture("top", this.extend(this.texture(this.name(block), "natural/"), "_top"))
                .texture("side_glow", this.extend(this.texture(this.name(block), "natural/"), "_side_glow"))
                .texture("bottom_glow", glowDirt ? this.texture(this.name(dirtBlock) + "_glow", "natural/") : this.texture("misc/glow_none"))
                .texture("top_glow", topGlow ? this.extend(this.texture(this.name(block), "natural/"), "_top_glow") : this.texture("misc/glow_none")).renderType("cutout");
        ModelFile grassSnowed = this.models().withExistingParent(this.name(block) + "_snowy", Redux.locate(BLOCK_FOLDER + "/cube_bottom_top_glow"))
                .texture("side", this.extend(this.texture(this.name(block), "natural/"), "_snow"))
                .texture("bottom", this.texture(this.name(dirtBlock), "natural/"))
                .texture("top", this.extend(this.texture(this.name(block), "natural/"), "_top"))
                .texture("side_glow", this.extend(this.texture(this.name(block), "natural/"), "_snow_glow"))
                .texture("bottom_glow", glowDirt ? this.texture(this.name(dirtBlock) + "_glow", "natural/") : this.texture("misc/glow_none"))
                .texture("top_glow", topGlow ? this.extend(this.texture(this.name(block), "natural/"), "_top_glow") : this.texture("misc/glow_none")).renderType("cutout");
        this.getVariantBuilder(block).forAllStatesExcept((state) -> {
            boolean snowy = state.getValue(SnowyDirtBlock.SNOWY);
            return ConfiguredModel.allYRotations(snowy ? grassSnowed : grass, 0, false);
        }, AetherBlockStateProperties.DOUBLE_DROPS);
    }


    public void aercloudAll(Block block, String location) {
        ResourceLocation texture = this.texture(this.name(block), location);
        this.aercloud(block, texture, texture, texture, texture, texture, texture, texture, texture, texture, texture, texture, texture, texture);
    }

    public void aercloud(Block block,
                         ResourceLocation upInside, ResourceLocation upOutside,
                         ResourceLocation downOutside, ResourceLocation downInside,
                         ResourceLocation northOutside, ResourceLocation northInside,
                         ResourceLocation southInside, ResourceLocation southOutside,
                         ResourceLocation westOutside, ResourceLocation westInside,
                         ResourceLocation eastInside, ResourceLocation eastOutside,
                         ResourceLocation particle) {
        ModelFile model = this.models().withExistingParent(this.name(block), this.mcLoc("block/block"))
                .texture("up_inside", upInside)
                .texture("up_outside", upOutside)
                .texture("down_outside", downOutside)
                .texture("down_inside", downInside)
                .texture("north_outside", northOutside)
                .texture("north_inside", northInside)
                .texture("south_inside", southInside)
                .texture("south_outside", southOutside)
                .texture("west_outside", westOutside)
                .texture("west_inside", westInside)
                .texture("east_inside", eastInside)
                .texture("east_outside", eastOutside)
                .texture("particle", particle)
                .renderType(new ResourceLocation("translucent"))
                .element().from(0.0F, 15.998F, 0.0F).to(16.0F, 16.0F, 16.0F)
                .face(Direction.DOWN).texture("#up_inside").uvs(0, 16, 16, 0).cullface(Direction.UP).end()
                .face(Direction.UP).texture("#up_outside").uvs(0, 0, 16, 16).cullface(Direction.UP).end()
                .end()
                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 0.002F, 16.0F)
                .face(Direction.DOWN).texture("#down_outside").uvs(0, 0, 16, 16).cullface(Direction.DOWN).end()
                .face(Direction.UP).texture("#down_inside").uvs(0, 16, 16, 0).cullface(Direction.DOWN).end()
                .end()
                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 0.002F)
                .face(Direction.NORTH).texture("#north_outside").uvs(0, 0, 16, 16).cullface(Direction.NORTH).end()
                .face(Direction.SOUTH).texture("#north_inside").uvs(16, 0, 0, 16).cullface(Direction.NORTH).end()
                .end()
                .element().from(0.0F, 0.0F, 15.998F).to(16.0F, 16.0F, 16.0F)
                .face(Direction.NORTH).texture("#south_inside").uvs(16, 0, 0, 16).cullface(Direction.SOUTH).end()
                .face(Direction.SOUTH).texture("#south_outside").uvs(0, 0, 16, 16).cullface(Direction.SOUTH).end()
                .end()
                .element().from(0.0F, 0.0F, 0.0F).to(0.002F, 16.0F, 16.0F)
                .face(Direction.WEST).texture("#west_outside").uvs(0, 0, 16, 16).cullface(Direction.WEST).end()
                .face(Direction.EAST).texture("#west_inside").uvs(16, 0, 0, 16).cullface(Direction.WEST).end()
                .end()
                .element().from(15.998F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                .face(Direction.WEST).texture("#east_inside").uvs(16, 0, 0, 16).cullface(Direction.EAST).end()
                .face(Direction.EAST).texture("#east_outside").uvs(0, 0, 16, 16).cullface(Direction.EAST).end()
                .end();
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(model));
    }


    public void floweringGrass(Block block, RegistryObject<Block> dirtBlock, RegistryObject<Block> sideBlock) {
        ModelFile grass = this.cubeBottomTop(this.name(block), this.extend(this.texture(sideBlock, "natural/"), "_side"), this.texture(dirtBlock, "natural/"), this.extend(this.texture(this.name(block), "natural/"), "_top"));
        ModelFile grassSnowed = this.cubeBottomTop(this.name(block) + "_snow", this.extend(this.texture(sideBlock, "natural/"), "_snow"), this.texture(dirtBlock, "natural/"), this.extend(this.texture(this.name(block), "natural/"), "_top"));
        this.getVariantBuilder(block).forAllStatesExcept((state) -> {
            boolean snowy = state.getValue(SnowyDirtBlock.SNOWY);
            return ConfiguredModel.allYRotations(snowy ? grassSnowed : grass, 0, false);
        }, AetherBlockStateProperties.DOUBLE_DROPS);
    }

    public void snowableLeaves(Block block, String location) {
        this.getVariantBuilder(block).forAllStatesExcept(state -> {
            boolean snowy = state.getValue(BlockStateProperties.SNOWY);
            return ConfiguredModel.builder().modelFile(snowy ? this.cubeBottomTop(this.name(block) + "_snowy", this.extend(this.texture(this.name(block), location), "_snowy"), this.texture(this.name(block), location), this.mcLoc("blocks/snow")) : this.cubeAll(block, location)).build();
        }, LeavesBlock.PERSISTENT, LeavesBlock.DISTANCE, LeavesBlock.WATERLOGGED, AetherBlockStateProperties.DOUBLE_DROPS);
    }

    public void flowerbed(Block block)
    {
        ModelFile flowerbed1 = this.models().withExistingParent(this.name(block) + "_1", mcLoc("flowerbed_1"))
                .texture("flowerbed", this.texture(this.name(block), "natural/"))
                .texture("stem", this.extend(this.texture(this.name(block), "natural/"), "_stem"))
                .renderType("cutout");
        ModelFile flowerbed2 = this.models().withExistingParent(this.name(block) + "_2", mcLoc("flowerbed_2"))
                .texture("flowerbed", this.texture(this.name(block), "natural/"))
                .texture("stem", this.extend(this.texture(this.name(block), "natural/"), "_stem"))
                .renderType("cutout");
        ModelFile flowerbed3 = this.models().withExistingParent(this.name(block) + "_3", mcLoc("flowerbed_3"))
                .texture("flowerbed", this.texture(this.name(block), "natural/"))
                .texture("stem", this.extend(this.texture(this.name(block), "natural/"), "_stem"))
                .renderType("cutout");
        ModelFile flowerbed4 = this.models().withExistingParent(this.name(block) + "_4", mcLoc("flowerbed_4"))
                .texture("flowerbed", this.texture(this.name(block), "natural/"))
                .texture("stem", this.extend(this.texture(this.name(block), "natural/"), "_stem"))
                .renderType("cutout");
        MultiPartBlockStateBuilder builder = this.getMultipartBuilder(block);
        for (Direction d : new Direction[] {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST})
        {
            builder.part().modelFile(flowerbed1).rotationY(d.getOpposite().get2DDataValue() * 90).addModel().condition(PinkPetalsBlock.AMOUNT, 1, 2, 3, 4).condition(PinkPetalsBlock.FACING, d).end();
            builder.part().modelFile(flowerbed2).rotationY(d.getOpposite().get2DDataValue() * 90).addModel().condition(PinkPetalsBlock.AMOUNT, 2, 3, 4).condition(PinkPetalsBlock.FACING, d).end();
            builder.part().modelFile(flowerbed3).rotationY(d.getOpposite().get2DDataValue() * 90).addModel().condition(PinkPetalsBlock.AMOUNT, 3, 4).condition(PinkPetalsBlock.FACING, d).end();
            builder.part().modelFile(flowerbed4).rotationY(d.getOpposite().get2DDataValue() * 90).addModel().condition(PinkPetalsBlock.AMOUNT, 4).condition(PinkPetalsBlock.FACING, d).end();
        }


    }

    public void floweringFieldsprootLeafBlock(Supplier<? extends Block> block, String location) {
        getVariantBuilder(block.get())
                .forAllStatesExcept(state -> {
                            int i = state.getValue(ReduxStates.PRISMATICNESS);
                            return ConfiguredModel.builder().modelFile(models().cubeAll(name(block) + "_" + i, texture(name(block) + "_" + i, location)).renderType(new ResourceLocation("cutout_mipped"))).build();
                        }
                        , AetherBlockStateProperties.DOUBLE_DROPS, ExtendedDistanceLeavesBlock.EXTENDED_DISTANCE);
    }


    public void classicFieldsprootLeafBlock(Supplier<? extends Block> block, Supplier<? extends Block> texture, String location, boolean end) {
        getVariantBuilder(block.get())
                .forAllStatesExcept(state -> {
                            int i = end ? 6 - state.getValue(ReduxStates.PRISMATICNESS_DECREASED) : state.getValue(ReduxStates.PRISMATICNESS_DECREASED);
                            return ConfiguredModel.builder().modelFile(models().getExistingFile(texture(name(texture) + "_" + i))).build();
                        }
                        , AetherBlockStateProperties.DOUBLE_DROPS, LeavesBlock.DISTANCE);
    }


    public void fieldsprootPetals(Block block, String stemTexture)
    {
        MultiPartBlockStateBuilder builder = this.getMultipartBuilder(block);
        for (int color = 0; color <= 6; color++) {
            ModelFile flowerbed_petal1 = this.models().withExistingParent(this.name(block) + "_" + color + "_petal1", modLoc("flowerbed_1"))
                    .texture("flowerbed", this.texture(this.name(block) + "_" + color, "natural/"))
                    .texture("stem", this.texture(stemTexture))
                    .renderType("cutout");
            ModelFile flowerbed_petal2 = this.models().withExistingParent(this.name(block) + "_" + color + "_petal2", modLoc("flowerbed_2"))
                    .texture("flowerbed", this.texture(this.name(block) + "_" + color, "natural/"))
                    .texture("stem", this.texture(stemTexture))
                    .renderType("cutout");
            ModelFile flowerbed_petal3 = this.models().withExistingParent(this.name(block) + "_" + color + "_petal3", modLoc("flowerbed_3"))
                    .texture("flowerbed", this.texture(this.name(block) + "_" + color, "natural/"))
                    .texture("stem", this.texture(stemTexture))
                    .renderType("cutout");
            ModelFile flowerbed_petal4 = this.models().withExistingParent(this.name(block) +"_" + color + "_petal4", modLoc("flowerbed_4"))
                    .texture("flowerbed", this.texture(this.name(block) + "_" + color, "natural/"))
                    .texture("stem", this.texture(stemTexture))
                    .renderType("cutout");
            for (Direction d : Direction.Plane.HORIZONTAL) {
                builder.part().modelFile(flowerbed_petal1).rotationY(d.getOpposite().get2DDataValue() * 90).addModel().condition(ReduxStates.PETAL_1, PetalPrismaticness.getFromIndex(color)).condition(PinkPetalsBlock.FACING, d).end();
                builder.part().modelFile(flowerbed_petal2).rotationY(d.getOpposite().get2DDataValue() * 90).addModel().condition(ReduxStates.PETAL_2, PetalPrismaticness.getFromIndex(color)).condition(PinkPetalsBlock.FACING, d).end();
                builder.part().modelFile(flowerbed_petal3).rotationY(d.getOpposite().get2DDataValue() * 90).addModel().condition(ReduxStates.PETAL_3, PetalPrismaticness.getFromIndex(color)).condition(PinkPetalsBlock.FACING, d).end();
                builder.part().modelFile(flowerbed_petal4).rotationY(d.getOpposite().get2DDataValue() * 90).addModel().condition(ReduxStates.PETAL_4, PetalPrismaticness.getFromIndex(color)).condition(PinkPetalsBlock.FACING, d).end();
            }
        }



    }

    public void flowerbed(Block block, String stemTexture)
    {
        ModelFile flowerbed1 = this.models().withExistingParent(this.name(block) + "_1", mcLoc("flowerbed_1"))
                .texture("flowerbed", this.texture(this.name(block), "natural/"))
                .texture("stem", this.texture(stemTexture, "natural/"))
                .renderType("cutout");
        ModelFile flowerbed2 = this.models().withExistingParent(this.name(block) + "_2", mcLoc("flowerbed_2"))
                .texture("flowerbed", this.texture(this.name(block), "natural/"))
                .texture("stem", this.texture(stemTexture, "natural/"))
                .renderType("cutout");
        ModelFile flowerbed3 = this.models().withExistingParent(this.name(block) + "_3", mcLoc("flowerbed_3"))
                .texture("flowerbed", this.texture(this.name(block), "natural/"))
                .texture("stem", this.texture(stemTexture, "natural/"))
                .renderType("cutout");
        ModelFile flowerbed4 = this.models().withExistingParent(this.name(block) + "_4", mcLoc("flowerbed_4"))
                .texture("flowerbed", this.texture(this.name(block), "natural/"))
                .texture("stem", this.texture(stemTexture, "natural/"))
                .renderType("cutout");
        MultiPartBlockStateBuilder builder = this.getMultipartBuilder(block);
        for (Direction d : new Direction[] {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST})
        {
            builder.part().modelFile(flowerbed1).rotationY(d.getOpposite().get2DDataValue() * 90).addModel().condition(PinkPetalsBlock.AMOUNT, 1, 2, 3, 4).condition(PinkPetalsBlock.FACING, d).end();
            builder.part().modelFile(flowerbed2).rotationY(d.getOpposite().get2DDataValue() * 90).addModel().condition(PinkPetalsBlock.AMOUNT, 2, 3, 4).condition(PinkPetalsBlock.FACING, d).end();
            builder.part().modelFile(flowerbed3).rotationY(d.getOpposite().get2DDataValue() * 90).addModel().condition(PinkPetalsBlock.AMOUNT, 3, 4).condition(PinkPetalsBlock.FACING, d).end();
            builder.part().modelFile(flowerbed4).rotationY(d.getOpposite().get2DDataValue() * 90).addModel().condition(PinkPetalsBlock.AMOUNT, 4).condition(PinkPetalsBlock.FACING, d).end();
        }


    }

    public void frostedGrass(Block block, RegistryObject<Block> dirtBlock) {
        ModelFile grass = this.grassBlock(block, dirtBlock);

        ModelFile grassSnowed = this.cubeBottomTop(this.name(block) + "_snow", this.extend(this.texture(this.name(block), "natural/"), "_snow"), this.texture(dirtBlock, "natural/"), this.extend(this.texture(this.name(block), "natural/"), "_top"));


        ModelFile grassSuperSnowed = this.models().withExistingParent(this.name(block) + "_super_snowed", modLoc("cube_bottom_top_super_snowed"))
                .texture("side", this.extend(this.texture(this.name(block), "natural/"), "_snow"))
                .texture("particle", this.extend(this.texture(this.name(block), "natural/"), "_snow"))
                .texture("bottom", this.texture(dirtBlock, "natural/"))
                .texture("top", this.extend(this.texture(this.name(block), "natural/"), "_top"));

                this.getVariantBuilder(block).forAllStatesExcept((state) -> {
            boolean snowy = state.getValue(SnowyDirtBlock.SNOWY);
            boolean super_snowed = state.getValue(ReduxStates.SNOWED);
            return ConfiguredModel.allYRotations(super_snowed ? grassSuperSnowed : snowy ? grassSnowed : grass, 0, false);
        }, AetherBlockStateProperties.DOUBLE_DROPS);
    }

    public ModelFile grassBlock(Block block, RegistryObject<Block> dirtBlock) {
        return this.cubeBottomTop(this.name(block), this.extend(this.texture(this.name(block), "natural/"), "_side"), this.texture(dirtBlock, "natural/"), this.extend(this.texture(this.name(block), "natural/"), "_top"));
    }
    public ResourceLocation texture(String name) {
        return modLoc("block/" + name);
    }

    public ResourceLocation  texture(String name, String location) {
        return modLoc("block/" + location + name);
    }
    public ResourceLocation  texture(RegistryObject<? extends Block> block, String location) {
        return new ResourceLocation(block.getKey().location().getNamespace(), "block/" + location + name(block.get()));
    }

    public void enchantedLogGlow(RotatedPillarBlock block, RotatedPillarBlock baseBlock) {
        this.axisBlockGlow(block, this.texture(this.name(block), "natural/"), this.extend(this.texture(this.name(baseBlock), "natural/"), "_top"));
    }

    public void woodGlow(RotatedPillarBlock block, RotatedPillarBlock baseBlock) {
        this.axisBlockGlow(block, this.texture(this.name(baseBlock), "natural/"), this.texture(this.name(baseBlock), "natural/"));
    }
    public void frostedCrossBlock(Block block, ModelFile noSnow, ModelFile snowy) {

        this.getVariantBuilder(block).forAllStates((state) -> {
            boolean snowiness = state.getValue(ReduxStates.SNOWY_TEXTURE);
            return ConfiguredModel.builder().modelFile(snowiness ? snowy : noSnow).build();         }       );
    }




    public void frostedCrossBlock(Block block, String location) {
        this.frostedCrossBlock(block, models().cross(this.name(block), this.texture(this.name(block), location)).renderType("cutout"), models().cross(this.name(block) + "_snowy", this.texture(this.name(block) + "_snowy", location)).renderType("cutout"));
    }

    public void tintableShortGrass(AetherShortGrassBlock block, String location) {
        this.getVariantBuilder(block).forAllStates((state) -> {
            boolean enchanted = state.getValue(ReduxStates.ENCHANTED);
            if (enchanted) {
                return ConfiguredModel.builder().modelFile(models().cross(state.getValue(ReduxStates.GRASS_SIZE).getSerializedName()  + "_enchanted_grass", modLoc("block/natural/enchanted_" + state.getValue(ReduxStates.GRASS_SIZE).getSerializedName()  + "_grass")).renderType("cutout")).build();
            }
            return ConfiguredModel.builder().modelFile(models().singleTexture(state.getValue(ReduxStates.GRASS_SIZE).getSerializedName() + "_aether_grass", mcLoc(BLOCK_FOLDER + "/tinted_cross"), "cross", modLoc("block/natural/aether_" + state.getValue(ReduxStates.GRASS_SIZE).getSerializedName() + "_grass")).renderType("cutout")).build();       }       );
    }

    public void tintedGrass(Supplier<? extends Block> block) {
        ResourceLocation dirt = new ResourceLocation(Aether.MODID, BLOCK_FOLDER + "/natural/aether_dirt");
        ResourceLocation side = new ResourceLocation(Aether.MODID, BLOCK_FOLDER + "/natural/aether_grass_block_side");
        ResourceLocation snow = new ResourceLocation(Aether.MODID, BLOCK_FOLDER + "/natural/aether_grass_block_snow");
        ResourceLocation top = Redux.locate(BLOCK_FOLDER + "/natural/aether_grass_block_top_overlay");
        ResourceLocation overlay = Redux.locate(BLOCK_FOLDER + "/natural/aether_grass_block_side_overlay");
        ModelFile grass = models().withExistingParent(name(block), modLoc(BLOCK_FOLDER + "/tinted_grass_block"))
                .texture("particle", dirt).texture("bottom", dirt).texture("side", side).texture("top", top).texture("overlay", overlay);
        ModelFile grassSnowed = cubeBottomTop(name(block) + "_snow", snow, dirt, top);
        getVariantBuilder(block.get()).forAllStatesExcept(state -> {
            boolean snowy = state.getValue(SnowyDirtBlock.SNOWY);
            return ConfiguredModel.allYRotations(snowy ? grassSnowed : grass, 0, false);
        }, AetherBlockStateProperties.DOUBLE_DROPS, ReduxStates.GRASS_BLOCK_TINT);
    }

    public void frostedCrossBlockGlow(Block block, String location) {
        BlockModelBuilder cross = models().withExistingParent(this.name(block), Redux.locate(BLOCK_FOLDER + "/cross/cross_glowing_overlay"))
                .texture("cross", this.texture(this.name(block), location))
                .texture("overlay", this.texture(this.name(block) + "_glow", location)).renderType("cutout" );
        BlockModelBuilder crossSnowy = models().withExistingParent(this.name(block) + "_snowy", Redux.locate(BLOCK_FOLDER + "/cross/cross_glowing_overlay"))
                .texture("cross", this.texture(this.name(block) + "_snowy", location))
                .texture("overlay", this.texture(this.name(block) + "_glow_snowy", location)).renderType("cutout");
        this.frostedCrossBlock(block, cross, crossSnowy);
    }

    public void lantern(Block block, String location) {
        BlockModelBuilder lantern = models().withExistingParent(this.name(block), mcLoc("template_lantern"))
                .texture("lantern", this.texture(this.name(block), location)).renderType("cutout");
        BlockModelBuilder hangingLantern = models().withExistingParent("hanging_" + this.name(block), mcLoc("template_hanging_lantern"))
                .texture("lantern", this.texture(this.name(block), location)).renderType("cutout");
        this.getVariantBuilder(block).forAllStates((state -> ConfiguredModel.builder().modelFile(state.getValue(LanternBlock.HANGING) ? hangingLantern : lantern).build()));
    }
    public void chain(Block block, String location) {
        BlockModelBuilder chain = models().withExistingParent(this.name(block), Redux.locate("template_chain"))
                .texture("chain", this.texture(this.name(block), location)).renderType("cutout");
        this.getVariantBuilder(block).forAllStates((state -> {
            Direction.Axis axis = state.getValue(ChainBlock.AXIS);
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder().modelFile(chain);
            if (axis == Direction.Axis.X) { builder = builder.rotationY(90); }
            if (axis != Direction.Axis.Y) { builder = builder.rotationX(90); }
            return builder.build();
        }));
    }


    public void crossGlowHarvestable(Block block, String location) {
        BlockModelBuilder cross = models().withExistingParent(this.name(block), Redux.locate(BLOCK_FOLDER + "/cross/cross_glow"))
                .texture("cross", this.texture(this.name(block), location)).renderType("cutout");
        BlockModelBuilder crossHarvested = models().withExistingParent("harvested_" + this.name(block), Redux.locate(BLOCK_FOLDER + "/cross/cross_glow"))
                .texture("cross", this.texture("harvested_" + this.name(block), location)).renderType("cutout");
        this.harvestableCrossBlock(block, crossHarvested, cross);
    }
    public void crossGlowHarvestableOccluded(Block block, String location) {
        this.harvestableCrossBlock(block, crossOccluded("harvested_" + this.name(block), this.texture("harvested_" + this.name(block), location)).renderType("cutout"), crossOccluded(this.name(block), this.texture(this.name(block), location)).renderType("cutout"));
    }

    public ModelBuilder<BlockModelBuilder> crossOccluded(String name, ResourceLocation cross) {
        return models().singleTexture(name, Redux.locate(BLOCK_FOLDER + "/cross/cross_occluded"), "cross", cross);
    }

    public void harvestableCrossBlock(Block block, ModelFile harvested, ModelFile full) {

        this.getVariantBuilder(block).forAllStates((state) -> {
            boolean isHarvested = state.getValue(ReduxStates.HARVESTED);
            return ConfiguredModel.builder().modelFile(isHarvested ? harvested : full).build();
        });
    }


    public void crossDoubleTopGlow(Block block, String location) {
        this.getVariantBuilder(block).forAllStates(state -> {
            ModelFile bottom = models().cross(this.name(block) + "_bottom", this.texture(this.name(block) + "_bottom", location)).renderType("cutout");
            ModelFile top = models().withExistingParent(this.name(block) + "_top", Redux.locate(BLOCK_FOLDER + "/cross/cross_glowing_overlay"))
                    .texture("cross", this.texture(this.name(block) + "_top", location))
                    .texture("overlay", this.texture(this.name(block) + "_top" + "_glow", location)).renderType("cutout");


            return ConfiguredModel.builder().modelFile(state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER ? bottom : top).build();
        });


    }
    public void crossDouble(Block block, String location) {
        this.getVariantBuilder(block).forAllStates(state -> {
            ModelFile top = models().cross(this.name(block) + "_top", this.texture(this.name(block) + "_top", location)).renderType("cutout");
            ModelFile bottom = models().cross(this.name(block) + "_bottom", this.texture(this.name(block) + "_bottom", location)).renderType("cutout");
            return ConfiguredModel.builder().modelFile(state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER ? bottom : top).build();
        });


    }
    public void crossGlowDouble(Block block, String location) {
        this.getVariantBuilder(block).forAllStates(state -> {
            ModelFile top = models().singleTexture(this.name(block) + "_top", Redux.locate(BLOCK_FOLDER + "/cross/cross_glow"), "cross", this.texture(this.name(block) + "_top", location)).renderType("cutout");
            ModelFile bottom = models().singleTexture(this.name(block) + "_bottom", Redux.locate(BLOCK_FOLDER + "/cross/cross_glow"), "cross", this.texture(this.name(block) + "_bottom", location)).renderType("cutout");
            return ConfiguredModel.builder().modelFile(state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER ? bottom : top).build();
        });


    }

    public ResourceLocation texture(String name, String location, String suffix) {
        return modLoc("block/" + location + name + suffix);
    }



    public void veridiumLantern(Block block, String location) {
        BlockModelBuilder lantern = models().withExistingParent(this.name(block), Redux.locate("template_veridium_lantern"))
                .texture("lantern", this.texture(this.name(block), location)).renderType("cutout");
        BlockModelBuilder hangingLantern = models().withExistingParent("hanging_" + this.name(block), Redux.locate("template_hanging_veridium_lantern"))
                .texture("lantern", this.texture(this.name(block), location)).renderType("cutout");
        BlockModelBuilder hangingChain = models().withExistingParent("hanging_" + this.name(block) + "_chain", Redux.locate("template_hanging_veridium_lantern_chain"))
                .texture("lantern", this.texture(this.name(block), location)).renderType("cutout");
        this.getMultipartBuilder(block).part().modelFile(lantern).rotationY(90).addModel().condition(VeridiumLanternBlock.AXIS, Direction.Axis.X).condition(LanternBlock.HANGING, false).end();
        this.getMultipartBuilder(block).part().modelFile(lantern).rotationY(0).addModel().condition(VeridiumLanternBlock.AXIS, Direction.Axis.Z).condition(LanternBlock.HANGING, false).end();
        this.getMultipartBuilder(block).part().modelFile(hangingLantern).rotationY(90).addModel().condition(VeridiumLanternBlock.AXIS, Direction.Axis.X).condition(LanternBlock.HANGING, true).end();
        this.getMultipartBuilder(block).part().modelFile(hangingLantern).rotationY(0).addModel().condition(VeridiumLanternBlock.AXIS, Direction.Axis.Z).condition(LanternBlock.HANGING, true).end();
        this.getMultipartBuilder(block).part().modelFile(hangingChain).addModel().condition(LanternBlock.HANGING, true).end();
    }

    protected String name(Supplier<? extends Block> block) {
        return ForgeRegistries.BLOCKS.getKey(block.get()).getPath();
    }

    public void carpet(Block block, Block baseBlock, String location) {
        this.simpleBlock(block, this.models().singleTexture(this.name(block), mcLoc(BLOCK_FOLDER + "/carpet"), "wool", this.texture(this.name(baseBlock), location)));

    }
    public void cutoutCarpet(Block block, Block baseBlock, String location) {
        this.simpleBlock(block, this.models().singleTexture(this.name(block), mcLoc(BLOCK_FOLDER + "/carpet"), "wool", this.texture(this.name(baseBlock), location)).renderType("cutout"));

    }

    public void leafPile(RegistryObject<? extends Block> block, RegistryObject<? extends Block> baseBlock, String location) {
        this.getVariantBuilder(block.get()).forAllStates(
                state -> ConfiguredModel.builder()
                        .modelFile(state.getValue(LeafPileBlock.LAYERS) == 16 ? this.models().cubeAll(this.name(block) + "_full", this.texture(baseBlock, location)).renderType("cutout") : this.models().singleTexture(
                                this.name(block) + state.getValue(LeafPileBlock.LAYERS),
                                modLoc(BLOCK_FOLDER + "/layer/layer_size" + state.getValue(LeafPileBlock.LAYERS)),
                                "block", this.texture(baseBlock, location)).renderType("cutout")).build());

    }


    public void potGlow(Block block, Block flower, String location) {
        ModelFile pot = this.models().withExistingParent(this.name(block), Redux.locate("block/pot/flower_pot_glow"))
                .texture("plant", this.modLoc("block/" + location + this.name(flower)))
                .texture("overlay", this.modLoc("block/" + location + this.name(flower) + "_glow")).renderType("cutout");
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(pot));
    }

    public void block(Supplier<? extends Block> block, String location) {
        simpleBlock(block.get(), cubeAll(block, location));
    }

    public void blockDoubleDrops(Supplier<? extends Block> block, String location) {
        getVariantBuilder(block.get()).forAllStatesExcept(state -> ConfiguredModel.builder().modelFile(cubeAll(block, location)).build(), AetherBlockStateProperties.DOUBLE_DROPS);
    }

    public ModelFile cubeAllCutout(Block block, String location) {
        return this.models().cubeAll(this.name(block), this.texture(this.name(block), location)).renderType("cutout");
    }
    public void randomBlockDoubleDrops(Supplier<? extends Block> block, String location) {
        getVariantBuilder(block.get()).forAllStatesExcept(state -> ConfiguredModel.allYRotations(cubeAll(block, location), 0, false), AetherBlockStateProperties.DOUBLE_DROPS);
    }

    public void crossBlock(Supplier<? extends Block> block, String location) {
        crossBlock(block, models().cross(name(block), texture(name(block), location)).renderType("cutout"));
    }



    private void crossBlock(Supplier<? extends Block> block, ModelFile model) {
        getVariantBuilder(block.get()).forAllStates(state -> ConfiguredModel.builder().modelFile(model).build());
    }

    @Deprecated
    public void largeMushroomBlock(Supplier<? extends Block> mushroom, Supplier<? extends Block> particle, String location, String particleLoc) {
        ModelFile model = models().withExistingParent(this.name(mushroom), modLoc(BLOCK_FOLDER + "/template_mushroom_block"))
                .texture("mushroom", this.texture(name(mushroom), location))
                .texture("particle", this.texture(name(particle), particleLoc))
                .renderType("cutout");
        getVariantBuilder(mushroom.get()).forAllStates((state -> ConfiguredModel.builder().modelFile(model).build()));
    }

    public void shimmerstool(Supplier<? extends Block> mushroom, String location) {
        ModelFile model = models().withExistingParent(this.name(mushroom), modLoc(BLOCK_FOLDER + "/template_shimmerstool"))
                .texture("side", this.texture(name(mushroom) + "_side", location))
                .texture("top", this.texture(name(mushroom) + "_top", location))
                .texture("bottom", this.texture(name(mushroom) + "_bottom", location));
        getVariantBuilder(mushroom.get()).forAllStates((state -> ConfiguredModel.builder().modelFile(model).build()));
    }

    public void pottedShimmerstool(Block potBlock, Block mushroom, String location) {
        ModelFile pot = this.models().withExistingParent(this.name(potBlock), Redux.locate(BLOCK_FOLDER + "/template_potted_shimmerstool"))
                .texture("side", this.texture(name(mushroom) + "_side", location))
                .texture("top", this.texture(name(mushroom) + "_top", location))
                .texture("bottom", this.texture(name(mushroom) + "_bottom", location));
        this.getVariantBuilder(potBlock).partialState().addModels(new ConfiguredModel(pot));
    }

    public void pottedMushroomBlock(Block potBlock, Block mushroom, String location) {
        ModelFile pot = this.models().withExistingParent(this.name(potBlock), Redux.locate(BLOCK_FOLDER + "/template_potted_mushroom_block")).texture("mushroom", this.modLoc("block/" + location + "potted_" + this.name(mushroom))).renderType("cutout");
        this.getVariantBuilder(potBlock).partialState().addModels(new ConfiguredModel(pot));
    }

    public void largeMushroomBlockGlow(Supplier<? extends Block> mushroom, Supplier<? extends Block> particle, String location, String particleLoc)
    {

        ModelFile model = models().withExistingParent(this.name(mushroom), modLoc(BLOCK_FOLDER + "/template_mushroom_block_glow"))
                .texture("mushroom", this.texture(name(mushroom), location))
                .texture("particle", this.texture(name(particle), particleLoc))
                .texture("glow", this.texture(name(mushroom) + "_glow", location))
                .renderType("cutout");
        getVariantBuilder(mushroom.get()).forAllStates((state -> ConfiguredModel.builder().modelFile(model).build()));
    }


    public void crossGlowOverlay(Block block, String location) {
        BlockModelBuilder cross = models().withExistingParent(this.name(block), Redux.locate(BLOCK_FOLDER + "/cross/cross_glowing_overlay"))
                .texture("cross", this.texture(this.name(block), location))
                .texture("overlay", this.texture(this.name(block) + "_glow", location)).renderType("cutout");
        this.crossBlock(block, cross);
    }

    public void crossTintedGlow(Block block, String location) {
        BlockModelBuilder cross = models().withExistingParent(this.name(block), Redux.locate(BLOCK_FOLDER + "/cross/cross_tinted_glow"))
                .texture("cross", this.texture(this.name(block), location))
                .texture("overlay", this.texture(this.name(block) + "_glow", location)).renderType("cutout");
        this.crossBlock(block, cross);
    }

    public void crossTintedDualGloverlay(Block block, String location) {
        BlockModelBuilder cross = models().withExistingParent(this.name(block), Redux.locate(BLOCK_FOLDER + "/cross/cross_tinted_dual_gloverlay"))
                .texture("cross", this.texture(this.name(block), location))
                .texture("glow", this.texture(this.name(block) + "_glow", location)).renderType("cutout")
                .texture("overlay", this.texture(this.name(block) + "_overlay", location)).renderType("cutout");
        this.crossBlock(block, cross);
    }
    public void crossTintedOverlay(Block block, String location) {
        BlockModelBuilder cross = models().withExistingParent(this.name(block), Redux.locate(BLOCK_FOLDER + "/cross/cross_tinted_overlay"))
                .texture("cross", this.texture(this.name(block), location))
                .texture("overlay", this.texture(this.name(block) + "_overlay", location)).renderType("cutout");
        this.crossBlock(block, cross);
    }


    public void crossTintedOverlayEnchantable(Block block, String location) {
        BlockModelBuilder cross = models().withExistingParent(this.name(block), Redux.locate(BLOCK_FOLDER + "/cross/cross_tinted_overlay"))
                .texture("cross", this.texture(this.name(block), location))
                .texture("overlay", this.texture(this.name(block) + "_overlay", location)).renderType("cutout");
        BlockModelBuilder ench = models().cross("enchanted_" + this.name(block), this.texture("enchanted_" + this.name(block), location)).renderType("cutout");
        this.getVariantBuilder(block).forAllStates((state) ->  ConfiguredModel.builder().modelFile(state.getValue(ReduxStates.ENCHANTED) ? ench : cross).build());
    }
    public void crossTintedEnchantable(Block block, String location) {
        BlockModelBuilder cross = models().withExistingParent(this.name(block), mcLoc(BLOCK_FOLDER + "/tinted_cross"))
                .texture("cross", this.texture(this.name(block), location)).renderType("cutout");
        BlockModelBuilder ench = models().cross("enchanted_" + this.name(block), this.texture("enchanted_" + this.name(block), location)).renderType("cutout");
        this.getVariantBuilder(block).forAllStates((state) ->  ConfiguredModel.builder().modelFile(state.getValue(ReduxStates.ENCHANTED) ? ench : cross).build());
    }
    public void crossTinted(Block block, String location) {
        BlockModelBuilder cross = models().withExistingParent(this.name(block), mcLoc(BLOCK_FOLDER + "/tinted_cross"))
                .texture("cross", this.texture(this.name(block), location)).renderType("cutout");
        this.crossBlock(block, cross);
    }

    public void crop(Block block, String location) {
        BlockModelBuilder cross = models().withExistingParent(this.name(block), Redux.locate(BLOCK_FOLDER + "/template_crop_plant"))
                .texture("plant", this.texture(this.name(block), location)).renderType("cutout");
        this.crossBlock(block, cross);
    }
    public void cropGrowable(Block block, String location, IntegerProperty ageProperty) {
        this.getVariantBuilder(block).forAllStates((state) -> {
            int stage = state.getValue(ageProperty);
            BlockModelBuilder cross = models().withExistingParent(this.name(block) + "_stage" + stage, Redux.locate(BLOCK_FOLDER + "/crop_lowered"))
                    .texture("plant", this.texture(this.name(block) + "_stage" + stage, location)).renderType("cutout");
            return ConfiguredModel.builder().modelFile(cross).build();
        });
    }
    public void cropOccluded(Block block, String location) {
        BlockModelBuilder cross = models().withExistingParent(this.name(block), Redux.locate(BLOCK_FOLDER + "/template_crop_plant_occluded"))
                .texture("plant", this.texture(this.name(block), location)).renderType("cutout");
        this.crossBlock(block, cross);
    }

    public void glowingBlock(Block block, String location) {
        BlockModelBuilder cross = models().withExistingParent(this.name(block), Redux.locate(BLOCK_FOLDER + "/cube_all_glow"))
                .texture("all", this.texture(this.name(block), location))
                .texture("glow", this.texture(this.name(block) + "_glow", location)).renderType("cutout");
        this.simpleBlock(block, cross);
    }





    public void saplingBlock(Supplier<? extends Block> block, String location) {
        ModelFile sapling = models().cross(name(block), texture(name(block), location));
        getVariantBuilder(block.get()).forAllStatesExcept(state -> ConfiguredModel.builder().modelFile(sapling).build(),
                SaplingBlock.STAGE);
    }
    public void stairs(Supplier<? extends StairBlock> block, Supplier<? extends Block> baseBlock, String location) {
        stairsBlock(block.get(), texture(name(baseBlock), location));
    }

    public void slab(Supplier<? extends SlabBlock> block, Supplier<? extends Block> baseBlock, String location) {
        slabBlock(block.get(), texture(name(baseBlock)), texture(name(baseBlock), location));
    }

    public void buttonBlock(Supplier<? extends ButtonBlock> block, ResourceLocation texture) {
        ModelFile button = models().button(name(block), texture);
        ModelFile buttonPressed = models().buttonPressed(name(block) + "_pressed", texture);
        buttonBlock(block.get(), button, buttonPressed);
    }

    public void pressurePlateBlock(Supplier<? extends PressurePlateBlock> block, ResourceLocation texture) {
        ModelFile pressurePlate = models().pressurePlate(name(block), texture);
        ModelFile pressurePlateDown = models().pressurePlateDown(name(block) + "_down", texture);
        pressurePlateBlock(block.get(), pressurePlate, pressurePlateDown);
    }

    public void signBlock(Supplier<? extends StandingSignBlock> signBlock, Supplier<? extends WallSignBlock> wallSignBlock, ResourceLocation texture) {
        ModelFile sign = models().sign(name(signBlock), texture);
        signBlock(signBlock.get(), wallSignBlock.get(), sign);
    }

    public void fence(Supplier<? extends FenceBlock> block, Supplier<? extends Block> baseBlock, String location) {
        fenceBlock(block.get(), texture(name(baseBlock), location));
        fenceColumn(block, name(baseBlock), location);
    }

    private void fenceColumn(Supplier<? extends FenceBlock> block, String side, String location) {
        String baseName = name(block);
        fourWayBlock(block.get(),
                models().fencePost(baseName + "_post", texture(side, location)),
                models().fenceSide(baseName + "_side", texture(side, location)));
    }

    public void fenceGateBlock(Supplier<? extends FenceGateBlock> block, Supplier<? extends Block> baseBlock, String location) {
        fenceGateBlockInternal(block.get(), name(block), texture(name(baseBlock), location));
    }

    public void fenceGateBlockInternal(FenceGateBlock block, String baseName, ResourceLocation texture) {
        ModelFile gate = models().fenceGate(baseName, texture);
        ModelFile gateOpen = models().fenceGateOpen(baseName + "_open", texture);
        ModelFile gateWall = models().fenceGateWall(baseName + "_wall", texture);
        ModelFile gateWallOpen = models().fenceGateWallOpen(baseName + "_wall_open", texture);
        fenceGateBlock(block, gate, gateOpen, gateWall, gateWallOpen);
    }

    public void wallBlock(Supplier<? extends WallBlock> block, Supplier<? extends Block> baseBlock, String location) {
        wallBlockInternal(block.get(), name(block), texture(name(baseBlock), location));
    }

    public void wallBlockInternal(WallBlock block, String baseName, ResourceLocation texture) {
        wallBlock(block, models().wallPost(baseName + "_post", texture), models().wallSide(baseName + "_side", texture), models().wallSideTall(baseName + "_side_tall", texture));
    }

    public void grass(Supplier<? extends Block> block, RegistryObject<? extends Block> dirtBlock) {
        if (Redux.ancientAetherCompat()) {
            ModelFile grass = cubeBottomTop(name(block), extend(texture(name(block), "natural/"), "_side"), texture(dirtBlock, "natural/"), extend(texture(name(block), "natural/"), "_top"));
            ModelFile grassSnowed = cubeBottomTop(name(block) + "_snow", extend(texture(name(block), "natural/"), "_snow"), texture(dirtBlock, "natural/"), extend(texture(name(block), "natural/"), "_top"));
            getVariantBuilder(block.get()).forAllStatesExcept(state -> {
                boolean snowy = state.getValue(SnowyDirtBlock.SNOWY);
                return ConfiguredModel.allYRotations(snowy ? grassSnowed : grass, 0, false);
            }, AetherBlockStateProperties.DOUBLE_DROPS, AncientAetherBlockStateProperties.TYPE);
        } else {
            ModelFile grass = cubeBottomTop(name(block), extend(texture(name(block), "natural/"), "_side"), texture(dirtBlock, "natural/"), extend(texture(name(block), "natural/"), "_top"));
            ModelFile grassSnowed = cubeBottomTop(name(block) + "_snow", extend(texture(name(block), "natural/"), "_snow"), texture(dirtBlock, "natural/"), extend(texture(name(block), "natural/"), "_top"));
            getVariantBuilder(block.get()).forAllStatesExcept(state -> {
                boolean snowy = state.getValue(SnowyDirtBlock.SNOWY);
                return ConfiguredModel.allYRotations(snowy ? grassSnowed : grass, 0, false);
            }, AetherBlockStateProperties.DOUBLE_DROPS);
        }
    }

    public void enchantedGrass(Supplier<? extends Block> block, Supplier<? extends Block> grassBlock, Supplier<? extends Block> dirtBlock) {
        ModelFile grass = cubeBottomTop(name(block), extend(texture(name(block), "natural/"), "_side"), texture(name(dirtBlock), "natural/"), extend(texture(name(block), "natural/"), "_top"));
        ModelFile grassSnowed = cubeBottomTop(name(grassBlock) + "_snow", extend(texture(name(grassBlock), "natural/"), "_snow"), texture(name(dirtBlock), "natural/"), extend(texture(name(block), "natural/"), "_top"));
        getVariantBuilder(block.get()).forAllStatesExcept(state -> {
            boolean snowy = state.getValue(SnowyDirtBlock.SNOWY);
            return ConfiguredModel.allYRotations(snowy ? grassSnowed : grass, 0, false);
        }, AetherBlockStateProperties.DOUBLE_DROPS);
    }

    public void dirtPath(Supplier<? extends Block> block, Supplier<? extends Block> dirtBlock) {
        ModelFile path = models().withExistingParent(name(block), mcLoc("block/dirt_path"))
                .texture("particle", modLoc("block/natural/" + name(dirtBlock)))
                .texture("top", modLoc("block/construction/" + name(block) + "_top"))
                .texture("side", modLoc("block/construction/" + name(block) + "_side"))
                .texture("bottom", modLoc("block/natural/" + name(dirtBlock)));
        getVariantBuilder(block.get()).forAllStatesExcept(state -> ConfiguredModel.allYRotations(path, 0, false), AetherBlockStateProperties.DOUBLE_DROPS);
    }

    public void log(Supplier<? extends RotatedPillarBlock> block) {
        axisBlock(block.get(), texture(name(block), "natural/"), extend(texture(name(block), "natural/"), "_top"));
    }

    public void enchantedLog(Supplier<? extends RotatedPillarBlock> block, Supplier<? extends RotatedPillarBlock> baseBlock) {
        axisBlock(block.get(), texture(name(block), "natural/"), extend(texture(name(baseBlock), "natural/"), "_top"));
    }

    public void wood(Supplier<? extends RotatedPillarBlock> block, Supplier<? extends RotatedPillarBlock> baseBlock) {
        axisBlock(block.get(), texture(name(baseBlock), "natural/"), texture(name(baseBlock), "natural/"));
    }

    public void pane(Supplier<? extends IronBarsBlock> block, Supplier<? extends GlassBlock> glass, String location) {
        paneBlockInternal(block.get(), name(block), texture(name(glass), location), extend(texture(name(block), location), "_top"));
    }

    private void paneBlockInternal(IronBarsBlock block, String baseName, ResourceLocation pane, ResourceLocation edge) {
        ModelFile post = models().panePost(baseName + "_post", pane, edge).renderType(new ResourceLocation("translucent"));
        ModelFile side = models().paneSide(baseName + "_side", pane, edge).renderType(new ResourceLocation("translucent"));
        ModelFile sideAlt = models().paneSideAlt(baseName + "_side_alt", pane, edge).renderType(new ResourceLocation("translucent"));
        ModelFile noSide = models().paneNoSide(baseName + "_noside", pane).renderType(new ResourceLocation("translucent"));
        ModelFile noSideAlt = models().paneNoSideAlt(baseName + "_noside_alt", pane).renderType(new ResourceLocation("translucent"));
        paneBlock(block, post, side, sideAlt, noSide, noSideAlt);    }

    public void altar(Supplier<? extends Block> block) {
        ModelFile altar = cubeBottomTop(name(block), extend(texture(name(block), "utility/"), "_side"), extend(texture(name(block), "utility/"), "_bottom"), extend(texture(name(block), "utility/"), "_bottom"));
        getVariantBuilder(block.get()).partialState().addModels(new ConfiguredModel(altar));
    }

    public void freezer(Supplier<? extends Block> block) {
        ModelFile freezer = cubeBottomTop(name(block), extend(texture(name(block), "utility/"), "_side"), extend(texture("altar", "utility/"), "_bottom"), extend(texture(name(block), "utility/"), "_top"));
        getVariantBuilder(block.get()).partialState().addModels(new ConfiguredModel(freezer));
    }

    public void incubator(Supplier<? extends Block> block) {
        ModelFile incubator = cubeBottomTop(name(block), extend(texture(name(block), "utility/"), "_side"), extend(texture("altar", "utility/"), "_bottom"), extend(texture(name(block), "utility/"), "_top"));
        getVariantBuilder(block.get()).partialState().addModels(new ConfiguredModel(incubator));
    }

    public void torchBlock(Supplier<? extends Block> block, Supplier<? extends Block> wall) {
        ModelFile torch = models().torch(name(block), texture(name(block), "utility/")).renderType("cutout");
        ModelFile torchwall = models().torchWall(name(wall), texture(name(block), "utility/")).renderType("cutout");
        simpleBlock(block.get(), torch);
        getVariantBuilder(wall.get()).forAllStates(state ->
                ConfiguredModel.builder()
                        .modelFile(torchwall)
                        .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 90) % 360)
                        .build());
    }

    public void berryBush(Supplier<? extends Block> block, Supplier<? extends Block> stem) {
        getVariantBuilder(block.get()).partialState().addModels(new ConfiguredModel(bush(block, stem)));
    }

    public ModelFile bush(Supplier<? extends Block> block, Supplier<? extends Block> stem) {
        return this.models().withExistingParent(name(block), mcLoc("block/block"))
                .texture("particle", texture(name(block), "natural/")).texture("bush", texture(name(block), "natural/")).texture("stem", texture(name(stem), "natural/"))
                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F).shade(true).allFaces((direction, builder) -> builder.texture("#bush").end()).end()
                .element().from(0.8F, 0.0F, 8.0F).to(15.2F, 16.0F, 8.0F).rotation().origin(8.0F, 8.0F, 8.0F).axis(Direction.Axis.Y).angle(45.0F).rescale(true).end().shade(true).face(Direction.NORTH).texture("#stem").end().face(Direction.SOUTH).texture("#stem").end().end()
                .element().from(8.0F, 0.0F, 0.8F).to(8.0F, 16.0F, 15.2F).rotation().origin(8.0F, 8.0F, 8.0F).axis(Direction.Axis.Y).angle(45.0F).rescale(true).end().shade(true).face(Direction.WEST).texture("#stem").end().face(Direction.EAST).texture("#stem").end().end();
    }

    public BlockModelBuilder pottedStemModel(Supplier<? extends Block> block, Supplier<? extends Block> stem, String location) {
        return models().withExistingParent(name(block), mcLoc("block/block"))
                .texture("particle", mcLoc("block/flower_pot")).texture("stem", modLoc("block/" + location + name(stem))).texture("dirt", mcLoc("block/dirt")).texture("flowerpot", mcLoc("block/flower_pot"))
                .element().from(5.0F, 0.0F, 5.0F).to(6.0F, 6.0F, 11.0F)
                .face(Direction.NORTH).uvs(10.0F, 10.0F, 11.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.EAST).uvs(5.0F, 10.0F, 11.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.SOUTH).uvs(5.0F, 10.0F, 6.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.WEST).uvs(5.0F, 10.0F, 11.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.UP).uvs(5.0F, 5.0F, 6.0F, 11.0F).texture("#flowerpot").end()
                .face(Direction.DOWN).uvs(5.0F, 5.0F, 6.0F, 11.0F).texture("#flowerpot").cullface(Direction.DOWN).end().end()
                .element().from(10.0F, 0.0F, 5.0F).to(11.0F, 6.0F, 11.0F)
                .face(Direction.NORTH).uvs(5.0F, 10.0F, 6.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.EAST).uvs(5.0F, 10.0F, 11.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.SOUTH).uvs(10.0F, 10.0F, 11.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.WEST).uvs(5.0F, 10.0F, 11.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.UP).uvs(10.0F, 5.0F, 11.0F, 11.0F).texture("#flowerpot").end()
                .face(Direction.DOWN).uvs(10.0F, 5.0F, 11.0F, 11.0F).texture("#flowerpot").cullface(Direction.DOWN).end().end()
                .element().from(6.0F, 0.0F, 5.0F).to(10.0F, 6.0F, 6.0F)
                .face(Direction.NORTH).uvs(6.0F, 10.0F, 10.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.SOUTH).uvs(6.0F, 10.0F, 10.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.UP).uvs(6.0F, 5.0F, 10.0F, 6.0F).texture("#flowerpot").end()
                .face(Direction.DOWN).uvs(6.0F, 10.0F, 10.0F, 11.0F).texture("#flowerpot").cullface(Direction.DOWN).end().end()
                .element().from(6.0F, 0.0F, 10.0F).to(10.0F, 6.0F, 11.0F)
                .face(Direction.NORTH).uvs(6.0F, 10.0F, 10.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.SOUTH).uvs(6.0F, 10.0F, 10.0F, 16.0F).texture("#flowerpot").end()
                .face(Direction.UP).uvs(6.0F, 10.0F, 10.0F, 11.0F).texture("#flowerpot").end()
                .face(Direction.DOWN).uvs(6.0F, 5.0F, 10.0F, 6.0F).texture("#flowerpot").cullface(Direction.DOWN).end().end()
                .element().from(6.0F, 0.0F, 6.0F).to(10.0F, 4.0F, 10.0F)
                .face(Direction.UP).uvs(6.0F, 6.0F, 10.0F, 10.0F).texture("#dirt").end()
                .face(Direction.DOWN).uvs(6.0F, 12.0F, 10.0F, 16.0F).texture("#flowerpot").cullface(Direction.DOWN).end().end()
                .element().from(1.0F, 4.0F, 8.0F).to(15.0F, 16.0F, 8.0F).rotation().angle(45.0F).axis(Direction.Axis.Y).origin(8.0F, 8.0F, 8.0F).end()
                .face(Direction.NORTH).uvs(0.0F, 4.0F, 16.0F, 16.0F).texture("#stem").end()
                .face(Direction.SOUTH).uvs(0.0F, 4.0F, 16.0F, 16.0F).texture("#stem").end().end()
                .element().from(8.0F, 4.0F, 1.0F).to(8.0F, 16.0F, 15.0F).rotation().angle(45.0F).axis(Direction.Axis.Y).origin(8.0F, 8.0F, 8.0F).end()
                .face(Direction.EAST).uvs(0.0F, 4.0F, 16.0F, 16.0F).texture("#stem").end()
                .face(Direction.WEST).uvs(0.0F, 4.0F, 16.0F, 16.0F).texture("#stem").end().end();
    }

    public void pottedStem(Supplier<? extends Block> stem, String location) {
        ModelFile pot = pottedStemModel(stem, stem, location).renderType("cutout");
        getVariantBuilder(stem.get()).partialState().addModels(new ConfiguredModel(pot));
    }

    public void pottedBush(Supplier<? extends Block> bush, Supplier<? extends Block> stem, String location) {
        ModelFile pot = pottedStemModel(bush, stem, location)
                .texture("stem", modLoc("block/" + location + name(stem))).texture("bush", modLoc("block/" + location + name(bush)))
                .element().from(3.0F, 6.0F, 3.0F).to(13.0F, 16.0F, 13.0F)
                .face(Direction.NORTH).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
                .face(Direction.EAST).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
                .face(Direction.SOUTH).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
                .face(Direction.WEST).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
                .face(Direction.UP).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end()
                .face(Direction.DOWN).uvs(3.0F, 3.0F, 13.0F, 13.0F).texture("#bush").end().end()
                .renderType("cutout");
        getVariantBuilder(bush.get()).partialState().addModels(new ConfiguredModel(pot));
    }

    public void pottedPlant(Supplier<? extends Block> block, Supplier<? extends Block> flower, String location) {
        ModelFile pot = models().withExistingParent(name(block), mcLoc("block/flower_pot_cross")).texture("plant", modLoc("block/" + location + name(flower))).renderType("cutout");
        getVariantBuilder(block.get()).partialState().addModels(new ConfiguredModel(pot));
    }

    public void dungeonBlock(Supplier<? extends Block> block, Supplier<? extends Block> baseBlock) {
        ConfiguredModel dungeonBlock = new ConfiguredModel(models().cubeAll(name(baseBlock), texture(name(baseBlock), "dungeon/")));
        getVariantBuilder(block.get()).partialState().setModels(dungeonBlock);
    }
    public ModelFile cubeAllTranslucent(Supplier<? extends Block> block, String location) {
        return models().cubeAll(name(block), texture(name(block), location)).renderType(new ResourceLocation("translucent"));
    }


    public void chestMimic(Supplier<? extends Block> block, Supplier<? extends Block> dummyBlock) {
        ModelFile chest = models().cubeAll(name(block), mcLoc("block/" + name(dummyBlock)));
        chest(block, chest);
    }

    public void treasureChest(Supplier<? extends Block> block, Supplier<? extends Block> dummyBlock) {
        ModelFile chest = models().cubeAll(name(block), texture(name(dummyBlock), "dungeon/"));
        chest(block, chest);
    }


    public void chest(Supplier<? extends Block> block, ModelFile chest) {
        getVariantBuilder(block.get()).forAllStatesExcept(state -> ConfiguredModel.builder().modelFile(chest).build(),
                ChestBlock.TYPE, ChestBlock.WATERLOGGED);
    }

    public void pillar(Supplier<? extends RotatedPillarBlock> block, String loc) {
        axisBlock(block.get(), extend(texture(name(block), loc), "_side"), extend(texture(name(block), loc), "_top"));
    }

    public void holefirepillarTop(FacingPillarBlock block) {
        ResourceLocation side = this.texture("holefire_pillar_carved", "dungeon/");
        ResourceLocation end = this.texture(this.name(block), "dungeon/");
        ModelFile vertical = this.models().cubeColumn(this.name(block), side, end);
        ModelFile horizontal = this.models().cubeColumnHorizontal(this.name(block) + "_horizontal", side, end);
        ((VariantBlockStateBuilder)((VariantBlockStateBuilder)((VariantBlockStateBuilder)((VariantBlockStateBuilder)((VariantBlockStateBuilder)this.getVariantBuilder(block).partialState().with(FacingPillarBlock.FACING, Direction.DOWN).modelForState().modelFile(vertical).rotationX(180).addModel()).partialState().with(FacingPillarBlock.FACING, Direction.EAST).modelForState().modelFile(horizontal).rotationX(90).rotationY(90).addModel()).partialState().with(FacingPillarBlock.FACING, Direction.NORTH).modelForState().modelFile(horizontal).rotationX(90).addModel()).partialState().with(FacingPillarBlock.FACING, Direction.SOUTH).modelForState().modelFile(horizontal).rotationX(90).rotationY(180).addModel()).partialState().with(FacingPillarBlock.FACING, Direction.UP).modelForState().modelFile(vertical).addModel()).partialState().with(FacingPillarBlock.FACING, Direction.WEST).modelForState().modelFile(horizontal).rotationX(90).rotationY(270).addModel();
    }

    public void bookshelf(Supplier<? extends Block> block, Supplier<? extends Block> endBlock) {
        ModelFile bookshelf = models().cubeColumn(name(block), texture(name(block), "construction/"), texture(name(endBlock), "construction/"));
        getVariantBuilder(block.get()).partialState().addModels(new ConfiguredModel(bookshelf));
    }

    public ModelFile cubeAll(Supplier<? extends Block> block, String location) {
        return models().cubeAll(name(block), texture(name(block), location));
    }

    public ModelFile cubeBottomTop(String block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        return models().cubeBottomTop(block, side, bottom, top);
    }

    public ResourceLocation extend(ResourceLocation rl, String suffix) {
        return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
    }

    public void doorBlock(Supplier<? extends DoorBlock> block, ResourceLocation bottom, ResourceLocation top) {
        doorBlockInternal(block.get(), name(block), bottom, top);
    }

    private void doorBlockInternal(DoorBlock block, String baseName, ResourceLocation bottom, ResourceLocation top) {
        ModelFile bottomLeft = models().doorBottomLeft(baseName + "_bottom_left", bottom, top).renderType("cutout");
        ModelFile bottomLeftOpen = models().doorBottomLeftOpen(baseName + "_bottom_left_open", bottom, top).renderType("cutout");
        ModelFile bottomRight = models().doorBottomRight(baseName + "_bottom_right", bottom, top).renderType("cutout");
        ModelFile bottomRightOpen = models().doorBottomRightOpen(baseName + "_bottom_right_open", bottom, top).renderType("cutout");
        ModelFile topLeft = models().doorTopLeft(baseName + "_top_left", bottom, top).renderType("cutout");
        ModelFile topLeftOpen = models().doorTopLeftOpen(baseName + "_top_left_open", bottom, top).renderType("cutout");
        ModelFile topRight = models().doorTopRight(baseName + "_top_right", bottom, top).renderType("cutout");
        ModelFile topRightOpen = models().doorTopRightOpen(baseName + "_top_right_open", bottom, top).renderType("cutout");
        doorBlock(block, bottomLeft, bottomLeftOpen, bottomRight, bottomRightOpen, topLeft, topLeftOpen, topRight, topRightOpen);
    }


    public void trapdoorBlock(Supplier<? extends TrapDoorBlock> block, ResourceLocation texture, boolean orientable) {
        trapdoorBlockInternal(block.get(), name(block), texture, orientable);
    }

    private void trapdoorBlockInternal(TrapDoorBlock block, String baseName, ResourceLocation texture, boolean orientable) {
        ModelFile bottom = orientable ? models().trapdoorOrientableBottom(baseName + "_bottom", texture) : models().trapdoorBottom(baseName + "_bottom", texture).renderType("cutout");
        ModelFile top = orientable ? models().trapdoorOrientableTop(baseName + "_top", texture) : models().trapdoorTop(baseName + "_top", texture).renderType("cutout");
        ModelFile open = orientable ? models().trapdoorOrientableOpen(baseName + "_open", texture) : models().trapdoorOpen(baseName + "_open", texture).renderType("cutout");
        trapdoorBlock(block, bottom, top, open, orientable);
    }


    private ModelBuilder<?> door(String name, String model, ResourceLocation bottom, ResourceLocation top) {
        return models().withExistingParent(name, "block/" + model)
                .texture("bottom", bottom)
                .texture("top", top);
    }

    public void doorBlock(DoorBlock block, ModelFile bottomLeft, ModelFile bottomLeftOpen, ModelFile bottomRight, ModelFile bottomRightOpen, ModelFile topLeft, ModelFile topLeftOpen, ModelFile topRight, ModelFile topRightOpen) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            int yRot = ((int) state.getValue(DoorBlock.FACING).toYRot()) + 90;
            boolean right = state.getValue(DoorBlock.HINGE) == DoorHingeSide.RIGHT;
            boolean open = state.getValue(DoorBlock.OPEN);
            if (open) {
                yRot += 90;
            }
            if (right && open) {
                yRot += 180;
            }
            yRot %= 360;
            return ConfiguredModel.builder().modelFile(state.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER ? (right ? (open ? bottomRightOpen : bottomRight) : (open ? bottomLeftOpen : bottomLeft)) : (right ? (open ? topRightOpen : topRight) : (open ? topLeftOpen : topLeft)))
                    .rotationY(yRot)
                    .build();
        }, DoorBlock.POWERED);
    }

    public void crossBlockExclude(Supplier<? extends Block> block, String location, Property property) {
        crossBlockExclude(block, models().cross(name(block), texture(name(block), location)).renderType("cutout"), property);
    }
    private void crossBlockExclude(Supplier<? extends Block> block, ModelFile model, Property property) {
        getVariantBuilder(block.get()).forAllStatesExcept(state -> ConfiguredModel.builder().modelFile(model).build(), property);
    }
    public void ambientCrossBlockExclude(Supplier<? extends Block> block, String location, Property property) {
        ambientCrossBlockExclude(block, models().singleTexture(name(block),
                        Redux.locate(models().BLOCK_FOLDER + "/ambient_cross"),
                        "cross", texture(name(block), location))
                .renderType("cutout"), property);
    }
    private void ambientCrossBlockExclude(Supplier<? extends Block> block, ModelFile model, Property property) {
        getVariantBuilder(block.get()).forAllStatesExcept(state -> ConfiguredModel.builder().modelFile(model).build(), property);
    }
    public void potAlt(Block block, Block flower, String location) {
        ModelFile pot = this.models().withExistingParent(this.name(block), mcLoc("block/flower_pot_cross"))
                .texture("plant", this.modLoc("block/" + location + "potted_" + this.name(flower))).renderType("cutout");
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(pot));
    }
    public void tintedPotDualGloverlay(Block block, Block flower, String location) {
        ModelFile pot = this.models().withExistingParent(this.name(block), Redux.locate("block/pot/flower_pot_tinted_dual_gloverlay"))
                .texture("plant", this.modLoc("block/" + location + this.name(flower)))
                .texture("glow", this.modLoc("block/" + location + this.name(flower) + "_glow"))
                .texture("overlay", this.modLoc("block/" + location + this.name(flower) + "_overlay")).renderType("cutout");
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(pot));
    }
    public void tintedPotGlowOverlay(Block block, Block flower, String location) {
        ModelFile pot = this.models().withExistingParent(this.name(block), Redux.locate("block/pot/flower_pot_tinted_glowing_overlay"))
                .texture("plant", this.modLoc("block/" + location + this.name(flower)))
                .texture("overlay", this.modLoc("block/" + location + this.name(flower) + "_glow")).renderType("cutout");
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(pot));
    }
    public void tintedPotGlowOverlayAlt(Block block, Block flower, String location) {
        ModelFile pot = this.models().withExistingParent(this.name(block), Redux.locate("block/pot/flower_pot_tinted_glowing_overlay"))
                .texture("plant", this.modLoc("block/" + location + "potted_" + this.name(flower)))
                .texture("overlay", this.modLoc("block/" + location + "potted_" + this.name(flower) + "_glow")).renderType("cutout");
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(pot));
    }
    public void tintedPotOverlay(Block block, Block flower, String location) {
        ModelFile pot = this.models().withExistingParent(this.name(block), Redux.locate("block/pot/flower_pot_tinted_overlay"))
                .texture("plant", this.modLoc("block/" + location + this.name(flower)))
                .texture("overlay", this.modLoc("block/" + location + this.name(flower) + "_overlay")).renderType("cutout");
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(pot));
    }
    public void tintedPot(Block block, Block flower, String location) {
        ModelFile pot = this.models().withExistingParent(this.name(block), Redux.locate("block/pot/flower_pot_tinted"))
                .texture("plant", this.modLoc("block/" + location + this.name(flower))).renderType("cutout");
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(pot));
    }
    public void tintedPotOverlayAlt(Block block, Block flower, String location) {
        ModelFile pot = this.models().withExistingParent(this.name(block), Redux.locate("block/pot/flower_pot_tinted_overlay"))
                .texture("plant", this.modLoc("block/" + location + "potted_" + this.name(flower)))
                .texture("overlay", this.modLoc("block/" + location + "potted_" + this.name(flower) + "_overlay")).renderType("cutout");
        this.getVariantBuilder(block).partialState().addModels(new ConfiguredModel(pot));
    }

    public void other(Supplier<? extends Block> block, RegistryObject<? extends Block> other, String location) {
        simpleBlock(block.get(), models().cubeAll(name(block), texture(other, location)));
    }
    public void other(Supplier<? extends Block> block, String other, String location) {
        simpleBlock(block.get(), models().cubeAll(name(block), texture(other, location)));
    }
    public void existingModel(Supplier<? extends Block> block, String other) {
        simpleBlock(block.get(), models().getExistingFile(texture(other)));
    }


    public void axisBlockGlow(RotatedPillarBlock block, ResourceLocation side, ResourceLocation end) {
        axisBlock(block,
                models().withExistingParent(name(block), Redux.locate(BLOCK_FOLDER) + "/cube_column_glow")
                        .texture("side", side)
                        .texture("end", end)
                        .texture("side_glow", this.extend(side, "_glow"))
                        .texture("end_glow", this.extend(end, "_glow")).renderType("cutout"),
                models().withExistingParent(name(block), Redux.locate(BLOCK_FOLDER) + "/cube_column_horizontal_glow")
                        .texture("side", side)
                        .texture("end", end)
                        .texture("side_glow", this.extend(side, "_glow"))
                        .texture("end_glow", this.extend(end, "_glow")).renderType("cutout"));
    }


        public void roots(Block block, ResourceLocation side, ResourceLocation top) {
            getVariantBuilder(block).forAllStatesExcept(state ->
                    ConfiguredModel.builder().modelFile(models().withExistingParent(name(block), Redux.locate(BLOCK_FOLDER) + "/template_roots")
                        .texture("side", side)
                        .texture("top", top).renderType("cutout")).build(), AetherBlockStateProperties.DOUBLE_DROPS);
    }
    public void roots(Block block, String loc) {
        roots(block, this.extend(this.texture(this.name(block), loc), "_side"), this.extend(this.texture(this.name(block), loc), "_top"));
    }






    public BlockModelBuilder makeWallPostModel(int width, int height, String name) {
        return this.models().withExistingParent(name, this.mcLoc("block/block")).element().from((float)(8 - width), 0.0F, (float)(8 - width)).to((float)(8 + width), (float)height, (float)(8 + width)).face(Direction.DOWN).texture("#top").cullface(Direction.DOWN).end().face(Direction.UP).texture("#top").cullface(Direction.UP).end().face(Direction.NORTH).texture("#side").end().face(Direction.SOUTH).texture("#side").end().face(Direction.WEST).texture("#side").end().face(Direction.EAST).texture("#side").end().end();
    }

    public BlockModelBuilder makeWallSideModel(int length, int height, String name, ModelBuilder.FaceRotation faceRotation, int u1, int u2) {
        return this.models().withExistingParent(name, this.mcLoc("block/block")).element().from(5.0F, 0.0F, 0.0F).to(11.0F, (float)height, (float)length).face(Direction.DOWN).texture("#top").rotation(faceRotation).uvs((float)u1, 5.0F, (float)u2, 11.0F).cullface(Direction.DOWN).end().face(Direction.UP).texture("#top").rotation(faceRotation).uvs((float)u1, 5.0F, (float)u2, 11.0F).end().face(Direction.NORTH).texture("#side").cullface(Direction.NORTH).end().face(Direction.SOUTH).texture("#side").end().face(Direction.WEST).texture("#side").end().face(Direction.EAST).texture("#side").end().end();
    }

    public void logWallBlock(WallBlock block, Block baseBlock, String location, String modid, boolean postUsesTop, ModelFile postBig, ModelFile postShort, ModelFile postTall, ModelFile side, ModelFile sideAlt, ModelFile sideTall, ModelFile sideTallAlt, ModelFile sideShort, ModelFile sideAltShort, ModelFile sideTallShort, ModelFile sideTallAltShort) {
        this.logWallBlockInternal(block, this.name(block), new ResourceLocation(modid, "block/" + location + this.name(baseBlock)), postUsesTop, postBig, postShort, postTall, side, sideAlt, sideTall, sideTallAlt, sideShort, sideAltShort, sideTallShort, sideTallAltShort);
    }

    private void logWallBlockInternal(WallBlock block, String baseName, ResourceLocation texture, boolean postUsesTop, ModelFile postBig, ModelFile postShort, ModelFile postTall, ModelFile side, ModelFile sideAlt, ModelFile sideTall, ModelFile sideTallAlt, ModelFile sideShort, ModelFile sideAltShort, ModelFile sideTallShort, ModelFile sideTallAltShort) {
        this.logWallBlock(this.getMultipartBuilder(block), this.models().getBuilder(baseName + "_post_short").parent(postShort).texture("particle", texture).texture("top", texture).texture("side", texture), this.models().getBuilder(baseName + "_post_tall").parent(postTall).texture("particle", texture).texture("top", texture).texture("side", texture), this.models().getBuilder(baseName + "_side").parent(side).texture("particle", texture).texture("top", texture).texture("side", texture), this.models().getBuilder(baseName + "_side_alt").parent(sideAlt).texture("particle", texture).texture("top", texture).texture("side", texture), this.models().getBuilder(baseName + "_side_tall").parent(sideTall).texture("particle", texture).texture("top", texture).texture("side", texture), this.models().getBuilder(baseName + "_side_tall_alt").parent(sideTallAlt).texture("particle", texture).texture("top", texture).texture("side", texture));
        this.logWallBlockWithPost(this.getMultipartBuilder(block), this.models().getBuilder(baseName + "_post").parent(postBig).texture("particle", texture).texture("top", postUsesTop ? "" + texture + "_top" : texture.toString()).texture("side", texture), this.models().getBuilder(baseName + "_side_short").parent(sideShort).texture("particle", texture).texture("top", texture).texture("side", texture), this.models().getBuilder(baseName + "_side_alt_short").parent(sideAltShort).texture("particle", texture).texture("top", texture).texture("side", texture), this.models().getBuilder(baseName + "_side_tall_short").parent(sideTallShort).texture("particle", texture).texture("top", texture).texture("side", texture), this.models().getBuilder(baseName + "_side_tall_alt_short").parent(sideTallAltShort).texture("particle", texture).texture("top", texture).texture("side", texture));
    }

    public void logWallBlock(MultiPartBlockStateBuilder builder, ModelFile postShort, ModelFile postTall, ModelFile side, ModelFile sideAlt, ModelFile sideTall, ModelFile sideTallAlt) {
        builder.part().modelFile(postShort).addModel().nestedGroup().condition(WallBlock.UP, false).condition(WallBlock.EAST_WALL, WallSide.LOW).condition(WallBlock.WEST_WALL, WallSide.LOW).end().end().part().modelFile(postTall).addModel().nestedGroup().condition(WallBlock.UP, false).condition(WallBlock.EAST_WALL, WallSide.TALL).condition(WallBlock.WEST_WALL, WallSide.TALL).end().end().part().modelFile(postShort).rotationY(90).addModel().nestedGroup().condition(WallBlock.UP, false).condition(WallBlock.EAST_WALL, WallSide.NONE).condition(WallBlock.NORTH_WALL, WallSide.LOW).condition(WallBlock.WEST_WALL, WallSide.NONE).condition(WallBlock.SOUTH_WALL, WallSide.LOW).end().end().part().modelFile(postTall).rotationY(90).addModel().nestedGroup().condition(WallBlock.UP, false).condition(WallBlock.EAST_WALL, WallSide.NONE).condition(WallBlock.NORTH_WALL, WallSide.TALL).condition(WallBlock.WEST_WALL, WallSide.NONE).condition(WallBlock.SOUTH_WALL, WallSide.TALL).end().end();
        WALL_PROPS.entrySet().stream().filter((e) -> {
            return e.getKey().getAxis().isHorizontal();
        }).forEach((e) -> {
            this.logWallSidePart(builder, side, sideAlt, e, WallSide.LOW, false);
            this.logWallSidePart(builder, sideTall, sideTallAlt, e, WallSide.TALL, false);
        });
    }

    public void logWallBlockWithPost(MultiPartBlockStateBuilder builder, ModelFile postBig, ModelFile side, ModelFile sideAlt, ModelFile sideTall, ModelFile sideTallAlt) {
        builder.part().modelFile(postBig).addModel().condition(WallBlock.UP, true).end();
        WALL_PROPS.entrySet().stream().filter((e) -> {
            return e.getKey().getAxis().isHorizontal();
        }).forEach((e) -> {
            this.logWallSidePart(builder, side, sideAlt, e, WallSide.LOW, true);
            this.logWallSidePart(builder, sideTall, sideTallAlt, e, WallSide.TALL, true);
        });
    }

    private void logWallSidePart(MultiPartBlockStateBuilder builder, ModelFile model, ModelFile modelAlt, Map.Entry<Direction, Property<WallSide>> entry, WallSide height, boolean hasPost) {
        int rotation = ((int) entry.getKey().toYRot() + 180) % 360;
        builder.part().modelFile(rotation < 180 ? model : modelAlt).rotationY(rotation).addModel().condition(entry.getValue(), height).condition(WallBlock.UP, hasPost);
    }



}
