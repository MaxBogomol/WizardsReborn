package mod.maxbogomol.wizards_reborn.util;

import net.minecraft.network.chat.Component;
import net.minecraftforge.fluids.FluidStack;

public class NumericalUtil {
    public static Component getWissenName(int amount, int maxAmount) {
        return getName(getWissenName(), amount, maxAmount);
    }

    public static Component getCooldownName(float amount) {
        return getNamePercent(getCooldownName(), amount);
    }

    public static Component getLightName(int amount, int maxAmount) {
        return getName(getLightName(), amount, maxAmount);
    }

    public static Component getExperienceName(int amount, int maxAmount) {
        return getName(getExperienceName(), amount, maxAmount);
    }

    public static Component getHeatName(int amount, int maxAmount) {
        return getName(getHeatName(), amount, maxAmount);
    }

    public static Component getFluidName(FluidStack fluid, int maxAmount) {
        if (fluid.isEmpty()) {
            return Component.translatable("gui.wizards_reborn.empty");
        }
        return getName(fluid.getDisplayName(), fluid.getAmount(), maxAmount);
    }

    public static Component getSteamName(int amount, int maxAmount) {
        return getName(getSteamName(), amount, maxAmount);
    }

    public static Component getWissenName() {
        return Component.translatable("gui.wizards_reborn.wissen");
    }

    public static Component getCooldownName() {
        return Component.translatable("gui.wizards_reborn.cooldown");
    }

    public static Component getLightName() {
        return Component.translatable("gui.wizards_reborn.light");
    }

    public static Component getExperienceName() {
        return Component.translatable("gui.wizards_reborn.experience");
    }

    public static Component getHeatName() {
        return Component.translatable("gui.wizards_reborn.heat");
    }

    public static Component getFluidName(FluidStack fluid) {
        return fluid.getDisplayName();
    }

    public static Component getSteamName() {
        return Component.translatable("gui.wizards_reborn.steam");
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
