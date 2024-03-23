package net.zepalesque.redux.network;

import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.network.packet.*;

import java.util.function.Function;

public class ReduxPacketHandler {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            Redux.locate("main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
        );

    public static synchronized void register() {
        register(ReduxCockatriceSyncPacket.class, ReduxCockatriceSyncPacket::decode);
        register(DoubleJumpPacket.class, DoubleJumpPacket::decode);
        register(ReduxPlayerSyncPacket.class, ReduxPlayerSyncPacket::decode);
        register(UpdateJumpAbilityPacket.class, UpdateJumpAbilityPacket::decode);
        register(ShootFireballPacket.class, ShootFireballPacket::decode);
        register(SubzeroArrowPacket.class, SubzeroArrowPacket::decode);
        register(SubzeroArrowHitGroundPacket.class, SubzeroArrowHitGroundPacket::decode);
        register(InfuseItemPacket.class, InfuseItemPacket::decode);
        register(VampireAmuletSyncPacket.class, VampireAmuletSyncPacket::decode);
        register(LoreUnlockPacket.class, LoreUnlockPacket::decode);
        register(SyncAetherGrassesPacket.class, SyncAetherGrassesPacket::decode);
        register(InfusionExpirePacket.class, buf -> new InfusionExpirePacket());
        register(BlightshadeParticlePacket.class, BlightshadeParticlePacket::decode);
    }

    private static int index;

    private static <MSG extends BasePacket> void register(Class<MSG> packet, Function<FriendlyByteBuf, MSG> decoder) {
        INSTANCE.messageBuilder(packet, index++).encoder(BasePacket::encode).decoder(decoder).consumerMainThread(BasePacket::handle).add();
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> {
            return player;
        }), message);
    }

    public static <MSG> void sendToNear(MSG message, double x, double y, double z, double radius, ResourceKey<Level> dimension) {
        INSTANCE.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(x, y, z, radius, dimension)), message);
    }

    public static <MSG> void sendToAll(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToDimension(MSG message, ResourceKey<Level> dimension) {
        INSTANCE.send(PacketDistributor.DIMENSION.with(() -> {
            return dimension;
        }), message);
    }
}
