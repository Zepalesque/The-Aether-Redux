package net.zepalesque.redux.block.natural.blight;

import com.aetherteam.aether.block.natural.AetherGrassBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;
import java.util.Optional;

// Allows for some easier modifications ofa
public class CustomAetherGrassBlock extends AetherGrassBlock {
    
    protected final ResourceKey<PlacedFeature> feature;
    
    public CustomAetherGrassBlock(ResourceKey<PlacedFeature> feature, Properties properties) {
        super(properties);
        this.feature = feature;
    }
    
    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos abovePos = pos.above();
        Block grass = this.bonemealPropogationBlock();
        Optional<Holder<PlacedFeature>> grassFeatureOptional = level.registryAccess().registryOrThrow(Registry.PLACED_FEATURE_REGISTRY).getHolder(this.getFeatureKey());
        
        start:
        for (int i = 0; i < 128; ++i) {
            BlockPos blockPos = abovePos;
            
            for (int j = 0; j < i / 16; ++j) {
                blockPos = blockPos.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                if (!level.getBlockState(blockPos.below()).is(this) || level.getBlockState(blockPos).isCollisionShapeFullBlock(level, blockPos))
                    continue start;
            }
            
            BlockState blockState = level.getBlockState(blockPos);
            if (blockState.is(grass) && random.nextInt(10) == 0)
                ((BonemealableBlock) grass).performBonemeal(level, random, blockPos, blockState);
            
            if (blockState.isAir()) {
                Holder<PlacedFeature> featureHolder;
                if (random.nextInt(8) == 0) {
                    List<ConfiguredFeature<?, ?>> list = level.getBiome(blockPos).value().getGenerationSettings().getFlowerFeatures();
                    if (list.isEmpty()) continue;
                    featureHolder = ((RandomPatchConfiguration) list.get(random.nextInt(list.size())).config()).feature();
                } else {
                    if (grassFeatureOptional.isEmpty()) continue;
                    featureHolder = grassFeatureOptional.get();
                }
                featureHolder.value().place(level, level.getChunkSource().getGenerator(), random, blockPos);
            }
        }
    }
    
    protected ResourceKey<PlacedFeature> getFeatureKey() {
        return this.feature;
    }
    
    protected Block bonemealPropogationBlock() {
        return this;
    }
}
