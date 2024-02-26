package net.zepalesque.redux.item.weapons;

import com.aetherteam.aether.entity.projectile.dart.AbstractDart;
import com.aetherteam.aether.item.combat.DartShooterItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.zepalesque.redux.entity.ReduxEntityTypes;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.item.util.VeridiumItem;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class VeridiumDartShooter extends DartShooterItem implements VeridiumItem {
    private final Supplier<Item> other;
    private final int useTime;

    public VeridiumDartShooter(Supplier<? extends Item> dartType, Supplier<Item> other, int useTime, Properties properties) {
        super(dartType, properties);
        this.other = other;
        this.useTime = useTime;
    }

    @Override
    public Item getReplacementItem(ItemStack stack) {
        return this.other.get();
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return this.useTime;
    }

    @Override
    public AbstractDart customDart(AbstractDart dart) {
        if (this.isInfused(this)) {
            AbstractDart newDart = ReduxEntityTypes.INFUSED_VERIDIUM_DART.get().create(dart.level());
            if (newDart != null && dart.getOwner() != null) {
                Entity owner = dart.getOwner();
                newDart.setPos(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
                newDart.setOwner(owner);
//                dart.discard();
                return newDart;
            }
        }
        return super.customDart(dart);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        InteractionResultHolder<ItemStack> result = super.use(level, player, hand);
        ItemStack stack = player.getItemInHand(hand);
        if (result.getResult().consumesAction() && this.isInfused(stack) && !level.isClientSide && !player.getAbilities().instabuild){
            ItemStack stackReplacement = VeridiumItem.depleteInfusion(stack);
            if (!stackReplacement.getItem().equals(stack.getItem())) {
                player.setItemInHand(hand, stackReplacement);
            }
            else {
                stack.setTag(stackReplacement.getTag());
            }
        }
        return result;
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
