package mod.maxbogomol.wizards_reborn.common.item.equipment;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum CustomArmorMaterial implements ArmorMaterial {
    ARCANE_GOLD("arcane_gold", 20, new int[]{ 2, 6, 5, 2 }, 30, SoundEvents.ARMOR_EQUIP_GOLD, 1f, 0f, () -> Ingredient.of(WizardsReborn.ARCANE_GOLD_INGOT.get())),
    INVENTOR_WIZARD("inventor_wizard", 25, new int[]{ 3, 7, 5, 3 }, 40, SoundEvents.ARMOR_EQUIP_LEATHER, 1f, 0f, () -> Ingredient.of(WizardsReborn.ARCANE_GOLD_INGOT.get())),
    ARCANE_FORTRESS("arcane_fortress", 30, new int[]{ 3, 8, 6, 3 }, 30, SoundEvents.ARMOR_EQUIP_GOLD, 2f, 0.15f, () -> Ingredient.of(WizardsReborn.ARCANE_GOLD_INGOT.get()));

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    private static final int[] BASE_DURABILITY = { 11, 16, 15, 13 };

    CustomArmorMaterial(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, SoundEvent equipSound,
                      float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type pType) {
        return BASE_DURABILITY[pType.ordinal()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type pType) {
        return this.protectionAmounts[pType.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return WizardsReborn.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}