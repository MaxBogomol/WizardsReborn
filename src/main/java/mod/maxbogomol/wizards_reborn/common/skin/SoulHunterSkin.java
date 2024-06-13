package mod.maxbogomol.wizards_reborn.common.skin;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.api.skin.Skin;
import mod.maxbogomol.wizards_reborn.client.model.armor.ArmorModel;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.InventorWizardArmorItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneScytheItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;

public class SoulHunterSkin extends Skin {
    public SoulHunterSkin(String id, Color color) {
        super(id, color);
    }

    @Override
    public boolean canApplyOnItem(ItemStack itemStack) {
        return ((itemStack.getItem() instanceof InventorWizardArmorItem) || (itemStack.getItem() instanceof ArcaneScytheItem) || (itemStack.getItem() instanceof ArcaneWandItem));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ArmorModel getArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default) {
        return WizardsRebornClient.SOUL_HUNTER_ARMOR_MODEL;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return WizardsReborn.MOD_ID + ":textures/models/armor/skin/soul_hunter.png";
    }

    @OnlyIn(Dist.CLIENT)
    public String getItemModelName(ItemStack stack) {
        if (stack.getItem() instanceof ArmorItem armor) {
            return switch (armor.getEquipmentSlot()) {
                case HEAD -> WizardsReborn.MOD_ID + ":soul_hunter_hood";
                case CHEST -> WizardsReborn.MOD_ID + ":soul_hunter_costume";
                case LEGS -> WizardsReborn.MOD_ID + ":soul_hunter_trousers";
                case FEET -> WizardsReborn.MOD_ID + ":soul_hunter_boots";
                default -> WizardsReborn.MOD_ID + ":soul_hunter_hood";
            };
        }
        if (stack.getItem() instanceof ArcaneScytheItem) {
            return WizardsReborn.MOD_ID+":soul_hunter_scythe";
        }
        if (stack.getItem() instanceof ArcaneWandItem) {
            return WizardsReborn.MOD_ID+":skin/soul_hunter_arcane_wand";
        }

        return WizardsReborn.MOD_ID+":soul_hunter_hood";
    }
}
