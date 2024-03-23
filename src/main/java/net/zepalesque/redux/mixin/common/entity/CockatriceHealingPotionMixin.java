package net.zepalesque.redux.mixin.common.entity;

import com.aetherteam.aether.entity.monster.Cockatrice;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Cockatrice.class)
public class CockatriceHealingPotionMixin {

    @Redirect(method = "performRangedAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"))
    private boolean preventSlowFall(Level instance, Entity entity, LivingEntity target)
    {
        boolean b = instance.addFreshEntity(entity);
        entity.remove(Entity.RemovalReason.DISCARDED);
        Cockatrice c = (Cockatrice) (Object) this;

        Vec3 vec3 = target.getDeltaMovement();
        double d0 = target.getX() + vec3.x - c.getX();
        double d1 = target.getEyeY() - (double)1.1F - c.getY();
        double d2 = target.getZ() + vec3.z - c.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        Potion potion = Potions.REGENERATION;

        ThrownPotion thrownpotion = new ThrownPotion(c.level(), c);
        thrownpotion.setItem(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), potion));
        thrownpotion.setXRot(thrownpotion.getXRot() - -20.0F);
        thrownpotion.shoot(d0, d1 + d3 * 0.2D, d2, 0.75F, 8.0F);

        return c.level().addFreshEntity(thrownpotion);
    }

}
