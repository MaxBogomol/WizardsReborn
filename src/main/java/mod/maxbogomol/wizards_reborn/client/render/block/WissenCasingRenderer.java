package mod.maxbogomol.wizards_reborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.block.casing.wissen.WissenCasingBlockEntity;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

public class WissenCasingRenderer implements BlockEntityRenderer<WissenCasingBlockEntity> {

    public WissenCasingRenderer() {}

    @Override
    public void render(WissenCasingBlockEntity casing, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        for (Direction direction : Direction.values()) {
            ms.pushPose();
            ms.translate(0.5F, 0.5F, 0.5F);
            BlockPos pos = new BlockPos(0, 0, 0).relative(direction);
            ms.translate(pos.getX() * 0.4375f, pos.getY() * 0.4375f, pos.getZ() * 0.4375f);

            if (casing.isConnection(direction)) {
                if (WissenUtils.isCanRenderWissenWand()) {
                    ms.pushPose();
                    ms.translate(-0.2f, -0.2f, -0.2f);
                    RenderUtils.renderBoxLines(new Vec3(0.4f, 0.4f, 0.4f), RenderUtils.colorConnectTo, partialTicks, ms);
                    ms.popPose();
                }
            }
            ms.popPose();
        }
    }
}
