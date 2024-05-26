package net.zepalesque.redux.blockset.stone.type;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.natural.AetherDoubleDropBlock;
import com.aetherteam.aether.block.natural.AetherLogBlock;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.blockset.util.ReduxGeneration;
import net.zepalesque.redux.data.prov.ReduxBlockStateProvider;
import net.zepalesque.redux.data.prov.ReduxItemModelProvider;
import net.zepalesque.redux.data.prov.ReduxLanguageProvider;
import net.zepalesque.redux.data.prov.ReduxRecipeProvider;
import net.zepalesque.redux.data.prov.loot.ReduxBlockLootProvider;
import net.zepalesque.redux.data.prov.tags.ReduxBlockTagsProvider;
import net.zepalesque.redux.data.prov.tags.ReduxItemTagsProvider;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.zenith.api.blockset.AbstractStoneSet;
import net.zepalesque.zenith.api.blockset.util.CraftingMatrix;
import net.zepalesque.zenith.mixin.mixins.common.accessor.FireAccessor;
import net.zepalesque.zenith.util.DatagenUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class BaseStoneSet extends AbstractStoneSet implements ReduxGeneration {

    public final String id, textureFolder, lore;

    protected final DeferredBlock<AetherDoubleDropBlock> base;
    protected final DeferredBlock<StairBlock> stairs;
    protected final DeferredBlock<SlabBlock> slab;
    protected final DeferredBlock<WallBlock> wall;
    protected final NoteBlockInstrument instrument;
    protected final Map<CraftingMatrix, Supplier<Block>> crafted_blocks = new HashMap<>();
    protected final Map<CraftingMatrix, AbstractStoneSet> crafted_sets = new HashMap<>();
    protected final List<Supplier<Block>> stonecut_blocks = new ArrayList<>();
    protected final List<AbstractStoneSet> stonecut_sets = new ArrayList<>();
    protected final Map<Supplier<Block>, Float> smelted_blocks = new HashMap<>();
    protected final Map<AbstractStoneSet, Float> smelted_sets = new HashMap<>();

    public BaseStoneSet(String id, MapColor color, SoundType sound, float breakTime, float blastResistance, NoteBlockInstrument instrument, String textureFolder, String lore) {
        this.id = id;
        this.textureFolder = textureFolder;
        this.lore = lore;
        DeferredRegister.Blocks blocks = ReduxBlocks.BLOCKS;
        DeferredRegister.Items items = ReduxItems.ITEMS;
        this.instrument = instrument;

        this.base = this.block(blocks, items, id, color, sound, breakTime, blastResistance);
        this.stairs = this.stairs(blocks, items, id, color, sound, breakTime, blastResistance);
        this.slab = this.slab(blocks, items, id, color, sound, breakTime, blastResistance);
        this.wall = this.wall(blocks, items, id, color, sound, breakTime, blastResistance);

    }


    @Override
    protected DeferredBlock<AetherDoubleDropBlock> block(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType, float breakTime, float blastResistance) {
        var block = registry.register(this.baseName(true), () -> new AetherDoubleDropBlock(
                BlockBehaviour.Properties.of()
                        .strength(breakTime, blastResistance)
                        .mapColor(color)
                        .sound(soundType)
                        .requiresCorrectToolForDrops()
                        .instrument(this.instrument)
        ));
        items.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    @Override
    public DeferredBlock<AetherDoubleDropBlock> block() {
        return this.base;
    }

    @Override
    protected DeferredBlock<StairBlock> stairs(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType, float breakTime, float blastResistance) {
        var block = registry.register(this.baseName(false) + "_stairs", () -> new StairBlock(() -> this.base.get().defaultBlockState(),
                BlockBehaviour.Properties.of()
                        .strength(breakTime, blastResistance)
                        .mapColor(color)
                        .sound(soundType)
                        .requiresCorrectToolForDrops()
                        .instrument(this.instrument)
        ));
        items.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    @Override
    public DeferredBlock<StairBlock> stairs() {
        return this.stairs;
    }

    @Override
    protected DeferredBlock<SlabBlock> slab(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType, float breakTime, float blastResistance) {
        var block = registry.register(this.baseName(false) + "_slab", () -> new SlabBlock(
                BlockBehaviour.Properties.of()
                        // Unchanged as vanilla appears to no longer have higher break times for slabs
                        .strength(breakTime, blastResistance)
                        .mapColor(color)
                        .sound(soundType)
                        .requiresCorrectToolForDrops()
                        .instrument(this.instrument)
        ));
        items.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    @Override
    public DeferredBlock<SlabBlock> slab() {
        return this.slab;
    }

    @Override
    protected DeferredBlock<WallBlock> wall(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType, float breakTime, float blastResistance) {
        var block = registry.register(this.baseName(false) + "_wall", () -> new WallBlock(
                BlockBehaviour.Properties.of()
                        .strength(breakTime, blastResistance)
                        .mapColor(color)
                        .sound(soundType)
                        .requiresCorrectToolForDrops()
                        .instrument(this.instrument)
        ));
        items.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    @Override
    public DeferredBlock<WallBlock> wall() {
        return this.wall;
    }

    @Override
    protected BaseStoneSet craftsInto(AbstractStoneSet set, CraftingMatrix shape) {
        this.crafted_sets.put(shape, set);
        return this;
    }

    @Override
    protected BaseStoneSet craftsInto(Supplier<Block> block, CraftingMatrix shape) {
        this.crafted_blocks.put(shape, block);
        return this;
    }

    @Override
    protected BaseStoneSet stonecutInto(AbstractStoneSet set) {
        this.stonecut_sets.add(set);
        return this;
    }

    @Override
    protected BaseStoneSet stonecutInto(Supplier<Block> block) {
        this.stonecut_blocks.add(block);
        return this;
    }

    @Override
    protected BaseStoneSet smeltsInto(AbstractStoneSet set, float experience) {
        this.smelted_sets.put(set, experience);
        return this;
    }

    @Override
    protected BaseStoneSet smeltsInto(Supplier<Block> block, float experience) {
        this.smelted_blocks.put(block, experience);
        return this;
    }

    @Override
    protected String baseName(boolean isBaseBlock) {
        return this.id;
    }

    @Override
    public void blockData(ReduxBlockStateProvider data) {
        data.block(this.block().get(), this.textureFolder);
        data.stairs(this.stairs().get(), this.block().get(), this.textureFolder);
        data.slab(this.slab().get(), this.block().get(), this.textureFolder);
        data.wallBlock(this.wall().get(), this.block().get(), this.textureFolder);
    }

    @Override
    public void itemData(ReduxItemModelProvider data) {
        data.itemBlock(this.block().get());
        data.itemBlock(this.stairs().get());
        data.itemBlock(this.slab().get());
        data.itemWallBlock(this.wall().get(), this.block().get(), this.textureFolder);
    }

    @Override
    public void langData(ReduxLanguageProvider data) {

        String blockName = DatagenUtil.getNameLocalized(this.block());

        data.add(this.block());
        data.addLore(this.block(), this.lore);
        data.add(this.stairs());
        data.addLore(this.stairs(), "Crafted from " + blockName + ". Stairs are useful for adding verticality to builds and are often used for decoration too!");
        data.add(this.slab());
        data.addLore(this.slab(), "Crafted from " + blockName + ". Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building's roofing!");
        data.add(this.wall());
        data.addLore(this.wall(), "Crafted from " + blockName + ". Walls can be used for decorative enclosures and defences. Great for keeping nasty intruders away!");
    }

    @Override
    public void recipeData(ReduxRecipeProvider data, RecipeOutput consumer) {

        this.crafted_sets.forEach((matrix, set) -> {
            matrix.apply(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, set.block().get(), matrix.count()))
                    .unlockedBy(ReduxRecipeProvider.getHasName(set.block().get()), ReduxRecipeProvider.has(set.block().get())).save(consumer,
                            ReduxRecipeProvider.getItemName(set.block().get()) + "_from_" + ReduxRecipeProvider.getItemName(this.block().get()));
        });

        this.crafted_blocks.forEach((matrix, block) -> {
            matrix.apply(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block.get(), matrix.count()))
                    .unlockedBy(ReduxRecipeProvider.getHasName(block.get()), ReduxRecipeProvider.has(block.get())).save(consumer,
                            ReduxRecipeProvider.getItemName(block.get()) + "_from_" + ReduxRecipeProvider.getItemName(this.block().get()));
        });

        this.stonecut_blocks.forEach(block -> {
            data.stone
        });


    }

    @Override
    public void blockTagData(ReduxBlockTagsProvider data) {

    }

    @Override
    public void itemTagData(ReduxItemTagsProvider data) {

    }

    @Override
    public void lootData(ReduxBlockLootProvider data) {

    }

    @Override
    public void mapData(DataMapProvider data) {

    }

    @Override
    public void flammables(FireAccessor accessor) {

    }

    @Override
    public void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {

    }
}
