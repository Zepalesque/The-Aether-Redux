package net.zepalesque.redux.util;

import java.util.function.Supplier;

public class AssertionUtil {
    public static void checkState(boolean b, Supplier<? extends RuntimeException> exception) {
        if (!b) {
            throw exception.get();
        }
    }
}
