package net.zepalesque.redux.effect;

import com.aetherteam.aether.item.AetherItems;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BlightwardEffect extends MobEffect {

    public BlightwardEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFFF160);
    }


    public boolean isInstantenous() {
        return false;
    }

    public List<ItemStack> getCurativeItems() {
        ArrayList<ItemStack> curatives = new ArrayList<>();
        curatives.add(new ItemStack(AetherItems.SKYROOT_POISON_BUCKET.get()));
        return curatives;
    }
}
