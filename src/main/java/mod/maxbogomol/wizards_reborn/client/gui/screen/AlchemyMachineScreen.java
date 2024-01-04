package mod.maxbogomol.wizards_reborn.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionUtils;
import mod.maxbogomol.wizards_reborn.client.gui.container.AlchemyMachineContainer;
import mod.maxbogomol.wizards_reborn.common.item.FluidStorageBaseItem;
import mod.maxbogomol.wizards_reborn.common.recipe.AlchemyMachineContext;
import mod.maxbogomol.wizards_reborn.common.recipe.AlchemyMachineRecipe;
import mod.maxbogomol.wizards_reborn.common.tileentity.AlchemyMachineTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Optional;

public class AlchemyMachineScreen extends AbstractContainerScreen<AlchemyMachineContainer> {
    private final ResourceLocation GUI = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/alchemy_machine.png");

    public AlchemyMachineScreen(AlchemyMachineContainer screenContainer, Inventory inv, Component titleIn) {
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

        if (menu.tileEntity instanceof AlchemyMachineTileEntity) {
            AlchemyMachineTileEntity machine = (AlchemyMachineTileEntity) menu.tileEntity;

            for (int ii = 0; ii <= 2; ii++) {
                int width = 32;
                width /= (double) machine.getMaxCapacity() / (double) machine.getTank(ii).getFluidAmount();
                gui.blit(GUI, i + 39, j + 59 + (ii * 15), 176, 0, width, 8, 256, 256);

                if (!machine.getFluidStack(ii).isEmpty()) {
                    if (x >= i + 39 && y >= j + 59 + (ii * 15) && x <= i + 39 + 32 && y <= j + 59 + (ii * 15) + 8) {
                        gui.renderTooltip(Minecraft.getInstance().font, FluidStorageBaseItem.getFluidName(machine.getFluidStack(ii), 5000), x, y);
                    }
                }
            }

            if (machine.wissenInCraft > 0 || machine.steamInCraft > 0 ) {
                int width = 22;
                width /= (double) (machine.wissenInCraft + machine.steamInCraft) / (machine.wissenIsCraft + machine.steamIsCraft);
                gui.blit(GUI, i + 97, j + 47, 176, 8, width, 15, 256, 256);
            }

            SimpleContainer inv = new SimpleContainer(7);
            for (int ii = 0; ii < machine.itemHandler.getSlots(); ii++) {
                inv.setItem(ii, machine.itemHandler.getStackInSlot(ii));
            }
            inv.setItem(6, machine.itemOutputHandler.getStackInSlot(0));

            List<ItemStack> items = machine.getItemsResult();

            if (items.size() > 0) {
                RenderSystem.setShaderColor(1f, 1f, 1f, 0.25f);
                gui.renderItem(items.get(0), i + 132, j + 48);
                gui.renderItemDecorations(Minecraft.getInstance().font, items.get(0), i + 132, j + 48);
                RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            }
        }
    }
}
