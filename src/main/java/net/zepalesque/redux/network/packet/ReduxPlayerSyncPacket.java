
package net.zepalesque.redux.network.packet;

import com.aetherteam.nitrogen.capability.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncEntityPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import net.zepalesque.redux.capability.player.ReduxPlayer;
import oshi.util.tuples.Quartet;

public class ReduxPlayerSyncPacket extends SyncEntityPacket<ReduxPlayer> {
    public ReduxPlayerSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public ReduxPlayerSyncPacket(int playerID, String key, INBTSynchable.Type type, Object value) {
        super(playerID, key, type, value);
    }

    public static ReduxPlayerSyncPacket decode(FriendlyByteBuf buf) {
        return new ReduxPlayerSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public LazyOptional<ReduxPlayer> getCapability(Entity entity) {
        return ReduxPlayer.get((Player) entity);
    }
}