package net.zepalesque.redux.attachment;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.attachment.anim.MoaAnimAttachment;

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
}
