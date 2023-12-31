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

public class ValkyrieRingItem extends RingItem {



    public ValkyrieRingItem(Supplier<? extends SoundEvent> ringSound, Properties properties) {
        super(ringSound, properties);
    }
    
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributes = HashMultimap.create();
        attributes.put(ForgeMod.BLOCK_REACH.get(), new AttributeModifier(uuid, "Valkyrie Ring Block Reach Boost", 1.5, AttributeModifier.Operation.ADDITION));
        attributes.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(uuid, "Valkyrie Ring Entity Reach Boost", 1.5, AttributeModifier.Operation.ADDITION));
        return attributes;    }

}
