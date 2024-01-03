package mod.maxbogomol.wizards_reborn.api.alchemy;

import mod.maxbogomol.wizards_reborn.common.alchemypotion.FluidAlchemyPotion;
import mod.maxbogomol.wizards_reborn.common.alchemypotion.RegisterAlchemyPotions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

public class AlchemyPotionUtils {
    public static AlchemyPotion getPotion(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (nbt.contains("alchemyPotion")) {
            AlchemyPotion potion = AlchemyPotions.getAlchemyPotion(nbt.getString("alchemyPotion"));
            if (potion != null) {
                return potion;
            }
        }

        return RegisterAlchemyPotions.EMPTY;
    }

    public static void setPotion(ItemStack stack, AlchemyPotion potion) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putString("alchemyPotion", potion.getId());
    }

    public static boolean isEmpty(AlchemyPotion potion) {
        return potion == RegisterAlchemyPotions.EMPTY;
    }

    public static AlchemyPotion getPotionFluid(Fluid fluid) {
        AlchemyPotion potionFluid = RegisterAlchemyPotions.EMPTY;

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
