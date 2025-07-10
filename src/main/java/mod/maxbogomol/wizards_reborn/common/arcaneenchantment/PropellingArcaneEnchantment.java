package mod.maxbogomol.wizards_reborn.common.arcaneenchantment;

import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentTypes;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtil;
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
        if (ArcaneEnchantmentUtil.isArcaneItem(stack)) {
            return ArcaneEnchantmentUtil.getArcaneEnchantmentTypes(stack).contains(ArcaneEnchantmentTypes.SHEARS) || ArcaneEnchantmentUtil.getArcaneEnchantmentTypes(stack).contains(ArcaneEnchantmentTypes.SCYTHE);
        }
        return false;
    }
}
