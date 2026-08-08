package net.zepalesque.redux.item.property;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Items;

public class ReduxFoods {

    public static final FoodProperties WYND_OATS = new Builder().fast().nutrition(1).saturationModifier(0F).build();
    public static final FoodProperties GUMMY_SWET_NERF = new Builder().fast().nutrition(7).saturationModifier(0.6F).build();
    public static final FoodProperties CAELGAE_CLUMP = new Builder().fast().nutrition(1).saturationModifier(0.1F).build();
    public static final FoodProperties SEAWEED_SALAD = new Builder().nutrition(5).saturationModifier(0.4F).usingConvertsTo(Items.BOWL).build();
    public static final FoodProperties TURBO_VERBENA = new Builder().nutrition(1).saturationModifier(0.1F).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 0), 1f).build();

}
