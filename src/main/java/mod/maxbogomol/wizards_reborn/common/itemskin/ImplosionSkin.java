package mod.maxbogomol.wizards_reborn.common.itemskin;

import mod.maxbogomol.fluffy_fur.common.itemskin.ItemClassSkinEntry;
import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.*;
import mod.maxbogomol.wizards_reborn.integration.common.farmersdelight.FarmersDelightIntegration;

import java.awt.*;

public class ImplosionSkin extends ItemSkin {
    public ImplosionSkin(String id, Color color) {
        super(id, color);
    }

    @Override
    public void setupSkinEntries() {
        addSkinEntry(new ItemClassSkinEntry(ArcaneSwordItem.class, WizardsReborn.MOD_ID+":implosion_sword"));
        addSkinEntry(new ItemClassSkinEntry(ArcanePickaxeItem.class, WizardsReborn.MOD_ID+":implosion_pickaxe"));
        addSkinEntry(new ItemClassSkinEntry(ArcaneAxeItem.class, WizardsReborn.MOD_ID+":implosion_axe"));
        addSkinEntry(new ItemClassSkinEntry(ArcaneShovelItem.class, WizardsReborn.MOD_ID+":implosion_shovel"));
        addSkinEntry(new ItemClassSkinEntry(ArcaneHoeItem.class, WizardsReborn.MOD_ID+":implosion_hoe"));
        addSkinEntry(new ItemClassSkinEntry(ArcaneScytheItem.class, WizardsReborn.MOD_ID+":implosion_scythe"));
        addSkinEntry(new ItemClassSkinEntry(ArcaneWandItem.class, WizardsReborn.MOD_ID+":skin/implosion_arcane_wand"));
        addSkinEntry(new ItemClassSkinEntry(WissenWandItem.class, WizardsReborn.MOD_ID+":implosion_wissen_wand"));
        if (FarmersDelightIntegration.isLoaded()) {
            FarmersDelightIntegration.LoadedOnly.addKnifeSkin(this, WizardsReborn.MOD_ID+":implosion_knife");
        }
    }
}
