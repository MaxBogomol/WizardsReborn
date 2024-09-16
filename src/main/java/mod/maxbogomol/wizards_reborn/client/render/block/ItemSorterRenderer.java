package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.block.sensor.SensorBaseBlock;
import mod.maxbogomol.wizards_reborn.common.block.sensor.item_sorter.ItemSorterBlockEntity;
import mod.maxbogomol.wizards_reborn.util.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class ItemSorterRenderer implements BlockEntityRenderer<ItemSorterBlockEntity> {

    public ItemSorterRenderer() {}

    @Override
    public void render(ItemSorterBlockEntity sensor, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Random random = new Random();
        random.setSeed(sensor.getBlockPos().asLong());

        double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 0.1F;

        ms.pushPose();
        ms.translate(0.5F, 0.5F, 0.5F);
        ms.mulPose(Axis.YP.rotationDegrees((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticks))) * 5F));
        ms.mulPose(Axis.XP.rotationDegrees((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticks))) * 5F));
        ms.mulPose(Axis.ZP.rotationDegrees((float) ((Math.sin(Math.toRadians(random.nextFloat() * 360) + ticks))) * 5F));

        ms.mulPose(Axis.YP.rotationDegrees(sensor.getBlockRotate()));
        ms.mulPose(Axis.XP.rotationDegrees(sensor.getBlockUpRotate()));
        RenderUtil.renderCustomModel(((SensorBaseBlock) sensor.getBlockState().getBlock()).getModel(sensor.getBlockState()), ItemDisplayContext.FIXED, false, ms, buffers, light, overlay);
        ms.popPose();

        if (WissenUtils.isCanRenderWissenWand()) {
            Direction outputDirection = sensor.getBlockState().getValue(SensorBaseBlock.FACING);
            BlockPos outputBlockpos = sensor.getBlockPos().relative(outputDirection);

            Direction inputDirection = sensor.getBlockState().getValue(SensorBaseBlock.FACING).getOpposite();
            BlockPos inputBlockpos = sensor.getBlockPos().relative(inputDirection);

            switch (sensor.getBlockState().getValue(SensorBaseBlock.FACE)) {
                case FLOOR:
                    outputBlockpos = sensor.getBlockPos().above();
                    inputBlockpos = sensor.getBlockPos().below();
                    break;
                case WALL:
                    break;
                case CEILING:
                    outputBlockpos = sensor.getBlockPos().below();
                    inputBlockpos = sensor.getBlockPos().above();
                    break;
            }

            ms.pushPose();
            ms.translate(sensor.getBlockPos().getX() - outputBlockpos.getX(), sensor.getBlockPos().getY() - outputBlockpos.getY(), sensor.getBlockPos().getZ() - outputBlockpos.getZ());
            RenderUtils.renderBoxLines(new Vec3(1, 1, 1), RenderUtils.colorConnectFrom, partialTicks, ms);
            ms.popPose();

            ms.pushPose();
            ms.translate(sensor.getBlockPos().getX() - inputBlockpos.getX(), sensor.getBlockPos().getY() - inputBlockpos.getY(), sensor.getBlockPos().getZ() - inputBlockpos.getZ());
            RenderUtils.renderBoxLines(new Vec3(1, 1, 1), RenderUtils.colorConnectTo, partialTicks, ms);
            ms.popPose();
        }
    }
}
