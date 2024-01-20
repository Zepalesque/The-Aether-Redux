package net.zepalesque.redux.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class IncurableEffect extends MobEffect {

    protected IncurableEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public boolean isInstantenous() {
        return false;
    }

    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }
}
