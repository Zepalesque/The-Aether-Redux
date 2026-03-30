package net.zepalesque.redux.util.world;

import net.minecraft.world.level.levelgen.RandomSupport;

// TODO: Move to Zenith
/// Utilities for deterministic single-use random numbers without mutation of state.<br>
/// Based on the inner workings of {@link net.minecraft.world.level.levelgen.XoroshiroRandomSource XoroshiroRandomSource}
public class PureRand {
	
	public static long getLong(long seed) {
		return getLong(seedLo(seed), seedHi(seed));
	}
	
	
	public static long seedLo(long seed) {
		var i = seed ^ 7640891576956012809L;
		i = RandomSupport.mixStafford13(i);
		return i;
	}
	
	public static long seedHi(long seed) {
		var i = seed ^ 7640891576956012809L;
		var j = i - 7046029254386353131L;
		j = RandomSupport.mixStafford13(j);
		return j;
	}
	
	public static long nextSeedLo(long lo, long hi) {
		return Long.rotateLeft(lo, 49) ^ hi ^ hi << 21;
	}
	
	public static long nextSeedHi(long lo, long hi) {
		return Long.rotateLeft(hi, 28);
	}
	
	public static long getLong(long lo, long hi) {
		return Long.rotateLeft(lo + hi, 17) + lo;
	}
	
	public static int getInt(long seed) {
		return (int) getLong(seed);
	}
	
	public static int getInt(long seed, int bound) {
		if (bound <= 0) throw new IllegalArgumentException("Bound must be positive");
		else {
			var i = Integer.toUnsignedLong(getInt(seed));
			var j = i * (long)bound;
			var k = j & 4294967295L;
			var lo = seedLo(seed);
			var hi = seedHi(seed);
			if (k < (long)bound)
				for (var l = Integer.remainderUnsigned(~bound + 1, bound); k < (long) l; k = j & 4294967295L) {
					
					i = Integer.toUnsignedLong((int) getLong(lo, hi));
					j = i * (long) bound;
					
					var newlo = nextSeedLo(lo, hi);
					var newhi = nextSeedHi(lo, hi);
					lo = newlo;
					hi = newhi;
				}
			
			var i1 = j >> 32;
			return (int)i1;
		}
	}
	
	public static long getBits(long seed, int bits) {
		return getLong(seed) >>> 64 - bits;
	}
	
	public static double getDouble(long seed) {
		return (double) getBits(seed, 53) * 1.110223E-16F;
	}
	
	public boolean getBool(long seed) {
		return (getLong(seed) & 1L) != 0L;
	}
}
