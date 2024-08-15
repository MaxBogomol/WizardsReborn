package mod.maxbogomol.wizards_reborn.api.skin;

import mod.maxbogomol.fluffy_fur.FluffyFurClient;
import mod.maxbogomol.fluffy_fur.client.model.armor.ArmorModel;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SkinEntry {
    public boolean canApplyOnItem(ItemStack itemStack) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public ArmorModel getArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default) {
        return FluffyFurClient.EMPTY_ARMOR_MODEL;
    }

    @OnlyIn(Dist.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return WizardsReborn.MOD_ID + ":textures/models/armor/skin/empty.png";
    }

    @OnlyIn(Dist.CLIENT)
    public String getItemModelName(ItemStack stack) {
        return null;
    }
}
