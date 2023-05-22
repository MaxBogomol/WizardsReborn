package mod.maxbogomol.wizards_reborn.api.wissen;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class WissenItemUtils {
    public static int getWissen(ItemStack stack) {
        CompoundNBT nbt = stack.getTag();
        return nbt.getInt("wissen");
    }

    public static void setWissen(ItemStack stack, int wissen) {
        CompoundNBT nbt = stack.getTag();
        nbt.putInt("wissen", wissen);
    }

    public static void addWissen(ItemStack stack, int wissen, int max_wissen) {
        CompoundNBT nbt = stack.getTag();
        nbt.putInt("wissen", nbt.getInt("wissen") + wissen);
        if (max_wissen < nbt.getInt("wissen")) {
            nbt.putInt("wissen", max_wissen);
        }
    }

    public static int addWissenRemain(ItemStack stack, int wissen) {
        int wissen_remain = 0;
        CompoundNBT nbt = stack.getTag();
        int max_wissen = nbt.getInt("max_wissen");
        nbt.putInt("wissen", nbt.getInt("wissen") + wissen);
        if (max_wissen < nbt.getInt("wissen")) {
            wissen_remain = nbt.getInt("wissen") - max_wissen;
            nbt.putInt("wissen", max_wissen);
        }
        return wissen_remain;
    }

    public static void removeWissen(ItemStack stack, int wissen) {
        CompoundNBT nbt = stack.getTag();
        nbt.putInt("wissen", nbt.getInt("wissen") - wissen);
        if (nbt.getInt("wissen") < 0) {
            nbt.putInt("wissen", 0);
        }
    }

    public static int getAddWissenRemain(ItemStack stack, int wissen, int max_wissen) {
        int wissen_remain = 0;
        CompoundNBT nbt = stack.getTag();
        if (max_wissen < nbt.getInt("wissen") + wissen) {
            wissen_remain = (nbt.getInt("wissen") + wissen) - max_wissen;
        }
        return wissen_remain;
    }

    public static int getRemoveWissenRemain(ItemStack stack, int wissen) {
        int wissen_remain = 0;
        CompoundNBT nbt = stack.getTag();
        if (0 < nbt.getInt("wissen") - wissen) {
            wissen_remain = -(nbt.getInt("wissen") - wissen);
        }
        return wissen_remain;
    }

    public static boolean canAddWissen(ItemStack stack, int wissen, int max_wissen) {
        CompoundNBT nbt = stack.getTag();
        return (max_wissen >= nbt.getInt("wissen") + wissen);
    }

    public static boolean canRemoveWissen(ItemStack stack, int wissen) {
        CompoundNBT nbt = stack.getTag();
        return (0 <= nbt.getInt("wissen") - wissen);
    }
}
