package net.zepalesque.redux.network.packet;

import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.zepalesque.redux.advancement.trigger.InfuseItemTrigger;
import net.zepalesque.redux.recipe.util.InfusionHolder;

import java.util.UUID;

public record InfuseItemPacket(UUID playerID, InfusionHolder holder) implements BasePacket {

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(this.playerID);
        buf.writeWithCodec(InfusionHolder.CODEC, this.holder);
    }
    public static InfuseItemPacket decode(FriendlyByteBuf buf) {
        UUID player = buf.readUUID();
        InfusionHolder holder = buf.readWithCodec(InfusionHolder.CODEC);
        return new InfuseItemPacket(player, holder);
    }

    public void execute(Player playerEntity) {

        if (playerEntity != null && playerEntity.getServer() != null) {
            Player player = playerEntity.level.getPlayerByUUID(this.playerID());
            if (player instanceof ServerPlayer sp) {
                InfuseItemTrigger.INSTANCE.trigger(sp, this.holder.getInfused(), this.holder.getResult());
                if (this.holder.getInfused().getCount() > 1) {
                    ItemStack s = this.holder().getResult();
                    s.setCount(1);
                    boolean flag = sp.getInventory().add(s);
                    if (!flag) {
                        double d0 = sp.getEyeY() - (double)0.3F;
                        ItemEntity itementity = new ItemEntity(sp.level, sp.getX(), d0, sp.getZ(), s);
                        itementity.setPickUpDelay(40);
                        sp.level.addFreshEntity(itementity);
                    } else {
                        sp.containerMenu.broadcastChanges();
                    }
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