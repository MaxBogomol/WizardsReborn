package mod.maxbogomol.wizards_reborn.common.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.common.item.IGuiParticleItem;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.common.block.salt_torch.SaltTorchBlockEntity;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;

public class SaltLanternItem extends BlockItem implements IGuiParticleItem {
    public SaltLanternItem(Block blockIn, Properties properties) {
        super(blockIn, properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderParticle(PoseStack pose, LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset) {
        float ticks = ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick();
        float offset = (float) (0.85f + Math.abs(Math.sin(Math.toRadians(ticks * 1.6f)) * 0.15f));
        float offsetW = (float) (0.75f + Math.abs(Math.sin(Math.toRadians(ticks * 0.6f)) * 0.25f));

        Color color1 = SaltTorchBlockEntity.colorFirst;
        float r1 = color1.getRed() / 255f;
        float g1 = color1.getGreen() / 255f;
        float b1 = color1.getBlue() / 255f;
        Color color2 = SaltTorchBlockEntity.colorSecond;
        float r2 = color2.getRed() / 255f;
        float g2 = color2.getGreen() / 255f;
        float b2 = color2.getBlue() / 255f;

        RenderUtils.startGuiParticle();
        MultiBufferSource.BufferSource buffersource = Minecraft.getInstance().renderBuffers().bufferSource();

        TextureAtlasSprite sparkle = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation(WizardsReborn.MOD_ID, "particle/sparkle"));
        TextureAtlasSprite wisp = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation(WizardsReborn.MOD_ID, "particle/wisp"));

        pose.pushPose();
        pose.translate(x + 8, y + 10, 100);
        pose.mulPose(Axis.ZP.rotationDegrees(ticks));
        RenderUtils.spriteGlowQuadCenter(pose, buffersource, 0, 0, 12f * offset, 12f * offset, sparkle.getU0(), sparkle.getU1(), sparkle.getV0(), sparkle.getV1(), r1, g1, b1, 0.5F);
        buffersource.endBatch();
        pose.popPose();

        pose.pushPose();
        pose.translate(x + 8, y + 10, 100);
        pose.mulPose(Axis.ZP.rotationDegrees(ticks + 45));
        RenderUtils.spriteGlowQuadCenter(pose, buffersource, 0, 0, 12f * offset, 12f * offset, sparkle.getU0(), sparkle.getU1(), sparkle.getV0(), sparkle.getV1(), r2, g2, b2, 0.5F);
        buffersource.endBatch();
        pose.popPose();

        pose.pushPose();
        pose.translate(x + 8, y + 10, 100);
        pose.mulPose(Axis.ZP.rotationDegrees(ticks));
        RenderUtils.spriteGlowQuadCenter(pose, buffersource, 0, 0, 10f * offsetW, 10f * offsetW, wisp.getU0(), wisp.getU1(), wisp.getV0(), wisp.getV1(), r1, g1, b1, 0.15F);
        buffersource.endBatch();
        pose.popPose();

        pose.pushPose();
        pose.translate(x + 8, y + 10, 100);
        pose.mulPose(Axis.ZP.rotationDegrees(ticks + 45));
        RenderUtils.spriteGlowQuadCenter(pose, buffersource, 0, 0, 10f * offsetW, 10f * offsetW, wisp.getU0(), wisp.getU1(), wisp.getV0(), wisp.getV1(), r2, g2, b2, 0.15F);
        buffersource.endBatch();
        pose.popPose();

        RenderUtils.endGuiParticle();
    }
}