package mod.maxbogomol.wizards_reborn.client.render.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.model.sniffalo.SniffaloCarpetArmorModel;
import mod.maxbogomol.wizards_reborn.common.entity.SniffaloEntity;
import mod.maxbogomol.wizards_reborn.common.item.CargoCarpetItem;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SnifferModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.level.block.AbstractBannerBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SniffaloCarpetLayer extends RenderLayer<SniffaloEntity, SnifferModel<SniffaloEntity>> {
    public static final ResourceLocation SNIFFALO_CARPET_TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/entity/sniffalo/carpet/carpet.png");
    public final SnifferModel defaultModel;

    public static BannerBlockEntity banner = new BannerBlockEntity(BlockPos.ZERO, Blocks.WHITE_BANNER.defaultBlockState());

    public SniffaloCarpetLayer(RenderLayerParent<SniffaloEntity, SnifferModel<SniffaloEntity>> renderer) {
        super(renderer);
        this.defaultModel = renderer.getModel();
    }

    @Override
    public void render(PoseStack ms, MultiBufferSource buffer, int packedLight, SniffaloEntity livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float betHeadYaw, float headPitch) {
        if (!livingEntity.getCarpetClient().isEmpty()) {
            ResourceLocation texture = SNIFFALO_CARPET_TEXTURE;
            if (livingEntity.getCarpetClient().getItem() instanceof CargoCarpetItem carpet) {
                texture = carpet.getCarpetTexture(livingEntity.getCarpetClient(), livingEntity);
            }

            SniffaloCarpetArmorModel model = WizardsRebornModels.SNIFFALO_CARPET;
            model.young = livingEntity.isBaby();
            model.copyFromDefault(defaultModel);
            model.setupAnim(livingEntity, livingEntity.walkAnimation.position(partialTicks), livingEntity.walkAnimation.speed(partialTicks), livingEntity.tickCount + partialTicks, betHeadYaw, headPitch);
            model.renderToBuffer(ms, buffer.getBuffer(RenderType.entityCutoutNoCull(texture)), packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

            if (!livingEntity.getBannerClient().isEmpty()) {
                if (livingEntity.getBannerClient().getItem() instanceof BannerItem bannerItem) {
                    banner.fromItem(livingEntity.getBannerClient(), ((AbstractBannerBlock)bannerItem.getBlock()).getColor());
                }

                ms.pushPose();
                ModelPart body = defaultModel.root().getChild("bone").getChild("body");
                ms.mulPose(Axis.XP.rotationDegrees((float) (Math.toDegrees(body.xRot))));
                ms.mulPose(Axis.YP.rotationDegrees((float) (Math.toDegrees(body.yRot))));
                ms.mulPose(Axis.ZP.rotationDegrees((float) (Math.toDegrees(body.zRot))));
                ms.translate(body.x / 16f, body.y / 16f, body.z / 16f);
                ms.translate(-1, -1, -1);
                ms.mulPose(Axis.ZP.rotationDegrees(180f));
                ms.mulPose(Axis.YP.rotationDegrees(180f));
                ms.translate(0.5f, 0.15f, -2.4f);
                Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(banner, ms, buffer, packedLight, OverlayTexture.NO_OVERLAY);
                ms.popPose();
            }
        }
    }
}
