package net.zepalesque.redux.util;

import com.mojang.serialization.Codec;

// TODO: move to zenith
public class MiscUtil {
	
	// rus reference (&🦀)
	public static IllegalStateException unreachable() {
		return new IllegalStateException("Encountered unreachable code! This is a bug!");
	}
	// rus reference (&🦀)
	public static IllegalStateException todo() {
		return new IllegalStateException("Unimplemented code (reached `todo`)");
	}
	
	public static Codec<Byte> byteRange(final byte minInclusive, final byte maxInclusive) {
		final var checker = Codec.checkRange(minInclusive, maxInclusive);
		return Codec.BYTE.flatXmap(checker, checker);
	}
	
}
