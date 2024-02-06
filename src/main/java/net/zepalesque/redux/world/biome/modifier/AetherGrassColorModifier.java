package net.zepalesque.redux.world.biome.modifier;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.network.ReduxPacketHandler;
import net.zepalesque.redux.network.packet.SyncAetherGrassesPacket;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public record AetherGrassColorModifier(HolderSet<Biome> biomes, int grass) implements BiomeModifier {

    public static Map<ResourceLocation, Integer> AETHER_GRASS_COLORS_SERVER = new HashMap<>();

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.AFTER_EVERYTHING && biomes.contains(biome)) {
            Optional<ResourceKey<Biome>> optional = biome.unwrapKey();
            optional.ifPresent(key -> AETHER_GRASS_COLORS_SERVER.put(key.location(), grass));
        }
    }

    public static void sendToClient(ServerPlayer player) {
        ReduxPacketHandler.sendToPlayer(new SyncAetherGrassesPacket(AETHER_GRASS_COLORS_SERVER), player);
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return ReduxBiomeModifierSerializers.AETHER_GRASS_COLOR.get();
    }
}
