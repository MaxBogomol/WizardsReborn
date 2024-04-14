package mod.maxbogomol.wizards_reborn.common.item;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.common.tileentity.SaltTorchTileEntity;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.awt.*;

public class SaltTorchItem extends StandingAndWallBlockItem implements IGuiParticleItem {
    public SaltTorchItem(Block block, Block wallBlock, Item.Properties properties, Direction attachmentDirection) {
        super(block, wallBlock, properties, attachmentDirection);
    }

    @Override
    public void renderParticle(PoseStack pose, LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset) {
        float ticks = ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick();
        float offset = (float) (0.85f + Math.abs(Math.sin(Math.toRadians(ticks * 1.6f)) * 0.15f));
        float offsetW = (float) (0.75f + Math.abs(Math.sin(Math.toRadians(ticks * 0.6f)) * 0.25f));

        Color color1 = SaltTorchTileEntity.colorFirst;
        float r1 = color1.getRed() / 255f;
        float g1 = color1.getGreen() / 255f;
        float b1 = color1.getBlue() / 255f;
        Color color2 = SaltTorchTileEntity.colorSecond;
        float r2 = color2.getRed() / 255f;
        float g2 = color2.getGreen() / 255f;
        float b2 = color2.getBlue() / 255f;

        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        MultiBufferSource.BufferSource buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(WizardsRebornClient::getGlowingShader);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

        TextureAtlasSprite sparkle = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation(WizardsReborn.MOD_ID, "particle/sparkle"));
        TextureAtlasSprite wisp = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation(WizardsReborn.MOD_ID, "particle/wisp"));

        pose.pushPose();
        pose.translate(x + 8, y + 6, 100);
        pose.mulPose(Axis.ZP.rotationDegrees(ticks));
        pose.translate(-5 * offset, -5 * offset, 0);
        RenderUtils.spriteGlowQuad(pose, buffersource, 0, 0, 10f * offset, 10f * offset, sparkle.getU0(), sparkle.getU1(), sparkle.getV0(), sparkle.getV1(), r1, g1, b1, 0.5F);
        buffersource.endBatch();
        pose.popPose();

        pose.pushPose();
        pose.translate(x + 8, y + 6, 100);
        pose.mulPose(Axis.ZP.rotationDegrees(ticks + 45));
        pose.translate(-5 * offset, -5 * offset, 0);
        RenderUtils.spriteGlowQuad(pose, buffersource, 0, 0, 10f * offset, 10f * offset, sparkle.getU0(), sparkle.getU1(), sparkle.getV0(), sparkle.getV1(), r2, g2, b2, 0.5F);
        buffersource.endBatch();
        pose.popPose();

        pose.pushPose();
        pose.translate(x + 8, y + 6, 100);
        pose.mulPose(Axis.ZP.rotationDegrees(ticks));
        pose.translate(-4 * offsetW, -4 * offsetW, 0);
        RenderUtils.spriteGlowQuad(pose, buffersource, 0, 0, 8f * offsetW, 8f * offsetW, wisp.getU0(), wisp.getU1(), wisp.getV0(), wisp.getV1(), r1, g1, b1, 0.15F);
        buffersource.endBatch();
        pose.popPose();

        pose.pushPose();
        pose.translate(x + 8, y + 6, 100);
        pose.mulPose(Axis.ZP.rotationDegrees(ticks + 45));
        pose.translate(-4 * offsetW, -4 * offsetW, 0);
        RenderUtils.spriteGlowQuad(pose, buffersource, 0, 0, 8f * offsetW, 8f * offsetW, wisp.getU0(), wisp.getU1(), wisp.getV0(), wisp.getV1(), r2, g2, b2, 0.15F);
        buffersource.endBatch();
        pose.popPose();

        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
    }
}