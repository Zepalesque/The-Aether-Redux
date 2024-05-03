package net.zepalesque.redux.api.blockhandler;

import com.aetherteam.aether.block.construction.BookshelfBlock;
import com.aetherteam.aether.block.natural.AetherLogBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.construction.LayeredBookshelfBlock;
import net.zepalesque.redux.block.natural.ReduxNaturalLog;
import net.zepalesque.redux.block.natural.ReduxNaturalWall;
import net.zepalesque.redux.block.sign.ReduxCeilingHangingSignBlock;
import net.zepalesque.redux.block.sign.ReduxSignBlock;
import net.zepalesque.redux.block.sign.ReduxWallHangingSignBlock;
import net.zepalesque.redux.block.sign.ReduxWallSignBlock;
import net.zepalesque.redux.blockentity.ReduxBlockEntityTypes;
import net.zepalesque.redux.blockentity.ReduxHangingSignBlockEntity;
import net.zepalesque.redux.blockentity.ReduxSignBlockEntity;
import net.zepalesque.redux.data.ReduxBlockstateData;
import net.zepalesque.redux.data.ReduxItemModelData;
import net.zepalesque.redux.data.ReduxLanguageData;
import net.zepalesque.redux.entity.ReduxEntityTypes;
import net.zepalesque.redux.entity.misc.ReduxBoat;
import net.zepalesque.redux.entity.misc.ReduxChestBoat;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.item.misc.ReduxBoatItem;
import net.zepalesque.redux.misc.ReduxTags;
import org.codehaus.plexus.util.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiFunction;

public class WoodHandler implements BlockHandler {
    public final TagKey<Item> logsTag;
    public final TagKey<Block> logsBlockTag;

    public final Optional<TagKey<Block>> sporingBlocksBlockTag;

    public final RegistryObject<Block> planks;
    public final RegistryObject<RotatedPillarBlock> log;
    public final RegistryObject<RotatedPillarBlock> wood;
    public final Optional<RegistryObject<RotatedPillarBlock>> strippedLog;
    public final Optional<RegistryObject<RotatedPillarBlock>> strippedWood;
    public final RegistryObject<StairBlock> stairs;
    public final RegistryObject<SlabBlock> slab;
    public final RegistryObject<FenceBlock> fence;
    public final RegistryObject<FenceGateBlock> fenceGate;
    public final RegistryObject<DoorBlock> door;
    public final RegistryObject<TrapDoorBlock> trapdoor;
    public final RegistryObject<PressurePlateBlock> pressurePlate;
    public final RegistryObject<ButtonBlock> button;
    public final RegistryObject<BookshelfBlock> bookshelf;
    public final RegistryObject<StandingSignBlock> sign;
    public final RegistryObject<WallSignBlock> wallSign;
    public final RegistryObject<WallHangingSignBlock> wallHangingSign;
    public final RegistryObject<CeilingHangingSignBlock> hangingSign;
    public final RegistryObject<SignItem> signItem;
    public final RegistryObject<HangingSignItem> hangingSignItem;
    public final RegistryObject<ReduxBoatItem> boatItem;
    public final RegistryObject<ReduxBoatItem> chestBoatItem;

    public final RegistryObject<WallBlock> logWall;
    public final RegistryObject<WallBlock> woodWall;
    public final Optional<RegistryObject<WallBlock>> strippedLogWall;
    public final Optional<RegistryObject<WallBlock>> strippedWoodWall;


    public final RegistryObject<EntityType<ReduxBoat>> boatEntity;
    public final RegistryObject<EntityType<ReduxChestBoat>> chestBoatEntity;
    public final RegistryObject<BlockEntityType<ReduxSignBlockEntity>> signEntity;
    public final RegistryObject<BlockEntityType<ReduxHangingSignBlockEntity>> hangingSignEntity;
    public final WoodType woodType;
    public final BlockSetType blockSet;

    public final String woodName;

    @Nullable
    public final String langName;

    public final boolean alwaysLogWalls;
    public final SoundType plankSoundType;
    public final SoundType logSoundType;
    public final String logSuffix;
    public final String woodSuffix;

    public final boolean hasSporingLogs;

    public final boolean hasStrippedLogs;

    public final boolean layeredBookshelf;

    public final Optional<RegistryObject<RotatedPillarBlock>> sporingLog;
    public final Optional<RegistryObject<RotatedPillarBlock>> sporingWood;

    public final String treeName;


    public static BiFunction<String, SoundType, BlockSetType> createBlockSet(SoundEvent doorClose, SoundEvent doorOpen, SoundEvent trapdoorClose, SoundEvent trapdoorOpen, SoundEvent pressurePlateClickOff, SoundEvent pressurePlateClickOn, SoundEvent buttonClickOff, SoundEvent buttonClickOn)
    {
        return ((name, soundType) -> new BlockSetType(name, true, soundType, doorClose, doorOpen, trapdoorClose, trapdoorOpen, pressurePlateClickOff, pressurePlateClickOn, buttonClickOff, buttonClickOn));
    }

    public static BiFunction<String, SoundType, BlockSetType> bambooSoundBlockSet()
    {
        return createBlockSet(
                SoundEvents.BAMBOO_WOOD_DOOR_CLOSE,
                SoundEvents.BAMBOO_WOOD_DOOR_OPEN,
                SoundEvents.BAMBOO_WOOD_TRAPDOOR_CLOSE,
                SoundEvents.BAMBOO_WOOD_TRAPDOOR_OPEN,
                SoundEvents.BAMBOO_WOOD_PRESSURE_PLATE_CLICK_OFF,
                SoundEvents.BAMBOO_WOOD_PRESSURE_PLATE_CLICK_ON,
                SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_OFF,
                SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_ON
        );
    }
    public static BiFunction<String, SoundType, BlockSetType> cherrySoundBlockSet()
    {
        return createBlockSet(
                SoundEvents.CHERRY_WOOD_DOOR_CLOSE,
                SoundEvents.CHERRY_WOOD_DOOR_OPEN,
                SoundEvents.CHERRY_WOOD_TRAPDOOR_CLOSE,
                SoundEvents.CHERRY_WOOD_TRAPDOOR_OPEN,
                SoundEvents.CHERRY_WOOD_PRESSURE_PLATE_CLICK_OFF,
                SoundEvents.CHERRY_WOOD_PRESSURE_PLATE_CLICK_ON,
                SoundEvents.CHERRY_WOOD_BUTTON_CLICK_OFF,
                SoundEvents.CHERRY_WOOD_BUTTON_CLICK_ON
        );
    }
    public static BiFunction<String, SoundType, BlockSetType> netherSoundBlockSet()
    {
        return createBlockSet(
                SoundEvents.NETHER_WOOD_DOOR_CLOSE,
                SoundEvents.NETHER_WOOD_DOOR_OPEN,
                SoundEvents.NETHER_WOOD_TRAPDOOR_CLOSE,
                SoundEvents.NETHER_WOOD_TRAPDOOR_OPEN,
                SoundEvents.NETHER_WOOD_PRESSURE_PLATE_CLICK_OFF,
                SoundEvents.NETHER_WOOD_PRESSURE_PLATE_CLICK_ON,
                SoundEvents.NETHER_WOOD_BUTTON_CLICK_OFF,
                SoundEvents.NETHER_WOOD_BUTTON_CLICK_ON
        );
    }
    public static BiFunction<String, SoundType, BlockSetType> woodSoundBlockSet()
    {
        return createBlockSet(
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


    /** Constructor for most wood types
     */
    public static WoodHandler tree(String pWoodname, boolean pLogWalls, MapColor pBarkColor, MapColor pWoodColor, boolean layeredBookshelf)
    {
        return handler(pWoodname, null, true, woodSoundBlockSet(), "trees", "log", "wood", SoundType.WOOD, SoundType.WOOD, pLogWalls, pBarkColor, pWoodColor, false, layeredBookshelf);
    }
    /** Constructor for fungus wood types
     */
    public static WoodHandler fungus(String pWoodname, boolean pLogWalls, MapColor pBarkColor, MapColor pWoodColor, boolean layeredBookshelf)
    {
        return handler(pWoodname, null, true, netherSoundBlockSet(), "mushrooms", "stem", "hyphae", SoundType.NETHER_WOOD, SoundType.STEM, pLogWalls, pBarkColor, pWoodColor, false, layeredBookshelf);
    }
    /** Constructor for fungus wood types
     */
    public static WoodHandler noStrippingFungus(String pWoodname, boolean pLogWalls, MapColor pBarkColor, MapColor pWoodColor, boolean layeredBookshelf)
    {
        return handler(pWoodname, null, false, netherSoundBlockSet(), "mushrooms", "stem", "hyphae", SoundType.NETHER_WOOD, SoundType.STEM, pLogWalls, pBarkColor, pWoodColor, false, layeredBookshelf);
    }
    /** More general constructor
     */
    public static WoodHandler handler(String pWoodName, @Nullable String pLangName, boolean pStrippedLog, BiFunction<String, SoundType, BlockSetType> blockSetTypeFunction, String pTreeName, String pLogSuffix, String pWoodSuffix, SoundType pPlankSoundType, SoundType pLogSoundType, boolean pLogWalls, MapColor barkColor, MapColor woodColor, boolean hasSporingLogs, boolean layeredBookshelf)
    {

        WoodHandler instance = new WoodHandler(pWoodName, pLangName, pStrippedLog, blockSetTypeFunction, pTreeName, pLogSuffix, pWoodSuffix, pPlankSoundType, pLogSoundType, pLogWalls, barkColor, woodColor, hasSporingLogs, layeredBookshelf);
        return instance;
    }

    protected WoodHandler(String pWoodName, @Nullable String pLangName, boolean pStrippedLogs, BiFunction<String, SoundType, BlockSetType> blockSetTypeFunction, String pTreeName,  String pLogSuffix, String pWoodSuffix, SoundType pPlankSoundType, SoundType pLogSoundType, boolean pLogWalls, MapColor barkColor, MapColor woodColor, boolean pSporingLogs, boolean layeredBookshelf) {
        this.hasSporingLogs = pSporingLogs;
        this.hasStrippedLogs = pStrippedLogs;
        this.treeName = pTreeName;
        if (pSporingLogs)
        {
            RegistryObject<RotatedPillarBlock> sporingLogRegistry = ReduxBlocks.register("sporing_" + pWoodName + "_" + pLogSuffix, () -> new AetherLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).sound(pLogSoundType).mapColor(barkColor)));
            this.sporingLog = Optional.of(sporingLogRegistry);
            RegistryObject<RotatedPillarBlock> sporingWoodRegistry = ReduxBlocks.register("sporing_" + pWoodName + "_" + pWoodSuffix, () -> new AetherLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).sound(pLogSoundType).mapColor(barkColor)));
            this.sporingWood = Optional.of(sporingWoodRegistry);
            this.sporingBlocksBlockTag = Optional.of(ReduxTags.Blocks.tag("sporing_" + pWoodName + "_" + pLogSuffix + "s"));
        } else {
            this.sporingLog = Optional.empty();
            this.sporingWood = Optional.empty();
            this.sporingBlocksBlockTag = Optional.empty();
        }
        this.logSuffix = pLogSuffix;
        this.woodSuffix = pWoodSuffix;
        this.alwaysLogWalls = pLogWalls;
        this.plankSoundType = pPlankSoundType;
        this.logSoundType = pLogSoundType;
        this.logsTag = ReduxTags.Items.tag(pWoodName+"_"+pLogSuffix+"s");
        this.logsBlockTag = ReduxTags.Blocks.tag(pWoodName+"_"+pLogSuffix+"s");
        this.woodName = pWoodName;
        this.langName = pLangName;

        this.bookshelf = ReduxBlocks.register(pWoodName + "_bookshelf", layeredBookshelf ? () -> new LayeredBookshelfBlock(BlockBehaviour.Properties.copy(Blocks.BOOKSHELF).mapColor(woodColor).sound(pPlankSoundType)) : () -> new BookshelfBlock(BlockBehaviour.Properties.copy(Blocks.BOOKSHELF).mapColor(woodColor).sound(pPlankSoundType)));
        this.layeredBookshelf = layeredBookshelf;

        this.blockSet = blockSetTypeFunction.apply(Redux.MODID + ":" + pWoodName, pPlankSoundType);
        this.woodType = new WoodType(Redux.MODID + ":" + pWoodName, this.blockSet);
        this.signEntity = ReduxBlockEntityTypes.BLOCK_ENTITY_TYPES.register(pWoodName + "_sign", () -> BlockEntityType.Builder.of(((pPos, pState) -> new ReduxSignBlockEntity(pPos, pState, this.getSign().get())), this.getSignBlock().get(), this.getWallSignBlock().get()).build(null));
        this.hangingSignEntity = ReduxBlockEntityTypes.BLOCK_ENTITY_TYPES.register(pWoodName + "_hanging_sign", () -> BlockEntityType.Builder.of(((pPos, pState) -> new ReduxHangingSignBlockEntity(pPos, pState, this.getHangingSign().get())), this.getHangingSignBlock().get(), this.getWallHangingSignBlock().get()).build(null));
        this.boatEntity = ReduxEntityTypes.ENTITY_TYPES.register(pWoodName + "_boat",
                () -> EntityType.Builder.<ReduxBoat>of(ReduxBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build(pWoodName + "_boat"));
        this.chestBoatEntity = ReduxEntityTypes.ENTITY_TYPES.register(pWoodName + "_chest_boat",
                () -> EntityType.Builder.<ReduxChestBoat>of(ReduxChestBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build(pWoodName + "_chest_boat"));
        this.log = ReduxBlocks.register(pWoodName + "_" + pLogSuffix, () -> new AetherLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).sound(pLogSoundType).mapColor(barkColor)));
        this.wood = ReduxBlocks.register(pWoodName + "_" + pWoodSuffix, () -> new ReduxNaturalLog(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).sound(pLogSoundType).mapColor(barkColor)));
        if (this.hasStrippedLogs)
        {
            RegistryObject<RotatedPillarBlock> strippedLog = ReduxBlocks.register("stripped_" + pWoodName + "_" + pLogSuffix, () -> new ReduxNaturalLog(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).sound(pLogSoundType).mapColor(woodColor)));
            this.strippedLog = Optional.of(strippedLog);
            RegistryObject<RotatedPillarBlock> strippedWood = ReduxBlocks.register("stripped_" + pWoodName + "_" + pWoodSuffix, () -> new ReduxNaturalLog(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).sound(pLogSoundType).mapColor(woodColor)));
            this.strippedWood = Optional.of(strippedWood);
            RegistryObject<WallBlock> strippedLogWall = ReduxBlocks.register("stripped_" + woodName + "_"+pLogSuffix+"_wall", () -> new ReduxNaturalWall(Block.Properties.of().mapColor(woodColor).strength(2.0F).sound(pLogSoundType)));
            RegistryObject<WallBlock> strippedWoodWall = ReduxBlocks.register("stripped_" + woodName + "_"+pWoodSuffix+"_wall", () -> new ReduxNaturalWall(Block.Properties.of().mapColor(woodColor).strength(2.0F).sound(pLogSoundType)));

            this.strippedLogWall = Optional.of(strippedLogWall);
            this.strippedWoodWall = Optional.of(strippedWoodWall);
        }
        else {
            this.strippedLog = Optional.empty();
            this.strippedWood = Optional.empty();
            this.strippedLogWall = Optional.empty();
            this.strippedWoodWall = Optional.empty();
        }
        this.planks = ReduxBlocks.register(pWoodName + "_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(pPlankSoundType).mapColor(woodColor)));
        this.stairs = ReduxBlocks.register(pWoodName + "_stairs", () -> new StairBlock(() -> this.planks.get().defaultBlockState(), BlockBehaviour.Properties.copy(this.planks.get())));
        this.slab = ReduxBlocks.register(pWoodName + "_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(this.planks.get()).strength(2.0F, 3.0F)));
        this.fence = ReduxBlocks.register(pWoodName + "_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE).sound(pPlankSoundType).mapColor(woodColor)));
        this.fenceGate = ReduxBlocks.register(pWoodName + "_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE).sound(pPlankSoundType).mapColor(woodColor), this.woodType));
        this.door = ReduxBlocks.register(pWoodName + "_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).sound(pPlankSoundType).mapColor(woodColor), this.blockSet));
        this.trapdoor = ReduxBlocks.register(pWoodName + "_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR).sound(pPlankSoundType).mapColor(woodColor), this.blockSet));
        this.pressurePlate = ReduxBlocks.register(pWoodName + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE).sound(pPlankSoundType).mapColor(woodColor), this.blockSet));
        this.button = ReduxBlocks.register(pWoodName + "_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).sound(pPlankSoundType).mapColor(woodColor), this.blockSet, 30, true));
        this.sign = ReduxBlocks.BLOCKS.register(pWoodName + "_sign", () -> new ReduxSignBlock(Block.Properties.of().mapColor(woodColor).noCollission().strength(1.0F).sound(pPlankSoundType), woodType) {
            @Override
            public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
                return new ReduxSignBlockEntity(pos, state, getSign().get());
            }
        });
        this.wallSign = ReduxBlocks.BLOCKS.register(pWoodName + "_wall_sign", () -> new ReduxWallSignBlock(Block.Properties.of().mapColor(woodColor).noCollission().strength(1.0F).sound(SoundType.WOOD), woodType) {
            @Override
            public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
                return new ReduxSignBlockEntity(pos, state, getSign().get());
            }
        });

        this.hangingSign = ReduxBlocks.BLOCKS.register(pWoodName + "_hanging_sign", () -> new ReduxCeilingHangingSignBlock(Block.Properties.of().mapColor(woodColor).noCollission().strength(1.0F).sound(pPlankSoundType), woodType) {
            @Override
            public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
                return new ReduxHangingSignBlockEntity(pos, state, getHangingSign().get());
            }
        });
        this.wallHangingSign = ReduxBlocks.BLOCKS.register(pWoodName + "_wall_hanging_sign", () -> new ReduxWallHangingSignBlock(Block.Properties.of().mapColor(woodColor).noCollission().strength(1.0F).sound(SoundType.WOOD), woodType) {
            @Override
            public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
                return new ReduxHangingSignBlockEntity(pos, state, getHangingSign().get());
            }
        });


        this.boatItem = ReduxItems.ITEMS.register(pWoodName + "_boat", () -> new ReduxBoatItem(false, (new Item.Properties()).stacksTo(1),
                ((level, hitresult) -> new ReduxBoat(this, level, hitresult.getLocation().x, hitresult.getLocation().y, hitresult.getLocation().z) {
                })));
        this.chestBoatItem = ReduxItems.ITEMS.register(pWoodName + "_chest_boat", () -> new ReduxBoatItem(false, (new Item.Properties()).stacksTo(1),
                ((level, hitresult)  -> new ReduxChestBoat(this, level, hitresult.getLocation().x, hitresult.getLocation().y, hitresult.getLocation().z) {
                })));

        this.signItem = ReduxItems.register(pWoodName + "_sign", () -> new SignItem((new Item.Properties()).stacksTo(16), this.sign.get(), this.wallSign.get()));
        this.hangingSignItem = ReduxItems.register(pWoodName + "_hanging_sign", () -> new HangingSignItem(this.hangingSign.get(), this.wallHangingSign.get(), (new Item.Properties()).stacksTo(16)));

        this.logWall = ReduxBlocks.register(woodName + "_"+pLogSuffix+"_wall", () -> new ReduxNaturalWall(Block.Properties.of().mapColor(barkColor).strength(2.0F).sound(pLogSoundType)));
        this.woodWall = ReduxBlocks.register(woodName + "_"+pWoodSuffix+"_wall", () -> new ReduxNaturalWall(Block.Properties.of().mapColor(barkColor).strength(2.0F).sound(pLogSoundType)));
    }

    private RegistryObject<BlockEntityType<ReduxSignBlockEntity>> getSign()
    {
        return this.signEntity;
    }
    private RegistryObject<StandingSignBlock> getSignBlock()
    {
        return this.sign;
    }
    private RegistryObject<WallSignBlock> getWallSignBlock()
    {
        return this.wallSign;
    }

    private RegistryObject<BlockEntityType<ReduxHangingSignBlockEntity>> getHangingSign()
    {
        return this.hangingSignEntity;
    }
    private RegistryObject<CeilingHangingSignBlock> getHangingSignBlock()
    {
        return this.hangingSign;
    }
    private RegistryObject<WallHangingSignBlock> getWallHangingSignBlock()
    {
        return this.wallHangingSign;
    }


    public void generateBlockstateData(ReduxBlockstateData data)
    {
        data.log(this.log.get());
        this.strippedLog.ifPresent((reg) -> data.log(reg.get()));
        data.block(this.planks.get(), "construction/");
        data.wood(this.wood.get(), this.log.get());
        this.strippedWood.ifPresent((reg) -> data.wood(reg.get(), this.strippedLog.get().get()));
        data.stairs(this.stairs.get(), this.planks.get(), "construction/");
        data.slab(this.slab.get(), this.planks.get(), "construction/");
        data.fence(this.fence.get(), this.planks.get(), "construction/");
        data.fenceGateBlock(this.fenceGate.get(), this.planks.get(), "construction/");
        data.doorBlock(this.door.get(), data.texture(data.name(this.door.get()), "construction/", "_bottom"), data.texture(data.name(this.door.get()), "construction/", "_top"));
        data.trapdoorBlock(this.trapdoor.get(), data.texture(data.name(this.trapdoor.get()), "construction/"), true);
        data.pressurePlateBlock(this.pressurePlate.get(), data.texture(data.name(this.planks.get()), "construction/"));
        data.buttonBlock(this.button.get(), data.texture(data.name(this.planks.get()), "construction/"));
        data.signBlock(this.sign.get(), this.wallSign.get(), data.texture(data.name(this.planks.get()), "construction/"));
        data.hangingSignBlock(this.hangingSign.get(), this.wallHangingSign.get(), data.texture(data.name(this.planks.get()), "construction/"));

        if (this.layeredBookshelf) {
            data.layeredBookshelf(this.bookshelf.get(), this.planks.get());
        } else {
            data.bookshelf(this.bookshelf.get(), this.planks.get());
        }

        ModelFile postBig = data.makeWallPostModel(4, 16, "wooden_post_big");
        ModelFile postShort = data.makeWallPostModel(3, 14, "wooden_post_short");
        ModelFile postTall = data.makeWallPostModel(3, 16, "wooden_post_tall");
        ModelFile side = data.makeWallSideModel(5, 14, "wooden_side", ModelBuilder.FaceRotation.CLOCKWISE_90, 0, 5);
        ModelFile sideAlt = data.makeWallSideModel(5, 14, "wooden_side_alt", ModelBuilder.FaceRotation.COUNTERCLOCKWISE_90, 11, 16);
        ModelFile sideTall = data.makeWallSideModel(5, 16, "wooden_side_tall", ModelBuilder.FaceRotation.CLOCKWISE_90, 0, 5);
        ModelFile sideTallAlt = data.makeWallSideModel(5, 16, "wooden_side_tall_alt", ModelBuilder.FaceRotation.COUNTERCLOCKWISE_90, 11, 16);
        ModelFile sideShort = data.makeWallSideModel(4, 14, "wooden_side_short", ModelBuilder.FaceRotation.CLOCKWISE_90, 0, 4);
        ModelFile sideAltShort = data.makeWallSideModel(4, 14, "wooden_side_alt_short", ModelBuilder.FaceRotation.COUNTERCLOCKWISE_90, 12, 16);
        ModelFile sideTallShort = data.makeWallSideModel(4, 16, "wooden_side_tall_short", ModelBuilder.FaceRotation.CLOCKWISE_90, 0, 4);
        ModelFile sideTallAltShort = data.makeWallSideModel(4, 16, "wooden_side_tall_alt_short", ModelBuilder.FaceRotation.COUNTERCLOCKWISE_90, 12, 16);
        data.logWallBlock(this.logWall.get(), this.log.get(), "natural/", this.log.getId().getNamespace(), true, postBig, postShort, postTall, side, sideAlt, sideTall, sideTallAlt, sideShort, sideAltShort, sideTallShort, sideTallAltShort);
        this.strippedLogWall.ifPresent((reg)->data.logWallBlock(reg.get(), this.strippedLog.get().get(), "natural/", reg.getId().getNamespace(), true, postBig, postShort, postTall, side, sideAlt, sideTall, sideTallAlt, sideShort, sideAltShort, sideTallShort, sideTallAltShort));
        data.logWallBlock(this.woodWall.get(), this.log.get(), "natural/", this.log.getId().getNamespace(), false, postBig, postShort, postTall, side, sideAlt, sideTall, sideTallAlt, sideShort, sideAltShort, sideTallShort, sideTallAltShort);
        this.strippedWoodWall.ifPresent((reg)->data.logWallBlock(reg.get(), this.strippedLog.get().get(), "natural/", reg.getId().getNamespace(), false, postBig, postShort, postTall, side, sideAlt, sideTall, sideTallAlt, sideShort, sideAltShort, sideTallShort, sideTallAltShort));
        this.sporingLog.ifPresent((sLog) -> {
            data.enchantedLogGlow(sLog.get(), this.log.get());
            this.sporingWood.ifPresent((sWood) -> data.woodGlow(sWood.get(), sLog.get()));
        });


    }

    public void generateItemModels(ReduxItemModelData data)
    {
        data.itemBlock(this.log);
        this.strippedLog.ifPresent((reg) -> data.itemBlock(reg.get()));
        data.itemBlock(this.planks);
        this.strippedWood.ifPresent((reg) -> data.itemBlock(reg.get()));
        data.itemBlock(this.wood);
        data.itemBlock(this.stairs);
        data.itemBlock(this.slab);
        data.itemBlock(this.bookshelf);
        data.itemBlock(this.trapdoor, "_bottom");
        data.itemFence(this.fence.get(), this.planks.get(), "construction/");
        data.itemBlock(this.fenceGate);
        data.item(this.door.get().asItem(), "misc/");
        data.itemBlock(this.pressurePlate);
        data.itemButton(this.button.get(), this.planks.get(), "construction/");
        data.item(this.signItem.get().asItem(), "misc/");
        data.item(this.hangingSignItem.get().asItem(), "misc/");
        data.item(this.boatItem.get().asItem(), "misc/");
        data.item(this.chestBoatItem.get().asItem(), "misc/");
        data.itemLogWallBlock(this.logWall.get(), this.log.get(), "natural/", Redux.MODID);
        this.strippedLogWall.ifPresent((reg) -> data.itemLogWallBlock(reg.get(), this.strippedLog.get().get(), "natural/", Redux.MODID));
        data.itemWoodWallBlock(this.woodWall.get(), this.log.get(), "natural/", Redux.MODID);
        this.strippedWoodWall.ifPresent((reg) -> data.itemWoodWallBlock(reg.get(), this.strippedLog.get().get(), "natural/", Redux.MODID));
        this.sporingLog.ifPresent(data::itemBlock);
        this.sporingWood.ifPresent(data::itemBlock);

    }


    public void generateLanguageData(ReduxLanguageData data)
    {

        boolean useAn = BlockHandler.isVowel(this.woodName.charAt(0));


        this.sporingLog.ifPresent((block) -> {
            data.addBlock(block, "Sporing " + getLocalizedName() + " " + StringUtils.capitalise(this.logSuffix));
            data.addLore(block,"This variant of the " + getLocalizedName() + " " + StringUtils.capitalise(this.logSuffix) + " has spores on it, which it drops when mined with a Zanite Axe or better.");
        });
        this.sporingWood.ifPresent((block) -> {
            data.addBlock(block, "Sporing " + getLocalizedName() + " " + StringUtils.capitalise(this.woodSuffix));
            data.addLore(block,"This variant of the " + getLocalizedName() + " " + StringUtils.capitalise(this.woodSuffix) + " has spores on it, which it drops when mined with a Zanite Axe or better.");
        });


        data.addItem(this.boatItem, getLocalizedName() + " Boat");
        data.addLore(this.boatItem, "Crafted from " + getLocalizedName() + " Planks. While the Aether does not have many large bodies of water, a boat can occasionally be a useful tool for crossing large distances over ice!");
        data.addItem(this.chestBoatItem, getLocalizedName() + " Boat with Chest");
        data.addLore(this.chestBoatItem, useAn ? "An " : "A " + getLocalizedName() + " Boat with a handy chest in the back. Helpful for transporting more items over long stretches of water, which are famously difficult to find in the Aether.");


       data.addBlock(this.logWall, getLocalizedName() + " "+ StringUtils.capitalise(this.logSuffix) +" Wall");
        data.addLore(this.logWall, "Crafted from " + getLocalizedName() + " "+ StringUtils.capitalise(this.logSuffix) +"s. Can be used for decorative enclosures and defences. Great for keeping nasty intruders away!");
       this.strippedLogWall.ifPresent((reg) ->
       {
           data.addBlock(reg, "Stripped " + getLocalizedName() + " "+ StringUtils.capitalise(this.logSuffix) +" Wall");
           data.addLore(reg, "Crafted from Stripped " + getLocalizedName() + " "+ StringUtils.capitalise(this.logSuffix) +"s. Can be used for decorative enclosures and defences. Great for keeping nasty intruders away!");
       });
       data.addBlock(this.woodWall, getLocalizedName() + " "+ StringUtils.capitalise(this.woodSuffix) +" Wall");
        data.addLore(this.woodWall, "Crafted from " + getLocalizedName() + " "+ StringUtils.capitalise(this.woodSuffix) +". Can be used for decorative enclosures and defences. Great for keeping nasty intruders away!");
        this.strippedWoodWall.ifPresent((reg) ->
        {
            data.addBlock(reg, "Stripped " + getLocalizedName() + " "+ StringUtils.capitalise(this.woodSuffix) +" Wall");
            data.addLore(reg, "Crafted from Stripped " + getLocalizedName() + " "+ StringUtils.capitalise(this.woodSuffix) +". Can be used for decorative enclosures and defences. Great for keeping nasty intruders away!");
        });

       data.addBlock(this.log, getLocalizedName() + " " + StringUtils.capitalise(this.logSuffix));
        data.addLore(this.log, "These spawn with " + getLocalizedName() + " " + StringUtils.capitalise(this.treeName) + ". They can be double dropped with Skyroot Axes. When put in a crafting table they will provide 4 " + getLocalizedName() + " Planks.");

        this.strippedLog.ifPresent((reg) -> {
            data.addBlock(reg, "Stripped " + getLocalizedName() + " " + StringUtils.capitalise(this.logSuffix));
            data.addLore(reg, useAn ? "An " : "A " + getLocalizedName() + " " + StringUtils.capitalise(this.logSuffix) + " that has had its bark stripped away with an Axe. When put in a crafting table they will provide 4 " + getLocalizedName() + " Planks.");
        });
        data.addBlock(this.planks, getLocalizedName() + " Planks");
        data.addLore(this.planks, "Planks from the " + getLocalizedName() + " tree. Can be used as a building material, along with several other useful things.");
       data.addBlock(this.wood, getLocalizedName() + " " + StringUtils.capitalise(this.woodSuffix));
        data.addLore(this.wood, "Six-sided variant of " + getLocalizedName() + " "+ StringUtils.capitalise(this.logSuffix) +"s. When put in a crafting table they will provide 4 " + getLocalizedName() + " Planks.");
        this.strippedWood.ifPresent((reg) -> {
            data.addBlock(reg, "Stripped " + getLocalizedName() + " "+ StringUtils.capitalise(this.woodSuffix));
            data.addLore(reg, getLocalizedName() + " "+ StringUtils.capitalise(this.woodSuffix) +" that has had its bark stripped away with an Axe. When put in a crafting table they will provide 4 " + getLocalizedName() + " Planks.");
        });
        data.addBlock(this.stairs, getLocalizedName() + " Stairs");
        data.addLore(this.stairs, "Crafted from " + getLocalizedName() + " Planks. Stairs are useful for adding verticality to builds and are often used for decoration too!");
       data.addBlock(this.slab, getLocalizedName() + " Slab");
        data.addLore(this.slab, "Crafted from " + getLocalizedName() + " Planks. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building's roofing!");
       data.addBlock(this.fence, getLocalizedName() + " Fence");
        data.addLore(this.fence, "Crafted from " + getLocalizedName() + " Planks and Skyroot Sticks. Fences are great for keeping your livestock safe from wandering predators!");
       data.addBlock(this.fenceGate, getLocalizedName() + " Fence Gate");
        data.addLore(this.fenceGate, "Crafted from " + getLocalizedName() + " Planks and Skyroot Sticks. Fence gates give a homely entrance and exit to your precious enclosures.");
       data.addBlock(this.door, getLocalizedName() + " Door");
        data.addLore(this.door, "Crafted from " + getLocalizedName() +
                (this == Redux.WoodHandlers.CLOUDCAP ? " Planks. Some have said that these doors cause places to almost feel more spacious inside than out." : " Planks. Doors are an ornate entrance helpful for keeping an enclosed and safe space without worry of monsters wandering in!")
        );
       data.addBlock(this.trapdoor, getLocalizedName() + " Trapdoor");
        data.addLore(this.trapdoor, "Crafted from "+getLocalizedName()+" Planks. Trapdoors are useful for covering entryways one block wide. They are often used to add extra protection to staircases.");
       data.addBlock(this.pressurePlate, getLocalizedName()+" Pressure Plate");
        data.addLore(this.pressurePlate, "Crafted from "+getLocalizedName()+" Planks. A wooden pressure plate used to activate mechanisms and redstone.");
       data.addBlock(this.button, getLocalizedName() + " Button");
        data.addLore(this.button, "Crafted from "+getLocalizedName()+" Planks, a button used to activate mechanisms and redstone.");
       data.addBlock(this.sign, getLocalizedName() + " Sign");
        data.addLore(this.sign, "Crafted from "+getLocalizedName()+" Planks. A helpful sign perfect for writing messages and directions on.");
       data.addBlock(this.hangingSign, getLocalizedName() + " Hanging Sign");
        data.addLore(this.hangingSign, "Crafted from "+getLocalizedName()+" Planks. A helpful hanging sign perfect for writing messages and directions on.");
        data.addBlock(this.bookshelf, getLocalizedName() + " Bookshelf");
        data.addLore(this.bookshelf, "A nice bookshelf made of " + getLocalizedName() + " wood. These are nice for decoration, and also will enhance the abilities of Enchanting Tables!");

        data.addEntityType(this.boatEntity, getLocalizedName() + " Boat");
        data.addEntityType(this.chestBoatEntity, getLocalizedName() + " Boat with Chest");

    }

    private String getLocalizedName()
    {
        return StringUtils.capitalise(this.langName == null ? this.woodName : this.langName);
    }
}



