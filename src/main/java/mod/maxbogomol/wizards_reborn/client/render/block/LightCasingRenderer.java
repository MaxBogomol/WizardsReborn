package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.client.render.LevelRenderHandler;
import mod.maxbogomol.wizards_reborn.api.light.LightUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.common.block.casing.light.LightCasingBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import mod.maxbogomol.wizards_reborn.util.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.Random;

public class LightCasingRenderer implements BlockEntityRenderer<LightCasingBlockEntity> {

    public LightCasingRenderer() {}

    @Override
    public void render(LightCasingBlockEntity casing, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Random random = new Random();
        random.setSeed(casing.getBlockPos().asLong());

        double ticksAlpha = (ClientTickHandler.ticksInGame + partialTicks);
        float alpha = (float) (0.35f + Math.abs(Math.sin(Math.toRadians(random.nextFloat() * 360f + ticksAlpha)) * 0.3f));

        MultiBufferSource bufferDelayed = LevelRenderHandler.getDelayedRender();

        for (Direction direction : Direction.values()) {
            ms.pushPose();
            ms.translate(0.5F, 0.5F, 0.5F);
            BlockPos pos = new BlockPos(0, 0, 0).relative(direction);
            ms.translate(pos.getX() * casing.getLightLensOffset(), pos.getY() * casing.getLightLensOffset(), pos.getZ() * casing.getLightLensOffset());

            RenderUtils.renderCustomModel(WizardsRebornModels.HOVERING_LENS, ItemDisplayContext.FIXED, false, ms, buffers, light, overlay);

            if (casing.isConnection(direction)) {
                RenderUtils.ray(ms, bufferDelayed, 0.075f, 0.075f, 1f, 0.564f, 0.682f, 0.705f, alpha, 0.564f, 0.682f, 0.705f, alpha);

                if (casing.canWork() && casing.getLight() > 0) {
                    Vec3 from = new Vec3(casing.getBlockPos().getX() + 0.5f + (pos.getX() * casing.getLightLensOffset()), casing.getBlockPos().getY() + 0.5f + (pos.getY() * casing.getLightLensOffset()), casing.getBlockPos().getZ() + 0.5f + (pos.getZ() * casing.getLightLensOffset()));
                    Vec3 to = LightUtil.getLightLensPos(casing.getBlockPos().relative(direction), casing.getLightLensPos());

                    Color color = LightUtil.getRayColorFromLumos(casing.getColor(), casing.getLumos(), casing.getBlockPos(), partialTicks);
                    ms.pushPose();
                    LightUtil.renderLightRay(casing.getLevel(), casing.getBlockPos(), from, to, 25f, color, partialTicks, ms);
                    ms.popPose();
                }

                if (WissenUtils.isCanRenderWissenWand()) {
                    ms.pushPose();
                    ms.translate(-0.2f, -0.2f, -0.2f);
                    RenderUtils.renderBoxLines(new Vec3(0.4f, 0.4f, 0.4f), RenderUtils.colorConnectTo, partialTicks, ms);
                    ms.popPose();
                }
            }
            ms.popPose();
        }
    }

    @Override
    public boolean shouldRenderOffScreen(LightCasingBlockEntity pBlockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(LightCasingBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }
}
