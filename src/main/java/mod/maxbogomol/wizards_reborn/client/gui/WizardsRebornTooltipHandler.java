package mod.maxbogomol.wizards_reborn.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.IFluidItem;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamItem;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenItem;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RenderTooltipEvent;
import org.lwjgl.opengl.GL11;

public class WizardsRebornTooltipHandler {

    public static void onPostTooltipEvent(RenderTooltipEvent.Color event) {
        ItemStack stack = event.getItemStack();

        int x = event.getX();
        int y = event.getY();
        PoseStack ms = event.getGraphics().pose();

        if (stack.getItem() instanceof IWissenItem) {
            IWissenItem item = (IWissenItem) stack.getItem();
            CompoundTag nbt = stack.getTag();

            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            ms.translate(0, 0, 410.0);

            event.getGraphics().blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/wissen_frame.png"), x - 4, y - 15, 0, 0, 48, 10, 64, 64);

            if (nbt!=null) {
                if (nbt.contains("wissen")) {
                    int width_wissen = 32;
                    width_wissen /= (double) item.getMaxWissen() / (double) WissenItemUtil.getWissen(stack);
                    event.getGraphics().blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/wissen_frame.png"), x + 4, y - 14, 0, 10, width_wissen, 8, 64, 64);
                }
            }

            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(1, 1, 1, 1);
        }

        if (stack.getItem() instanceof IFluidItem) {
            IFluidItem item = (IFluidItem) stack.getItem();
            CompoundTag nbt = stack.getTag();

            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            ms.translate(0, 0, 410.0);

            event.getGraphics().blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/fluid_frame.png"), x - 4, y - 15, 0, 0, 48, 10, 64, 64);

            if (nbt!=null) {
                if (nbt.contains("fluidTank")) {
                    int width_fluid = 32;
                    width_fluid /= (double) item.getMaxFluid(stack) / (double) nbt.getCompound("fluidTank").getInt("Amount");
                    event.getGraphics().blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/fluid_frame.png"), x + 4, y - 14, 0, 10, width_fluid, 8, 64, 64);
                }
            }

            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(1, 1, 1, 1);
        }

        if (stack.getItem() instanceof ISteamItem) {
            ISteamItem item = (ISteamItem) stack.getItem();
            CompoundTag nbt = stack.getTag();

            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            ms.translate(0, 0, 410.0);

            event.getGraphics().blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/steam_frame.png"), x - 4, y - 15, 0, 0, 48, 10, 64, 64);

            if (nbt!=null) {
                if (nbt.contains("steam")) {
                    int width_steam = 32;
                    width_steam /= (double) item.getMaxSteam() / (double) nbt.getInt("steam");
                    event.getGraphics().blit(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/steam_frame.png"), x + 4, y - 14, 0, 10, width_steam, 8, 64, 64);
                }
            }

            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(1, 1, 1, 1);
        }
    }
}
