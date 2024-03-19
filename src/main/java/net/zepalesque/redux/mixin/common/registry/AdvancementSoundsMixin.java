package net.zepalesque.redux.mixin.common.registry;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.api.AetherAdvancementSoundOverrides;
import com.aetherteam.aether.api.registers.AdvancementSoundOverride;
import net.minecraft.advancements.Advancement;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.misc.ReduxTags;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.module.ModuleDescriptor;

@Mixin(AetherAdvancementSoundOverrides.class)
public class AdvancementSoundsMixin {

        @Unique
        private static final boolean before140 = ModuleDescriptor.Version.parse(ModList.get().getModFileById(Aether.MODID).versionString()).compareTo(ModuleDescriptor.Version.parse("1.20.1-1.4.0-neoforge")) < 0;

        @Inject(method = "retrieveOverride", at = @At(value = "HEAD"), cancellable = true)
        protected static void redux$retrieve(Advancement advancement, CallbackInfoReturnable<SoundEvent> cir) {
                if (before140) {
                        @Nullable RegistryObject<AdvancementSoundOverride> reg = null;
                        for (RegistryObject<AdvancementSoundOverride> ov : AetherAdvancementSoundOverrides.ADVANCEMENT_SOUND_OVERRIDES.getEntries()) {
                                if (ov.get().matches(advancement)) {
                                        reg = ov;
                                        if (ov.getHolder().isEmpty() || !ov.getHolder().get().is(ReduxTags.Adverrides.LOW_PRIORITY)) {
                                                break;
                                        }
                                }
                        }
                        if (reg != null) {
                                cir.setReturnValue(reg.get().sound().get());
                        }
                }
        }
}
