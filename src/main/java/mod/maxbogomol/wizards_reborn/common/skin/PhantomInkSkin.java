package mod.maxbogomol.wizards_reborn.common.skin;

import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import net.minecraft.world.entity.EquipmentSlot;

import java.awt.*;

public class PhantomInkSkin extends ItemSkin {
    public PhantomInkSkin(String id, Color color) {
        super(id, color);
    }

    @Override
    public void setupSkinEntries() {
        addSkinEntry(new ArcaneArmorClassSkinEntry(ArcaneArmorItem.class,
                WizardsReborn.MOD_ID+":textures/models/armor/skin/empty.png")
                .addArmorSkin(EquipmentSlot.HEAD, null)
                .addArmorSkin(EquipmentSlot.CHEST, null)
                .addArmorSkin(EquipmentSlot.LEGS, null)
                .addArmorSkin(EquipmentSlot.FEET, null));
    }
}
