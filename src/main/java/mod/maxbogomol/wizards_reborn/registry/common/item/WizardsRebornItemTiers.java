package mod.maxbogomol.wizards_reborn.registry.common.item;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public class WizardsRebornItemTiers implements Tier {
    public static WizardsRebornItemTiers ARCANE_GOLD = new WizardsRebornItemTiers(3, 450, 8f, 2f, 25, () -> Ingredient.of(WizardsRebornItems.ARCANE_GOLD_INGOT.get()));
    public static WizardsRebornItemTiers ARCANE_WOOD = new WizardsRebornItemTiers(2, 175, 5f, 0.5f, 15, () -> Ingredient.of(WizardsRebornItems.ARCANE_WOOD_BRANCH.get()));
    public static WizardsRebornItemTiers INNOCENT_WOOD = new WizardsRebornItemTiers(3, 225, 6.5f, 1f, 20, () -> Ingredient.of(WizardsRebornItems.INNOCENT_WOOD_BRANCH.get()));

    public final int harvestLevel;
    public final int maxUses;
    public final float efficiency;
    public final float attackDamage;
    public final int enchantability;
    public final Supplier<Ingredient> repairMaterial;

    WizardsRebornItemTiers(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = repairMaterial;
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
