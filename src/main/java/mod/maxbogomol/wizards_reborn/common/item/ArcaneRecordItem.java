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

        poseStack.pushPose();
        poseStack.translate(x + (isCassette ? 8f : 7.5f), y + (isCassette ? 8.5f : 8f), 100);
        poseStack.mulPose(Axis.ZP.rotationDegrees(ticks));
        RenderBuilder sparkleBuilder = RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/star"))
                .setColor(color).setAlpha(0.5f)
                .renderCenteredQuad(poseStack, isCassette ? 6f : 8f);
        poseStack.mulPose(Axis.ZP.rotationDegrees(22.5f));
        sparkleBuilder.renderCenteredQuad(poseStack, isCassette ? 6f : 8f);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(x + (isCassette ? 8f : 7.5f), y + (isCassette ? 8.5f : 8f), 100);
        poseStack.mulPose(Axis.ZP.rotationDegrees(-ticks));
        sparkleBuilder.setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/wisp"))
                .renderCenteredQuad(poseStack, isCassette ? 7f : 8f)
                .endBatch();
        poseStack.popPose();
    }
}