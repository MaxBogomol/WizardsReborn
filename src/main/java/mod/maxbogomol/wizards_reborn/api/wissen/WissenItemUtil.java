package mod.maxbogomol.wizards_reborn.api.wissen;

import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

public class WissenItemUtil {

    public static int getWissen(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        if (!containsWissen(stack)) return 0;
        return nbt.getInt("wissen");
    }

    public static void setWissen(ItemStack stack, int wissen) {
        CompoundTag nbt = stack.getTag();
        nbt.putInt("wissen", wissen);
    }

    public static void addWissen(ItemStack stack, int wissen, int maxWissen) {
        CompoundTag nbt = stack.getTag();
        if (!containsWissen(stack)) return;
        nbt.putInt("wissen", nbt.getInt("wissen") + wissen);
        if (maxWissen < nbt.getInt("wissen")) {
            nbt.putInt("wissen", maxWissen);
        }
    }

    public static void removeWissen(ItemStack stack, int wissen) {
        CompoundTag nbt = stack.getTag();
        if (!containsWissen(stack)) return;
        nbt.putInt("wissen", nbt.getInt("wissen") - wissen);
        if (nbt.getInt("wissen") < 0) {
            nbt.putInt("wissen", 0);
        }
    }

    public static int getAddWissenRemain(ItemStack stack, int wissen, int maxWissen) {
        int wissen_remain = 0;
        CompoundTag nbt = stack.getTag();
        if (!containsWissen(stack)) return 0;
        if (maxWissen < nbt.getInt("wissen") + wissen) {
            wissen_remain = (nbt.getInt("wissen") + wissen) - maxWissen;
        }
        return wissen_remain;
    }

    public static int getRemoveWissenRemain(ItemStack stack, int wissen) {
        int wissen_remain = 0;
        CompoundTag nbt = stack.getTag();
        if (!containsWissen(stack)) return 0;
        if (0 < nbt.getInt("wissen") - wissen) {
            wissen_remain = -(nbt.getInt("wissen") - wissen);
        }
        return wissen_remain;
    }

    public static boolean canAddWissen(ItemStack stack, int wissen, int maxWissen) {
        CompoundTag nbt = stack.getTag();
        if (!containsWissen(stack))return false;
        return (maxWissen >= nbt.getInt("wissen") + wissen);
    }

    public static boolean canRemoveWissen(ItemStack stack, int wissen) {
        CompoundTag nbt = stack.getTag();
        if (!containsWissen(stack)) return false;
        return (0 <= nbt.getInt("wissen") - wissen);
    }

    public static void existWissen(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (!nbt.contains("wissen")) {
            nbt.putInt("wissen", 0);
        }
    }

    public static boolean containsWissen(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        if (nbt == null) return false;
        return nbt.contains("wissen");
    }
}
