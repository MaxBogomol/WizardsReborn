package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.common.block.totem.experience_absorption.TotemOfExperienceAbsorptionBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import mod.maxbogomol.wizards_reborn.util.WizardsRebornRenderUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class TotemOfExperienceAbsorptionRenderer implements BlockEntityRenderer<TotemOfExperienceAbsorptionBlockEntity> {

    @Override
    public void render(TotemOfExperienceAbsorptionBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Random random = new Random();
        random.setSeed(blockEntity.getBlockPos().asLong());

        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 0.05F;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 3;
        ticksUp = (ticksUp) % 360;

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.59375F, 0.5F);
        poseStack.translate(0F, (Math.sin(Math.toRadians(ticksUp)) * 0.0625F), 0F);
        poseStack.mulPose(Axis.YP.rotationDegrees((float) ticks * 10));
        poseStack.mulPose(Axis.YP.rotationDegrees((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticks))) * 7F));
        poseStack.mulPose(Axis.XP.rotationDegrees((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticks))) * 7F));
        poseStack.mulPose(Axis.ZP.rotationDegrees((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticks))) * 7F));
        for (int i = 0; i < 4; i += 1) {
            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees(i * 90F));

            float tt = blockEntity.tick - partialTicks;
            if (blockEntity.getExperience() > 0 && blockEntity.getWissen() < blockEntity.getMaxWissen()) {
                tt = blockEntity.tick + partialTicks;
                if (tt > 20) {
                    tt = 20;
                }
            } else if (blockEntity.tick == 0) {
                tt = 0;
            }

            float t = Mth.lerp(tt / 20f, 0.09375F, 0.15625F);
            float size = Mth.lerp(tt / 20f, 1F, 0.5F);

            poseStack.translate(t, 0F, t);
            poseStack.scale(size, size, size);
            poseStack.mulPose(Axis.YP.rotationDegrees(180F));
            RenderUtil.renderCustomModel(WizardsRebornModels.TOTEM_OF_EXPERIENCE_ABSORPTION_PIECE, ItemDisplayContext.NONE, false, poseStack, bufferSource, light, overlay);
            poseStack.popPose();
        }
        poseStack.popPose();

        if (WissenUtil.isCanRenderWissenWand()) {
            poseStack.pushPose();
            poseStack.translate(-1, -1, -1);
            RenderUtil.renderConnectBoxLines(poseStack, new Vec3(3, 3, 3), WizardsRebornRenderUtil.colorArea, 0.5f);
            poseStack.popPose();
        }
    }

    @Override
    public boolean shouldRenderOffScreen(TotemOfExperienceAbsorptionBlockEntity blockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(TotemOfExperienceAbsorptionBlockEntity blockEntity, Vec3 cameraPos) {
        return true;
    }
}
