package net.zepalesque.redux.util.level;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;

import java.util.Collection;

public class LevelUtil {



    public static boolean isBlockInAABB(AABB bb, Level world, Block block)
    {
        return isBlockInAABB(bb, world, block, true);
    }

    public static boolean isBlockInAABB(AABB bb, Level world, Block block, boolean checkLoaded)
    {
        int minX = Mth.floor(bb.minX);
        int minY = Mth.floor(bb.minY);
        int minZ = Mth.floor(bb.minZ);

        int maxX = Mth.ceil(bb.maxX);
        int maxY = Mth.ceil(bb.maxY);
        int maxZ = Mth.ceil(bb.maxZ);

        if (checkLoaded && !world.hasChunksAt(new BlockPos(minX, minY, minZ), new BlockPos(maxX, maxY, maxZ)))
        {
            return false;
        }

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        LevelChunk chunk = null;

        for (int x = minX; x < maxX; x++)
        {
            for (int z = minZ; z < maxZ; z++)
            {
                if (chunk != null && (chunk.getPos().x != (x >> 4) || chunk.getPos().z != (z >> 4)))
                {
                    chunk = null;
                }

                if (chunk == null)
                {
                    chunk = world.getChunk(x >> 4, z >> 4);
                }

                for (int y = minY; y < maxY; y++)
                {
                    pos.set(x, y, z);

                    if (world.getBlockState(pos).getBlock() == block)
                    {
                        return true;
                    }
                }

            }

        }

        return false;
    }



    public static boolean isTagInAABB(AABB bb, Level world, TagKey<Block> tag, boolean checkLoaded)
    {
        int minX = Mth.floor(bb.minX);
        int minY = Mth.floor(bb.minY);
        int minZ = Mth.floor(bb.minZ);

        int maxX = Mth.ceil(bb.maxX);
        int maxY = Mth.ceil(bb.maxY);
        int maxZ = Mth.ceil(bb.maxZ);

        if (checkLoaded && !world.hasChunksAt(new BlockPos(minX, minY, minZ), new BlockPos(maxX, maxY, maxZ)))
        {
            return false;
        }

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        LevelChunk chunk = null;

        for (int x = minX; x < maxX; x++)
        {
            for (int z = minZ; z < maxZ; z++)
            {
                if (chunk != null && (chunk.getPos().x != (x >> 4) || chunk.getPos().z != (z >> 4)))
                {
                    chunk = null;
                }

                if (chunk == null)
                {
                    chunk = world.getChunk(x >> 4, z >> 4);
                }

                for (int y = minY; y < maxY; y++)
                {
                    pos.set(x, y, z);

                    if (world.getBlockState(pos).is(tag))
                    {
                        return true;
                    }
                }

            }

        }

        return false;
    }

//    @OnlyIn(Dist.DEDICATED_SERVER)
//    Map<ChunkPos, LevelChunk> CHECKED_CHUNKS_SERVER;
//
//    @OnlyIn(Dist.CLIENT)
//    Map<ChunkPos, LevelChunk> CHECKED_CHUNKS_SERVER_CLIENT;


    public static boolean isTagInAABB1(AABB bb, Level world, TagKey<Block> tag)
    {
        int minX = Mth.floor(bb.minX);
        int minY = Mth.floor(bb.minY);
        int minZ = Mth.floor(bb.minZ);

        int maxX = Mth.ceil(bb.maxX);
        int maxY = Mth.ceil(bb.maxY);
        int maxZ = Mth.ceil(bb.maxZ);

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        for (int x = minX; x < maxX; x++)
        {
            for (int z = minZ; z < maxZ; z++)
            {
                for (int y = minY; y < maxY; y++)
                {
                    pos.set(x, y, z);

                    if (world.getBlockState(pos).is(tag))
                    {
                        return true;
                    }
                }

            }

        }

        return false;
    }


    public static boolean isCollectionInAABB(AABB bb, Level level, Collection<Block> collection)
    {
        int minX = Mth.floor(bb.minX);
        int minY = Mth.floor(bb.minY);
        int minZ = Mth.floor(bb.minZ);

        int maxX = Mth.ceil(bb.maxX);
        int maxY = Mth.ceil(bb.maxY);
        int maxZ = Mth.ceil(bb.maxZ);

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        for (int x = minX; x < maxX; x++)
        {
            for (int z = minZ; z < maxZ; z++)
            {


                for (int y = minY; y < maxY; y++)
                {
                    pos.set(x, y, z);

                    if (collection.contains(level.getBlockState(pos).getBlock()))
                    {
                        return true;
                    }
                }

            }

        }
        return false;
    }

}