package net.zepalesque.redux.mixin.common.world;

import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

// code copyyyyy: https://github.com/ThatJadenXgamer/Elysium/blob/a1b11210c2d3ca4d4c0b1a5fc13b200c640da2f9/src/main/java/net/jadenxgamer/elysium_api/impl/mixin/biome/NoiseGeneratorSettingsAccessor.java
@Mixin(NoiseGeneratorSettings.class)
public interface NoiseSettingsAccessor {
    
    @Final
    @Mutable
    @Accessor("surfaceRule")
    void redux$setSurfaceRule(SurfaceRules.RuleSource rule);
}