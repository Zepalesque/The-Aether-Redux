
package net.zepalesque.redux.network.packet;

import com.aetherteam.nitrogen.capability.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncEntityPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.zepalesque.redux.capability.living.VampireAmulet;
import oshi.util.tuples.Quartet;

public class VampireAmuletSyncPacket extends SyncEntityPacket<VampireAmulet> {
    public VampireAmuletSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public VampireAmuletSyncPacket(int entityId, String key, INBTSynchable.Type type, Object value) {
        super(entityId, key, type, value);
    }

    public static VampireAmuletSyncPacket decode(FriendlyByteBuf buf) {
        return new VampireAmuletSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public LazyOptional<VampireAmulet> getCapability(Entity entity) {
        return VampireAmulet.get((LivingEntity) entity);
    }
}
