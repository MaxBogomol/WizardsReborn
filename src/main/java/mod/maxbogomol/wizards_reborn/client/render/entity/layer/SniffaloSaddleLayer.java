package mod.maxbogomol.wizards_reborn.client.render.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.model.sniffalo.SniffaloSaddleArmorModel;
import mod.maxbogomol.wizards_reborn.common.entity.SniffaloEntity;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import net.minecraft.client.model.SnifferModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SniffaloSaddleLayer extends RenderLayer<SniffaloEntity, SnifferModel<SniffaloEntity>> {
    public static final ResourceLocation SNIFFALO_SADDLE_TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/entity/sniffalo/saddle.png");
    public final SnifferModel defaultModel;

    public SniffaloSaddleLayer(RenderLayerParent<SniffaloEntity, SnifferModel<SniffaloEntity>> renderer) {
        super(renderer);
        this.defaultModel = renderer.getModel();
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, SniffaloEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float betHeadYaw, float headPitch) {
        if (livingEntity.isSaddled()) {
            SniffaloSaddleArmorModel model = WizardsRebornModels.SNIFFALO_SADDLE;
            model.young = livingEntity.isBaby();
            model.copyFromDefault(defaultModel);
            model.setupAnim(livingEntity, livingEntity.walkAnimation.position(partialTicks), livingEntity.walkAnimation.speed(partialTicks), livingEntity.tickCount + partialTicks, betHeadYaw, headPitch);
            model.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(SNIFFALO_SADDLE_TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        }
    }
}
