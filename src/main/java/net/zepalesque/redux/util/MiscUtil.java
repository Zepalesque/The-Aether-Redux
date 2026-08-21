package net.zepalesque.redux.util;

public class MiscUtil {
	
	// rus reference (&🦀)
	public static IllegalStateException unreachable() {
		return new IllegalStateException("Encountered unreachable code! This is a bug!");
	}
	// rus reference (&🦀)
	public static IllegalStateException todo() {
		return new IllegalStateException("Unimplemented code (reached `todo`)");
	}
}
