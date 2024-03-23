package net.zepalesque.redux.capability.aprilfools;

import com.aetherteam.nitrogen.capability.INBTSynchable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.zepalesque.redux.capability.ReduxCapabilities;

public interface WackyBat extends INBTSynchable<CompoundTag> {
    LivingEntity getEntity();

    static LazyOptional<WackyBat> get(LivingEntity thingy) {
        return thingy.getCapability(ReduxCapabilities.WACKY_BAT);
    }

    void tick();

    boolean isResting();
    void setResting(boolean rest);
}
