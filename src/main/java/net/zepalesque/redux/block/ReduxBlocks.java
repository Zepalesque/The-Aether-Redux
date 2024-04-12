package net.zepalesque.redux.block;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.miscellaneous.FloatingBlock;
import com.aetherteam.aether.block.natural.AetherDoubleDropBlock;
import com.aetherteam.aether.block.natural.AetherDoubleDropsLeaves;
import com.aetherteam.aether.block.natural.AetherLogBlock;
import com.aetherteam.aether.block.natural.LeavesWithParticlesBlock;
import com.aetherteam.aether.item.AetherCreativeTabs;
import com.aetherteam.aether.mixin.mixins.common.accessor.FireBlockAccessor;
import com.aetherteam.nitrogen.item.block.EntityBlockItem;
import com.google.common.base.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.blockhandler.WoodHandler;
import net.zepalesque.redux.block.construction.VeridiumLanternBlock;
import net.zepalesque.redux.block.container.*;
import net.zepalesque.redux.block.dungeon.Flareblossom;
import net.zepalesque.redux.block.natural.*;
import net.zepalesque.redux.block.natural.blight.*;
import net.zepalesque.redux.block.natural.cloudcap.*;
import net.zepalesque.redux.block.natural.enchanted.EnchantableBushBlock;
import net.zepalesque.redux.block.natural.enchanted.EnchantableFlowerBlock;
import net.zepalesque.redux.block.natural.enchanted.EnchantedVinesHeadBlock;
import net.zepalesque.redux.block.natural.enchanted.EnchantedVinesPlantBlock;
import net.zepalesque.redux.block.natural.frosted.SnowableLeavesBlock;
import net.zepalesque.redux.block.natural.shrublands.ZanberryBushBlock;
import net.zepalesque.redux.block.natural.shrublands.ZanberryShrubBlock;
import net.zepalesque.redux.block.natural.skyfields.FieldsprootLeafBlock;
import net.zepalesque.redux.block.natural.skyfields.FieldsprootPetalsBlock;
import net.zepalesque.redux.block.natural.skyfields.classic.AzureFieldsprootLeaves;
import net.zepalesque.redux.block.natural.skyfields.classic.ClassicFieldsprootLeaves;
import net.zepalesque.redux.block.util.CommonPlantBounds;
import net.zepalesque.redux.blockentity.ReduxBlockEntityTypes;
import net.zepalesque.redux.blockentity.SkyrootChestBlockEntity;
import net.zepalesque.redux.blockentity.SkyrootChestMimicBlockEntity;
import net.zepalesque.redux.blockentity.TrappedSkyrootChestBlockEntity;
import net.zepalesque.redux.client.particle.ReduxParticleTypes;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.data.resource.ReduxConfiguredFeatures;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.item.block.WoodenBlockItem;
import net.zepalesque.redux.misc.ReduxTags;
import net.zepalesque.redux.world.tree.grower.CrystalTree;
import net.zepalesque.redux.world.tree.grower.ReduxSuppliedTree;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ReduxBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Redux.MODID);
    public static final DeferredRegister<Item> ITEMS = ReduxItems.ITEMS;

    // L O G
    public static final RegistryObject<RotatedPillarBlock> DRIFTSHALE = register("driftshale",
            () -> new AetherLogBlock(BlockBehaviour.Properties.copy(Blocks.SANDSTONE).strength(0.6F, 3.0F)));

    public static RegistryObject<Block> POLISHED_DRIFTSHALE = register("polished_driftshale",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).color(MaterialColor.SAND).requiresCorrectToolForDrops().strength(0.6F, 3.0F)));

    public static RegistryObject<StairBlock> POLISHED_DRIFTSHALE_STAIRS = register("polished_driftshale_stairs",
            () -> new StairBlock(() -> (POLISHED_DRIFTSHALE.get()).defaultBlockState(), BlockBehaviour.Properties.copy(POLISHED_DRIFTSHALE.get())));

    public static RegistryObject<WallBlock> POLISHED_DRIFTSHALE_WALL = register("polished_driftshale_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(POLISHED_DRIFTSHALE.get())));

    public static RegistryObject<SlabBlock> POLISHED_DRIFTSHALE_SLAB = register("polished_driftshale_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(POLISHED_DRIFTSHALE.get()).strength(0.75F, 6.0F)));

    public static RegistryObject<Block> DIVINITE = register("divinite",
            () -> new AetherDoubleDropBlock(BlockBehaviour.Properties.of(Material.STONE).color(MaterialColor.SAND).requiresCorrectToolForDrops().strength(0.5F, 6.0F).sound(SoundType.TUFF)));

public static RegistryObject<StairBlock> DIVINITE_STAIRS = register("divinite_stairs",
            () -> new StairBlock(() -> (DIVINITE.get()).defaultBlockState(), BlockBehaviour.Properties.copy(DIVINITE.get())));

    public static RegistryObject<WallBlock> DIVINITE_WALL = register("divinite_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(DIVINITE.get())));

    public static RegistryObject<SlabBlock> DIVINITE_SLAB = register("divinite_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(DIVINITE.get()).strength(0.75F, 6.0F)));

    public static RegistryObject<Block> SENTRITE = register("sentrite",
            () -> new AetherDoubleDropBlock(BlockBehaviour.Properties.of(Material.STONE).color(MaterialColor.DEEPSLATE).requiresCorrectToolForDrops().strength(1.0F, 6.0F).sound(SoundType.NETHER_ORE)));

    public static RegistryObject<StairBlock> SENTRITE_STAIRS = register("sentrite_stairs",
            () -> new StairBlock(() -> (SENTRITE.get()).defaultBlockState(), BlockBehaviour.Properties.copy(SENTRITE.get())));

    public static RegistryObject<WallBlock> SENTRITE_WALL = register("sentrite_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(SENTRITE.get())));

    public static RegistryObject<SlabBlock> SENTRITE_SLAB = register("sentrite_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(SENTRITE.get()).strength(1.25F, 6.0F)));

    public static RegistryObject<Block> SENTRITE_BRICKS = register("sentrite_bricks",
            () -> new AetherDoubleDropBlock(BlockBehaviour.Properties.of(Material.STONE).color(MaterialColor.DEEPSLATE).requiresCorrectToolForDrops().strength(1.0F, 6.0F).sound(SoundType.NETHER_BRICKS    )));

    public static RegistryObject<StairBlock> SENTRITE_BRICK_STAIRS = register("sentrite_brick_stairs",
            () -> new StairBlock(() -> (SENTRITE_BRICKS.get()).defaultBlockState(), BlockBehaviour.Properties.copy(SENTRITE_BRICKS.get())));

    public static RegistryObject<WallBlock> SENTRITE_BRICK_WALL = register("sentrite_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(SENTRITE_BRICKS.get())));

    public static RegistryObject<SlabBlock> SENTRITE_BRICK_SLAB = register("sentrite_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(SENTRITE_BRICKS.get()).strength(1.25F, 6.0F)));

    public static RegistryObject<FieldsprootPetalsBlock> FIELDSPROOT_PETALS = register("fieldsproot_petals",
            () -> new FieldsprootPetalsBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().hasPostProcess((state, lvl, pos) -> true).sound(SoundType.MOSS_CARPET)));

    public static RegistryObject<Block> FIELDSPROOT_LEAVES = register("fieldsproot_leaves", () -> new FieldsprootLeafBlock(
            FieldsprootLeafBlock::particleFromState, BlockBehaviour.Properties.copy(AetherBlocks.SKYROOT_LEAVES.get()).hasPostProcess((state, lvl, pos) -> true).isSuffocating(ReduxBlocks::never).isViewBlocking(ReduxBlocks::never).color(MaterialColor.COLOR_PURPLE).sound(SoundType.AZALEA_LEAVES)));

    public static RegistryObject<Block> PRISMATIC_FIELDSPROOT_LEAVES = register("prismatic_fieldsproot_leaves", () -> new ClassicFieldsprootLeaves(
            ClassicFieldsprootLeaves.PRISMATIC, BlockBehaviour.Properties.copy(AetherBlocks.SKYROOT_LEAVES.get()).hasPostProcess((state, lvl, pos) -> true).isSuffocating(ReduxBlocks::never).isViewBlocking(ReduxBlocks::never).color(MaterialColor.COLOR_PINK).sound(SoundType.AZALEA_LEAVES)));

    public static RegistryObject<Block> SPECTRAL_FIELDSPROOT_LEAVES = register("spectral_fieldsproot_leaves", () -> new ClassicFieldsprootLeaves(
            ClassicFieldsprootLeaves.SPECTRAL, BlockBehaviour.Properties.copy(AetherBlocks.SKYROOT_LEAVES.get()).hasPostProcess((state, lvl, pos) -> true).isSuffocating(ReduxBlocks::never).isViewBlocking(ReduxBlocks::never).color(MaterialColor.COLOR_LIGHT_BLUE).sound(SoundType.AZALEA_LEAVES)));

    public static RegistryObject<Block> AZURE_FIELDSPROOT_LEAVES = register("azure_fieldsproot_leaves", () -> new AzureFieldsprootLeaves(
            BlockBehaviour.Properties.copy(AetherBlocks.SKYROOT_LEAVES.get()).hasPostProcess((state, lvl, pos) -> true).isSuffocating(ReduxBlocks::never).isViewBlocking(ReduxBlocks::never).color(MaterialColor.COLOR_LIGHT_BLUE).sound(SoundType.AZALEA_LEAVES)));


    public static RegistryObject<Block> BLIGHTED_AERCLOUD = register("blighted_aercloud", () -> new BlightedAercloudBlock(BlockBehaviour.Properties.copy(AetherBlocks.COLD_AERCLOUD.get()).color(MaterialColor.COLOR_LIGHT_GREEN)));



    public static RegistryObject<Block> HOLYSILT = register("holysilt",
            () -> new HolysiltBlock(BlockBehaviour.Properties.of(Material.DIRT).color(MaterialColor.COLOR_LIGHT_GRAY).strength(0.5F).sound(SoundType.SAND)));

    public static RegistryObject<AetherShortGrassBlock> SHORT_AETHER_GRASS = register("short_aether_grass",
                () -> new AetherShortGrassBlock(BlockBehaviour.Properties.copy(Blocks.GRASS).hasPostProcess(ReduxBlocks::always)));

    public static RegistryObject<Block> AVELIUM = register("avelium",
            () -> new AveliumBlock(BlockBehaviour.Properties.of(Material.GRASS).color(MaterialColor.TERRACOTTA_LIGHT_BLUE).randomTicks().strength(0.2F).sound(SoundType.ROOTED_DIRT)));

    public static RegistryObject<Block> AVELIUM_SPROUTS = register("avelium_sprouts",
            () -> new AveliumPlantBlock(BlockBehaviour.Properties.copy(Blocks.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).sound(SoundType.NETHER_SPROUTS), true));
    public static RegistryObject<Block> AVELIUM_ROOTS = register("avelium_roots",
            () -> new AveliumPlantBlock(BlockBehaviour.Properties.copy(Blocks.GRASS).sound(SoundType.ROOTS), false));
    public static final RegistryObject<FlowerPotBlock> POTTED_AVELIUM_ROOTS = BLOCKS.register("potted_avelium_roots", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, AVELIUM_ROOTS, Block.Properties.copy(Blocks.FLOWER_POT)));


    public static RegistryObject<Block> SHELL_SHINGLES = register("shell_shingles",
            () -> new PushReactionBlock(BlockBehaviour.Properties.of(Material.STONE).color(MaterialColor.TERRACOTTA_BLUE).strength(0.4F, 1200.0F).sound(SoundType.MUD_BRICKS), PushReaction.DESTROY));

    public static RegistryObject<StairBlock> SHELL_SHINGLE_STAIRS = register("shell_shingle_stairs",
            () -> new StairBlock(() -> (SHELL_SHINGLES.get()).defaultBlockState(), BlockBehaviour.Properties.copy(SHELL_SHINGLES.get())));

    public static RegistryObject<WallBlock> SHELL_SHINGLE_WALL = register("shell_shingle_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(SHELL_SHINGLES.get())));

    public static RegistryObject<SlabBlock> SHELL_SHINGLE_SLAB = register("shell_shingle_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(SHELL_SHINGLES.get())));


    public static RegistryObject<Block> ENCHANTED_SHELL_SHINGLES = register("enchanted_shell_shingles",
            () -> new PushReactionBlock(BlockBehaviour.Properties.of(Material.STONE).color(MaterialColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(2.0F, 1200.0F).sound(SoundType.MUD_BRICKS), PushReaction.DESTROY));

    public static RegistryObject<StairBlock> ENCHANTED_SHELL_SHINGLE_STAIRS = register("enchanted_shell_shingle_stairs",
            () -> new StairBlock(() -> (ENCHANTED_SHELL_SHINGLES.get()).defaultBlockState(), BlockBehaviour.Properties.copy(ENCHANTED_SHELL_SHINGLES.get())));

    public static RegistryObject<WallBlock> ENCHANTED_SHELL_SHINGLE_WALL = register("enchanted_shell_shingle_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(ENCHANTED_SHELL_SHINGLES.get())));

    public static RegistryObject<SlabBlock> ENCHANTED_SHELL_SHINGLE_SLAB = register("enchanted_shell_shingle_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(ENCHANTED_SHELL_SHINGLES.get())));


    public static RegistryObject<Block> CLOUD_CAP_BLOCK = register("cloud_cap_block",
            () -> new HugeAetherMushroomBlock(BlockBehaviour.Properties.of(Material.WOOD).color(MaterialColor.LAPIS).strength(0.5F).sound(SoundType.FUNGUS)));

    public static RegistryObject<Block> JELLYSHROOM_JELLY_BLOCK = register("jellyshroom_jelly_block",
            () -> new JellyshroomCapBlock(BlockBehaviour.Properties.of(Material.CLAY).color(MaterialColor.COLOR_PURPLE).strength(0.5F).sound(SoundType.HONEY_BLOCK).noOcclusion().isSuffocating(ReduxBlocks::never).isViewBlocking(ReduxBlocks::never)));

    public static RegistryObject<Block> CLOUDCAP_SPORES = register("cloudcap_spores",
            () -> new CloudcapSporesBlock(BlockBehaviour.Properties.of(Material.PLANT).color(MaterialColor.COLOR_LIGHT_BLUE).strength(0.3F).sound(SoundType.WART_BLOCK).lightLevel((state) -> 15).emissiveRendering(ReduxBlocks::always).noOcclusion()));

    public static final RegistryObject<Block> JELLYSHROOM = register("jellyshroom", () -> new AetherMushroom(Block.box(2D, 0D, 3D, 12D, 13D, 12D), Block.Properties.of(Material.PLANT).offsetType(BlockBehaviour.OffsetType.XZ).noCollission().instabreak().sound(SoundType.FUNGUS).color(MaterialColor.COLOR_PURPLE), ReduxConfiguredFeatures.LARGE_JELLYSHROOM));
    public static final RegistryObject<FlowerPotBlock> POTTED_JELLYSHROOM = BLOCKS.register("potted_jellyshroom", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, JELLYSHROOM, Block.Properties.copy(Blocks.FLOWER_POT)));

    public static final RegistryObject<Block> SHIMMERSTOOL = register("shimmerstool", () -> new ShimmerstoolBlock(Block.Properties.of(Material.PLANT).sound(SoundType.FUNGUS /* TODO: Amethyst-like sounds? */).noCollission().offsetType(BlockBehaviour.OffsetType.XZ).instabreak().color(MaterialColor.TERRACOTTA_LIGHT_BLUE).lightLevel((state) -> 10)));
    public static final RegistryObject<FlowerPotBlock> POTTED_SHIMMERSTOOL = BLOCKS.register("potted_shimmerstool", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SHIMMERSTOOL, Block.Properties.copy(Blocks.FLOWER_POT).lightLevel((state) -> 5)));

    public static final RegistryObject<Block> IRIDIA = register("iridia", () -> new FlowerBlock(() -> MobEffects.HEAL, 4, Block.Properties.copy(Blocks.DANDELION).color(MaterialColor.QUARTZ)));
    public static final RegistryObject<FlowerPotBlock> POTTED_IRIDIA = BLOCKS.register("potted_iridia", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, IRIDIA, Block.Properties.copy(Blocks.FLOWER_POT)));

    public static RegistryObject<Block> XAELIA_PATCH = register("xaelia_patch",
            () -> new CustomBoundsFlowerBlock(CommonPlantBounds.FLOWER_CLUSTER, () -> MobEffects.DIG_SPEED, 60, BlockBehaviour.Properties.copy(Blocks.DANDELION).hasPostProcess(ReduxBlocks::always).color(MaterialColor.COLOR_LIGHT_GRAY)));

    public static final RegistryObject<Block> SPIROLYCTIL = register("spirolyctil", () -> new FlowerBlock(() -> MobEffects.LEVITATION, 4, Block.Properties.copy(Blocks.DANDELION).color(MaterialColor.DIAMOND)));
    public static final RegistryObject<FlowerPotBlock> POTTED_SPIROLYCTIL = BLOCKS.register("potted_spirolyctil", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SPIROLYCTIL, Block.Properties.copy(Blocks.FLOWER_POT)));
    public static RegistryObject<Block> GOLDEN_CLOVER = register("golden_clover",
            () -> new EnchantableBushBlock(Block.box(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D), BlockBehaviour.Properties.of(Material.PLANT).hasPostProcess(ReduxBlocks::always).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<FlowerPotBlock> POTTED_GOLDEN_CLOVER = BLOCKS.register("potted_golden_clover", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GOLDEN_CLOVER, Block.Properties.copy(Blocks.FLOWER_POT)));

    public static RegistryObject<Block> AURUM = register("aurum",
            () -> new EnchantableFlowerBlock(CommonPlantBounds.FLOWER, () -> MobEffects.LUCK, 60, BlockBehaviour.Properties.copy(Blocks.POPPY).hasPostProcess(ReduxBlocks::always).color(MaterialColor.GOLD)));
    public static final RegistryObject<FlowerPotBlock> POTTED_AURUM = BLOCKS.register("potted_aurum", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, AURUM, Block.Properties.copy(Blocks.FLOWER_POT)));

    public static RegistryObject<Block> ZYATRIX = register("zyatrix",
            () -> new EnchantableFlowerBlock(CommonPlantBounds.FLOWER, () -> MobEffects.MOVEMENT_SPEED, 60, BlockBehaviour.Properties.copy(Blocks.PINK_TULIP).hasPostProcess(ReduxBlocks::always).color(MaterialColor.COLOR_PURPLE)));
    public static final RegistryObject<FlowerPotBlock> POTTED_ZYATRIX = BLOCKS.register("potted_zyatrix", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ZYATRIX, Block.Properties.copy(Blocks.FLOWER_POT)));

    public static RegistryObject<Block> ENCHANTED_WHITE_FLOWER = BLOCKS.register("enchanted_white_flower",
            () -> new FlowerBlock(() -> MobEffects.SLOW_FALLING, 60, BlockBehaviour.Properties.copy(Blocks.POPPY).color(MaterialColor.WOOL)) {
                @Override
                public String getDescriptionId() {
                    return AetherBlocks.WHITE_FLOWER.get().getDescriptionId();
                }
                @Override
                public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
                    return new ItemStack(AetherBlocks.WHITE_FLOWER.get());
                }
            });

    public static RegistryObject<Block> DAGGERBLOOM = register("daggerbloom",
            () -> new CustomBoundsFlowerBlock(CommonPlantBounds.FERN, () -> MobEffects.MOVEMENT_SLOWDOWN, 60, BlockBehaviour.Properties.copy(Blocks.POPPY).hasPostProcess(ReduxBlocks::always).color(MaterialColor.ICE)));
    public static final RegistryObject<FlowerPotBlock> POTTED_DAGGERBLOOM = BLOCKS.register("potted_daggerbloom", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, DAGGERBLOOM, Block.Properties.copy(Blocks.FLOWER_POT)));

    public static RegistryObject<Block> THERATIP = register("theratip",
            () -> new CustomBoundsFlowerBlock(CommonPlantBounds.FERN, () -> MobEffects.DAMAGE_BOOST, 60, BlockBehaviour.Properties.copy(Blocks.POPPY).hasPostProcess(ReduxBlocks::always).color(MaterialColor.COLOR_PINK)));

    public static final RegistryObject<FlowerPotBlock> POTTED_THERATIP = BLOCKS.register("potted_theratip", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, THERATIP, Block.Properties.copy(Blocks.FLOWER_POT)));

    public static RegistryObject<Block> BLIGHTSHADE = register("blightshade",
            () -> new BlightshadeBlock(() -> MobEffects.DARKNESS, 60, BlockBehaviour.Properties.copy(Blocks.WITHER_ROSE).color(MaterialColor.COLOR_BLACK)));
    public static final RegistryObject<FlowerPotBlock> POTTED_BLIGHTSHADE = BLOCKS.register("potted_blightshade", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BLIGHTSHADE, Block.Properties.copy(Blocks.FLOWER_POT)));

    public static RegistryObject<Block> CLOUDCAP_MUSHLING = register("cloudcap_mushling",
            () -> new AetherMushroom(Block.box(4.0D, 0.0D, 4.0D, 12.0D, 12.0D, 12.0D), BlockBehaviour.Properties.of(Material.PLANT).lightLevel((state) -> 6).noCollission().instabreak().sound(SoundType.NETHER_SPROUTS).offsetType(BlockBehaviour.OffsetType.XZ), ReduxConfiguredFeatures.LARGE_CLOUDCAP));
    public static final RegistryObject<FlowerPotBlock> POTTED_CLOUDCAP_MUSHLING = BLOCKS.register("potted_cloudcap_mushling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, CLOUDCAP_MUSHLING, Block.Properties.copy(Blocks.FLOWER_POT).lightLevel((state) -> 3)));

    public static RegistryObject<Block> LUMINA = register("lumina",
            () -> new FlowerBlock(() -> MobEffects.NIGHT_VISION, 60, BlockBehaviour.Properties.copy(Blocks.POPPY).hasPostProcess(ReduxBlocks::always).lightLevel((state) -> 9).color(MaterialColor.WOOL)));

    public static final RegistryObject<FlowerPotBlock> POTTED_LUMINA = BLOCKS.register("potted_lumina", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, LUMINA, Block.Properties.copy(Blocks.FLOWER_POT)));

    public static RegistryObject<Block> COARSE_AETHER_DIRT = register("coarse_aether_dirt",
            () -> new AetherDoubleDropBlock(BlockBehaviour.Properties.of(Material.DIRT).color(MaterialColor.TERRACOTTA_CYAN).strength(0.2F).sound(SoundType.GRAVEL)));

    public static RegistryObject<Block> GILDED_HOLYSTONE = register("gilded_holystone",
            () -> new AetherDoubleDropBlock(BlockBehaviour.Properties.copy(AetherBlocks.MOSSY_HOLYSTONE.get()).color(MaterialColor.SAND)));

    public static RegistryObject<StairBlock> GILDED_HOLYSTONE_STAIRS = register("gilded_holystone_stairs",
            () -> new StairBlock(() -> (GILDED_HOLYSTONE.get()).defaultBlockState(), BlockBehaviour.Properties.copy(GILDED_HOLYSTONE.get())));

    public static RegistryObject<WallBlock> GILDED_HOLYSTONE_WALL = register("gilded_holystone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(GILDED_HOLYSTONE.get())));

    public static RegistryObject<SlabBlock> GILDED_HOLYSTONE_SLAB = register("gilded_holystone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(GILDED_HOLYSTONE.get()).strength(2.0F, 6.0F)));

    public static RegistryObject<Block> BLIGHTMOSS_HOLYSTONE = register("blightmoss_holystone",
            () -> new AetherDoubleDropBlock(BlockBehaviour.Properties.copy(AetherBlocks.MOSSY_HOLYSTONE.get()).color(MaterialColor.TERRACOTTA_PURPLE).sound(SoundType.NETHER_GOLD_ORE)));

    public static RegistryObject<StairBlock> BLIGHTMOSS_HOLYSTONE_STAIRS = register("blightmoss_holystone_stairs",
            () -> new StairBlock(() -> (BLIGHTMOSS_HOLYSTONE.get()).defaultBlockState(), BlockBehaviour.Properties.copy(GILDED_HOLYSTONE.get())));

    public static RegistryObject<WallBlock> BLIGHTMOSS_HOLYSTONE_WALL = register("blightmoss_holystone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(BLIGHTMOSS_HOLYSTONE.get())));

    public static RegistryObject<SlabBlock> BLIGHTMOSS_HOLYSTONE_SLAB = register("blightmoss_holystone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(BLIGHTMOSS_HOLYSTONE.get()).strength(2.0F, 6.0F)));



    public static final RegistryObject<Block> CARVED_STONE_BRICKS = register("carved_stone_bricks", () -> new Block(Block.Properties.of(Material.STONE).strength(0.5F, 6.0F).requiresCorrectToolForDrops()));
    public static RegistryObject<StairBlock> CARVED_STONE_BRICK_STAIRS = register("carved_stone_brick_stairs",
            () -> new StairBlock(() -> (CARVED_STONE_BRICKS.get()).defaultBlockState(), BlockBehaviour.Properties.copy(CARVED_STONE_BRICKS.get())));

    public static RegistryObject<WallBlock> CARVED_STONE_BRICK_WALL = register("carved_stone_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(CARVED_STONE_BRICKS.get())));

    public static RegistryObject<SlabBlock> CARVED_STONE_BRICK_SLAB = register("carved_stone_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(CARVED_STONE_BRICKS.get()).strength(2.0F, 6.0F)));

    public static final RegistryObject<RotatedPillarBlock> CARVED_STONE_PILLAR = register("carved_stone_pillar",
            () -> new RotatedPillarBlock(Block.Properties.of(Material.STONE).strength(0.5F, 6.0F).requiresCorrectToolForDrops()));
    
    public static RegistryObject<Block> BLIGHTWILLOW_LEAVES = register("blightwillow_leaves", () -> new AetherDoubleDropsLeaves(BlockBehaviour.Properties.of(Material.LEAVES).color(MaterialColor.TERRACOTTA_PURPLE).strength(0.2F).randomTicks().sound(SoundType.AZALEA_LEAVES).noOcclusion().isValidSpawn(ReduxBlocks::ocelotOrParrot).isSuffocating(ReduxBlocks::never).isViewBlocking(ReduxBlocks::never)));


    public static final RegistryObject<SaplingBlock> BLIGHTWILLOW_SAPLING = register("blightwillow_sapling", () ->
            new SaplingBlock(new ReduxSuppliedTree(ReduxConfiguredFeatures.BLIGHTWILLOW_TREE), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).sound(SoundType.AZALEA))
    );
    public static final RegistryObject<FlowerPotBlock> POTTED_BLIGHTWILLOW_SAPLING = BLOCKS.register("potted_blightwillow_sapling",
            () -> new FlowerPotBlock(() ->
                    (FlowerPotBlock)Blocks.FLOWER_POT, BLIGHTWILLOW_SAPLING,
                    BlockBehaviour.Properties.copy(Blocks.FLOWER_POT))
    );


    public static final RegistryObject<SaplingBlock> CRYSTAL_SAPLING = register("crystal_sapling", () ->
            new SaplingBlock(new CrystalTree(false), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING))
    );
    public static final RegistryObject<FlowerPotBlock> POTTED_CRYSTAL_SAPLING = BLOCKS.register("potted_crystal_sapling",
            () -> new FlowerPotBlock(() ->
                    (FlowerPotBlock)Blocks.FLOWER_POT, CRYSTAL_SAPLING,
                    BlockBehaviour.Properties.copy(Blocks.FLOWER_POT))
    );
    public static final RegistryObject<SaplingBlock> CRYSTAL_FRUIT_SAPLING = register("crystal_fruit_sapling", () ->
            new SaplingBlock(new CrystalTree(true), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING))
    );
    public static final RegistryObject<FlowerPotBlock> POTTED_CRYSTAL_FRUIT_SAPLING = BLOCKS.register("potted_crystal_fruit_sapling",
            () -> new FlowerPotBlock(() ->
                    (FlowerPotBlock)Blocks.FLOWER_POT, CRYSTAL_FRUIT_SAPLING,
                    BlockBehaviour.Properties.copy(Blocks.FLOWER_POT))
    );


    public static final RegistryObject<SaplingBlock> FIELDSPROOT_SAPLING = register("fieldsproot_sapling", () ->
            new SaplingBlock(new ReduxSuppliedTree(ReduxConfiguredFeatures.FIELDSPROOT_TREE), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).sound(SoundType.BAMBOO_SAPLING))
    );
    public static final RegistryObject<FlowerPotBlock> POTTED_FIELDSPROOT_SAPLING = BLOCKS.register("potted_fieldsproot_sapling",
            () -> new FlowerPotBlock(() ->
                    (FlowerPotBlock)Blocks.FLOWER_POT, FIELDSPROOT_SAPLING,
                    BlockBehaviour.Properties.copy(Blocks.FLOWER_POT))
    );



    public static final RegistryObject<SaplingBlock> PRISMATIC_FIELDSPROOT_SAPLING = register("prismatic_fieldsproot_sapling", () ->
            new SaplingBlock(new ReduxSuppliedTree(ReduxConfiguredFeatures.PRISMATIC_FIELDSPROOT_TREE), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).sound(SoundType.BAMBOO_SAPLING))
    );
    public static final RegistryObject<FlowerPotBlock> POTTED_PRISMATIC_FIELDSPROOT_SAPLING = BLOCKS.register("potted_prismatic_fieldsproot_sapling",
            () -> new FlowerPotBlock(() ->
                    (FlowerPotBlock)Blocks.FLOWER_POT, PRISMATIC_FIELDSPROOT_SAPLING,
                    BlockBehaviour.Properties.copy(Blocks.FLOWER_POT))
    );

    public static final RegistryObject<SaplingBlock> SPECTRAL_FIELDSPROOT_SAPLING = register("spectral_fieldsproot_sapling", () ->
            new SaplingBlock(new ReduxSuppliedTree(ReduxConfiguredFeatures.SPECTRAL_FIELDSPROOT_TREE), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).sound(SoundType.BAMBOO_SAPLING))

    );
    public static final RegistryObject<FlowerPotBlock> POTTED_SPECTRAL_FIELDSPROOT_SAPLING = BLOCKS.register("potted_spectral_fieldsproot_sapling",
            () -> new FlowerPotBlock(() ->
                    (FlowerPotBlock)Blocks.FLOWER_POT, SPECTRAL_FIELDSPROOT_SAPLING,
                    BlockBehaviour.Properties.copy(Blocks.FLOWER_POT))
    );
    public static final RegistryObject<SaplingBlock> AZURE_FIELDSPROOT_SAPLING = register("azure_fieldsproot_sapling", () ->
            new SaplingBlock(new ReduxSuppliedTree(ReduxConfiguredFeatures.AZURE_FIELDSPROOT_TREE), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).sound(SoundType.BAMBOO_SAPLING))
    );
    
    public static final RegistryObject<FlowerPotBlock> POTTED_AZURE_FIELDSPROOT_SAPLING = BLOCKS.register("potted_azure_fieldsproot_sapling",
            () -> new FlowerPotBlock(() ->
                    (FlowerPotBlock)Blocks.FLOWER_POT, AZURE_FIELDSPROOT_SAPLING,
                    BlockBehaviour.Properties.copy(Blocks.FLOWER_POT))
    );

    public static RegistryObject<Block> GLACIA_LEAVES = register("glacia_leaves", () -> new SnowableLeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).color(MaterialColor.LAPIS).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(ReduxBlocks::ocelotOrParrot).isSuffocating(ReduxBlocks::never).isViewBlocking(ReduxBlocks::never)));

    public static RegistryObject<Block> PURPLE_GLACIA_LEAVES = register("purple_glacia_leaves", () -> new SnowableLeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).color(MaterialColor.COLOR_PURPLE).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(ReduxBlocks::ocelotOrParrot).isSuffocating(ReduxBlocks::never).isViewBlocking(ReduxBlocks::never)));

    public static final RegistryObject<SaplingBlock> GLACIA_SAPLING = register("glacia_sapling", () ->
            new SaplingBlock(new ReduxSuppliedTree(ReduxConfiguredFeatures.GLACIA_TREE), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING))
    );
    public static final RegistryObject<FlowerPotBlock> POTTED_GLACIA_SAPLING = BLOCKS.register("potted_glacia_sapling",
            () -> new FlowerPotBlock(() ->
                    (FlowerPotBlock)Blocks.FLOWER_POT, GLACIA_SAPLING,
                    BlockBehaviour.Properties.copy(Blocks.FLOWER_POT))
    );


    public static final RegistryObject<SaplingBlock> PURPLE_GLACIA_SAPLING = register("purple_glacia_sapling", () ->
            new SaplingBlock(new ReduxSuppliedTree(ReduxConfiguredFeatures.PURPLE_GLACIA_TREE), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING))
    );
    public static final RegistryObject<FlowerPotBlock> POTTED_PURPLE_GLACIA_SAPLING = BLOCKS.register("purple_potted_glacia_sapling",
            () -> new FlowerPotBlock(() ->
                    (FlowerPotBlock)Blocks.FLOWER_POT, PURPLE_GLACIA_SAPLING,
                    BlockBehaviour.Properties.copy(Blocks.FLOWER_POT))
    );



    public static RegistryObject<Block> WYNDSPROUTS = register("wyndsprouts",
            () -> new BaseAetherBushPlant(BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.MOSS_CARPET).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<FlowerPotBlock> POTTED_WYNDSPROUTS = BLOCKS.register("potted_wyndsprouts", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, WYNDSPROUTS, Block.Properties.copy(Blocks.FLOWER_POT)));

    public static RegistryObject<Block> SKYSPROUTS = register("skysprouts",
            () -> new BaseAetherBushPlant(BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.MOSS_CARPET).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<FlowerPotBlock> POTTED_SKYSPROUTS = BLOCKS.register("potted_skysprouts", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SKYSPROUTS, Block.Properties.copy(Blocks.FLOWER_POT)));

    public static RegistryObject<Block> LUXWEED = register("luxweed",
            () -> new BaseAetherBushPlant(BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.MOSS_CARPET).offsetType(BlockBehaviour.OffsetType.XZ).lightLevel( (state) -> 5).color(MaterialColor.COLOR_CYAN)));

    public static final RegistryObject<FlowerPotBlock> POTTED_LUXWEED = BLOCKS.register("potted_luxweed", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, LUXWEED, Block.Properties.copy(Blocks.FLOWER_POT).lightLevel( (state) -> 5)));

    public static RegistryObject<Block> WYNDSPROUTS_CROP = BLOCKS.register("wyndsprouts_crop",
            () -> new SproutsCropBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.CROP)));

    public static RegistryObject<Block> SKYSPROUTS_CROP = BLOCKS.register("skysprouts_crop",
            () -> new SproutsCropBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.CROP)));


    public static RegistryObject<Block> BLIGHTMOSS_BLOCK = register("blightmoss_block", () -> new BlightmossBlock(BlockBehaviour.Properties.copy(Blocks.MOSS_BLOCK).color(MaterialColor.COLOR_PURPLE)));
    public static final RegistryObject<Block> BLIGHTMOSS_CARPET = register("blightmoss_carpet", () -> new DoubleDropsCarpet(BlockBehaviour.Properties.copy(Blocks.MOSS_CARPET).color(MaterialColor.COLOR_PURPLE)));

    public static RegistryObject<Block> FUNGAL_GROWTH = register("fungal_growth", () -> new FungalGrowthBlock(BlockBehaviour.Properties.copy(Blocks.MOSS_BLOCK).color(MaterialColor.TERRACOTTA_LIGHT_BLUE).sound(SoundType.WART_BLOCK)));
    public static final RegistryObject<Block> FUNGAL_CARPET = register("fungal_carpet", () -> new DoubleDropsCarpet(BlockBehaviour.Properties.copy(Blocks.MOSS_CARPET).color(MaterialColor.TERRACOTTA_LIGHT_BLUE).sound(SoundType.NETHER_WART)));

    public static RegistryObject<Block> GILDED_OAK_LEAVES = register("gilded_oak_leaves", () -> new LeavesWithParticlesBlock(ReduxParticleTypes.GILDED_SKYROOT_LEAVES, BlockBehaviour.Properties.copy(AetherBlocks.GOLDEN_OAK_LEAVES.get()).isSuffocating(ReduxBlocks::never).isViewBlocking(ReduxBlocks::never).color(MaterialColor.QUARTZ)));


    public static RegistryObject<Block> BLIGHTED_SKYROOT_LEAVES = register("blighted_skyroot_leaves", () -> new AetherDoubleDropsLeaves(BlockBehaviour.Properties.copy(AetherBlocks.SKYROOT_LEAVES.get()).isSuffocating(ReduxBlocks::never).isViewBlocking(ReduxBlocks::never).color(MaterialColor.TERRACOTTA_PURPLE)));



    public static final RegistryObject<Block> ZANBERRY_BUSH = register("zanberry_bush", () -> new ZanberryBushBlock(Block.Properties.of(Material.PLANT).color(MaterialColor.COLOR_LIGHT_GRAY).strength(0.2F).sound(SoundType.GRASS).noOcclusion().isValidSpawn(ReduxBlocks::ocelotOrParrot).isSuffocating(ReduxBlocks::never).isViewBlocking(ReduxBlocks::never)));
    public static final RegistryObject<Block> ZANBERRY_BUSH_STEM = register("zanberry_bush_stem", () -> new ZanberryShrubBlock(Block.Properties.of(Material.PLANT).color(MaterialColor.COLOR_LIGHT_GRAY).strength(0.2F).sound(SoundType.GRASS).noCollission()));
    public static final RegistryObject<FlowerPotBlock> POTTED_ZANBERRY_BUSH_STEM = BLOCKS.register("potted_zanberry_bush_stem", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ZANBERRY_BUSH_STEM, Block.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistryObject<FlowerPotBlock> POTTED_ZANBERRY_BUSH = BLOCKS.register("potted_zanberry_bush", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ZANBERRY_BUSH_STEM, Block.Properties.copy(Blocks.FLOWER_POT)));


    public static final RegistryObject<SaplingBlock> GILDED_OAK_SAPLING = register("gilded_oak_sapling", () ->
            new SaplingBlock(new ReduxSuppliedTree(random -> ReduxConfig.COMMON.alternate_gilded_trees.get() ? ReduxConfiguredFeatures.SMALL_GILDED_OAK_TREE : random.nextBoolean() ? ReduxConfiguredFeatures.FANCY_GILDED_OAK_TREE : ReduxConfiguredFeatures.GILDED_OAK_TREE), BlockBehaviour.Properties.copy(AetherBlocks.GOLDEN_OAK_SAPLING.get()))
    );

    public static final RegistryObject<FlowerPotBlock> POTTED_GILDED_OAK_SAPLING = BLOCKS.register("potted_gilded_oak_sapling",
            () -> new FlowerPotBlock(() ->
                    (FlowerPotBlock)Blocks.FLOWER_POT, GILDED_OAK_SAPLING,
                    BlockBehaviour.Properties.copy(Blocks.FLOWER_POT))
    );

    public static final RegistryObject<SaplingBlock> BLIGHTED_SKYROOT_SAPLING = register("blighted_skyroot_sapling", () ->
            new SaplingBlock(new ReduxSuppliedTree(ReduxConfiguredFeatures.BLIGHTED_SKYROOT_TREE), BlockBehaviour.Properties.copy(AetherBlocks.SKYROOT_SAPLING.get()))
    );

    public static final RegistryObject<FlowerPotBlock> POTTED_BLIGHTED_SKYROOT_SAPLING = BLOCKS.register("potted_blighted_skyroot_sapling",
            () -> new FlowerPotBlock(() ->
                    (FlowerPotBlock)Blocks.FLOWER_POT, BLIGHTED_SKYROOT_SAPLING,
                    BlockBehaviour.Properties.copy(Blocks.FLOWER_POT))
    );

    public static final RegistryObject<LeafPileBlock> GOLDEN_LEAF_PILE = register("golden_leaf_pile", () -> new LeafPileBlock(BlockBehaviour.Properties.copy(AetherBlocks.GOLDEN_OAK_LEAVES.get()).strength(0.1F).sound(SoundType.AZALEA_LEAVES)));
    public static final RegistryObject<LeafPileBlock> GILDED_LEAF_PILE = register("gilded_leaf_pile", () -> new LeafPileBlock(BlockBehaviour.Properties.copy(GILDED_OAK_LEAVES.get()).strength(0.1F).sound(SoundType.AZALEA_LEAVES)));

    public static final RegistryObject<LeafPileBlock> BLIGHTWILLOW_LEAF_PILE = register("blightwillow_leaf_pile", () -> new LeafPileBlock(BlockBehaviour.Properties.copy(BLIGHTWILLOW_LEAVES.get()).strength(0.1F).sound(SoundType.AZALEA_LEAVES)));

    public static RegistryObject<Block> SPLITFERN = register("splitfern",
            () -> new EnchantableBushBlock(CommonPlantBounds.FERN, BlockBehaviour.Properties.copy(Blocks.FERN).hasPostProcess(ReduxBlocks::always).sound(SoundType.MOSS_CARPET).offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<FlowerPotBlock> POTTED_SPLITFERN = BLOCKS.register("potted_splitfern", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, SPLITFERN, Block.Properties.copy(Blocks.FLOWER_POT)));

    public static RegistryObject<Block> VERIDIUM_ORE = register("veridium_ore",
            () -> new AetherDoubleDropBlock(BlockBehaviour.Properties.of(Material.STONE).color(MaterialColor.WOOL).strength(3.0F).requiresCorrectToolForDrops()));

    public static RegistryObject<Block> VERIDIUM_BLOCK = register("veridium_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL).color(MaterialColor.LAPIS).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK)));

    public static RegistryObject<Block> RAW_VERIDIUM_BLOCK = register("raw_veridium_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL).color(MaterialColor.LAPIS).strength(3.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));

    public static RegistryObject<Block> RAW_VALKYRUM_BLOCK = register("raw_valkyrum_block",
            () -> new Block(BlockBehaviour.Properties.copy(AetherBlocks.ZANITE_BLOCK.get()).strength(6.0F, 8.0F).color(MaterialColor.TERRACOTTA_WHITE)));


    public static RegistryObject<Block> RAW_GRAVITITE_BLOCK = register("raw_gravitite_block",
            () -> new FloatingBlock(false, BlockBehaviour.Properties.copy(AetherBlocks.ENCHANTED_GRAVITITE.get())));

    public static RegistryObject<Block> GRAVITITE_BLOCK = registerModifyItemProperties("gravitite_block",
            () -> new FloatingBlock(true, BlockBehaviour.Properties.copy(AetherBlocks.ENCHANTED_GRAVITITE.get())), properties -> properties.rarity(Rarity.RARE));


    // TODO: Consider using unique behavior rather than reskinned Glow Lichen
    public static RegistryObject<MultifaceBlock> LIGHTROOTS = BLOCKS.register("lightroots",
            () -> new GlowLichenBlock(BlockBehaviour.Properties.copy(Blocks.GLOW_LICHEN).lightLevel(GlowLichenBlock.emission(12)).color(MaterialColor.DIAMOND).randomTicks()));

    public static RegistryObject<EnchantedVinesHeadBlock> GOLDEN_VINES = register("golden_vines",
            () -> new EnchantedVinesHeadBlock(BlockBehaviour.Properties.copy(Blocks.WEEPING_VINES)
                    .color(MaterialColor.GOLD).sound(SoundType.CAVE_VINES), (state) -> state.is(ReduxTags.Blocks.ENCHANTED_VINES_SURVIVE), ReduxBlocks.GOLDEN_VINES_PLANT));

    public static RegistryObject<EnchantedVinesPlantBlock> GOLDEN_VINES_PLANT = BLOCKS.register("golden_vines_plant",
            () -> new EnchantedVinesPlantBlock(BlockBehaviour.Properties.copy(Blocks.WEEPING_VINES_PLANT)
                    .color(MaterialColor.GOLD).sound(SoundType.CAVE_VINES), (state) -> state.is(ReduxTags.Blocks.ENCHANTED_VINES_SURVIVE), ReduxBlocks.GOLDEN_VINES));

    public static RegistryObject<EnchantedVinesHeadBlock> GILDED_VINES = register("gilded_vines",
            () -> new EnchantedVinesHeadBlock(BlockBehaviour.Properties.copy(Blocks.WEEPING_VINES)
                    .color(MaterialColor.QUARTZ).sound(SoundType.CAVE_VINES), (state) -> state.is(ReduxTags.Blocks.ENCHANTED_VINES_SURVIVE), ReduxBlocks.GILDED_VINES_PLANT));

    public static RegistryObject<EnchantedVinesPlantBlock> GILDED_VINES_PLANT = BLOCKS.register("gilded_vines_plant",
            () -> new EnchantedVinesPlantBlock(BlockBehaviour.Properties.copy(Blocks.WEEPING_VINES_PLANT)
                    .color(MaterialColor.QUARTZ).sound(SoundType.CAVE_VINES), (state) -> state.is(ReduxTags.Blocks.ENCHANTED_VINES_SURVIVE), ReduxBlocks.GILDED_VINES));


    public static RegistryObject<CorruptedVinesHeadBlock> CORRUPTED_VINES = register("corrupted_vines",
            () -> new CorruptedVinesHeadBlock(BlockBehaviour.Properties.copy(Blocks.TWISTING_VINES).lightLevel(value -> 5)
                    .color(MaterialColor.TERRACOTTA_MAGENTA).sound(SoundType.CAVE_VINES), ReduxBlocks.CORRUPTED_VINES_PLANT));

    public static RegistryObject<CorruptedVinesPlantBlock> CORRUPTED_VINES_PLANT = BLOCKS.register("corrupted_vines_plant",
            () -> new CorruptedVinesPlantBlock(BlockBehaviour.Properties.copy(Blocks.TWISTING_VINES_PLANT)
                    .color(MaterialColor.TERRACOTTA_MAGENTA).sound(SoundType.CAVE_VINES).lightLevel(value -> 8), ReduxBlocks.CORRUPTED_VINES));

    public static RegistryObject<Block> VERIDIUM_CHAIN = register("veridium_chain",
            () -> new ChainBlock(BlockBehaviour.Properties.copy(Blocks.CHAIN)));

    public static RegistryObject<Block> VERIDIUM_LANTERN = register("veridium_lantern",
            () -> new VeridiumLanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN).lightLevel((p_187433_) -> 13).noOcclusion()));


    public static final RegistryObject<Block> SKYROOT_CRAFTING_TABLE = register("skyroot_crafting_table", () -> new SkyrootCraftingTableBlock(Block.Properties.of(Material.WOOD).strength(2.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> HOLYSTONE_FURNACE = register("holystone_furnace", () -> new HolystoneFurnaceBlock(Block.Properties.of(Material.STONE).strength(3.5F).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> SKYROOT_CHEST = registerTile("skyroot_chest", () -> new SkyrootChestBlock(Block.Properties.of(Material.WOOD).strength(2.5F).sound(SoundType.WOOD), ReduxBlockEntityTypes.SKYROOT_CHEST::get));
    public static final RegistryObject<Block> TRAPPED_SKYROOT_CHEST = registerTile("trapped_skyroot_chest", () -> new TrappedSkyrootChestBlock(Block.Properties.of(Material.WOOD).strength(2.5F).sound(SoundType.WOOD), ReduxBlockEntityTypes.TRAPPED_SKYROOT_CHEST::get));
    public static final RegistryObject<LadderBlock> SKYROOT_LADDER = register("skyroot_ladder", () -> new LadderBlock(BlockBehaviour.Properties.of(Material.DECORATION).strength(0.4F).sound(SoundType.LADDER).noOcclusion()));

    public static final RegistryObject<Block> SKYROOT_CHEST_MIMIC = registerTile("skyroot_chest_mimic", () -> new SkyrootChestMimicBlock(Block.Properties.copy(SKYROOT_CHEST.get()).noLootTable()));

    public static RegistryObject<Block> FLAREBLOSSOM = register("flareblossom",
            () -> new Flareblossom(() -> MobEffects.BLINDNESS, 60, BlockBehaviour.Properties.copy(Blocks.POPPY).hasPostProcess(ReduxBlocks::always).lightLevel((state) -> 11).color(MaterialColor.GOLD)));

    public static final RegistryObject<FlowerPotBlock> POTTED_FLAREBLOSSOM = BLOCKS.register("potted_flareblossom", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, FLAREBLOSSOM, Block.Properties.copy(Blocks.FLOWER_POT)));


    public static RegistryObject<Block> INFERNIA = register("infernia",
            () -> new CustomBoundsFlowerBlock(CommonPlantBounds.FLOWER, () -> MobEffects.FIRE_RESISTANCE, 60, BlockBehaviour.Properties.copy(Blocks.POPPY).hasPostProcess(ReduxBlocks::always).color(MaterialColor.COLOR_ORANGE)));
    public static final RegistryObject<FlowerPotBlock> POTTED_INFERNIA = BLOCKS.register("potted_infernia", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, INFERNIA, Block.Properties.copy(Blocks.FLOWER_POT)));



    public static void registerFlammability() {
        FireBlockAccessor fireBlockAccessor = (FireBlockAccessor)Blocks.FIRE;
        fireBlockAccessor.callSetFlammable(BLIGHTWILLOW_LEAVES.get(), 30, 60);
        fireBlockAccessor.callSetFlammable(GLACIA_LEAVES.get(), 30, 60);
        for (WoodHandler woodHandler : Redux.WoodHandlers.WOOD_HANDLERS) {
            woodHandler.strippedLog.ifPresent((reg) -> fireBlockAccessor.callSetFlammable(reg.get(), 5, 5));
            fireBlockAccessor.callSetFlammable(woodHandler.log.get(), 5, 5);
            fireBlockAccessor.callSetFlammable(woodHandler.logWall.get(), 5, 5);
            woodHandler.strippedLogWall.ifPresent((reg) -> fireBlockAccessor.callSetFlammable(reg.get(), 5, 5));
            fireBlockAccessor.callSetFlammable(woodHandler.woodWall.get(), 5, 5);
            woodHandler.strippedWoodWall.ifPresent((reg) -> fireBlockAccessor.callSetFlammable(reg.get(), 5, 5));
            fireBlockAccessor.callSetFlammable(woodHandler.wood.get(), 5, 5);
            woodHandler.strippedWood.ifPresent((reg) -> fireBlockAccessor.callSetFlammable(reg.get(), 5, 5));
            fireBlockAccessor.callSetFlammable(woodHandler.planks.get(), 5, 20);
            fireBlockAccessor.callSetFlammable(woodHandler.fenceGate.get(), 5, 20);
            fireBlockAccessor.callSetFlammable(woodHandler.fence.get(), 5, 20);
            fireBlockAccessor.callSetFlammable(woodHandler.stairs.get(), 5, 20);
            fireBlockAccessor.callSetFlammable(woodHandler.slab.get(), 5, 20);
            woodHandler.sporingLog.ifPresent((block) -> fireBlockAccessor.callSetFlammable(block.get(), 5, 5));
            woodHandler.sporingWood.ifPresent((block) -> fireBlockAccessor.callSetFlammable(block.get(), 5, 5));
        }
        fireBlockAccessor.callSetFlammable(IRIDIA.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(AURUM.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(GOLDEN_CLOVER.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(SPLITFERN.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(LUXWEED.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(SPIROLYCTIL.get(), 60, 100);
        fireBlockAccessor.callSetFlammable(BLIGHTSHADE.get(), 60, 100);
    }
    public static void registerWoodTypes() {
        for (WoodHandler woodHandler : Redux.WoodHandlers.WOOD_HANDLERS)
        {
            WoodType.register(woodHandler.woodType);
        }
    }

    public static void registerPots() {
        FlowerPotBlock pot = (FlowerPotBlock) Blocks.FLOWER_POT;
        pot.addPlant(ReduxBlocks.IRIDIA.getId(), ReduxBlocks.POTTED_IRIDIA);
        pot.addPlant(ReduxBlocks.GOLDEN_CLOVER.getId(), ReduxBlocks.POTTED_GOLDEN_CLOVER);
        pot.addPlant(ReduxBlocks.AURUM.getId(), ReduxBlocks.POTTED_AURUM);
        pot.addPlant(ReduxBlocks.ZYATRIX.getId(), ReduxBlocks.POTTED_ZYATRIX);
        pot.addPlant(ReduxBlocks.LUXWEED.getId(), ReduxBlocks.POTTED_LUXWEED);
        pot.addPlant(ReduxBlocks.SPIROLYCTIL.getId(), ReduxBlocks.POTTED_SPIROLYCTIL);
        pot.addPlant(ReduxBlocks.BLIGHTSHADE.getId(), ReduxBlocks.POTTED_BLIGHTSHADE);
        pot.addPlant(ReduxBlocks.ZANBERRY_BUSH_STEM.getId(), ReduxBlocks.POTTED_ZANBERRY_BUSH_STEM);
        pot.addPlant(ReduxBlocks.ZANBERRY_BUSH.getId(), ReduxBlocks.POTTED_ZANBERRY_BUSH);
        pot.addPlant(ReduxBlocks.BLIGHTWILLOW_SAPLING.getId(), ReduxBlocks.POTTED_BLIGHTWILLOW_SAPLING);
        pot.addPlant(ReduxBlocks.GLACIA_SAPLING.getId(), ReduxBlocks.POTTED_GLACIA_SAPLING);
        pot.addPlant(ReduxBlocks.PURPLE_GLACIA_SAPLING.getId(), ReduxBlocks.POTTED_PURPLE_GLACIA_SAPLING);
        pot.addPlant(ReduxBlocks.LUMINA.getId(), ReduxBlocks.POTTED_LUMINA);
        pot.addPlant(ReduxBlocks.DAGGERBLOOM.getId(), ReduxBlocks.POTTED_DAGGERBLOOM);
        pot.addPlant(ReduxBlocks.WYNDSPROUTS.getId(), ReduxBlocks.POTTED_WYNDSPROUTS);
        pot.addPlant(ReduxBlocks.SPLITFERN.getId(), ReduxBlocks.POTTED_SPLITFERN);
        pot.addPlant(ReduxBlocks.CLOUDCAP_MUSHLING.getId(), ReduxBlocks.POTTED_CLOUDCAP_MUSHLING);
        pot.addPlant(ReduxBlocks.AVELIUM_ROOTS.getId(), ReduxBlocks.POTTED_AVELIUM_ROOTS);
        pot.addPlant(ReduxBlocks.JELLYSHROOM.getId(), ReduxBlocks.POTTED_JELLYSHROOM);
        pot.addPlant(ReduxBlocks.GILDED_OAK_SAPLING.getId(), ReduxBlocks.POTTED_GILDED_OAK_SAPLING);
        pot.addPlant(ReduxBlocks.BLIGHTED_SKYROOT_SAPLING.getId(), ReduxBlocks.POTTED_BLIGHTED_SKYROOT_SAPLING);
        pot.addPlant(ReduxBlocks.SKYSPROUTS.getId(), ReduxBlocks.POTTED_SKYSPROUTS);
        pot.addPlant(ReduxBlocks.THERATIP.getId(), ReduxBlocks.POTTED_THERATIP);
        pot.addPlant(ReduxBlocks.CRYSTAL_SAPLING.getId(), ReduxBlocks.POTTED_CRYSTAL_SAPLING);
        pot.addPlant(ReduxBlocks.CRYSTAL_FRUIT_SAPLING.getId(), ReduxBlocks.POTTED_CRYSTAL_FRUIT_SAPLING);
        pot.addPlant(ReduxBlocks.SHIMMERSTOOL.getId(), ReduxBlocks.POTTED_SHIMMERSTOOL);
        pot.addPlant(ReduxBlocks.FIELDSPROOT_SAPLING.getId(), ReduxBlocks.POTTED_FIELDSPROOT_SAPLING);
        pot.addPlant(ReduxBlocks.PRISMATIC_FIELDSPROOT_SAPLING.getId(), ReduxBlocks.POTTED_PRISMATIC_FIELDSPROOT_SAPLING);
        pot.addPlant(ReduxBlocks.AZURE_FIELDSPROOT_SAPLING.getId(), ReduxBlocks.POTTED_AZURE_FIELDSPROOT_SAPLING);
        pot.addPlant(ReduxBlocks.SPECTRAL_FIELDSPROOT_SAPLING.getId(), ReduxBlocks.POTTED_SPECTRAL_FIELDSPROOT_SAPLING);
        pot.addPlant(ReduxBlocks.FLAREBLOSSOM.getId(), ReduxBlocks.POTTED_FLAREBLOSSOM);
        pot.addPlant(ReduxBlocks.INFERNIA.getId(), ReduxBlocks.POTTED_INFERNIA);
    }


    private static <T extends Block> RegistryObject<T> registerItem(final String name, final Supplier<? extends T> block, Function<RegistryObject<T>, Supplier<? extends Item>> item)
    {
        RegistryObject<T> obj = ReduxBlocks.BLOCKS.register(name, block);
        ITEMS.register(name, item.apply(obj));
        return obj;
    }

    private static <B extends Block> RegistryObject<B> registerTile(String name, Supplier<? extends B> block) {
        return registerBaseTile(name, block, ReduxBlocks::registerBlockItem);
    }

    private static <T extends Block> RegistryObject<T> registerBaseTile(final String name, final Supplier<? extends T> block, BiFunction<RegistryObject<T>, CreativeModeTab, Supplier<? extends Item>> item)
    {
        RegistryObject<T> obj = BLOCKS.register(name, block);
        ITEMS.register(name, item.apply(obj, AetherCreativeTabs.AETHER_BLOCKS));
        return obj;
    }

    private static <B extends Block> Supplier<BlockItem> registerBlockItem(final RegistryObject<B> blockRegistryObject, CreativeModeTab tab) {
        return () -> {
            B block = Objects.requireNonNull(blockRegistryObject.get());
            if (block == SKYROOT_CRAFTING_TABLE.get() || block == SKYROOT_LADDER.get()) {
                return new WoodenBlockItem(block, new Item.Properties().tab(tab));
            } else if (block == SKYROOT_CHEST.get()) {
                return new EntityBlockItem(block, SkyrootChestBlockEntity::new, new Item.Properties().tab(tab));
            } else if (block == TRAPPED_SKYROOT_CHEST.get()) {
                return new EntityBlockItem(block, TrappedSkyrootChestBlockEntity::new, new Item.Properties().tab(tab));
            } else if (block == SKYROOT_CHEST_MIMIC.get()) {
                return new EntityBlockItem(block, SkyrootChestMimicBlockEntity::new, new Item.Properties().tab(tab));
            } else {
                return new BlockItem(block, new Item.Properties().tab(tab));
            }
        };
    }

    /** NOTE: will always set the tab to {@link AetherCreativeTabs#AETHER_BLOCKS } */
    public static <T extends Block> RegistryObject<T> register(final String name, final Supplier<? extends T> block) {
        return registerItem(name, block, object -> () -> new BlockItem(object.get(), new Item.Properties().tab(AetherCreativeTabs.AETHER_BLOCKS)));
    }
    public static <T extends Block> RegistryObject<T> registerNoTab(final String name, final Supplier<? extends T> block) {
        return registerItem(name, block, object -> () -> new BlockItem(object.get(), new Item.Properties()));
    }

    public static <T extends Block> RegistryObject<T> registerModifyItemProperties(final String name, final Supplier<? extends T> block, UnaryOperator<Item.Properties> propertyModifier) {
        return registerItem(name, block, object -> () -> new BlockItem(object.get(), propertyModifier.apply(new Item.Properties())));
    }
    public static boolean ocelotOrParrot(BlockState pstate, BlockGetter level, BlockPos pos, EntityType<?> entityType) {
        return entityType == EntityType.OCELOT || entityType == EntityType.PARROT;
    }

    public static boolean never(BlockState state, BlockGetter level, BlockPos ps) {
        return false;
    }

    public static <A> boolean never(BlockState state, BlockGetter level, BlockPos pos, A whatDoINameThisParameter) {
        return false;
    }

    public static boolean always(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

}
