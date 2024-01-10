package mod.maxbogomol.wizards_reborn.common.crystal;

import mod.maxbogomol.wizards_reborn.api.crystal.PolishingType;

import java.awt.*;

public class AdvancedPolishingType extends PolishingType {
    public AdvancedPolishingType(String id) {
        super(id, 2);
    }

    public boolean hasParticle() {
        return true;
    }

    public Color getColor() {
        return new Color(222, 202, 136);
    }
}
