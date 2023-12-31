    package net.zepalesque.redux.loot.modifiers;

import com.aetherteam.aether.AetherTags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.zepalesque.redux.item.ReduxItems;

    public class AddOatsModifier extends LootModifier {
    public static final Codec<AddOatsModifier> CODEC = RecordCodecBuilder.create((instance) -> LootModifier.codecStart(instance).apply(instance, AddOatsModifier::new));

    public AddOatsModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    @Override
    public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> lootStacks, LootContext context) {
        Vec3 originVec = context.getParamOrNull(LootContextParams.ORIGIN);
        if (originVec != null && context.getLevel().getBiome(BlockPos.containing(originVec)).is(AetherTags.Biomes.NO_WHEAT_SEEDS)) {
            if (!context.getLevel().getBlockState(BlockPos.containing(originVec)).hasProperty(DoublePlantBlock.HALF) || context.getLevel().getBlockState(BlockPos.containing(originVec)).getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER) {
                int count = 0;
                float f = context.getRandom().nextFloat();
                if (f >= 0.65) { count = 1; }
                if (f >= 0.9) { count = 2; }
                lootStacks.add(new ItemStack(ReduxItems.OATS.get(), count));
            }
        }
        return lootStacks;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return AddOatsModifier.CODEC;
    }
}
