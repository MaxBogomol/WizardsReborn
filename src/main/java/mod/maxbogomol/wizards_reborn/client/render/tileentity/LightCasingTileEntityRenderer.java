package mod.maxbogomol.wizards_reborn.client.render.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.api.light.LightUtils;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.client.render.WorldRenderHandler;
import mod.maxbogomol.wizards_reborn.common.tileentity.LightCasingTileEntity;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class LightCasingTileEntityRenderer implements BlockEntityRenderer<LightCasingTileEntity> {

    public LightCasingTileEntityRenderer() {}

    @Override
    public void render(LightCasingTileEntity casing, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Random random = new Random();
        random.setSeed(casing.getBlockPos().asLong());

        double ticksAlpha = (ClientTickHandler.ticksInGame + partialTicks);
        float alpha = (float) (0.35f + Math.abs(Math.sin(Math.toRadians(random.nextFloat() * 360f + ticksAlpha)) * 0.3f));

        MultiBufferSource bufferDelayed = WorldRenderHandler.getDelayedRender();

        for (Direction direction : Direction.values()) {
            ms.pushPose();
            ms.translate(0.5F, 0.5F, 0.5F);
            BlockPos pos = new BlockPos(0, 0, 0).relative(direction);
            ms.translate(pos.getX() * 0.4375f, pos.getY() * 0.4375f, pos.getZ() * 0.4375f);

            RenderUtils.renderCustomModel(WizardsRebornClient.HOVERING_LENS_MODEl, ItemDisplayContext.FIXED, false, ms, buffers, light, overlay);

            if (casing.isConnection(direction)) {
                RenderUtils.ray(ms, bufferDelayed, 0.075f, 0.075f, 1f, 0.564f, 0.682f, 0.705f, alpha, 0.564f, 0.682f, 0.705f, alpha);

                if (casing.canWork() && casing.getLight() > 0) {
                    Vec3 from = new Vec3(casing.getBlockPos().getX() + 0.5f + (pos.getX() * 0.4375f), casing.getBlockPos().getY() + 0.5f + (pos.getY() * 0.4375f), casing.getBlockPos().getZ() + 0.5f + (pos.getZ() * 0.4375f));
                    Vec3 to = LightUtils.getLightLensPos(casing.getBlockPos().relative(direction), casing.getLightLensPos());

                    LightUtils.renderLightRay(casing.getLevel(), casing.getBlockPos(), from, to, 25f, casing.getColor(), partialTicks, ms);
                }
            }
            ms.popPose();
        }
    }

    @Override
    public boolean shouldRenderOffScreen(LightCasingTileEntity pBlockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(LightCasingTileEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }
}