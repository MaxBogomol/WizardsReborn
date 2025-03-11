package mod.maxbogomol.wizards_reborn.common.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.common.item.IGuiParticleItem;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import net.minecraft.client.Minecraft;
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
    public void renderParticle(PoseStack poseStack, LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset) {
        int ii = lengthInSeconds;

        float ticks = ((ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick()) + (ii * 10)) * 0.6f;
        float alpha = (float) (0.1f + Math.abs(Math.sin(Math.toRadians(ticks)) * 0.15f));

        if (!isCassette) {
            for (int i = 0; i < 30; i++) {
                poseStack.pushPose();
                float offset = (float) (Math.abs(Math.sin(Math.toRadians(i * 6 + (ticks * 2f)))));
                offset = (offset - 0.25f) * (1 / 0.75f);
                if (offset < 0) offset = 0;
                poseStack.translate(x + 7.5 + (Math.sin(Math.toRadians(i * 12)) * 7), y + 8 + (Math.cos(Math.toRadians(i * 12)) * 4), 100);
                poseStack.mulPose(Axis.ZP.rotationDegrees(ticks + (i * 2f)));
                RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                        .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/sparkle"))
                        .setColor(color).setAlpha(alpha)
                        .renderCenteredQuad(poseStack, 3f * offset)
                        .endBatch();
                poseStack.popPose();
            }
        } else {
            for (int i = 0; i < 30; i++) {
                poseStack.pushPose();
                float offset = (float) (Math.abs(Math.sin(Math.toRadians(i * 6 + (ticks * 2f)))));
                offset = (offset - 0.25f) * (1 / 0.75f);
                if (offset < 0) offset = 0;
                poseStack.translate(x + 8 + (Math.sin(Math.toRadians(i * 12)) * 4), y + 8.5f, 100);
                poseStack.mulPose(Axis.ZP.rotationDegrees(ticks + (i * 2f)));
                RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                        .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/wisp"))
                        .setColor(color).setAlpha(alpha)
                        .renderCenteredQuad(poseStack, 5f * offset)
                        .endBatch();
                poseStack.popPose();
            }
        }
    }
}