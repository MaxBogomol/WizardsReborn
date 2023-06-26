package mod.maxbogomol.wizards_reborn.common.crystal;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class FireCrystalType extends CrystalType {
    public FireCrystalType() {

    }

    public Color getColor() {
        return new Color(225, 127, 103);
    }

    public ResourceLocation getMiniIcon() {
        return new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/spell/fire_mini_icon.png");
    }

    public ResourceLocation getIcon() {
        return new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/spell/fire_icon.png");
    }
}
