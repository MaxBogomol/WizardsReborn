package mod.maxbogomol.wizards_reborn.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.render.entity.layer.SniffaloArmorLayer;
import mod.maxbogomol.wizards_reborn.client.render.entity.layer.SniffaloCarpetLayer;
import mod.maxbogomol.wizards_reborn.client.render.entity.layer.SniffaloSaddleLayer;
import mod.maxbogomol.wizards_reborn.common.entity.SniffaloEntity;
import net.minecraft.client.model.SnifferModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class SniffaloRenderer extends MobRenderer<SniffaloEntity, SnifferModel<SniffaloEntity>> {
    public static final ResourceLocation SNIFFALO_TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/entity/sniffalo/sniffalo.png");
    public static SnifferModel snifferModel;

    public SniffaloRenderer(EntityRendererProvider.Context context) {
        super(context, new SnifferModel<>(context.bakeLayer(ModelLayers.SNIFFER)), 1.1F);
        addLayer(new SniffaloSaddleLayer(this));
        addLayer(new SniffaloCarpetLayer(this));
        addLayer(new SniffaloArmorLayer(this));
        snifferModel = getModel();
    }

    @Override
    public ResourceLocation getTextureLocation(SniffaloEntity entity) {
        return SNIFFALO_TEXTURE;
    }

    public static void playerOffset(Player player, SniffaloEntity sniffalo, SnifferModel snifferModel, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        snifferModel.setupAnim(sniffalo, sniffalo.walkAnimation.position(partialTicks), sniffalo.walkAnimation.speed(partialTicks), sniffalo.tickCount + partialTicks, 0, 0);
        ModelPart body = snifferModel.root().getChild("bone").getChild("body");
        poseStack.translate(0, sniffalo.getBbHeight(), 0);
        poseStack.mulPose(Axis.XP.rotation(body.xRot));
        poseStack.mulPose(Axis.YP.rotation(body.yRot));
        poseStack.mulPose(Axis.ZP.rotation(body.zRot));
        if (sniffalo.isDigging()) poseStack.translate(0, -0.4f, 0);
        poseStack.translate(0, -sniffalo.getBbHeight(), 0);
        poseStack.translate(body.x / 16f, body.y / 16f, body.z / 16f);
    }
}