package mod.maxbogomol.wizards_reborn.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.maxbogomol.wizards_reborn.common.tileentity.WissenTranslatorTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

import java.awt.*;

public class WissenTranslatorTileEntityRenderer extends TileEntityRenderer<WissenTranslatorTileEntity> {

    public WissenTranslatorTileEntityRenderer(TileEntityRendererDispatcher manager) {
        super(manager);
    }

    @Override
    public void render(WissenTranslatorTileEntity translator, float partialTicks, MatrixStack ms, IRenderTypeBuffer buffers, int light, int overlay) {

    }
}
