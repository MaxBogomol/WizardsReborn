package mod.maxbogomol.wizards_reborn.common.skin;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.skin.Skin;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;

public class ImplosionSkin extends Skin {
    public ImplosionSkin(String id, Color color) {
        super(id, color);
    }

    @Override
    public boolean canApplyOnItem(ItemStack itemStack) {
        return ((itemStack.getItem() instanceof ArcaneSwordItem) || (itemStack.getItem() instanceof ArcanePickaxeItem) ||(itemStack.getItem() instanceof ArcaneAxeItem)
                || (itemStack.getItem() instanceof ArcaneShovelItem) || (itemStack.getItem() instanceof ArcaneHoeItem) ||(itemStack.getItem() instanceof ArcaneScytheItem)
                || (itemStack.getItem() instanceof ArcaneWandItem));
    }

    @OnlyIn(Dist.CLIENT)
    public String getItemModelName(ItemStack stack) {
        if (stack.getItem() instanceof ArcaneSwordItem) {
            return WizardsReborn.MOD_ID+":implosion_sword";
        }
        if (stack.getItem() instanceof ArcanePickaxeItem) {
            return WizardsReborn.MOD_ID+":implosion_pickaxe";
        }
        if (stack.getItem() instanceof ArcaneAxeItem) {
            return WizardsReborn.MOD_ID+":implosion_axe";
        }
        if (stack.getItem() instanceof ArcaneShovelItem) {
            return WizardsReborn.MOD_ID+":implosion_shovel";
        }
        if (stack.getItem() instanceof ArcaneHoeItem) {
            return WizardsReborn.MOD_ID+":implosion_hoe";
        }
        if (stack.getItem() instanceof ArcaneScytheItem) {
            return WizardsReborn.MOD_ID+":implosion_scythe";
        }
        if (stack.getItem() instanceof ArcaneWandItem) {
            return WizardsReborn.MOD_ID+":skin/implosion_arcane_wand";
        }
        return WizardsReborn.MOD_ID+":implosion_sword";
    }
}
