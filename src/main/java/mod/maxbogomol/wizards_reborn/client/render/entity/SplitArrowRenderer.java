package mod.maxbogomol.wizards_reborn.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.client.render.trail.TrailPoint;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SplitArrowEntity;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornArcaneEnchantments;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SplitArrowRenderer<T extends SplitArrowEntity> extends EntityRenderer<T> {

    public SplitArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(SplitArrowEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        Color color = WizardsRebornArcaneEnchantments.SPLIT.getColor();

        List<TrailPoint> trail = new ArrayList<>(entity.trailPointBuilder.getTrailPoints());
        if (trail.size() > 1 && entity.tickCount >= entity.trailPointBuilder.trailLength.get()) {
            TrailPoint position = trail.get(0);
            TrailPoint nextPosition = trail.get(1);
            float x = (float) Mth.lerp(partialTicks, position.getPosition().x, nextPosition.getPosition().x);
            float y = (float) Mth.lerp(partialTicks, position.getPosition().y, nextPosition.getPosition().y);
            float z = (float) Mth.lerp(partialTicks, position.getPosition().z, nextPosition.getPosition().z);
            trail.set(0, new TrailPoint(new Vec3(x, y, z)));
        }

        float x = (float) Mth.lerp(partialTicks, entity.xOld, entity.getX());
        float y = (float) Mth.lerp(partialTicks, entity.yOld, entity.getY());
        float z = (float) Mth.lerp(partialTicks, entity.zOld, entity.getZ());

        if (trail.size() > 0) {
            trail.set(trail.size() - 1, new TrailPoint(new Vec3(x, y, z)));
        }

        poseStack.pushPose();
        poseStack.translate(0, 0.1f, 0);
        poseStack.translate(-x, -y, -z);
        RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/trail"))
                .setColor(color)
                .setFirstAlpha(0.25f).setSecondAlpha(1f)
                .renderTrail(poseStack, trail, (f) -> {return f * 0.25f;});
        poseStack.popPose();
    }

    @Override
    public boolean shouldRender(T livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return null;
    }
}