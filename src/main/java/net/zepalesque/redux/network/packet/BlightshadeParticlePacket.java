
package net.zepalesque.redux.network.packet;

import com.aetherteam.nitrogen.network.BasePacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.phys.AABB;
import net.zepalesque.redux.advancement.trigger.DoubleJumpTrigger;
import net.zepalesque.redux.capability.player.ReduxPlayer;
import net.zepalesque.redux.client.particle.ReduxParticleTypes;
import net.zepalesque.redux.world.feature.config.FieldsprootTreeConfig;

import java.util.UUID;

public record BlightshadeParticlePacket(AABB bounds) implements BasePacket {

    public void encode(FriendlyByteBuf buf) {
        buf.writeWithCodec(AABB_CODEC, this.bounds);
    }

    public static final Codec<AABB> AABB_CODEC = RecordCodecBuilder.create((mushroom) ->
            mushroom.group(
                    Codec.DOUBLE.fieldOf("minX").forGetter(o -> o.minX),
                    Codec.DOUBLE.fieldOf("minY").forGetter(o -> o.minY),
                    Codec.DOUBLE.fieldOf("minZ").forGetter(o -> o.minZ),
                    Codec.DOUBLE.fieldOf("maxX").forGetter(o -> o.maxX),
                    Codec.DOUBLE.fieldOf("maxY").forGetter(o -> o.maxY),
                    Codec.DOUBLE.fieldOf("maxZ").forGetter(o -> o.maxZ))
                    .apply(mushroom, AABB::new));

    public static BlightshadeParticlePacket decode(FriendlyByteBuf buf) {
        AABB box = buf.readWithCodec(AABB_CODEC);
        return new BlightshadeParticlePacket(box);
    }

    public void execute(Player playerEntity) {
        if (Minecraft.getInstance().level != null) {
            RandomSource rand = Minecraft.getInstance().level.getRandom();
            AABB shrunk = this.bounds.inflate(-0.1);
            int count = rand.nextInt(25) + 50;
            for (int i = 0; i < count; i++) {
                double x = shrunk.minX + (rand.nextDouble() * shrunk.getXsize());
                double y = shrunk.minY + (shrunk.getYsize() * 0.25) + (rand.nextDouble() * shrunk.getYsize() * 0.75);
                double z = shrunk.minZ + (rand.nextDouble() * shrunk.getZsize());
                Minecraft.getInstance().level.addParticle(ReduxParticleTypes.BLIGHTSHADE.get(), x, y, z, 0.05, 0.05, 0.05);
            }
        }

    }

    @Override
    public AABB bounds() {
        return bounds;
    }
}
