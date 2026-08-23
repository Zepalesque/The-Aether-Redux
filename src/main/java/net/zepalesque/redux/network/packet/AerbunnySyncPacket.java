package net.zepalesque.redux.network.packet;

import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncEntityPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.attachment.CockatriceShootingAttachment;
import net.zepalesque.redux.attachment.ReduxDataAttachments;
import net.zepalesque.redux.attachment.ReduxPlayerAttachment;
import net.zepalesque.redux.attachment.anim.AerbunnyAnimAttachment;
import oshi.util.tuples.Quartet;

import java.util.function.Supplier;

/**
 * Sync packet for values in the {@link ReduxPlayerAttachment} class.
 */
public class AerbunnySyncPacket extends SyncEntityPacket<AerbunnyAnimAttachment> {
	public static final Type<AerbunnySyncPacket> TYPE = new Type<>(
		Redux.loc("sync_aerbunny_anim_attachment")
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, AerbunnySyncPacket> STREAM_CODEC =
		CustomPacketPayload.codec(AerbunnySyncPacket::write, AerbunnySyncPacket::decode);

	public AerbunnySyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
		super(values);
	}

	public AerbunnySyncPacket(int mobId, String key, INBTSynchable.Type type, Object value) {
		super(mobId, key, type, value);
	}

	@Override
	public Type<AerbunnySyncPacket> type() {
		return TYPE;
	}

	public static AerbunnySyncPacket decode(RegistryFriendlyByteBuf buf) {
		return new AerbunnySyncPacket(SyncEntityPacket.decodeEntityValues(buf));
	}

	@Override
	public Supplier<AttachmentType<AerbunnyAnimAttachment>> getAttachment() {
		return ReduxDataAttachments.AERBUNNY_ANIM;
	}

	public static void execute(AerbunnySyncPacket payload, IPayloadContext context) {
		SyncEntityPacket.execute(payload, context.player());
	}
}
