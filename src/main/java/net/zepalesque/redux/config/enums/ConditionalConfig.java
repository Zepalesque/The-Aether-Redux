package net.zepalesque.redux.config.enums;

import java.util.function.Supplier;

public interface ConditionalConfig extends CharSequence {

    boolean enabled();
}
