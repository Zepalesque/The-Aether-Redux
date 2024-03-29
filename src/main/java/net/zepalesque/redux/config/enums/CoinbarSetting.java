package net.zepalesque.redux.config.enums;

import net.zepalesque.redux.client.render.ReduxOverlays;
import net.zepalesque.redux.config.ReduxConfig;

import java.util.function.Supplier;

public enum CoinbarSetting {

    With_Jade(ReduxOverlays::jadeOn), True(() -> true), False(() -> false);
    private final Supplier<Boolean> useModel;
    CoinbarSetting(Supplier<Boolean> useModel) {
        this.useModel = useModel;
    }

    public boolean useSidebar() {
        return this.useModel.get();
    }
}
