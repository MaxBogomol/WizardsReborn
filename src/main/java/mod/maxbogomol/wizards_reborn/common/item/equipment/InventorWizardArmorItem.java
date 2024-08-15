package mod.maxbogomol.wizards_reborn.common.item.equipment;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import mod.maxbogomol.fluffy_fur.client.model.armor.ArmorModel;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentType;
import mod.maxbogomol.wizards_reborn.api.skin.Skin;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
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

public class InventorWizardArmorItem extends ArcaneArmorItem implements IForgeItem {
    public InventorWizardArmorItem(ArmorMaterial material, ArmorItem.Type type, Properties properties) {
        super(material, type, properties);
        arcaneEnchantmentTypes.add(ArcaneEnchantmentType.MAGIC_ARMOR);
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
        atts.put(WizardsReborn.WISSEN_DISCOUNT.get(), new AttributeModifier(getWissenDiscountUUIDForSlot(slot), "bonus", getWissenDiscountForSlot(slot), AttributeModifier.Operation.ADDITION));
        atts.put(WizardsReborn.MAGIC_ARMOR.get(), new AttributeModifier(getMagicArmorUUIDForSlot(slot), "bonus", getMagicArmorForSlot(slot), AttributeModifier.Operation.ADDITION));
        return slot == type.getSlot() ? atts.build() : super.getDefaultAttributeModifiers(slot);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(java.util.function.Consumer<net.minecraftforge.client.extensions.common.IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public ArmorModel getHumanoidArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default) {
                float pticks = Minecraft.getInstance().getFrameTime();
                float f = Mth.rotLerp(pticks, entity.yBodyRotO, entity.yBodyRot);
                float f1 = Mth.rotLerp(pticks, entity.yHeadRotO, entity.yHeadRot);
                float netHeadYaw = f1 - f;
                float netHeadPitch = Mth.lerp(pticks, entity.xRotO, entity.getXRot());

                ArmorModel model = WizardsRebornClient.INVENTOR_WIZARD_ARMOR_MODEL;

                Skin skin = Skin.getSkinFromItem(itemStack);
                if (skin != null) model = skin.getArmorModel(entity, itemStack, armorSlot, _default);

                model.slot = type.getSlot();
                model.copyFromDefault(_default);
                model.setupAnim(entity, entity.walkAnimation.position(pticks), entity.walkAnimation.speed(pticks), entity.tickCount + pticks, netHeadYaw, netHeadPitch);
                return model;
            }
        });
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        Skin skin = Skin.getSkinFromItem(stack);
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
            case HEAD -> WizardsReborn.INVENTOR_WIZARD_HAT.get().getDefaultInstance();
            case CHEST -> WizardsReborn.INVENTOR_WIZARD_COSTUME.get().getDefaultInstance();
            case LEGS -> WizardsReborn.INVENTOR_WIZARD_TROUSERS.get().getDefaultInstance();
            case FEET -> WizardsReborn.INVENTOR_WIZARD_BOOTS.get().getDefaultInstance();
            default -> ItemStack.EMPTY;
        };
    }

    @Override
    public float getMagicModifier() {
        return 1f;
    }
}
