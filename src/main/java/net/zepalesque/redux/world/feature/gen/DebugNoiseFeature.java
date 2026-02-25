package net.zepalesque.redux.world.feature.gen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.zepalesque.zenith.api.world.density.PerlinNoiseFunction;
import net.zepalesque.zenith.util.math.MathUtil;

import java.util.List;

public class DebugNoiseFeature extends Feature<DebugNoiseFeature.Config> {
    
    public DebugNoiseFeature(Codec<Config> codec) {
        super(codec);
    }
    
    @Override
    public boolean place(FeaturePlaceContext<DebugNoiseFeature.Config> context) {
        var cfg = context.config();
        var lvl = context.level();
        var gradient = cfg.gradient();
        
        var noiseFun = cfg.noise();
        
        var visitor = PerlinNoiseFunction.createOrGetVisitor(lvl.getSeed());
        noiseFun.mapAll(visitor);
        // The feature should be placed once per chunk as it places one-chunk pieces of the noise
        var chunkX = context.origin().getX() - context.origin().getX() % 16;
        var chunkZ = context.origin().getZ() - context.origin().getZ() % 16;
        // Place blocks across the entire chunk
        for (var x = 0; x < 16; x++) {
            for (var z = 0; z < 16; z++) {
                // calculate new coords based on the for loops' values
                var xCoord = chunkX + x;
                var yCoord = cfg.yLevel();
                var zCoord = chunkZ + z;
                
                var initCalc = noiseFun.compute(new DensityFunction.SinglePointContext(xCoord, cfg.sampleY(), zCoord));
                var inverped = MathUtil.clampedInverseLerp(initCalc, -0.5, 0.5);
                
                var size = gradient.size();
                var index = Mth.clamp(Mth.floor(size * inverped), 0, size - 1);
                var pos = new BlockPos(xCoord, yCoord, zCoord);
                this.setBlock(context.level(), pos, gradient.get(index).getState(context.random(), pos));
            }
        }
        return true;
    }
    
    public record Config(
        List<BlockStateProvider> gradient,
        int yLevel,
        int sampleY,
        DensityFunction noise
    ) implements FeatureConfiguration {
        public static final Codec<DebugNoiseFeature.Config> CODEC = RecordCodecBuilder.create(
            builder -> builder.group(
                BlockStateProvider.CODEC.listOf().fieldOf("gradient").forGetter(DebugNoiseFeature.Config::gradient),
                Codec.INT.fieldOf("y_level").forGetter(DebugNoiseFeature.Config::yLevel),
                Codec.INT.fieldOf("sample_y").forGetter(DebugNoiseFeature.Config::yLevel),
                DensityFunction.HOLDER_HELPER_CODEC.fieldOf("noise").forGetter(DebugNoiseFeature.Config::noise) // lack of trailing commas my beloathed
            ).apply(builder, DebugNoiseFeature.Config::new)
        );
    }
}
