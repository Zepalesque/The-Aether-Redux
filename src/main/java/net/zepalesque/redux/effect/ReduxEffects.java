package net.zepalesque.redux.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;

public class ReduxEffects {

    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Redux.MODID);
    public static final RegistryObject<MobEffect> BLIGHTWARD = EFFECTS.register("blightward", BlightwardEffect::new);
    public static final RegistryObject<MobEffect> ADRENALINE_RUSH = EFFECTS.register("adrenaline_rush", () ->
            new IncurableEffect(MobEffectCategory.BENEFICIAL, 0x8E5252)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, "112ed4c9-f427-4103-8427-034bdc37134d", 0.1, AttributeModifier.Operation.MULTIPLY_BASE)
                    .addAttributeModifier(Attributes.ATTACK_DAMAGE, "4a0257e4-9d12-49c7-91b7-4c346cd51f80", 0.1, AttributeModifier.Operation.MULTIPLY_BASE)
                    .addAttributeModifier(Attributes.ARMOR, "b76c2751-d4a4-44bf-84a8-be428999a4cb", 1.0, AttributeModifier.Operation.ADDITION)
    );
    public static final RegistryObject<MobEffect> ADRENAL_FATIGUE = EFFECTS.register("adrenal_fatigue", () ->
            new IncurableEffect(MobEffectCategory.HARMFUL, 0x54303D)
                    .addAttributeModifier(Attributes.MOVEMENT_SPEED, "09d3aa36-16b3-4bcd-8d27-0e0307d58d43", -0.1, AttributeModifier.Operation.MULTIPLY_BASE)
                    .addAttributeModifier(Attributes.ATTACK_DAMAGE, "f2c1afa3-1c28-4f78-bfa1-2668cb598a71", -0.35, AttributeModifier.Operation.MULTIPLY_BASE)
                    .addAttributeModifier(Attributes.ARMOR, "ee88a5ad-aa22-4c98-9a67-fcb15900d40f", -2.0, AttributeModifier.Operation.ADDITION)
    );
}
