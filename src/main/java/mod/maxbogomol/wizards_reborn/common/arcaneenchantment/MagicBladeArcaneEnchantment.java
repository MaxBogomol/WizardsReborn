package mod.maxbogomol.wizards_reborn.common.arcaneenchantment;

import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneAxeItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneScytheItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneSwordItem;
import net.minecraft.world.item.ItemStack;

import java.awt.*;

public class MagicBladeArcaneEnchantment extends ArcaneEnchantment {

    public MagicBladeArcaneEnchantment(String id, int maxLevel) {
        super(id, maxLevel);
    }

    public Color getColor() {
        return new Color(110, 78, 169);
    }


    public boolean canEnchantItem(ItemStack stack) {
        return ((stack.getItem() instanceof ArcaneSwordItem) || (stack.getItem() instanceof ArcaneAxeItem) || (stack.getItem() instanceof ArcaneScytheItem));
    }
}
