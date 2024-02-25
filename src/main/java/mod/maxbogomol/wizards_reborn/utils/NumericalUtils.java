package mod.maxbogomol.wizards_reborn.utils;

import net.minecraft.network.chat.Component;
import net.minecraftforge.fluids.FluidStack;

public class NumericalUtils {
    public static Component getWissenName(int amount, int maxAmount) {
        return getName(Component.translatable("gui.wizards_reborn.wissen"), amount, maxAmount);
    }

    public static Component getCooldownName(float amount) {
        return getNamePercent(Component.translatable("gui.wizards_reborn.cooldown"), amount);
    }

    public static Component getLightName(int amount, int maxAmount) {
        return getName(getLightName(), amount, maxAmount);
    }

    public static Component getLightName() {
        return Component.translatable("gui.wizards_reborn.light");
    }

    public static Component getExperienceName(int amount, int maxAmount) {
        return getName(Component.translatable("gui.wizards_reborn.experience"), amount, maxAmount);
    }

    public static Component getHeatName(int amount, int maxAmount) {
        return getName(Component.translatable("gui.wizards_reborn.heat"), amount, maxAmount);
    }

    public static Component getFluidName(FluidStack fluid, int maxAmount) {
        return getName(fluid.getDisplayName(), fluid.getAmount(), maxAmount);
    }

    public static Component getSteamName(int amount, int maxAmount) {
        return getName(Component.translatable("gui.wizards_reborn.steam"), amount, maxAmount);
    }

    public static Component getName(Component name, int amount, int maxAmount) {
        return Component.literal(name.getString())
                .append(Component.literal(" "))
                .append(Component.literal(String.valueOf(amount)).
                        append(Component.literal("/")).
                        append(Component.literal(String.valueOf(maxAmount))));
    }

    public static Component getNamePercent(Component name, float amount) {
        int number = 0;
        if (amount > 0) {
            number = Math.round((1f / amount) * 100f);
        }
        return Component.literal(name.getString())
                .append(Component.literal(" "))
                .append(Component.literal(String.valueOf(number)).
                        append(Component.literal("%")));
    }
}
