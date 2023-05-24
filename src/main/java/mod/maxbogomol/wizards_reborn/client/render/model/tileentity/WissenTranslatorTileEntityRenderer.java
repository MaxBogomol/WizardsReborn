package mod.maxbogomol.wizards_reborn.client.render.model.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenTileEntity;
import mod.maxbogomol.wizards_reborn.client.render.RenderUtils;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.common.tileentity.WissenTranslatorTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import java.awt.*;

public class WissenTranslatorTileEntityRenderer extends TileEntityRenderer<WissenTranslatorTileEntity> {

    public WissenTranslatorTileEntityRenderer(TileEntityRendererDispatcher manager) {
        super(manager);
    }

    @Override
    public void render(WissenTranslatorTileEntity translator, float partialTicks, MatrixStack ms, IRenderTypeBuffer buffers, int light, int overlay) {
        Minecraft mc = Minecraft.getInstance();

        PlayerEntity player = mc.player;
        ItemStack main = player.getHeldItemMainhand();
        ItemStack offhand = player.getHeldItemOffhand();
        boolean renderWand = false;

        if (!main.isEmpty() && main.getItem() instanceof WissenWandItem) {
            renderWand = true;
        } else {
            if (!offhand.isEmpty() && offhand.getItem() instanceof WissenWandItem) {
                renderWand = true;
            }
        }

        renderWand = false;

        if (renderWand) {
            if (translator.isToBlock) {
                ms.push();
                BlockPos blockPos = new BlockPos(translator.blockToX, translator.blockToY, translator.blockToZ);
                RenderUtils.renderBoxBlockOutline(ms, buffers, mc.world.getBlockState(blockPos).getShape(mc.world, blockPos),
                        blockPos.getX() - translator.getPos().getX(), blockPos.getY() - translator.getPos().getY(), blockPos.getZ() - translator.getPos().getZ(), new Color(255, 255, 0));

                if (mc.world.getTileEntity(blockPos) instanceof IWissenTileEntity) {
                    RenderUtils.renderLine(ms, buffers, 0.5f, 0.5f, 0.5f,
                            blockPos.getX() - translator.getPos().getX() + 0.5f, blockPos.getY() - translator.getPos().getY() + 0.5f, blockPos.getZ() - translator.getPos().getZ() + 0.5f, new Color(255, 255, 0));
                }
                ms.pop();
            }

            if (translator.isFromBlock) {
                ms.push();
                BlockPos blockPos = new BlockPos(translator.blockFromX, translator.blockFromY, translator.blockFromZ);
                RenderUtils.renderBoxBlockOutline(ms, buffers, mc.world.getBlockState(blockPos).getShape(mc.world, blockPos),
                        blockPos.getX() - translator.getPos().getX(), blockPos.getY() - translator.getPos().getY(), blockPos.getZ() - translator.getPos().getZ(), new Color(255, 0, 255));

                if (mc.world.getTileEntity(blockPos) instanceof IWissenTileEntity) {
                    RenderUtils.renderLine(ms, buffers, 0.5f, 0.5f, 0.5f,
                            blockPos.getX() - translator.getPos().getX() + 0.5f, blockPos.getY() - translator.getPos().getY() + 0.5f, blockPos.getZ() - translator.getPos().getZ() + 0.5f, new Color(255, 0, 255));
                }
                ms.pop();
            }
        }
    }
}
