package mod.maxbogomol.wizards_reborn.common.skin;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.api.skin.Skin;
import mod.maxbogomol.wizards_reborn.client.model.armor.ArmorModel;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;

public class SummerLoveSkin extends Skin {
    public SummerLoveSkin(String id, Color color) {
        super(id, color);
    }

    @Override
    public void setupSkinEntries() {
        addSkinEntry(new MagnificentMaidArmorSkinEntry(ArcaneArmorItem.class,
                WizardsReborn.MOD_ID+":textures/models/armor/skin/summer_love")
                .addArmorSkin(EquipmentSlot.HEAD, WizardsReborn.MOD_ID + ":summer_love_flower")
                .addArmorSkin(EquipmentSlot.CHEST, WizardsReborn.MOD_ID + ":summer_love_dress")
                .addArmorSkin(EquipmentSlot.FEET, WizardsReborn.MOD_ID + ":summer_love_boots"));
        addSkinEntry(new ItemClassSkinEntry(ArcaneWandItem.class, WizardsReborn.MOD_ID+":skin/summer_love_arcane_wand"));
        addSkinEntry(new ItemClassSkinEntry(WissenWandItem.class, WizardsReborn.MOD_ID+":summer_love_wissen_wand"));
    }

    public static class MagnificentMaidArmorSkinEntry extends ArmorClassSkinEntry {
        public MagnificentMaidArmorSkinEntry(Class item, String skin) {
            super(item, skin);
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public ArmorModel getArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default) {
            if (!isDefaultModel(entity)) {
                return WizardsRebornClient.MAGNIFICENT_MAID_SLIM_ARMOR_MODEL;
            }
            return WizardsRebornClient.MAGNIFICENT_MAID_ARMOR_MODEL;
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
