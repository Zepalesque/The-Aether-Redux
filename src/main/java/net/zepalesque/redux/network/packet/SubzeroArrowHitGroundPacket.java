package net.zepalesque.redux.network.packet;

import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.zepalesque.redux.capability.arrow.SubzeroArrow;

public record SubzeroArrowHitGroundPacket(int entityID) implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityID);
    }

    public static SubzeroArrowHitGroundPacket decode(FriendlyByteBuf buf) {
        int entityID = buf.readInt();
        return new SubzeroArrowHitGroundPacket(entityID);
    }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null && Minecraft.getInstance().player.level().getEntity(this.entityID) instanceof AbstractArrow arrow) {
            SubzeroArrow.get(arrow).ifPresent(subzeroArrow -> subzeroArrow.setHitGroundTimer(2));
        }
    }
}
