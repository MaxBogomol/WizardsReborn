package mod.maxbogomol.wizards_reborn.client.render.tileentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.common.tileentity.ArcaneIteratorTileEntity;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class ArcaneIteratorTileEntityRenderer implements BlockEntityRenderer<ArcaneIteratorTileEntity> {

    public ArcaneIteratorTileEntityRenderer() {}

    @Override
    public void render(ArcaneIteratorTileEntity iterator, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        Random random = new Random();
        random.setSeed(iterator.getBlockPos().asLong());

        Minecraft mc = Minecraft.getInstance();
        double ticks = (iterator.rotate + partialTicks);
        double ticksOffset = (ClientTickHandler.ticksInGame + partialTicks) * 0.1F;
        double ticksUp = (ClientTickHandler.ticksInGame + partialTicks) * 4;
        ticksUp = (ticksUp) % 360;

        if (!iterator.isWorks()) {
            ticks = iterator.rotate;
        }

        float x = 0.15625F;
        float y = 0.15625F;
        float z = 0.15625F;
        float offset = 1;
        float size = 1;

        if (iterator.wissenInCraft > 0 && iterator.startCraft) {
            if (iterator.offset > 0) {
                offset = (iterator.offset + partialTicks) / 40F;
                if (offset > 1) {
                    offset = 1;
                }
                offset = offset + 1;
            }
            if (iterator.scale > 0) {
                size = (iterator.scale + partialTicks) / 60F;
                if (size > 1) {
                    size = 1;
                }
                size = 1 - size;
            }
        } else {
            if (iterator.offset > 0) {
                offset = (iterator.offset - partialTicks) / 40F;
                if (offset < 0) {
                    offset = 0;
                }
                offset = offset + 1;
            }
            if (iterator.scale > 0) {
                size = (iterator.scale - partialTicks) / 60F;
                if (size < 0) {
                    size = 0;
                }
                size = 1 - size;
            }
        }
        if (size < 0) {
            size = 0;
        }

        ms.pushPose();
        ms.translate(0.5F, 0.5F + (Math.sin(Math.toRadians(ticksUp)) * 0.03125F), 0.5F);
        ms.mulPose(Axis.YP.rotationDegrees((float) (random.nextFloat() * 360 + ticks)));
        ms.mulPose(Axis.XP.rotationDegrees((float) (random.nextFloat() * 360 + ticks)));
        ms.scale(size, size, size);
        ms.mulPose(Axis.ZP.rotationDegrees((float) (random.nextFloat() * 360 + ticks)));
        renderPiece(-x * offset, y * offset, -z * offset, 90F, 0F, size, iterator, partialTicks, ms, buffers, light, overlay);
        renderPiece(x * offset, y * offset, -z * offset, 0F, 0F, size, iterator, partialTicks, ms, buffers, light, overlay);
        renderPiece(-x * offset, y * offset, z * offset, 180F, 0F, size, iterator, partialTicks, ms, buffers, light, overlay);
        renderPiece(x * offset, y * offset, z * offset, -90F, 0F, size, iterator, partialTicks, ms, buffers, light, overlay);
        renderPiece(-x * offset, -y * offset, -z * offset, 90F, 90F, size, iterator, partialTicks, ms, buffers, light, overlay);
        renderPiece(x * offset, -y * offset, -z * offset, 0F, 90F, size, iterator, partialTicks, ms, buffers, light, overlay);
        renderPiece(-x * offset, -y * offset, z * offset, 180F, 90F, size, iterator, partialTicks, ms, buffers, light, overlay);
        renderPiece(x * offset, -y * offset, z * offset, -90F, 90F, size, iterator, partialTicks, ms, buffers, light, overlay);
        ms.popPose();

        if (WissenUtils.isCanRenderWissenWand()) {
            ms.pushPose();
            ms.translate(-5, -3, -5);
            RenderUtils.renderBoxLines(new Vec3(11, 7, 11), RenderUtils.colorArea, partialTicks, ms);
            ms.popPose();

            if (!iterator.isWorks()) {
                ms.pushPose();
                ms.translate(0, -2, 0);
                RenderUtils.renderBoxLines(new Vec3(1, 3, 1), RenderUtils.colorMissing, partialTicks, ms);
                ms.popPose();
            }
        }
    }

    public void renderPiece(float x, float y, float z, float yRot, float zRot, float size ,ArcaneIteratorTileEntity iterator, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light, int overlay) {
        ms.pushPose();
        ms.translate(x, y, z);
        ms.mulPose(Axis.YP.rotationDegrees(yRot));
        ms.mulPose(Axis.ZP.rotationDegrees(-zRot));
        ms.scale(size, size, size);
        RenderUtils.renderCustomModel(WizardsRebornClient.ARCANE_ITERATOR_PIECE_MODEl, ItemDisplayContext.FIXED, false, ms, buffers, light, overlay);
        ms.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen(ArcaneIteratorTileEntity pBlockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(ArcaneIteratorTileEntity pBlockEntity, Vec3 pCameraPos) {
        return true;
    }
}
