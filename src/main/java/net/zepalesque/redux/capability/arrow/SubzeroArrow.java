package net.zepalesque.redux.capability.arrow;

import com.aetherteam.nitrogen.capability.INBTSynchable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.common.util.LazyOptional;
import net.zepalesque.redux.capability.ReduxCapabilities;

public interface SubzeroArrow extends INBTSynchable<CompoundTag> {
    AbstractArrow getArrow();

    static LazyOptional<SubzeroArrow> get(AbstractArrow arrow) {
        return arrow.getCapability(ReduxCapabilities.SUBZERO_ARROW);
    }

    void setSubzeroArrow(boolean isSubzeroArrow);
    boolean isSubzeroArrow();

    void setSlownessTime(int time);
    int getSlownessTime();

    boolean hitGround();
    void setHitGround(boolean hitGround);

    void tick();

    int getHitGroundTimer();
    void setHitGroundTimer(int time);


}
