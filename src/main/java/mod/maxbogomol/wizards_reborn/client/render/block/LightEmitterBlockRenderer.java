package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.api.light.ILightBlockEntity;
import mod.maxbogomol.wizards_reborn.api.light.LightUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.block.light_emitter.LightEmitterBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import mod.maxbogomol.wizards_reborn.util.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.Random;

public class LightEmitterBlockRenderer implements BlockEntityRenderer<LightEmitterBlockEntity> {

    public LightEmitterBlockRenderer() {}

    @Override
    public void render(LightEmitterBlockEntity emitter, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Random random = new Random();
        random.setSeed(emitter.getBlockPos().asLong());

        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 0.4f;
        double ticksAlpha = (ClientTickHandler.ticksInGame + partialTicks);
        float alpha = (float) (0.35f + Math.abs(Math.sin(Math.toRadians(random.nextFloat() * 360f + ticksAlpha)) * 0.3f));

        MultiBufferSource bufferDelayed = FluffyFurRenderTypes.getDelayedRender();

        ms.pushPose();
        ms.translate(0.5F, 0.8125F, 0.5F);
        ms.mulPose(Axis.YP.rotationDegrees((float) (random.nextFloat() * 360 + ticks)));
        ms.mulPose(Axis.XP.rotationDegrees((float) (random.nextFloat() * 360 + ticks)));
        ms.mulPose(Axis.ZP.rotationDegrees((float) (random.nextFloat() * 360 + ticks)));
        RenderUtil.renderCustomModel(WizardsRebornModels.HOVERING_LENS, ItemDisplayContext.FIXED, false, ms, buffers, light, overlay);
        RenderUtils.ray(ms, bufferDelayed, 0.075f, 0.075f, 1f, 0.564f, 0.682f, 0.705f, alpha, 0.564f, 0.682f, 0.705f, alpha);
        ms.popPose();


        if (emitter.isToBlock && emitter.getLight() > 0) {
            BlockPos pos = new BlockPos(emitter.blockToX, emitter.blockToY, emitter.blockToZ);
            if (emitter.getLevel().getBlockEntity(pos) instanceof ILightBlockEntity lightTile) {
                Vec3 from = LightUtil.getLightLensPos(emitter.getBlockPos(), emitter.getLightLensPos());
                Vec3 to = LightUtil.getLightLensPos(pos, lightTile.getLightLensPos());

                ms.pushPose();
                ms.translate(0.5F, 0.8125F, 0.5F);
                Color color = LightUtil.getRayColorFromLumos(emitter.getRayColor(), emitter.getLumos(), emitter.getBlockPos(), partialTicks);
                LightUtil.renderLightRay(emitter.getLevel(), emitter.getBlockPos(), from, to, 25f, color, partialTicks, ms);
                ms.popPose();
            }
        }

        if (WissenUtils.isCanRenderWissenWand()) {
            if (emitter.isToBlock) {
                ms.pushPose();
                Vec3 lensPos = emitter.getLightLensPos();
                ms.translate(lensPos.x(), lensPos.y(), lensPos.z());
                BlockPos pos = new BlockPos(emitter.blockToX, emitter.blockToY, emitter.blockToZ);
                if (emitter.getLevel().getBlockEntity(pos) instanceof ILightBlockEntity lightTile) {
                    RenderUtils.renderConnectLine(LightUtil.getLightLensPos(emitter.getBlockPos(), emitter.getLightLensPos()), LightUtil.getLightLensPos(pos, lightTile.getLightLensPos()), RenderUtils.colorConnectTo, partialTicks, ms);
                }
                ms.popPose();
            }
        }
    }

    @Override
    public boolean shouldRenderOffScreen(LightEmitterBlockEntity pBlockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(LightEmitterBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }
}
