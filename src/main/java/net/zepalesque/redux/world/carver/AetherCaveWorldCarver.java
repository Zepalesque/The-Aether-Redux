package net.zepalesque.redux.world.carver;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CaveWorldCarver;
import org.apache.commons.lang3.mutable.MutableBoolean;

import javax.annotation.Nullable;
import java.util.function.Function;

public class AetherCaveWorldCarver extends CaveWorldCarver {

    public AetherCaveWorldCarver(Codec<CaveCarverConfiguration> pCodec) {
        super(pCodec);
    }

    protected boolean carveBlock(CarvingContext pContext, CaveCarverConfiguration pConfig, ChunkAccess pChunk, Function<BlockPos, Holder<Biome>> pBiomeGetter, CarvingMask pCarvingMask, BlockPos.MutableBlockPos pPos, BlockPos.MutableBlockPos pCheckPos, Aquifer pAquifer, MutableBoolean pReachedSurface) {
        BlockState blockstate = pChunk.getBlockState(pPos);
        if (blockstate.is(Blocks.GRASS_BLOCK) || blockstate.is(Blocks.MYCELIUM)) {
            pReachedSurface.setTrue();
        }

        if (!this.canReplaceBlock(pConfig, blockstate) && !isDebugEnabled(pConfig)) {
            return false;
        } else {
            BlockState blockstate1 = this.getCarveState(pContext, pConfig, pPos, pAquifer);
            if (blockstate1 == null) {
                return false;
            } else {
                pChunk.setBlockState(pPos, blockstate1, false);
                if (pAquifer.shouldScheduleFluidUpdate() && !blockstate1.getFluidState().isEmpty()) {
                    pChunk.markPosForPostprocessing(pPos);
                }

                if (pReachedSurface.isTrue()) {
                    pCheckPos.setWithOffset(pPos, Direction.DOWN);
                    if (pChunk.getBlockState(pCheckPos).is(Blocks.DIRT)) {
                        pContext.topMaterial(pBiomeGetter, pChunk, pCheckPos, !blockstate1.getFluidState().isEmpty()).ifPresent((p_272572_) -> {
                            pChunk.setBlockState(pCheckPos, p_272572_, false);
                            if (!p_272572_.getFluidState().isEmpty()) {
                                pChunk.markPosForPostprocessing(pCheckPos);
                            }

                        });
                    }
                }

                return true;
            }
        }
    }
    protected static boolean isDebugEnabled(CarverConfiguration pConfig) {
        return pConfig.debugSettings.isDebugMode();
    }

    protected static BlockState getDebugState(CarverConfiguration pConfig, BlockState pState) {
        if (pState.is(Blocks.AIR)) {
            return pConfig.debugSettings.getAirState();
        } else if (pState.is(Blocks.WATER)) {
            BlockState blockstate = pConfig.debugSettings.getWaterState();
            return blockstate.hasProperty(BlockStateProperties.WATERLOGGED) ? blockstate.setValue(BlockStateProperties.WATERLOGGED, true) : blockstate;
        } else {
            return pState.is(Blocks.LAVA) ? pConfig.debugSettings.getLavaState() : pState;
        }
    }
    @Nullable
    private BlockState getCarveState(CarvingContext pContext, CaveCarverConfiguration pConfig, BlockPos pPos, Aquifer pAquifer) {

        BlockState blockstate = Blocks.CAVE_AIR.defaultBlockState();
        if (blockstate == null) {
            return isDebugEnabled(pConfig) ? pConfig.debugSettings.getBarrierState() : null;
        } else {
            return isDebugEnabled(pConfig) ? getDebugState(pConfig, blockstate) : blockstate;}
    }
}
