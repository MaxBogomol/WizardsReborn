package mod.maxbogomol.wizards_reborn.client.render.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.common.tileentity.TotemOfExperienceAbsorptionTileEntity;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class TotemOfExperienceAbsorptionTileEntityRenderer implements BlockEntityRenderer<TotemOfExperienceAbsorptionTileEntity> {

    public TotemOfExperienceAbsorptionTileEntityRenderer() {}

    @Override
    public void render(TotemOfExperienceAbsorptionTileEntity totem, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
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
            RenderUtils.renderCustomModel(WizardsRebornClient.TOTEM_OF_EXPERIENCE_ABSORPTION_PIECE_MODEL, ItemDisplayContext.FIXED, false, ms, buffers, light, overlay);
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
    public boolean shouldRenderOffScreen(TotemOfExperienceAbsorptionTileEntity pBlockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(TotemOfExperienceAbsorptionTileEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }
}
