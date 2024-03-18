package net.zepalesque.redux.advancement;

import com.aetherteam.aether.api.AetherAdvancementSoundOverrides;
import com.aetherteam.aether.api.registers.AdvancementSoundOverride;
import com.aetherteam.aether.client.AetherSoundEvents;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;

public class ReduxAdvancementSounds {

    public static final DeferredRegister<AdvancementSoundOverride> SOUNDS = DeferredRegister.create(AetherAdvancementSoundOverrides.ADVANCEMENT_SOUND_OVERRIDE_REGISTRY_KEY, Redux.MODID);

    public static final RegistryObject<AdvancementSoundOverride> GOLD = SOUNDS.register("gold_dungeon", () -> new AdvancementSoundOverride(
            advancement -> advancement.getId().getPath().equals("gold_dungeon"),
            ReduxSoundEvents.ADVANCEMENT_GOLD));

}
