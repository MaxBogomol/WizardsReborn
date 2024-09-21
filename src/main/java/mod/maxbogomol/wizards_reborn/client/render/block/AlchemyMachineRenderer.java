package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.common.block.alchemy_boiler.AlchemyBoilerBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.alchemy_machine.AlchemyMachineBlockEntity;
import mod.maxbogomol.wizards_reborn.util.WizardsRebornRenderUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.phys.Vec3;

public class AlchemyMachineRenderer implements BlockEntityRenderer<AlchemyMachineBlockEntity> {

    @Override
    public void render(AlchemyMachineBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        if (WissenUtil.isCanRenderWissenWand()) {
            if (!(blockEntity.getLevel().getBlockEntity(blockEntity.getBlockPos().above()) instanceof AlchemyBoilerBlockEntity boiler)) {
                poseStack.pushPose();
                WizardsRebornRenderUtil.renderBoxLines(new Vec3(1f, 2f, 1f), WizardsRebornRenderUtil.colorMissing, partialTicks, poseStack);
                poseStack.popPose();
            }
        }
    }
}
