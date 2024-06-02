package net.zepalesque.redux.block;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.natural.LeavesWithParticlesBlock;
import com.google.common.base.Supplier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.natural.AetherShortGrassBlock;
import net.zepalesque.redux.block.natural.leaves.FallingLeavesBlock;
import net.zepalesque.redux.client.particle.ReduxParticles;
import net.zepalesque.redux.event.hook.ToolActionHooks;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.world.tree.ReduxTreeGrowers;
import net.zepalesque.zenith.mixin.mixins.common.accessor.FireAccessor;

import java.util.function.Function;

public class ReduxBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Redux.MODID);

    public static DeferredBlock<AetherShortGrassBlock> SHORT_AETHER_GRASS = register("short_aether_grass",
            () -> new AetherShortGrassBlock(
                    Properties.ofFullCopy(Blocks.SHORT_GRASS)
                            .hasPostProcess((state, level, pos) -> true)
            ));

    public static final DeferredBlock<SaplingBlock> CLOUDROOT_SAPLING = register("cloudroot_sapling",
            () -> new SaplingBlock(ReduxTreeGrowers.CLOUDROOT, Block.Properties.ofFullCopy(Blocks.OAK_SAPLING)));


    public static DeferredBlock<FallingLeavesBlock> CLOUDROOT_LEAVES = register("cloudroot_leaves",
            () -> new FallingLeavesBlock(ReduxParticles.CLOUDROOT_LEAF,
                    BlockBehaviour.Properties.ofFullCopy(AetherBlocks.SKYROOT_LEAVES.get()).mapColor(MapColor.QUARTZ)));


    private static <T extends Block> DeferredBlock<T> register(final String name, final Supplier<? extends T> block, Function<DeferredBlock<T>, Supplier<? extends Item>> item) {
        DeferredBlock<T> obj = ReduxBlocks.BLOCKS.register(name, block);
        ReduxItems.ITEMS.register(name, item.apply(obj));
        return obj;
    }

    public static <T extends Block> DeferredBlock<T> register(final String name, final Supplier<? extends T> block) {
        return register(name, block, object -> () -> new BlockItem(object.get(), new Item.Properties()));
    }

    public static void registerFlammability() {
        FireAccessor accessor = (FireAccessor) Blocks.FIRE;

        Redux.WOOD_SETS.forEach(set -> set.flammables(accessor));
    }

    public static void registerToolConversions() {
        Redux.WOOD_SETS.forEach(set -> set.setupStrippables(ToolActionHooks.STRIPPABLES));
    }
}
