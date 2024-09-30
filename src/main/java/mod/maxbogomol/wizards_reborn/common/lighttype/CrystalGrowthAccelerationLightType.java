package mod.maxbogomol.wizards_reborn.common.lighttype;

import mod.maxbogomol.wizards_reborn.api.light.LightType;
import mod.maxbogomol.wizards_reborn.common.crystalritual.CrystalGrowthAccelerationCrystalRitual;

import java.awt.*;

public class CrystalGrowthAccelerationLightType extends LightType {

    public CrystalGrowthAccelerationLightType(String id) {
        super(id);
    }

    public Color getColor() {
        return CrystalGrowthAccelerationCrystalRitual.color;
    }
}
