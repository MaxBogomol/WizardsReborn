package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.render.LevelRenderHandler;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.block.totem.experience_totem.ExperienceTotemBlockEntity;
import mod.maxbogomol.wizards_reborn.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class ExperienceTotemRenderer implements BlockEntityRenderer<ExperienceTotemBlockEntity> {

    public ExperienceTotemRenderer() {}

    @Override
    public void render(ExperienceTotemBlockEntity totem, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Random random = new Random();
        random.setSeed(totem.getBlockPos().asLong());

        Minecraft mc = Minecraft.getInstance();
        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 0.5F;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 3;
        ticksUp = (ticksUp) % 360;

        double ticksAlpha = (ClientTickHandler.ticksInGame + partialTicks);
        float alpha = (float) (0.45f + Math.abs(Math.sin(Math.toRadians(random.nextFloat() * 360f + ticksAlpha)) * 0.2f));

        float globalOffset = (float) Math.abs(Math.sin(Math.toRadians(random.nextFloat() * 360f + (ticksAlpha / 3))));
        float offset = (float) (0.55f + Math.abs(Math.sin(Math.toRadians(random.nextFloat() * 360f + ticksAlpha * 2)) * 0.45f));

        if (totem.getExperience() > 0) {
            float size = (0.85f + (0.15f * globalOffset)) * ((float) totem.getExperience() / totem.getMaxExperience());

            MultiBufferSource bufferDelayed = LevelRenderHandler.getDelayedRender();

            ms.pushPose();
            ms.translate(0.5F, 0.75F, 0.5F);
            ms.translate(0F, (Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0F);
            ms.mulPose(Axis.YP.rotationDegrees((float) (random.nextFloat() * 360 + ticks)));
            ms.mulPose(Axis.XP.rotationDegrees((float) (random.nextFloat() * 360 + ticks)));
            ms.mulPose(Axis.ZP.rotationDegrees((float) (random.nextFloat() * 360 + ticks)));
            ms.scale(size, size, size);
            RenderUtils.ray(ms, bufferDelayed, 0.06f, 0.06f, 1f, 0.243f, 0.564f, 0.250f, 0.75f);
            RenderUtils.ray(ms, bufferDelayed, 0.075f, 0.075f, 1f, 0.243f, 0.564f, 0.250f, alpha);
            RenderUtils.ray(ms, bufferDelayed, 0.1f, 0.1f, 1f, 0.784f, 1f, 0.560f, alpha / 2);
            RenderUtils.ray(ms, bufferDelayed, 0.12f * offset, 0.12f * offset, 1f, 0.960f, 1f, 0.560f, 0.2f);
            ms.popPose();
        }

        if (WissenUtils.isCanRenderWissenWand()) {
            ms.pushPose();
            ms.translate(-1, -1, -1);
            RenderUtils.renderBoxLines(new Vec3(3, 3, 3), RenderUtils.colorArea, partialTicks, ms);
            ms.popPose();
        }
    }

    @Override
    public boolean shouldRenderOffScreen(ExperienceTotemBlockEntity pBlockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(ExperienceTotemBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }
}
