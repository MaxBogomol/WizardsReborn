package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.block.alchemy_boiler.AlchemyBoilerBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.alchemy_machine.AlchemyMachineBlockEntity;
import mod.maxbogomol.wizards_reborn.util.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.phys.Vec3;

public class AlchemyBoilerRenderer implements BlockEntityRenderer<AlchemyBoilerBlockEntity> {

    @Override
    public void render(AlchemyBoilerBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        if (WissenUtils.isCanRenderWissenWand()) {
            if (!(blockEntity.getLevel().getBlockEntity(blockEntity.getBlockPos().below()) instanceof AlchemyMachineBlockEntity machine)) {
                poseStack.pushPose();
                poseStack.translate(0, -1, 0);
                RenderUtils.renderBoxLines(new Vec3(1f, 2f, 1f), RenderUtils.colorMissing, partialTicks, poseStack);
                poseStack.popPose();
            }
        }
    }
}
