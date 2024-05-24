package net.zepalesque.redux.blockset;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.AetherWoodTypes;
import com.aetherteam.aether.block.construction.SkyrootSignBlock;
import com.aetherteam.aether.block.natural.AetherLogBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.natural.NaturalLog;
import net.zepalesque.redux.entity.ReduxEntities;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.tile.ReduxTiles;
import net.zepalesque.zenith.api.blockset.BaseWoodSet;
import net.zepalesque.zenith.block.ZenithCeilingHangingSignBlock;
import net.zepalesque.zenith.block.ZenithSignBlock;
import net.zepalesque.zenith.block.ZenithWallHangingSignBlock;
import net.zepalesque.zenith.block.ZenithWallSignBlock;
import net.zepalesque.zenith.entity.misc.ZenithBoat;
import net.zepalesque.zenith.entity.misc.ZenithChestBoat;
import net.zepalesque.zenith.item.ZenithBoatItem;
import net.zepalesque.zenith.tile.ZenithHangingSignBlockEntity;
import net.zepalesque.zenith.tile.ZenithSignBlockEntity;

public class AetherWoodSet extends BaseWoodSet {

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

    public AetherWoodSet(String id, MapColor woodColor, MapColor barkColor, SoundType sound) {

        this.setType = this.setType(id, sound);
        this.woodType = this.woodType(id, this.setType, sound);

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
        this.trapdoor = this.trapDoor(blocks, items, id, woodColor);
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
        return registry.register(id + "_log", () -> new AetherLogBlock(Properties.of()
                .mapColor(state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? woodColor : barkColor)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sound(soundType)
                .ignitedByLava()
        ));
    }

    @Override
    public DeferredBlock<AetherLogBlock> log() {
        return this.log;
    }

    @Override
    protected DeferredBlock<AetherLogBlock> strippedLog(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType) {
        return registry.register(id + "_stripped_log", () -> new AetherLogBlock(Properties.of()
                .mapColor(color)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sound(soundType)
                .ignitedByLava()
        ));
    }

    @Override
    public DeferredBlock<AetherLogBlock> strippedLog() {
        return this.stripped_log;
    }

    @Override
    protected DeferredBlock<NaturalLog> wood(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType) {
        return registry.register(id + "_wood", () -> new NaturalLog(Properties.of()
                .mapColor(color)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sound(soundType)
                .ignitedByLava()
        ));
    }

    @Override
    public DeferredBlock<NaturalLog> wood() {
        return this.wood;
    }

    @Override
    protected DeferredBlock<NaturalLog> strippedWood(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType) {
        return registry.register(id + "_stripped_wood", () -> new NaturalLog(Properties.of()
                .mapColor(color)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F)
                .sound(soundType)
                .ignitedByLava()
        ));
    }

    @Override
    public DeferredBlock<NaturalLog> strippedWood() {
        return this.stripped_wood;
    }

    @Override
    protected DeferredBlock<Block> planks(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType) {
        return registry.register(id + "_planks", () -> new Block(BlockBehaviour.Properties.of()
                .mapColor(color)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2.0F, 3.0F)
                .sound(soundType)
                .ignitedByLava()
        ));
    }

    @Override
    public DeferredBlock<Block> planks() {
        return this.planks;
    }

    @Override
    protected DeferredBlock<StairBlock> stairs(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType) {
        return registry.register(id + "_stairs", () -> new StairBlock(
                () -> this.planks().get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(this.planks().get())
        ));
    }

    @Override
    public DeferredBlock<StairBlock> stairs() {
        return this.stairs;
    }

    @Override
    protected DeferredBlock<SlabBlock> slab(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType) {
        return registry.register(id + "_slab", () -> new SlabBlock(
                BlockBehaviour.Properties.ofFullCopy(this.planks().get()).strength(2.0F, 3.0F)
        ));
    }

    @Override
    public DeferredBlock<SlabBlock> slab() {
        return this.slab;
    }

    @Override
    protected DeferredBlock<FenceBlock> fence(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType) {
        return registry.register(id + "_fence", () -> new FenceBlock(
                BlockBehaviour.Properties.of()
                        .mapColor(color)
                        .forceSolidOn()
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(2.0F, 3.0F)
                        .sound(soundType)
                        .ignitedByLava()
        ));
    }

    @Override
    public DeferredBlock<FenceBlock> fence() {
        return this.fence;
    }

    @Override
    protected DeferredBlock<FenceGateBlock> fenceGate(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color) {
        return registry.register(id + "_fence_gate", () -> new FenceGateBlock(
                this.woodType(),
                BlockBehaviour.Properties.of()
                        .mapColor(color)
                        .forceSolidOn()
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(2.0F, 3.0F)
                        .ignitedByLava()
        ));
    }

    @Override
    public DeferredBlock<FenceGateBlock> fenceGate() {
        return this.fence_gate;
    }

    @Override
    protected DeferredBlock<DoorBlock> door(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color) {
        return registry.register(id + "_door", () -> new DoorBlock(
                this.setType(),
                BlockBehaviour.Properties.of()
                        .mapColor(color)
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(3.0F)
                        .noOcclusion()
                        .ignitedByLava()
                        .pushReaction(PushReaction.DESTROY)
        ));
    }

    @Override
    public DeferredBlock<DoorBlock> door() {
        return this.door;
    }

    @Override
    protected DeferredBlock<TrapDoorBlock> trapDoor(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color) {
        return registry.register(id + "_trapdoor", () -> new TrapDoorBlock(
                this.setType(),
                BlockBehaviour.Properties.of()
                        .mapColor(color)
                        .instrument(NoteBlockInstrument.BASS)
                        .strength(3.0F)
                        .noOcclusion()
                        .isValidSpawn((state, level, pos, entity) -> false)
                        .ignitedByLava()
        ));
    }

    @Override
    public DeferredBlock<TrapDoorBlock> trapDoor() {
        return this.trapdoor;
    }

    @Override
    protected DeferredBlock<PressurePlateBlock> pressurePlate(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color) {
        return registry.register(id + "_pressure_plate", () -> new PressurePlateBlock(
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
    }

    @Override
    public DeferredBlock<PressurePlateBlock> pressurePlate() {
        return this.pressure_plate;
    }

    @Override
    protected DeferredBlock<ButtonBlock> button(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color) {
        return registry.register(id + "_button", () -> new ButtonBlock(
                this.setType(),
                30,
                BlockBehaviour.Properties.of()
                        .noCollission()
                        .strength(0.5F)
                        .pushReaction(PushReaction.DESTROY)
        ));
    }

    @Override
    public DeferredBlock<ButtonBlock> button() {
        return this.button;
    }

    @Override
    protected DeferredBlock<ZenithSignBlock> sign(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType) {
        return registry.register(id + "_sign", () -> new ZenithSignBlock(
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
        return registry.register(id + "_hanging_sign", () -> new ZenithCeilingHangingSignBlock(
                this.woodType(),
                this.hangingSignEntity()::get,
                Block.Properties.of()
                        .mapColor(color)
                        .forceSolidOn().ignitedByLava()
                        .instrument(NoteBlockInstrument.BASS)
                        .noCollission().strength(1.0F)
                        .sound(soundType)
        ));
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
        )
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
    public void blockGen(BlockStateProvider data) {

    }

    @Override
    public void itemGen(ItemModelProvider data) {

    }

    @Override
    public void langGen(LanguageProvider data) {

    }

    @Override
    public void recipeGen(RecipeProvider data) {

    }

    @Override
    public void blockTagData(BlockTagsProvider data) {

    }

    @Override
    public void lootData(BlockLootSubProvider data) {

    }

    @Override
    public void mapData(DataMapProvider data) {

    }
}
