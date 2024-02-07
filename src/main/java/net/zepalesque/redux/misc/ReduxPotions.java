package net.zepalesque.redux.misc;

import com.aetherteam.aether.effect.AetherEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.effect.BlightwardEffect;

public class ReduxPotions {

    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, Redux.MODID);
    public static final RegistryObject<Potion> INTOXICATION = POTIONS.register("intoxication", () -> new Potion(new MobEffectInstance(AetherEffects.INEBRIATION.get(), 900)));
    public static final RegistryObject<Potion> LONG_INTOXICATION = POTIONS.register("long_intoxication", () -> new Potion("intoxication", new MobEffectInstance(AetherEffects.INEBRIATION.get(), 1800)));

}
