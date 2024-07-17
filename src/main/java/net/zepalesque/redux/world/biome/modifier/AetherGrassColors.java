package net.zepalesque.redux.world.biome.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.zepalesque.redux.network.ReduxPacketHandler;
import net.zepalesque.redux.network.packet.SyncAetherGrassesPacket;
import net.zepalesque.redux.util.holder.HolderUtil;
import net.zepalesque.redux.util.codec.ReduxCodecs;
import net.zepalesque.redux.util.holder.RegistryMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public record AetherGrassColors(RegistryMap<Biome, Integer> colorMap) implements BiomeModifier {

    public static Map<ResourceLocation, Integer> SERVER_MAP = new HashMap<>();

    public static final Codec<AetherGrassColors> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            ReduxCodecs.MAP_CODEC.fieldOf("colormap").forGetter(AetherGrassColors::colorMap)).apply(builder, AetherGrassColors::new));


    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.AFTER_EVERYTHING) {
            Optional<ResourceKey<Biome>> key = biome.unwrapKey();
            key.ifPresent(biomeResourceKey -> colorMap.get(biome).ifPresent(color ->
                    SERVER_MAP.put(biomeResourceKey.location(), color)));
        }
    }

    public static void sendToClient(ServerPlayer player) {
        ReduxPacketHandler.sendToPlayer(new SyncAetherGrassesPacket(SERVER_MAP), player);
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return CODEC;
    }
}
