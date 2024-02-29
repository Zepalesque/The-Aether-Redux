package net.zepalesque.redux.item.tools;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.item.util.VeridiumItem;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class VeridiumShovelItem extends ShovelItem implements VeridiumItem {
    public VeridiumShovelItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        InteractionResult result = super.useOn(pContext);
        if (result == InteractionResult.sidedSuccess(pContext.getLevel().isClientSide) && this.isInfused(pContext.getItemInHand()) && !pContext.getPlayer().level().isClientSide && !pContext.getPlayer().getAbilities().instabuild){
          ItemStack pStack = pContext.getItemInHand();
            ItemStack stackReplacement = VeridiumItem.depleteInfusion(pStack, pContext.getPlayer());
            if (!stackReplacement.getItem().equals(pStack.getItem())) {
                pContext.getPlayer().setItemInHand(pContext.getHand(), stackReplacement);
            }
            else {
                pStack.setTag(stackReplacement.getTag());
            }
        }
        return result;
    }

    @Override
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        if (pState.getDestroySpeed(pLevel, pPos) != 0F && this.isInfused(pStack) && !pEntityLiving.level().isClientSide && (!(pEntityLiving instanceof Player player) || !player.getAbilities().instabuild)) {
            ItemStack stackReplacement = VeridiumItem.depleteInfusion(pStack, pEntityLiving);
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
        return this.isInfused(stack) ? ReduxItems.VERIDIUM_SHOVEL.get() : ReduxItems.INFUSED_VERIDIUM_SHOVEL.get();
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        MutableComponent component = Component.translatable("tooltip.aether_redux.ambrosium_charge", VeridiumItem.getInfusion(pStack)).withStyle(ChatFormatting.GRAY);

        pTooltipComponents.add(component);
        Component c = ReduxItems.TooltipUtils.SHIFT_OR_DEFAULT.apply(Component.translatable("gui.aether_redux.infusion_tooltip"));
        pTooltipComponents.add(c);
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return this.isInfused(stack) ? amount * VeridiumItem.CHARGED_DAMAGE_MULTIPLIER : amount;
    }

}
