
package net.zepalesque.redux.network.packet;

import com.aetherteam.nitrogen.capability.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncEntityPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.zepalesque.redux.capability.aprilfools.WackyBat;
import oshi.util.tuples.Quartet;

public class WackyBatSyncPacket extends SyncEntityPacket<WackyBat> {

    public WackyBatSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public WackyBatSyncPacket(int playerID, String key, INBTSynchable.Type type, Object value) {
        super(playerID, key, type, value);
    }

    public static WackyBatSyncPacket decode(FriendlyByteBuf buf) {
        return new WackyBatSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public LazyOptional<WackyBat> getCapability(Entity entity) {
        return WackyBat.get((LivingEntity) entity);
    }
}
