package mod.maxbogomol.wizards_reborn.common.itemskin;

import mod.maxbogomol.fluffy_fur.client.model.armor.ArmorModel;
import mod.maxbogomol.fluffy_fur.common.itemskin.ItemClassSkinEntry;
import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;

public class MagnificentMaidSkin extends ItemSkin {
    public MagnificentMaidSkin(String id, Color color) {
        super(id, color);
    }

    @Override
    public void setupSkinEntries() {
        addSkinEntry(new MagnificentMaidArmorSkinEntry(ArcaneArmorItem.class,
                WizardsReborn.MOD_ID+":textures/models/armor/skin/magnificent_maid")
                .addArmorSkin(EquipmentSlot.HEAD, WizardsReborn.MOD_ID + ":magnificent_maid_headwear")
                .addArmorSkin(EquipmentSlot.CHEST, WizardsReborn.MOD_ID + ":magnificent_maid_suit")
                .addArmorSkin(EquipmentSlot.LEGS, WizardsReborn.MOD_ID + ":magnificent_maid_stockings")
                .addArmorSkin(EquipmentSlot.FEET, WizardsReborn.MOD_ID + ":magnificent_maid_boots"));
        addSkinEntry(new ItemClassSkinEntry(ArcaneWandItem.class, WizardsReborn.MOD_ID+":skin/magnificent_maid_arcane_wand"));
        addSkinEntry(new ItemClassSkinEntry(WissenWandItem.class, WizardsReborn.MOD_ID+":magnificent_maid_wissen_wand"));
    }

    public static class MagnificentMaidArmorSkinEntry extends ArcaneArmorClassSkinEntry {

        public MagnificentMaidArmorSkinEntry(Class item, String skin) {
            super(item, skin);
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public ArmorModel getArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default) {
            if (!isDefaultModel(entity)) {
                return WizardsRebornModels.MAGNIFICENT_MAID_SLIM_ARMOR;
            }
            return WizardsRebornModels.MAGNIFICENT_MAID_ARMOR;
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            if (!isDefaultModel(entity)) {
                return skin + "_slim.png";
            }
            return skin + ".png";
        }
    }
}
