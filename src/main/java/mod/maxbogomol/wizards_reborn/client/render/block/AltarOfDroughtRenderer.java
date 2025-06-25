package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.common.block.altar_of_drought.AltarOfDroughtBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import mod.maxbogomol.wizards_reborn.util.WizardsRebornRenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class AltarOfDroughtRenderer implements BlockEntityRenderer<AltarOfDroughtBlockEntity> {

    @Override
    public void render(AltarOfDroughtBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Random random = new Random();
        random.setSeed(blockEntity.getBlockPos().asLong());

        Minecraft minecraft = Minecraft.getInstance();
        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 0.1F;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
        ticksUp = (ticksUp) % 360;

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.203125F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getBlockRotate()));
        poseStack.mulPose(Axis.XP.rotationDegrees(90F));
        poseStack.scale(0.5F,0.5F,0.5F);
        minecraft.getItemRenderer().renderStatic(blockEntity.getItemHandler().getItem(0), ItemDisplayContext.NONE, light, overlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.625F + (Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticks))) * 5F));
        poseStack.mulPose(Axis.XP.rotationDegrees((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticks))) * 5F));
        poseStack.mulPose(Axis.ZP.rotationDegrees((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticks))) * 5F));
        RenderUtil.renderCustomModel(WizardsRebornModels.ALTAR_OF_DROUGHT_FRAME, ItemDisplayContext.NONE, false, poseStack, bufferSource, light, overlay);
        poseStack.popPose();

        if (WissenUtil.isCanRenderWissenWand()) {
            poseStack.pushPose();
            poseStack.translate(-12, -12, -12);
            RenderUtil.renderConnectBoxLines(poseStack, new Vec3(25, 25, 25), WizardsRebornRenderUtil.colorArea, 0.5f);
            poseStack.popPose();
        }
    }

    @Override
    public boolean shouldRenderOffScreen(AltarOfDroughtBlockEntity blockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(AltarOfDroughtBlockEntity blockEntity, Vec3 cameraPos) {
        return true;
    }
}
