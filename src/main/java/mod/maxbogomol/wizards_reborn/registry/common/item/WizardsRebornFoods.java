package mod.maxbogomol.wizards_reborn.registry.common.item;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class WizardsRebornFoods {
    public static final FoodProperties MOR = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.2F).effect(() -> new MobEffectInstance(MobEffects.POISON, 450, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 350, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, 250, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.WEAKNESS, 550, 1), 1.0F).build();
    public static final FoodProperties PITCHER_DEW = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.1F).alwaysEat().fast().effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 3000, 0), 1.0F).effect(() -> new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 500, 0), 1.0F).build();
    public static final FoodProperties PITCHER_TURNIP = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.4F).build();
    public static final FoodProperties UNDERGROUND_GRAPE = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.2F).build();
    public static final FoodProperties FERAL_FRUIT = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.5F).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 420, 0), 1.0F).build();
    public static final FoodProperties ROTTEN_FERAL_FRUIT = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.1F).effect(() -> new MobEffectInstance(MobEffects.HUNGER, 1000, 0), 1.0F).effect(() -> new MobEffectInstance(WizardsRebornMobEffects.TIPSY.get(), 2500, 0), 1.0F).build();
    public static final FoodProperties ARCANE_GOLD_CARROT = (new FoodProperties.Builder()).nutrition(7).saturationMod(1.2F).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 0), 1.0F).build();
    public static final FoodProperties MOR_PIE = (new FoodProperties.Builder()).nutrition(8).saturationMod(0.4F).effect(() -> new MobEffectInstance(WizardsRebornMobEffects.MOR_SPORES.get(), 600, 0), 1.0F).build();
    public static final FoodProperties ELDER_MOR_PIE = (new FoodProperties.Builder()).nutrition(8).saturationMod(0.4F).effect(() -> new MobEffectInstance(WizardsRebornMobEffects.MOR_SPORES.get(), 900, 0), 1.0F).build();
    public static final FoodProperties PITCHER_TURNIP_PIE = (new FoodProperties.Builder()).nutrition(8).saturationMod(0.3F).effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 500, 0), 1.0F).build();
    public static final FoodProperties CARROT_PIE = (new FoodProperties.Builder()).nutrition(8).saturationMod(0.3F).build();
    public static final FoodProperties BLIN = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.2F).build();
    public static final FoodProperties SWEET_BERRIES_BLIN = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.2F).build();
    public static final FoodProperties GLOW_BERRIES_BLIN = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.2F).build();
    public static final FoodProperties UNDERGROUND_GRAPE_BLIN = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.2F).build();
    public static final FoodProperties PITCHER_DEW_BLIN = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.2F).build();
    public static final FoodProperties SHINY_CLOVER_BLIN = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.2F).build();
    public static final FoodProperties MOR_BLIN = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.2F).build();
    public static final FoodProperties ELDER_MOR_BLIN = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.2F).build();
    public static final FoodProperties HONEY_BLIN = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.2F).build();
    public static final FoodProperties CHOCOLATE_BLIN = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.2F).build();
    public static final FoodProperties MOR_SOUP = (new FoodProperties.Builder()).nutrition(7).saturationMod(0.7F).effect(() -> new MobEffectInstance(WizardsRebornMobEffects.MOR_SPORES.get(), 600, 0), 1.0F).build();
    public static final FoodProperties ELDER_MOR_SOUP = (new FoodProperties.Builder()).nutrition(7).saturationMod(0.7F).effect(() -> new MobEffectInstance(WizardsRebornMobEffects.MOR_SPORES.get(), 900, 0), 1.0F).build();
    public static final FoodProperties SWEET_BERRIES_JAM = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.2F).alwaysEat().build();
    public static final FoodProperties GLOW_BERRIES_JAM = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.2F).alwaysEat().build();
    public static final FoodProperties UNDERGROUND_GRAPE_JAM = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.2F).alwaysEat().build();
    public static final FoodProperties PITCHER_DEW_JAM = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.2F).alwaysEat().build();
    public static final FoodProperties SHINY_CLOVER_JAM = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.2F).alwaysEat().build();
    public static final FoodProperties MOR_JAM = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.2F).alwaysEat().build();
    public static final FoodProperties ELDER_MOR_JAM = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.2F).alwaysEat().build();
    public static final FoodProperties HONEY = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.2F).alwaysEat().build();
    public static final FoodProperties CHOCOLATE = (new FoodProperties.Builder()).nutrition(3).saturationMod(0.2F).alwaysEat().build();
    public static final FoodProperties SHRIMP = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.25F).build();
    public static final FoodProperties FRIED_SHRIMP = (new FoodProperties.Builder()).nutrition(8).saturationMod(0.6F).build();
}
