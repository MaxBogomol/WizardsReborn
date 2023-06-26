package mod.maxbogomol.wizards_reborn.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import mod.maxbogomol.wizards_reborn.common.tileentity.CrystalTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

public class CrystalTileEntityRenderer extends TileEntityRenderer<CrystalTileEntity> {

    public CrystalTileEntityRenderer(TileEntityRendererDispatcher manager) {
        super(manager);
    }

    @Override
    public void render(CrystalTileEntity pedestal, float partialTicks, MatrixStack ms, IRenderTypeBuffer buffers, int light, int overlay) {

    }
}