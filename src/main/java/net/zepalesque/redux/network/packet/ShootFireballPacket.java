
package net.zepalesque.redux.network.packet;

import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.zepalesque.redux.capability.player.ReduxPlayer;
import net.zepalesque.redux.util.player.AbilityUtil;

import java.util.UUID;

public record ShootFireballPacket(UUID playerID) implements BasePacket {

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(this.playerID);
    }

    public static ShootFireballPacket decode(FriendlyByteBuf buf) {
        UUID player = buf.readUUID();
        return new ShootFireballPacket(player);
    }

    public void execute(Player playerEntity) {

        if (playerEntity != null && playerEntity.level() != null) {
            Player player = playerEntity.level().getPlayerByUUID(this.playerID());
            if (player != null) {
                ReduxPlayer.get(player).ifPresent((reduxPlayer) -> {
                    if (reduxPlayer.canShootFireball()) {
                        reduxPlayer.fireballSetup();
                        AbilityUtil.shootFireballs(player);
                    }
                });
            }
        }

    }

    public UUID playerID() {
        return this.playerID;
    }

}
