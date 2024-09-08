package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualArea;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualUtil;
import mod.maxbogomol.wizards_reborn.api.light.ILightBlockEntity;
import mod.maxbogomol.wizards_reborn.api.light.LightRayHitResult;
import mod.maxbogomol.wizards_reborn.api.light.LightUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.block.crystal.CrystalBlockEntity;
import mod.maxbogomol.wizards_reborn.common.item.CrystalItem;
import mod.maxbogomol.wizards_reborn.util.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.Random;

public class CrystalRenderer implements BlockEntityRenderer<CrystalBlockEntity> {

    public CrystalRenderer() {}

    @Override
    public void render(CrystalBlockEntity crystal, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Random random = new Random();
        random.setSeed(crystal.getBlockPos().asLong());

        double ticksAlpha = (ClientTickHandler.ticksInGame + partialTicks);
        float alpha = (float) (0.15f + Math.abs(Math.sin(Math.toRadians(random.nextFloat() * 360f + ticksAlpha)) * 0.05f));

        MultiBufferSource bufferDelayed = FluffyFurRenderTypes.getDelayedRender();

        CrystalRitual ritual = crystal.getCrystalRitual();

        if (crystal.getLight() > 0) {
            if (!crystal.getItemHandler().getItem(0).isEmpty()) {
                if (crystal.getItemHandler().getItem(0).getItem() instanceof CrystalItem crystalItem) {
                    Color color = crystalItem.getType().getColor();
                    float r = color.getRed() / 255f;
                    float g = color.getGreen() / 255f;
                    float b = color.getBlue() / 255f;

                    ms.pushPose();
                    ms.translate(0.5F, 0.3825F, 0.5F);
                    ms.mulPose(Axis.ZP.rotationDegrees(-90f));
                    RenderUtils.ray(ms, bufferDelayed, 0.2f, 0.4f, 1f, r, g, b, alpha);
                    ms.popPose();
                }
            }

            if (crystal.isToBlock && !CrystalRitualUtil.isEmpty(ritual) && ritual.hasLightRay(crystal)) {
                BlockPos pos = new BlockPos(crystal.blockToX, crystal.blockToY, crystal.blockToZ);
                if (crystal.getLevel().getBlockEntity(pos) instanceof ILightBlockEntity lightTile) {
                    Vec3 from = LightUtil.getLightLensPos(crystal.getBlockPos(), crystal.getLightLensPos());
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

                    ms.pushPose();
                    ms.translate(0.5F, 0.3125F, 0.5F);
                    Color color = new Color(0.886f, 0.811f, 0.549f);
                    LightRayHitResult hitResult = LightUtil.getLightRayHitResult(crystal.getLevel(), crystal.getBlockPos(), from, to, 25f);
                    LightUtil.renderLightRay(from, hitResult.getPosHit(), hitResult.getDistance() + rayDistance, 25f, color, partialTicks, ms);
                    ms.popPose();
                }
            }
        }

        if (!CrystalRitualUtil.isEmpty(ritual) && ritual.canStartWithCrystal(crystal)) {
            ritual.render(crystal, partialTicks, ms, buffers, light, overlay);
        }

        if (WissenUtils.isCanRenderWissenWand()) {
            if (!CrystalRitualUtil.isEmpty(ritual)) {
                CrystalRitualArea area = ritual.getArea(crystal);
                ms.pushPose();
                ms.translate(-area.getSizeFrom().x(), -area.getSizeFrom().y(), -area.getSizeFrom().z());
                RenderUtils.renderBoxLines(new Vec3( area.getSizeFrom().x() + area.getSizeTo().x() + 1, area.getSizeFrom().y() + area.getSizeTo().y() + 1, area.getSizeFrom().z() + area.getSizeTo().z() + 1), RenderUtils.colorArea, partialTicks, ms);
                ms.popPose();
            }

            if (crystal.startRitual) {
                ms.pushPose();
                RenderUtils.renderBoxLines(new Vec3(1, 1, 1), crystal.getCrystalColor(), partialTicks, ms);
                ms.popPose();
            }

            if (crystal.isToBlock) {
                ms.pushPose();
                Vec3 lensPos = crystal.getLightLensPos();
                ms.translate(lensPos.x(), lensPos.y(), lensPos.z());
                BlockPos pos = new BlockPos(crystal.blockToX, crystal.blockToY, crystal.blockToZ);
                if (crystal.getLevel().getBlockEntity(pos) instanceof ILightBlockEntity lightTile) {
                    RenderUtils.renderConnectLine(LightUtil.getLightLensPos(crystal.getBlockPos(), crystal.getLightLensPos()), LightUtil.getLightLensPos(pos, lightTile.getLightLensPos()), RenderUtils.colorConnectTo, partialTicks, ms);
                }
                ms.popPose();
            }
        }
    }

    @Override
    public boolean shouldRenderOffScreen(CrystalBlockEntity pBlockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(CrystalBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }
}