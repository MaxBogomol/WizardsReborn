package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.api.monogram.Monogram;
import mod.maxbogomol.wizards_reborn.common.block.engraved_wisestone.EngravedWisestoneBlock;
import mod.maxbogomol.wizards_reborn.common.block.engraved_wisestone.EngravedWisestoneBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.Random;

public class EngravedWisestoneRenderer implements BlockEntityRenderer<EngravedWisestoneBlockEntity> {

    public EngravedWisestoneRenderer() {}

    @Override
    public void render(EngravedWisestoneBlockEntity tile, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        MultiBufferSource bufferDelayed = FluffyFurRenderTypes.getDelayedRender();
        float ticks = ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick() * 0.1f;

        Random random = new Random();
        random.setSeed(tile.getBlockPos().asLong());

        if (tile.glowTicks > 0 && tile.getBlockState().getBlock() instanceof EngravedWisestoneBlock block && block.hasMonogram()) {
            Monogram monogram = block.getMonogram();
            Color color = monogram.getColor();

            TextureAtlasSprite sprite = RenderUtil.getSprite(monogram.getTexture());

            float width = 1f;
            float alpha = (tile.glowTicks + partialTicks) / 20f;
            if (!tile.glow) alpha = (tile.glowTicks - partialTicks) / 20f;
            if (alpha > 1f) alpha = 1f;
            if (alpha < 0f) alpha = 0f;
            float offset = 1f + ((1f - alpha) * 10f);
            float rotateOffset = offset / 2f;

            ms.pushPose();
            ms.translate(0.5f, 0.5f, 0.5f);
            ms.mulPose(Axis.YP.rotationDegrees(tile.getHorizontalBlockRotate()));
            ms.mulPose(Axis.XP.rotationDegrees(tile.getVerticalBlockRotate()));
            if (tile.getVerticalBlockRotate() == 0) ms.mulPose(Axis.ZP.rotationDegrees(180f));
            ms.translate(0f, 0f, -0.505f);
            RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                    .setUV(sprite)
                    .setColor(Color.WHITE).setAlpha(0.5f * alpha)
                    .renderCenteredQuad(ms, 0.25f);

            for (int i = 0; i < 5; i++) {
                ms.translate(0, 0, -0.005f * offset);
                ms.mulPose(Axis.ZP.rotationDegrees((float) Math.sin(Math.toRadians(((random.nextFloat() * 360) + ticks))) * rotateOffset));
                ms.mulPose(Axis.XP.rotationDegrees((float) Math.sin(Math.toRadians(((random.nextFloat() * 360) + ticks))) * rotateOffset));
                ms.mulPose(Axis.YP.rotationDegrees((float) Math.sin(Math.toRadians(((random.nextFloat() * 360) + ticks))) * rotateOffset));
                RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                        .enableSided()
                        .setUV(sprite)
                        .setColor(color).setAlpha(0.2f * alpha)
                        .renderWavyQuad(ms, 0.25f * width, 0.02f * offset, ticks + (random.nextFloat() * 100));
                width = width + 0.1f;
            }

            ms.popPose();
        }
    }

    @Override
    public boolean shouldRenderOffScreen(EngravedWisestoneBlockEntity pBlockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(EngravedWisestoneBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }
}
