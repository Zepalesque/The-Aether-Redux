package net.zepalesque.redux.data.loot;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.providers.AetherBlockLootSubProvider;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.loot.functions.DoubleDrops;
import com.aetherteam.aether.mixin.mixins.common.accessor.BlockLootAccessor;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.blockhandler.WoodHandler;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.natural.SproutsCropBlock;
import net.zepalesque.redux.block.util.state.ReduxStates;
import net.zepalesque.redux.block.util.state.enums.PetalPrismaticness;
import net.zepalesque.redux.item.ReduxItems;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReduxBlockLootData extends AetherBlockLootSubProvider {
    private static final Set<Item> EXPLOSION_RESISTANT = Stream.of(AetherBlocks.TREASURE_CHEST.get()).map(ItemLike::asItem).collect(Collectors.toSet());

    public ReduxBlockLootData() {
        super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags());
    }

    public void generate() {
        dropSelfDouble(ReduxBlocks.HOLYSILT.get());
        dropSelfDouble(ReduxBlocks.BLIGHTED_AERCLOUD.get());
        dropSelfDouble(ReduxBlocks.RAINBOW_AERCLOUD.get());
        dropSelfDouble(ReduxBlocks.DIVINITE.get());
        this.add(ReduxBlocks.DIVINITE_SLAB.get(), this::createSlabItemTable);
        dropSelf(ReduxBlocks.DIVINITE_STAIRS.get());
        dropSelf(ReduxBlocks.DIVINITE_WALL.get());
        dropSelfDouble(ReduxBlocks.ROCK_CANDY.get());
        this.add(ReduxBlocks.ROCK_CANDY_SLAB.get(), this::createSlabItemTable);
        dropSelf(ReduxBlocks.ROCK_CANDY_STAIRS.get());
        dropSelf(ReduxBlocks.ROCK_CANDY_WALL.get());
        dropSelfDouble(ReduxBlocks.SENTRITE.get());
        this.add(ReduxBlocks.SENTRITE_SLAB.get(), this::createSlabItemTable);
        dropSelf(ReduxBlocks.SENTRITE_STAIRS.get());
        dropSelf(ReduxBlocks.SENTRITE_WALL.get());
        dropSelfDouble(ReduxBlocks.SENTRITE_BRICKS.get());
        this.add(ReduxBlocks.SENTRITE_BRICK_SLAB.get(), this::createSlabItemTable);
        dropSelf(ReduxBlocks.SENTRITE_BRICK_STAIRS.get());
        dropSelf(ReduxBlocks.SENTRITE_BRICK_WALL.get());

        dropSelf(ReduxBlocks.HARDENED_SYRUP.get());
        dropSelf(ReduxBlocks.CHARCOAL_BLOCK.get());

        dropNone(ReduxBlocks.MAPLE_SYRUP.get());

        dropSelf(ReduxBlocks.HOLEFIRE_STONE.get());
        dropSelf(ReduxBlocks.GLOWY_HOLEFIRE_STONE.get());
        this.add(ReduxBlocks.HOLEFIRE_SLAB.get(), this::createSlabItemTable);
        dropSelf(ReduxBlocks.HOLEFIRE_STAIRS.get());
        dropSelf(ReduxBlocks.HOLEFIRE_WALL.get());
        dropSelf(ReduxBlocks.HOLEFIRE_PILLAR.get());
        dropSelf(ReduxBlocks.HOLEFIRE_PILLAR_TOP.get());

        dropSelf(ReduxBlocks.SHELL_SHINGLES.get());
        this.add(ReduxBlocks.SHELL_SHINGLE_SLAB.get(), this::createSlabItemTable);
        dropSelf(ReduxBlocks.SHELL_SHINGLE_STAIRS.get());
        dropSelf(ReduxBlocks.SHELL_SHINGLE_WALL.get());

        dropSelf(ReduxBlocks.ENCHANTED_SHELL_SHINGLES.get());
        this.add(ReduxBlocks.ENCHANTED_SHELL_SHINGLE_SLAB.get(), this::createSlabItemTable);
        dropSelf(ReduxBlocks.ENCHANTED_SHELL_SHINGLE_STAIRS.get());
        dropSelf(ReduxBlocks.ENCHANTED_SHELL_SHINGLE_WALL.get());

        dropSelf(ReduxBlocks.XAELIA_PATCH.get());

        this.add(ReduxBlocks.AVELIUM_SPROUTS.get(), this.shears());
        this.add(ReduxBlocks.SUGARSPROUTS.get(), this.shears());
        this.add(ReduxBlocks.SHORT_DEEP_GRASS.get(), this.shears());
        this.dropPottedContents(ReduxBlocks.POTTED_AVELIUM_ROOTS.get());
        this.add(ReduxBlocks.AVELIUM_ROOTS.get(), this.shears());

        this.add(ReduxBlocks.BLIGHTWILLOW_LEAVES.get(),
                (leaves) -> droppingWithChancesAndSkyrootSticks(leaves, ReduxBlocks.BLIGHTWILLOW_SAPLING.get(), BlockLootAccessor.aether$getNormalLeavesSaplingChances()));

        this.dropDoubleWithFortune(ReduxBlocks.POPROCK_ORE.get(), ReduxItems.LEMON_POPROCKS.get());

        this.dropSelf(ReduxBlocks.BLIGHTWILLOW_SAPLING.get());
        this.dropSelf(ReduxBlocks.CRYSTAL_SAPLING.get());
        this.dropSelf(ReduxBlocks.CRYSTAL_FRUIT_SAPLING.get());
        this.dropSelf(ReduxBlocks.PURPLE_CRYSTAL_FRUIT_SAPLING.get());

        this.dropPottedContents(ReduxBlocks.POTTED_BLIGHTWILLOW_SAPLING.get());
        this.dropPottedContents(ReduxBlocks.POTTED_CRYSTAL_SAPLING.get());
        this.dropPottedContents(ReduxBlocks.POTTED_CRYSTAL_FRUIT_SAPLING.get());
        this.dropPottedContents(ReduxBlocks.POTTED_PURPLE_CRYSTAL_FRUIT_SAPLING.get());


        this.dropSelf(ReduxBlocks.GILDED_VINES.get());
        this.dropOther(ReduxBlocks.GILDED_VINES_PLANT.get(), ReduxBlocks.GILDED_VINES.get());
        this.dropSelf(ReduxBlocks.GOLDEN_VINES.get());
        this.dropOther(ReduxBlocks.GOLDEN_VINES_PLANT.get(), ReduxBlocks.GOLDEN_VINES.get());
        this.dropSelf(ReduxBlocks.CORRUPTED_VINES.get());
        this.dropOther(ReduxBlocks.CORRUPTED_VINES_PLANT.get(), ReduxBlocks.CORRUPTED_VINES.get());


        this.dropSelf(ReduxBlocks.BLIGHTED_SKYROOT_SAPLING.get());
        this.dropPottedContents(ReduxBlocks.POTTED_BLIGHTED_SKYROOT_SAPLING.get());
        this.dropSelf(ReduxBlocks.GILDED_OAK_SAPLING.get());
        this.dropPottedContents(ReduxBlocks.POTTED_GILDED_OAK_SAPLING.get());
        this.add(ReduxBlocks.GILDED_OAK_LEAVES.get(),
                (leaves) -> droppingGoldenOakLeaves(leaves, ReduxBlocks.GILDED_OAK_SAPLING.get(), BlockLootAccessor.aether$getNormalLeavesSaplingChances()));

        this.add(ReduxBlocks.BLIGHTED_SKYROOT_LEAVES.get(),
                (leaves) -> droppingWithChancesAndSkyrootSticks(leaves, ReduxBlocks.BLIGHTED_SKYROOT_SAPLING.get(), BlockLootAccessor.aether$getNormalLeavesSaplingChances()));

        this.add(ReduxBlocks.FIELDSPROOT_LEAVES.get(),
                (leaves) -> droppingWithChancesAndSkyrootSticks(leaves, ReduxBlocks.FIELDSPROOT_SAPLING.get(), BlockLootAccessor.aether$getNormalLeavesSaplingChances()));

        this.add(ReduxBlocks.PRISMATIC_FIELDSPROOT_LEAVES.get(),
                (leaves) -> droppingWithChancesAndSkyrootSticks(leaves, ReduxBlocks.PRISMATIC_FIELDSPROOT_SAPLING.get(), BlockLootAccessor.aether$getNormalLeavesSaplingChances()));

        this.add(ReduxBlocks.AZURE_FIELDSPROOT_LEAVES.get(),
                (leaves) -> droppingWithChancesAndSkyrootSticks(leaves, ReduxBlocks.AZURE_FIELDSPROOT_SAPLING.get(), BlockLootAccessor.aether$getNormalLeavesSaplingChances()));

        this.add(ReduxBlocks.SPECTRAL_FIELDSPROOT_LEAVES.get(),
                (leaves) -> droppingWithChancesAndSkyrootSticks(leaves, ReduxBlocks.SPECTRAL_FIELDSPROOT_SAPLING.get(), BlockLootAccessor.aether$getNormalLeavesSaplingChances()));

        dropWhenSilkTouch(ReduxBlocks.GOLDEN_LEAF_PILE.get());
        dropWhenSilkTouch(ReduxBlocks.GILDED_LEAF_PILE.get());
        dropWhenSilkTouch(ReduxBlocks.BLIGHTWILLOW_LEAF_PILE.get());

        this.add(ReduxBlocks.GLACIA_LEAVES.get(),
                (leaves) -> droppingWithChancesAndSkyrootSticks(leaves, ReduxBlocks.GLACIA_SAPLING.get(), BlockLootAccessor.aether$getNormalLeavesSaplingChances()));
        this.dropSelf(ReduxBlocks.GLACIA_SAPLING.get());
        this.dropPottedContents(ReduxBlocks.POTTED_GLACIA_SAPLING.get());

        this.dropSelf(ReduxBlocks.FIELDSPROOT_SAPLING.get());
        this.dropPottedContents(ReduxBlocks.POTTED_FIELDSPROOT_SAPLING.get());


        this.dropSelf(ReduxBlocks.PRISMATIC_FIELDSPROOT_SAPLING.get());
        this.dropSelf(ReduxBlocks.AZURE_FIELDSPROOT_SAPLING.get());
        this.dropSelf(ReduxBlocks.SPECTRAL_FIELDSPROOT_SAPLING.get());

        this.dropPottedContents(ReduxBlocks.POTTED_PRISMATIC_FIELDSPROOT_SAPLING.get());
        this.dropPottedContents(ReduxBlocks.POTTED_AZURE_FIELDSPROOT_SAPLING.get());
        this.dropPottedContents(ReduxBlocks.POTTED_SPECTRAL_FIELDSPROOT_SAPLING.get());

        this.add(ReduxBlocks.PURPLE_GLACIA_LEAVES.get(),
                (leaves) -> droppingWithChancesAndSkyrootSticks(leaves, ReduxBlocks.PURPLE_GLACIA_SAPLING.get(), BlockLootAccessor.aether$getNormalLeavesSaplingChances()));
        this.dropSelf(ReduxBlocks.PURPLE_GLACIA_SAPLING.get());
        this.dropPottedContents(ReduxBlocks.POTTED_PURPLE_GLACIA_SAPLING.get());

        dropSelf(ReduxBlocks.IRIDIA.get());
        this.dropPottedContents(ReduxBlocks.POTTED_IRIDIA.get());
        this.add(ReduxBlocks.SHORT_AETHER_GRASS.get(), shears());
        this.dropSelfDouble(ReduxBlocks.GILDED_HOLYSTONE.get());
        this.add(ReduxBlocks.GILDED_HOLYSTONE_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(ReduxBlocks.GILDED_HOLYSTONE_STAIRS.get());
        this.dropSelf(ReduxBlocks.GILDED_HOLYSTONE_WALL.get());
        this.dropSelfDouble(ReduxBlocks.COARSE_AETHER_DIRT.get());
        this.dropSelfDouble(ReduxBlocks.COCOA_SOIL.get());
        this.add(ReduxBlocks.LIGHTROOTS.get(), createMultifaceBlockDrops(ReduxBlocks.LIGHTROOTS.get()));
        this.dropSelfDouble(ReduxBlocks.BLIGHTMOSS_HOLYSTONE.get());
        this.add(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_STAIRS.get());
        this.dropSelf(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_WALL.get());
        this.add(ReduxBlocks.GOLDEN_CLOVER.get(), shearsOr(Blocks.AIR));
        this.dropPottedContents(ReduxBlocks.POTTED_GOLDEN_CLOVER.get());
        dropSelf(ReduxBlocks.AURUM.get());
        this.dropPottedContents(ReduxBlocks.POTTED_AURUM.get());
        dropSelf(ReduxBlocks.ZYATRIX.get());
        this.dropPottedContents(ReduxBlocks.POTTED_ZYATRIX.get());
        dropSelf(ReduxBlocks.VANILLA.get());
        this.dropPottedContents(ReduxBlocks.POTTED_VANILLA.get());
        dropSelf(ReduxBlocks.PINK_CANDYFLOSS_BLOCK.get());
        dropSelf(ReduxBlocks.BLUE_CANDYFLOSS_BLOCK.get());
        dropSelf(ReduxBlocks.FLAWLESS_BLOOM.get());
        this.dropPottedContents(ReduxBlocks.POTTED_FLAWLESS_BLOOM.get());
        dropSelf(ReduxBlocks.ROOTROSE.get());
        this.dropPottedContents(ReduxBlocks.POTTED_ROOTROSE.get());
        dropSelf(ReduxBlocks.EDELWEISS.get());
        this.dropPottedContents(ReduxBlocks.POTTED_EDELWEISS.get());
        dropSelf(ReduxBlocks.PEPPERMINT_BARKLING.get());
        this.dropPottedContents(ReduxBlocks.POTTED_PEPPERMINT_BARKLING.get());
        dropSelf(ReduxBlocks.BLUE_COTTON_CANDY.get());
        this.dropPottedContents(ReduxBlocks.POTTED_BLUE_COTTON_CANDY.get());
        dropSelf(ReduxBlocks.PINK_COTTON_CANDY.get());
        this.dropPottedContents(ReduxBlocks.POTTED_PINK_COTTON_CANDY.get());
        dropSelf(ReduxBlocks.CARVED_STONE_BRICKS.get());
        dropSelf(ReduxBlocks.CARVED_STONE_BRICK_STAIRS.get());
        this.add(ReduxBlocks.CARVED_STONE_BRICK_SLAB.get(), this::createSlabItemTable);
        dropSelf(ReduxBlocks.CARVED_STONE_BRICK_WALL.get());
        dropSelf(ReduxBlocks.CARVED_STONE_PILLAR.get());
        this.dropDoubleWithSilk(ReduxBlocks.AVELIUM.get(), AetherBlocks.AETHER_DIRT.get());
        this.dropDoubleWithSilk(ReduxBlocks.DEEP_GRASS_BLOCK.get(), AetherBlocks.AETHER_DIRT.get());
        this.dropDoubleWithSilk(ReduxBlocks.SUGARGRASS_BLOCK.get(), ReduxBlocks.COCOA_SOIL.get());
        this.add(ReduxBlocks.LUXWEED.get(), shears());
        this.dropPottedContents(ReduxBlocks.POTTED_LUXWEED.get());
        this.dropSelfDouble(ReduxBlocks.SPIROLYCTIL.get());
        this.dropPottedContents(ReduxBlocks.POTTED_SPIROLYCTIL.get());
        this.dropSelfDouble(ReduxBlocks.BLIGHTSHADE.get());
        this.dropSelf(ReduxBlocks.GRAVITITE_BLOCK.get());
        this.dropSelf(ReduxBlocks.RAW_GRAVITITE_BLOCK.get());
        this.dropSelf(ReduxBlocks.PAPER_PILLAR.get());
        this.dropSelf(ReduxBlocks.PAPER_BLOCK.get());
        this.naturalDrop(ReduxBlocks.PEPPERMINT_BLOCK.get(), ReduxBlocks.PEPPERMINT_LOG.get());
        this.dropSelf(ReduxBlocks.PEPPERMINT_LOG.get());
        this.dropPottedContents(ReduxBlocks.POTTED_BLIGHTSHADE.get());

        this.add(ReduxBlocks.WYNDSPROUTS_CROP.get(),
                this.createCropDrops(
                        ReduxBlocks.WYNDSPROUTS_CROP.get(),
                        ReduxItems.BUNDLE_OF_WYNDSPROUTS.get(),
                        ReduxItems.WYNDSPROUT_SEEDS.get(),
                        LootItemBlockStatePropertyCondition.
                                hasBlockStateProperties(ReduxBlocks.WYNDSPROUTS_CROP.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SproutsCropBlock.AGE, SproutsCropBlock.MAX_AGE))));

        this.add(ReduxBlocks.SKYSPROUTS_CROP.get(),
                this.createCropDrops(
                        ReduxBlocks.SKYSPROUTS_CROP.get(),
                        ReduxItems.SKYBUD.get(),
                        ReduxItems.SKYSPROUT_SEEDS.get(),
                        LootItemBlockStatePropertyCondition.
                                hasBlockStateProperties(ReduxBlocks.SKYSPROUTS_CROP.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SproutsCropBlock.AGE, SproutsCropBlock.MAX_AGE))));

        this.dropSelf(ReduxBlocks.CLOUD_CAP_BLOCK.get());
        this.dropSelf(ReduxBlocks.JELLYSHROOM.get());
        this.dropSelf(ReduxBlocks.SHIMMERSTOOL.get());
        this.dropPottedContents(ReduxBlocks.POTTED_JELLYSHROOM.get());
        this.dropPottedContents(ReduxBlocks.POTTED_SHIMMERSTOOL.get());

        this.add(ReduxBlocks.FIELDSPROOT_PETALS.get(), this.createFieldsproutPetalsDrops(ReduxBlocks.FIELDSPROOT_PETALS.get()));

        this.add(ReduxBlocks.ZANBERRY_BUSH.get(), (bush) -> this.droppingBerryBush(bush, ReduxBlocks.ZANBERRY_BUSH_STEM.get(), ReduxItems.ZANBERRY.get()));
        this.dropSelfDouble(ReduxBlocks.ZANBERRY_BUSH_STEM.get());
        this.dropPottedContents(ReduxBlocks.POTTED_ZANBERRY_BUSH_STEM.get());
        this.dropPottedContents(ReduxBlocks.POTTED_ZANBERRY_BUSH.get());

        this.dropOther(ReduxBlocks.ENCHANTED_WHITE_FLOWER.get(), AetherBlocks.WHITE_FLOWER.get());

        this.dropSelfDouble(ReduxBlocks.BLIGHTMOSS_BLOCK.get());
        this.dropSelfDouble(ReduxBlocks.BLIGHTMOSS_CARPET.get());
        this.dropSelfDouble(ReduxBlocks.FUNGAL_GROWTH.get());
        this.dropSelfDouble(ReduxBlocks.FUNGAL_CARPET.get());
        this.dropSelf(ReduxBlocks.LUMINA.get());
        this.dropPottedContents(ReduxBlocks.POTTED_LUMINA.get());
        this.dropSelf(ReduxBlocks.DAGGERBLOOM.get());
        this.dropPottedContents(ReduxBlocks.POTTED_DAGGERBLOOM.get());
        this.dropSelf(ReduxBlocks.THERATIP.get());
        this.dropPottedContents(ReduxBlocks.POTTED_THERATIP.get());
        this.dropSelf(ReduxBlocks.FLAREBLOSSOM.get());
        this.dropPottedContents(ReduxBlocks.POTTED_FLAREBLOSSOM.get());
        this.dropSelf(ReduxBlocks.INFERNIA.get());
        this.dropPottedContents(ReduxBlocks.POTTED_INFERNIA.get());
        this.add(ReduxBlocks.WYNDSPROUTS.get(), createSproutsDrops(ReduxBlocks.WYNDSPROUTS.get(), ReduxItems.WYNDSPROUT_SEEDS.get(), 0.25F));
        this.dropPottedContents(ReduxBlocks.POTTED_WYNDSPROUTS.get());
        this.add(ReduxBlocks.SKYSPROUTS.get(), createSproutsDrops(ReduxBlocks.SKYSPROUTS.get(), ReduxItems.SKYSPROUT_SEEDS.get(), 0.25F));
        this.dropPottedContents(ReduxBlocks.POTTED_SKYSPROUTS.get());
        this.dropSelf(ReduxBlocks.SPLITFERN.get());
        this.dropPottedContents(ReduxBlocks.POTTED_SPLITFERN.get());
        this.dropSelf(ReduxBlocks.VERIDIUM_BLOCK.get());
        this.dropSelf(ReduxBlocks.RAW_VERIDIUM_BLOCK.get());
        this.dropSelf(ReduxBlocks.RAW_VALKYRUM_BLOCK.get());
        this.dropSelf(ReduxBlocks.VERIDIUM_ORE.get());
        this.dropSelf(ReduxBlocks.VERIDIUM_CHAIN.get());
        this.dropSelf(ReduxBlocks.VERIDIUM_LANTERN.get());

        this.dropSelf(ReduxBlocks.CLOUDCAP_MUSHLING.get());
        this.dropPottedContents(ReduxBlocks.POTTED_CLOUDCAP_MUSHLING.get());

        this.dropSelfDouble(ReduxBlocks.CLOUDCAP_SPORES.get());


        this.dropSelfDouble(ReduxBlocks.JELLYSHROOM_JELLY_BLOCK.get());

        for (WoodHandler woodHandler : Redux.WoodHandlers.WOOD_HANDLERS)
        {
            this.dropSelfDouble(woodHandler.log.get());
            woodHandler.strippedWood.ifPresent((reg) ->
                    woodHandler.strippedLog.ifPresent(logReg -> this.naturalDrop(reg.get(), logReg.get())));
            woodHandler.strippedLog.ifPresent((reg) -> this.dropSelf(reg.get()));
            this.naturalDrop(woodHandler.logWall.get(), woodHandler.log.get());
            woodHandler.strippedLogWall.ifPresent((reg) ->
                    woodHandler.strippedLog.ifPresent(logReg -> this.naturalDrop(reg.get(), logReg.get())));
            this.naturalDrop(woodHandler.woodWall.get(), woodHandler.log.get());
            woodHandler.strippedWoodWall.ifPresent((reg) ->
                    woodHandler.strippedLog.ifPresent(logReg -> this.naturalDrop(reg.get(), logReg.get())));
            this.naturalDrop(woodHandler.wood.get(), woodHandler.log.get());
            this.dropSelf(woodHandler.planks.get());
            this.dropSelf(woodHandler.stairs.get());
            this.dropSelf(woodHandler.slab.get());
            this.dropSelf(woodHandler.fence.get());
            this.dropSelf(woodHandler.fenceGate.get());
            this.dropSelf(woodHandler.trapdoor.get());
            this.dropSelf(woodHandler.pressurePlate.get());
            this.dropSelf(woodHandler.button.get());
            this.dropOther(woodHandler.wallSign.get(), woodHandler.sign.get());
            this.dropSelf(woodHandler.sign.get());
            this.dropSelf(woodHandler.hangingSign.get());
            this.dropSelf(woodHandler.wallHangingSign.get());
            this.addPublic(woodHandler.door.get(), this.createDoorTable(woodHandler.door.get()));

            woodHandler.sporingLog.ifPresent((block)-> this.addPublic(block.get(), (logBlock) -> this.droppingDoubleGoldenOak(logBlock, woodHandler.log.get(), ReduxItems.BLIGHTED_SPORES.get())));
            woodHandler.sporingWood.ifPresent((block)-> this.addPublic(block.get(), (wood) -> this.droppingDoubleGoldenOak(wood, woodHandler.wood.get(), ReduxItems.BLIGHTED_SPORES.get())));
            this.add(woodHandler.bookshelf.get(), (bookshelf) -> {
                return this.createSingleItemTableWithSilkTouch(bookshelf, Items.BOOK, ConstantValue.exactly(3.0F));
            });
        }

    }


    protected Function<Block, LootTable.Builder> shearsOr(ItemLike drop, float chance)
    {
        return shearsOr(drop, chance, 1.0F, 1.0F);
    }


    protected LootTable.Builder createMultifaceBlockDrops(Block block) {
        return LootTable.lootTable().withPool(LootPool.lootPool().add(this.applyExplosionDecay(block, LootItem.lootTableItem(block).apply(Direction.values(), (p_251536_) -> SetItemCountFunction.setCount(ConstantValue.exactly(1.0F), true).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MultifaceBlock.getFaceProperty(p_251536_), true)))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(-1.0F), true)))));
    }


    private LootTable.Builder naturalDropBase(Block block, ItemLike other) {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(applyExplosionDecay(other, LootItem.lootTableItem(other)))
                                .when(
                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ReduxStates.NATURAL_GEN, true))
                                ).apply(DoubleDrops.builder())
                ).withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(applyExplosionDecay(other, LootItem.lootTableItem(block)))
                                .when(
                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ReduxStates.NATURAL_GEN, false))
                                ).apply(DoubleDrops.builder())
                );
    }

    protected void naturalDrop(Block block, ItemLike other) {
        this.add(block, naturalDropBase(block, other));
    }


    public Function<Block, LootTable.Builder> shears()
    {
        return shearsOr(Blocks.AIR);
    }


    public Function<Block, LootTable.Builder> shearsOr(ItemLike drop, float chance, float min, float max)
    {
        return (block) -> createShearsDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(drop).when(LootItemRandomChanceCondition.randomChance(chance)).apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 2))));
    }

    protected LootTable.Builder createSproutsDrops(Block block, ItemLike seeds, float chance) {
        return createShearsDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(seeds).when(LootItemRandomChanceCondition.randomChance(chance)).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 2))));
    }

    public Function<Block, LootTable.Builder> shearsOr(ItemLike drop)
    {
        return shearsOr(drop, 0.25F);
    }

    protected Iterable<Block> getKnownBlocks() {
        Stream stream = ReduxBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get);
        Objects.requireNonNull(stream);
        return stream::iterator;
    }

    public void dropSelfDouble(Block pBlock) {
        this.dropOther(pBlock, pBlock);
    }

    public void dropOther(Block pBlock, ItemLike pItem) {
        this.add(pBlock, this.createSingleItemTable(pItem));
    }
    public void addPublic(Block pBlock, LootTable.Builder pBuilder) {
        this.add(pBlock, pBuilder);
    }
    public void addPublic(Block pBlock, Function<Block, LootTable.Builder> pFactory) {
        this.add(pBlock, pFactory.apply(pBlock));
    }
    public LootTable.Builder createDoorTable(Block pDoorBlock) {
        return this.createSinglePropConditionTable(pDoorBlock, DoorBlock.HALF, DoubleBlockHalf.LOWER);
    }

    public LootTable.Builder createSlabItemTable(Block pBlock) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(this.applyExplosionDecay(pBlock, LootItem.lootTableItem(pBlock).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE)))))));
    }

    public void dropPottedContents(Block pFlowerPot) {
        this.add(pFlowerPot, (p_250193_) -> {
            return this.createPotFlowerItemTable(((FlowerPotBlock)p_250193_).getContent());
        });
    }





    protected LootTable.Builder createFieldsproutPetalsDrops(Block block) {
        LootTable.Builder base = LootTable.lootTable();
        List<Property<PetalPrismaticness>> props = List.of(ReduxStates.PETAL_1, ReduxStates.PETAL_2, ReduxStates.PETAL_3, ReduxStates.PETAL_4);

        for (Property<PetalPrismaticness> prop : props) {
            LootItemCondition.Builder hasPetal =
                    LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(prop, PetalPrismaticness.NONE)).invert();

            LootPool.Builder pool = LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(
                    this.applyExplosionDecay(block,LootItem.lootTableItem(block)).when(hasPetal));
            base = base.withPool(pool);
        }


        return base;
    }

    public void dropSelf(Block block) {
        this.dropOther(block, block);
    }

    public LootTable.Builder createDoublePlantWithCustomDrops(Block pBlock, Block pSheared, ItemLike drop, float chance) {
        LootPoolEntryContainer.Builder<?> builder = LootItem.lootTableItem(pSheared).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))).when(HAS_SHEARS).otherwise(this.applyExplosionCondition(pBlock, LootItem.lootTableItem(drop)).when(LootItemRandomChanceCondition.randomChance(chance)));
        return LootTable.lootTable().withPool(LootPool.lootPool().add(builder).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(pBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).build()).build()), new BlockPos(0, 1, 0)))).withPool(LootPool.lootPool().add(builder).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER))).when(LocationCheck.checkLocation(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(pBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).build()).build()), new BlockPos(0, -1, 0))));
    }

}
