package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.common.block.totem.experience.ExperienceTotemBlockEntity;
import mod.maxbogomol.wizards_reborn.util.WizardsRebornRenderUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class ExperienceTotemRenderer implements BlockEntityRenderer<ExperienceTotemBlockEntity> {

    @Override
    public void render(ExperienceTotemBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Random random = new Random();
        random.setSeed(blockEntity.getBlockPos().asLong());

        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 0.5F;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 3;
        ticksUp = (ticksUp) % 360;

        double ticksAlpha = (ClientTickHandler.ticksInGame + partialTicks);
        float alpha = (float) (0.45f + Math.abs(Math.sin(Math.toRadians(random.nextFloat() * 360f + ticksAlpha)) * 0.2f));

        float globalOffset = (float) Math.abs(Math.sin(Math.toRadians(random.nextFloat() * 360f + (ticksAlpha / 3))));
        float offset = (float) (0.55f + (((Math.sin(Math.toRadians(random.nextFloat() * 360f + ticksAlpha * 2)) + 1) * 0.5f) * 0.45f));

        if (blockEntity.getExperience() > 0) {
            float size = (0.85f + (0.15f * globalOffset)) * ((float) blockEntity.getExperience() / blockEntity.getMaxExperience());

            RenderBuilder builder = RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE);

            poseStack.pushPose();
            poseStack.translate(0.5F, 0.75F, 0.5F);
            poseStack.translate(0F, (Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0F);
            poseStack.mulPose(Axis.YP.rotationDegrees((float) (random.nextFloat() * 360 + ticks)));
            poseStack.mulPose(Axis.XP.rotationDegrees((float) (random.nextFloat() * 360 + ticks)));
            poseStack.mulPose(Axis.ZP.rotationDegrees((float) (random.nextFloat() * 360 + ticks)));
            poseStack.scale(size, size, size);
            builder.setColorRaw(0.243f, 0.564f, 0.250f).setAlpha(0.75f).renderCenteredCube(poseStack, 0.06f);
            builder.setColorRaw(0.243f, 0.564f, 0.250f).setAlpha(alpha).renderCenteredCube(poseStack, 0.075f);
            builder.setColorRaw(0.784f, 1f, 0.560f).setAlpha(alpha / 2f).renderCenteredCube(poseStack, 0.1f);
            builder.setColorRaw(0.960f, 1f, 0.560f).setAlpha(0.2f).renderCenteredCube(poseStack, 0.12f * offset);
            poseStack.popPose();
        }

        if (WissenUtil.isCanRenderWissenWand()) {
            poseStack.pushPose();
            poseStack.translate(-1, -1, -1);
            RenderUtil.renderConnectBoxLines(poseStack, new Vec3(3, 3, 3), WizardsRebornRenderUtil.colorArea, 0.5f);
            poseStack.popPose();
        }
    }

    @Override
    public boolean shouldRenderOffScreen(ExperienceTotemBlockEntity blockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(ExperienceTotemBlockEntity blockEntity, Vec3 cameraPos) {
        return true;
    }
}
