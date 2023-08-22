package mod.maxbogomol.wizards_reborn.common.item.equipment.curio;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenItem;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nonnull;
import java.util.UUID;

public class ArcanumAmuletItem extends Item implements ICurioItem, IWissenItem {

    private static final ResourceLocation AMULET_TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID,"textures/entity/curio/arcanum_amulet.png");

    public ArcanumAmuletItem(Item.Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_GOLD, 1.0f, 1.0f);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slot, ItemStack stack) {
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext,
                                                                        UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        return atts;
    }

    /*@Override
    public boolean canRender(String identifier, int index, LivingEntity living, ItemStack stack) {
        return true;
    }

    @Override
    public void render(String identifier, int index, PoseStack matrixStack,
                       MultiBufferSource renderTypeBuffer, int light, LivingEntity living,
                       float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
                       float netHeadYaw, float headPitch, ItemStack stack) {
        ICurio.RenderHelper.translateIfSneaking(matrixStack, living);
        ICurio.RenderHelper.rotateIfSneaking(matrixStack, living);

        AmuletModel<?> model = new AmuletModel<>();
        Vec3 rotate = RenderUtils.followBodyRotation(living);
        model.model.yRot = (float) rotate.y();

        VertexConsumer vertexBuilder = ItemRenderer
                .getFoilBuffer(renderTypeBuffer, model.renderType(AMULET_TEXTURE), false,
                        false);
        model
                .renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F,
                        1.0F);
    }*/

    @Override
    public int getMaxWissen() {
        return 1000;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {
        if (!world.isClientSide()) {
            WissenItemUtils.existWissen(stack);
        }
    }
}
