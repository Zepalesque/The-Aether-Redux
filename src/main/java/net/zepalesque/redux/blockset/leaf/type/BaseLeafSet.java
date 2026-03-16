package net.zepalesque.redux.blockset.leaf.type;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.blockset.util.MutableLoreGeneration;
import net.zepalesque.redux.blockset.util.ReduxGeneration;
import net.zepalesque.redux.data.prov.ReduxBlockStateProvider;
import net.zepalesque.redux.data.prov.ReduxDataMapProvider;
import net.zepalesque.redux.data.prov.ReduxItemModelProvider;
import net.zepalesque.redux.data.prov.ReduxLanguageProvider;
import net.zepalesque.redux.data.prov.ReduxRecipeProvider;
import net.zepalesque.redux.data.prov.loot.ReduxBlockLootProvider;
import net.zepalesque.redux.data.prov.tags.ReduxBlockTagsProvider;
import net.zepalesque.redux.data.prov.tags.ReduxItemTagsProvider;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.zenith.api.blockset.type.AbstractLeafSet;
import net.zepalesque.zenith.mixin.mixins.common.accessor.FireAccessor;
import net.zepalesque.zenith.util.function.Consumers;
import net.zepalesque.zenith.util.item.TabUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public abstract class BaseLeafSet<L extends LeavesBlock, S extends SaplingBlock, Self extends BaseLeafSet<L, S, Self>>  extends AbstractLeafSet<Self>
	implements
	ReduxGeneration
{
	public final String id, saplTexFold, leafTexFold;
	protected String saplingLore;
	protected String leafLore;
	// java with typedefs when
	protected final Pair<Collection<TagKey<Item>>, Collection<TagKey<Block>>> leafTags = Pair.of(new ArrayList<>(), new ArrayList<>());
	protected final Pair<Collection<TagKey<Item>>, Collection<TagKey<Block>>> saplingTags = Pair.of(new ArrayList<>(), new ArrayList<>());
	protected final Collection<TagKey<Block>> potTags = new ArrayList<>();
	protected float leafCompost = 0.3F;
	protected float saplingCompost = 0.65F;
	
	public BaseLeafSet(String id, String saplTexFold, String leafTexFold, TreeGrower grower, Supplier<L> leaves, Function<TreeGrower, S> sapling) {
		this.id = id;
		var blocks = ReduxBlocks.BLOCKS;
		var items = ReduxItems.ITEMS;
		this.saplTexFold = saplTexFold;
		this.leafTexFold = leafTexFold;
		this.grower = grower;
		this.leaves = leaves(blocks, items, id, leaves);
		this.sapling = sapling(blocks, items, id, () -> sapling.apply(grower));
		this.pot = pot(blocks, id);
		potTags.add(BlockTags.FLOWER_POTS);
	}
	
	protected final Map<Supplier<CreativeModeTab>, Pair<ItemLike, TabAdditionPhase>> leafBeforeOrdering =
		new HashMap<>();
	protected final Map<Supplier<CreativeModeTab>, Pair<ItemLike, TabAdditionPhase>> leafAfterOrdering =
		new HashMap<>();
	protected final Map<Supplier<CreativeModeTab>, TabAdditionPhase> leafAppended = new HashMap<>();
	@Nullable
	protected Pair<Integer, Integer> leafFlammability = Pair.of(60, 100);
	
	
	
	protected final Map<Supplier<CreativeModeTab>, Pair<ItemLike, TabAdditionPhase>> saplingBeforeOrdering =
		new HashMap<>();
	protected final Map<Supplier<CreativeModeTab>, Pair<ItemLike, TabAdditionPhase>> saplingAfterOrdering =
		new HashMap<>();
	protected final Map<Supplier<CreativeModeTab>, TabAdditionPhase> saplingAppended = new HashMap<>();
	@Nullable
	protected Pair<Integer, Integer> saplingFlammability = Pair.of(60, 100);
	
	protected final DeferredBlock<S> sapling;
	protected final DeferredBlock<L> leaves;
	protected final DeferredBlock<FlowerPotBlock> pot;
	protected final TreeGrower grower;
	protected UnaryOperator<BlockBehaviour.Properties> potProperties = UnaryOperator.identity();
	protected UnaryOperator<BlockBehaviour.Properties> leafProperties = UnaryOperator.identity();
	

	
	@Override
	protected <T extends Block> DeferredBlock<T> leaves(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, Supplier<T> constructor) {
		var leaves = registry.register(id + "_leaves", constructor);
		items.register(leaves.getId().getPath(), () ->
			new BlockItem(leaves.get(), new Item.Properties())
		);
		return leaves;
	}
	
	@Override
	public DeferredBlock<? extends LeavesBlock> leaves() {
		return this.leaves;
	}
	
	@Override
	protected <T extends Block> DeferredBlock<T> sapling(
		DeferredRegister.Blocks registry,
		DeferredRegister.Items items,
		String id,
		Supplier<T> constructor
	) {
		var sapling = registry.register(id + "_sapling", constructor);
		items.register(sapling.getId().getPath(), () ->
			new BlockItem(sapling.get(), new Item.Properties())
		);
		return sapling;
	}
	
	@Override
	public DeferredBlock<? extends SaplingBlock> sapling() {
		return this.sapling;
	}
	
	@Override
	protected DeferredBlock<FlowerPotBlock> pot(DeferredRegister.Blocks registry, String id) {
		return registry.register("potted_" + id + "_sapling", () ->
			new FlowerPotBlock(
				() -> (FlowerPotBlock) Blocks.FLOWER_POT,
				() -> this.sapling().get(),
				// why does java not allow this.potProperties(..) smh my head
				this.potProperties.apply(BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT))
			)
		);
	}
	
	@Override
	public DeferredBlock<FlowerPotBlock> pot() {
		return this.pot;
	}

	
	@Override
	public Self withSaplingItemTag(TagKey<Item> tag) {
		this.saplingTags.getFirst().add(tag);
		return self();
	}
	
	@Override
	public Self withLeafItemTag(TagKey<Item> tag) {
		this.leafTags.getFirst().add(tag);
		return self();
	}
	
	@Override
	public Self withLeafTag(TagKey<Block> tag) {
		this.leafTags.getSecond().add(tag);
		return self();
	}
	
	@Override
	public Self withSaplingTag(TagKey<Block> tag) {
		this.saplingTags.getSecond().add(tag);
		return self();
	}
	
	@Override
	public Self withPotTag(TagKey<Block> tag) {
		this.potTags.add(tag);
		return self();
	}
	
	@Override
	public Self leafTabAfter(
		Supplier<CreativeModeTab> tab,
		ItemLike placeAfter,
		TabAdditionPhase phase
	) {
		this.leafAfterOrdering.put(tab, Pair.of(placeAfter, phase));
		return self();
	}
	
	@Override
	public Self leafTabBefore(Supplier<CreativeModeTab> tab, ItemLike placeBefore, TabAdditionPhase phase) {
		this.leafBeforeOrdering.put(tab, Pair.of(placeBefore, phase));
		return self();
	}
	
	@Override
	public Self leafTabAppend(Supplier<CreativeModeTab> tab, TabAdditionPhase phase) {
		this.leafAppended.put(tab, phase);
		return self();
	}
	
	@Override
	public Self saplingTabAfter(Supplier<CreativeModeTab> tab, ItemLike placeAfter, TabAdditionPhase phase) {
		this.saplingAfterOrdering.put(tab, Pair.of(placeAfter, phase));
		return self();
	}
	
	@Override
	public Self saplingTabBefore(Supplier<CreativeModeTab> tab, ItemLike placeBefore, TabAdditionPhase phase) {
		this.saplingBeforeOrdering.put(tab, Pair.of(placeBefore, phase));
		return self();
	}
	
	@Override
	public Self saplingTabAppend(Supplier<CreativeModeTab> tab, TabAdditionPhase phase) {
		this.saplingAppended.put(tab, phase);
		return self();
	}
	
	@Override
	public Self leafCompost(float amount) {
		this.leafCompost = amount;
		return self();
	}
	
	@Override
	public Self saplingCompost(float amount) {
		this.saplingCompost = amount;
		return self();
	}
	
	@Override
	public Self leafFlammable(int encouragement, int flammability) {
		this.leafFlammability = Pair.of(encouragement, flammability);
		return self();
	}
	@Override
	public Self saplingFlammable(int encouragement, int flammability) {
		this.saplingFlammability = Pair.of(encouragement, flammability);
		return self();
	}
	@Override
	public Self leafInflammable() {
		this.leafFlammability = null;
		return self();
	}
	@Override
	public Self saplingInflammable() {
		this.saplingFlammability = null;
		return self();
	}
	
	@Override
	public TreeGrower grower() {
		return this.grower;
	}
	
	@Override
	public Self withPotProperties(UnaryOperator<BlockBehaviour.Properties> prop) {
		var old = this.potProperties;
		this.potProperties = original -> prop.apply(old.apply(original));
		return self();
	}
	
	@Override
	public Self withLeafProperties(UnaryOperator<BlockBehaviour.Properties> prop) {
		var old = this.leafProperties;
		this.leafProperties = original -> prop.apply(old.apply(original));
		return self();
	}
	
	@Override
	public void flammables(FireAccessor fire) {
		if (this.leafFlammability != null) fire.callSetFlammable(
			this.leaves().get(),
			this.leafFlammability.getFirst(),
			this.leafFlammability.getSecond()
		);
		if (this.saplingFlammability != null) fire.callSetFlammable(
			this.sapling().get(),
			this.saplingFlammability.getFirst(),
			this.saplingFlammability.getSecond()
		);
		
		// Do pot stuff while we're at it
		var pot = (FlowerPotBlock) Blocks.FLOWER_POT;
		addFlower(pot, this.sapling, this.pot);
	}
	
	protected void addFlower(
		FlowerPotBlock base,
		Supplier<? extends Block> sapling,
		Supplier<? extends Block> pot
	) {
		base.addPlant(BuiltInRegistries.BLOCK.getKey(sapling.get()), pot);
	}
	
	@Override
	public void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {}
	
	// Ignore the prev value, implementation is different here
	@Override
	public @Nullable ItemLike addToCreativeTab(BuildCreativeModeTabContentsEvent event, ItemLike prev, TabAdditionPhase phase) {
		
		for (var entry : this.leafAfterOrdering.entrySet()) {
			var tabToAddTo = entry.getKey();
			if (TabUtil.isForTab(event, tabToAddTo)) {
				var pair = entry.getValue();
				if (phase == pair.getSecond()) TabUtil.putAfter(event, pair.getFirst(), this.leaves());
			}
		}
		for (var entry : this.saplingAfterOrdering.entrySet()) {
			var tabToAddTo = entry.getKey();
			if (TabUtil.isForTab(event, tabToAddTo)) {
				var pair = entry.getValue();
				if (phase == pair.getSecond()) TabUtil.putAfter(event, pair.getFirst(), this.sapling());
			}
		}
		for (var entry : this.leafBeforeOrdering.entrySet()) {
			var tabToAddTo = entry.getKey();
			if (TabUtil.isForTab(event, tabToAddTo)) {
				var pair = entry.getValue();
				if (phase == pair.getSecond()) TabUtil.putBefore(event, pair.getFirst(), this.leaves());
			}
		}
		for (var entry : this.saplingBeforeOrdering.entrySet()) {
			var tabToAddTo = entry.getKey();
			if (TabUtil.isForTab(event, tabToAddTo)) {
				var pair = entry.getValue();
				if (phase == pair.getSecond()) TabUtil.putBefore(event, pair.getFirst(), this.sapling());
			}
		}
		for (var entry : this.leafAppended.entrySet()) {
			var tabToAddTo = entry.getKey();
			if (TabUtil.isForTab(event, tabToAddTo)) {
				var current = entry.getValue();
				if (phase == current) TabUtil.put(event, this.leaves());
			}
		}
		for (var entry : this.saplingAppended.entrySet()) {
			var tabToAddTo = entry.getKey();
			if (TabUtil.isForTab(event, tabToAddTo)) {
				var current = entry.getValue();
				if (phase == current) TabUtil.put(event, this.sapling());
			}
		}
		return null;
	}
	
	@Override
	public String getID() {
		return this.id;
	}

	

	
	@Override
	public void langData(ReduxLanguageProvider data) {
		data.addBlock(this.leaves());
		if (this.leafLore != null) data.addLore(this.leaves(), this.leafLore);
		data.addBlock(this.sapling());
		if (this.saplingLore != null) data.addLore(this.sapling(), this.saplingLore);
		data.addBlock(this.pot());
	}
	
	@Override
	public void recipeData(ReduxRecipeProvider data, RecipeOutput consumer, HolderLookup.Provider lookup) {
	
	}
	
	@Override
	public void blockTagData(ReduxBlockTagsProvider data) {
		this.leafTags.getSecond().forEach(tag -> data.tag(tag).add(this.leaves().get()));
		this.saplingTags.getSecond().forEach(tag -> data.tag(tag).add(this.sapling().get()));
		this.potTags.forEach(tag -> data.tag(tag).add(this.pot().get()));
	}
	
	@Override
	public void itemTagData(ReduxItemTagsProvider data) {
		this.leafTags.getFirst().forEach(tag -> data.tag(tag).add(this.leaves().asItem()));
		this.saplingTags.getFirst().forEach(tag -> data.tag(tag).add(this.sapling().asItem()));
	}
	
	@Override
	public void lootData(ReduxBlockLootProvider data) {
		data.dropSelf(this.sapling().get());
		data.leaves(this.leaves(), this.sapling());
		data.dropPottedContents(this.pot.get());
	}
	
	@Override
	public void mapData(ReduxDataMapProvider data) {
		var compostables = data.builder(NeoForgeDataMaps.COMPOSTABLES);
		data.addCompost(compostables, this.sapling(), this.saplingCompost);
		data.addCompost(compostables, this.leaves(), this.leafCompost);
	}
	
	public Self withLeafLore(String lore) {
		this.leafLore = lore;
		return self();
	}
	
	
	public Self withSaplingLore(String lore) {
		this.saplingLore = lore;
		return self();
	}
	
	@SuppressWarnings("unchecked")
	protected Self self() {
		return (Self) this;
	}
}
