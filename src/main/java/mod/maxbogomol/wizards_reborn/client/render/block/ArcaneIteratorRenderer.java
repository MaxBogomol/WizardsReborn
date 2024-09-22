package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.common.block.arcane_iterator.ArcaneIteratorBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import mod.maxbogomol.wizards_reborn.util.WizardsRebornRenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArcaneIteratorRenderer implements BlockEntityRenderer<ArcaneIteratorBlockEntity> {

    @Override
    public void render(ArcaneIteratorBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Random random = new Random();
        random.setSeed(blockEntity.getBlockPos().asLong());

        Minecraft minecraft = Minecraft.getInstance();
        double ticks = (blockEntity.rotate + partialTicks);
        double ticksOffset = (ClientTickHandler.ticksInGame + partialTicks) * 0.1F;
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

        MultiBufferSource bufferDelayed = FluffyFurRenderTypes.getDelayedRender();
        VertexConsumer builder = bufferDelayed.getBuffer(FluffyFurRenderTypes.ADDITIVE);

        if ((1f - size) > 0) {
            poseStack.pushPose();
            List<Vec3> trailList = new ArrayList<>();
            float xOffset = (float) (Math.cos(blockEntity.angleA) * Math.cos(blockEntity.angleB));
            float yOffset = (float) (Math.sin(blockEntity.angleA) * Math.cos(blockEntity.angleB));
            float zOffset = (float) Math.sin(blockEntity.angleB);

            float trailSize = (1f - size) * 1.1f;
            float trailWidth = (1f - size) * 0.3f;

            poseStack.translate(0.5F, 0.5F, 0.5F);
            trailList.add(new Vec3(0, 0, 0));
            trailList.add(new Vec3(xOffset * trailSize, yOffset * trailSize, zOffset * trailSize));

            //WizardsRebornRenderUtil.renderTrail(poseStack, builder, Vec3.ZERO, trailList, trailWidth, 0, 0.25f,0, 1.0f, new Color(0.807f, 0.800f, 0.639f), 8, false);
            //WizardsRebornRenderUtil.renderTrail(poseStack, builder, Vec3.ZERO, trailList, trailWidth, 0, 0.5f,0,0.75f, new Color(0.611f, 0.352f, 0.447f), 8, false);

            trailList.clear();
            trailList.add(new Vec3(0, 0, 0));
            trailList.add(new Vec3(xOffset * -trailSize, yOffset * -trailSize, zOffset * -trailSize));

            //WizardsRebornRenderUtil.renderTrail(poseStack, builder, Vec3.ZERO, trailList, trailWidth, 0, 0.25f,0, 1.0f, new Color(0.807f, 0.800f, 0.639f), 8, false);
            //WizardsRebornRenderUtil.renderTrail(poseStack, builder, Vec3.ZERO, trailList, trailWidth, 0, 0.5f,0,0.75f, new Color(0.611f, 0.352f, 0.447f), 8, false);
            poseStack.popPose();
        }
    }

    public void renderPiece(float x, float y, float z, float yRot, float zRot, float size, ArcaneIteratorBlockEntity iterator, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        poseStack.pushPose();
        poseStack.translate(x, y, z);
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
        poseStack.mulPose(Axis.ZP.rotationDegrees(-zRot));
        poseStack.scale(size, size, size);
        RenderUtil.renderCustomModel(WizardsRebornModels.ARCANE_ITERATOR_PIECE, ItemDisplayContext.FIXED, false, poseStack, bufferSource, light, overlay);
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
