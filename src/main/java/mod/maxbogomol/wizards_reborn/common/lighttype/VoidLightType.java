package mod.maxbogomol.wizards_reborn.common.lighttype;

import mod.maxbogomol.wizards_reborn.api.light.LightType;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;

import java.awt.*;

public class VoidLightType extends LightType {

    public VoidLightType(String id) {
        super(id);
    }

    public Color getColor() {
        return WizardsRebornCrystals.voidCrystalColor;
    }
}
