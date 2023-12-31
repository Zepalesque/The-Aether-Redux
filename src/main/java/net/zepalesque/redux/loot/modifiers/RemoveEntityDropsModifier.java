package net.zepalesque.redux.loot.modifiers;

import com.aetherteam.aether.AetherTags.Biomes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

public class RemoveEntityDropsModifier extends LootModifier {

    public static final Codec<RemoveEntityDropsModifier> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter(modifier -> modifier.item),
            LootModifier.LOOT_CONDITIONS_CODEC.fieldOf("conditions").forGetter(modifier -> modifier.conditions)
    ).apply(instance, RemoveEntityDropsModifier::new));

    private final Item item;
    public RemoveEntityDropsModifier(Item item, LootItemCondition[] conditions) {
        super(conditions);
        this.item = item;
    }

    public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> lootStacks, LootContext context) {
        Vec3 originVec = context.getParamOrNull(LootContextParams.ORIGIN);
        if (originVec != null && context.getLevel().getBiome(BlockPos.containing(originVec)).is(Biomes.NO_WHEAT_SEEDS)) {
            lootStacks.removeIf((itemStack) -> itemStack.is(this.item));
        }

        return lootStacks;
    }

    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
