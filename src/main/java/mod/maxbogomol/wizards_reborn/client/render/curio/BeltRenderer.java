package mod.maxbogomol.wizards_reborn.client.render.curio;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.client.model.curio.BeltModel;
import mod.maxbogomol.wizards_reborn.common.item.equipment.curio.ICurioItemTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class BeltRenderer implements ICurioRenderer {
    public static ResourceLocation TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/entity/curio/leather_belt.png");

    BeltModel model = null;

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext,
                                                                          PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer,
                                                                          int light, float limbSwing, float limbSwingAmount, float partialTicks,
                                                                          float ageInTicks, float netHeadYaw, float headPitch) {
        if (model == null) {
            model = new BeltModel(Minecraft.getInstance().getEntityModels().bakeLayer(WizardsRebornClient.BELT_LAYER));
        }

        LivingEntity entity = slotContext.entity();
        if (stack.getItem() instanceof ICurioItemTexture) {
            ICurioItemTexture curio = (ICurioItemTexture) stack.getItem();
            TEXTURE = curio.getTexture(stack, entity);
        }

        ICurioRenderer.followBodyRotations(entity, model);

        model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        model.renderToBuffer(matrixStack, renderTypeBuffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }
}
