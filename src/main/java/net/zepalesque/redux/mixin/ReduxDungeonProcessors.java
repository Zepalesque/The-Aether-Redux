package net.zepalesque.redux.mixin;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockStateMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.zepalesque.redux.block.ReduxBlocks;

public class ReduxDungeonProcessors {



    public static final RuleProcessor BRONZE_BLOCKS = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new RandomBlockMatchTest(ReduxBlocks.CARVED_BASE.get(), 0.1F), AlwaysTrueTest.INSTANCE, ReduxBlocks.SENTRY_BASE.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockStateMatchTest(ReduxBlocks.CARVED_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 0.1F), AlwaysTrueTest.INSTANCE, ReduxBlocks.SENTRY_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.X)),
            new ProcessorRule(new RandomBlockStateMatchTest(ReduxBlocks.CARVED_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y), 0.1F), AlwaysTrueTest.INSTANCE, ReduxBlocks.SENTRY_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y)),
            new ProcessorRule(new RandomBlockStateMatchTest(ReduxBlocks.CARVED_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 0.1F), AlwaysTrueTest.INSTANCE, ReduxBlocks.SENTRY_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z))
    ));

    public static final RuleProcessor BRONZE_TRAPS = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new RandomBlockMatchTest(ReduxBlocks.CARVED_BASE.get(), 0.13F), AlwaysTrueTest.INSTANCE, ReduxBlocks.TRAPPED_CARVED_BASE.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockStateMatchTest(ReduxBlocks.CARVED_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 0.13F), AlwaysTrueTest.INSTANCE, ReduxBlocks.TRAPPED_CARVED_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.X)),
            new ProcessorRule(new RandomBlockStateMatchTest(ReduxBlocks.CARVED_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y), 0.13F), AlwaysTrueTest.INSTANCE, ReduxBlocks.TRAPPED_CARVED_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y)),
            new ProcessorRule(new RandomBlockStateMatchTest(ReduxBlocks.CARVED_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 0.13F), AlwaysTrueTest.INSTANCE, ReduxBlocks.TRAPPED_CARVED_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z)),
            new ProcessorRule(new RandomBlockMatchTest(ReduxBlocks.SENTRY_BASE.get(), 0.003F), AlwaysTrueTest.INSTANCE, ReduxBlocks.TRAPPED_SENTRY_BASE.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockStateMatchTest(ReduxBlocks.SENTRY_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 0.003F), AlwaysTrueTest.INSTANCE, ReduxBlocks.TRAPPED_SENTRY_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.X)),
            new ProcessorRule(new RandomBlockStateMatchTest(ReduxBlocks.SENTRY_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y), 0.003F), AlwaysTrueTest.INSTANCE, ReduxBlocks.TRAPPED_SENTRY_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y)),
            new ProcessorRule(new RandomBlockStateMatchTest(ReduxBlocks.SENTRY_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 0.003F), AlwaysTrueTest.INSTANCE, ReduxBlocks.TRAPPED_SENTRY_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z))
    ));

    public static final RuleProcessor BRONZE_LOCKED = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new RandomBlockMatchTest(ReduxBlocks.LOCKED_CARVED_BASE.get(), 0.05F), AlwaysTrueTest.INSTANCE, ReduxBlocks.LOCKED_SENTRY_BASE.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockStateMatchTest(ReduxBlocks.LOCKED_CARVED_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 0.05F), AlwaysTrueTest.INSTANCE, ReduxBlocks.LOCKED_SENTRY_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.X)),
            new ProcessorRule(new RandomBlockStateMatchTest(ReduxBlocks.LOCKED_CARVED_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y), 0.05F), AlwaysTrueTest.INSTANCE, ReduxBlocks.LOCKED_SENTRY_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y)),
            new ProcessorRule(new RandomBlockStateMatchTest(ReduxBlocks.LOCKED_CARVED_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 0.05F), AlwaysTrueTest.INSTANCE, ReduxBlocks.LOCKED_SENTRY_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z))
    ));
}
