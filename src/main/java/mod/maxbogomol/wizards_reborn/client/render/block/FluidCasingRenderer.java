package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.common.block.casing.fluid.FluidCasingBlockEntity;
import mod.maxbogomol.wizards_reborn.util.WizardsRebornRenderUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class FluidCasingRenderer implements BlockEntityRenderer<FluidCasingBlockEntity> {

    @Override
    public void render(FluidCasingBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Optional<Boolean> powered = blockEntity.getLevel().getBlockState(blockEntity.getBlockPos()).getOptionalValue(BlockStateProperties.POWERED);
        if (powered.isPresent() && powered.get() && WissenUtil.isCanRenderWissenWand()) {
            poseStack.pushPose();
            RenderUtil.renderConnectBoxLines(poseStack, new Vec3(1f, 1f, 1f), WizardsRebornRenderUtil.colorArea, 0.5f);
            poseStack.popPose();
        }
    }
}
