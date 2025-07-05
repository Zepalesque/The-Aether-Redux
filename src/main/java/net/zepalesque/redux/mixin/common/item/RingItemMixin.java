package net.zepalesque.redux.mixin.common.item;

import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.accessories.ring.RingItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.zepalesque.redux.advancement.trigger.ReduxAdvancementTriggers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RingItem.class)
public class RingItemMixin extends ItemMixin {

    @Override
    protected void redux$OnDestroyed(ItemEntity itemEntity, CallbackInfo ci) {
        super.redux$OnDestroyed(itemEntity, ci);
        RingItem self = (RingItem) (Object) this;
        if (AetherItems.GOLDEN_RING.get().equals(self) && !itemEntity.getLevel().isClientSide() && itemEntity.getOwner() != null
            // implicit nullcast in instanceof check, may be able to improve in 1.20.1 as it uses the entity rather than the uuid i believe?
            && itemEntity.getLevel().getPlayerByUUID(itemEntity.getOwner()) instanceof ServerPlayer sp)
            ReduxAdvancementTriggers.THROW_GOLD_RING_INTO_LAVA.trigger(sp);
    }
}
