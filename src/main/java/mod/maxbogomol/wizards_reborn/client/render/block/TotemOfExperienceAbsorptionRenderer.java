package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.block.totem.experience_absorption.TotemOfExperienceAbsorptionBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import mod.maxbogomol.wizards_reborn.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class TotemOfExperienceAbsorptionRenderer implements BlockEntityRenderer<TotemOfExperienceAbsorptionBlockEntity> {

    public TotemOfExperienceAbsorptionRenderer() {}

    @Override
    public void render(TotemOfExperienceAbsorptionBlockEntity totem, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Random random = new Random();
        random.setSeed(totem.getBlockPos().asLong());

        Minecraft mc = Minecraft.getInstance();
        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 0.05F;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 3;
        ticksUp = (ticksUp) % 360;

        ms.pushPose();
        ms.translate(0.5F, 0.59375F, 0.5F);
        ms.translate(0F, (Math.sin(Math.toRadians(ticksUp)) * 0.0625F), 0F);
        ms.mulPose(Axis.YP.rotationDegrees((float) ticks * 10));
        ms.mulPose(Axis.YP.rotationDegrees((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticks))) * 7F));
        ms.mulPose(Axis.XP.rotationDegrees((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticks))) * 7F));
        ms.mulPose(Axis.ZP.rotationDegrees((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticks))) * 7F));
        for (int i = 0; i < 4; i += 1) {
            ms.pushPose();
            ms.mulPose(Axis.YP.rotationDegrees(i * 90F));

            float tt = totem.tick - partialTicks;
            if (totem.getExperience() > 0 && totem.getWissen() < totem.getMaxWissen()) {
                tt = totem.tick + partialTicks;
                if (tt > 20) {
                    tt = 20;
                }
            } else if (totem.tick == 0) {
                tt = 0;
            }

            float t = Mth.lerp(tt / 20f, 0.09375F, 0.15625F);
            float size = Mth.lerp(tt / 20f, 1F, 0.5F);

            ms.translate(t, 0F, t);
            ms.scale(size, size, size);
            ms.mulPose(Axis.YP.rotationDegrees(180F));
            RenderUtil.renderCustomModel(WizardsRebornModels.TOTEM_OF_EXPERIENCE_ABSORPTION_PIECE, ItemDisplayContext.FIXED, false, ms, buffers, light, overlay);
            ms.popPose();
        }
        ms.popPose();

        if (WissenUtils.isCanRenderWissenWand()) {
            ms.pushPose();
            ms.translate(-1, -1, -1);
            RenderUtils.renderBoxLines(new Vec3(3, 3, 3), RenderUtils.colorArea, partialTicks, ms);
            ms.popPose();
        }
    }

    @Override
    public boolean shouldRenderOffScreen(TotemOfExperienceAbsorptionBlockEntity pBlockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(TotemOfExperienceAbsorptionBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }
}
