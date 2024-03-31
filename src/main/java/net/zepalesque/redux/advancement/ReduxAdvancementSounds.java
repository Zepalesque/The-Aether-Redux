package net.zepalesque.redux.advancement;

import com.aetherteam.aether.api.AetherAdvancementSoundOverrides;
import com.aetherteam.aether.api.registers.AdvancementSoundOverride;
import com.aetherteam.aether.client.AetherSoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;

public class ReduxAdvancementSounds {

    public static final DeferredRegister<AdvancementSoundOverride> SOUNDS = DeferredRegister.create(AetherAdvancementSoundOverrides.ADVANCEMENT_SOUND_OVERRIDE_REGISTRY_KEY, Redux.MODID);
    public static final RegistryObject<AdvancementSoundOverride> GOLD = SOUNDS.register("gold_dungeon", () -> new AdvancementSoundOverride(10,
            advancement -> advancement.getId().getPath().equals("gold_dungeon"),
            ReduxSoundEvents.ADVANCEMENT_GOLD));

    public static final RegistryObject<AdvancementSoundOverride> GENERAL_REDUX = SOUNDS.register("redux_general", () -> new AdvancementSoundOverride(0, (advancement) -> AetherAdvancementSoundOverrides.checkRoot(advancement, Redux.locate("install")), AetherSoundEvents.UI_TOAST_AETHER_GENERAL));
    public static final RegistryObject<AdvancementSoundOverride> LOBOTIUM = SOUNDS.register("lobotium_dungeon", () -> new AdvancementSoundOverride(10,
            advancement -> advancement.getId().getPath().equals("lobotium_dungeon"),
            ReduxSoundEvents.ADVANCEMENT_LOBOTIUM));

}
