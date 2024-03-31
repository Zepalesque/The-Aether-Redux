package net.zepalesque.redux.world.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.jetbrains.annotations.NotNull;

public final class HeightThresholdJigsawStructure extends Structure {
    public static final int MAX_TOTAL_STRUCTURE_RANGE = 128;
    public static final Codec<HeightThresholdJigsawStructure> CODEC = ExtraCodecs.validate(RecordCodecBuilder.mapCodec((builder) -> {
        return builder.group(settingsCodec(builder), StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter((structure) -> {
            return structure.startPool;
        }), ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter((structure) -> {
            return structure.startJigsawName;
        }), Codec.intRange(0, 7).fieldOf("size").forGetter((structure) -> {
            return structure.maxDepth;
        }), HeightProvider.CODEC.fieldOf("start_height").forGetter((structure) -> {
            return structure.startHeight;
        }), Codec.BOOL.fieldOf("use_expansion_hack").forGetter((structure) -> {
            return structure.useExpansionHack;
        }), Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter((structure) -> {
            return structure.projectStartToHeightmap;
        }), Codec.intRange(1, MAX_TOTAL_STRUCTURE_RANGE).fieldOf("max_distance_from_center").forGetter((structure) -> {
            return structure.maxDistanceFromCenter;
        }), Codec.INT.optionalFieldOf("min_height").forGetter((structure) -> {
            return structure.heightMin;
        }), Codec.INT.optionalFieldOf("max_height").forGetter((structure) -> {
            return structure.heightMax;
        })).apply(builder, HeightThresholdJigsawStructure::new);
    }), HeightThresholdJigsawStructure::verifyRange).codec();
    private final Holder<StructureTemplatePool> startPool;
    private final Optional<ResourceLocation> startJigsawName;
    private final int maxDepth;
    private final HeightProvider startHeight;
    private final Optional<Integer> heightMin;
    private final Optional<Integer> heightMax;
    private final boolean useExpansionHack;
    private final Optional<Heightmap.Types> projectStartToHeightmap;
    private final int maxDistanceFromCenter;

    private static DataResult<HeightThresholdJigsawStructure> verifyRange(HeightThresholdJigsawStructure structure) {
        byte b0;
        TerrainAdjustment terrainAdjustment = structure.terrainAdaptation();
        if (terrainAdjustment == TerrainAdjustment.NONE) {
            b0 = 0;
        } else if (terrainAdjustment == TerrainAdjustment.BURY || terrainAdjustment == TerrainAdjustment.BEARD_THIN || terrainAdjustment == TerrainAdjustment.BEARD_BOX) {
            b0 = 12;
        } else {
            throw new IncompatibleClassChangeError();
        }

        return structure.maxDistanceFromCenter + b0 > MAX_TOTAL_STRUCTURE_RANGE ? DataResult.error(() -> "Structure size including terrain adaptation must not exceed " + MAX_TOTAL_STRUCTURE_RANGE) : DataResult.success(structure);
    }

    public HeightThresholdJigsawStructure(Structure.StructureSettings settings, Holder<StructureTemplatePool> startPool, Optional<ResourceLocation> startJigsawName, int maxDepth, HeightProvider startHeight, boolean useExpansionHack, Optional<Heightmap.Types> projectStartToHeightmap, int maxDistanceToCenter, Optional<Integer> minY, Optional<Integer> maxY) {
        super(settings);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.maxDepth = maxDepth;
        this.startHeight = startHeight;
        this.useExpansionHack = useExpansionHack;
        this.projectStartToHeightmap = projectStartToHeightmap;
        this.maxDistanceFromCenter = maxDistanceToCenter;
        this.heightMin = minY;
        this.heightMax = maxY;
    }

    public HeightThresholdJigsawStructure(Structure.StructureSettings settings, Holder<StructureTemplatePool> startPool, int maxDepth, HeightProvider startHeight, boolean useExpansionHack, Heightmap.Types projectStartToHeightmap, Optional<Integer> minY, Optional<Integer> maxY) {
        this(settings, startPool, Optional.empty(), maxDepth, startHeight, useExpansionHack, Optional.of(projectStartToHeightmap), 80, minY, maxY);
    }

    public HeightThresholdJigsawStructure(Structure.StructureSettings settings, Holder<StructureTemplatePool> startPool, int maxDepth, HeightProvider startHeight, boolean useExpansionHack, Optional<Integer> minY, Optional<Integer> maxY) {
        this(settings, startPool, Optional.empty(), maxDepth, startHeight, useExpansionHack, Optional.empty(), 80, minY, maxY);
    }
    private boolean extraSpawningChecks(GenerationContext context) {
        if (this.heightMin.isEmpty() && this.heightMax.isEmpty()) {
            return true;
        }
        ChunkPos chunkpos = context.chunkPos();
        return this.withinRange(context.chunkGenerator().getFirstOccupiedHeight(
                chunkpos.getMiddleBlockX(),
                chunkpos.getMiddleBlockZ(),
                Heightmap.Types.WORLD_SURFACE_WG,
                context.heightAccessor(),
                context.randomState())) ;
    }

    private boolean withinRange(int i) {
        return (this.heightMin.isEmpty() || i >= this.heightMin.get()) && (this.heightMax.isEmpty() || i <= this.heightMax.get());
    }

    public @NotNull Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
        if (!extraSpawningChecks(context)) {
            return Optional.empty();
        }
        ChunkPos chunkpos = context.chunkPos();
        int i = this.startHeight.sample(context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));

        BlockPos blockpos = new BlockPos(chunkpos.getMinBlockX(), i, chunkpos.getMinBlockZ());
        return JigsawPlacement.addPieces(context, this.startPool, this.startJigsawName, this.maxDepth, blockpos, this.useExpansionHack, this.projectStartToHeightmap, this.maxDistanceFromCenter);
    }

    public StructureType<?> type() {
        return ReduxStructureTypes.HEIGHT_THRESHOLD_JIGSAW.get();
    }
}
