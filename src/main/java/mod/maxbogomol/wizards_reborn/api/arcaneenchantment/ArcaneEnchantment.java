package mod.maxbogomol.wizards_reborn.api.arcaneenchantment;

import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

import java.awt.*;

public class ArcaneEnchantment {
    public String id;
    public int maxLevel;

    public ArcaneEnchantment(String id, int maxLevel) {
        this.id = id;
        this.maxLevel = maxLevel;
    }

    public String getId() {
        return id;
    }

    public Color getColor() {
        return new Color(255, 255, 255);
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public String getTranslatedName() {
        return getTranslatedName(id);
    }

    public static String getTranslatedName(String id) {
        int i = id.indexOf(":");
        String modId = id.substring(0, i);
        String spellId = id.substring(i + 1);
        return "arcane_enchantment."  + modId + "." + spellId;
    }

    public boolean canEnchantItem(ItemStack stack) {
        return true;
    }

    public boolean checkCompatibility(ArcaneEnchantment arcaneEnchantment) {
        return true;
    }

    public Component getFullname(int level) {
        MutableComponent component = Component.translatable(getTranslatedName());
        if (level != 1 || this.getMaxLevel() != 1) {
            component.append(CommonComponents.SPACE).append(Component.translatable("enchantment.level." + level));
        }

        return component;
    }
}
