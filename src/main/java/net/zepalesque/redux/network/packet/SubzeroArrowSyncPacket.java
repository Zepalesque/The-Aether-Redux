package net.zepalesque.redux.network.packet;

import com.aetherteam.nitrogen.capability.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncEntityPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.common.util.LazyOptional;
import net.zepalesque.redux.capability.arrow.SubzeroArrow;
import oshi.util.tuples.Quartet;

public class SubzeroArrowSyncPacket extends SyncEntityPacket<SubzeroArrow> {
    public SubzeroArrowSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public SubzeroArrowSyncPacket(int playerID, String key, INBTSynchable.Type type, Object value) {
        super(playerID, key, type, value);
    }

    public static SubzeroArrowSyncPacket decode(FriendlyByteBuf buf) {
        return new SubzeroArrowSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public LazyOptional<SubzeroArrow> getCapability(Entity entity) {
        return SubzeroArrow.get((AbstractArrow) entity);
    }
}