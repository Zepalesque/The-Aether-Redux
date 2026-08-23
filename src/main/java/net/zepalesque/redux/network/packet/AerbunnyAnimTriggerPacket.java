package net.zepalesque.redux.network.packet;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.passive.Aerbunny;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.attachment.ReduxDataAttachments;
import net.zepalesque.redux.attachment.anim.AerbunnyAnimAttachment;

public final class AerbunnyAnimTriggerPacket {
	public record HurtAnim(int mobID) implements CustomPacketPayload {
		public static final Type<HurtAnim> TYPE = new Type<>(Redux.loc("aerbunny_hurt_anim"));
		
		public static final StreamCodec<RegistryFriendlyByteBuf, HurtAnim> STREAM_CODEC =
			CustomPacketPayload.codec(HurtAnim::write, HurtAnim::decode);
		
		public void write(FriendlyByteBuf buf) {
			buf.writeInt(this.mobID());
		}
		
		public static HurtAnim decode(FriendlyByteBuf buf) {
			var mobID = buf.readInt();
			return new HurtAnim(mobID);
		}
		
		@Override
		public Type<? extends CustomPacketPayload> type() {
			return TYPE;
		}
		
		public static void execute(HurtAnim payload, IPayloadContext context) {
			var lvl = Minecraft.getInstance().level;
			if (lvl != null) {
				var entity = lvl.getEntity(payload.mobID());
				if (
					entity != null &&
						entity.hasData(ReduxDataAttachments.AERBUNNY_ANIM.get()) &&
						entity.getType() == AetherEntityTypes.AERBUNNY.get()
				) {
					var bnuuy = (Aerbunny) entity;
					AerbunnyAnimAttachment.get(bnuuy).onClientHurt(bnuuy);
				}
			}
		}
	}
	
	public record TwitchAnim(int mobID) implements CustomPacketPayload {
		public static final Type<TwitchAnim> TYPE = new Type<>(Redux.loc("aerbunny_hurt_anim"));
		
		public static final StreamCodec<RegistryFriendlyByteBuf, TwitchAnim> STREAM_CODEC =
			CustomPacketPayload.codec(TwitchAnim::write, TwitchAnim::decode);
		
		public void write(FriendlyByteBuf buf) {
			buf.writeInt(this.mobID());
		}
		
		public static TwitchAnim decode(FriendlyByteBuf buf) {
			var mobID = buf.readInt();
			return new TwitchAnim(mobID);
		}
		
		@Override
		public Type<? extends CustomPacketPayload> type() {
			return TYPE;
		}
		
		public static void execute(TwitchAnim payload, IPayloadContext context) {
			var lvl = Minecraft.getInstance().level;
			if (lvl != null) {
				var entity = lvl.getEntity(payload.mobID());
				if (
					entity != null &&
						entity.hasData(ReduxDataAttachments.AERBUNNY_ANIM.get()) &&
						entity.getType() == AetherEntityTypes.AERBUNNY.get()
				) {
					var bnuuy = (Aerbunny) entity;
					AerbunnyAnimAttachment.get(bnuuy).doTwitchAnim(bnuuy);
				}
			}
		}
	}
}
