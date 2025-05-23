package mod.maxbogomol.wizards_reborn.common.arcaneenchantment;

import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentTypes;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.IArcaneItem;
import net.minecraft.world.item.ItemStack;

import java.awt.*;

public class PropellingArcaneEnchantment extends ArcaneEnchantment {

    public PropellingArcaneEnchantment(String id, int maxLevel) {
        super(id, maxLevel);
    }

    @Override
    public Color getColor() {
        return new Color(255, 219, 0);
    }

    @Override
    public boolean canEnchantItem(ItemStack stack) {
        if (stack.getItem() instanceof IArcaneItem item) {
            return item.getArcaneEnchantmentTypes().contains(ArcaneEnchantmentTypes.SHEARS) || item.getArcaneEnchantmentTypes().contains(ArcaneEnchantmentTypes.SCYTHE);
        }
        return false;
    }
}
