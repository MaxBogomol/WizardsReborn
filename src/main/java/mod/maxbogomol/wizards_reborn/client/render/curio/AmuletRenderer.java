package mod.maxbogomol.wizards_reborn.client.render.curio;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.integration.common.curios.ICurioItemTexture;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.model.curio.AmuletModel;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
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

public class AmuletRenderer implements ICurioRenderer {
    public static ResourceLocation TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/entity/curio/arcanum_amulet.png");

    AmuletModel model = null;

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource bufferSource, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (model == null) model = new AmuletModel(Minecraft.getInstance().getEntityModels().bakeLayer(WizardsRebornModels.AMULET_LAYER));

        LivingEntity entity = slotContext.entity();
        if (stack.getItem() instanceof ICurioItemTexture curio) {
            ResourceLocation texture = curio.getTexture(stack, entity);
            if (texture != null) TEXTURE = texture;
        }

        ICurioRenderer.followBodyRotations(entity, model);

        model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        model.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }
}
