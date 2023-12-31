package net.zepalesque.redux.util.math;

import net.minecraft.world.phys.AABB;

public class AABBUtil {


    public static boolean intersects(AABB first, AABB second)
    {
        return first.intersects(second);
    }
}
