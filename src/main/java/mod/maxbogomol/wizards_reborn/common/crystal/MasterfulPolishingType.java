package mod.maxbogomol.wizards_reborn.common.crystal;

import mod.maxbogomol.wizards_reborn.api.crystal.PolishingType;

import java.awt.*;

public class MasterfulPolishingType extends PolishingType {

    public static Color color = new Color(178, 227, 148);

    public MasterfulPolishingType(String id) {
        super(id, 3);
    }

    public boolean hasParticle() {
        return true;
    }

    public Color getColor() {
        return color;
    }
}
