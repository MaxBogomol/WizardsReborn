package mod.maxbogomol.wizards_reborn.registry.common;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotion;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionHandler;
import mod.maxbogomol.wizards_reborn.common.alchemypotion.ColorAlchemyPotion;
import mod.maxbogomol.wizards_reborn.common.alchemypotion.FluidAlchemyPotion;
import mod.maxbogomol.wizards_reborn.common.alchemypotion.MilkAlchemyPotion;
import mod.maxbogomol.wizards_reborn.registry.common.fluid.WizardsRebornFluids;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeMod;

import java.awt.*;

public class WizardsRebornAlchemyPotions {
    public static AlchemyPotion EMPTY = new AlchemyPotion(WizardsReborn.MOD_ID+":empty");
    public static AlchemyPotion COMBINED = new AlchemyPotion(WizardsReborn.MOD_ID+":combined");
    public static FluidAlchemyPotion WATER, MILK, MUNDANE_BREW, ALCHEMY_OIL, OIL_TEA, WISSEN_TEA, MILK_TEA, MUSHROOM_BREW, HELLISH_MUSHROOM_BREW, MOR_BREW, FLOWER_BREW;
    public static AlchemyPotion NIGHT_VISION, INVISIBILITY, LEAPING, FIRE_RESISTANCE, SWIFTNESS, SLOWNESS, TURTLE_MASTER, WATER_BREATHING, HEALING, HARMING, POISON, REGENERATION, STRENGTH, WEAKNESS, LUCK, SLOW_FALLING;
    public static ColorAlchemyPotion ABSORPTION, RESISTANCE, MAGICAL_ATTUNEMENT, DARKNESS;

    public static void init() {
        WATER = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":water", Fluids.WATER, new Color(55, 92, 196));
        MILK = new MilkAlchemyPotion(WizardsReborn.MOD_ID+":milk", ForgeMod.MILK.get(), new Color(255, 255, 255));
        MUNDANE_BREW = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":mundane_brew", WizardsRebornFluids.MUNDANE_BREW.get(), new Color(50, 75, 141));
        ALCHEMY_OIL = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":alchemy_oil", WizardsRebornFluids.ALCHEMY_OIL.get(), new Color(96, 47, 59), new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 800, 3), new MobEffectInstance(MobEffects.WEAKNESS, 800, 1));
        OIL_TEA = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":oil_tea", WizardsRebornFluids.OIL_TEA.get(), new Color(189, 124, 129), new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 3600, 1), new MobEffectInstance(MobEffects.REGENERATION, 3600, 0), new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3600, 1));
        WISSEN_TEA = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":wissen_tea", WizardsRebornFluids.WISSEN_TEA.get(), new Color(119, 164, 208), new MobEffectInstance(WizardsRebornMobEffects.WISSEN_AURA.get(), 7800));
        MILK_TEA = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":milk_tea", WizardsRebornFluids.MILK_TEA.get(), new Color(194, 221, 238), new MobEffectInstance(MobEffects.REGENERATION, 1200));
        MUSHROOM_BREW = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":mushroom_brew", WizardsRebornFluids.MUSHROOM_BREW.get(), new Color(141, 107, 83));
        HELLISH_MUSHROOM_BREW = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":hellish_mushroom_brew", WizardsRebornFluids.HELLISH_MUSHROOM_BREW.get(), new Color(78, 27, 27));
        MOR_BREW = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":mor_brew", WizardsRebornFluids.MOR_BREW.get(), new Color(77, 84, 116), new MobEffectInstance(WizardsRebornMobEffects.MOR_SPORES.get(), 1600));
        FLOWER_BREW = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":flower_brew", WizardsRebornFluids.FLOWER_BREW.get(), new Color(32, 68, 38));

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

        ABSORPTION = new ColorAlchemyPotion(WizardsReborn.MOD_ID+":absorption", new Color(236, 203, 69), new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3));
        RESISTANCE = new ColorAlchemyPotion(WizardsReborn.MOD_ID+":resistance", new Color(102, 96, 114), new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 4800, 1));
        MAGICAL_ATTUNEMENT = new ColorAlchemyPotion(WizardsReborn.MOD_ID+":magical_attunement", new Color(68, 197, 148), new MobEffectInstance(WizardsRebornMobEffects.WISSEN_AURA.get(), 3600, 1));
        DARKNESS = new ColorAlchemyPotion(WizardsReborn.MOD_ID+":darkness", new Color(36, 30, 37), new MobEffectInstance(MobEffects.DARKNESS, 6000, 0));

        AlchemyPotionHandler.register(EMPTY);
        AlchemyPotionHandler.register(COMBINED);
        AlchemyPotionHandler.register(WATER);
        AlchemyPotionHandler.register(MILK);
        AlchemyPotionHandler.register(MUNDANE_BREW);
        AlchemyPotionHandler.register(ALCHEMY_OIL);
        AlchemyPotionHandler.register(OIL_TEA);
        AlchemyPotionHandler.register(WISSEN_TEA);
        AlchemyPotionHandler.register(MILK_TEA);
        AlchemyPotionHandler.register(MUSHROOM_BREW);
        AlchemyPotionHandler.register(HELLISH_MUSHROOM_BREW);
        AlchemyPotionHandler.register(MOR_BREW);
        AlchemyPotionHandler.register(FLOWER_BREW);
        AlchemyPotionHandler.register(NIGHT_VISION);
        AlchemyPotionHandler.register(INVISIBILITY);
        AlchemyPotionHandler.register(LEAPING);
        AlchemyPotionHandler.register(FIRE_RESISTANCE);
        AlchemyPotionHandler.register(SWIFTNESS);
        AlchemyPotionHandler.register(SLOWNESS);
        AlchemyPotionHandler.register(TURTLE_MASTER);
        AlchemyPotionHandler.register(WATER_BREATHING);
        AlchemyPotionHandler.register(HEALING);
        AlchemyPotionHandler.register(HARMING);
        AlchemyPotionHandler.register(POISON);
        AlchemyPotionHandler.register(REGENERATION);
        AlchemyPotionHandler.register(STRENGTH);
        AlchemyPotionHandler.register(WEAKNESS);
        AlchemyPotionHandler.register(LUCK);
        AlchemyPotionHandler.register(SLOW_FALLING);
        AlchemyPotionHandler.register(ABSORPTION);
        AlchemyPotionHandler.register(RESISTANCE);
        AlchemyPotionHandler.register(MAGICAL_ATTUNEMENT);
        AlchemyPotionHandler.register(DARKNESS);
    }
}
