package mod.maxbogomol.wizards_reborn.common.skin;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.api.skin.Skin;
import mod.maxbogomol.wizards_reborn.client.model.armor.ArmorModel;
import mod.maxbogomol.wizards_reborn.common.item.equipment.InventorWizardArmorItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
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
    public boolean canApplyOnItem(ItemStack itemStack) {
        if (itemStack.getItem() instanceof InventorWizardArmorItem armor) {
            return armor.getEquipmentSlot() == EquipmentSlot.HEAD;
        }

        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ArmorModel getArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default) {
        return WizardsRebornClient.TOP_HAT_ARMOR_MODEL;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return WizardsReborn.MOD_ID + ":textures/models/armor/skin/top_hat.png";
    }

    @OnlyIn(Dist.CLIENT)
    public String getItemModelName(ItemStack stack) {
        return WizardsReborn.MOD_ID+":top_hat";
    }
}
