package net.zepalesque.redux.network.packet;

import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;

public class InfusionExpirePacket implements BasePacket {
    @Override
    public void encode(FriendlyByteBuf friendlyByteBuf) { }

    @Override
    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().level != null) {
            Player player = Minecraft.getInstance().player;
            player.level().playSound(player, player.getX(), player.getY(), player.getZ(), ReduxSoundEvents.INFUSION_EXPIRE.get(), SoundSource.PLAYERS, 0.8F, 0.8F + player.level().getRandom().nextFloat() * 0.4F);
        }
    }
}
