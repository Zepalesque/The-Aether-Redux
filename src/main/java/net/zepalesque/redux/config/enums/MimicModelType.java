package net.zepalesque.redux.config.enums;

import net.zepalesque.redux.config.ReduxConfig;

import java.util.function.Supplier;

public enum MimicModelType {

    sync_with_server(ReduxConfig.COMMON.smaller_mimic_hitbox), modern_override(() -> true), classic_override(() -> false);
    private final Supplier<Boolean> useModel;
    MimicModelType(Supplier<Boolean> useModel) {
        this.useModel = useModel;
    }

    public boolean shouldUseModern() {
        return this.useModel.get();
    }
}
