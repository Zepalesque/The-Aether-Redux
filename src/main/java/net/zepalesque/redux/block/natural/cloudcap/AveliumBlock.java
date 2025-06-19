package net.zepalesque.redux.block.natural.cloudcap;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.natural.AetherGrassBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.natural.blight.CustomAetherGrassBlock;
import net.zepalesque.redux.data.resource.ReduxPlacements;

import java.util.List;

// TODO: Does
public class AveliumBlock extends CustomAetherGrassBlock {
    public AveliumBlock(Properties properties) {
        super(ReduxPlacements.AVELIUM_BONEMEAL, properties);
    }
}
