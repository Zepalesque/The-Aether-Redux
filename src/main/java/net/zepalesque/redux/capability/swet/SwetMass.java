package net.zepalesque.redux.capability.swet;

import com.aetherteam.aether.entity.monster.Swet;
import com.aetherteam.nitrogen.capability.INBTSynchable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.zepalesque.redux.capability.ICompoundTagNonSynching;

public interface SwetMass extends /*INBTSynchable<CompoundTag>*/ ICompoundTagNonSynching {

    Swet getSwet();


}
