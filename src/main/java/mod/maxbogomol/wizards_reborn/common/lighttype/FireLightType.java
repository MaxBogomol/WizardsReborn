package mod.maxbogomol.wizards_reborn.common.lighttype;

import mod.maxbogomol.wizards_reborn.api.light.LightType;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;

import java.awt.*;

public class FireLightType extends LightType {

    public FireLightType(String id) {
        super(id);
    }

    public Color getColor() {
        return WizardsRebornCrystals.fireCrystalColor;
    }
}
