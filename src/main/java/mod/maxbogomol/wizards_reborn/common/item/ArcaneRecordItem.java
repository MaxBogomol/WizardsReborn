package mod.maxbogomol.wizards_reborn.common.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;

public class ArcaneRecordItem extends RecordItem implements IGuiParticleItem {
    public int lengthInSeconds;
    public Color color;
    public boolean isCassette = false;

    public ArcaneRecordItem(int analogOutput, SoundEvent sound, Properties properties, int lengthInSeconds, Color color) {
        super(analogOutput, sound, properties, lengthInSeconds);
        this.lengthInSeconds = lengthInSeconds;
        this.color = color;
    }

    public ArcaneRecordItem setCassette() {
        isCassette = true;
        return this;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderParticle(PoseStack pose, LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset) {
        int ii = lengthInSeconds;

        float ticks = ((ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick()) + (ii * 10)) * 0.6f;
        float alpha = (float) (0.1f + Math.abs(Math.sin(Math.toRadians(ticks)) * 0.15f));
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        RenderUtils.startGuiParticle();
        MultiBufferSource.BufferSource buffersource = Minecraft.getInstance().renderBuffers().bufferSource();

        TextureAtlasSprite sparkle = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation(WizardsReborn.MOD_ID, "particle/sparkle"));
        TextureAtlasSprite wisp = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation(WizardsReborn.MOD_ID, "particle/wisp"));

        if (!isCassette) {
            for (int i = 0; i < 30; i++) {
                pose.pushPose();
                float offset = (float) (Math.abs(Math.sin(Math.toRadians(i * 6 + (ticks * 2f)))));
                offset = (offset - 0.25f) * (1 / 0.75f);
                if (offset < 0) offset = 0;
                pose.translate(x + 7.5 + (Math.sin(Math.toRadians(i * 12)) * 7), y + 8 + (Math.cos(Math.toRadians(i * 12)) * 4), 100);
                pose.mulPose(Axis.ZP.rotationDegrees(ticks + (i * 2f)));
                RenderUtils.spriteGlowQuadCenter(pose, buffersource, 0, 0, 6f * offset, 6f * offset, sparkle.getU0(), sparkle.getU1(), sparkle.getV0(), sparkle.getV1(), r, g, b, alpha);
                buffersource.endBatch();
                pose.popPose();
            }
        } else {
            for (int i = 0; i < 30; i++) {
                pose.pushPose();
                float offset = (float) (Math.abs(Math.sin(Math.toRadians(i * 6 + (ticks * 2f)))));
                offset = (offset - 0.25f) * (1 / 0.75f);
                if (offset < 0) offset = 0;
                pose.translate(x + 7.5 + (Math.sin(Math.toRadians(i * 12)) * 4), y + 8.5f, 100);
                pose.mulPose(Axis.ZP.rotationDegrees(ticks + (i * 2f)));
                RenderUtils.spriteGlowQuadCenter(pose, buffersource, 0, 0, 10f * offset, 10f * offset, wisp.getU0(), wisp.getU1(), wisp.getV0(), wisp.getV1(), r, g, b, alpha);
                buffersource.endBatch();
                pose.popPose();
            }
        }

        RenderUtils.endGuiParticle();
    }
}