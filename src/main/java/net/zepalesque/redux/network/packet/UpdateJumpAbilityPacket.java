
package net.zepalesque.redux.network.packet;

import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.nitrogen.capability.INBTSynchable;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.zepalesque.redux.capability.player.ReduxPlayer;
import net.zepalesque.redux.item.ReduxItems;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;
import java.util.UUID;

public record UpdateJumpAbilityPacket(UUID playerID) implements BasePacket {

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(this.playerID);
    }

    public static UpdateJumpAbilityPacket decode(FriendlyByteBuf buf) {
        UUID player = buf.readUUID();
        return new UpdateJumpAbilityPacket(player);
    }

    public void execute(Player playerEntity) {

        if (playerEntity != null && playerEntity.level != null) {
            Player player = playerEntity.level.getPlayerByUUID(this.playerID());
            if (player != null) {
                ReduxPlayer.get(player).ifPresent((reduxPlayer) -> {
                    List<SlotResult> ringList = EquipmentUtil.getCurios(player, ReduxItems.AIRBOUND_CAPE.get());
                    reduxPlayer.setSynched(INBTSynchable.Direction.CLIENT, "setMaxAirJumps", ringList != null && ringList.size() > 0 ? 1 : 0);
                });
            }
        }

    }

    public UUID playerID() {
        return this.playerID;
    }

}
