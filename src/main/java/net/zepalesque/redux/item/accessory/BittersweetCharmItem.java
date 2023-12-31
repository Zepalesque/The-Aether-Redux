package net.zepalesque.redux.item.accessory;

import com.aetherteam.aether.item.accessories.pendant.PendantItem;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class BittersweetCharmItem extends PendantItem {


    public BittersweetCharmItem(ResourceLocation pendantLocation, Supplier<? extends SoundEvent> pendantSound, Properties properties) {
        super(pendantLocation, pendantSound, properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributes = HashMultimap.create();
        attributes.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "Bittersweet Charm Speed Boost", 0.05, AttributeModifier.Operation.MULTIPLY_TOTAL));
        return attributes;    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tagTooltips, ItemStack stack) {
        tagTooltips.add(Component.translatable("gui.aether_redux.bittersweet_charm_poison_chance").withStyle(ChatFormatting.BLUE));
        return super.getAttributesTooltip(tagTooltips, stack);
    }
}
