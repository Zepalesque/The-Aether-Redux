
package net.zepalesque.redux.network.packet;

import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.zepalesque.redux.capability.player.ReduxPlayer;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public record LoreUnlockPacket(UUID playerID, @Nullable Item itemUnlock) implements BasePacket {

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(this.playerID);
        buf.writeUtf(itemUnlock == null ? "" : ForgeRegistries.ITEMS.getKey(this.itemUnlock).toString());
    }

    public static LoreUnlockPacket decode(FriendlyByteBuf buf) {
        UUID player = buf.readUUID();
        String location = buf.readUtf();
        if (location != null && !location.isEmpty()) {
            ResourceLocation loc = new ResourceLocation(location);
            if (ForgeRegistries.ITEMS.containsKey(loc)) {
                return new LoreUnlockPacket(player, ForgeRegistries.ITEMS.getValue(loc));
            }
        }
        return new LoreUnlockPacket(player, null);
    }

    public void execute(Player playerEntity) {

        if (itemUnlock != null && playerEntity != null && playerEntity.level != null) {
            Player player = playerEntity.level.getPlayerByUUID(this.playerID());
            if (player != null) {
                ReduxPlayer.get(player).ifPresent((reduxPlayer) -> {
                    reduxPlayer.getLoreModule().unlock(itemUnlock);
                });
            }
        }

    }

    public UUID playerID() {
        return this.playerID;
    }

}
