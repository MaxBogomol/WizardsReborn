package mod.maxbogomol.wizards_reborn.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.gui.container.AlchemyFurnaceContainer;
import mod.maxbogomol.wizards_reborn.common.item.FluidStorageBaseItem;
import mod.maxbogomol.wizards_reborn.common.tileentity.AlchemyFurnaceTileEntity;
import mod.maxbogomol.wizards_reborn.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class AlchemyFurnaceScreen extends AbstractContainerScreen<AlchemyFurnaceContainer> {
    private final ResourceLocation GUI = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/alchemy_furnace.png");

    public AlchemyFurnaceScreen(AlchemyFurnaceContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.imageHeight = 176;
        this.inventoryLabelY = this.inventoryLabelY + 10;
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(gui);
        super.render(gui, mouseX, mouseY, partialTicks);
        this.renderTooltip(gui, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, ColorUtils.packColor(255, 237, 201, 146), false);
        guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTicks, int x, int y) {
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        int i = this.leftPos;
        int j = this.topPos;
        gui.blit(GUI, i, j, 0, 0, this.imageWidth, this.imageHeight);

        if (menu.tileEntity instanceof AlchemyFurnaceTileEntity furnace) {
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
                width = 13;
                width /= (double) furnace.burnMaxTime / (double) furnace.burnTime;
                gui.blit(GUI, i + 72, j + 46 + 16 - width, 176, 47 + 13 - width, 18, width, 256, 256);
            }

            if (furnace.cookTime > 0) {
                width = 22;
                width /= (double) furnace.cookMaxTime / (double) furnace.cookTime;
                gui.blit(GUI, i + 97, j + 47, 176, 32, width, 15, 256, 256);
            }

            if (!furnace.getTank().getFluid().isEmpty()) {
                if (x >= i + 19 && y >= j + 40 && x <= i + 19 + 8 && y <= j + 40 + 32) {
                    gui.renderTooltip(Minecraft.getInstance().font, FluidStorageBaseItem.getFluidName(furnace.getTank().getFluid(), 10000), x, y);
                }
            }

            List<ItemStack> items = furnace.getItemsResult();

            if (items.size() > 0) {
                gui.renderItem(items.get(0), i + 132, j + 48);
                RenderSystem.setShaderColor(1f, 1f, 1f, 0.25f);
                gui.renderItemDecorations(Minecraft.getInstance().font, items.get(0), i + 132, j + 48, String.valueOf(items.get(0).getCount()));
                RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            }
        }
    }
}
