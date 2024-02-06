
package net.zepalesque.redux.network.packet;

import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;
import net.zepalesque.redux.client.ReduxColors;

import java.util.Map;

public record SyncAetherGrassesPacket(Map<ResourceLocation, Integer> colors) implements BasePacket {

    public void encode(FriendlyByteBuf buf) {
        buf.writeMap(this.colors, FriendlyByteBuf::writeResourceLocation, FriendlyByteBuf::writeInt);
    }

    public static SyncAetherGrassesPacket decode(FriendlyByteBuf buf) {
        Map<ResourceLocation, Integer> colors = buf.readMap(FriendlyByteBuf::readResourceLocation, FriendlyByteBuf::readInt);
        return new SyncAetherGrassesPacket(colors);
    }

    public void execute(Player player) {

        if (Minecraft.getInstance().player != null) {
            Level level = Minecraft.getInstance().player.level();
            ReduxColors.AETHER_GRASS_COLORS.clear();
            this.colors.forEach((biomeLoc, color) -> {
                Biome biome = level.registryAccess().registryOrThrow(ForgeRegistries.Keys.BIOMES).get(biomeLoc);
                if (biome != null) {
                    ReduxColors.AETHER_GRASS_COLORS.put(biome, color);
                }
            });
        }

    }



}
