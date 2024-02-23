package mod.maxbogomol.wizards_reborn.client.render.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.tileentity.SteamCasingTileEntity;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

public class SteamCasingTileEntityRenderer implements BlockEntityRenderer<SteamCasingTileEntity> {

    public SteamCasingTileEntityRenderer() {}

    @Override
    public void render(SteamCasingTileEntity casing, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        if (!casing.getLevel().getBlockState(casing.getBlockPos()).isAir() && casing.getLevel().getBlockState(casing.getBlockPos()).getValue(BlockStateProperties.POWERED) && WissenUtils.isCanRenderWissenWand()) {
            ms.pushPose();
            RenderUtils.renderBoxLines(new Vec3(1f, 1f, 1f), RenderUtils.colorArea, partialTicks, ms);
            ms.popPose();
        }
    }
}
