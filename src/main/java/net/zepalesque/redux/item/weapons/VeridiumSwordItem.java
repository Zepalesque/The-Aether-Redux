package net.zepalesque.redux.item.weapons;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.zepalesque.redux.entity.projectile.Ember;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.item.util.VeridiumItem;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class VeridiumSwordItem extends SwordItem implements VeridiumItem {

    public VeridiumSwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    /** Debug method for spark spawning */
    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if (entity instanceof Player player) {
            Vec3 look = player.getViewVector(1.0F).normalize();
            float rotation = Mth.wrapDegrees(entity.getYRot());
            Ember ember = new Ember(entity.level(), player);
            ember.setPos(entity.getX(), entity.getY() + entity.getEyeHeight(), entity.getZ());
            ember.shootFromRotation(player, entity.getXRot(), rotation, 0.0F, 1.0F, 1.0F);
            if (!player.level().isClientSide()) {
                entity.level().addFreshEntity(ember);
            }
        }
        return super.onEntitySwing(stack, entity);

    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (this.isCharged(pStack) && !pAttacker.level().isClientSide && (!(pAttacker instanceof Player) || !((Player) pAttacker).getAbilities().instabuild)) {
            ItemStack stackReplacement = VeridiumItem.decreaseCharge(pStack);
            if (!stackReplacement.getItem().equals(pStack.getItem())) {
                pAttacker.setItemSlot(EquipmentSlot.MAINHAND, stackReplacement);
            }
            else {
                pStack.setTag(stackReplacement.getTag());
            }
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        if (pState.getDestroySpeed(pLevel, pPos) != 0F && this.isCharged(pStack) && !pEntityLiving.level().isClientSide && (!(pEntityLiving instanceof Player player) || !player.getAbilities().instabuild)) {
            ItemStack stackReplacement = VeridiumItem.decreaseCharge(pStack);
            if (!stackReplacement.getItem().equals(pStack.getItem())) {
                pEntityLiving.setItemSlot(EquipmentSlot.MAINHAND, stackReplacement);
            }
            else {
                pStack.setTag(stackReplacement.getTag());
            }
        }
        return super.mineBlock(pStack, pLevel, pState, pPos, pEntityLiving);

    }

    public Item getReplacementItem(ItemStack stack){
        return this.isCharged(stack) ? ReduxItems.VERIDIUM_SWORD.get() : ReduxItems.INFUSED_VERIDIUM_SWORD.get();
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        MutableComponent component = Component.translatable("tooltip.aether_redux.ambrosium_charge", VeridiumItem.getCharge(pStack)).withStyle(ChatFormatting.GRAY);

        pTooltipComponents.add(component);
        Component c = ReduxItems.TooltipUtils.SHIFT_OR_DEFAULT.apply(Component.translatable("gui.aether_redux.infusion_tooltip"));
        pTooltipComponents.add(c);
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return this.isCharged(stack) ? amount * VeridiumItem.CHARGED_DAMAGE_MULTIPLIER : amount;
    }
}
