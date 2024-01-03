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
    public static FluidAlchemyPotion WATER, MUNDANE, ALCHEMY_OIL, OIL_TEA, WISSEN_TEA, MUSHROOM_BREW, HELLISH_MUSHROOM_BREW, MOR_BREW, FLOWER_BREW;
    public static EffectAlchemyPotion NIGHT_VISION, INVISIBILITY, LEAPING, FIRE_RESISTANCE, SWIFTNESS, SLOWNESS, TURTLE_MASTER, WATER_BREATHING, HEALING, HARMING, POISON, REGENERATION, STRENGTH, WEAKNESS, LUCK, SLOW_FALLING;

    public static void init() {
        WATER = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":water", Fluids.WATER, new Color(55, 92, 196));
        MUNDANE = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":mundane", Fluids.WATER, new Color(55, 92, 196));
        ALCHEMY_OIL = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":alchemy_oil", Fluids.WATER, new Color(55, 92, 196));
        OIL_TEA = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":oil_tea", Fluids.WATER, new Color(55, 92, 196));
        WISSEN_TEA = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":wissen_tea", Fluids.WATER, new Color(55, 92, 196));
        MUSHROOM_BREW = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":mushroom_brew", Fluids.WATER, new Color(55, 92, 196));
        HELLISH_MUSHROOM_BREW = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":hellish_mushroom_brew", Fluids.WATER, new Color(55, 92, 196));
        MOR_BREW = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":mor_brew", Fluids.WATER, new Color(55, 92, 196));
        FLOWER_BREW = new FluidAlchemyPotion(WizardsReborn.MOD_ID+":flower_brew", Fluids.WATER, new Color(55, 92, 196));

        NIGHT_VISION = new EffectAlchemyPotion(WizardsReborn.MOD_ID+":night_vision", new MobEffectInstance(MobEffects.NIGHT_VISION, 12000));
        INVISIBILITY = new EffectAlchemyPotion(WizardsReborn.MOD_ID+":invisibility", new MobEffectInstance(MobEffects.INVISIBILITY, 12000));
        LEAPING = new EffectAlchemyPotion(WizardsReborn.MOD_ID+":leaping", new MobEffectInstance(MobEffects.JUMP, 12000, 1));
        FIRE_RESISTANCE = new EffectAlchemyPotion(WizardsReborn.MOD_ID+":fire_resistance", new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 12000));
        SWIFTNESS = new EffectAlchemyPotion(WizardsReborn.MOD_ID+":swiftness", new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 12000, 1));
        SLOWNESS = new EffectAlchemyPotion(WizardsReborn.MOD_ID+":slowness", new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 12000, 2));
        TURTLE_MASTER = new EffectAlchemyPotion(WizardsReborn.MOD_ID+":turtle_master", new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1000, 5), new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1000, 3));
        WATER_BREATHING = new EffectAlchemyPotion(WizardsReborn.MOD_ID+":water_breathing", new MobEffectInstance(MobEffects.WATER_BREATHING, 12000));
        HEALING = new EffectAlchemyPotion(WizardsReborn.MOD_ID+":healing", new MobEffectInstance(MobEffects.HEAL, 1, 1));
        HARMING = new EffectAlchemyPotion(WizardsReborn.MOD_ID+":harming", new MobEffectInstance(MobEffects.HARM, 1, 1));
        POISON = new EffectAlchemyPotion(WizardsReborn.MOD_ID+":poison", new MobEffectInstance(MobEffects.WATER_BREATHING, 1200, 1));
        REGENERATION = new EffectAlchemyPotion(WizardsReborn.MOD_ID+":regeneration", new MobEffectInstance(MobEffects.REGENERATION, 3600, 1));
        STRENGTH = new EffectAlchemyPotion(WizardsReborn.MOD_ID+":strength", new MobEffectInstance(MobEffects.DAMAGE_BOOST, 7200, 1));
        WEAKNESS = new EffectAlchemyPotion(WizardsReborn.MOD_ID+":weakness", new MobEffectInstance(MobEffects.WEAKNESS, 3600, 1));
        LUCK = new EffectAlchemyPotion(WizardsReborn.MOD_ID+":luck", new MobEffectInstance(MobEffects.LUCK, 12000, 1));
        SLOW_FALLING = new EffectAlchemyPotion(WizardsReborn.MOD_ID+":slow_falling", new MobEffectInstance(MobEffects.SLOW_FALLING, 12000));

        AlchemyPotions.register(EMPTY);
        AlchemyPotions.register(COMBINED);
        AlchemyPotions.register(WATER);
        AlchemyPotions.register(MUNDANE);
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
