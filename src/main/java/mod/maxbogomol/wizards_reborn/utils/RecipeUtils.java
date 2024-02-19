package mod.maxbogomol.wizards_reborn.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotion;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotions;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantments;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRituals;
import mod.maxbogomol.wizards_reborn.common.alchemypotion.RegisterAlchemyPotions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeUtils {
    public static FluidStack deserializeFluidStack(JsonObject json) {
        String fluidName = GsonHelper.getAsString(json, "fluid");
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidName));
        if (fluid == null || fluid == Fluids.EMPTY) {
            throw new JsonSyntaxException("Unknown fluid " + fluidName);
        }
        int amount = GsonHelper.getAsInt(json, "amount");
        return new FluidStack(fluid, amount);
    }

    public static MobEffectInstance deserializeMobEffect(JsonObject json) {
        String effectName = GsonHelper.getAsString(json, "effect");
        MobEffect mobEffect = ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(effectName));
        if (mobEffect == null) {
            throw new JsonSyntaxException("Unknown effect " + effectName);
        }
        int duration = GsonHelper.getAsInt(json, "duration");
        int amplifier = GsonHelper.getAsInt(json, "amplifier");
        return new MobEffectInstance(mobEffect, duration, amplifier);
    }

    public static MobEffectInstance mobEffectFromNetwork(FriendlyByteBuf buffer) {
        if (buffer.readBoolean()) {
            MobEffect mobEffect = buffer.readRegistryId();
            int duration = buffer.readInt();
            int amplifier = buffer.readInt();
            return new MobEffectInstance(mobEffect, duration, amplifier);
        }
        return null;
    }

    public static void mobEffectToNetwork(MobEffectInstance effect, FriendlyByteBuf buffer) {
        if (effect == null) {
            buffer.writeBoolean(false);
        } else {
            buffer.writeBoolean(true);
            buffer.writeRegistryId(ForgeRegistries.MOB_EFFECTS, effect.getEffect());
            buffer.writeInt(effect.getDuration());
            buffer.writeInt(effect.getAmplifier());
        }

    }

    public static Enchantment deserializeEnchantment(JsonObject json) {
        String enchantmentName = GsonHelper.getAsString(json, "enchantment");
        Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(enchantmentName));
        if (enchantment == null) {
            throw new JsonSyntaxException("Unknown enchantment " + enchantmentName);
        }
        return enchantment;
    }

    public static Enchantment enchantmentFromNetwork(FriendlyByteBuf buffer) {
        return !buffer.readBoolean() ? null : buffer.readRegistryId();
    }

    public static void enchantmentToNetwork(Enchantment enchantment, FriendlyByteBuf buffer) {
        if (enchantment == null) {
            buffer.writeBoolean(false);
        } else {
            buffer.writeBoolean(true);
            buffer.writeRegistryId(ForgeRegistries.ENCHANTMENTS, enchantment);
        }
    }

    public static ArcaneEnchantment deserializeArcaneEnchantment(JsonObject json) {
        String enchantmentName = GsonHelper.getAsString(json, "arcane_enchantment");
        ArcaneEnchantment enchantment = ArcaneEnchantments.getArcaneEnchantment(enchantmentName);
        if (enchantment == null) {
            throw new JsonSyntaxException("Unknown arcane enchantment " + enchantmentName);
        }
        return enchantment;
    }

    public static ArcaneEnchantment  arcaneEnchantmentFromNetwork(FriendlyByteBuf buffer) {
        return !buffer.readBoolean() ? null : ArcaneEnchantments.getArcaneEnchantment(buffer.readComponent().getString());
    }

    public static void arcaneEnchantmentToNetwork(ArcaneEnchantment enchantment, FriendlyByteBuf buffer) {
        if (enchantment == null) {
            buffer.writeBoolean(false);
        } else {
            buffer.writeBoolean(true);
            buffer.writeComponent(Component.literal(enchantment.getId()));
        }
    }

    public static AlchemyPotion deserializeAlchemyPotion(JsonObject json) {
        String potionName = GsonHelper.getAsString(json, "alchemy_potion");
        AlchemyPotion potion = AlchemyPotions.getAlchemyPotion(potionName);
        if (potion == null) {
            throw new JsonSyntaxException("Unknown alchemy potion " + potionName);
        }
        return potion;
    }

    public static AlchemyPotion alchemyPotionFromNetwork(FriendlyByteBuf buffer) {
        return !buffer.readBoolean() ? RegisterAlchemyPotions.EMPTY : AlchemyPotions.getAlchemyPotion(buffer.readComponent().getString());
    }

    public static void alchemyPotionToNetwork(AlchemyPotion potion, FriendlyByteBuf buffer) {
        if (potion == null) {
            buffer.writeBoolean(false);
        } else {
            buffer.writeBoolean(true);
            buffer.writeComponent(Component.literal(potion.getId()));
        }
    }

    public static CrystalRitual deserializeCrystalRitual(JsonObject json) {
        String ritualName = GsonHelper.getAsString(json, "crystal_ritual");
        CrystalRitual ritual = CrystalRituals.getCrystalRitual(ritualName);
        if (ritual == null) {
            throw new JsonSyntaxException("Unknown crystal ritual " + ritualName);
        }
        return ritual;
    }

    public static CrystalRitual crystalRitualFromNetwork(FriendlyByteBuf buffer) {
        return !buffer.readBoolean() ? WizardsReborn.EMPTY_CRYSTAL_RITUAL : CrystalRituals.getCrystalRitual(buffer.readComponent().getString());
    }

    public static void crystalRitualToNetwork(CrystalRitual ritual, FriendlyByteBuf buffer) {
        if (ritual == null) {
            buffer.writeBoolean(false);
        } else {
            buffer.writeBoolean(true);
            buffer.writeComponent(Component.literal(ritual.getId()));
        }
    }
}
