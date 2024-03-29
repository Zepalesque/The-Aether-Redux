package net.zepalesque.redux.capability.living;

import com.aetherteam.nitrogen.capability.INBTSynchable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.zepalesque.redux.capability.ICompoundTagNonSynching;
import net.zepalesque.redux.capability.ReduxCapabilities;
import net.zepalesque.redux.capability.player.RainbowCloudModule;

public interface ReduxLiving extends ICompoundTagNonSynching {
    LivingEntity getMob();

    static LazyOptional<ReduxLiving> get(LivingEntity mob) {
        return mob.getCapability(ReduxCapabilities.REDUX_LIVING);
    }

    RainbowCloudModule getRainbowModule();
    void tick();
}
