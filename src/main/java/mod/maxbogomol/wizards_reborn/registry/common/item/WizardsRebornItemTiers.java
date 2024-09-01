package mod.maxbogomol.wizards_reborn.registry.common.item;

import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum WizardsRebornItemTiers implements Tier {

    ARCANE_GOLD(3, 450, 8f, 2f, 25, () -> Ingredient.of(WizardsRebornItems.ARCANE_GOLD_INGOT.get())),
    ARCANE_WOOD(2, 175, 5f, 0.5f, 15, () -> Ingredient.of(WizardsRebornItems.ARCANE_WOOD_BRANCH.get())),
    INNOCENT_WOOD(3, 225, 6.5f, 1f, 20, () -> Ingredient.of(WizardsRebornItems.INNOCENT_WOOD_BRANCH.get()));

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final LazyLoadedValue<Ingredient> repairMaterial;

    WizardsRebornItemTiers(int harvestLevel, int maxUses, float efficiency,
                           float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = new LazyLoadedValue<>(repairMaterial);
    }

    @Override
    public int getUses() {
        return maxUses;
    }

    @Override
    public float getSpeed() {
        return efficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return attackDamage;
    }

    @Override
    public int getLevel() {
        return harvestLevel;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairMaterial.get();
    }
}
