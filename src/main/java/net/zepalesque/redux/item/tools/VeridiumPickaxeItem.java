package net.zepalesque.redux.item.tools;

import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.item.TooltipUtils;
import net.zepalesque.redux.item.VeridiumItem;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class VeridiumPickaxeItem extends PickaxeItem implements VeridiumItem {
    public VeridiumPickaxeItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public Item getUninfusedItem(ItemStack stack) {
        return ReduxItems.VERIDIUM_PICKAXE.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag advanced) {
        MutableComponent infusion = Component.translatable("tooltip.aether_redux.infusion_level", stack.getTagElement(VeridiumItem.NBT_KEY)).withStyle(ChatFormatting.GRAY);

        tooltips.add(infusion);
        Component info = TooltipUtils.TOOLTIP_SHIFT_FOR_INFO.apply(Component.translatable("gui.aether_redux.infusion_info"));
        tooltips.add(info);
        super.appendHoverText(stack, level, tooltips, advanced);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // TODO: Entity tag
        int amount = target instanceof Slider ? 1 : 2;
        ItemStack transform = VeridiumItem.deplete(stack, attacker, amount);
        if (!attacker.level().isClientSide() && transform != null && transform != stack) {
            // TODO: play sound
            attacker.setItemSlot(EquipmentSlot.MAINHAND, transform);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity user) {
        if (!user.level().isClientSide()) {
            boolean instaBreak = state.getDestroySpeed(level, pos) <= 0.0F;
            // Avoid decreasing infusion on insta-break blocks
            if (!instaBreak) {
                int amount = stack.isCorrectToolForDrops(state) ? 1 : 2;
                ItemStack transform = VeridiumItem.deplete(stack, user, amount);
                if (!user.level().isClientSide() && transform != null && transform != stack) {
                    // TODO: play sound
                    user.setItemSlot(EquipmentSlot.MAINHAND, transform);
                }
            }
        }
        return super.mineBlock(stack, level, state, pos, user);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return super.damageItem(stack, amount, entity, onBroken) * VeridiumItem.DURABILITY_DMG_MULTIPLIER;
    }

    public static class Uninfused extends PickaxeItem {

        public Uninfused(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
            super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        }

        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag advanced) {
            Component info = TooltipUtils.TOOLTIP_SHIFT_FOR_INFO.apply(Component.translatable("gui.aether_redux.infusion_info"));
            tooltips.add(info);
            super.appendHoverText(stack, level, tooltips, advanced);
        }
    }
}
