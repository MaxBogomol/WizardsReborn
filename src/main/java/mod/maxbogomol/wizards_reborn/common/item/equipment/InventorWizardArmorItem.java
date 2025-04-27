package mod.maxbogomol.wizards_reborn.common.item.equipment;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import mod.maxbogomol.fluffy_fur.client.model.armor.ArmorModel;
import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentTypes;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornAttributes;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.extensions.IForgeItem;

import java.util.function.Consumer;

public class InventorWizardArmorItem extends ArcaneArmorItem implements IForgeItem {

    public InventorWizardArmorItem(ArmorMaterial material, ArmorItem.Type type, Properties properties) {
        super(material, type, properties);
        arcaneEnchantmentTypes.add(ArcaneEnchantmentTypes.MAGIC_ARMOR);
    }

    @Override
    public int getWissenDiscountForSlot(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> 3;
            case CHEST -> 3;
            case LEGS -> 2;
            case FEET -> 2;
            default -> 0;
        };
    }

    @Override
    public int getMagicArmorForSlot(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> 4;
            case CHEST -> 5;
            case LEGS -> 3;
            case FEET -> 3;
            default -> 0;
        };
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> atts = ImmutableMultimap.builder();
        atts.putAll(super.getDefaultAttributeModifiers(slot));
        atts.put(WizardsRebornAttributes.WISSEN_DISCOUNT.get(), new AttributeModifier(getWissenDiscountUUIDForSlot(slot), "bonus", getWissenDiscountForSlot(slot), AttributeModifier.Operation.ADDITION));
        atts.put(WizardsRebornAttributes.MAGIC_ARMOR.get(), new AttributeModifier(getMagicArmorUUIDForSlot(slot), "bonus", getMagicArmorForSlot(slot), AttributeModifier.Operation.ADDITION));
        return slot == type.getSlot() ? atts.build() : super.getDefaultAttributeModifiers(slot);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public ArmorModel getHumanoidArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel original) {
                float partialTicks = Minecraft.getInstance().getFrameTime();
                float f = Mth.rotLerp(partialTicks, entity.yBodyRotO, entity.yBodyRot);
                float f1 = Mth.rotLerp(partialTicks, entity.yHeadRotO, entity.yHeadRot);
                float netHeadYaw = f1 - f;
                float netHeadPitch = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());

                ArmorModel model = WizardsRebornModels.INVENTOR_WIZARD_ARMOR;

                ItemSkin skin = ItemSkin.getSkinFromItem(itemStack);
                if (skin != null) model = skin.getArmorModel(entity, itemStack, equipmentSlot, original);

                model.slot = type.getSlot();
                model.copyFromDefault(original);
                model.setupAnim(entity, entity.walkAnimation.position(partialTicks), entity.walkAnimation.speed(partialTicks), entity.tickCount + partialTicks, netHeadYaw, netHeadPitch);
                return model;
            }
        });
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        ItemSkin skin = ItemSkin.getSkinFromItem(stack);
        if (skin != null) return skin.getArmorTexture(stack, entity, slot, type);
        return WizardsReborn.MOD_ID + ":textures/models/armor/inventor_wizard.png";
    }

    @Override
    public boolean hasCustomModel() {
        return true;
    }

    @Override
    public boolean hasArmorSet() {
        return true;
    }

    @Override
    public MutableComponent getArmorSetName() {
        return Component.translatable("lore.wizards_reborn.inventor_wizard_armor");
    }

    @Override
    public ChatFormatting getArmorSetColor() {
        return ChatFormatting.AQUA;
    }

    @Override
    public ItemStack getArmorSetItem(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> WizardsRebornItems.INVENTOR_WIZARD_HAT.get().getDefaultInstance();
            case CHEST -> WizardsRebornItems.INVENTOR_WIZARD_COSTUME.get().getDefaultInstance();
            case LEGS -> WizardsRebornItems.INVENTOR_WIZARD_TROUSERS.get().getDefaultInstance();
            case FEET -> WizardsRebornItems.INVENTOR_WIZARD_BOOTS.get().getDefaultInstance();
            default -> ItemStack.EMPTY;
        };
    }

    @Override
    public float getMagicModifier() {
        return 1f;
    }
}
