package mod.maxbogomol.wizards_reborn.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.gui.container.AlchemyFurnaceContainer;
import mod.maxbogomol.wizards_reborn.common.tileentity.AlchemyFurnaceTileEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AlchemyFurnaceScreen extends AbstractContainerScreen<AlchemyFurnaceContainer> {
    private final ResourceLocation GUI = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/alchemy_furnace.png");

    public AlchemyFurnaceScreen(AlchemyFurnaceContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.imageHeight = 201;
        this.inventoryLabelY = this.inventoryLabelY + 46;
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(gui);
        super.render(gui, mouseX, mouseY, partialTicks);
        this.renderTooltip(gui, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {

    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTicks, int x, int y) {
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        this.minecraft.getTextureManager().bindForSetup(GUI);
        int i = this.leftPos;
        int j = this.topPos;
        gui.blit(GUI, i, j, 0, 0, this.imageWidth, this.imageHeight);

        if (menu.tileEntity instanceof AlchemyFurnaceTileEntity) {
            AlchemyFurnaceTileEntity furnace = (AlchemyFurnaceTileEntity) menu.tileEntity;

            int width = 32;
            width /= (double) furnace.getFluidMaxAmount() / (double) furnace.getFluidAmount();
            gui.blit(GUI, i + 19, j + 40 + 32 - width, 176, 32 - width, 8, width, 256, 256);

            width = 32;
            width /= (double) furnace.getMaxHeat() / (double) furnace.getHeat();
            gui.blit(GUI, i + 19 + 15, j + 40 + 32 - width, 176 + 8, 32 - width, 8, width, 256, 256);

            width = 32;
            width /= (double) furnace.getMaxSteam() / (double) furnace.getSteam();
            gui.blit(GUI, i + 19 + 30, j + 40 + 32 - width, 176 + 16, 32 - width, 8, width, 256, 256);

            if (furnace.burnMaxTime > 0) {
                width = 18;
                width /= (double) furnace.burnMaxTime / (double) furnace.burnTime;
                gui.blit(GUI, i + 72, j + 46 + 18 - width, 176, 47 + 18 - width, 18, width, 256, 256);
            }

            if (furnace.cookTime > 0) {
                width = 22;
                width /= (double) furnace.cookMaxTime / (double) furnace.cookTime;
                gui.blit(GUI, i + 97, j + 47, 176, 32, width, 15, 256, 256);
            }
        }
    }
}
