package net.zepalesque.redux.capability.swet;

import com.aetherteam.aether.entity.monster.Swet;
import com.aetherteam.nitrogen.capability.INBTSynchable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import net.zepalesque.redux.capability.ICompoundTagNonSynching;
import net.zepalesque.redux.capability.ReduxCapabilities;
import net.zepalesque.redux.capability.player.ReduxPlayer;

public interface SwetMass extends /*INBTSynchable<CompoundTag>*/ ICompoundTagNonSynching {

    Swet getSwet();

    static LazyOptional<SwetMass> get(Swet entity) {
        return entity.getCapability(ReduxCapabilities.SWET_MASS);
    }

    void tick();

}
