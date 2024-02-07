package net.zepalesque.redux.item.weapons;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.zepalesque.redux.entity.projectile.VeridiumArrow;

public class VeridiumArrowItem extends ArrowItem {
    public VeridiumArrowItem(Properties properties) {
        super(properties);
    }

    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter) {
        return new VeridiumArrow(shooter, level);
    }
}
