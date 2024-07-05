package mod.maxbogomol.wizards_reborn.client.render.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.common.tileentity.AltarOfDroughtTileEntity;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class AltarOfDroughtTileEntityRenderer implements BlockEntityRenderer<AltarOfDroughtTileEntity> {

    public AltarOfDroughtTileEntityRenderer() {}

    @Override
    public void render(AltarOfDroughtTileEntity altar, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Random random = new Random();
        random.setSeed(altar.getBlockPos().asLong());

        Minecraft mc = Minecraft.getInstance();
        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 0.1F;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
        ticksUp = (ticksUp) % 360;

        ms.pushPose();
        ms.translate(0.5F, 0.203125F, 0.5F);
        ms.mulPose(Axis.YP.rotationDegrees(altar.getBlockRotate()));
        ms.mulPose(Axis.XP.rotationDegrees(90F));
        ms.scale(0.5F,0.5F,0.5F);
        mc.getItemRenderer().renderStatic(altar.getItemHandler().getItem(0), ItemDisplayContext.FIXED, light, overlay, ms, buffers, altar.getLevel(), 0);
        ms.popPose();

        ms.pushPose();
        ms.translate(0.5F, 0.625F + (Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0.5F);
        ms.mulPose(Axis.YP.rotationDegrees((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticks))) * 5F));
        ms.mulPose(Axis.XP.rotationDegrees((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticks))) * 5F));
        ms.mulPose(Axis.ZP.rotationDegrees((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticks))) * 5F));
        RenderUtils.renderCustomModel(WizardsRebornClient.ALTAR_OF_DROUGHT_FRAME_MODEL, ItemDisplayContext.FIXED, false, ms, buffers, light, overlay);
        ms.popPose();

        if (WissenUtils.isCanRenderWissenWand()) {
            ms.pushPose();
            ms.translate(-15, -15, -15);
            RenderUtils.renderBoxLines(new Vec3(31, 31, 31), RenderUtils.colorArea, partialTicks, ms);
            ms.popPose();
        }
    }

    @Override
    public boolean shouldRenderOffScreen(AltarOfDroughtTileEntity pBlockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(AltarOfDroughtTileEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }
}
