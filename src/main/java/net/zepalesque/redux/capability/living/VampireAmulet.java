package net.zepalesque.redux.capability.living;

import com.aetherteam.nitrogen.capability.INBTSynchable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.zepalesque.redux.capability.ReduxCapabilities;

public interface VampireAmulet extends INBTSynchable<CompoundTag> {
    LivingEntity getMob();

    static LazyOptional<VampireAmulet> get(LivingEntity mob) {
        return mob.getCapability(ReduxCapabilities.VAMPIRE_AMULET);
    }

    boolean canUseAbility();
    void setAbilityUse(boolean b);

    int countdown();

    boolean hasCurio();
    void resetTimer();

    void tick();
}
