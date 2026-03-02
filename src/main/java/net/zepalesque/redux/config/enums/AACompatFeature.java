package net.zepalesque.redux.config.enums;

import java.util.function.Supplier;
import net.zepalesque.zenith.util.mod.CompatHelper;
import org.jetbrains.annotations.Nullable;

// me when charsequence wrappers
public class AACompatFeature {
    public enum Overridden implements ConditionalConfig {
        ALWAYS_TRUE("always_true", true), ALWAYS_FALSE("always_false", false), WITHOUT_ANCIENT_AETHER("without_ancient_aethe    r", () -> !CompatHelper.loaded("ancient_aether"));

        private final String serialized;
        @Nullable private final Supplier<Boolean> supplier;
        private final boolean value;

        Overridden(String serialized, Supplier<Boolean> value) {
            this.serialized = serialized;
            this.value = false;
            this.supplier = value;
        }

        Overridden(String serialized, boolean value) {
            this.serialized = serialized;
            this.value = value;
            this.supplier = null;
        }

        @Override
        public String toString() {
            return this.serialized;
        }

        @Override
        public boolean enabled() {
            if (this.supplier == null) return this.value;
            else return this.supplier.get();
        }

        @Override
        public String serialized() {
            return this.toString();
        }
    }

    public enum Compat implements ConditionalConfig {
        ALWAYS_TRUE("always_true", true), ALWAYS_FALSE("always_false", false), WITH_ANCIENT_AETHER("with_ancient_aether", () -> CompatHelper.loaded("ancient_aether"));

        private final String serialized;
        @Nullable private final Supplier<Boolean> supplier;
        private final boolean value;

        Compat(String serialized, Supplier<Boolean> value) {
            this.serialized = serialized;
            this.value = false;
            this.supplier = value;
        }

        Compat(String serialized, boolean value) {
            this.serialized = serialized;
            this.value = value;
            this.supplier = null;
        }

        @Override
        public String toString() {
            return serialized;
        }

        @Override
        public boolean enabled() {
            if (this.supplier == null) return this.value;
            else return this.supplier.get();
        }

        @Override
        public String serialized() {
            return this.toString();
        }
    }
}
