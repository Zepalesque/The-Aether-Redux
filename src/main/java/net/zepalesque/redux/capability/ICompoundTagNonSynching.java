package net.zepalesque.redux.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface ICompoundTagNonSynching extends INBTSerializable<CompoundTag> {

    @Override
    default CompoundTag serializeNBT() {
        return new CompoundTag();
    }

    @Override
    default void deserializeNBT(CompoundTag nbt) {

    }
}
