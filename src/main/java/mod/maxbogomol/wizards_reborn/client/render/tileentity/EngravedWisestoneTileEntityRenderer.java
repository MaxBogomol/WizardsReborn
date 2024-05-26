package mod.maxbogomol.wizards_reborn.client.render.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.api.monogram.Monogram;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.client.render.WorldRenderHandler;
import mod.maxbogomol.wizards_reborn.common.block.EngravedWisestoneBlock;
import mod.maxbogomol.wizards_reborn.common.tileentity.EngravedWisestoneTileEntity;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import java.awt.*;
import java.util.Random;

public class EngravedWisestoneTileEntityRenderer implements BlockEntityRenderer<EngravedWisestoneTileEntity> {

    public EngravedWisestoneTileEntityRenderer() {}

    @Override
    public void render(EngravedWisestoneTileEntity tile, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        MultiBufferSource.BufferSource buffersource = WorldRenderHandler.getDelayedRender();
        float ticks = ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick() * 0.1f;

        Random random = new Random();
        random.setSeed(tile.getBlockPos().asLong());

        if (tile.glowTicks > 0 && tile.getBlockState().getBlock() instanceof EngravedWisestoneBlock block && block.hasMonogram()) {
            Monogram monogram = block.getMonogram();
            Color color = monogram.getColor();
            float r = color.getRed() / 255f;
            float g = color.getGreen() / 255f;
            float b = color.getBlue() / 255f;

            TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(monogram.getTexture());

            float width = 1f;
            float alpha = (tile.glowTicks + partialTicks) / 20f;
            if (!tile.glow) alpha = (tile.glowTicks - partialTicks) / 20f;
            if (alpha > 1f) alpha = 1f;
            if (alpha < 0f) alpha = 0f;
            float offset = 1f + ((1f - alpha) * 10f);

            ms.pushPose();
            ms.translate(0.5f, 0.5f, 0.5f);
            ms.mulPose(Axis.YP.rotationDegrees(tile.getHorizontalBlockRotate()));
            ms.mulPose(Axis.XP.rotationDegrees(tile.getVerticalBlockRotate()));
            if (tile.getVerticalBlockRotate() == 0) ms.mulPose(Axis.ZP.rotationDegrees(180f));
            ms.translate(0f, 0f, -0.501f);
            RenderUtils.spriteGlowQuadCenter(ms, buffersource, 0, 0, 0.5f, 0.5f, sprite.getU0(), sprite.getU1(), sprite.getV0(), sprite.getV1(), 1, 1, 1, 0.5f * alpha);

            for (int i = 0; i < 5; i++) {
                ms.translate(0, 0, -0.005f * offset);
                ms.mulPose(Axis.ZP.rotationDegrees((float) Math.sin(Math.toRadians(((random.nextFloat() * 360) + ticks))) * offset));
                ms.mulPose(Axis.XP.rotationDegrees((float) Math.sin(Math.toRadians(((random.nextFloat() * 360) + ticks))) * offset));
                ms.mulPose(Axis.YP.rotationDegrees((float) Math.sin(Math.toRadians(((random.nextFloat() * 360) + ticks))) * offset));
                RenderUtils.spriteGlowQuadCenter(ms, buffersource, 0, 0, 0.5f * width, 0.5f * width, sprite.getU0(), sprite.getU1(), sprite.getV0(), sprite.getV1(), r, g, b, 0.2f * alpha);

                ms.pushPose();
                ms.mulPose(Axis.YP.rotationDegrees(180f));
                RenderUtils.spriteGlowQuadCenter(ms, buffersource, 0, 0, 0.5f * width, 0.5f * width, sprite.getU1(), sprite.getU0(), sprite.getV0(), sprite.getV1(), r, g, b, 0.2f * alpha);
                ms.popPose();

                width = width + 0.1f;
            }

            ms.popPose();
        }
    }
}
