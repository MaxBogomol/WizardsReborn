package mod.maxbogomol.wizards_reborn.common.crystal;

import mod.maxbogomol.wizards_reborn.api.crystal.PolishingType;

import java.awt.*;

public class PurePolishingType extends PolishingType {

    public static Color color = new Color(147, 208, 212);

    public PurePolishingType(String id) {
        super(id, 4);
    }

    public boolean hasParticle() {
        return true;
    }

    public Color getColor() {
        return color;
    }
}
