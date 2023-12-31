
package net.zepalesque.redux.network.packet;

import com.aetherteam.aether.entity.monster.Cockatrice;
import com.aetherteam.nitrogen.capability.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncEntityPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.LazyOptional;
import net.zepalesque.redux.capability.cockatrice.ReduxCockatrice;
import oshi.util.tuples.Quartet;

public class ReduxCockatriceSyncPacket extends SyncEntityPacket<ReduxCockatrice> {

    public ReduxCockatriceSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public ReduxCockatriceSyncPacket(int playerID, String key, INBTSynchable.Type type, Object value) {
        super(playerID, key, type, value);
    }

    public static ReduxCockatriceSyncPacket decode(FriendlyByteBuf buf) {
        return new ReduxCockatriceSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public LazyOptional<ReduxCockatrice> getCapability(Entity entity) {
        return ReduxCockatrice.get((Cockatrice) entity);
    }
}
