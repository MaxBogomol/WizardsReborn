package mod.maxbogomol.wizards_reborn.common.skin;

import mod.maxbogomol.fluffy_fur.common.itemskin.ArmorClassSkinEntry;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;

public class ArcaneArmorClassSkinEntry extends ArmorClassSkinEntry {
    public Map<EquipmentSlot, String> skins = new HashMap<>();

    public ArcaneArmorClassSkinEntry(Class item, String skin) {
        super(item, skin);
    }

    public ArcaneArmorClassSkinEntry addArmorSkin(EquipmentSlot armorSlot, String skin) {
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
}
