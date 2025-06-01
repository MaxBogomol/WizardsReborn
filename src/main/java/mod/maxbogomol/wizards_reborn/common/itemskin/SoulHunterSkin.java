package mod.maxbogomol.wizards_reborn.common.itemskin;

import mod.maxbogomol.fluffy_fur.client.model.armor.ArmorModel;
import mod.maxbogomol.fluffy_fur.common.itemskin.ItemClassSkinEntry;
import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.InventorWizardArmorItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneScytheItem;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;

public class SoulHunterSkin extends ItemSkin {

    public SoulHunterSkin(String id, Color color) {
        super(id, color);
    }

    @Override
    public void setupSkinEntries() {
        addSkinEntry(new SoulHunterArmorSkinEntry(InventorWizardArmorItem.class,
                WizardsReborn.MOD_ID+":textures/models/armor/skin/soul_hunter.png")
                .addArmorSkin(EquipmentSlot.HEAD, WizardsReborn.MOD_ID + ":soul_hunter_hood")
                .addArmorSkin(EquipmentSlot.CHEST, WizardsReborn.MOD_ID + ":soul_hunter_costume")
                .addArmorSkin(EquipmentSlot.LEGS, WizardsReborn.MOD_ID + ":soul_hunter_trousers")
                .addArmorSkin(EquipmentSlot.FEET, WizardsReborn.MOD_ID + ":soul_hunter_boots"));
        addSkinEntry(new ItemClassSkinEntry(ArcaneScytheItem.class, WizardsReborn.MOD_ID+":soul_hunter_scythe"));
        addSkinEntry(new ItemClassSkinEntry(ArcaneWandItem.class, WizardsReborn.MOD_ID+":skin/soul_hunter_arcane_wand"));
        addSkinEntry(new ItemClassSkinEntry(WissenWandItem.class, WizardsReborn.MOD_ID+":soul_hunter_wissen_wand"));

        addApplyingItem(Component.translatable("item_skin.wizards_reborn.arcane_magic_armor"));
        addApplyingItem(Component.translatable("item_skin.wizards_reborn.arcane_scythes"));
        addApplyingItem(Component.translatable("item_skin.wizards_reborn.arcane_wand"));
        addApplyingItem(Component.translatable("item_skin.wizards_reborn.wissen_wand"));
    }

    public static class SoulHunterArmorSkinEntry extends ArcaneArmorClassSkinEntry {

        public SoulHunterArmorSkinEntry(Class item, String skin) {
            super(item, skin);
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public ArmorModel getArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default) {
            return WizardsRebornModels.SOUL_HUNTER_ARMOR;
        }
    }
}
