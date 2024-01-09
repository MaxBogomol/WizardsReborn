package mod.maxbogomol.wizards_reborn.common.alchemypotion;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotion;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotions;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.material.Fluids;

import java.awt.*;

public class RegisterAlchemyPotions {
    public static AlchemyPotion EMPTY = new AlchemyPotion(WizardsReborn.MOD_ID+":empty");
    public static AlchemyPotion COMBINED = new AlchemyPotion(WizardsReborn.MOD_ID+":combined");
    public static FluidAlchemyPotion WATER, MUNDANE_BREW, ALCHEMY_OIL, OIL_TEA, WISSEN_TEA, MUSHROOM_BREW, HELLISH_MUSHROOM_BREW, MOR_BREW, FLOWER_BREW;
    public static AlchemyPotion NIGHT_VISION, INVISIBILITY, LEAPING, FIRE_RESISTANCE, SWIFTNESS, SLOWNESS, TURTLE_MASTER, WATER_BREATHING, HEALING, HARMING, POISON, REGENERATION, STRENGTH, WEAKNESS, LUCK, SLOW_FALLING;

    public static void init() {
        WATER = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":water", Fluids.WATER, new Color(55, 92, 196));
        MUNDANE_BREW = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":mundane_brew", WizardsReborn.MUNDANE_BREW_FLUID.get(), new Color(50, 75, 141));
        ALCHEMY_OIL = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":alchemy_oil", WizardsReborn.ALCHEMY_OIL_FLUID.get(), new Color(96, 47, 59), new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 800, 3), new MobEffectInstance(MobEffects.WEAKNESS, 800, 1));
        OIL_TEA = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":oil_tea", WizardsReborn.OIL_TEA_FLUID.get(), new Color(189, 124, 129), new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 3600, 1), new MobEffectInstance(MobEffects.REGENERATION, 3600, 0), new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3600, 1));
        WISSEN_TEA = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":wissen_tea", WizardsReborn.WISSEN_TEA_FLUID.get(), new Color(119, 164, 208), new MobEffectInstance(WizardsReborn.WISSEN_AURA_EFFECT.get(), 7800));
        MUSHROOM_BREW = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":mushroom_brew", WizardsReborn.MUSHROOM_BREW_FLUID.get(), new Color(141, 107, 83));
        HELLISH_MUSHROOM_BREW = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":hellish_mushroom_brew", WizardsReborn.HELLISH_MUSHROOM_BREW_FLUID.get(), new Color(78, 27, 27));
        MOR_BREW = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":mor_brew", WizardsReborn.MOR_BREW_FLUID.get(), new Color(77, 84, 116), new MobEffectInstance(WizardsReborn.MOR_SPORES_EFFECT.get(), 1600));
        FLOWER_BREW = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":flower_brew", WizardsReborn.FLOWER_BREW_FLUID.get(), new Color(32, 68, 38));

        NIGHT_VISION = new AlchemyPotion(WizardsReborn.MOD_ID+":night_vision", new MobEffectInstance(MobEffects.NIGHT_VISION, 12000));
        INVISIBILITY = new AlchemyPotion(WizardsReborn.MOD_ID+":invisibility", new MobEffectInstance(MobEffects.INVISIBILITY, 12000));
        LEAPING = new AlchemyPotion(WizardsReborn.MOD_ID+":leaping", new MobEffectInstance(MobEffects.JUMP, 12000, 1));
        FIRE_RESISTANCE = new AlchemyPotion(WizardsReborn.MOD_ID+":fire_resistance", new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 12000));
        SWIFTNESS = new AlchemyPotion(WizardsReborn.MOD_ID+":swiftness", new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 12000, 1));
        SLOWNESS = new AlchemyPotion(WizardsReborn.MOD_ID+":slowness", new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 12000, 2));
        TURTLE_MASTER = new AlchemyPotion(WizardsReborn.MOD_ID+":turtle_master", new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1000, 5), new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1000, 3));
        WATER_BREATHING = new AlchemyPotion(WizardsReborn.MOD_ID+":water_breathing", new MobEffectInstance(MobEffects.WATER_BREATHING, 12000));
        HEALING = new AlchemyPotion(WizardsReborn.MOD_ID+":healing", new MobEffectInstance(MobEffects.HEAL, 1, 1));
        HARMING = new AlchemyPotion(WizardsReborn.MOD_ID+":harming", new MobEffectInstance(MobEffects.HARM, 1, 1));
        POISON = new AlchemyPotion(WizardsReborn.MOD_ID+":poison", new MobEffectInstance(MobEffects.POISON, 1200, 1));
        REGENERATION = new AlchemyPotion(WizardsReborn.MOD_ID+":regeneration", new MobEffectInstance(MobEffects.REGENERATION, 3600, 1));
        STRENGTH = new AlchemyPotion(WizardsReborn.MOD_ID+":strength", new MobEffectInstance(MobEffects.DAMAGE_BOOST, 7200, 1));
        WEAKNESS = new AlchemyPotion(WizardsReborn.MOD_ID+":weakness", new MobEffectInstance(MobEffects.WEAKNESS, 3600, 1));
        LUCK = new AlchemyPotion(WizardsReborn.MOD_ID+":luck", new MobEffectInstance(MobEffects.LUCK, 12000, 1));
        SLOW_FALLING = new AlchemyPotion(WizardsReborn.MOD_ID+":slow_falling", new MobEffectInstance(MobEffects.SLOW_FALLING, 12000));

        AlchemyPotions.register(EMPTY);
        AlchemyPotions.register(COMBINED);
        AlchemyPotions.register(WATER);
        AlchemyPotions.register(MUNDANE_BREW);
        AlchemyPotions.register(ALCHEMY_OIL);
        AlchemyPotions.register(OIL_TEA);
        AlchemyPotions.register(WISSEN_TEA);
        AlchemyPotions.register(MUSHROOM_BREW);
        AlchemyPotions.register(HELLISH_MUSHROOM_BREW);
        AlchemyPotions.register(MOR_BREW);
        AlchemyPotions.register(FLOWER_BREW);
        AlchemyPotions.register(NIGHT_VISION);
        AlchemyPotions.register(INVISIBILITY);
        AlchemyPotions.register(LEAPING);
        AlchemyPotions.register(FIRE_RESISTANCE);
        AlchemyPotions.register(SWIFTNESS);
        AlchemyPotions.register(SLOWNESS);
        AlchemyPotions.register(TURTLE_MASTER);
        AlchemyPotions.register(WATER_BREATHING);
        AlchemyPotions.register(HEALING);
        AlchemyPotions.register(HARMING);
        AlchemyPotions.register(POISON);
        AlchemyPotions.register(REGENERATION);
        AlchemyPotions.register(STRENGTH);
        AlchemyPotions.register(WEAKNESS);
        AlchemyPotions.register(LUCK);
        AlchemyPotions.register(SLOW_FALLING);
    }
}
