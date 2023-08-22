package mod.maxbogomol.wizards_reborn.api.wissen;

import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

public class WissenItemUtils {
    public static int getWissen(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        return nbt.getInt("wissen");
    }

    public static void setWissen(ItemStack stack, int wissen) {
        CompoundTag nbt = stack.getTag();
        nbt.putInt("wissen", wissen);
    }

    public static void addWissen(ItemStack stack, int wissen, int max_wissen) {
        CompoundTag nbt = stack.getTag();
        nbt.putInt("wissen", nbt.getInt("wissen") + wissen);
        if (max_wissen < nbt.getInt("wissen")) {
            nbt.putInt("wissen", max_wissen);
        }
    }

    public static int addWissenRemain(ItemStack stack, int wissen) {
        int wissen_remain = 0;
        CompoundTag nbt = stack.getTag();
        int max_wissen = nbt.getInt("max_wissen");
        nbt.putInt("wissen", nbt.getInt("wissen") + wissen);
        if (max_wissen < nbt.getInt("wissen")) {
            wissen_remain = nbt.getInt("wissen") - max_wissen;
            nbt.putInt("wissen", max_wissen);
        }
        return wissen_remain;
    }

    public static void removeWissen(ItemStack stack, int wissen) {
        CompoundTag nbt = stack.getTag();
        nbt.putInt("wissen", nbt.getInt("wissen") - wissen);
        if (nbt.getInt("wissen") < 0) {
            nbt.putInt("wissen", 0);
        }
    }

    public static int getAddWissenRemain(ItemStack stack, int wissen, int max_wissen) {
        int wissen_remain = 0;
        CompoundTag nbt = stack.getTag();
        if (max_wissen < nbt.getInt("wissen") + wissen) {
            wissen_remain = (nbt.getInt("wissen") + wissen) - max_wissen;
        }
        return wissen_remain;
    }

    public static int getRemoveWissenRemain(ItemStack stack, int wissen) {
        int wissen_remain = 0;
        CompoundTag nbt = stack.getTag();
        if (0 < nbt.getInt("wissen") - wissen) {
            wissen_remain = -(nbt.getInt("wissen") - wissen);
        }
        return wissen_remain;
    }

    public static boolean canAddWissen(ItemStack stack, int wissen, int max_wissen) {
        CompoundTag nbt = stack.getTag();
        return (max_wissen >= nbt.getInt("wissen") + wissen);
    }

    public static boolean canRemoveWissen(ItemStack stack, int wissen) {
        CompoundTag nbt = stack.getTag();
        return (0 <= nbt.getInt("wissen") - wissen);
    }

    public static void existWissen(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (!nbt.contains("wissen")) {
            nbt.putInt("wissen", 0);
        }
    }
}
