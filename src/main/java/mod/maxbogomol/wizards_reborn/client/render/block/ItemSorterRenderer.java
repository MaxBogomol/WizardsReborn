package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.common.block.sensor.SensorBaseBlock;
import mod.maxbogomol.wizards_reborn.common.block.sensor.item_sorter.ItemSorterBlockEntity;
import mod.maxbogomol.wizards_reborn.util.WizardsRebornRenderUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class ItemSorterRenderer implements BlockEntityRenderer<ItemSorterBlockEntity> {
    
    @Override
    public void render(ItemSorterBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Random random = new Random();
        random.setSeed(blockEntity.getBlockPos().asLong());

        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 0.1F;

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.5F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticks))) * 5F));
        poseStack.mulPose(Axis.XP.rotationDegrees((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticks))) * 5F));
        poseStack.mulPose(Axis.ZP.rotationDegrees((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticks))) * 5F));

        poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getBlockRotate()));
        poseStack.mulPose(Axis.XP.rotationDegrees(blockEntity.getBlockUpRotate()));
        RenderUtil.renderCustomModel(((SensorBaseBlock) blockEntity.getBlockState().getBlock()).getModel(blockEntity.getBlockState()), ItemDisplayContext.FIXED, false, poseStack, bufferSource, light, overlay);
        poseStack.popPose();

        if (WissenUtil.isCanRenderWissenWand()) {
            Direction outputDirection = blockEntity.getBlockState().getValue(SensorBaseBlock.FACING);
            BlockPos outputBlockpos = blockEntity.getBlockPos().relative(outputDirection);

            Direction inputDirection = blockEntity.getBlockState().getValue(SensorBaseBlock.FACING).getOpposite();
            BlockPos inputBlockpos = blockEntity.getBlockPos().relative(inputDirection);

            switch (blockEntity.getBlockState().getValue(SensorBaseBlock.FACE)) {
                case FLOOR:
                    outputBlockpos = blockEntity.getBlockPos().above();
                    inputBlockpos = blockEntity.getBlockPos().below();
                    break;
                case WALL:
                    break;
                case CEILING:
                    outputBlockpos = blockEntity.getBlockPos().below();
                    inputBlockpos = blockEntity.getBlockPos().above();
                    break;
            }

            poseStack.pushPose();
            poseStack.translate(blockEntity.getBlockPos().getX() - outputBlockpos.getX(), blockEntity.getBlockPos().getY() - outputBlockpos.getY(), blockEntity.getBlockPos().getZ() - outputBlockpos.getZ());
            RenderUtil.renderConnectBoxLines(poseStack, new Vec3(1, 1, 1), WizardsRebornRenderUtil.colorConnectFrom, 0.5f);
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(blockEntity.getBlockPos().getX() - inputBlockpos.getX(), blockEntity.getBlockPos().getY() - inputBlockpos.getY(), blockEntity.getBlockPos().getZ() - inputBlockpos.getZ());
            RenderUtil.renderConnectBoxLines(poseStack, new Vec3(1, 1, 1), WizardsRebornRenderUtil.colorConnectTo, 0.5f);
            poseStack.popPose();
        }
    }
}
