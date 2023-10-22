package mod.maxbogomol.wizards_reborn.api.wissen;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;

public class WissenUtils {
    public static int getAddWissenRemain(int current_wissen, int wissen, int max_wissen) {
        int wissen_remain = 0;
        if (max_wissen < (current_wissen + wissen)) {
            wissen_remain = (current_wissen + wissen) - max_wissen;
        }
        return wissen_remain;
    }

    public static int getRemoveWissenRemain(int current_wissen, int wissen) {
        int wissen_remain = 0;
        if (0 > (current_wissen - wissen)) {
            wissen_remain = -(current_wissen - wissen);
        }
        return wissen_remain;
    }

    public static boolean canAddWissen(int current_wissen, int wissen, int max_wissen) {
        return (max_wissen >= (current_wissen + wissen));
    }

    public static boolean canRemoveWissen(int current_wissen, int wissen) {
        return (0 <= (current_wissen - wissen));
    }

    public static float getWissenCostModifierWithSale(Player player) {
        AttributeInstance attr = player.getAttribute(WizardsReborn.WISSEN_SALE.get());
        return (float) (attr.getValue() / 100d);
    }
}