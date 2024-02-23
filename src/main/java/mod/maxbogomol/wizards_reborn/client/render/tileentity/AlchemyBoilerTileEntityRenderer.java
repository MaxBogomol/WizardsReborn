package mod.maxbogomol.wizards_reborn.client.render.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.tileentity.AlchemyBoilerTileEntity;
import mod.maxbogomol.wizards_reborn.common.tileentity.AlchemyMachineTileEntity;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.phys.Vec3;

public class AlchemyBoilerTileEntityRenderer implements BlockEntityRenderer<AlchemyBoilerTileEntity> {

    public AlchemyBoilerTileEntityRenderer() {}

    @Override
    public void render(AlchemyBoilerTileEntity boiler, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        if (WissenUtils.isCanRenderWissenWand()) {
            if (!(boiler.getLevel().getBlockEntity(boiler.getBlockPos().below()) instanceof AlchemyMachineTileEntity machine)) {
                ms.pushPose();
                ms.translate(0, -1, 0);
                RenderUtils.renderBoxLines(new Vec3(1f, 2f, 1f), RenderUtils.colorMissing, partialTicks, ms);
                ms.popPose();
            }
        }
    }
}
