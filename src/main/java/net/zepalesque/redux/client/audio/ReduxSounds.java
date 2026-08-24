package net.zepalesque.redux.client.audio;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;

public class ReduxSounds {
	public static final DeferredRegister<SoundEvent>
		SOUNDS = Redux.reg(BuiltInRegistries.SOUND_EVENT);

	public static final DeferredHolder<SoundEvent, SoundEvent>
		EMBER_BOUNCE_BIG = register("entity.ember.bounce_big");
	public static final DeferredHolder<SoundEvent, SoundEvent>
		EMBER_BOUNCE_MEDIUM = register("entity.ember.bounce_medium");
	public static final DeferredHolder<SoundEvent, SoundEvent>
		EMBER_BOUNCE_SMALL = register("entity.ember.bounce_small");

	public static final DeferredHolder<SoundEvent, SoundEvent>
		AERJUMP = register("item.aerbound_cape.aerjump");
	public static final DeferredHolder<SoundEvent, SoundEvent>
		INFUSE_ITEM = register("item.generic.infuse");
	public static final DeferredHolder<SoundEvent, SoundEvent>
		INFUSION_EXPIRE = register("item.generic.infusion_expire");
	public static final DeferredHolder<SoundEvent, SoundEvent>
		ITEM_MUSIC_DISC_SENTIENCE = register("item.music_disc.sentience");

	public static final DeferredHolder<SoundEvent, SoundEvent>
		WILLOW_SPORES_CONVERT = register("item.willow_spores.convert");

	public static final DeferredHolder<SoundEvent, SoundEvent>
		LOGICATOR_CLICK = register("block.logicator.click");

	public static final DeferredHolder<SoundEvent, SoundEvent>
		SLIDER_SIGNAL = register("entity.slider.signal");

	public static final DeferredHolder<SoundEvent, SoundEvent>
		SWET_ATTACK = register("entity.swet.attack");

	private static DeferredHolder<SoundEvent, SoundEvent> register(String location) {
		return SOUNDS.register(location, () ->
			SoundEvent.createVariableRangeEvent(Redux.loc(location))
		);
	}
}
