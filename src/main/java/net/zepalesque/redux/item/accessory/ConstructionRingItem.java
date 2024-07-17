package net.zepalesque.redux.item.accessory;

import com.aetherteam.aether.item.accessories.ring.RingItem;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;
import java.util.function.Supplier;

public class ConstructionRingItem extends RingItem {



    public ConstructionRingItem(Supplier<? extends SoundEvent> ringSound, Properties properties) {
        super(ringSound, properties);
    }
    
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributes = HashMultimap.create();
        attributes.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(uuid, "Valkyrie Ring Reach Boost", 1.5, AttributeModifier.Operation.ADDITION));
        return attributes;    }

}
