package mod.maxbogomol.wizards_reborn.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.maxbogomol.fluffy_fur.client.render.LevelRenderHandler;
import mod.maxbogomol.wizards_reborn.common.entity.SplitArrowEntity;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornArcaneEnchantments;
import mod.maxbogomol.wizards_reborn.util.RenderUtils;
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
    public void render(SplitArrowEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {
        MultiBufferSource bufferDelayed = LevelRenderHandler.getDelayedRender();
        VertexConsumer builder = bufferDelayed.getBuffer(RenderUtils.GLOWING);
        Color color = WizardsRebornArcaneEnchantments.SPLIT.getColor();

        List<Vec3> trailList = new ArrayList<>(entity.trail);
        if (trailList.size() > 1 && entity.tickCount >= 10) {
            Vec3 position = trailList.get(0);
            Vec3 nextPosition = trailList.get(1);
            float x = (float) Mth.lerp(partialTicks, position.x, nextPosition.x);
            float y = (float) Mth.lerp(partialTicks, position.y, nextPosition.y);
            float z = (float) Mth.lerp(partialTicks, position.z, nextPosition.z);
            trailList.set(0, new Vec3(x, y, z));
        }

        float x = (float) Mth.lerp(partialTicks, entity.xOld, entity.getX());
        float y = (float) Mth.lerp(partialTicks, entity.yOld, entity.getY());
        float z = (float) Mth.lerp(partialTicks, entity.zOld, entity.getZ());

        stack.pushPose();
        stack.translate(0, 0.1f, 0);
        stack.translate(entity.getX() - x, entity.getY() - y,  entity.getZ() - z);
        RenderUtils.renderTrail(stack, builder, entity.position(), trailList, 0,0.1f, 0,1.0f, 1.0f, color, 4, true);
        //RenderUtils.renderTrail(stack, builder, entity.position(), trailList, 0,0.05f, 0,1.0f, 1.0f, color, 4, true);
        stack.popPose();
    }

    @Override
    public boolean shouldRender(T livingEntityIn, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return null;
    }
}