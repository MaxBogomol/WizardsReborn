package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.common.block.arcane_iterator.ArcaneIteratorBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import mod.maxbogomol.wizards_reborn.util.WizardsRebornRenderUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class ArcaneIteratorRenderer implements BlockEntityRenderer<ArcaneIteratorBlockEntity> {

    @Override
    public void render(ArcaneIteratorBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Random random = new Random();
        random.setSeed(blockEntity.getBlockPos().asLong());

        double ticks = (blockEntity.rotate + partialTicks);
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
        ticksUp = (ticksUp) % 360;

        if (!blockEntity.isWorks()) {
            ticks = blockEntity.rotate;
        }

        float x = 0.15625F;
        float y = 0.15625F;
        float z = 0.15625F;
        float offset = 1;
        float size = 1;

        if (blockEntity.wissenInCraft > 0 && blockEntity.startCraft) {
            if (blockEntity.offset > 0) {
                offset = (blockEntity.offset + partialTicks) / 40F;
                if (offset > 1) {
                    offset = 1;
                }
                offset = offset + 1;
            }
            if (blockEntity.scale > 0) {
                size = (blockEntity.scale + partialTicks) / 60F;
                if (size > 1) {
                    size = 1;
                }
                size = 1 - size;
            }
        } else {
            if (blockEntity.offset > 0) {
                offset = (blockEntity.offset - partialTicks) / 40F;
                if (offset < 0) {
                    offset = 0;
                }
                offset = offset + 1;
            }
            if (blockEntity.scale > 0) {
                size = (blockEntity.scale - partialTicks) / 60F;
                if (size < 0) {
                    size = 0;
                }
                size = 1 - size;
            }
        }
        if (size < 0) {
            size = 0;
        }

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.5F + (Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees((float) (random.nextFloat() * 360 + ticks)));
        poseStack.mulPose(Axis.XP.rotationDegrees((float) (random.nextFloat() * 360 + ticks)));
        poseStack.scale(size, size, size);
        poseStack.mulPose(Axis.ZP.rotationDegrees((float) (random.nextFloat() * 360 + ticks)));
        renderPiece(-x * offset, y * offset, -z * offset, 90F, 0F, size, blockEntity, partialTicks, poseStack, bufferSource, light, overlay);
        renderPiece(x * offset, y * offset, -z * offset, 0F, 0F, size, blockEntity, partialTicks, poseStack, bufferSource, light, overlay);
        renderPiece(-x * offset, y * offset, z * offset, 180F, 0F, size, blockEntity, partialTicks, poseStack, bufferSource, light, overlay);
        renderPiece(x * offset, y * offset, z * offset, -90F, 0F, size, blockEntity, partialTicks, poseStack, bufferSource, light, overlay);
        renderPiece(-x * offset, -y * offset, -z * offset, 90F, 90F, size, blockEntity, partialTicks, poseStack, bufferSource, light, overlay);
        renderPiece(x * offset, -y * offset, -z * offset, 0F, 90F, size, blockEntity, partialTicks, poseStack, bufferSource, light, overlay);
        renderPiece(-x * offset, -y * offset, z * offset, 180F, 90F, size, blockEntity, partialTicks, poseStack, bufferSource, light, overlay);
        renderPiece(x * offset, -y * offset, z * offset, -90F, 90F, size, blockEntity, partialTicks, poseStack, bufferSource, light, overlay);
        poseStack.popPose();

        if (WissenUtil.isCanRenderWissenWand()) {
            poseStack.pushPose();
            poseStack.translate(-5, -3, -5);
            RenderUtil.renderConnectBoxLines(poseStack, new Vec3(11, 7, 11), WizardsRebornRenderUtil.colorArea, 0.5f);
            poseStack.popPose();

            if (!blockEntity.isWorks()) {
                poseStack.pushPose();
                poseStack.translate(0, -2, 0);
                RenderUtil.renderConnectBoxLines(poseStack, new Vec3(1, 3, 1), WizardsRebornRenderUtil.colorMissing, 0.5f);
                poseStack.popPose();
            }
        }

        RenderBuilder builder = RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE).setSecondAlpha(0);

        if ((1f - size) > 0) {
            poseStack.pushPose();
            float xOffset = (float) (Math.cos(blockEntity.angleA) * Math.cos(blockEntity.angleB));
            float yOffset = (float) (Math.sin(blockEntity.angleA) * Math.cos(blockEntity.angleB));
            float zOffset = (float) Math.sin(blockEntity.angleB);

            double yaw = Math.atan2(zOffset, xOffset);
            double pitch = Math.atan2(Math.sqrt(zOffset * zOffset + xOffset * xOffset), yOffset) + Math.PI;

            float trailSize = (1f - size) * 1.1f;
            float trailWidth = (1f - size) * 0.6f;

            poseStack.translate(0.5F, 0.5F, 0.5F);
            poseStack.mulPose(Axis.YP.rotation((float) -yaw));
            poseStack.mulPose(Axis.ZP.rotation((float) -pitch));
            builder.setColorRaw(0.807f, 0.800f, 0.639f).setFirstAlpha(1f).renderBeam(poseStack, 0.25f * trailWidth, trailSize * 0.9f, 0);
            builder.setColorRaw(0.611f, 0.352f, 0.447f).setFirstAlpha(0.75f).renderBeam(poseStack, 0.45f * trailWidth, trailSize, 0);
            poseStack.mulPose(Axis.ZP.rotationDegrees(180f));
            builder.setColorRaw(0.807f, 0.800f, 0.639f).setFirstAlpha(1f).renderBeam(poseStack, 0.25f * trailWidth, trailSize * 0.9f, 0);
            builder.setColorRaw(0.611f, 0.352f, 0.447f).setFirstAlpha(0.75f).renderBeam(poseStack, 0.45f * trailWidth, trailSize, 0);
            poseStack.popPose();
        }
    }

    public void renderPiece(float x, float y, float z, float yRot, float zRot, float size, ArcaneIteratorBlockEntity iterator, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        poseStack.pushPose();
        poseStack.translate(x, y, z);
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
        poseStack.mulPose(Axis.ZP.rotationDegrees(-zRot));
        poseStack.scale(size, size, size);
        RenderUtil.renderCustomModel(WizardsRebornModels.ARCANE_ITERATOR_PIECE, ItemDisplayContext.NONE, false, poseStack, bufferSource, light, overlay);
        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(ArcaneIteratorBlockEntity blockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(ArcaneIteratorBlockEntity blockEntity, Vec3 cameraPos) {
        return true;
    }
}
