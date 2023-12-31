package net.zepalesque.redux.event.hook;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.loot.AetherLootContexts;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.level.BlockEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.blockhandler.WoodHandler;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.loot.ReduxLoot;

import java.util.List;
import java.util.Map;

public class ReduxToolHooks {


        public static final Map<Block, Block> STRIPPABLES = Maps.newHashMap(new ImmutableMap.Builder<Block, Block>()

            .build());


    static {

        for (WoodHandler woodHandler : Redux.Handlers.Wood.WOOD_HANDLERS) {
            if (woodHandler.hasStrippedLogs) {
                STRIPPABLES.put(woodHandler.log.get(), woodHandler.strippedLog.get().get());
                STRIPPABLES.put(woodHandler.wood.get(), woodHandler.strippedWood.get().get());
                STRIPPABLES.put(woodHandler.logWall.get(), woodHandler.strippedLogWall.get().get());
                STRIPPABLES.put(woodHandler.woodWall.get(), woodHandler.strippedWoodWall.get().get());
                woodHandler.sporingLog.ifPresent((log) -> STRIPPABLES.put(log.get(), woodHandler.strippedLog.get().get()));
                woodHandler.sporingWood.ifPresent((wood) -> STRIPPABLES.put(wood.get(), woodHandler.strippedWood.get().get()));
            }
        }
    }

    /**
     * Blocks able to be flattened with {@link ToolActions#SHOVEL_FLATTEN}, and the equivalent result block.
     */
    public static final Map<Block, Block> FLATTENABLES = Maps.newHashMap((new ImmutableMap.Builder<Block, Block>())
            .put(ReduxBlocks.BLIGHTED_AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_DIRT_PATH.get())
            .put(ReduxBlocks.FROSTED_AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_DIRT_PATH.get())
            .put(ReduxBlocks.AEVELIUM.get(), AetherBlocks.AETHER_DIRT_PATH.get())
            .put(ReduxBlocks.LIGHTROOT_AETHER_DIRT.get(), AetherBlocks.AETHER_DIRT_PATH.get())
            .build());

    /**
     * Blocks able to be tilled with {@link ToolActions#HOE_TILL}, and the equivalent result block.
     */
    public static final Map<Block, Block> TILLABLES = Maps.newHashMap((new ImmutableMap.Builder<Block, Block>())
            .put(ReduxBlocks.BLIGHTED_AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_FARMLAND.get())
            .put(ReduxBlocks.FROSTED_AETHER_GRASS_BLOCK.get(), AetherBlocks.AETHER_FARMLAND.get())
            .put(ReduxBlocks.AEVELIUM.get(), AetherBlocks.AETHER_FARMLAND.get())
            .put(ReduxBlocks.LIGHTROOT_AETHER_DIRT.get(), AetherBlocks.AETHER_FARMLAND.get())
            .build());

    /**
     * Handles modifying blocks when a {@link ToolAction} is performed on them.
     * @param accessor The {@link LevelAccessor} of the level.
     * @param pos The {@link Block} within the level.
     * @param old The old {@link BlockState} of the block an action is being performed on.
     * @param action The {@link ToolAction} being performed on the block.
     * @return The new {@link BlockState} of the block.
     * @see com.aetherteam.aether.event.listeners.abilities.ToolAbilityListener#setupToolModifications(BlockEvent.BlockToolModificationEvent)
     */
    public static BlockState setupToolActions(LevelAccessor accessor, BlockPos pos, BlockState old, ToolAction action) {
        Block oldBlock = old.getBlock();
        if (action == ToolActions.AXE_STRIP) {
            if (STRIPPABLES.containsKey(oldBlock)) {
                return STRIPPABLES.get(oldBlock).withPropertiesOf(old);
            }
        } else if (action == ToolActions.SHOVEL_FLATTEN) {
            if (FLATTENABLES.containsKey(oldBlock)) {
                return FLATTENABLES.get(oldBlock).withPropertiesOf(old);
            }
        } else if (action == ToolActions.HOE_TILL) {
            if (accessor.getBlockState(pos.above()).isAir()) {
                if (TILLABLES.containsKey(oldBlock)) {
                    return TILLABLES.get(oldBlock).withPropertiesOf(old);
                }
            }
        }
        return old;
    }


    public static void stripBlightwillow(LevelAccessor accessor, BlockState state, ItemStack stack, ToolAction action, UseOnContext context) {
        if (action == ToolActions.AXE_STRIP) {
            if (accessor instanceof Level level) {
                if (Redux.Handlers.Wood.BLIGHTWILLOW.sporingBlocksBlockTag.isPresent() && state.is(Redux.Handlers.Wood.BLIGHTWILLOW.sporingBlocksBlockTag.get()) && stack.is(AetherTags.Items.GOLDEN_AMBER_HARVESTERS)) {
                    if (level.getServer() != null && level instanceof ServerLevel serverLevel) {
                        Vec3 vector = context.getClickLocation();
                        LootParams parameters = (new LootParams.Builder(serverLevel)).withParameter(LootContextParams.TOOL, stack).create(AetherLootContexts.STRIPPING);
                        LootTable lootTable = level.getServer().getLootData().getLootTable(ReduxLoot.STRIP_BLIGHTWILLOW);
                        List<ItemStack> list = lootTable.getRandomItems(parameters);
                        for (ItemStack itemStack : list) {
                            ItemEntity itemEntity = new ItemEntity(level, vector.x(), vector.y(), vector.z(), itemStack);
                            itemEntity.setDefaultPickUpDelay();
                            level.addFreshEntity(itemEntity);
                        }
                    }
                }
            }
        }
    }


}
