package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualArea;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualUtil;
import mod.maxbogomol.wizards_reborn.api.light.ILightBlockEntity;
import mod.maxbogomol.wizards_reborn.api.light.LightRayHitResult;
import mod.maxbogomol.wizards_reborn.api.light.LightUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.common.block.crystal.CrystalBlockEntity;
import mod.maxbogomol.wizards_reborn.common.item.CrystalItem;
import mod.maxbogomol.wizards_reborn.util.WizardsRebornRenderUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.Random;

public class CrystalRenderer implements BlockEntityRenderer<CrystalBlockEntity> {

    @Override
    public void render(CrystalBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Random random = new Random();
        random.setSeed(blockEntity.getBlockPos().asLong());

        double ticksAlpha = (ClientTickHandler.ticksInGame + partialTicks);
        float alpha = (float) (0.15f + Math.abs(Math.sin(Math.toRadians(random.nextFloat() * 360f + ticksAlpha)) * 0.05f));

        MultiBufferSource bufferDelayed = FluffyFurRenderTypes.getDelayedRender();

        CrystalRitual ritual = blockEntity.getCrystalRitual();

        if (blockEntity.getLight() > 0) {
            if (!blockEntity.getItemHandler().getItem(0).isEmpty()) {
                if (blockEntity.getItemHandler().getItem(0).getItem() instanceof CrystalItem crystalItem) {
                    Color color = crystalItem.getType().getColor();
                    float r = color.getRed() / 255f;
                    float g = color.getGreen() / 255f;
                    float b = color.getBlue() / 255f;

                    poseStack.pushPose();
                    poseStack.translate(0.5F, 0.3825F, 0.5F);
                    poseStack.mulPose(Axis.ZP.rotationDegrees(-90f));
                    //WizardsRebornRenderUtil.ray(poseStack, bufferDelayed, 0.2f, 0.4f, 1f, r, g, b, alpha);
                    poseStack.popPose();
                }
            }

            if (blockEntity.isToBlock && !CrystalRitualUtil.isEmpty(ritual) && ritual.hasLightRay(blockEntity)) {
                BlockPos pos = new BlockPos(blockEntity.blockToX, blockEntity.blockToY, blockEntity.blockToZ);
                if (blockEntity.getLevel().getBlockEntity(pos) instanceof ILightBlockEntity lightTile) {
                    Vec3 from = LightUtil.getLightLensPos(blockEntity.getBlockPos(), blockEntity.getLightLensPos());
                    Vec3 to = LightUtil.getLightLensPos(pos, lightTile.getLightLensPos());

                    double dX = to.x() - from.x();
                    double dY = to.y() - from.y();
                    double dZ = to.z() - from.z();

                    double yaw = Math.atan2(dZ, dX);
                    double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

                    float rayDistance = 0.33f;

                    double X = Math.sin(pitch) * Math.cos(yaw) * rayDistance;
                    double Y = Math.cos(pitch) * rayDistance;
                    double Z = Math.sin(pitch) * Math.sin(yaw) * rayDistance;

                    from = from.add(-X, -Y, -Z);

                    poseStack.pushPose();
                    poseStack.translate(0.5F, 0.3125F, 0.5F);
                    Color color = new Color(0.886f, 0.811f, 0.549f);
                    LightRayHitResult hitResult = LightUtil.getLightRayHitResult(blockEntity.getLevel(), blockEntity.getBlockPos(), from, to, 25f);
                    LightUtil.renderLightRay(from, hitResult.getPosHit(), hitResult.getDistance() + rayDistance, 25f, color, partialTicks, poseStack);
                    poseStack.popPose();
                }
            }
        }

        if (!CrystalRitualUtil.isEmpty(ritual) && ritual.canStartWithCrystal(blockEntity)) {
            ritual.render(blockEntity, partialTicks, poseStack, bufferSource, light, overlay);
        }

        if (WissenUtil.isCanRenderWissenWand()) {
            if (!CrystalRitualUtil.isEmpty(ritual)) {
                CrystalRitualArea area = ritual.getArea(blockEntity);
                poseStack.pushPose();
                poseStack.translate(-area.getSizeFrom().x(), -area.getSizeFrom().y(), -area.getSizeFrom().z());
                RenderUtil.renderConnectBoxLines(poseStack, new Vec3( area.getSizeFrom().x() + area.getSizeTo().x() + 1, area.getSizeFrom().y() + area.getSizeTo().y() + 1, area.getSizeFrom().z() + area.getSizeTo().z() + 1), WizardsRebornRenderUtil.colorArea, 0.5f);
                poseStack.popPose();
            }

            if (blockEntity.startRitual) {
                poseStack.pushPose();
                RenderUtil.renderConnectBoxLines(poseStack, new Vec3(1, 1, 1), blockEntity.getCrystalColor(), 0.5f);
                poseStack.popPose();
            }

            if (blockEntity.isToBlock) {
                poseStack.pushPose();
                Vec3 lensPos = blockEntity.getLightLensPos();
                poseStack.translate(lensPos.x(), lensPos.y(), lensPos.z());
                BlockPos pos = new BlockPos(blockEntity.blockToX, blockEntity.blockToY, blockEntity.blockToZ);
                if (blockEntity.getLevel().getBlockEntity(pos) instanceof ILightBlockEntity lightTile) {
                    RenderUtil.renderConnectLine(poseStack, LightUtil.getLightLensPos(blockEntity.getBlockPos(), blockEntity.getLightLensPos()), LightUtil.getLightLensPos(pos, lightTile.getLightLensPos()), WizardsRebornRenderUtil.colorConnectTo, 0.5f);
                }
                poseStack.popPose();
            }
        }
    }

    @Override
    public boolean shouldRenderOffScreen(CrystalBlockEntity blockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(CrystalBlockEntity blockEntity, Vec3 cameraPos) {
        return true;
    }
}