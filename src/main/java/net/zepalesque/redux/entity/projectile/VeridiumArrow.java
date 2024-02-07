package net.zepalesque.redux.entity.projectile;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.zepalesque.redux.entity.ReduxEntityTypes;
import net.zepalesque.redux.item.ReduxItems;

public class VeridiumArrow extends AbstractArrow {
    public VeridiumArrow(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
        this.initialize();
    }

    public VeridiumArrow(double x, double y, double z, Level level) {
        super(ReduxEntityTypes.VERIDIUM_ARROW.get(), x, y, z, level);
        this.initialize();
    }

    public VeridiumArrow(LivingEntity shooter, Level level) {
        super(ReduxEntityTypes.VERIDIUM_ARROW.get(), shooter, level);
        this.initialize();
    }


    private void initialize() {
        this.setBaseDamage(5.0D);
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        super.shoot(x, y, z, velocity * 2.0F, inaccuracy);
    }

    @Override
    public void shootFromRotation(Entity shooter, float x, float y, float z, float velocity, float inaccuracy) {
        super.shootFromRotation(shooter, x, y, z, velocity * 2.0F, inaccuracy);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ReduxItems.VERIDIUM_ARROW.get());
    }


}
