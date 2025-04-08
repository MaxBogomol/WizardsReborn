package mod.maxbogomol.wizards_reborn.client.gui.tooltip;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;

public class ValueFrameClientTooltipComponent implements ClientTooltipComponent {
    public ResourceLocation texture;
    public float width;

    public ValueFrameClientTooltipComponent(ValueFrameTooltipComponent component) {
        this.texture = component.getTexture();
        this.width = component.getWidth();
    }

    @Override
    public int getHeight() {
        return 12;
    }

    @Override
    public int getWidth(Font font) {
        return 48;
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics guiGraphics) {
        guiGraphics.blit(texture, x, y, 0, 0, 48, 10, 64, 64);
        int width = 32;
        width /= this.width;
        if (width == 0 && this.width > 0 && !Float.isInfinite(this.width)) width = 1;
        guiGraphics.blit(texture, x + 8, y + 1, 0, 10, width, 8, 64, 64);
    }
}
