package net.zepalesque.redux.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.zepalesque.redux.mixin.common.world.ChunkAccessAccessor;
import net.zepalesque.redux.world.feature.config.SurfaceRuleLakeConfig;

import java.util.Optional;

public class SurfaceRuleLakeFeature extends Feature<SurfaceRuleLakeConfig> {
    private static final BlockState AIR;

    public SurfaceRuleLakeFeature(Codec<SurfaceRuleLakeConfig> p_66259_) {
        super(p_66259_);
    }

    public boolean place(FeaturePlaceContext<SurfaceRuleLakeConfig> context) {
        BlockPos blockpos = context.origin();
        WorldGenLevel worldgenlevel = context.level();
        RandomSource random = context.random();
        SurfaceRuleLakeConfig config = context.config();
        if (blockpos.getY() <= worldgenlevel.getMinBuildHeight() + 4) {
            return false;
        } else {
            blockpos = blockpos.below(4);
            boolean[] aboolean = new boolean[2048];
            int i = random.nextInt(4) + 4;

            for(int j = 0; j < i; ++j) {
                double d0 = random.nextDouble() * 6.0 + 3.0;
                double d1 = random.nextDouble() * 4.0 + 2.0;
                double d2 = random.nextDouble() * 6.0 + 3.0;
                double d3 = random.nextDouble() * (16.0 - d0 - 2.0) + 1.0 + d0 / 2.0;
                double d4 = random.nextDouble() * (8.0 - d1 - 4.0) + 2.0 + d1 / 2.0;
                double d5 = random.nextDouble() * (16.0 - d2 - 2.0) + 1.0 + d2 / 2.0;

                for(int l = 1; l < 15; ++l) {
                    for(int i1 = 1; i1 < 15; ++i1) {
                        for(int j1 = 1; j1 < 7; ++j1) {
                            double d6 = ((double)l - d3) / (d0 / 2.0);
                            double d7 = ((double)j1 - d4) / (d1 / 2.0);
                            double d8 = ((double)i1 - d5) / (d2 / 2.0);
                            double d9 = d6 * d6 + d7 * d7 + d8 * d8;
                            if (d9 < 1.0) {
                                aboolean[(l * 16 + i1) * 8 + j1] = true;
                            }
                        }
                    }
                }
            }

            BlockState blockstate1 = config.fluid().getState(random, blockpos);

            int i2;
            int k1;
            int j3;
            for(k1 = 0; k1 < 16; ++k1) {
                for(i2 = 0; i2 < 16; ++i2) {
                    for(j3 = 0; j3 < 8; ++j3) {
                        boolean flag = !aboolean[(k1 * 16 + i2) * 8 + j3] && (k1 < 15 && aboolean[((k1 + 1) * 16 + i2) * 8 + j3] || k1 > 0 && aboolean[((k1 - 1) * 16 + i2) * 8 + j3] || i2 < 15 && aboolean[(k1 * 16 + i2 + 1) * 8 + j3] || i2 > 0 && aboolean[(k1 * 16 + (i2 - 1)) * 8 + j3] || j3 < 7 && aboolean[(k1 * 16 + i2) * 8 + j3 + 1] || j3 > 0 && aboolean[(k1 * 16 + i2) * 8 + (j3 - 1)]);
                        if (flag) {
                            BlockState testState = worldgenlevel.getBlockState(blockpos.offset(k1, j3, i2));
                            if (j3 >= 4 && testState.liquid()) {
                                return false;
                            }

                            if (j3 < 4 && !testState.isSolid() && worldgenlevel.getBlockState(blockpos.offset(k1, j3, i2)) != blockstate1) {
                                return false;
                            }
                        }
                    }
                }
            }

            BlockPos blockpos2;
            for(k1 = 0; k1 < 16; ++k1) {
                for(i2 = 0; i2 < 16; ++i2) {
                    for(j3 = 0; j3 < 8; ++j3) {
                        if (aboolean[(k1 * 16 + i2) * 8 + j3]) {
                            blockpos2 = blockpos.offset(k1, j3, i2);
                            if (this.canReplaceBlock(worldgenlevel.getBlockState(blockpos2))) {
                                boolean flag1 = j3 >= 4;
                                worldgenlevel.setBlock(blockpos2, flag1 ? AIR : blockstate1, 2);
                                if (flag1) {
                                    worldgenlevel.scheduleTick(blockpos2, AIR.getBlock(), 0);
                                    this.markAboveForPostProcessing(worldgenlevel, blockpos2);
                                }
                            }
                        }
                    }
                }
            }

                for(i2 = 0; i2 < 16; ++i2) {
                    for(j3 = 0; j3 < 16; ++j3) {
                        for(int j4 = 4; j4 < 8; ++j4) {
                            if (aboolean[(i2 * 16 + j3) * 8 + j4]) {
                                BlockPos blockpos3 = blockpos.offset(i2, j4 - 1, j3);
                                if (isDirt(worldgenlevel.getBlockState(blockpos3)) && worldgenlevel.getBrightness(LightLayer.SKY, blockpos.offset(i2, j4, j3)) > 0) {
                                    if (context.level().getChunkSource() instanceof ServerChunkCache serverChunkCache) {
                                        if (serverChunkCache.getGenerator() instanceof NoiseBasedChunkGenerator noiseBasedChunkGenerator) {
                                            NoiseGeneratorSettings settingsHolder = noiseBasedChunkGenerator.generatorSettings().get();
                                            SurfaceRules.RuleSource surfaceRule = settingsHolder.surfaceRule();
                                            ChunkAccess chunkAccess = context.level().getChunk(blockpos3);
                                            NoiseChunk noisechunk = ((ChunkAccessAccessor) chunkAccess).getNoiseChunk();
                                            if (noisechunk != null) {
                                                CarvingContext carvingcontext = new CarvingContext(noiseBasedChunkGenerator, context.level().registryAccess(), chunkAccess.getHeightAccessorForGeneration(), noisechunk, serverChunkCache.randomState(), surfaceRule);
                                                Optional<BlockState> state = carvingcontext.topMaterial(context.level().getBiomeManager()::getBiome, chunkAccess, blockpos3, false);
                                                state.ifPresent(blockState -> worldgenlevel.setBlock(blockpos3, blockState, 2));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            if (blockstate1.getFluidState().is(FluidTags.WATER)) {
                for(i2 = 0; i2 < 16; ++i2) {
                    for(j3 = 0; j3 < 16; ++j3) {
                        blockpos2 = blockpos.offset(i2, 4, j3);
                        if (worldgenlevel.getBiome(blockpos2).value().shouldFreeze(worldgenlevel, blockpos2, false) && this.canReplaceBlock(worldgenlevel.getBlockState(blockpos2))) {
                            worldgenlevel.setBlock(blockpos2, Blocks.ICE.defaultBlockState(), 2);
                        }
                    }
                }
            }

            return true;
        }
    }

    private boolean canReplaceBlock(BlockState p_190952_) {
        return !p_190952_.is(BlockTags.FEATURES_CANNOT_REPLACE);
    }

    static {
        AIR = Blocks.CAVE_AIR.defaultBlockState();
    }
}
