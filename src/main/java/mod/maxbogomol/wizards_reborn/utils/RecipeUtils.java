package mod.maxbogomol.wizards_reborn.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.network.FriendlyByteBuf;
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
}
