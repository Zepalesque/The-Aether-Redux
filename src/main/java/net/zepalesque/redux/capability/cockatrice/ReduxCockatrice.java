package net.zepalesque.redux.capability.cockatrice;

import com.aetherteam.aether.entity.monster.Cockatrice;
import com.aetherteam.nitrogen.capability.INBTSynchable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.LazyOptional;
import net.zepalesque.redux.capability.ReduxCapabilities;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface ReduxCockatrice extends INBTSynchable<CompoundTag> {
    Cockatrice getCockatrice();

    static LazyOptional<ReduxCockatrice> get(Cockatrice cockatrice) {
        return cockatrice.getCapability(ReduxCapabilities.REDUX_COCKATRICE);
    }

    void setShooting(boolean melee);
    boolean isShooting();

    byte getTargetAnim();
    byte getPrevTargetAnim();

    byte getLegAnim();
    byte getPrevLegAnim();

    void handleLegAnim();
    void handleTargetAnim();

    void refreshNearby();
    @Nullable Collection<Entity> getNearby();
    int nearbyCount();
    boolean wasMelee();



    void tick();
}
