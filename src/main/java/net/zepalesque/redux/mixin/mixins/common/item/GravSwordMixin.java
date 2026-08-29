package net.zepalesque.redux.mixin.mixins.common.item;

import com.aetherteam.aether.item.combat.GravititeSwordItem;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.zepalesque.redux.config.ReduxConfig;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GravititeSwordItem.class)
public abstract class GravSwordMixin extends SwordItem {
	private GravSwordMixin() { super(null, null); }

    @WrapMethod(method = "hurtEnemy")
    public boolean redux$hurtEnemy(
		ItemStack stack,
		LivingEntity target,
		LivingEntity attacker,
		Operation<Boolean> og
	) {
		return ReduxConfig.getOrDefault(ReduxConfig.SERVER.improved_gravitite)
			? super.hurtEnemy(stack, target, attacker)
			: og.call(stack, target, attacker);
	}
}
