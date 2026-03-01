package mod.maxbogomol.wizards_reborn.common.gui.tooltip;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public class ValueFrameTooltipComponent implements TooltipComponent {
    public ResourceLocation texture;
    public float width;

    public ValueFrameTooltipComponent(ResourceLocation texture, float width) {
        this.texture = texture;
        this.width = width;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public float getWidth() {
        return width;
    }
}
