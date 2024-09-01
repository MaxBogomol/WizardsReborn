package mod.maxbogomol.wizards_reborn.common.skin;

import mod.maxbogomol.fluffy_fur.client.model.armor.ArmorModel;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurModels;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
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
            if (itemStack.getItem() instanceof ArcaneArmorItem armor && armor.hasCustomModel()) {
                return skins.containsKey(armor.getEquipmentSlot());
            }
        }
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public String getItemModelName(ItemStack stack) {
        if (stack.getItem() instanceof ArcaneArmorItem armor) {
            return skins.get(armor.getEquipmentSlot());
        }
        return null;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ArmorModel getArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default) {
        return FluffyFurModels.EMPTY_ARMOR;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return skin;
    }
}
