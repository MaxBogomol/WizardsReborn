package mod.maxbogomol.wizards_reborn.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.gui.container.SniffaloContainer;
import mod.maxbogomol.wizards_reborn.common.entity.SniffaloEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SniffaloScreen extends AbstractContainerScreen<SniffaloContainer> {
    private final ResourceLocation GUI = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/sniffalo.png");
    public final SniffaloEntity sniffalo;

    public SniffaloScreen(SniffaloContainer screenContainer, Inventory inv, SniffaloEntity sniffalo) {
        super(screenContainer, inv, sniffalo.getDisplayName());
        this.imageHeight = 192;
        this.inventoryLabelY = this.inventoryLabelY + 26;
        this.sniffalo = sniffalo;
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

        if (!sniffalo.isSaddled()) {
            gui.blit(GUI, i + 8, j + 18, 176, 18, 16, 16, 256, 256);
        }
        if (!sniffalo.isCarpeted()) {
            gui.blit(GUI, i + 8, j + 36, 176, 34, 16, 16, 256, 256);
        }
        if (!sniffalo.isBannered()) {
            gui.blit(GUI, i + 8, j + 54, 176, 50, 16, 16, 256, 256);
        }
        if (!sniffalo.isArmored()) {
            gui.blit(GUI, i + 8, j + 72, 176, 66, 16, 16, 256, 256);
        }

        if (sniffalo.isCarpeted()) {
            for (int ii = 0; ii < 4; ii++) {
                for (int iii = 0; iii < 5; iii++) {
                    gui.blit(GUI, i + 79 + (iii * 18), j + 17 + (ii * 18), 176, 0, 18, 18, 256, 256);
                }
            }
        }

        InventoryScreen.renderEntityInInventoryFollowsMouse(gui, i + 51, j + 70, 11, (float) (i + 51) - x, (float) (j + 70 - 30) - y, this.sniffalo);
    }
}
