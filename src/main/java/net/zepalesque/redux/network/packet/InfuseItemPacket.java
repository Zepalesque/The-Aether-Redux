package net.zepalesque.redux.network.packet;

import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.zepalesque.redux.advancement.trigger.InfuseItemTrigger;
import net.zepalesque.redux.recipe.util.InfusionHolder;

import java.util.UUID;

public record InfuseItemPacket(UUID playerID, InfusionHolder holder) implements BasePacket {

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(this.playerID);
        buf.writeJsonWithCodec(InfusionHolder.CODEC, this.holder);
    }
    public static InfuseItemPacket decode(FriendlyByteBuf buf) {
        UUID player = buf.readUUID();
        InfusionHolder holder = buf.readJsonWithCodec(InfusionHolder.CODEC);
        return new InfuseItemPacket(player, holder);
    }

    public void execute(Player playerEntity) {

        if (playerEntity != null && playerEntity.getServer() != null) {
            Player player = playerEntity.level().getPlayerByUUID(this.playerID());
            if (player instanceof ServerPlayer sp) {
                InfuseItemTrigger.INSTANCE.trigger(sp, this.holder.getInfused(), this.holder.getResult());
                if (this.holder.getInfused().getCount() >= 1) {
                    ItemStack s = this.holder().getResult();
                    s.setCount(1);
                    sp.addItem(s);
                }
            }
        }

    }

    public UUID playerID() {
        return this.playerID;
    }

    public InfusionHolder holder() {
        return this.holder;
    }
}