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
import org.spongepowered.asm.mixin.Unique;

public class ReduxDungeonProcessors {

    public static final RuleProcessor BRONZE_BLOCKS = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new RandomBlockMatchTest(ReduxBlocks.CARVED_STONE_BASE.get(), 0.1F), AlwaysTrueTest.INSTANCE, ReduxBlocks.SENTRY_STONE_BASE.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockStateMatchTest(ReduxBlocks.CARVED_STONE_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 0.1F), AlwaysTrueTest.INSTANCE, ReduxBlocks.SENTRY_STONE_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.X)),
            new ProcessorRule(new RandomBlockStateMatchTest(ReduxBlocks.CARVED_STONE_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y), 0.1F), AlwaysTrueTest.INSTANCE, ReduxBlocks.SENTRY_STONE_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y)),
            new ProcessorRule(new RandomBlockStateMatchTest(ReduxBlocks.CARVED_STONE_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 0.1F), AlwaysTrueTest.INSTANCE, ReduxBlocks.SENTRY_STONE_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z))
    ));

    public static final RuleProcessor BRONZE_TRAPS = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new RandomBlockMatchTest(ReduxBlocks.CARVED_STONE_BASE.get(), 0.1F), AlwaysTrueTest.INSTANCE, ReduxBlocks.TRAPPED_CARVED_STONE_BASE.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockStateMatchTest(ReduxBlocks.CARVED_STONE_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 0.1F), AlwaysTrueTest.INSTANCE, ReduxBlocks.TRAPPED_CARVED_STONE_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.X)),
            new ProcessorRule(new RandomBlockStateMatchTest(ReduxBlocks.CARVED_STONE_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y), 0.1F), AlwaysTrueTest.INSTANCE, ReduxBlocks.TRAPPED_CARVED_STONE_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y)),
            new ProcessorRule(new RandomBlockStateMatchTest(ReduxBlocks.CARVED_STONE_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 0.1F), AlwaysTrueTest.INSTANCE, ReduxBlocks.TRAPPED_CARVED_STONE_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z)),
            new ProcessorRule(new RandomBlockMatchTest(ReduxBlocks.SENTRY_STONE_BASE.get(), 0.1F), AlwaysTrueTest.INSTANCE, ReduxBlocks.TRAPPED_SENTRY_STONE_BASE.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockStateMatchTest(ReduxBlocks.SENTRY_STONE_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 0.1F), AlwaysTrueTest.INSTANCE, ReduxBlocks.TRAPPED_SENTRY_STONE_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.X)),
            new ProcessorRule(new RandomBlockStateMatchTest(ReduxBlocks.SENTRY_STONE_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y), 0.1F), AlwaysTrueTest.INSTANCE, ReduxBlocks.TRAPPED_SENTRY_STONE_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y)),
            new ProcessorRule(new RandomBlockStateMatchTest(ReduxBlocks.SENTRY_STONE_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 0.1F), AlwaysTrueTest.INSTANCE, ReduxBlocks.TRAPPED_SENTRY_STONE_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z))
    ));

    public static final RuleProcessor BRONZE_LOCKED = new RuleProcessor(ImmutableList.of(
            new ProcessorRule(new RandomBlockMatchTest(ReduxBlocks.LOCKED_CARVED_STONE_BASE.get(), 0.1F), AlwaysTrueTest.INSTANCE, ReduxBlocks.LOCKED_SENTRY_STONE_BASE.get().defaultBlockState()),
            new ProcessorRule(new RandomBlockStateMatchTest(ReduxBlocks.LOCKED_CARVED_STONE_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.X), 0.1F), AlwaysTrueTest.INSTANCE, ReduxBlocks.LOCKED_SENTRY_STONE_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.X)),
            new ProcessorRule(new RandomBlockStateMatchTest(ReduxBlocks.LOCKED_CARVED_STONE_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y), 0.1F), AlwaysTrueTest.INSTANCE, ReduxBlocks.LOCKED_SENTRY_STONE_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y)),
            new ProcessorRule(new RandomBlockStateMatchTest(ReduxBlocks.LOCKED_CARVED_STONE_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z), 0.1F), AlwaysTrueTest.INSTANCE, ReduxBlocks.LOCKED_SENTRY_STONE_PILLAR.get().defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z))
    ));
}
