package mod.maxbogomol.wizards_reborn.api.crystal;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class CrystalType {
    public CrystalType() {

    }

    public Color getColor() {
        return new Color(255, 255, 255);
    }

    public ResourceLocation getMiniIcon() {
        return new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/spell/empty_mini_icon.png");
    }

    public ResourceLocation getIcon() {
        return new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/spell/empty_icon.png");
    }
}
