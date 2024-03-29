package net.zepalesque.redux.item.food;

import com.aetherteam.aether.AetherConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;

public class PoprocksItem extends Item {
    public PoprocksItem(Properties properties) {
        super(properties);
    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        user.playSound(ReduxSoundEvents.POPROCKS_CRACKLE.get());
        return super.finishUsingItem(stack, level, user);
    }
}
