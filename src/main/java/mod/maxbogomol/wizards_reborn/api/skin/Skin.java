package mod.maxbogomol.wizards_reborn.api.skin;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.client.model.armor.ArmorModel;
import mod.maxbogomol.wizards_reborn.utils.ColorUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;

public class Skin {
    public String id;
    public Color color;

    public Skin(String id) {
        this.id = id;
        this.color = new Color(255, 255, 255);
    }

    public Skin(String id, Color color) {
        this.id = id;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public String getTranslatedName() {
        return getTranslatedName(id);
    }

    public String getTranslatedLoreName() {
        return getTranslatedLoreName(id);
    }

    public static String getTranslatedName(String id) {
        int i = id.indexOf(":");
        String modId = id.substring(0, i);
        String monogramId = id.substring(i + 1);
        return "skin."  + modId + "." + monogramId;
    }

    public static String getTranslatedLoreName(String id) {
        return getTranslatedName(id) + ".lore";
    }

    public static Component getSkinName(Skin skin) {
        Color color = skin.getColor();

        return Component.translatable(skin.getTranslatedName()).withStyle(Style.EMPTY.withColor(ColorUtils.packColor(255, color.getRed(), color.getGreen(), color.getBlue())));
    }

    public static Component getSkinComponent(Skin skin) {
        return Component.translatable("lore.wizards_reborn.skin").withStyle(Style.EMPTY.withColor(ColorUtils.packColor(255, 249, 210, 129)))
                .append(" ").append(getSkinName(skin));
    }

    public Component getSkinName() {
        return getSkinName(this);
    }

    public Component getSkinComponent() {
        return getSkinComponent(this);
    }

    public boolean canApplyOnItem(ItemStack itemStack) {
        return true;
    }

    public ItemStack applyOnItem(ItemStack itemStack) {
        CompoundTag nbt = itemStack.getOrCreateTag();
        nbt.putString("skin", this.getId());
        return itemStack;
    }

    public static Skin getSkinFromItem(ItemStack itemStack) {
        CompoundTag nbt = itemStack.getOrCreateTag();
        if (nbt.contains("skin")) {
            return Skins.getSkin(nbt.getString("skin"));
        }
        return null;
    }

    @OnlyIn(Dist.CLIENT)
    public ArmorModel getArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default) {
        return WizardsRebornClient.INVENTOR_WIZARD_ARMOR_MODEL;
    }

    @OnlyIn(Dist.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return WizardsReborn.MOD_ID + ":textures/models/armor/inventor_wizard.png";
    }

    @OnlyIn(Dist.CLIENT)
    public String getItemModelName(ItemStack stack) {
        return "";
    }
}
