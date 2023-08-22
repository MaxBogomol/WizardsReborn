package mod.maxbogomol.wizards_reborn.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenTileEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import org.lwjgl.opengl.GL11;

public class HUDEventHandler {
    private HUDEventHandler() {}

    public static void onDrawScreenPost(RenderGuiOverlayEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        ItemStack main = mc.player.getMainHandItem();
        ItemStack offhand = mc.player.getOffhandItem();
        PoseStack ms = event.getGuiGraphics().pose();

        //if (event.getOverlay() == RenderGuiOverlayEvent.ElementType.ALL) {

            Player player = mc.player;
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
                    HitResult pos = mc.hitResult;
                    if (pos != null) {
                        BlockPos bpos = pos.getType() == HitResult.Type.BLOCK ? ((BlockHitResult) pos).getBlockPos() : null;
                        BlockEntity tileentity = bpos != null ? mc.level.getBlockEntity(bpos) : null;

                        if (tileentity != null) {
                            if (tileentity instanceof IWissenTileEntity) {
                                IWissenTileEntity wissentile = (IWissenTileEntity) tileentity;

                                int x = mc.getWindow().getGuiScaledWidth() / 2 - (48 / 2);
                                int y = mc.getWindow().getGuiScaledHeight() / 2 + 32 - 10;

                                RenderSystem.setShaderTexture(0, new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/wissen_frame.png"));
                                //event.getGuiGraphics().blit(ms, x, y, 0, 0, 48, 10, 64, 64);
                                int width_wissen = 32;
                                width_wissen /= (double) wissentile.getMaxWissen() / (double) wissentile.getWissen();
                                RenderSystem.setShaderTexture(0, new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/wissen_frame.png"));
                                //event.getGuiGraphics().blit(ms, x + 8, y + 1, 0, 10, width_wissen, 8, 64, 64);
                            }
                        }
                    }
                }
            }

            RenderSystem.disableBlend();
            RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        //}
    }
}
