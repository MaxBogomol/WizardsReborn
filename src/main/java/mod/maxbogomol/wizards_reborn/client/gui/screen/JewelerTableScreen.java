package mod.maxbogomol.wizards_reborn.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.gui.container.JewelerTableContainer;
import mod.maxbogomol.wizards_reborn.common.block.jeweler_table.JewelerTableBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class JewelerTableScreen extends AbstractContainerScreen<JewelerTableContainer> {
    private final ResourceLocation GUI = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/jeweler_table.png");

    public JewelerTableScreen(JewelerTableContainer screenContainer, Inventory inv, Component titleIn) {
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

        if (menu.blockEntity instanceof JewelerTableBlockEntity table) {
            List<ItemStack> items = table.getItemsResult();

            if (items.size() > 0 && table.itemOutputHandler.getStackInSlot(0).isEmpty()) {
                gui.renderItem(items.get(0), i + 132, j + 36);
                RenderSystem.setShaderColor(1f, 1f, 1f, 0.25f);
                gui.renderItemDecorations(Minecraft.getInstance().font, items.get(0), i + 132, j + 36, String.valueOf(items.get(0).getCount()));
                RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            }
        }
    }
}
