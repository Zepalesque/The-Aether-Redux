package net.zepalesque.redux.attachment;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.attachment.anim.AerbunnyAnimAttachment;
import net.zepalesque.redux.attachment.anim.MoaAnimAttachment;
import net.zepalesque.redux.attachment.anim.SliderSignalAttachment;

public class ReduxDataAttachments {
	public static final DeferredRegister<AttachmentType<?>>
		ATTACHMENTS = Redux.reg(NeoForgeRegistries.ATTACHMENT_TYPES);

	public static final DeferredHolder<AttachmentType<?>, AttachmentType<ReduxPlayerAttachment>>
		REDUX_PLAYER = ATTACHMENTS.register(
			"redux_player",
			() -> AttachmentType
				.builder(ReduxPlayerAttachment::new)
				.serialize(ReduxPlayerAttachment.CODEC)
				.copyOnDeath()
				.build()
		);
	public static final DeferredHolder<AttachmentType<?>, AttachmentType<SliderSignalAttachment>>
		SLIDER_SIGNAL = ATTACHMENTS.register(
			"slider_signal",
			() -> AttachmentType
				.builder(SliderSignalAttachment::new)
				.build()
		);
	public static final DeferredHolder<AttachmentType<?>, AttachmentType<MoaAnimAttachment>>
		MOA_ANIM = ATTACHMENTS.register(
			"moa_anim",
			() -> AttachmentType
				.builder(MoaAnimAttachment::new)
				.build()
		);
	public static final DeferredHolder<AttachmentType<?>, AttachmentType<CockatriceShootingAttachment>>
		COCKATRICE_SHOOTING = ATTACHMENTS.register(
			"cockatrice_shooting",
			() -> AttachmentType
				.builder(CockatriceShootingAttachment::new)
				.serialize(CockatriceShootingAttachment.CODEC)
				.build()
		);
	public static final DeferredHolder<AttachmentType<?>, AttachmentType<AerbunnyAnimAttachment>>
		AERBUNNY_ANIM = ATTACHMENTS.register(
			"aerbunny_anim",
			() -> AttachmentType
				.builder(AerbunnyAnimAttachment::new)
				.serialize(AerbunnyAnimAttachment.CODEC)
				.build()
		);
}
