package net.zepalesque.redux.api.blockhandler;

import com.aetherteam.aether.block.construction.BookshelfBlock;
import com.aetherteam.aether.block.natural.AetherLogBlock;
import com.aetherteam.aether.item.AetherCreativeTabs;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.blockhandlers.WoodHandlers;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.construction.LayeredBookshelfBlock;
import net.zepalesque.redux.block.natural.ReduxNaturalLog;
import net.zepalesque.redux.block.natural.ReduxNaturalWall;
import net.zepalesque.redux.block.sign.ReduxSignBlock;
import net.zepalesque.redux.block.sign.ReduxWallSignBlock;
import net.zepalesque.redux.blockentity.ReduxBlockEntityTypes;
import net.zepalesque.redux.blockentity.ReduxSignBlockEntity;
import net.zepalesque.redux.entity.ReduxEntityTypes;
import net.zepalesque.redux.entity.misc.ReduxBoat;
import net.zepalesque.redux.entity.misc.ReduxChestBoat;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.item.misc.ReduxBoatItem;
import net.zepalesque.redux.misc.ReduxTags;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

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
    public final RegistryObject<SignItem> signItem;
    public final RegistryObject<ReduxBoatItem> boatItem;
    public final RegistryObject<ReduxBoatItem> chestBoatItem;

    public final RegistryObject<WallBlock> logWall;
    public final RegistryObject<WallBlock> woodWall;
    public final Optional<RegistryObject<WallBlock>> strippedLogWall;
    public final Optional<RegistryObject<WallBlock>> strippedWoodWall;


    public final RegistryObject<EntityType<ReduxBoat>> boatEntity;
    public final RegistryObject<EntityType<ReduxChestBoat>> chestBoatEntity;
    public final RegistryObject<BlockEntityType<ReduxSignBlockEntity>> signEntity;
    public final WoodType woodType;

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



    /** Constructor for most wood types
     */
    public static WoodHandler tree(String pWoodname, boolean pLogWalls, MaterialColor pBarkColor, MaterialColor pWoodColor, boolean layeredBookshelf)
    {
        return handler(pWoodname, null, true, "trees", "log", "wood", SoundType.WOOD, SoundType.WOOD, pLogWalls, pBarkColor, pWoodColor, false, layeredBookshelf);
    }
    /** Constructor for fungus wood types
     */
    public static WoodHandler fungus(String pWoodname, boolean pLogWalls, MaterialColor pBarkColor, MaterialColor pWoodColor, boolean layeredBookshelf)
    {
        return handler(pWoodname, null, true, "mushrooms", "stem", "hyphae", SoundType.WOOD, SoundType.STEM, pLogWalls, pBarkColor, pWoodColor, false, layeredBookshelf);
    }
    /** Constructor for fungus wood types
     */
    public static WoodHandler noStrippingFungus(String pWoodname, boolean pLogWalls, MaterialColor pBarkColor, MaterialColor pWoodColor, boolean layeredBookshelf)
    {
        return handler(pWoodname, null, false, "mushrooms", "stem", "hyphae", SoundType.WOOD, SoundType.STEM, pLogWalls, pBarkColor, pWoodColor, false, layeredBookshelf);
    }
    /** More general constructor
     */
    public static WoodHandler handler(String pWoodName, @Nullable String pLangName, boolean pStrippedLog, String pTreeName, String pLogSuffix, String pWoodSuffix, SoundType pPlankSoundType, SoundType pLogSoundType, boolean pLogWalls, MaterialColor barkColor, MaterialColor woodColor, boolean hasSporingLogs, boolean layeredBookshelf) {
        WoodHandler instance = new WoodHandler(pWoodName, pLangName, pStrippedLog, pTreeName, pLogSuffix, pWoodSuffix, pPlankSoundType, pLogSoundType, pLogWalls, barkColor, woodColor, hasSporingLogs, layeredBookshelf);
        return instance;
    }

    protected WoodHandler(String pWoodName, @Nullable String pLangName, boolean pStrippedLogs, String pTreeName,  String pLogSuffix, String pWoodSuffix, SoundType pPlankSoundType, SoundType pLogSoundType, boolean pLogWalls, MaterialColor barkColor, MaterialColor woodColor, boolean pSporingLogs, boolean layeredBookshelf) {
        this.hasSporingLogs = pSporingLogs;
        this.hasStrippedLogs = pStrippedLogs;
        this.treeName = pTreeName;
        if (pSporingLogs)
        {
            RegistryObject<RotatedPillarBlock> sporingLogRegistry = ReduxBlocks.register("sporing_" + pWoodName + "_" + pLogSuffix, () -> new AetherLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).sound(pLogSoundType).color(barkColor)));
            this.sporingLog = Optional.of(sporingLogRegistry);
            RegistryObject<RotatedPillarBlock> sporingWoodRegistry = ReduxBlocks.register("sporing_" + pWoodName + "_" + pWoodSuffix, () -> new AetherLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).sound(pLogSoundType).color(barkColor)));
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

        this.bookshelf = ReduxBlocks.register(pWoodName + "_bookshelf", layeredBookshelf ? () -> new LayeredBookshelfBlock(BlockBehaviour.Properties.copy(Blocks.BOOKSHELF).color(woodColor).sound(pPlankSoundType)) : () -> new BookshelfBlock(BlockBehaviour.Properties.copy(Blocks.BOOKSHELF).color(woodColor).sound(pPlankSoundType)));
        this.layeredBookshelf = layeredBookshelf;

        this.woodType = WoodType.create(Redux.MODID + ":" + pWoodName);
        this.signEntity = ReduxBlockEntityTypes.BLOCK_ENTITY_TYPES.register(pWoodName + "_sign", () -> BlockEntityType.Builder.of(((pPos, pState) -> new ReduxSignBlockEntity(pPos, pState, this.getSign().get())), this.getSignBlock().get(), this.getWallSignBlock().get()).build(null));
        this.boatEntity = ReduxEntityTypes.ENTITY_TYPES.register(pWoodName + "_boat",
                () -> EntityType.Builder.<ReduxBoat>of(ReduxBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build(pWoodName + "_boat"));
        this.chestBoatEntity = ReduxEntityTypes.ENTITY_TYPES.register(pWoodName + "_chest_boat",
                () -> EntityType.Builder.<ReduxChestBoat>of(ReduxChestBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build(pWoodName + "_chest_boat"));
        this.log = ReduxBlocks.register(pWoodName + "_" + pLogSuffix, () -> new AetherLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).sound(pLogSoundType).color(barkColor)));
        this.wood = ReduxBlocks.register(pWoodName + "_" + pWoodSuffix, () -> new ReduxNaturalLog(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).sound(pLogSoundType).color(barkColor)));
        if (this.hasStrippedLogs)
        {
            RegistryObject<RotatedPillarBlock> strippedLog = ReduxBlocks.register("stripped_" + pWoodName + "_" + pLogSuffix, () -> new ReduxNaturalLog(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).sound(pLogSoundType).color(woodColor)));
            this.strippedLog = Optional.of(strippedLog);
            RegistryObject<RotatedPillarBlock> strippedWood = ReduxBlocks.register("stripped_" + pWoodName + "_" + pWoodSuffix, () -> new ReduxNaturalLog(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).sound(pLogSoundType).color(woodColor)));
            this.strippedWood = Optional.of(strippedWood);
            RegistryObject<WallBlock> strippedLogWall = ReduxBlocks.register("stripped_" + woodName + "_"+pLogSuffix+"_wall", () -> new ReduxNaturalWall(Block.Properties.of(Material.WOOD).color(woodColor).strength(2.0F).sound(pLogSoundType)));
            RegistryObject<WallBlock> strippedWoodWall = ReduxBlocks.register("stripped_" + woodName + "_"+pWoodSuffix+"_wall", () -> new ReduxNaturalWall(Block.Properties.of(Material.WOOD).color(woodColor).strength(2.0F).sound(pLogSoundType)));

            this.strippedLogWall = Optional.of(strippedLogWall);
            this.strippedWoodWall = Optional.of(strippedWoodWall);
        }
        else {
            this.strippedLog = Optional.empty();
            this.strippedWood = Optional.empty();
            this.strippedLogWall = Optional.empty();
            this.strippedWoodWall = Optional.empty();
        }
        this.planks = ReduxBlocks.register(pWoodName + "_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(pPlankSoundType).color(woodColor)));
        this.stairs = ReduxBlocks.register(pWoodName + "_stairs", () -> new StairBlock(() -> this.planks.get().defaultBlockState(), BlockBehaviour.Properties.copy(this.planks.get())));
        this.slab = ReduxBlocks.register(pWoodName + "_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(this.planks.get()).strength(2.0F, 3.0F)));
        this.fence = ReduxBlocks.register(pWoodName + "_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE).sound(pPlankSoundType).color(woodColor)));
        this.fenceGate = ReduxBlocks.register(pWoodName + "_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE).sound(pPlankSoundType).color(woodColor)));
        this.door = ReduxBlocks.register(pWoodName + "_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).sound(pPlankSoundType).color(woodColor)));
        this.trapdoor = ReduxBlocks.register(pWoodName + "_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR).sound(pPlankSoundType).color(woodColor)));
        this.pressurePlate = ReduxBlocks.register(pWoodName + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE).sound(pPlankSoundType).color(woodColor)));
        this.button = ReduxBlocks.register(pWoodName + "_button", () -> new WoodButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).sound(pPlankSoundType).color(woodColor)));
        this.sign = ReduxBlocks.BLOCKS.register(pWoodName + "_sign", () -> new ReduxSignBlock(Block.Properties.of(Material.WOOD).color(woodColor).noCollission().strength(1.0F).sound(pPlankSoundType), woodType) {
            @Override
            public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
                return new ReduxSignBlockEntity(pos, state, getSign().get());
            }
        });
        this.wallSign = ReduxBlocks.BLOCKS.register(pWoodName + "_wall_sign", () -> new ReduxWallSignBlock(Block.Properties.of(Material.WOOD).color(woodColor).noCollission().strength(1.0F).sound(SoundType.WOOD), woodType) {
            @Override
            public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
                return new ReduxSignBlockEntity(pos, state, getSign().get());
            }
        });


        this.boatItem = ReduxItems.ITEMS.register(pWoodName + "_boat", () -> new ReduxBoatItem(false, (new Item.Properties()).stacksTo(1).tab(AetherCreativeTabs.AETHER_EQUIPMENT_AND_UTILITIES),
                ((level, hitresult) -> new ReduxBoat(this, level, hitresult.getLocation().x, hitresult.getLocation().y, hitresult.getLocation().z) {
                })));
        this.chestBoatItem = ReduxItems.ITEMS.register(pWoodName + "_chest_boat", () -> new ReduxBoatItem(false, (new Item.Properties()).stacksTo(1).tab(AetherCreativeTabs.AETHER_EQUIPMENT_AND_UTILITIES),
                ((level, hitresult)  -> new ReduxChestBoat(this, level, hitresult.getLocation().x, hitresult.getLocation().y, hitresult.getLocation().z) {
                })));

        this.signItem = ReduxItems.register(pWoodName + "_sign", () -> new SignItem((new Item.Properties()).stacksTo(16).tab(AetherCreativeTabs.AETHER_BLOCKS), this.sign.get(), this.wallSign.get()));

        this.logWall = ReduxBlocks.register(woodName + "_"+pLogSuffix+"_wall", () -> new ReduxNaturalWall(Block.Properties.of(Material.WOOD).color(barkColor).strength(2.0F).sound(pLogSoundType)));
        this.woodWall = ReduxBlocks.register(woodName + "_"+pWoodSuffix+"_wall", () -> new ReduxNaturalWall(Block.Properties.of(Material.WOOD).color(barkColor).strength(2.0F).sound(pLogSoundType)));
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
}



