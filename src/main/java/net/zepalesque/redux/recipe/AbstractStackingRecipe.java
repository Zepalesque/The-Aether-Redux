package net.zepalesque.redux.recipe;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.zepalesque.redux.api.ItemStackConstructor;
import net.zepalesque.redux.item.util.CustomStackingBehavior;

import javax.annotation.Nullable;
import java.util.Optional;

public abstract class AbstractStackingRecipe implements StackingRecipe {
    protected final RecipeType<?> type;
    protected final ResourceLocation id;
    protected final Ingredient ingredient;
    protected final ItemStackConstructor result;
    protected final Optional<CompoundTag> additional;
    protected final Optional<SoundEvent> sound;

    public AbstractStackingRecipe(RecipeType<?> type, ResourceLocation id, Ingredient ingredient, ItemStackConstructor result, Optional<CompoundTag> additional, Optional<SoundEvent> sound) {
        this.type = type;
        this.id = id;
        this.ingredient = ingredient;
        this.result = result;
        this.additional = additional;
        this.sound = sound;
    }

    public boolean matches(Level level, ItemStack item) {
        return this.getIngredient().test(item);
    }

    @Nullable
    @Override
    public ItemStack getResultStack(ItemStack originalStack) {
        if (!this.getIngredient().test(originalStack)) {
            return null;
        }

        ItemStack resultStack = this.getResult().createStack();

        resultStack.setTag(originalStack.getTag() != null ? originalStack.getTag().copy() : null);
        resultStack.setCount(originalStack.getCount());
        if (resultStack.getItem() instanceof CustomStackingBehavior custom) {
            resultStack = custom.transformStack(this.ingredient, resultStack, this.type, this.additional);
        }
        return resultStack;
    }

    @Override
    public RecipeType<?> getType() {
        return this.type;
    }

    @Override
    public Ingredient getIngredient() {
        return this.ingredient;
    }

    @Override
    public ItemStackConstructor getResult() {
        return this.result;
    }

    public Optional<CompoundTag> getAdditionalData() {
        return additional;
    }

    public Optional<SoundEvent> getSound() {
        return sound;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }
}
