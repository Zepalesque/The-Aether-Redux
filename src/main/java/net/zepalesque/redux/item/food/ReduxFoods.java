package net.zepalesque.redux.item.food;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.zepalesque.redux.effect.ReduxEffects;

public class ReduxFoods {
    public static final FoodProperties NERFED_GUMMY_SWET = (new FoodProperties.Builder()).fast().nutrition(7).saturationMod(0.6F).build();
    public static final FoodProperties SWET_JELLY = (new FoodProperties.Builder()).fast().nutrition(2).saturationMod(0.05F).build();
    public static final FoodProperties BLUEBERRY_PIE = (new FoodProperties.Builder()).nutrition(9).saturationMod(0.3F).build();
    public static final FoodProperties ENCHANTED_BLUEBERRY_PIE = (new FoodProperties.Builder()).nutrition(11).saturationMod(0.4F)
            .effect( () -> new MobEffectInstance(MobEffects.REGENERATION, 100, 0), 1F).build();

    public static final FoodProperties WYNDSPROUT_SEEDS = (new FoodProperties.Builder()).fast().nutrition(1).saturationMod(0F).build();

    public static final FoodProperties SKYSPROUT_SEEDS = (new FoodProperties.Builder()).fast().nutrition(1).effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 200, 0), 0.85F).saturationMod(0F).build();
    public static final FoodProperties MOUSE_EAR_SOUP = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.6F).build();
    public static final FoodProperties GLOWBUDS = (new FoodProperties.Builder()).fast().nutrition(1).saturationMod(0.05F)
            .effect( () -> new MobEffectInstance(ReduxEffects.BLIGHTWARD.get(), 610, 0), 0.2F).alwaysEat().build();

    public static final FoodProperties PURIFIED_GLOWBUDS = (new FoodProperties.Builder()).fast().nutrition(2).saturationMod(0.1F)
            .effect( () -> new MobEffectInstance(ReduxEffects.BLIGHTWARD.get(), 1510, 0), 1.0F).alwaysEat().build();

    public static final FoodProperties WYNDSPROUT_BAGEL = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.2F).build();
    public static final FoodProperties BLUEBERRY_BAGEL = (new FoodProperties.Builder()).nutrition(10).saturationMod(0.2F).build();

    public static final FoodProperties OATMEAL = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.7F).build();

    public static final FoodProperties QUICKROOT = (new FoodProperties.Builder()).fast().nutrition(3).saturationMod(0.1F)
            .effect( () -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 610, 0), 2F).build();

    public static final FoodProperties CHROMABERRY = (new FoodProperties.Builder()).fast().nutrition(3).saturationMod(0.1F).build();

    public static final FoodProperties LIGHTROOT_CLUMP = (new FoodProperties.Builder()).fast().nutrition(2).saturationMod(0.2F)
            .effect( () -> new MobEffectInstance(MobEffects.NIGHT_VISION, 450, 0), 2F).alwaysEat().build();

}
