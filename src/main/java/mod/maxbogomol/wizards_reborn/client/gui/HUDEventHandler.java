package mod.maxbogomol.wizards_reborn.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenTileEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

public class HUDEventHandler {
    private HUDEventHandler() {}

    public static void onDrawScreenPost(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        ItemStack main = mc.player.getHeldItemMainhand();
        ItemStack offhand = mc.player.getHeldItemOffhand();
        MatrixStack ms = event.getMatrixStack();

        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {

            PlayerEntity player = mc.player;
            boolean renderWissenWand = false;

            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            if (!main.isEmpty() && main.getItem() instanceof WissenWandItem) {
                renderWissenWand=true;
            } else {
                if (!offhand.isEmpty() && offhand.getItem() instanceof WissenWandItem) {
                    renderWissenWand=true;
                }
            }
            if (renderWissenWand) {
                if (!player.isSpectator()) {
                    RayTraceResult pos = mc.objectMouseOver;
                    if (pos != null) {
                        BlockPos bpos = pos.getType() == RayTraceResult.Type.BLOCK ? ((BlockRayTraceResult) pos).getPos() : null;
                        TileEntity tileentity = bpos != null ? mc.world.getTileEntity(bpos) : null;

                        if (tileentity != null) {
                            if (tileentity instanceof IWissenTileEntity) {
                                IWissenTileEntity wissentile = (IWissenTileEntity) tileentity;

                                int x = mc.getMainWindow().getScaledWidth() / 2 - (48 / 2);
                                int y = mc.getMainWindow().getScaledHeight() / 2 + 32 - 10;

                                mc.textureManager.bindTexture(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/wissen_frame.png"));
                                AbstractGui.blit(ms, x, y, 0, 0, 48, 10, 64, 64);
                                int width_wissen = 32;
                                width_wissen /= (double) wissentile.getMaxWissen() / (double) wissentile.getWissen();
                                mc.textureManager.bindTexture(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/wissen_frame.png"));
                                AbstractGui.blit(ms, x + 8, y + 1, 0, 10, width_wissen, 8, 64, 64);
                            }
                        }
                    }
                }
            }

            RenderSystem.disableBlend();
            RenderSystem.color4f(1F, 1F, 1F, 1F);
        }
    }
}
