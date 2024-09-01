package mod.maxbogomol.wizards_reborn.api.alchemy;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import mod.maxbogomol.wizards_reborn.common.alchemypotion.FluidAlchemyPotion;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornAlchemyPotions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

public class AlchemyPotionUtil {

    public static AlchemyPotion deserializeAlchemyPotion(JsonObject json) {
        String potionName = GsonHelper.getAsString(json, "alchemy_potion");
        AlchemyPotion potion = AlchemyPotions.getAlchemyPotion(potionName);
        if (potion == null) {
            throw new JsonSyntaxException("Unknown alchemy potion " + potionName);
        }
        return potion;
    }

    public static AlchemyPotion alchemyPotionFromNetwork(FriendlyByteBuf buffer) {
        return !buffer.readBoolean() ? WizardsRebornAlchemyPotions.EMPTY : AlchemyPotions.getAlchemyPotion(buffer.readComponent().getString());
    }

    public static void alchemyPotionToNetwork(AlchemyPotion potion, FriendlyByteBuf buffer) {
        if (potion == null) {
            buffer.writeBoolean(false);
        } else {
            buffer.writeBoolean(true);
            buffer.writeComponent(Component.literal(potion.getId()));
        }
    }

    public static AlchemyPotion getPotion(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (nbt.contains("alchemyPotion")) {
            AlchemyPotion potion = AlchemyPotions.getAlchemyPotion(nbt.getString("alchemyPotion"));
            if (potion != null) {
                return potion;
            }
        }

        return WizardsRebornAlchemyPotions.EMPTY;
    }

    public static void setPotion(ItemStack stack, AlchemyPotion potion) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putString("alchemyPotion", potion.getId());
    }

    public static boolean isEmpty(AlchemyPotion potion) {
        return potion == WizardsRebornAlchemyPotions.EMPTY;
    }

    public static AlchemyPotion getPotionFluid(Fluid fluid) {
        AlchemyPotion potionFluid = WizardsRebornAlchemyPotions.EMPTY;

        for (AlchemyPotion potion : AlchemyPotions.getAlchemyPotions()) {
            if (potion instanceof FluidAlchemyPotion fluidPotion) {
                if (fluidPotion.getFluid() == fluid) {
                    potionFluid = fluidPotion;
                }
            }
        }

        return potionFluid;
    }
}
