package net.zepalesque.redux.data;

import com.aetherteam.aether.data.providers.AetherItemModelProvider;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.loaders.ItemLayerModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.blockhandler.WoodHandler;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.item.ReduxItems;

import java.util.function.Function;
import java.util.function.Supplier;

public class ReduxItemModelData extends AetherItemModelProvider {
    public ReduxItemModelData(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Redux.MODID, existingFileHelper);
    }
    @Override
    protected void registerModels() {

        itemBlockFlat(ReduxBlocks.AETHER_GRASS, "natural/");
        itemBlockFlat(ReduxBlocks.ENCHANTED_AETHER_GRASS, "natural/");
        itemBlockFlat(ReduxBlocks.FIRECAP, "natural/");
        itemBlock(ReduxBlocks.HOLYSILT);
        itemBlock(ReduxBlocks.VITRIUM);
        itemBlock(ReduxBlocks.VITRIUM_SLAB);
        itemBlock(ReduxBlocks.VITRIUM_STAIRS);
        itemWallBlock(ReduxBlocks.VITRIUM_WALL, ReduxBlocks.VITRIUM, "natural/");
        itemBlock(ReduxBlocks.VITRIUM_BRICKS);
        itemBlock(ReduxBlocks.VITRIUM_BRICK_SLAB);
        itemBlock(ReduxBlocks.VITRIUM_BRICK_STAIRS);
        itemWallBlock(ReduxBlocks.VITRIUM_BRICK_WALL, ReduxBlocks.VITRIUM_BRICKS, "construction/");

        itemBlockFlat(ReduxBlocks.IRIDIA, "natural/");
        itemBlockFlatGlow(ReduxBlocks.TALL_CLOUDCAP, "natural/", "_top");
        itemBlock(ReduxBlocks.GILDED_HOLYSTONE);
        itemBlock(ReduxBlocks.GILDED_HOLYSTONE_SLAB);
        itemBlock(ReduxBlocks.GILDED_HOLYSTONE_STAIRS);
        itemWallBlock(ReduxBlocks.GILDED_HOLYSTONE_WALL, ReduxBlocks.GILDED_HOLYSTONE, "natural/");
        itemBlock(ReduxBlocks.BLIGHTMOSS_HOLYSTONE);
        itemBlock(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_SLAB);
        itemBlock(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_STAIRS);
        itemWallBlock(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_WALL, ReduxBlocks.BLIGHTMOSS_HOLYSTONE, "natural/");
        item(ReduxItems.BLUE_SWET_JELLY, "food/");
        item(ReduxItems.GOLDEN_SWET_JELLY, "food/");
        item(ReduxItems.GOLDEN_SWET_BALL, "materials/");
        item(ReduxItems.VANILLA_SWET_BALL, "materials/");
        item(ReduxItems.VANILLA_SWET_JELLY, "food/");
        item(ReduxItems.VANILLA_GUMMY_SWET, "food/");
        vampireAmulet(ReduxItems.VAMPIRE_AMULET, "accessories/");
        item(ReduxItems.ENCHANTED_RING, "accessories/");
        item(ReduxItems.SPECTRAL_DART, "weapons/");
        item(ReduxItems.SPECTRAL_DART_SHOOTER, "weapons/");
        item(ReduxItems.OATS, "materials/");
        itemBlock(ReduxBlocks.AEVYRN_BUSH);
        item(ReduxItems.BLUEBERRY_PIE, "food/");
        item(ReduxItems.ENCHANTED_BLUEBERRY_PIE, "food/");
        item(ReduxBlocks.GOLDEN_CLOVER.get().asItem(), "misc/");
        itemBlockFlat(ReduxBlocks.AURUM, "natural/");
        itemBlock(ReduxBlocks.CARVED_STONE_BRICKS);
        itemBlock(ReduxBlocks.CARVED_STONE_BRICK_SLAB);
        itemBlock(ReduxBlocks.CARVED_STONE_BRICK_STAIRS);
        itemWallBlock(ReduxBlocks.CARVED_STONE_BRICK_WALL, ReduxBlocks.CARVED_STONE_BRICKS, "construction/");
        itemBlock(ReduxBlocks.CARVED_STONE_PILLAR);
        itemBlockFlat(ReduxBlocks.GILDED_WHITE_FLOWER, "natural/");
        itemBlock(ReduxBlocks.FROSTED_HOLYSTONE);
        itemBlock(ReduxBlocks.FROSTED_HOLYSTONE_SLAB);
        itemBlock(ReduxBlocks.FROSTED_HOLYSTONE_STAIRS);
        itemWallBlock(ReduxBlocks.FROSTED_HOLYSTONE_WALL, ReduxBlocks.FROSTED_HOLYSTONE, "natural/");
        eggItem(ReduxItems.VANILLA_SWET_SPAWN_EGG);
        item(ReduxItems.VALKYRIE_RING, "accessories/");
        item(ReduxItems.AIRBOUND_CAPE, "accessories/");
        item(ReduxItems.PHOENIX_EMBLEM, "accessories/");
        item(ReduxItems.MUSIC_DISC_LABYRINTHS_VENGEANCE, "misc/");
        item(ReduxItems.MOUSE_EAR_SOUP, "food/");
        itemBlock(ReduxBlocks.COARSE_AETHER_DIRT);

        itemBlock(ReduxBlocks.BLIGHTWILLOW_LEAVES);

        this.itemBlockWithParent(ReduxBlocks.GOLDEN_LEAF_PILE.get(), (block) -> modLoc(BLOCK_FOLDER + "/" + this.blockName(block) + "1"));
        this.itemBlockWithParent(ReduxBlocks.GILDED_LEAF_PILE.get(), (block) -> modLoc(BLOCK_FOLDER + "/" + this.blockName(block) + "1"));

        itemBlockFlatGlow(ReduxBlocks.CLOUDCAP_MUSHLING, "natural/");

        this.itemBlockFlat(ReduxBlocks.BLIGHTWILLOW_SAPLING, "natural/");
        this.itemBlockFlat(ReduxBlocks.FLOWERING_FIELDSPROUT_SAPLING.get(), "natural/");

        itemBlock(ReduxBlocks.GLACIA_LEAVES);
        this.itemBlockFlat(ReduxBlocks.GLACIA_SAPLING.get(), "natural/");

        itemBlock(ReduxBlocks.BLIGHTED_AETHER_GRASS_BLOCK);
        itemBlock(ReduxBlocks.AEVELIUM);
        itemBlock(ReduxBlocks.CLOUD_CAP_BLOCK);
        itemBlock(ReduxBlocks.CLOUDCAP_SPORES);
        itemBlock(ReduxBlocks.SPRINGSHROOM);
        itemBlockFlat(ReduxBlocks.BLIGHTED_AETHER_GRASS, "natural/");
        itemBlockFlatGlow(ReduxBlocks.LUXWEED, "natural/");
        itemBlockFlat(ReduxBlocks.SPIROLYCTIL, "natural/");
        itemBlockFlat(ReduxBlocks.BLIGHTSHADE, "natural/");
        itemBlockFlatGlow(ReduxBlocks.BLIGHTED_FUNGI, "natural/");

        itemFullGlow(ReduxItems.LIGHTROOT_CLUMP, "food/");

        itemBlock(ReduxBlocks.LIGHTROOT_AETHER_DIRT.get());


        blockCustomTexture(ReduxBlocks.FLOWERING_FIELDSPROUT_LEAVES, "natural/", "flowering_fieldsprout_leaves");
        itemCustomTexture(ReduxBlocks.FIELDSPROUT_PETALS, "misc/", "fieldsprout_petals");
        item(ReduxItems.VERIDIUM_NUGGET.get(), "materials/");

        itemBlockFlatItemTexture(ReduxBlocks.VERIDIUM_LANTERN, "misc/");
        itemBlockFlatItemTexture(ReduxBlocks.VERIDIUM_CHAIN, "misc/");

        this.itemBlock(ReduxBlocks.CHROMATIC_BERRY_LEAVES.get());
        this.itemBlock(ReduxBlocks.CHROMATIC_LEAVES.get());
        this.itemBlockFlat(ReduxBlocks.CHROMATIC_SHRUB.get(), "natural/");
        this.itemFullGlow(ReduxItems.LUXBUDS, "materials/");
        this.itemFullGlow(ReduxItems.PURIFIED_LUXBUDS, "materials/");
        this.itemFullGlow(ReduxItems.BLIGHTED_SPORES, "materials/");

        this.itemBlockFlat(ReduxBlocks.AEVELIUM_GROWTH.get(), "natural/");
        this.item(ReduxBlocks.AEVELIUM_SPROUTS.get().asItem(), "misc/");

        this.itemBlock(ReduxBlocks.SPRINGSHROOM_SPORES.get());
        this.itemBlock(ReduxBlocks.SPRINGSHROOM_CAP_BLOCK.get());

        this.itemBlock(ReduxBlocks.BLIGHTMOSS_BLOCK.get());
        this.itemBlock(ReduxBlocks.BLIGHTMOSS_CARPET.get());
        this.itemBlock(ReduxBlocks.GILDED_OAK_LEAVES.get());
        this.itemBlock(ReduxBlocks.FROSTED_AETHER_GRASS_BLOCK.get());
        this.itemBlockFlat(ReduxBlocks.FROSTED_AETHER_GRASS.get(), "natural/");
        this.itemBlockFlat(ReduxBlocks.GILDED_OAK_SAPLING.get(), "natural/");

        this.itemBlockFlat(ReduxBlocks.BLIGHTED_SKYROOT_SAPLING.get(), "natural/");

        this.itemBlock(ReduxBlocks.BLIGHTED_SKYROOT_LEAVES.get());

        this.itemBlock(ReduxBlocks.BLIGHTWILLOW_ROOTS.get());

        this.itemBlockFlatGlow(ReduxBlocks.LUMINA, "natural/");
        this.itemBlockFlat(ReduxBlocks.DAGGERBLOOM, "natural/");
        this.itemBlockFlat(ReduxBlocks.FROSTED_PURPLE_FLOWER, "natural/");

        itemBlockFlat(ReduxBlocks.WYNDSPROUTS, "natural/");
        itemBlockFlat(ReduxBlocks.SKYSPROUTS, "natural/");
        itemBlockFlat(ReduxBlocks.FROSTED_FERN, "natural/");
        this.item(ReduxItems.BUNDLE_OF_AETHER_GRASS, "materials/");

        this.itemBlockFlatOtherBlockTexture(ReduxBlocks.GOLDEN_VINES, ReduxBlocks.GOLDEN_VINES_PLANT, "natural/");
        this.itemBlockFlatOtherBlockTexture(ReduxBlocks.GILDED_VINES, ReduxBlocks.GILDED_VINES_PLANT, "natural/");

        this.handheldItem(ReduxItems.VERIDIUM_SWORD.get(), "weapons/");
        this.handheldItem(ReduxItems.VERIDIUM_PICKAXE.get(), "tools/");
        this.handheldItem(ReduxItems.VERIDIUM_AXE.get(), "tools/");
        this.handheldItem(ReduxItems.VERIDIUM_SHOVEL.get(), "tools/");
        this.handheldItem(ReduxItems.VERIDIUM_HOE.get(), "tools/");
        this.handheldItem(ReduxItems.INFUSED_VERIDIUM_SWORD.get(), "weapons/");
        this.handheldItem(ReduxItems.INFUSED_VERIDIUM_PICKAXE.get(), "tools/");
        this.handheldItem(ReduxItems.INFUSED_VERIDIUM_AXE.get(), "tools/");
        this.handheldItem(ReduxItems.INFUSED_VERIDIUM_SHOVEL.get(), "tools/");
        this.handheldItem(ReduxItems.INFUSED_VERIDIUM_HOE.get(), "tools/");

        this.item(ReduxItems.VERIDIUM_INGOT, "materials/");
        this.item(ReduxItems.RAW_VERIDIUM, "materials/");

        this.itemBlock(ReduxBlocks.VERIDIUM_BLOCK.get());
        this.itemBlock(ReduxBlocks.VERIDIUM_ORE.get());
        this.itemBlock(ReduxBlocks.RAW_VERIDIUM_BLOCK.get());

        this.itemGlow(ReduxItems.COCKATRICE_FEATHER, "accessories/");
        this.item(ReduxItems.OAT_MUFFIN, "food/");

        this.item(ReduxItems.QUICKROOT, "materials/");

        this.item(ReduxItems.CHROMABERRY, "food/");

        for (WoodHandler woodHandler : Redux.Handlers.Wood.WOOD_HANDLERS)        {
            woodHandler.generateItemModels(this);
        }

    }
    public String blockName(Supplier<? extends Block> block) {
        return ForgeRegistries.BLOCKS.getKey(block.get()).getPath();
    }

    protected ResourceLocation texture(String name) {
        return modLoc("block/" + name);
    }

    protected ResourceLocation texture(String name, String location) {
        return modLoc("block/" + location + name);
    }

    public ItemModelBuilder item(Supplier<? extends Item> item, String location) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item.get());
        return withExistingParent(id.getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + location + id.getPath()));
    }
    public ItemModelBuilder vampireAmulet(Supplier<? extends Item> item, String location) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item.get());
        String active = id.getPath() + "_active";
        ItemModelBuilder builder = withExistingParent(active, mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + location + id.getPath() + "_active"));
        return withExistingParent(id.getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + location + id.getPath() + "_inactive")).override().predicate(Redux.locate("active"), 1.0F).model(this.getExistingFile(this.modLoc("item/" + active))).end();
    }

    public ItemModelBuilder itemGlow(Supplier<? extends Item> item, String location) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item.get());
        return withExistingParent(id.getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + location + id.getPath())).texture("layer1", modLoc("item/" + location + id.getPath() + "_glow")).customLoader((itemModelBuilder,existingFileHelper) ->
                        ItemLayerModelBuilder.begin(itemModelBuilder, existingFileHelper).emissive(15, 15, 1)).end();
    }

    public ItemModelBuilder itemFullGlow(Supplier<? extends Item> item, String location) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item.get());
        return withExistingParent(id.getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + location + id.getPath())).customLoader((itemModelBuilder,existingFileHelper) ->
                        ItemLayerModelBuilder.begin(itemModelBuilder, existingFileHelper).emissive(15, 15, 0)).end();
    }

    public ItemModelBuilder blockCustomTexture(Supplier<? extends Block> block, String loc, String name) {
        return withExistingParent(itemName(block.get().asItem()), "block/cube_all")
                .texture("all", modLoc("block/" + loc + name));
    }
    public ItemModelBuilder itemCustomTexture(Supplier<? extends ItemLike> item, String location, String texture) {
        return withExistingParent(itemName(item.get().asItem()), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + location + texture));
    }

    public ItemModelBuilder itemBlockFlatFullGlow(Supplier<? extends Block> block, String location, String suffix) {
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer0", texture(blockName(block) + suffix, location)).customLoader((itemModelBuilder,existingFileHelper) ->
                        ItemLayerModelBuilder.begin(itemModelBuilder, existingFileHelper).emissive(15, 15, 1)).end();
    }
    public ItemModelBuilder itemBlockFlatFullGlow(Supplier<? extends Block> block, String location) {
        return itemBlockFlatFullGlow(block, location, "");
    }

    public ItemModelBuilder handheldItem(Supplier<? extends Item> item, String location) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item.get());
        return withExistingParent(id.getPath(), mcLoc("item/handheld"))
                .texture("layer0", modLoc("item/" + location + id.getPath()));
    }



    public void itemBlockWithParent(Block block, Function<Block, ResourceLocation> existingParent) {
        this.withExistingParent(this.blockName(block), existingParent.apply(block));
    }

    public ItemModelBuilder handheldItemGlow(Supplier<? extends Item> item, String location) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item.get());
        return withExistingParent(id.getPath(), mcLoc("item/handheld"))
                .texture("layer0", modLoc("item/" + location + id.getPath())).texture("layer1", modLoc("item/" + location + id.getPath() + "_glow")).customLoader((itemModelBuilder,existingFileHelper) ->
                        ItemLayerModelBuilder.begin(itemModelBuilder, existingFileHelper).emissive(15, 15, 1)).end();
    }
    public ItemModelBuilder handheldItemFullGlow(Supplier<? extends Item> item, String location) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item.get());
        return withExistingParent(id.getPath(), mcLoc("item/handheld"))
                .texture("layer0", modLoc("item/" + location + id.getPath())).customLoader((itemModelBuilder,existingFileHelper) ->
                        ItemLayerModelBuilder.begin(itemModelBuilder, existingFileHelper).emissive(15, 15, 0)).end();
    }

    public ItemModelBuilder itemBlock(Supplier<? extends Block> block) {
        return withExistingParent(blockName(block), texture(blockName(block)));
    }

    public ItemModelBuilder itemBlock(Supplier<? extends Block> block, String suffix) {
        return withExistingParent(blockName(block), texture(blockName(block) + suffix));
    }

    public ItemModelBuilder lookalikeBlock(Supplier<? extends Block> block, ResourceLocation lookalike) {
        return withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), lookalike);
    }

    public ItemModelBuilder itemBlockFlat(Supplier<? extends Block> block, String location) {
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer0", texture(blockName(block), location));
    }
    public ItemModelBuilder itemBlockFlatWithPottedTexture(Supplier<? extends Block> block, String location) {
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer0", texture("potted_" + blockName(block), location));
    }
    public ItemModelBuilder itemBlockFlatGlow(Supplier<? extends Block> block, String location) {
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer0", texture(blockName(block), location))
                .texture("layer1", texture(blockName(block) + "_glow", location)).customLoader((itemModelBuilder,existingFileHelper) ->
                ItemLayerModelBuilder.begin(itemModelBuilder, existingFileHelper).emissive(15, 15, 1)).end();
    }
    public ItemModelBuilder itemBlockFlatGlow(Supplier<? extends Block> block, String location, String suffix) {
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer0", texture(blockName(block), location) + suffix)
                .texture("layer1", texture(blockName(block) + suffix + "_glow", location)).customLoader((itemModelBuilder,existingFileHelper) ->
                ItemLayerModelBuilder.begin(itemModelBuilder, existingFileHelper).emissive(15, 15, 1)).end();
    }
    public ItemModelBuilder itemFence(Supplier<? extends Block> block, Supplier<? extends Block> baseBlock, String location) {
        return withExistingParent(blockName(block), mcLoc("block/fence_inventory"))
                .texture("texture", texture(blockName(baseBlock), location));
    }

    public ItemModelBuilder itemWallBlock(Supplier<? extends Block> block, Supplier<? extends Block> baseBlock, String location) {
        return wallInventory(blockName(block), texture(blockName(baseBlock), location));
    }

    public ItemModelBuilder itemButton(Supplier<? extends Block> block, Supplier<? extends Block> baseBlock, String location) {
        return withExistingParent(blockName(block), mcLoc("block/button_inventory"))
                .texture("texture", texture(blockName(baseBlock), location));
    }

    public ItemModelBuilder itemBlockFlatItemTexture(Supplier<? extends Block> block, String location) {
        ResourceLocation id = ForgeRegistries.BLOCKS.getKey(block.get());
        return withExistingParent(id.getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + location + id.getPath()));
    }

    public ItemModelBuilder itemBlockFlatOtherBlockTexture(Supplier<? extends Block> block, Supplier<? extends Block> blockForTexture, String location) {
        ResourceLocation id = ForgeRegistries.BLOCKS.getKey(block.get());
        ResourceLocation tex = ForgeRegistries.BLOCKS.getKey(blockForTexture.get());
        return withExistingParent(id.getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/" + location + tex.getPath()));
    }
    protected ResourceLocation texture(String name, String location, String suffix) {
        return modLoc("block/" + location + name + suffix);
    }
    public ItemModelBuilder itemBlockFlat(Supplier<? extends Block> block, String location, String suffix) {
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer0", texture(blockName(block), location, suffix));
    }

    public ItemModelBuilder eggItem(Supplier<? extends Item> item) {
        return this.withExistingParent(ForgeRegistries.ITEMS.getKey(item.get()).getPath(), this.mcLoc("item/template_spawn_egg"));
    }
    public void itemLogWallBlock(Block block, Block baseBlock, String location, String modid) {
        ResourceLocation baseTexture = new ResourceLocation(modid, "block/" + location + this.blockName(baseBlock));
        this.withExistingParent(this.blockName(block), this.mcLoc("block/block"))
                .transforms()
                .transform(ItemDisplayContext.GUI).rotation(30.0F, 135.0F, 0.0F).translation(0.0F, 0.0F, 0.0F).scale(0.625F, 0.625F, 0.625F).end()
                .transform(ItemDisplayContext.FIXED).rotation(0.0F, 90.0F, 0.0F).translation(0.0F, 0.0F, 0.0F).scale(0.5F, 0.5F, 0.5F).end()
                .end()
                .texture("top", baseTexture + "_top").texture("side", baseTexture)
                .element().from(4.0F, 0.0F, 4.0F).to(12.0F, 16.0F, 12.0F)
                .face(Direction.DOWN).uvs(4.0F, 4.0F, 12.0F, 12.0F).texture("#top").cullface(Direction.DOWN).end()
                .face(Direction.UP).uvs(4.0F, 4.0F, 12.0F, 12.0F).texture("#top").end()
                .face(Direction.NORTH).uvs(4.0F, 0.0F, 12.0F, 16.0F).texture("#side").end()
                .face(Direction.SOUTH).uvs(4.0F, 0.0F, 12.0F, 16.0F).texture("#side").end()
                .face(Direction.WEST).uvs(4.0F, 0.0F, 12.0F, 16.0F).texture("#side").end()
                .face(Direction.EAST).uvs(4.0F, 0.0F, 12.0F, 16.0F).texture("#side").end().end()
                .element().from(5.0F, 0.0F, 0.0F).to(11.0F, 13.0F, 16.0F)
                .face(Direction.DOWN).uvs(5.0F, 0.0F, 11.0F, 16.0F).texture("#top").cullface(Direction.DOWN).end()
                .face(Direction.UP).uvs(5.0F, 0.0F, 11.0F, 16.0F).texture("#top").end()
                .face(Direction.NORTH).uvs(5.0F, 3.0F, 11.0F, 16.0F).texture("#side").cullface(Direction.NORTH).end()
                .face(Direction.SOUTH).uvs(5.0F, 3.0F, 11.0F, 16.0F).texture("#side").cullface(Direction.SOUTH).end()
                .face(Direction.WEST).uvs(0.0F, 3.0F, 16.0F, 16.0F).texture("#side").end()
                .face(Direction.EAST).uvs(0.0F, 3.0F, 16.0F, 16.0F).texture("#side").end().end();
    }

    public void itemWoodWallBlock(Block block, Block baseBlock, String location, String modid) {
        this.wallInventory(this.blockName(block), new ResourceLocation(modid, "block/" + location + this.blockName(baseBlock)));
    }
}


