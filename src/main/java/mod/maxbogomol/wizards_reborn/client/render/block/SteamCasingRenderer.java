package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.common.block.casing.steam.SteamCasingBlockEntity;
import mod.maxbogomol.wizards_reborn.util.WizardsRebornRenderUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class SteamCasingRenderer implements BlockEntityRenderer<SteamCasingBlockEntity> {

    @Override
    public void render(SteamCasingBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        Optional<Boolean> powered = blockEntity.getLevel().getBlockState(blockEntity.getBlockPos()).getOptionalValue(BlockStateProperties.POWERED);
        if (powered.isPresent() && powered.get() && WissenUtil.isCanRenderWissenWand()) {
            poseStack.pushPose();
            RenderUtil.renderConnectBoxLines(poseStack, new Vec3(1f, 1f, 1f), WizardsRebornRenderUtil.colorArea, 0.5f);
            poseStack.popPose();
        }
    }
}
