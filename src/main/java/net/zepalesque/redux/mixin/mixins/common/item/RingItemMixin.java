package net.zepalesque.redux.mixin.mixins.common.item;

import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.accessories.ring.RingItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.zepalesque.redux.advancement.ReduxAdvancementTriggers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RingItem.class)
public class RingItemMixin extends ItemMixin {

    @Override
    protected void redux$OnDestroyed(ItemEntity itemEntity, CallbackInfo ci) {
        super.redux$OnDestroyed(itemEntity, ci);
        RingItem self = (RingItem) (Object) this;
        if (AetherItems.GOLDEN_RING.get().equals(self) && !itemEntity.level().isClientSide() && itemEntity.getOwner() != null && itemEntity.getOwner() instanceof ServerPlayer sp)
            ReduxAdvancementTriggers.THROW_GOLD_RING_INTO_LAVA.get().trigger(sp);
    }
}
