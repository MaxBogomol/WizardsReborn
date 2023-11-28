package mod.maxbogomol.wizards_reborn.common.item.equipment;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.client.model.armor.InventorWizardArmorModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
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

import java.util.UUID;

public class InventorWizardArmorItem extends ArmorItem implements IForgeItem {
    public static final UUID BASE_WISSEN_SALE_UUID = UUID.fromString("0D0AA300-8D31-11EE-B9D1-0242AC120002");

    public InventorWizardArmorItem(ArmorMaterial material, ArmorItem.Type type, Properties properties) {
        super(material, type, properties);
    }

    public static int getWissenSaleForSlot(EquipmentSlot slot) {
        return switch (slot) {
            case CHEST -> 3;
            case HEAD -> 3;
            case LEGS -> 2;
            case FEET -> 2;
            default -> 0;
        };
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> atts = ImmutableMultimap.builder();
        atts.putAll(super.getDefaultAttributeModifiers(slot));
        atts.put(WizardsReborn.WISSEN_SALE.get(), new AttributeModifier(BASE_WISSEN_SALE_UUID, "bonus", getWissenSaleForSlot(slot), AttributeModifier.Operation.ADDITION));
        return slot == type.getSlot() ? atts.build() : super.getDefaultAttributeModifiers(slot);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(java.util.function.Consumer<net.minecraftforge.client.extensions.common.IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public InventorWizardArmorModel getHumanoidArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default) {
                float pticks = Minecraft.getInstance().getFrameTime();
                float f = Mth.rotLerp(pticks, entity.yBodyRotO, entity.yBodyRot);
                float f1 = Mth.rotLerp(pticks, entity.yHeadRotO, entity.yHeadRot);
                float netHeadYaw = f1 - f;
                float netHeadPitch = Mth.lerp(pticks, entity.xRotO, entity.getXRot());
                WizardsRebornClient.INVENTOR_WIZARD_ARMOR_MODEL.slot = type.getSlot();
                WizardsRebornClient.INVENTOR_WIZARD_ARMOR_MODEL.copyFromDefault(_default);
                WizardsRebornClient.INVENTOR_WIZARD_ARMOR_MODEL.setupAnim(entity, entity.walkAnimation.position(pticks), entity.walkAnimation.speed(pticks), entity.tickCount + pticks, netHeadYaw, netHeadPitch);
                return WizardsRebornClient.INVENTOR_WIZARD_ARMOR_MODEL;
            }
        });
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return WizardsReborn.MOD_ID + ":textures/models/armor/inventor_wizard.png";
    }
}
