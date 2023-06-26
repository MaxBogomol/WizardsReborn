package mod.maxbogomol.wizards_reborn.common.crystal;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class EarthCrystalType extends CrystalType {
    public EarthCrystalType() {

    }

    public Color getColor() {
        return new Color(138, 201, 123);
    }

    public ResourceLocation getMiniIcon() {
        return new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/spell/earth_mini_icon.png");
    }

    public ResourceLocation getIcon() {
        return new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/spell/earth_icon.png");
    }
}
