package net.zepalesque.redux.mixin;

import com.google.common.collect.ImmutableMap;
import net.zepalesque.redux.Redux;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;


public final class ReduxMixinPlugin implements IMixinConfigPlugin {

    Map<String, Supplier<Boolean>> OVERRIDES = ImmutableMap.<String, Supplier<Boolean>>builder()
            .put("net.zepalesque.redux.mixin.client.render.BattleSentryRendererMixin", Redux::aetherGenesisCompat)
            .put("net.zepalesque.redux.mixin.client.render.layer.BattleSentryLayerMixin", Redux::aetherGenesisCompat)
            .build();

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return OVERRIDES.containsKey(mixinClassName) ? OVERRIDES.get(mixinClassName).get() : true;
    }

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}