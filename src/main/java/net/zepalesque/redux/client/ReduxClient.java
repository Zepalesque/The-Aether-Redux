package net.zepalesque.redux.client;

import net.zepalesque.unity.block.natural.AetherShortGrassBlock;

public class ReduxClient {

    public static void registerTintOverrides() {
        AetherShortGrassBlock.COLOR_OVERRIDES.add(ReduxColors::reduxColors);
    }
}
