package mod.maxbogomol.wizards_reborn.common.arcaneenchantment;

import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentType;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.IArcaneItem;
import net.minecraft.world.item.ItemStack;

import java.awt.*;

public class LifeRootsArcaneEnchantment extends ArcaneEnchantment {

    public LifeRootsArcaneEnchantment(String id, int maxLevel) {
        super(id, maxLevel);
    }

    @Override
    public Color getColor() {
        return new Color(164, 201, 167);
    }

    @Override
    public boolean canEnchantItem(ItemStack stack) {
        if (stack.getItem() instanceof IArcaneItem item) {
            return item.getArcaneEnchantmentTypes().contains(ArcaneEnchantmentType.WOODEN);
        }
        return false;
    }
}
