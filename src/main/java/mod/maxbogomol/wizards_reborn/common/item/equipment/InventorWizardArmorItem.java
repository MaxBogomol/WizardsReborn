package mod.maxbogomol.wizards_reborn.common.item.equipment;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.client.model.armor.InventorWizardArmorModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.extensions.IForgeItem;

public class InventorWizardArmorItem extends ArmorItem implements IForgeItem {
    public InventorWizardArmorItem(ArmorMaterial material, ArmorItem.Type type, Properties properties) {
        super(material, type, properties);
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
