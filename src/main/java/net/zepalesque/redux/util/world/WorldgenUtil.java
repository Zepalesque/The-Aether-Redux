package net.zepalesque.redux.util.world;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;

// TODO: Move to Zenith
public class WorldgenUtil {

    // Offsets a MutableBlockPos in a direction, a certain amount of blocks
    public static BlockPos.MutableBlockPos setWithOffset(
        BlockPos.MutableBlockPos mut,
        Vec3i pos,
        Direction dir,
        int amount) {
        return mut.set(pos.getX() + dir.getStepX() * amount, pos.getY() + dir.getStepY() * amount, pos.getZ() + dir.getStepZ() * amount);
    }

}
