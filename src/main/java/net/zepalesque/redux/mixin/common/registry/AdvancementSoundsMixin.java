package net.zepalesque.redux.mixin.common.registry;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.api.AetherAdvancementSoundOverrides;
import com.aetherteam.aether.api.registers.AdvancementSoundOverride;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.misc.ReduxTags;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.module.ModuleDescriptor;
import java.util.Map;
import java.util.Optional;

@Mixin(AetherAdvancementSoundOverrides.class)
public class AdvancementSoundsMixin {

        @Unique
        private static final boolean notAfter1_3_0 = ModuleDescriptor.Version.parse(ModList.get().getModFileById(Aether.MODID).versionString()).compareTo(ModuleDescriptor.Version.parse("1.19.2-1.3.0-forge")) <= 0;

        @Inject(method = "retrieveOverride", at = @At(value = "HEAD"), cancellable = true, remap = false)
        private static void redux$retrieve(Advancement advancement, CallbackInfoReturnable<SoundEvent> cir) {
                if (notAfter1_3_0) {
                        @Nullable Holder<AdvancementSoundOverride> holder = null;
                        for (Optional<Holder<AdvancementSoundOverride>> override : AetherAdvancementSoundOverrides.ADVANCEMENT_SOUND_OVERRIDE_REGISTRY.get().getEntries().stream().map(Map.Entry::getValue).map(AetherAdvancementSoundOverrides.ADVANCEMENT_SOUND_OVERRIDE_REGISTRY.get()::getHolder).toList()) {
                                if (override.isPresent() && override.get().get().matches(advancement)) {
                                        holder = override.get();
                                        if (!override.get().is(ReduxTags.Adverrides.LOW_PRIORITY)) {
                                                break;
                                        }
                                }
                        }
                        if (holder != null) {
                                cir.setReturnValue(holder.get().sound().get());
                        }
                }
        }
}
