package mod.maxbogomol.wizards_reborn.client.render.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.tileentity.AlchemyBoilerTileEntity;
import mod.maxbogomol.wizards_reborn.common.tileentity.AlchemyMachineTileEntity;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.phys.Vec3;

public class AlchemyMachineTileEntityRenderer implements BlockEntityRenderer<AlchemyMachineTileEntity> {

    public AlchemyMachineTileEntityRenderer() {}

    @Override
    public void render(AlchemyMachineTileEntity machine, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        if (WissenUtils.isCanRenderWissenWand()) {
            if (!(machine.getLevel().getBlockEntity(machine.getBlockPos().above()) instanceof AlchemyBoilerTileEntity boiler)) {
                ms.pushPose();
                RenderUtils.renderBoxLines(new Vec3(1f, 2f, 1f), RenderUtils.colorMissing, partialTicks, ms);
                ms.popPose();
            }
        }
    }
}
