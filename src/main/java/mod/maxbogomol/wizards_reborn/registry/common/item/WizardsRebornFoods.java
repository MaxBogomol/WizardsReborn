package mod.maxbogomol.wizards_reborn.registry.common.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class WizardsRebornFoods {
    public static final FoodProperties MOR = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.2F).effect(new MobEffectInstance(MobEffects.POISON, 450, 0), 1.0F).effect(new MobEffectInstance(MobEffects.CONFUSION, 350, 0), 1.0F).effect(new MobEffectInstance(MobEffects.BLINDNESS, 250, 0), 1.0F).effect(new MobEffectInstance(MobEffects.WEAKNESS, 550, 1), 1.0F).build();
    public static final FoodProperties PITCHER_DER = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.1F).alwaysEat().fast().build();
    public static final FoodProperties PITCHER_TURNIP = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.4F).build();
    public static final FoodProperties UNDERGROUND_GRAPE = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.2F).build();
    public static final FoodProperties SHRIMP = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.25F).build();
    public static final FoodProperties FRIED_SHRIMP = (new FoodProperties.Builder()).nutrition(8).saturationMod(0.6F).build();
}
