package net.zepalesque.redux.block;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.dungeon.DoorwayBlock;
import com.aetherteam.aether.block.dungeon.TrappedBlock;
import com.aetherteam.aether.block.natural.LeavesWithParticlesBlock;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.google.common.base.Supplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.construction.BaseLitBlock;
import net.zepalesque.redux.block.dungeon.DoorwayPillarBlock;
import net.zepalesque.redux.block.dungeon.RunelightBlock;
import net.zepalesque.redux.block.dungeon.TrappedPillarBlock;
import net.zepalesque.redux.block.natural.AetherShortGrassBlock;
import net.zepalesque.redux.block.natural.leaves.FallingLeavesBlock;
import net.zepalesque.redux.block.state.ReduxBlockBuilders;
import net.zepalesque.redux.client.particle.ReduxParticles;
import net.zepalesque.redux.event.hook.ToolActionHooks;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.world.tree.ReduxTreeGrowers;
import net.zepalesque.zenith.mixin.mixins.common.accessor.FireAccessor;

import java.util.function.Function;

public class ReduxBlocks extends ReduxBlockBuilders {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Redux.MODID);

    public static DeferredBlock<AetherShortGrassBlock> SHORT_AETHER_GRASS = register("short_aether_grass",
            () -> new AetherShortGrassBlock(
                    Properties.ofFullCopy(Blocks.SHORT_GRASS)
                            .hasPostProcess((state, level, pos) -> true)
            ));

    public static final DeferredBlock<SaplingBlock> CLOUDROOT_SAPLING = register("cloudroot_sapling",
            () -> new SaplingBlock(ReduxTreeGrowers.CLOUDROOT, Properties.ofFullCopy(Blocks.OAK_SAPLING)));

    public static DeferredBlock<FallingLeavesBlock> CLOUDROOT_LEAVES = register("cloudroot_leaves",
            () -> new FallingLeavesBlock(ReduxParticles.CLOUDROOT_LEAF,
                    BlockBehaviour.Properties.ofFullCopy(AetherBlocks.SKYROOT_LEAVES.get()).mapColor(MapColor.QUARTZ)));

    public static final DeferredBlock<Block> CARVED_STONE_PILLAR = register("carved_stone_pillar", () -> new RotatedPillarBlock(Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(0.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> SENTRY_STONE_PILLAR = register("sentry_stone_pillar", () -> new RotatedPillarBlock(Properties.ofFullCopy(CARVED_STONE_PILLAR.get()).lightLevel(state -> 11)));
    public static final DeferredBlock<Block> CARVED_STONE_BASE = register("carved_stone_base", () -> new Block(Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(0.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> SENTRY_STONE_BASE = register("sentry_stone_base", () -> new Block(Properties.ofFullCopy(CARVED_STONE_BASE.get()).lightLevel(state -> 11)));

    public static final DeferredBlock<Block> LOCKED_CARVED_STONE_PILLAR = register("locked_carved_stone_pillar", () -> new RotatedPillarBlock(Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(-1.0F, 3600000.0F)));
    public static final DeferredBlock<Block> LOCKED_SENTRY_STONE_PILLAR = register("locked_sentry_stone_pillar", () -> new RotatedPillarBlock(Properties.ofFullCopy(LOCKED_CARVED_STONE_PILLAR.get()).lightLevel(state -> 11)));
    public static final DeferredBlock<Block> LOCKED_CARVED_STONE_BASE = register("locked_carved_stone_base", () -> new Block(Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(-1.0F, 3600000.0F)));
    public static final DeferredBlock<Block> LOCKED_SENTRY_STONE_BASE = register("locked_sentry_stone_base", () -> new Block(Properties.ofFullCopy(LOCKED_CARVED_STONE_BASE.get()).lightLevel(state -> 11)));

    public static final DeferredBlock<Block> TRAPPED_CARVED_STONE_PILLAR = register("trapped_carved_stone_pillar", () -> new TrappedPillarBlock(AetherEntityTypes.SENTRY::get, () -> CARVED_STONE_PILLAR.get().defaultBlockState(), Properties.ofFullCopy(CARVED_STONE_PILLAR.get())));
    public static final DeferredBlock<Block> TRAPPED_SENTRY_STONE_PILLAR = register("trapped_sentry_stone_pillar", () -> new TrappedPillarBlock(AetherEntityTypes.SENTRY::get, () -> SENTRY_STONE_PILLAR.get().defaultBlockState(), Properties.ofFullCopy(SENTRY_STONE_PILLAR.get())));
    public static final DeferredBlock<Block> TRAPPED_CARVED_STONE_BASE = register("trapped_carved_stone_base", () -> new TrappedBlock(AetherEntityTypes.SENTRY::get, () -> CARVED_STONE_BASE.get().defaultBlockState(), Properties.ofFullCopy(CARVED_STONE_BASE.get())));
    public static final DeferredBlock<Block> TRAPPED_SENTRY_STONE_BASE = register("trapped_sentry_stone_base", () -> new TrappedBlock(AetherEntityTypes.SENTRY::get, () -> SENTRY_STONE_BASE.get().defaultBlockState(), Properties.ofFullCopy(SENTRY_STONE_BASE.get())));

    public static final DeferredBlock<Block> BOSS_DOORWAY_CARVED_STONE_PILLAR = register("boss_doorway_carved_stone_pillar", () -> new DoorwayPillarBlock(AetherEntityTypes.SLIDER::get, Properties.ofFullCopy(CARVED_STONE_PILLAR.get())));
    public static final DeferredBlock<Block> BOSS_DOORWAY_SENTRY_STONE_PILLAR = register("boss_doorway_sentry_stone_pillar", () -> new DoorwayPillarBlock(AetherEntityTypes.SLIDER::get, Properties.ofFullCopy(SENTRY_STONE_PILLAR.get())));
    public static final DeferredBlock<Block> BOSS_DOORWAY_CARVED_STONE_BASE = register("boss_doorway_carved_stone_base", () -> new DoorwayBlock(AetherEntityTypes.SLIDER::get, Properties.ofFullCopy(CARVED_STONE_BASE.get())));
    public static final DeferredBlock<Block> BOSS_DOORWAY_SENTRY_STONE_BASE = register("boss_doorway_sentry_stone_base", () -> new DoorwayBlock(AetherEntityTypes.SLIDER::get, Properties.ofFullCopy(SENTRY_STONE_BASE.get())));

    public static final DeferredBlock<Block> RUNELIGHT = register("runelight", () ->
            new RunelightBlock(Properties.of()
                    .mapColor(state -> state.getValue(BaseLitBlock.LIT) ? MapColor.COLOR_LIGHT_BLUE : MapColor.LAPIS)
                    .lightLevel(state -> state.getValue(BaseLitBlock.LIT) ? 13 : 1)
                    .strength(0.7F, 6.0F)
                    .sound(SoundType.COPPER_BULB)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE),
                    false
            ));

    public static final DeferredBlock<Block> LOCKED_RUNELIGHT = register("runelight", () ->
            new RunelightBlock(Properties.of()
                    .mapColor(state -> state.getValue(BaseLitBlock.LIT) ? MapColor.COLOR_LIGHT_BLUE : MapColor.LAPIS)
                    .lightLevel(state -> state.getValue(BaseLitBlock.LIT) ? 13 : 1)
                    .strength(-1.0F, 3600000.0F)
                    .sound(SoundType.COPPER_BULB)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE),
                    true
            ));

    public static void registerFlammability() {
        FireAccessor accessor = (FireAccessor) Blocks.FIRE;

        Redux.WOOD_SETS.forEach(set -> set.flammables(accessor));
    }

    public static void registerToolConversions() {
        Redux.WOOD_SETS.forEach(set -> set.setupStrippables(ToolActionHooks.STRIPPABLES));
    }
}
