package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.common.block.jeweler_table.JewelerTableBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import mod.maxbogomol.wizards_reborn.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

public class JewelerTableRenderer implements BlockEntityRenderer<JewelerTableBlockEntity> {

    public JewelerTableRenderer() {}

    @Override
    public void render(JewelerTableBlockEntity table, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Minecraft mc = Minecraft.getInstance();
        Vec3 pos = table.getBlockRotatePos();
        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 2;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
        ticksUp = (ticksUp) % 360;


        double ticksStone = table.stoneRotate;
        if (table.stoneSpeed > 0) {
            ticksStone = (table.stoneRotate + ((partialTicks) * table.stoneSpeed));
        }

        ms.pushPose();
        ms.translate(pos.x(), pos.y(), pos.z());
        ms.mulPose(Axis.YP.rotationDegrees(table.getBlockRotate()));
        ms.mulPose(Axis.XP.rotationDegrees((float) ticksStone));
        RenderUtils.renderCustomModel(WizardsRebornModels.JEWELER_TABLE_STONE, ItemDisplayContext.FIXED, false, ms, buffers, light, overlay);
        ms.popPose();

        ms.pushPose();
        ms.translate(0.5F, 0.703125F, 0.5F);
        ms.mulPose(Axis.YP.rotationDegrees(table.getBlockRotate()));
        ms.mulPose(Axis.XP.rotationDegrees(90F));
        ms.mulPose(Axis.ZP.rotationDegrees(-3F));
        ms.translate(0, -0.0725, 0);
        ms.scale(0.5F,0.5F,0.5F);
        mc.getItemRenderer().renderStatic(table.itemHandler.getStackInSlot(0), ItemDisplayContext.FIXED, light, overlay, ms, buffers, table.getLevel(), 0);
        ms.popPose();

        ms.pushPose();
        ms.translate(0.5F, 0.703125F + 0.03125F, 0.5F);
        ms.mulPose(Axis.YP.rotationDegrees(table.getBlockRotate()));
        ms.mulPose(Axis.XP.rotationDegrees(90F + 5F));
        ms.mulPose(Axis.ZP.rotationDegrees(15F));
        ms.translate(0.125F, -0.0625F, 0);
        ms.scale(0.5F,0.5F,0.5F);
        mc.getItemRenderer().renderStatic(table.itemHandler.getStackInSlot(1), ItemDisplayContext.FIXED, light, overlay, ms, buffers, table.getLevel(), 0);
        ms.popPose();

        ms.pushPose();
        ms.translate(0.5F, 1F, 0.5F);
        ms.mulPose(Axis.YP.rotationDegrees(table.getBlockRotate() + 90F));
        ms.translate(0.125F, 0, 0);
        ms.translate(0F, (float) (Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0F);
        ms.mulPose(Axis.YP.rotationDegrees((float) ticks));
        ms.scale(0.5F, 0.5F, 0.5F);
        mc.getItemRenderer().renderStatic(table.itemOutputHandler.getStackInSlot(0), ItemDisplayContext.FIXED, light, overlay, ms, buffers, table.getLevel(), 0);
        ms.popPose();
    }
}
