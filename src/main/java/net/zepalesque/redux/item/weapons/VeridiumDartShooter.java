package net.zepalesque.redux.item.weapons;

import com.aetherteam.aether.entity.projectile.dart.AbstractDart;
import com.aetherteam.aether.item.combat.DartShooterItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.zepalesque.redux.entity.ReduxEntityTypes;
import net.zepalesque.redux.item.tools.VeridiumItem;
import net.zepalesque.redux.item.util.TooltipUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class VeridiumDartShooter extends DartShooterItem implements VeridiumItem {
    private final Supplier<? extends Item> uninfused;
    private final int useTime;

    public VeridiumDartShooter(Supplier<? extends Item> dartType, int useTime, Properties properties, Supplier<? extends Item> uninfused) {
        super(dartType, properties);
        this.uninfused = uninfused;
        this.useTime = useTime;
    }

    @Override
    public Item getUninfusedItem(ItemStack stack) {
        return this.uninfused.get();
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return this.useTime;
    }


    @Override
    public AbstractDart customDart(AbstractDart dart) {
            AbstractDart newDart = ReduxEntityTypes.INFUSED_VERIDIUM_DART.get().create(dart.level());
            if (newDart != null && dart.getOwner() != null) {
                Entity owner = dart.getOwner();
                newDart.setPos(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
                newDart.setOwner(owner);
                return newDart;
            }
            return super.customDart(dart);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        ItemStack original = super.finishUsingItem(stack, level, user);
        ItemStack transform = this.deplete(original, user, 1);
        return transform == null ? original : transform;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag advanced) {
        MutableComponent infusion = Component.translatable("tooltip.aether_redux.infusion_charge", stack.getTag() == null ? 0 : stack.getTag().getByte(VeridiumItem.NBT_KEY)).withStyle(ChatFormatting.GRAY);

        tooltips.add(infusion);
        Component info = TooltipUtils.TOOLTIP_SHIFT_FOR_INFO.apply(Component.translatable("gui.aether_redux.infusion_info"));
        tooltips.add(info);
        super.appendHoverText(stack, level, tooltips, advanced);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return super.damageItem(stack, amount, entity, onBroken) * VeridiumItem.DURABILITY_DMG_MULTIPLIER;
    }

    public static class Uninfused extends DartShooterItem {

        private final int useTime;
        public Uninfused(Supplier<? extends Item> dartType, int useTime, Properties properties) {
            super(dartType, properties);
            this.useTime = useTime;
        }

        @Override
        public int getUseDuration(ItemStack stack) {
            return this.useTime;
        }

        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag advanced) {
            Component info = TooltipUtils.TOOLTIP_SHIFT_FOR_INFO.apply(Component.translatable("gui.aether_redux.infusion_info"));
            tooltips.add(info);
            super.appendHoverText(stack, level, tooltips, advanced);
        }
    }
}
