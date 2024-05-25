package net.zepalesque.redux.blockset;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.natural.AetherLogBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.natural.NaturalLog;
import net.zepalesque.redux.data.ReduxTags;
import net.zepalesque.redux.data.prov.ReduxBlockStateProvider;
import net.zepalesque.redux.data.prov.ReduxItemModelProvider;
import net.zepalesque.redux.data.prov.ReduxLanguageProvider;
import net.zepalesque.redux.data.prov.ReduxRecipeProvider;
import net.zepalesque.redux.entity.ReduxEntities;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.tile.ReduxTiles;
import net.zepalesque.zenith.api.blockset.AbstractWoodSet;
import net.zepalesque.zenith.block.ZenithCeilingHangingSignBlock;
import net.zepalesque.zenith.block.ZenithSignBlock;
import net.zepalesque.zenith.block.ZenithWallHangingSignBlock;
import net.zepalesque.zenith.block.ZenithWallSignBlock;
import net.zepalesque.zenith.entity.misc.ZenithBoat;
import net.zepalesque.zenith.entity.misc.ZenithChestBoat;
import net.zepalesque.zenith.item.ZenithBoatItem;
import net.zepalesque.zenith.tile.ZenithHangingSignBlockEntity;
import net.zepalesque.zenith.tile.ZenithSignBlockEntity;
import net.zepalesque.zenith.util.DatagenUtil;
import org.apache.commons.lang3.CharUtils;
import org.codehaus.plexus.util.StringUtils;

import javax.annotation.Nullable;

public class BaseAetherWoodSet extends AbstractWoodSet {

    protected final String id;

    protected final DeferredBlock<AetherLogBlock> log;
    protected final DeferredBlock<AetherLogBlock> stripped_log;
    protected final DeferredBlock<NaturalLog> wood;
    protected final DeferredBlock<NaturalLog> stripped_wood;
    protected final DeferredBlock<Block> planks;
    protected final DeferredBlock<StairBlock> stairs;
    protected final DeferredBlock<SlabBlock> slab;
    protected final DeferredBlock<FenceBlock> fence;
    protected final DeferredBlock<FenceGateBlock> fence_gate;
    protected final DeferredBlock<DoorBlock> door;
    protected final DeferredBlock<TrapDoorBlock> trapdoor;
    protected final DeferredBlock<PressurePlateBlock> pressure_plate;
    protected final DeferredBlock<ButtonBlock> button;
    protected final DeferredBlock<ZenithSignBlock> sign;
    protected final DeferredBlock<ZenithWallSignBlock> wall_sign;
    protected final DeferredBlock<ZenithCeilingHangingSignBlock> hanging_sign;
    protected final DeferredBlock<ZenithWallHangingSignBlock> wall_hanging_sign;

    protected final DeferredItem<ZenithBoatItem> boat_item;
    protected final DeferredItem<ZenithBoatItem> chest_boat_item;

    protected final DeferredHolder<EntityType<?>, EntityType<? extends ZenithBoat>> boat;
    protected final DeferredHolder<EntityType<?>, EntityType<? extends ZenithChestBoat>> chest_boat;

    protected final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends ZenithSignBlockEntity>> sign_entity;
    protected final DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends ZenithHangingSignBlockEntity>> hanging_sign_entity;

    protected final BlockSetType setType;
    protected final WoodType woodType;

    public final TagKey<Item> logsTag;
    public final TagKey<Block> logsBlockTag;

    public BaseAetherWoodSet(String id, MapColor woodColor, MapColor barkColor, SoundType sound) {

        this.id = id;

        this.setType = this.setType(id, sound);
        this.woodType = this.woodType(id, this.setType, sound);

        this.logsTag = this.logsTag(id);
        this.logsBlockTag = this.logsBlockTag(id);

        DeferredRegister.Blocks blocks = ReduxBlocks.BLOCKS;
        DeferredRegister.Items items = ReduxItems.ITEMS;
        this.log = this.log(blocks, items, id, woodColor, barkColor, sound);
        this.stripped_log = this.strippedLog(blocks, items, id, woodColor, sound);
        this.wood = this.wood(blocks, items, id, barkColor, sound);
        this.stripped_wood = this.strippedWood(blocks, items, id, woodColor, sound);
        this.planks = this.planks(blocks, items, id, woodColor, sound);
        this.stairs = this.stairs(blocks, items, id, woodColor, sound);
        this.slab = this.slab(blocks, items, id, woodColor, sound);
        this.fence = this.fence(blocks, items, id, woodColor, sound);
        this.fence_gate = this.fenceGate(blocks, items, id, woodColor);
        this.door = this.door(blocks, items, id, woodColor);
        this.trapdoor = this.trapdoor(blocks, items, id, woodColor);
        this.pressure_plate = this.pressurePlate(blocks, items, id, woodColor);
        this.button = this.button(blocks, items, id, woodColor);
        this.sign = this.sign(blocks, items, id, woodColor, sound);
        this.wall_sign = this.wallSign(blocks, id, woodColor, sound);
        this.hanging_sign = this.hangingSign(blocks, items, id, woodColor, sound);
        this.wall_hanging_sign = this.wallHangingSign(blocks, id, woodColor, sound);

        this.boat_item = this.boatItem(items, id);
        this.chest_boat_item = this.chestBoatItem(items, id);

        DeferredRegister<EntityType<?>> entities = ReduxEntities.ENTITIES;
        this.boat = this.boatEntity(entities, id);
        this.chest_boat = this.chestBoatEntity(entities, id);

        DeferredRegister<BlockEntityType<?>> tiles = ReduxTiles.TILES;
        this.sign_entity = this.signEntity(tiles, id);
        this.hanging_sign_entity = this.hangingSignEntity(tiles, id);
    }

    @Override
    protected DeferredBlock<AetherLogBlock> log(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor woodColor, MapColor barkColor, SoundType soundType) {
        var block = registry.register(id + this.logSuffix(LangType.ID), () -> new AetherLogBlock(Properties.of()
                .mapColor(state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? woodColor : barkColor)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sound(soundType)
                .ignitedByLava()
        ));
        items.register(id + this.logSuffix(LangType.ID), () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    @Override
    public DeferredBlock<AetherLogBlock> log() {
        return this.log;
    }

    @Override
    protected DeferredBlock<AetherLogBlock> strippedLog(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType) {
        var block = registry.register(id + "_stripped" + this.logSuffix(LangType.ID), () -> new AetherLogBlock(Properties.of()
                .mapColor(color)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sound(soundType)
                .ignitedByLava()));
        items.register(id + "_stripped" + this.logSuffix(LangType.ID), () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    @Override
    public DeferredBlock<AetherLogBlock> strippedLog() {
        return this.stripped_log;
    }

    @Override
    protected DeferredBlock<NaturalLog> wood(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType) {
        var block = registry.register(id + this.woodSuffix(LangType.ID), () -> new NaturalLog(Properties.of()
                .mapColor(color)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sound(soundType)
                .ignitedByLava()
        ));
        items.register(id + this.woodSuffix(LangType.ID), () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    @Override
    public DeferredBlock<NaturalLog> wood() {
        return this.wood;
    }

    @Override
    protected DeferredBlock<NaturalLog> strippedWood(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType) {
        var block = registry.register(id + "_stripped" + this.woodSuffix(LangType.ID), () -> new NaturalLog(Properties.of()
                .mapColor(color)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sound(soundType)
                .ignitedByLava()
        ));
        items.register(id + "_stripped" + this.woodSuffix(LangType.ID), () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    @Override
    public DeferredBlock<NaturalLog> strippedWood() {
        return this.stripped_wood;
    }

    @Override
    protected DeferredBlock<Block> planks(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType) {
        var block = registry.register(id + "_planks", () -> new Block(BlockBehaviour.Properties.of()
                .mapColor(color)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F, 3.0F)
                .sound(soundType)
                .ignitedByLava()
        ));
        items.register(id + "_planks", () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    @Override
    public DeferredBlock<Block> planks() {
        return this.planks;
    }

    @Override
    protected DeferredBlock<StairBlock> stairs(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType) {
        var block = registry.register(id + "_stairs", () -> new StairBlock(
                () -> this.planks().get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(this.planks().get())
        ));
        items.register(id + "_stairs", () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    @Override
    public DeferredBlock<StairBlock> stairs() {
        return this.stairs;
    }

    @Override
    protected DeferredBlock<SlabBlock> slab(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType) {
        var block = registry.register(id + "_slab", () -> new SlabBlock(
                BlockBehaviour.Properties.ofFullCopy(this.planks().get()).strength(2.0F, 3.0F)
        ));
        items.register(id + "_slab", () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    @Override
    public DeferredBlock<SlabBlock> slab() {
        return this.slab;
    }

    @Override
    protected DeferredBlock<FenceBlock> fence(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType) {
        var block = registry.register(id + "_fence", () -> new FenceBlock(
                BlockBehaviour.Properties.of()
                        .mapColor(color)
                        .forceSolidOn()
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(2.0F, 3.0F)
                        .sound(soundType)
                        .ignitedByLava()
        ));
        items.register(id + "_fence", () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    @Override
    public DeferredBlock<FenceBlock> fence() {
        return this.fence;
    }

    @Override
    protected DeferredBlock<FenceGateBlock> fenceGate(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color) {
        var block = registry.register(id + "_fence_gate", () -> new FenceGateBlock(
                this.woodType(),
                BlockBehaviour.Properties.of()
                        .mapColor(color)
                        .forceSolidOn()
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(2.0F, 3.0F)
                        .ignitedByLava()
        ));
        items.register(id + "_fence_gate", () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    @Override
    public DeferredBlock<FenceGateBlock> fenceGate() {
        return this.fence_gate;
    }

    @Override
    protected DeferredBlock<DoorBlock> door(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color) {
        var block = registry.register(id + "_door", () -> new DoorBlock(
                this.setType(),
                BlockBehaviour.Properties.of()
                        .mapColor(color)
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(3.0F)
                        .noOcclusion()
                        .ignitedByLava()
                        .pushReaction(PushReaction.DESTROY)
        ));
        items.register(id + "_door", () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    @Override
    public DeferredBlock<DoorBlock> door() {
        return this.door;
    }

    @Override
    protected DeferredBlock<TrapDoorBlock> trapdoor(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color) {
        var block = registry.register(id + "_trapdoor", () -> new TrapDoorBlock(
                this.setType(),
                BlockBehaviour.Properties.of()
                        .mapColor(color)
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(3.0F)
                        .noOcclusion()
                        .isValidSpawn((state, level, pos, entity) -> false)
                        .ignitedByLava()
        ));
        items.register(id + "_trapdoor", () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    @Override
    public DeferredBlock<TrapDoorBlock> trapdoor() {
        return this.trapdoor;
    }

    @Override
    protected DeferredBlock<PressurePlateBlock> pressurePlate(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color) {
        var block = registry.register(id + "_pressure_plate", () -> new PressurePlateBlock(
                this.setType(),
                BlockBehaviour.Properties.of()
                        .mapColor(color)
                        .forceSolidOn()
                        .instrument(NoteBlockInstrument.BASS)
                        .noCollission()
                        .strength(0.5F)
                        .ignitedByLava()
                        .pushReaction(PushReaction.DESTROY)
        ));
        items.register(id + "_pressure_plate", () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    @Override
    public DeferredBlock<PressurePlateBlock> pressurePlate() {
        return this.pressure_plate;
    }

    @Override
    protected DeferredBlock<ButtonBlock> button(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color) {
        var block = registry.register(id + "_button", () -> new ButtonBlock(
                this.setType(),
                30,
                BlockBehaviour.Properties.of()
                        .noCollission()
                        .strength(0.5F)
                        .pushReaction(PushReaction.DESTROY)
        ));
        items.register(id + "_button", () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    @Override
    public DeferredBlock<ButtonBlock> button() {
        return this.button;
    }

    @Override
    protected DeferredBlock<ZenithSignBlock> sign(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType) {
        var block = registry.register(id + "_sign", () -> new ZenithSignBlock(
                this.woodType(),
                this.signEntity()::get,
                Block.Properties.of()
                        .mapColor(color)
                        .forceSolidOn().ignitedByLava()
                        .instrument(NoteBlockInstrument.BASS)
                        .noCollission().strength(1.0F)
                        .sound(soundType)
        ));
        items.register(id + "_sign", () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    @Override
    public DeferredBlock<ZenithSignBlock> sign() {
        return this.sign;
    }

    @Override
    protected DeferredBlock<ZenithWallSignBlock> wallSign(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType) {
        return registry.register(id + "_wall_sign", () -> new ZenithWallSignBlock(
                this.woodType(),
                this.signEntity()::get,
                Block.Properties.of()
                        .mapColor(color)
                        .forceSolidOn().ignitedByLava()
                        .instrument(NoteBlockInstrument.BASS)
                        .noCollission().strength(1.0F)
                        .sound(soundType)
        ));   
    }

    @Override
    public DeferredBlock<ZenithWallSignBlock> wallSign() {
        return this.wall_sign;
    }

    @Override
    protected DeferredBlock<ZenithCeilingHangingSignBlock> hangingSign(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType) {
        var block = registry.register(id + "_hanging_sign", () -> new ZenithCeilingHangingSignBlock(
                this.woodType(),
                this.hangingSignEntity()::get,
                Block.Properties.of()
                        .mapColor(color)
                        .forceSolidOn().ignitedByLava()
                        .instrument(NoteBlockInstrument.BASS)
                        .noCollission().strength(1.0F)
                        .sound(soundType)
        ));
        items.register(id + "_hanging_sign", () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    @Override
    public DeferredBlock<ZenithCeilingHangingSignBlock> hangingSign() {
        return this.hanging_sign;
    }

    @Override
    protected DeferredBlock<ZenithWallHangingSignBlock> wallHangingSign(DeferredRegister.Blocks registry, String id, MapColor color, SoundType soundType) {
        return registry.register(id + "_wall_hanging_sign", () -> new ZenithWallHangingSignBlock(
                this.woodType(),
                this.hangingSignEntity()::get,
                Block.Properties.of()
                        .mapColor(color)
                        .forceSolidOn().ignitedByLava()
                        .instrument(NoteBlockInstrument.BASS)
                        .noCollission().strength(1.0F)
                        .sound(soundType)
        ));    }

    @Override
    public DeferredBlock<ZenithWallHangingSignBlock> wallHangingSign() {
        return this.wall_hanging_sign;
    }

    @Override
    protected DeferredItem<ZenithBoatItem> boatItem(DeferredRegister.Items registry, String id) {
        return registry.register(id + "_boat", () -> new ZenithBoatItem(new Item.Properties().stacksTo(1),
                (level, raycast) -> new ZenithBoat(this, level, raycast.getLocation().x, raycast.getLocation().y, raycast.getLocation().z)
        ));
    }

    @Override
    public DeferredItem<ZenithBoatItem> boatItem() {
        return this.boat_item;
    }

    @Override
    protected DeferredItem<ZenithBoatItem> chestBoatItem(DeferredRegister.Items registry, String id) {
        return registry.register(id + "_chest_boat", () -> new ZenithBoatItem(new Item.Properties().stacksTo(1),
                (level, raycast) -> new ZenithChestBoat(this, level, raycast.getLocation().x, raycast.getLocation().y, raycast.getLocation().z)
        ));    }

    @Override
    public DeferredItem<ZenithBoatItem> chestBoatItem() {
        return this.chest_boat_item;
    }

    @Override
    protected DeferredHolder<EntityType<?>, EntityType<? extends ZenithBoat>> boatEntity(DeferredRegister<EntityType<?>> registry, String id) {
        return registry.register(id + "_boat", () ->
                EntityType.Builder.<ZenithBoat>of(ZenithBoat::new, MobCategory.MISC)
                        .sized(1.375F, 0.5625F).clientTrackingRange(10).build(id + "_boat")
        );
    }

    @Override
    public DeferredHolder<EntityType<?>, EntityType<? extends ZenithBoat>> boatEntity() {
        return this.boat;
    }

    @Override
    protected DeferredHolder<EntityType<?>, EntityType<? extends ZenithChestBoat>> chestBoatEntity(DeferredRegister<EntityType<?>> registry, String id) {
        return registry.register(id + "_boat", () ->
                EntityType.Builder.<ZenithChestBoat>of(ZenithChestBoat::new, MobCategory.MISC)
                        .sized(1.375F, 0.5625F).clientTrackingRange(10).build(id + "_boat")
        );

    }

    @Override
    public DeferredHolder<EntityType<?>, EntityType<? extends ZenithChestBoat>> chestBoatEntity() {
        return this.chest_boat;
    }

    @Override
    protected DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends ZenithSignBlockEntity>> signEntity(DeferredRegister<BlockEntityType<?>> registry, String id) {
        return registry.register(id + "_sign", () ->
                BlockEntityType.Builder.of(((pPos, pState) -> new ZenithSignBlockEntity(pPos, pState, () -> this.signEntity().get())),
                        this.sign().get(),
                        this.wallSign().get())
                        .build(null));
    }

    @Override
    public DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends ZenithSignBlockEntity>> signEntity() {
        return this.sign_entity;
    }

    @Override
    protected DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends ZenithHangingSignBlockEntity>> hangingSignEntity(DeferredRegister<BlockEntityType<?>> registry, String id) {
        return registry.register(id + "_hanging_sign", () ->
                BlockEntityType.Builder.of(((pPos, pState) -> new ZenithHangingSignBlockEntity(pPos, pState, () -> this.hangingSignEntity().get())),
                                this.sign().get(),
                                this.wallSign().get())
                        .build(null));    }

    @Override
    public DeferredHolder<BlockEntityType<?>, BlockEntityType<? extends ZenithHangingSignBlockEntity>> hangingSignEntity() {
        return this.hanging_sign_entity;
    }

    @Override
    protected BlockSetType setType(String id, SoundType sound) {
        return new BlockSetType(
                id,
                true,
                true,
                true,
                BlockSetType.PressurePlateSensitivity.EVERYTHING,
                sound,
                SoundEvents.WOODEN_DOOR_CLOSE,
                SoundEvents.WOODEN_DOOR_OPEN,
                SoundEvents.WOODEN_TRAPDOOR_CLOSE,
                SoundEvents.WOODEN_TRAPDOOR_OPEN,
                SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_OFF,
                SoundEvents.WOODEN_PRESSURE_PLATE_CLICK_ON,
                SoundEvents.WOODEN_BUTTON_CLICK_OFF,
                SoundEvents.WOODEN_BUTTON_CLICK_ON
        );
    }

    @Override
    public BlockSetType setType() {
        return this.setType;
    }

    @Override
    protected WoodType woodType(String id, BlockSetType type, SoundType sound) {
        return new WoodType(
                id,
                type,
                sound,
                SoundType.HANGING_SIGN,
                SoundEvents.FENCE_GATE_CLOSE,
                SoundEvents.FENCE_GATE_OPEN
        );
    }

    @Override
    public WoodType woodType() {
        return this.woodType;
    }

    @Override
    protected TagKey<Item> logsTag(String id) {
        return ReduxTags.Items.tag(id + this.logSuffix(LangType.PLURAL));
    }

    @Override
    public TagKey<Item> logsTag() {
        return this.logsTag;
    }

    @Override
    protected TagKey<Block> logsBlockTag(String id) {
        return ReduxTags.Blocks.tag(id + this.logSuffix(LangType.PLURAL));
    }

    @Override
    public TagKey<Block> logsBlockTag() {
        return this.logsBlockTag;
    }

    @Override
    public void blockGen(BlockStateProvider provider) {
        if (provider instanceof ReduxBlockStateProvider data) {
           this.blockGen(data);
        }
    }

    protected void blockGen(ReduxBlockStateProvider data) {
        data.log(this.log().get());
        data.log(this.strippedLog().get());
        data.wood(this.wood().get(), this.log().get());
        data.wood(this.strippedWood().get(), this.strippedLog().get());
        data.block(this.planks().get(), "construction/");
        data.stairs(this.stairs().get(), this.planks().get(), "construction/");
        data.slab(this.slab().get(), this.planks().get(), "construction/");
        data.fence(this.fence().get(), this.planks().get(), "construction/");
        data.fenceGateBlock(this.fenceGate().get(), this.planks().get(), "construction/");
        data.doorBlock(this.door().get(), data.texture(data.name(this.door().get()), "construction/", "_bottom"), data.texture(data.name(this.door().get()), "construction/", "_top"));
        data.trapdoorBlock(this.trapdoor().get(), data.texture(data.name(this.trapdoor().get()), "construction/"), true);
        data.pressurePlateBlock(this.pressurePlate().get(), data.texture(data.name(this.planks().get()), "construction/"));
        data.buttonBlock(this.button().get(), data.texture(data.name(this.planks().get()), "construction/"));
        data.signBlock(this.sign().get(), this.wallSign().get(), data.texture(data.name(this.planks().get()), "construction/"));
        data.hangingSignBlock(this.hangingSign().get(), this.wallHangingSign().get(), data.texture(data.name(this.planks().get()), "construction/"));
    }

    @Override
    public void itemGen(ItemModelProvider provider) {
        if (provider instanceof ReduxItemModelProvider data) {
          this.itemGen(data);
        }
    }

    protected void itemGen(ReduxItemModelProvider data) {
        data.itemBlock(this.log().get());
        data.itemBlock(this.strippedLog().get());
        data.itemBlock(this.planks().get());
        data.itemBlock(this.strippedWood().get());
        data.itemBlock(this.wood().get());
        data.itemBlock(this.stairs().get());
        data.itemBlock(this.slab().get());
        data.itemBlock(this.trapdoor().get(), "_bottom");
        data.itemFence(this.fence().get(), this.planks().get(), "construction/");
        data.itemBlock(this.fenceGate().get());
        data.item(this.door().get().asItem(), "misc/");
        data.itemBlock(this.pressurePlate().get());
        data.itemButton(this.button().get(), this.planks().get(), "construction/");
        data.item(this.sign().get().asItem(), "misc/");
        data.item(this.hangingSign().get().asItem(), "misc/");
        data.item(this.boatItem().get(), "misc/");
        data.item(this.chestBoatItem().get(), "misc/");
    }


    @Override
    public void langGen(LanguageProvider provider) {
        if (provider instanceof ReduxLanguageProvider data) {
            this.langGen(data);
        }
    }

    public void langGen(ReduxLanguageProvider data) {
        boolean vowel = DatagenUtil.isVowel(this.id.charAt(0));

        String indefiniteLowercase = vowel ? "an" : "a";
        String indefiniteUppercase = vowel ? "An" : "A";
        String name = DatagenUtil.getNameLocalized(this.id);

        data.add(this.log());
        data.addLore(this.log(), "These spawn with " + name + " " + this.treesName(true) + ". They can be double dropped with Skyroot Axes. When put in a crafting table they will provide 4 " + name + " Planks.");
        data.add(this.strippedLog());
        data.addLore(this.strippedLog(), indefiniteUppercase + name + " " + this.logSuffix(LangType.LANG) + " that has had its bark stripped away with an Axe. When put in a crafting table they will provide 4 " + name + " Planks.");
        data.add(this.wood());
        data.addLore(this.wood(), "Six-sided variant of " + name + " " + this.logSuffix(LangType.LANG_PLURAL) + ". When put in a crafting table they will provide 4 " + name + " Planks.");
        data.add(this.strippedWood());
        data.addLore(this.strippedWood(), name + " " + this.woodSuffix(LangType.LANG) + " that has had its bark stripped away with an Axe. When put in a crafting table they will provide 4 " + name + " Planks.");
        data.add(this.planks());
        data.addLore(this.planks(), "Planks from the " + name + " " + this.treesName(false) + ". Can be used as a building material, along with several other useful things.");
        data.add(this.stairs());
        data.addLore(this.stairs(), "Crafted from " + name + " Planks. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        data.add(this.slab());
        data.addLore(this.slab(), "Crafted from " + name + " Planks. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building's roofing!");
        data.add(this.fence());
        data.addLore(this.fence(), "Crafted from " + name + " Planks and Skyroot Sticks. Fences are great for keeping your livestock safe from wandering predators!");
        data.add(this.fenceGate());
        data.addLore(this.fenceGate(), "Crafted from " + name + " Planks and Skyroot Sticks. Fence gates give a homely entrance and exit to your precious enclosures.");
        data.add(this.door());
        data.addLore(this.door(), "Crafted from " + name + " Planks. Doors are an ornate entrance helpful for keeping an enclosed and safe space without worry of monsters wandering in!");
        data.add(this.trapdoor());
        data.addLore(this.trapdoor(), "Crafted from " + name + " Planks. Trapdoors are useful for covering entryways one block wide. They are often used to add extra protection to staircases.");
        data.add(this.pressurePlate());
        data.addLore(this.pressurePlate(), "Crafted from " + name + " Planks. A wooden pressure plate used to activate mechanisms and redstone.");
        data.add(this.button());
        data.addLore(this.button(), "Crafted from " + name + " Planks, a button used to activate mechanisms and redstone.");
        data.add(this.sign());
        data.addLore(this.sign(), "Crafted from " + name + " Planks. A helpful sign perfect for writing messages and directions on.");
        data.add(this.hangingSign());
        data.addLore(this.hangingSign(), "Crafted from " + name + " Planks. A helpful hanging sign perfect for writing messages and directions on.");

        data.add(this.boatEntity());
        data.addEntityType(this.chestBoatEntity(), name + " Boat with Chest");

        data.add(this.boatItem());
        data.addLore(this.boatItem(), "Crafted from " + name + " Planks. While the Aether does not have many large bodies of water, a boat can occasionally be a useful tool for crossing large distances over ice!");
        data.addItem(this.chestBoatItem(), name + " Boat with Chest");
        data.addLore(this.chestBoatItem(), indefiniteUppercase + name + " Boat with a handy chest in the back. Helpful for transporting more items over long stretches of water, which are famously difficult to find in the Aether.");

    }

    @Override
    public void recipeGen(RecipeProvider provider, RecipeOutput output) {
        if (provider instanceof ReduxRecipeProvider data) {
            this.recipeGen(data, output);
        }
    }

    public void recipeGen(ReduxRecipeProvider data, RecipeOutput consumer) {

        ReduxRecipeProvider.woodFromLogs(consumer, this.wood().get(), this.log().get());

        ReduxRecipeProvider.woodFromLogs(consumer, this.strippedWood().get(), this.strippedLog().get());

        ReduxRecipeProvider.planksFromLog(consumer, this.planks().get(), this.logsTag(), 4);

        ReduxRecipeProvider.slab(consumer, RecipeCategory.BUILDING_BLOCKS, this.slab().get(), this.planks().get());

        data.stairs(this.stairs(), this.planks()).save(consumer);

        data.fence(this.fence(), this.planks()).save(consumer);

        data.fenceGate(this.fenceGate(), this.planks()).save(consumer);

        ReduxRecipeProvider.doorBuilder(this.door().get(), Ingredient.of(this.planks().get()))
                .unlockedBy(ReduxRecipeProvider.getHasName(this.planks().get()), ReduxRecipeProvider.has(this.planks().get())).group("wooden_door").save(consumer);

        ReduxRecipeProvider.trapdoorBuilder(this.trapdoor().get(), Ingredient.of(this.planks().get()))
                .unlockedBy(ReduxRecipeProvider.getHasName(this.planks().get()), ReduxRecipeProvider.has(this.planks().get())).group("wooden_trapdoor").save(consumer);

        ReduxRecipeProvider.pressurePlate(consumer, this.pressurePlate().get(), this.planks().get());

        ReduxRecipeProvider.buttonBuilder(this.button().get(), Ingredient.of(this.planks().get()))
                .unlockedBy(ReduxRecipeProvider.getHasName(this.planks().get()), ReduxRecipeProvider.has(this.planks().get())).group("wooden_button").save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, this.sign().get(), 3)
                .group("wooden_sign")
                .define('P', this.planks().get())
                .define('/', Tags.Items.RODS_WOODEN)
                .pattern("PPP")
                .pattern("PPP")
                .pattern(" / ")
                .unlockedBy(ReduxRecipeProvider.getHasName(this.planks()), ReduxRecipeProvider.has(this.planks()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, this.hangingSign().get(), 6)
                .group("hanging_sign")
                .define('#', this.strippedLog())
                .define('X', Items.CHAIN)
                .pattern("X X")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_stripped_logs", ReduxRecipeProvider.has(this.strippedLog()))
                .save(consumer);

        ReduxRecipeProvider.woodenBoat(consumer, this.boatItem().get(), this.planks().get());

        ReduxRecipeProvider.chestBoat(consumer, this.chestBoatItem().get(), this.boatItem().get());
    }


    @Override
    public void blockTagData(BlockTagsProvider provider) {

    }

    @Override
    public void itemTagData(ItemTagsProvider provider) {

    }

    @Override
    public void entityTagData(EntityTypeTagsProvider data) {

    }

    @Override
    public void lootData(BlockLootSubProvider provider) {

    }

    @Override
    public void mapData(DataMapProvider provider) {

    }

    @Override
    @Nullable
    public String logSuffix(LangType type) {
        return switch (type) {
            case ID -> "_log";
            case PLURAL -> "_logs";
            case LANG -> " Log";
            case LANG_PLURAL -> " Logs";
        };
    }

    @Override
    public String woodSuffix(LangType type) {
        return switch (type) {
            case ID, PLURAL -> "_wood";
            case LANG, LANG_PLURAL -> " Wood";
        };
    }

    @Override
    public String treesName(boolean isPlural) {
        return isPlural ? "trees" : "tree";
    }
}
