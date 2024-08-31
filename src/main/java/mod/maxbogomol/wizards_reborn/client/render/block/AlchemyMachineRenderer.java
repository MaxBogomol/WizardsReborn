package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.block.alchemy_boiler.AlchemyBoilerBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.alchemy_machine.AlchemyMachineBlockEntity;
import mod.maxbogomol.wizards_reborn.util.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.phys.Vec3;

public class AlchemyMachineRenderer implements BlockEntityRenderer<AlchemyMachineBlockEntity> {

    public AlchemyMachineRenderer() {}

    @Override
    public void render(AlchemyMachineBlockEntity machine, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        if (WissenUtils.isCanRenderWissenWand()) {
            if (!(machine.getLevel().getBlockEntity(machine.getBlockPos().above()) instanceof AlchemyBoilerBlockEntity boiler)) {
                ms.pushPose();
                RenderUtils.renderBoxLines(new Vec3(1f, 2f, 1f), RenderUtils.colorMissing, partialTicks, ms);
                ms.popPose();
            }
        }
    }
}
