package net.zepalesque.redux.network.packet;

import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncEntityPacket;
import java.util.function.Supplier;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.attachment.CockatriceShootingAttachment;
import net.zepalesque.redux.attachment.ReduxDataAttachments;
import net.zepalesque.redux.attachment.ReduxPlayerAttachment;
import oshi.util.tuples.Quartet;

/**
 * Sync packet for values in the {@link ReduxPlayerAttachment} class.
 */
public class CockatriceShootPacket extends SyncEntityPacket<CockatriceShootingAttachment> {
	public static final Type<CockatriceShootPacket> TYPE = new Type<>(
		Redux.loc("sync_cockatrice_shooting_attachment")
	);

	public static final StreamCodec<RegistryFriendlyByteBuf, CockatriceShootPacket> STREAM_CODEC =
		CustomPacketPayload.codec(CockatriceShootPacket::write, CockatriceShootPacket::decode);

	public CockatriceShootPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
		super(values);
	}

	public CockatriceShootPacket(int mobId, String key, INBTSynchable.Type type, Object value) {
		super(mobId, key, type, value);
	}

	@Override
	public Type<CockatriceShootPacket> type() {
		return TYPE;
	}

	public static CockatriceShootPacket decode(RegistryFriendlyByteBuf buf) {
		return new CockatriceShootPacket(SyncEntityPacket.decodeEntityValues(buf));
	}

	@Override
	public Supplier<AttachmentType<CockatriceShootingAttachment>> getAttachment() {
		return ReduxDataAttachments.COCKATRICE_SHOOTING;
	}

	public static void execute(CockatriceShootPacket payload, IPayloadContext context) {
		SyncEntityPacket.execute(payload, context.player());
	}
}
