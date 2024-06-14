package mod.maxbogomol.wizards_reborn.common.skin;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.api.skin.Skin;
import mod.maxbogomol.wizards_reborn.client.model.armor.ArmorModel;
import mod.maxbogomol.wizards_reborn.common.item.equipment.InventorWizardArmorItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;

public class TopHatSkin extends Skin {
    public TopHatSkin(String id, Color color) {
        super(id, color);
    }

    @Override
    public void setupSkinEntries() {
        addSkinEntry(new TopHatArmorSkinEntry(InventorWizardArmorItem.class,
                WizardsReborn.MOD_ID+":textures/models/armor/skin/top_hat.png")
                .addArmorSkin(EquipmentSlot.HEAD, WizardsReborn.MOD_ID + ":top_hat"));
    }

    public static class TopHatArmorSkinEntry extends ArmorClassSkinEntry {
        public TopHatArmorSkinEntry(Class item, String skin) {
            super(item, skin);
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public ArmorModel getArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default) {
            return WizardsRebornClient.TOP_HAT_ARMOR_MODEL;
        }
    }
}
