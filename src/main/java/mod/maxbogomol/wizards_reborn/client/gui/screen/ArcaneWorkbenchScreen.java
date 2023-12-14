package mod.maxbogomol.wizards_reborn.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.gui.container.ArcaneWorkbenchContainer;
import mod.maxbogomol.wizards_reborn.common.recipe.ArcaneWorkbenchRecipe;
import mod.maxbogomol.wizards_reborn.common.tileentity.ArcaneWorkbenchTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;

import java.util.Optional;

public class ArcaneWorkbenchScreen extends AbstractContainerScreen<ArcaneWorkbenchContainer> {
    private final ResourceLocation GUI = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcane_workbench.png");

    public ArcaneWorkbenchScreen(ArcaneWorkbenchContainer screenContainer, Inventory inv, Component titleIn) {
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

        if (menu.tileEntity instanceof ArcaneWorkbenchTileEntity) {
            ArcaneWorkbenchTileEntity workbench = (ArcaneWorkbenchTileEntity) menu.tileEntity;

            SimpleContainer inv = new SimpleContainer(14);
            for (int ii = 0; ii < workbench.itemHandler.getSlots(); ii++) {
                inv.setItem(ii, workbench.itemHandler.getStackInSlot(ii));
            }
            inv.setItem(13, workbench.itemOutputHandler.getStackInSlot(0));

            Optional<ArcaneWorkbenchRecipe> recipe = workbench.getLevel().getRecipeManager().getRecipeFor(WizardsReborn.ARCANE_WORKBENCH_RECIPE.get(), inv, workbench.getLevel());

            if (recipe.isPresent()) {
                RenderSystem.setShaderColor(1f, 1f, 1f, 0.25f);
                gui.renderItem(recipe.get().getResultItem(RegistryAccess.EMPTY), i + 146, j + 48);
                gui.renderItemDecorations(Minecraft.getInstance().font, recipe.get().getResultItem(RegistryAccess.EMPTY), i + 146, j + 48);
                RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            }
        }
    }
}
