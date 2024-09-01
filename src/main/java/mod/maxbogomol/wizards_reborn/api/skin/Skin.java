package mod.maxbogomol.wizards_reborn.api.skin;

import mod.maxbogomol.fluffy_fur.client.model.armor.ArmorModel;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurModels;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
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
import java.util.ArrayList;
import java.util.List;

public class Skin {
    public String id;
    public Color color;
    public List<SkinEntry> skinEntries = new ArrayList<>();

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

        return Component.translatable(skin.getTranslatedName()).withStyle(Style.EMPTY.withColor(ColorUtil.packColor(255, color.getRed(), color.getGreen(), color.getBlue())));
    }

    public static Component getSkinComponent(Skin skin) {
        return Component.translatable("lore.wizards_reborn.skin").withStyle(Style.EMPTY.withColor(ColorUtil.packColor(255, 249, 210, 129)))
                .append(" ").append(getSkinName(skin));
    }

    public Component getSkinName() {
        return getSkinName(this);
    }

    public Component getSkinComponent() {
        return getSkinComponent(this);
    }

    public boolean canApplyOnItem(ItemStack itemStack) {
        for (SkinEntry skinEntry : getSkinEntries()) {
            if (skinEntry.canApplyOnItem(itemStack)) return true;
        }
        return false;
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
        for (SkinEntry skinEntry : getSkinEntries()) {
            if (skinEntry.canApplyOnItem(itemStack)) return skinEntry.getArmorModel(entity, itemStack, armorSlot, _default);
        }
        return FluffyFurModels.EMPTY_ARMOR;
    }

    @OnlyIn(Dist.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        for (SkinEntry skinEntry : getSkinEntries()) {
            if (skinEntry.canApplyOnItem(stack)) return skinEntry.getArmorTexture(stack, entity, slot, type);
        }
        return WizardsReborn.MOD_ID + ":textures/models/armor/skin/empty.png";
    }

    @OnlyIn(Dist.CLIENT)
    public String getItemModelName(ItemStack stack) {
        for (SkinEntry skinEntry : getSkinEntries()) {
            if (skinEntry.canApplyOnItem(stack)) return skinEntry.getItemModelName(stack);
        }
        return null;
    }

    public List<SkinEntry> getSkinEntries() {
        return skinEntries;
    }

    public void addSkinEntry(SkinEntry skinEntry) {
        skinEntries.add(skinEntry);
    }

    public void setupSkinEntries() {

    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isDefaultModel(Entity entity) {
        if (entity instanceof AbstractClientPlayer player) {
            return player.getModelName().equals("default");
        }

        return true;
    }
}
