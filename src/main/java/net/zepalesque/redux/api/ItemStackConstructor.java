package net.zepalesque.redux.api;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public record ItemStackConstructor(Item item, Optional<CompoundTag> tag) {
    public static final Codec<ItemStackConstructor> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(ItemStackConstructor::item),
            CompoundTag.CODEC.optionalFieldOf("tag").forGetter(ItemStackConstructor::tag)
    ).apply(builder, ItemStackConstructor::new));

    public ItemStack createStack() {
        return new ItemStack(this.item(), 1, this.tag().orElse(null));
    }
}
