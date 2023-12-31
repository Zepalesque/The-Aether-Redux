package net.zepalesque.redux.world.tree.trunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class GenesisSkinnyHookedTrunkPlacer extends BaseGenesisHookedTrunkPlacer {
    public static final Codec<GenesisSkinnyHookedTrunkPlacer> CODEC = RecordCodecBuilder.create((codec) -> trunkPlacerParts(codec)
            .and(BlockStateProvider.CODEC.fieldOf("skinnyTrunkProvider").forGetter((placer) -> placer.skinnyTrunkProvider))
            .apply(codec, GenesisSkinnyHookedTrunkPlacer::new));
    private final BlockStateProvider skinnyTrunkProvider;

    public GenesisSkinnyHookedTrunkPlacer(int height, int heightRandA, int heightRandB, BlockStateProvider skinnyTrunkProvider) {
        super(height, heightRandA, heightRandB);
        this.skinnyTrunkProvider = skinnyTrunkProvider;
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ReduxTrunkPlacers.GENESIS_SKINNY_HOOKED_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, int height, BlockPos pos, TreeConfiguration config) {
        List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();

        list.addAll(this.placeVerticalTrunk(level, blockSetter, random, height, pos, this.skinnyTrunkProvider));
        list.addAll(this.placeBranches(level, blockSetter, random, height, pos, config));

        return list;
    }

    @Override
    public boolean isTrunk(LevelSimulatedReader level, BlockPos pos) {
        return level.isStateAtPosition(pos, (state) -> state.is(BlockTags.LOGS));
    }
}