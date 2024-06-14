package mod.maxbogomol.wizards_reborn.common.skin;

import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.client.model.armor.ArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;

public class ArmorClassSkinEntry extends ItemClassSkinEntry {
    public Map<EquipmentSlot, String> skins = new HashMap<>();

    public ArmorClassSkinEntry(Class item, String skin) {
        super(item, skin);
    }

    public ArmorClassSkinEntry addArmorSkin(EquipmentSlot armorSlot, String skin) {
        skins.put(armorSlot, skin);
        return this;
    }

    @Override
    public boolean canApplyOnItem(ItemStack itemStack) {
        if (item.isInstance(itemStack.getItem())) {
            if (itemStack.getItem() instanceof ArmorItem armor) {
                return skins.containsKey(armor.getEquipmentSlot());
            }
        }
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public String getItemModelName(ItemStack stack) {
        if (stack.getItem() instanceof ArmorItem armor) {
            return skins.get(armor.getEquipmentSlot());
        }
        return "";
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ArmorModel getArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default) {
        return WizardsRebornClient.INVENTOR_WIZARD_ARMOR_MODEL;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return skin;
    }
}
