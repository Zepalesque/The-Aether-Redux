package net.zepalesque.redux.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import net.zepalesque.redux.util.math.MathUtil;
import net.zepalesque.redux.world.feature.config.CloudLayerConfig;

import java.util.List;

public class StoneLayerFeature extends Feature<CloudLayerConfig> {

    public static final PerlinSimplexNoise base_noise = new PerlinSimplexNoise(new XoroshiroRandomSource(42), List.of(0, 1, 2, 3, 4));
    public static final PerlinSimplexNoise y_offset = new PerlinSimplexNoise(new XoroshiroRandomSource(95), List.of(0, 1));

    public StoneLayerFeature(Codec<CloudLayerConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<CloudLayerConfig> context) {
        int chunkX = context.origin().getX() - (context.origin().getX() % 16);
        int chunkZ = context.origin().getZ() - (context.origin().getZ() % 16);
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {

                double scale = context.config().scaleXZ() * 0.00375;
                int xCoord = chunkX + x;
                int zCoord = chunkZ + z;
                double yOffset = y_offset.getValue(xCoord * scale * 0.75D, zCoord * scale * 0.75D, false);
                float offs = (float) Mth.lerp(Mth.inverseLerp(yOffset, -0.5, 0.5), 0D, 10D);
                    float blocksUp = 1 + offs;
                float blocksDown = 1 - offs;

                for (int i = Mth.floor(-blocksDown); i <= Mth.floor(blocksUp); i++) {
                    int y = Mth.clamp(context.config().yLevel() + i, context.level().getMinBuildHeight(), context.level().getMaxBuildHeight());
                    BlockPos pos = new BlockPos(xCoord, y, zCoord);
                    if (context.config().predicate().test(context.level(), pos)) {
                        this.setBlock(context.level(), pos, context.config().block().getState(context.random(), pos));
                    }
                }
            }
        }
        return false;
    }



}
