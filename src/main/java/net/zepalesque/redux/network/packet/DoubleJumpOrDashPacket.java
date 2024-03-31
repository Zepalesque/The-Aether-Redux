
package net.zepalesque.redux.network.packet;

import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.zepalesque.redux.advancement.trigger.DoubleJumpTrigger;
import net.zepalesque.redux.capability.player.ReduxPlayer;
import net.zepalesque.redux.effect.ReduxEffects;

import java.util.UUID;

public record DoubleJumpOrDashPacket(UUID playerID) implements BasePacket {

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(this.playerID);
    }

    public static DoubleJumpOrDashPacket decode(FriendlyByteBuf buf) {
        UUID player = buf.readUUID();
        return new DoubleJumpOrDashPacket(player);
    }

    public void execute(Player playerEntity) {

        if (playerEntity != null && playerEntity.getServer() != null) {
            Player player = playerEntity.level().getPlayerByUUID(this.playerID());
            if (player != null) {
                if (player.hasEffect(ReduxEffects.DASH.get())) {
                    ReduxPlayer.get(player).ifPresent(ReduxPlayer::dash);
                } else {
                    ReduxPlayer.get(player).ifPresent(ReduxPlayer::doubleJump);
                    if (player instanceof ServerPlayer sp) {
                        DoubleJumpTrigger.INSTANCE.trigger(sp);
                    }
                }
            }
        }

    }

    public UUID playerID() {
        return this.playerID;
    }

}
