package mod.maxbogomol.wizards_reborn.common.itemskin;

import mod.maxbogomol.fluffy_fur.client.model.armor.ArmorModel;
import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.item.equipment.InventorWizardArmorItem;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;

public class TopHatSkin extends ItemSkin {

    public TopHatSkin(String id, Color color) {
        super(id, color);
    }

    @Override
    public void setupSkinEntries() {
        addSkinEntry(new TopHatArmorSkinEntry(InventorWizardArmorItem.class,
                WizardsReborn.MOD_ID+":textures/models/armor/skin/top_hat.png")
                .addArmorSkin(EquipmentSlot.HEAD, WizardsReborn.MOD_ID + ":top_hat"));

        addApplyingItem(Component.translatable("item_skin.wizards_reborn.arcane_magic_hats"));
    }

    public static class TopHatArmorSkinEntry extends ArcaneArmorClassSkinEntry {

        public TopHatArmorSkinEntry(Class item, String skin) {
            super(item, skin);
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public ArmorModel getArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default) {
            return WizardsRebornModels.TOP_HAT_ARMOR;
        }
    }
}
