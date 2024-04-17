package net.zepalesque.redux.util;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.world.structurepiece.AetherStructurePieceTypes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.config.ReduxConfig;

import java.util.ArrayList;
import java.util.List;

public class ReduxMixinHooks {
    public static List<StructurePieceType> getValidPieceTypes() {
        List<StructurePieceType> types = new ArrayList<>();
        types.add(AetherStructurePieceTypes.SILVER_DUNGEON_ROOM.get());
        types.add(AetherStructurePieceTypes.BRONZE_DUNGEON_ROOM.get());
        return types;
    }

    public static BlockState replaceChest(BlockState state, StructurePieceType structurePieceType) {
        if (getValidPieceTypes().contains(structurePieceType) && ReduxConfig.COMMON.skyroot_dungeon_chests.get()) {
            if (state.is(Blocks.CHEST)) {
                return ReduxBlocks.SKYROOT_CHEST.get().withPropertiesOf(state);
            } else if (state.is(AetherBlocks.CHEST_MIMIC.get())) {
                return ReduxBlocks.SKYROOT_CHEST_MIMIC.get().withPropertiesOf(state);
            }
        }
        return state;
    }
}