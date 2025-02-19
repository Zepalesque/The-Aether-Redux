package net.zepalesque.redux.temp;

import net.minecraft.world.phys.Vec3;

public class VectorUtil {

    public static Vec3 threshold(Vec3 vec3, Vec3 threshold) {
        Vec3 abs = abs(vec3);

        if (coordsGreaterThanInclusive(abs, threshold)) return vec3;
        else if (coordsGreaterThanInclusive(threshold, abs)) return Vec3.ZERO;

        double vx = vec3.x, vy = vec3.y, vz = vec3.z;
        double tx = threshold.x, ty = threshold.y, tz = threshold.z;

        double x = Math.abs(vx) < tx ? 0 : vx, y = Math.abs(vy) < ty ? 0 : vy, z = Math.abs(vz) < tz ? 0 : vz;

        return new Vec3(x, y, z);
    }

    public static Vec3 abs(Vec3 vec3) {
        return coordsGreaterThanInclusive(vec3, Vec3.ZERO) ? vec3 : new Vec3(Math.abs(vec3.x), Math.abs(vec3.y), Math.abs(vec3.z));
    }

    public static boolean coordsGreaterThanInclusive(Vec3 a, Vec3 b) {
        return a.x >= b.x && a.y >= b.y && a.z >= b.z;
    }

    public static boolean coordsGreaterThanExclusive(Vec3 a, Vec3 b) {
        return a.x > b.x && a.y > b.y && a.z > b.z;
    }
}
