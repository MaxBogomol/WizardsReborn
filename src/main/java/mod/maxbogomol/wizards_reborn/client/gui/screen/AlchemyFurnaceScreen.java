package mod.maxbogomol.wizards_reborn.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornClientConfig;
import mod.maxbogomol.wizards_reborn.client.gui.container.AlchemyFurnaceContainer;
import mod.maxbogomol.wizards_reborn.common.block.alchemy_furnace.AlchemyFurnaceBlockEntity;
import mod.maxbogomol.wizards_reborn.util.NumericalUtil;
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
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, ColorUtil.packColor(255, 237, 201, 146), false);
        guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTicks, int x, int y) {
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        int i = this.leftPos;
        int j = this.topPos;
        gui.blit(GUI, i, j, 0, 0, this.imageWidth, this.imageHeight);

        if (menu.blockEntity instanceof AlchemyFurnaceBlockEntity furnace) {
            int width = 32;
            width /= (double) furnace.getFluidMaxAmount() / (double) furnace.getFluidAmount();
            gui.blit(GUI, i + 19, j + 60 - width, 176, 32 - width, 8, width, 256, 256);

            width = 32;
            width /= (double) furnace.getMaxHeat() / (double) furnace.getHeat();
            gui.blit(GUI, i + 19 + 15, j + 60 - width, 176 + 8, 32 - width, 8, width, 256, 256);

            width = 32;
            width /= (double) furnace.getMaxSteam() / (double) furnace.getSteam();
            gui.blit(GUI, i + 19 + 30, j + 60 - width, 176 + 16, 32 - width, 8, width, 256, 256);

            if (furnace.burnMaxTime > 0) {
                width = 13;
                width /= (double) furnace.burnMaxTime / (double) furnace.burnTime;
                gui.blit(GUI, i + 72, j + 50 - width, 176, 47 + 13 - width, 18, width, 256, 256);
            }

            if (furnace.cookTime > 0) {
                width = 22;
                width /= (double) furnace.cookMaxTime / (double) furnace.cookTime;
                gui.blit(GUI, i + 97, j + 35, 176, 32, width, 15, 256, 256);
            }

            if (x >= i + 19 && y >= j + 28 && x <= i + 19 + 8 && y <= j + 28 + 32) {
                Component component = NumericalUtil.getFluidName(furnace.getTank().getFluid(), 10000);
                if (!WizardsRebornClientConfig.NUMERICAL_FLUID.get()) {
                    component = NumericalUtil.getFluidName(furnace.getTank().getFluid());
                }
                gui.renderTooltip(Minecraft.getInstance().font, component, x, y);
            }

            if (x >= i + 19 + 15 && y >= j + 28 && x <= i + 19 + 15 + 8 && y <= j + 28 + 32) {
                Component component = NumericalUtil.getHeatName(furnace.getHeat(), furnace.getMaxSteam());
                if (!WizardsRebornClientConfig.NUMERICAL_HEAT.get()) {
                    component = NumericalUtil.getHeatName();
                }
                gui.renderTooltip(Minecraft.getInstance().font, component, x, y);
            }

            if (x >= i + 19 + 30 && y >= j + 28 && x <= i + 19 + 30 + 8 && y <= j + 28 + 32) {
                Component component = NumericalUtil.getSteamName(furnace.getSteam(), furnace.getMaxSteam());
                if (!WizardsRebornClientConfig.NUMERICAL_STEAM.get()) {
                    component = NumericalUtil.getSteamName();
                }
                gui.renderTooltip(Minecraft.getInstance().font, component, x, y);
            }

            List<ItemStack> items = furnace.getItemsResult();

            if (items.size() > 0 && furnace.itemOutputHandler.getStackInSlot(0).isEmpty()) {
                gui.renderItem(items.get(0), i + 132, j + 36);
                RenderSystem.setShaderColor(1f, 1f, 1f, 0.25f);
                gui.renderItemDecorations(Minecraft.getInstance().font, items.get(0), i + 132, j + 36, String.valueOf(items.get(0).getCount()));
                RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            }
        }
    }
}
