package mod.maxbogomol.wizards_reborn.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.client.gui.container.JewelerTableContainer;
import mod.maxbogomol.wizards_reborn.common.recipe.JewelerTableRecipe;
import mod.maxbogomol.wizards_reborn.common.tileentity.JewelerTableTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Optional;

public class JewelerTableScreen extends AbstractContainerScreen<JewelerTableContainer> {
    private final ResourceLocation GUI = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/jeweler_table.png");

    public JewelerTableScreen(JewelerTableContainer screenContainer, Inventory inv, Component titleIn) {
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

        if (menu.tileEntity instanceof JewelerTableTileEntity) {
            JewelerTableTileEntity table = (JewelerTableTileEntity) menu.tileEntity;

            List<ItemStack> items = table.getItemsResult();

            if (items.size() > 0) {
                double ticks = (ClientTickHandler.ticksInGame + partialTicks) * 3;
                RenderSystem.setShaderColor(1f, 1f, 1f, (float) (0.5f + (Math.sin(Math.toRadians(ticks)) * 0.25)));
                gui.renderItem(items.get(0), i + 132, j + 48);
                gui.renderItemDecorations(Minecraft.getInstance().font, items.get(0), i + 146, j + 48);
                RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            }
        }
    }
}
