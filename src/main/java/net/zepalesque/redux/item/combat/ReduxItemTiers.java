package net.zepalesque.redux.item.combat;

import com.aetherteam.aether.AetherTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.util.Lazy;
import net.zepalesque.redux.item.ReduxItems;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum ReduxItemTiers implements Tier {

    VERIDIUM(2, 750, 2.25F, 1.0F, 0, () -> Ingredient.of(ReduxItems.VERIDIUM_INGOT.get())),
    INFUSED_VERIDIUM(2, 750, 7.0F, 1.0F, 0, () -> Ingredient.of(ReduxItems.VERIDIUM_INGOT.get()));

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Lazy<Ingredient> repairMaterial;

    ReduxItemTiers(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Lazy<Ingredient> repairMaterial) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getUses() {
        return this.maxUses;
    }

    @Override
    public float getSpeed() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamage;
    }

    @Override
    public int getLevel() {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    @NotNull
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }

}
