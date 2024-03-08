package net.zepalesque.redux.capability.swet;

import com.aetherteam.aether.entity.monster.Swet;
import net.minecraft.nbt.CompoundTag;

// TODO
public class SwetMassCapability implements SwetMass {

    private final Swet swet;

    
    public SwetMassCapability(Swet swet) {
        this.swet = swet;
    }


    @Override
    public Swet getSwet() {
        return null;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
    }


}
