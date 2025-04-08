package mod.maxbogomol.wizards_reborn.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.gui.container.AlchemyMachineContainer;
import mod.maxbogomol.wizards_reborn.common.block.alchemy_machine.AlchemyMachineBlockEntity;
import mod.maxbogomol.wizards_reborn.util.NumericalUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class AlchemyMachineScreen extends AbstractContainerScreen<AlchemyMachineContainer> {
    private final ResourceLocation GUI = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/alchemy_machine.png");

    public AlchemyMachineScreen(AlchemyMachineContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.imageHeight = 209;
        this.inventoryLabelY = this.inventoryLabelY + 43;
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

        if (menu.blockEntity instanceof AlchemyMachineBlockEntity machine) {
            for (int ii = 0; ii <= 2; ii++) {
                int width = 32;
                double value = (double) machine.getMaxCapacity() / (double) machine.getTank(ii).getFluidAmount();
                width /= value;
                if (width == 0 && value > 0 && !Double.isInfinite(value)) width = 1;
                gui.blit(GUI, i + 39, j + 65 + (ii * 15), 176, 0, width, 8, 256, 256);

                if (x >= i + 39 && y >= j + 65 + (ii * 15) && x <= i + 39 + 32 && y <= j + 65 + (ii * 15) + 8) {
                    gui.renderTooltip(Minecraft.getInstance().font, NumericalUtil.getFluidName(machine.getFluidStack(ii), 5000), x, y);
                }
            }

            if (machine.wissenInCraft > 0 || machine.steamInCraft > 0) {
                int width = 22;
                double value = (double) (machine.wissenInCraft + machine.steamInCraft) / (machine.wissenIsCraft + machine.steamIsCraft);
                width /= value;
                if (width == 0 && value > 0 && !Double.isInfinite(value)) width = 1;
                gui.blit(GUI, i + 97, j + 53, 176, 8, width, 15, 256, 256);
            }

            SimpleContainer inv = new SimpleContainer(7);
            for (int ii = 0; ii < machine.itemHandler.getSlots(); ii++) {
                inv.setItem(ii, machine.itemHandler.getStackInSlot(ii));
            }
            inv.setItem(6, machine.itemOutputHandler.getStackInSlot(0));

            List<ItemStack> items = machine.getItemsResult();

            if (items.size() > 0 && machine.itemOutputHandler.getStackInSlot(0).isEmpty()) {
                gui.renderItem(items.get(0), i + 132, j + 54);
                RenderSystem.setShaderColor(1f, 1f, 1f, 0.25f);
                gui.renderItemDecorations(Minecraft.getInstance().font, items.get(0), i + 132, j + 54, String.valueOf(items.get(0).getCount()));
                RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            }
        }
    }
}
